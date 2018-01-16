package cn.edu.nju.zhiziTools;

import java.awt.geom.Ellipse2D;

public class Electron {
	
	
     public double base_x;
     public double base_y;
     public double x;
     public double y;
     public double next_x;
     public double next_y;
     public double a;
     public double b;
     public int i;
     public double theta ;
     public Ellipse2D.Double e = new Ellipse2D.Double();
     
	  public Electron(Spaceship s,double a,double b,int i,double theta){
		  this.base_x = s.x1 ;
		  this.base_y = s.y1 ;
		  this.a = a ;
		  this.b = b;
		  this.i = i;
		  this.theta = theta;
		  
		  x = base_x-a*Math.cos(theta)*Math.cos(i*Math.PI/90)+b*Math.sin(i*Math.PI/90)*Math.sin(theta); 
		  next_x =base_x-a*Math.cos(theta)*Math.cos((i+1)*Math.PI/90)+b*Math.sin((i+1)*Math.PI/90)*Math.sin(theta);
		  y=base_y-a*Math.sin(theta)*Math.cos(i*Math.PI/90)-b*Math.sin(i*Math.PI/90)*Math.cos(theta);
		  next_y =base_y-a*Math.sin(theta)*Math.cos((i+1)*Math.PI/90)-b*Math.sin((i+1)*Math.PI/90)*Math.cos(theta);
		  e.setFrameFromCenter(x, y, x+2, y+2);
	  }
	 
	  
	  public Electron(Spaceship s,double a,int i){
		  
	  }
	  
	  
	  
	  public Ellipse2D.Double getE(){
		  e.setFrameFromCenter(x, y, x+2, y+2);
		  return e ;
	  }
}
