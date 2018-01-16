package cn.edu.nju.waterDropTools;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class WaterDrop {
	int init_x[] = {-27,-2,-2};
	int init_y[] = {0,10,-10};
	
	public double x;
	public double y;
	public double vx;
	public double vy;
	
	double zoom = 1;
	double zoomSpeed = 0.03;
	
	private Shape circle ;
	private Shape triangle;
	
	AffineTransform asRotate = new AffineTransform();
	AffineTransform asMove = new AffineTransform();
	AffineTransform asZoom = new AffineTransform();
	
	public Area waterDrop = new Area();
	
	public WaterDrop(double x,double y,double vx, double vy){
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		
		AffineTransform asScale = new AffineTransform();
		asScale.scale(1.7, 1.7);
	
		Ellipse2D.Double circle = new Ellipse2D.Double();
		circle.setFrameFromCenter(0, 0, 10, 10);
		
		triangle =  asScale.createTransformedShape(new Polygon(init_x, init_y, 3));
		this.circle = asScale.createTransformedShape(circle);
		
		asMove.translate(x, y);
	}
	
	public Area getArea(){
		asRotate.setToIdentity();
		asRotate.rotate(Math.atan2(vy, vx));
		
		asMove.translate(vx, vy);
		x = x+vx;
		y = y+vy;
		
		Shape temShape = asMove.createTransformedShape(asRotate.createTransformedShape(circle));
		Shape temShape2 =asMove.createTransformedShape(asRotate.createTransformedShape(triangle));
		
		waterDrop = new Area(temShape);
	    waterDrop.add(new Area(temShape2));
		
	   
	    return waterDrop;
	}
	
	
	public Area getShadow(){
		
		 //这里控制水倒影的放大缩小
	    if((zoom>=1.4&&zoomSpeed>0)||(zoom<=1&&zoomSpeed<0)){
	    	zoomSpeed = -zoomSpeed;
	    }
	    zoom = zoom+zoomSpeed;	    
	    asZoom.setToIdentity();
	    asZoom.scale(zoom, zoom);
	   
	    
	    
	    //生成放大版水滴倒影
	   Shape temShape = asMove.createTransformedShape(asRotate.createTransformedShape(asZoom.createTransformedShape(circle)));
	   Shape temShape2 =asMove.createTransformedShape(asRotate.createTransformedShape(asZoom.createTransformedShape(triangle)));
		Area shadow = new Area(temShape);
		shadow.add(new Area(temShape2));
		
		return shadow;
	}
	
	public void setSpeed(double vx,double vy){
		this.vx = vx;
		this.vy = vy;
	}
	
}
