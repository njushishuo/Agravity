package cn.edu.nju.zhiziTools;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.*;



public class Collision{
  public double x;
  public double y;
  public double size;
  public int m;
  public double vx;
  public double vy;
  Ellipse2D.Double ellipse = new Ellipse2D.Double();
  Point p = new Point();
  public double s;
  double shadow_size;
  Ellipse2D.Double e = new Ellipse2D.Double();
	
	
 public Collision(double x,double y , double size , int m,double vx,double vy){
	 this.x = x ;
	 this.y = y ;
	 this.size = size ;
	 this.m =m;
	 this.vx = vx;
	 this.vy = vy;
	 ellipse.setFrameFromCenter(x, y, x+size, y+size);
	 this.p.setLocation(x, y);
	 this.s = Math.sqrt(Math.pow(vx, 2)+Math.pow(vy, 2));
 }
	
 


  public Ellipse2D.Double getEllipse(){
	  ellipse.setFrameFromCenter(x, y, x+size, y+size);
	  return ellipse ;
  }
  
  public Ellipse2D.Double getShadow(int index){

	  e.setFrameFromCenter(x, y, x+size+6*Math.abs(Math.sin(index*Math.PI/90)), y+size+6*Math.abs(Math.sin(index*Math.PI/90)));
      return e;
  }
  
  

  public Point getP(){
	  this.p.setLocation(x, y);
	  return p ;
  }


	
}
