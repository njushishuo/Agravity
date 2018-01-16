package cn.edu.nju.zhiziTools;

import java.awt.*;
import java.awt.geom.Ellipse2D;



public class Gravitation {
   int gravitation_x;
   int gravitation_y;
   int gravitation_size;
   Ellipse2D.Double e = new Ellipse2D.Double();
   
   
   
      public Gravitation(int x,int y,int s){
    	  this.gravitation_x = x;
    	  this.gravitation_y = y;
    	  this.gravitation_size = s;
    	  e.setFrameFromCenter(gravitation_x, gravitation_y, gravitation_x+gravitation_size,gravitation_y+gravitation_size );
      }
      
      
      
      public Ellipse2D.Double getEllipse(){
    	  return e ;
      }
}
