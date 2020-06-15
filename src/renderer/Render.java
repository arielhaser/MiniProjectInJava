package renderer;

import elements.*;
import geometries.*;
import primitives.*;
import primitives.Color;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.*;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * A class for rendering image by the scene with image writer element
 * to determine the properties of the image
 */
public class Render {
    Scene _scene;
    ImageWriter _imageWriter;
    private static final int MAX_CALC_COLOR_LEVEL = 10; // max level of the recursion for the reflection and refraction
    private static final double MIN_CALC_COLOR_K = 0.001; // minimum for the K
    private int _threads = 1;
    private final int SPARE_THREADS = 2;
    private boolean _print = true;
    private boolean SOFT_SHADOW = false;
    private int NUM_OF_RAYS = 50;


    /**
     * Render's constructor
     * @param _scene = list of 3D objects
     * @param _imageWriter = properties of the image
     */
    public Render(ImageWriter _imageWriter, Scene _scene) {
        this._scene = _scene;
        this._imageWriter = _imageWriter;
    }

    /**
     * the calColor function returning the calColor function with the k =1 and the MAX_CALC_COLOR_LEVEL
     * @param geopoint the nearest point which insert the objects' by the ray
     * @param inRay the ray arriving to the point
     * @return the calColor function initializing with the k =1 and the MAX_CALC_COLOR_LEVEL
     */
    private Color calcColor(GeoPoint geopoint, Ray inRay) {
        return calcColor(geopoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0).add(
                _scene.getAmbientLight().get_intensity());
    }

    /**
     * Calculate the exact color intensity which the pixel should be draw using all the intermediate functions
     * @param geo = the nearest point which insert the objects' by the ray
     * @param inRay = the ray arriving to the point
     * @return = the color of the pixel
     */
    private Color calcColor(GeoPoint geo, Ray inRay, int level, double k)
    {
        Color color = new Color (geo.geometry.get_emission());

        Vector v = inRay.get_direction();
        Vector n = geo.geometry.getNormal(geo.point);
        Material material = geo.geometry.get_material();
        int nShininess = material.get_nShininess();
        double kd = material.get_kD();
        double ks = material.get_kS();
        for (LightSource lightSource : _scene.get_lights()) {
            Vector l = lightSource.getL(geo.point);
            if (alignZero(n.dotProduct(l))*alignZero(n.dotProduct(v)) > 0) {
                double ktr = transparency(lightSource, l, n, geo);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(geo.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }

        if (level == 1) return Color.BLACK; //condition to stop the recursion
        double kr = geo.geometry.get_material().get_kR(), kkr = k * kr;
        // the other condition to stop the recursion
        if (kkr > MIN_CALC_COLOR_K)  {
            Ray reflectedRay = constructReflectedRay(n, geo.point, inRay);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay,
                        level - 1, kkr).scale(kr));
        }
        double kt = geo.geometry.get_material().get_kT(), kkt = k * kt;
        //condition to stop the recursion
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(n, geo.point, inRay);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null)
                color = color.add(calcColor(refractedPoint, refractedRay,
                        level - 1, kkt).scale(kt));
        }
        return color;
    }

    /**
     *
     * @param ks specular component coefficient
     * @param l the vector direction l from light to point
     * @param n the vector normal at the point
     * @param v direction from point of view to point
     * @param nShininess shininess level
     * @param lightIntensity light intensity at the point
     * @return specular component light effect at the point
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = (n.scale((l.dotProduct(n))*-2)).add(l);
        double factor = ks*(Math.pow(max((-1)*v.dotProduct(r),0), nShininess));
        return lightIntensity.scale(factor);
    }

    /**
     * Calculate Diffusive component of light reflection.
     * @param kd the coefficient kD
     * @param l the vector direction l from light to point
     * @param n the vector normal at the point
     * @param lightIntensity light intensity at the point
     * @return diffusive component of light reflection
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double factor = kd*Math.abs(l.dotProduct(n));
        return lightIntensity.scale(factor);
    }

    /**
     * Find the closest point in the points on objects' which inserted
     * by the ray
     * @param intersectionPoints = list of all the interaction points
     * @return the closet 3D point
     */
    private GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints)
    {
        double minDistance = Double.MAX_VALUE;
        Point3D p0 = this._scene.getCamera().get_p0();
        GeoPoint minDistancePoint = null;

        for (GeoPoint geoPoint: intersectionPoints)
        {
         if(p0.distance(geoPoint.point) < minDistance){
            minDistance = p0.distance(geoPoint.point);
            minDistancePoint = new GeoPoint(geoPoint.geometry, geoPoint.point);}
        }
        return minDistancePoint;
    }

    /**
     * activate writeToImage function which find in ImageWriter class
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }

    /**
     * function allowing to check the presence of shade or not.
     * If nothing separates the geometric shape and the light source, then it is unshaded,
     * that is to say that it receives this light source normally.
     * And if the light source does not reach the geometric shape at a specific location,
     * due to the presence of something else  between twice:  this location will be shaded.
     * @param light the light Source
     * @param l vector from the source light to the geometry
     * @param n the vector normal
     * @param gp the GeoPoint located on the geometry
     * @return True if it's unshaded and False if there is shadow
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint gp){
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) return true;
        double lightDistance = light.getDistance(gp.point);
        for (GeoPoint geo : intersections) {
            // verify if the intersection isn't after the light source and if the geometry isn't opaque
            if (alignZero(geo.point.distance(gp.point) - lightDistance) <= 0
            && gp.geometry.get_material().get_kT() == 0)
                return false;
        }
        return true;
    }

    /**
     * the same function as unshaded but the light passes more or less through an object
     * which is not opaque according to a coefficient of transparency.
     * it works by calculating the average between the sum of ktr with the number of rays present in the beam
     * @param light the light source
     * @param l vector from the source light to the geometry
     * @param n the vector normal
     * @param geopoint the GeoPoint located on the geometry
     * @return ktr: the coefficient of transparency which starts at 1
     * and which decreases with each meeting of a new intersection
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        double sum_ktr = 0;
        List<Ray> rays = constructRaysToLight(light, l, n, geopoint);
        for (Ray ray : rays) {
            List<GeoPoint> intersections = _scene.getGeometries().findIntersections(ray);
            if (intersections != null) {
                double lightDistance = light.getDistance(geopoint.point);
                double ktr = 1;
                for (GeoPoint geo : intersections) {
                    if (alignZero(geo.point.distance(geopoint.point) - lightDistance) <= 0) {
                        ktr *= geo.geometry.get_material().get_kT();
                        if (ktr < MIN_CALC_COLOR_K) {
                            ktr = 0;
                            break;
                        }
                    }
                }
                sum_ktr += ktr;
            } else
                sum_ktr += 1;
        }
        return sum_ktr/rays.size();// the average
    }

    /**
     * the function allows to create the beam of rays from the geometry to the source lights
     * @param light the light source
     * @param l vector from the source light to the geometry
     * @param n the vector normal
     * @param geopoint the GeoPoint located on the geometry
     * @return the beam of rays
     */
    private List<Ray> constructRaysToLight(LightSource light, Vector l, Vector n, GeoPoint geopoint){
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point, lightDirection, n);
        List<Ray> beam = new ArrayList<>();
        beam.add(lightRay);
        if (!SOFT_SHADOW || light.getRadius() == 0) return beam;
        Point3D p0 = lightRay.get_p00();
        Vector v = lightRay.get_direction();
        Vector vx = (new Vector(-v.get_head().get_y().get(), v.get_head().get_x().get(),0)).normalized();
        Vector vy = (v.crossProduct(vx)).normalized();
        double r = light.getRadius();
        Point3D pC = lightRay.getPoint(light.getDistance(p0));
        for (int i=0; i<NUM_OF_RAYS-1; i++){
            // create random polar system coordinates of a point in circle of radius r
            double cosTeta = random(-1,1);
            double sinTeta = sqrt(1 - cosTeta*cosTeta);
            double d = random (-r,r);
            // Convert polar coordinates to Cartesian ones
            double x = d*cosTeta;
            double y = d*sinTeta;
            // pC - center of the circle
            // p0 - start of central ray, v - its direction, distance - from p0 to pC
            Point3D point = pC;
            if (!isZero(x)) point = point.add(vx.scale(x));
            if (!isZero(y)) point = point.add(vy.scale(y));
            beam.add(new Ray(p0, point.subtract(p0))); // normalized inside Ray ctor
        }
        return beam;
    }

    /**
     * the function which will allow us to choose the placement of the rays at random in the virtual circle
     * @param rangeMin the minimum limit
     * @param rangeMax the maximum limit
     * @return the random result in the domain
     */
    public double random(double rangeMin, double rangeMax){
        Random r = new Random();
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }

        /**
         * print a grid on the image utself, useful for debugging
         * @param interval = a number which decide how many times the line should be printed
         * @param colorsep = specific color of the grid
         */
    public void printGrid(int interval,Color colorsep) {
        double rows = this._imageWriter.getNx();
        double cols = _imageWriter.getNy();
        //Writing the lines.
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                if (col % interval == 0 || row % interval == 0) {
                    _imageWriter.writePixel(row, col, colorsep);
                }
            }
        }
    }

    /**
     * function allowing to find the closest intersection
     * @param ray the ray with which we will look for the intersection points
     * @return the closest intersection GeoPoint
     */
    private GeoPoint findClosestIntersection(Ray ray){

        Intersectable geometries = _scene.getGeometries();
        List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
        if (intersectionPoints == null)
            return null;

        double minDistance = Double.MAX_VALUE;
        Point3D p0 = ray.get_p00();
        GeoPoint minDistancePoint = null;
        for (GeoPoint geoPoint: intersectionPoints)
        {
            if(p0.distance(geoPoint.point) < minDistance){
                minDistance = p0.distance(geoPoint.point);
                minDistancePoint = new GeoPoint(geoPoint.geometry, geoPoint.point);}
        }
        return minDistancePoint;

    }

    /**
     * function allowing to construct the reflected ray from the point
     * @param n the normal vector of the geometry
     * @param point the origin point of the reflected ray
     * @param ray the ray which arrives at the point and which will reflected
     * @return the reflected ray
     */
    public Ray constructReflectedRay(Vector n, Point3D point, Ray ray){
        Vector v = ray.get_direction();
        Vector r = (n.scale((v.dotProduct(n))*-2)).add(v);
        return new Ray(point, r, n);
    }

    /**
     * function allowing to construct the refracted ray from the point: in our project he is in the same direction
     * of he ray which arrives at the point
     * @param n the normal vector of the geometry
     * @param point the origin point of the refracted ray
     * @param ray the ray which arrives at the point and which will refracted
     * @return the refracted ray
     */
    public Ray constructRefractedRay(Vector n, Point3D point, Ray ray){
        return new Ray(point, ray.get_direction(), n);
    }

    // ------------------NEW SECTION-------------------
    /**
	 * Pixel is an internal helper class whose objects are associated with a Render object that
	 * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
	 * its progress.<br/>
	 * There is a main follow up object and several secondary objects - one in each thread.
	 * @author Dan
	 *
	 */
	private class Pixel {
		private long _maxRows = 0;
		private long _maxCols = 0;
		private long _pixels = 0;
		public volatile int row = 0;
		public volatile int col = -1;
		private long _counter = 0;
		private int _percents = 0;
		private long _nextCounter = 0;

		/**
		 * The constructor for initializing the main follow up Pixel object
		 * @param maxRows the amount of pixel rows
		 * @param maxCols the amount of pixel columns
		 */
		public Pixel(int maxRows, int maxCols) {
			_maxRows = maxRows;
			_maxCols = maxCols;
			_pixels = maxRows * maxCols;
			_nextCounter = _pixels / 100;
			if (Render.this._print) System.out.printf("\r %02d%%", _percents);
		}

		/**
		 *  Default constructor for secondary Pixel objects
		 */
		public Pixel() {}

		/**
		 * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
		 * critical section for all the threads, and main Pixel object data is the shared data of this critical
		 * section.<br/>
		 * The function provides next pixel number each call.
		 * @param target target secondary Pixel object to copy the row/column of the next pixel 
		 * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
		 * finished, any other value - the progress percentage (only when it changes)
		 */
		private synchronized int nextP(Pixel target) {
			++col;
			++_counter;
			if (col < _maxCols) {
				target.row = this.row;
				target.col = this.col;
				if (_counter == _nextCounter) {
					++_percents;
					_nextCounter = _pixels * (_percents + 1) / 100;
					return _percents;
				}
				return 0;
			}
			++row;
			if (row < _maxRows) {
				col = 0;
				if (_counter == _nextCounter) {
					++_percents;
					_nextCounter = _pixels * (_percents + 1) / 100;
					return _percents;
				}
				return 0;
			}
			return -1;
		}

		/**
		 * Public function for getting next pixel number into secondary Pixel object.
		 * The function prints also progress percentage in the console window.
		 * @param target target secondary Pixel object to copy the row/column of the next pixel 
		 * @return true if the work still in progress, -1 if it's done
		 */
		public boolean nextPixel(Pixel target) {
			int percents = nextP(target);
			if (percents > 0)
				if (Render.this._print) System.out.printf("\r %02d%%", percents);
			if (percents >= 0)
				return true;
			if (Render.this._print) System.out.printf("\r %02d%%", 100);
			return false;
		}
	}

    /**
	 * This function renders image's pixel color map from the scene included with
     * the Renderer object.
     * Draw any pixel in the view plane in specific color
     * which determine by the objects' color's and background
     * in the scene.
     */
	public void renderImage() {
		final int nX = _imageWriter.getNx();
		final int nY = _imageWriter.getNy();
		final double dist = _scene.getDistance();
		final double width = _imageWriter.getWidth();
		final double height = _imageWriter.getHeight();
		final Camera camera = _scene.getCamera();

		final Pixel thePixel = new Pixel(nY, nX);

		// Generate threads
		Thread[] threads = new Thread[_threads];
		for (int i = _threads - 1; i >= 0; --i) {
			threads[i] = new Thread(() -> {
				Pixel pixel = new Pixel();
				while (thePixel.nextPixel(pixel)) {
				    List<Ray> rays = camera.constructRaysThroughPixel(nX, nY, pixel.col, pixel.row, //
                            dist, width, height);
				    _imageWriter.writePixel(pixel.col, pixel.row, calcColor(rays));
				}
			});
		}

		// Start threads
		for (Thread thread : threads) thread.start();

		// Wait for all threads to finish
		for (Thread thread : threads) try { thread.join(); } catch (Exception e) {}
		if (_print) System.out.printf("\r100%%\n");
	}

    private Color calcColor(List<Ray> rays) {
        Color sum_color = new Color(Color.BLACK);
        for (Ray ray : rays) {
            GeoPoint closestPoint = findClosestIntersection(ray);
            if (closestPoint == null)
                sum_color = sum_color.add(_scene.getBackground()); // the pixel with the background color at this point
            else
                sum_color = sum_color.add(calcColor(closestPoint, ray));
        }
        return sum_color.reduce(rays.size());
    }

    /**
	 * Set multithreading <br>
	 * - if the parameter is 0 - number of coress less 2 is taken
	 * 
	 * @param threads number of threads
	 * @return the Render object itself
	 */
	public Render setMultithreading(int threads) {
		if (threads < 0)
			throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
		if (threads != 0)
			_threads = threads;
		else {
			int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
			if (cores <= 2)
				_threads = 1;
			else
				_threads = cores;
		}
		return this;
	}

	/**
	 * Set debug printing on
	 * 
	 * @return the Render object itself
	 */
	public Render setDebugPrint() {
		_print = true;
		return this;
	}

    /**
     * the setter of Soft shadow
     * @param SOFT_SHADOW the boolean
     */
    public void set_SOFT_SHADOW(boolean SOFT_SHADOW) {
        this.SOFT_SHADOW = SOFT_SHADOW;
    }

    /**
     * the setter of number of rays
     * @param NUM_OF_RAYS the integer: number of rays
     */
    public void set_NUM_OF_RAYS(int NUM_OF_RAYS) {
        this.NUM_OF_RAYS = NUM_OF_RAYS;
    }

}
