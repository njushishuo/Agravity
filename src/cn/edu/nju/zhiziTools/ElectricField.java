package cn.edu.nju.zhiziTools;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class ElectricField {
   double x;
   double y;
   double w;
   double h;
   int index;
   Rectangle2D.Double ef = new Rectangle2D.Double();
   
   
   public ElectricField(double x,double y,double w,double h,int index){
	   this.x = x;
	   this.y = y;
	   this.w = w;
	   this.h = h;
	   this.index = index;
	   double rate = h/w;
	   ef.setFrame(x-6*Math.abs(Math.sin(index*Math.PI/40)), y-6*rate*Math.abs(Math.sin(index*Math.PI/40)), w+2*6*Math.abs(Math.sin(index*Math.PI/40)), h+2*rate*6*Math.abs(Math.sin(index*Math.PI/40)));
   }
   
   public Rectangle2D.Double getElec (){
	   return ef;
   }
}
