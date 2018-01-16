package cn.edu.nju.zhiziTools;


import java.awt.*;
import java.awt.geom.Rectangle2D;


public class Obstacle {
   Rectangle2D.Double r = new Rectangle2D.Double();
   public double x;
   public double y;
   public double w;
   public double h;
	
	public Obstacle(double x,double y, double w , double h){
		r.setFrame(x, y,w,h);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h =h;
	}
	
	
	
	public Rectangle2D.Double getObstacle(){
		r.setFrame(x, y,w,h);
		return r;
	}
	
	public  Rectangle2D.Double getShadow(int index){
		Rectangle2D.Double r2 = new Rectangle2D.Double();
		r2.setFrame(x-6*Math.abs(Math.sin(index*Math.PI/90)), y-6*Math.abs(Math.sin(index*Math.PI/90)), w+2*6*Math.abs(Math.sin(index*Math.PI/90)), h+2*6*Math.abs(Math.sin(index*Math.PI/90)));
	
	return r2;
	}
}
