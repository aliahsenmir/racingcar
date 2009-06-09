/**
 * 
 */
package solo;


import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Comparator;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import antlr.collections.impl.Vector;
import cern.colt.Sorting;
import cern.colt.Swapper;

import com.graphbuilder.curve.ControlPath;

/**
 * @author kokichi3000
 *
 */
public final class EdgeDetector {


	public final static double DELTA = 0.001;
	final static double PRECISION = 1000000.0d;
	public final static double PI_2 = Math.PI/2;
	public int whichE = 0;//highest point belong to which edge,0 = unknown

	public final static double UNKNOWN_ANGLE = -2;
	public final static double DELTATIME = 0.002;
	public final static double MAXSTEERSPEED = Math.PI*2/3;
	public final static double ANGLEACCURACY =100.0d; 
	public final static double MINDIST = 1;
	
	public final static Comparator<Vector2D> Vector2DComparator = new Comparator<Vector2D>(){

		@Override
		public final int compare(Vector2D a, Vector2D b) {
			// TODO Auto-generated method stub
			return (a.y==b.y) ? 0 : (a.y>b.y) ? 1 : -1;
		}};
		
	
//	public final static Swapper swapper = new Swapper(){
//		@Override
//		public void swap(int i, int j) {
//			// TODO Auto-generated method stub
//			double tmp = angles[i];
//			angles[i] = angles[j];
//			angles[j] = tmp;
//			Vector2D v = backP[i];
//			backP[i] = backP[j];
//			backP[j] = v;
//		}
//	};
	
	public static class Swap implements Swapper{
		Object[] backP;
		
		public Swap(Object[] b) {
			// TODO Auto-generated constructor stub
			backP = b;
		}
		
		@Override
		public void swap(int i, int j) {
			// TODO Auto-generated method stub
			Object v = backP[i];
			backP[i] = backP[j];
			backP[j] = v;
			
		}		
	}
	
	Vector2D currentPointAhead = null;
	ControlPath cp=null;
	double[] tracks;

	double trackWidth=0;
	double curPos=0;	
	double distRaced;	

	public final static int NUM_POINTS=150;
	double curAngle;
	double maxY;

	public ObjectArrayList<Vector2D> left = null;
	public ObjectArrayList<Vector2D> right = null;
	private static Vector2D[] backP = new Vector2D[NUM_POINTS];//x is axis along track axis
	private static Vector2D[] backR = new Vector2D[NUM_POINTS];//x is axis along track axis
	public ObjectArrayList<Vector2D> allPoints = null;
//	public static ObjectArrayList<Vector2D> allPointsR = null;
//	private static double[] angles = new double[NUM_POINTS];
//	DoubleArrayList x = null;
//	DoubleArrayList y = null;
	int numpoint = 0;
//	int firstIndexMax = -1,lastIndexMax = -1;

	double maxDistance=-1;
	public double leftStraight = -1;
	public double rightStraight = -1;
	public double straightDist =-1;
	Vector2D highestPoint = null;	
	int turn;
	Vector2D center = null;
	double radiusL = -1;
	double radiusR = -1;
//	private static double[] tmpX = new double[NUM_POINTS];//x is axis along track axis
//	private static double[] tmpY = new double[NUM_POINTS];
//	private static double[] tmpRx = new double[NUM_POINTS];//x is axis along track axis
//	private static double[] tmpRy = new double[NUM_POINTS];

	/**
	 * Copy Constructor
	 *
	 * @param edgeDetector a <code>EdgeDetector</code> object
	 */
	public EdgeDetector(EdgeDetector edgeDetector) 
	{
		this.cp = edgeDetector.cp;
		this.trackWidth = edgeDetector.trackWidth;
		this.curPos = edgeDetector.curPos;
		this.distRaced = edgeDetector.distRaced;
		this.curAngle = edgeDetector.curAngle;
		this.maxY = edgeDetector.maxY;
		this.center = (edgeDetector.center==null) ? null : new Vector2D(edgeDetector.center);
		this.radiusL = edgeDetector.radiusL;
		this.radiusR = edgeDetector.radiusR;		
//		this.left = edgeDetector.left;
//		this.right = edgeDetector.right;
//		this.firstIndexMax = edgeDetector.firstIndexMax;
//		this.lastIndexMax = edgeDetector.lastIndexMax;
		this.maxDistance = edgeDetector.maxDistance;
		this.leftStraight = edgeDetector.leftStraight;
		this.rightStraight = edgeDetector.rightStraight;
		this.straightDist = edgeDetector.straightDist;
		this.highestPoint = edgeDetector.highestPoint;
//		this.x = new DoubleArrayList(edgeDetector.x);
//		this.y = new DoubleArrayList(edgeDetector.y);
		this.numpoint = edgeDetector.numpoint;
		this.turn = edgeDetector.turn;		
		this.currentPointAhead = edgeDetector.currentPointAhead;		
		this.whichE = edgeDetector.whichE;
		this.left = (edgeDetector.left!=null)? new ObjectArrayList<Vector2D>(edgeDetector.left) : null;
		this.right = (edgeDetector.right!=null)? new ObjectArrayList<Vector2D>(edgeDetector.right) : null;
		this.allPoints = (edgeDetector.allPoints!=null)? new ObjectArrayList<Vector2D>(edgeDetector.allPoints) : null;
	}


	public EdgeDetector() {		
	}


	public EdgeDetector(CarState cs) {
		// TODO Auto-generated constructor stub
		tracks = cs.getTrack();
		curPos = -Math.round(cs.getTrackPos()*PRECISION)/PRECISION;
		//		curPos = -cs.getTrackPos();
		curAngle = Math.round(cs.getAngle()*PRECISION)/PRECISION;
		maxDistance=-1;
		int firstIndexMax = -1;
		int lastIndexMax = -1;		
		maxY = -1;
		distRaced = Math.round(cs.distRaced*PRECISION)/PRECISION;			
		trackWidth =-1;

		if (Math.abs(curAngle)<0.01)
			trackWidth = Math.round((tracks[0]+tracks[18])*Math.cos(cs.angle));						

		leftStraight=-1;
		rightStraight=-1;			
		int j = 0;
		
		for (int i=0;i<19;++i){
			double  angle = Math.PI-SimpleDriver.ANGLE_LK[i]-cs.angle;
			//			double xx = tracks[i]* Math.cos(angle);
			//			double yy = tracks[i]* Math.sin(angle);
			double xx =Math.round(tracks[i]* Math.cos(angle)*PRECISION)/PRECISION;
			double yy =Math.round(tracks[i]* Math.sin(angle)*PRECISION)/PRECISION;
			if (yy<0) continue;
			//			angle=Math.round((Math.PI-angle)*ANGLEACCURACY)/ANGLEACCURACY;
			//			angle = Math.PI - angle;			
			angle = (xx==0) ? PI_2 : (yy>=0) ? Math.PI-Math.atan2(yy,xx) : Math.PI - angle;
			angle = Math.round(angle*PRECISION)/PRECISION;
			if (i==9){
				currentPointAhead = new Vector2D(xx,yy);
			}


			if (maxDistance<tracks[i])
				maxDistance = tracks[i];

			if (maxY<yy && tracks[i]<=99.95){
				maxY = yy;
				firstIndexMax =j;
				lastIndexMax =j;				
			} else if (maxY==yy && lastIndexMax>=0 && tracks[lastIndexMax]<=99.95){
				lastIndexMax =j;
			} else if (tracks[i]>99.95){				
				if (maxY<yy && firstIndexMax>=0 && tracks[firstIndexMax]<=99.95){
					maxY = yy;
					firstIndexMax =j;
					lastIndexMax =j;
				} else if (maxY==yy && lastIndexMax>=0){
					lastIndexMax = j;
				} else {
					continue;
				}
			}			
//			tmpX[j] = xx;
//			tmpY[j] = yy;
			Vector2D v = new Vector2D(xx,yy);
			backP[j] = v;
//			angles[j] = angle;
			j++;
		}
		for (int i=0;i<j;++i){
			backR[i] = backP[j-1-i];
		}
		numpoint = j;	
		allPoints = ObjectArrayList.wrap(backP,j);
//		allPointsR = ObjectArrayList.wrap(backR,j);
//		this.x = DoubleArrayList.wrap(tmpX, j);
//		this.y = DoubleArrayList.wrap(tmpY, j);


		highestPoint = (firstIndexMax>0 && firstIndexMax<numpoint) ? backP[firstIndexMax] : null;		
		left = (firstIndexMax>0 && firstIndexMax<numpoint) ? ObjectArrayList.wrap(backP,firstIndexMax)  : null;
		right = (lastIndexMax<numpoint-1 && lastIndexMax>=0) ? ObjectArrayList.wrap(backR,numpoint-1-lastIndexMax) : null;
		if (firstIndexMax>0){
			Vector2D v = backP[firstIndexMax-1];
			if (highestPoint.length()<99.95 && highestPoint.distance(v)<trackWidth) {
				whichE = -1; 
				left = (firstIndexMax>0 && firstIndexMax<numpoint) ? ObjectArrayList.wrap(backP,firstIndexMax+1)  : null;
			}
		}
		if (whichE==0 && lastIndexMax<numpoint-1){
			Vector2D v = backP[lastIndexMax+1];
			if (highestPoint!=null && highestPoint.length()<99.95 && highestPoint.distance(v)<trackWidth) {
				whichE = 1;
				right = (lastIndexMax<numpoint-1 && lastIndexMax>=0) ? ObjectArrayList.wrap(backR,numpoint-lastIndexMax) : null;
			}
		}
		
		double l0 = (left!=null && left.size()>0) ? left.get(0).x : -10000;
		double l1 = (left!=null && left.size()>0) ? left.get(left.size()-1).x : -10000;
		double r0 = (right!=null && right.size()>0) ? right.get(0).x : -10000;
		double r1 = (right!=null && right.size()>0) ? right.get(right.size()-1).x : -10000;
		boolean straight = (highestPoint!=null && highestPoint.length()>=99.95);
		boolean nlS = Math.abs(l1-l0)>TrackSegment.EPSILON;
		boolean nrS = Math.abs(r1-r0)>TrackSegment.EPSILON;
		if (straight && (Math.abs(l1-l0)<=TrackSegment.EPSILON || Math.abs(r1-r0)<=TrackSegment.EPSILON)) 
			turn = MyDriver.STRAIGHT;
		else if ((nlS && l1>l0) || (nrS && r1>r0))
			turn = 1;
		else if ((nlS && l1<l0) || (nrS && r1<r0))
			turn = -1;
		else turn = 2;
		
		double dL = getStraightDist(left);
		double dR = getStraightDist(right);
		straightDist = Math.max(dL, dR);

	}	
	
	public final double getStraightDist(ObjectArrayList<Vector2D> left){
		double val = 0;
		if (left!=null && left.size()>0){
			double l0 = -10000;
			boolean ok = false;
			int i = 0;
			for (i=0;i<left.size();++i){
				Vector2D v = left.get(i);
				if (!ok && v.y>0) {
					l0 = v.x;
					ok = true;
					continue;
				} 
				if (ok && Math.abs(v.x-l0)>TrackSegment.EPSILON)				
					break;				
			}
			val = left.get(i-1).y;	
		}
		return val;
	}
	
public final static void join(ObjectArrayList<Vector2D> edge,ObjectArrayList<Vector2D> oldEdge){
	int len = oldEdge.size();
	Vector2D p = null;
	
	for (int i=0;i<len;++i){
		Vector2D v = oldEdge.get(i);
		double x = v.x;
		double y = v.y;
		if (Math.sqrt(x*x+y*y)>99.0) continue;			
					
		if (edge!=null && edge.size()>0){
			int index = Sorting.binarySearchFromTo(edge.elements(), v, 0, edge.size()-1,Vector2DComparator);		
			if (index<0) index = -index-1;
			if (index>0){				
				p = edge.get(index-1);
				if (Math.hypot(p.x-x,p.y-y)<MINDIST) continue;				
			}
	
			if (index<edge.size()){				
				p = edge.get(index);
				if (Math.hypot(p.x-x,p.y-y)<MINDIST) continue;					
			}
			
			edge.add(v);
		}
	}
	if (edge!=null && edge.size()>0)
	Arrays.quicksort(edge.elements(), 0,edge.size()-1,new Swap(edge.elements()), Vector2DComparator);
}

public final AffineTransform combine(EdgeDetector ed,double distRaced){
	long ti = System.currentTimeMillis();
	if (distRaced>ed.straightDist)
		return null;
	if (trackWidth<=0) {
		trackWidth = ed.trackWidth;
	}	
	straightDist = Math.max(ed.straightDist - distRaced,this.straightDist);
	double tW = Math.round(trackWidth)*0.5d;
	double prevTW = Math.round(ed.trackWidth)*0.5d;
	double toMiddle = -tW*curPos;
	double prevToMiddle = -prevTW*ed.curPos;
	double ax = toMiddle-prevToMiddle;
	int len=ed.numpoint;

	double scale = tW/prevTW;		
//	double[] xx = ed.x.elements();
//	double[] yy = ed.y.elements();	
	
	AffineTransform at = new AffineTransform();
	at.scale(scale, 1);
	at.translate(ax, -distRaced);
	int sz = (numpoint+ed.numpoint<NUM_POINTS) ? NUM_POINTS : (numpoint+ed.numpoint)*2;
	if (backP.length<sz){
//		tmpX = new double[sz];
//		tmpY = new double[sz];		
		backR = new Vector2D[sz];
		System.arraycopy(backP, 0, backR, 0, numpoint);
		backP = new Vector2D[sz];
		System.arraycopy(backR, 0, backP, 0, numpoint);
//		System.arraycopy(angles, 0, tmpX, 0, numpoint);
//		angles = new double[sz];
//		System.arraycopy(tmpX, 0, angles, 0, numpoint);
		left = null;
		right = null;
	} 		
	Vector2D[] bF = new Vector2D[len];
	Vector2D[] bB = new Vector2D[len];
	if (ed.allPoints!=null)
	for (int i=0;i<len;++i){
		Vector2D v = ed.allPoints.get(i);
		v.x *= scale;
		v.x += ax;
		v.y -= distRaced;		
		bF[i] = v;
		bB[len-1-i] = bF[i];
	}
		
	if (ed!=null && ed.left!=null && ed.left.size()>0) join(left,ObjectArrayList.wrap(bF, ed.left.size()));
	if (ed!=null && ed.right!=null && ed.right.size()>0)join(right,ObjectArrayList.wrap(bB, ed.right.size()));
	Vector2D lower = (highestPoint.y<ed.highestPoint.y) ? highestPoint : ed.highestPoint;
	int whichL = (highestPoint.y<ed.highestPoint.y) ? whichE : ed.whichE;
	Vector2D higher = (highestPoint.y<ed.highestPoint.y) ? ed.highestPoint : highestPoint;
	int whichH = (highestPoint.y<ed.highestPoint.y) ? ed.whichE : whichE;
	if (lower.distance(higher)>0){
		double  angleH = (higher.x==0) ? PI_2 : Math.PI-Math.atan2(higher.y,higher.x);	
		angleH=Math.round(angleH*PRECISION)/PRECISION;
		double  angleL = (lower.x==0) ? PI_2 : Math.PI-Math.atan2(lower.y,lower.x);	
		angleL=Math.round(angleL*PRECISION)/PRECISION;
		if (whichL==0){
			if (angleL<angleH && left!=null) {
				int index = Sorting.binarySearchFromTo(left.elements(), lower, 0, left.size()-1, Vector2DComparator);
				if (index<0) index = -index-1;
				left.add(index,lower);
			} else if (angleL>angleH && right!=null){
				int index = Sorting.binarySearchFromTo(right.elements(), lower, 0, right.size()-1, Vector2DComparator);
				if (index<0) index = -index-1;
				right.add(index,lower);
			}
		}
	}
		
	if (whichH==0){
		Vector2D point = (left==null || left.size()==0) ? null : left.get(left.size()-1);
		if (point!=null && point.distance(higher)<trackWidth){
			left.add(higher);
			whichH = -1;
		} else {
			point = (right==null || right.size()==0) ? null : right.get(right.size()-1);
			if (point!=null && point.distance(higher)<trackWidth){
				right.add(higher);
				whichH = 1;
			}	
		}
	}	
	highestPoint = higher;
	whichE = whichH;
	System.out.println("I : "+(System.currentTimeMillis()-ti));

	int lS = (left==null || left.size()==0) ? 0 : left.size();
	int rS = (right==null || right.size()==0) ? 0 : right.size();
	numpoint = (whichE==0) ? lS+rS+1 : lS+rS;
	Vector2D[] newArr = new Vector2D[numpoint];
	int j = left.size();
	if (left!=null)
		System.arraycopy(left.elements(), 0, newArr, 0, j);
	if (whichE==0)
		newArr[j++] = highestPoint;
	
	if (right!=null)
		System.arraycopy(right.elements(), 0, newArr, j, right.size());

	allPoints = ObjectArrayList.wrap(newArr, numpoint);
	double dL = getStraightDist(left);
	double dR = getStraightDist(right);
	straightDist = Math.max(dL, dR);
	System.out.println("K : "+(System.currentTimeMillis()-ti));
	return at;
}



public int estimateTurn(){						
	boolean all=true;
	double toRight = toRightEdge();
	int firstIndexMax = left.size();
	int lastIndexMax = firstIndexMax;
	for (int i=firstIndexMax;i<=lastIndexMax;++i){
		Vector2D v = allPoints.get(i); 
		double r = v.x+toRight;
		if (!(Math.abs(r)<=DELTA || Math.abs(r-trackWidth)<=DELTA)){
			all = false;
		} else if (r>0){
			return MyDriver.TURNRIGHT;			
		} else if (r<-trackWidth)
			return MyDriver.TURNLEFT;
	}
	if (all)
		//			if (maxDistance>=99)
		return MyDriver.STRAIGHT;
	//		else return MyDriver.UNKNOWN;

	double sum=0;		
	for (int i=0;i<firstIndexMax;++i){
		Vector2D v = allPoints.get(i);
		sum += v.x;
	}
	double meanL = Math.round(sum/firstIndexMax*10000)/10000.0d;
	sum=0;
	int len = this.allPoints.size();
	for (int i=lastIndexMax+1;i<len;++i){
		Vector2D v = allPoints.get(i);
		sum += v.x;
	}
	double meanR = Math.round(sum/(len-1-lastIndexMax)*10000.0)/10000.0d;

	if (firstIndexMax<=0 || lastIndexMax>=len-1)
		return MyDriver.UNKNOWN;
	double rl = allPoints.get(firstIndexMax-1).x;
	double rr = allPoints.get(lastIndexMax+1).x;

	if (Math.abs(rl-meanL)<=DELTA && Math.abs(rr-meanR)<=DELTA){
		if (maxDistance>=99) {		
			return MyDriver.STRAIGHT;
		}
		return MyDriver.UNKNOWN;
	}
	if (meanR<=0 || rl<meanL-DELTA || meanR>rr+DELTA)
		return MyDriver.TURNLEFT;

	return MyDriver.TURNRIGHT;
}



public double toMiddle(){
	if (trackWidth<=0 || curPos<-1 || curPos>1) return Double.NaN;
	return Math.round(trackWidth/2*curPos*10000.0d)/10000.0d;
}

public double toLeftEdge(){
	if (trackWidth<=0 || curPos<-1 || curPos>1) return Double.NaN;
	return Math.round(trackWidth/2*(1+curPos)*10000.0d)/10000.0d;
}

public double toRightEdge(){
	if (trackWidth<=0 || curPos<-1 || curPos>1) return Double.NaN;
	return -Math.round(trackWidth/2*(1-curPos)*10000.0d)/10000.0d;
}

/**
 * @return the trackWidth
 */
public double getTrackWidth() {
	return trackWidth;
}

/**
 * @param trackWidth the trackWidth to set
 */
public void setTrackWidth(double trackWidth) {
	this.trackWidth = trackWidth;
}

/**
 * @return the maxDistance
 */
public double getMaxDistance() {
	return maxDistance;
}

/**
 * @param maxDistance the maxDistance to set
 */
public void setMaxDistance(double maxDistance) {
	this.maxDistance = maxDistance;
}

double SimSteerUpdate(double oldSteer,double steer){		
	/* input control */		
	steer *= SimpleDriver.steerLock;
	double stdelta = steer - oldSteer;
	double sign = (stdelta<0) ?-1:1;

	if ( Math.abs(stdelta/DELTATIME) > MAXSTEERSPEED ){
		steer = sign * MAXSTEERSPEED * DELTATIME + oldSteer;
	};


	return steer;		
}


public void drawEdge(double[] x,double[] y,final String title){			
	XYSeries series = new XYSeries("Curve");

	//		for (int i=0;i<numPointLeft;++i){
	//		series.add(leftEgdeX[i], leftEgdeY[i]);
	//		}

	for (int i=0;i<x.length;++i){			
		series.add(x[i],y[i]);
	}

	//		for (int i=0;i<numPointRight;++i){
	//		series.add(rightEgdeX[i], rightEgdeY[i]);
	//		}

	XYDataset xyDataset = new XYSeriesCollection(series);

	// Create plot and show it
	final JFreeChart chart = ChartFactory.createScatterPlot(title, "x", "Membership", xyDataset, PlotOrientation.VERTICAL, false, true, false );
	chart.getXYPlot().getDomainAxis().setRange(-20.0,90.0);
	chart.getXYPlot().getRangeAxis().setRange(-20.0,100.0);

	Thread p = new Thread(new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				BufferedImage image = chart.createBufferedImage(600, 400);
				ImageIO.write(image, "png", new File(title+".png"));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	});

	p.start();

	//		ChartPanel chartPanel = new ChartPanel(chart);
	//		jf.setContentPane(chartPanel);
	//		jf.setPreferredSize(new Dimension(600,400));
	//		jf.setMinimumSize(new Dimension(600,400));
	//		jf.setVisible(true);

}


public static void drawEdge(ObjectList<Vector2D> vs,final String title){			
	XYSeries series = new XYSeries("Curve");

	for (Vector2D v:vs){			 
		series.add(v.x,v.y);
	}

	XYDataset xyDataset = new XYSeriesCollection(series);

	// Create plot and show it
	final JFreeChart chart = ChartFactory.createScatterPlot(title, "x", "Membership", xyDataset, PlotOrientation.VERTICAL, false, true, false );
	chart.getXYPlot().getDomainAxis().setRange(-50.0,50.0);
	chart.getXYPlot().getRangeAxis().setRange(-20.0,100.0);

	Thread p = new Thread(new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				BufferedImage image = chart.createBufferedImage(600, 400);
				ImageIO.write(image, "png", new File(title+".png"));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	});

	p.start();
}

public static void drawEdge(Vector2D[] vs,final String title){			
	XYSeries series = new XYSeries("Curve");

	for (int i=0;i<vs.length;++i){			
		series.add(vs[i].x,vs[i].y);
	}

	XYDataset xyDataset = new XYSeriesCollection(series);

	// Create plot and show it
	final JFreeChart chart = ChartFactory.createScatterPlot(title, "x", "Membership", xyDataset, PlotOrientation.VERTICAL, false, true, false );
	chart.getXYPlot().getDomainAxis().setRange(-50.0,50.0);
	chart.getXYPlot().getRangeAxis().setRange(-20.0,100.0);

	Thread p = new Thread(new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				BufferedImage image = chart.createBufferedImage(600, 400);
				ImageIO.write(image, "png", new File(title+".png"));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	});

	p.start();
}

public static void drawEdge(EdgeDetector ed,final String title){			
	XYSeries series = new XYSeries("Curve");

	for (int i=0;i<ed.numpoint;++i){
		Vector2D v = ed.allPoints.get(i);
		series.add(v.x,v.y);
	}

	XYDataset xyDataset = new XYSeriesCollection(series);

	// Create plot and show it
	final JFreeChart chart = ChartFactory.createScatterPlot(title, "x", "Membership", xyDataset, PlotOrientation.VERTICAL, false, true, false );
	chart.getXYPlot().getDomainAxis().setRange(-50.0,50.0);
	chart.getXYPlot().getRangeAxis().setRange(-20.0,100.0);

	Thread p = new Thread(new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				BufferedImage image = chart.createBufferedImage(600, 400);
				ImageIO.write(image, "png", new File(title+".png"));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	});

	p.start();
}

public static void drawEdge(XYSeries series,final String title){					
	XYDataset xyDataset = new XYSeriesCollection(series);

	// Create plot and show it
	final JFreeChart chart = ChartFactory.createScatterPlot(title, "x", "Membership", xyDataset, PlotOrientation.VERTICAL, false, true, false );		
	chart.getXYPlot().getDomainAxis().setRange(-60.0,60.0);
	chart.getXYPlot().getRangeAxis().setRange(-10.0,110.0);
	//		chart.getXYPlot().getDomainAxis().setRange(-5.0,5.0);
	//		chart.getXYPlot().getRangeAxis().setRange(-5.0,5.0);


	Thread p = new Thread(new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				BufferedImage image = chart.createBufferedImage(500, 500);
				ImageIO.write(image, "png", new File(title+".png"));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	});

	p.start();


}



/* compute the radius given three points */
double radius(double x1, double y1, double x2, double y2, double x3, double y3)
{
	double dx1 = x2 - x1;
	double dy1 = y2 - y1;
	double dx2 = x3 - x2;
	double dy2 = y3 - y2;

	//double z = (dy1*dx2 - dx1*dy2);
	double z = dx1*dy2 - dy1*dx2;
	double sign = (z<0)?-1:1;

	if (z != 0.0) {
		double k = (dx2*(x3-x1) - dy2*(y1-y3))/z;
		return sign*Math.sqrt((1.0+k*k)*(dx1*dx1+dy1*dy1))/2.0;
	} else {
		return Double.MAX_VALUE;
	}
}


public Vector2D[] getEdges(){
	return allPoints.elements();
}

public ObjectArrayList<Vector2D> getLeftEdge(){
	return left;
}

public ObjectArrayList<Vector2D> getRightEdge(){
	return right;
}



public static double[] reverse(double[] ar){
	if (ar==null) return null;
	int len = ar.length/2;
	int l = ar.length-1;
	for (int i=0;i<len;++i){
		double tmp = ar[i];
		ar[i] = ar[l-i];
		ar[l-i] = tmp;
	}
	return ar;
}

public static DoubleArrayList reverse(DoubleArrayList ar){
	if (ar==null) return null;
	DoubleArrayList rs = new DoubleArrayList();
	int len = ar.size();
	int l = len-1;
	for (int i=0;i<len;++i){
		rs.add(ar.getDouble(l-i));
	}

	return rs;
}


}
