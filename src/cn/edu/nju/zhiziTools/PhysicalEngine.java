package cn.edu.nju.zhiziTools;



import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;
import java.util.Scanner;

import GameMusic.MusicThread;
import cn.edu.nju.publicTools.DrawParticle;

public class PhysicalEngine {
    
	
	
	public void pe1(Spaceship ship ,DrawParticle d){
        double t = Math.abs(ship.speed_y/ship.speed_x);
		ship.x1 =ship.x1+ ship.speed_x;
		ship.y1 =ship.y1+ ship.speed_y; 
		
		
		if(ship.speed_x>0)
		ship.speed_x  -= 0.01;
		if(ship.speed_x<0)
			ship.speed_x  += 0.01;
		if(ship.speed_y>0)
		ship.speed_y  -= 0.01;
		if(ship.speed_y<0)
			ship.speed_y += 0.01;
		
		
		if(Math.abs(ship.speed_x)<0.1)
			ship.speed_x =0;
		if(Math.abs(ship.speed_y)<0.1)
		    ship.speed_y =0;
		
		
		if(ship.x1-ship.size<=0){
			ship.x1=ship.size;
			ship.speed_x=-5*ship.speed_x/6;
            d.add(new Point2D.Double(ship.x1,ship.y1));
            
        	MusicThread musicthread = new MusicThread ();
		    musicthread.creatMT("rebound00", 1);
		    musicthread.start();
		}
		if(ship.x1+ship.size>=1280){
                ship.x1=1280-ship.size;
				ship.speed_x=-5*ship.speed_x/6;
				d.add(new Point2D.Double(ship.x1,ship.y1));
				
				MusicThread musicthread = new MusicThread ();
			     musicthread.creatMT("rebound00", 1);
			     musicthread.start();
			}
	    if(ship.y1<ship.size){
	    	ship.y1=ship.size;
	    	ship.speed_y=-5*ship.speed_y/6;
	    	d.add(new Point2D.Double(ship.x1,ship.y1));
	    	
	    	MusicThread musicthread = new MusicThread ();
		     musicthread.creatMT("rebound00", 1);
		     musicthread.start();
	    }
		
	    if(ship.y1+ship.size>=720){
            ship.y1 = 720 - ship.size;
	    	ship.speed_y=-5*ship.speed_y/6;
	    	d.add(new Point2D.Double(ship.x1,ship.y1));
	    	
	    	MusicThread musicthread = new MusicThread ();
		     musicthread.creatMT("rebound00", 1);
		     musicthread.start();
	    }
	}
	
	
	public void pe1(Collision c,DrawParticle d){

		c.x =c.x+ c.vx;
		c.y =c.y +c.vy;

		if(c.vx>0)
			c.vx  -= 0.01;
			if(c.vx<0)
				c.vx += 0.01;
			if(c.vy>0)
			c.vy  -= 0.01;
			if(c.vy<0)
				c.vy += 0.01;
		if(Math.abs(c.vx)<0.1)
			c.vx =0;
		if(Math.abs(c.vy)<0.1)
		    c.vy =0;
	
		if(c.x<c.size){
			c.x=c.size+5;
		c.vx=-5*c.vx/6;
        d.add(new Point2D.Double(0, c.y));
		}
		if(c.x+c.size>1280){
               c.x=1280-c.size;
				c.vx=-5*c.vx/6;
				d.add(new Point2D.Double(1280, c.y));
			}
	    if(c.y<c.size){
	    	c.y=c.size+5;
	    	c.vy=-5*c.vy/6;
	    	d.add(new Point2D.Double(c.x,0));
	    }
		
	    if(c.y+c.size>720){
	    	c.y=720-c.size;
	    	c.vy=-5*c.vy/6;
	    	d.add(new Point2D.Double(c.x, 720));
	    }
	}
	
	
	
	
	
	
	
	
	
	public void pe2(Ellipse2D.Double g1,Rectangle2D r,double speed_x , double speed_y,int gravitation_x,int gravitation_y,Spaceship spaceship){
		  if(g1.intersects(r)){
	        	double ax = ( gravitation_x-spaceship.x1)*0.02;
	        	double ay = ( gravitation_y-spaceship.y1)*0.02;
	        	spaceship.speed_x +=ax;
	        	spaceship.speed_y +=ay;	   	
	        }
	}
	public void pe2(Ellipse2D.Double g1,Rectangle2D r,double speed_x , double speed_y,int gravitation_x,int gravitation_y,Collision c){
		  if(g1.intersects(r)){
	        	double ax = ( gravitation_x-c.x)*0.0005;
	        	double ay = ( gravitation_y-c.y)*0.0005;
	        	c.vx +=ax;
	        	c.vy +=ay;
	   	
	        }
	}
	
	
	
	public void pe3(Rectangle2D.Double obstacle1,Rectangle2D g1,Spaceship spaceship){
		   if(obstacle1.intersects(g1)){
	           if(!(spaceship.x1<450&&spaceship.x1>400))
	               spaceship.speed_x = -spaceship.speed_x;
	           if(spaceship.x1<450&&spaceship.x1>400)
	                spaceship.speed_y = -spaceship.speed_y;
	        }
	}
	
	
	
	public void pe3(Rectangle2D.Double obstacle1,Rectangle2D g1,Collision c,Obstacle o,DrawParticle d){
		   if(c.vx>0){
			   if((c.x+c.vx+c.size>=o.x)&&(c.x+c.size+c.vx<=o.x+o.w)&&c.y>=o.y&&c.y<=o.y+o.h){
				   c.x=o.x-c.size;
				   c.vx = -c.vx;
			   }				   
		   }
	        
		  if(c.vx<0){
			  if((c.x+c.vx-c.size<o.x+o.w)&&(c.x+c.vx-c.size>=o.x)&&c.y>=o.y&&c.y<=o.y+o.h){
			       c.x = o.x+o.w+c.size;
				   c.vx = -c.vx;
				   d.add(new Point2D.Double(c.x-c.size,c.y));
		   }
		   }
		  
		  if(c.vy>0){
			  if(c.y+c.size+c.vy>=o.y&&c.y<=o.y+o.h&&c.x>=o.x&&c.x<=o.x+o.w){
				  c.y = o.y - c.size ;
				  c.vy = - c.vy;
				  d.add(new Point2D.Double(c.x, c.y+c.size));
			  }
		  }
		  
		  if(c.vy<0){
			  if(c.y-c.size+c.vy<=o.y+o.h&&c.y>o.y&&c.x>=o.x&&c.x<=o.x+o.w){
				  c.y = o.y+o.h+c.size;
				  c.vy = - c.vy;
				  d.add(new Point2D.Double(c.x, c.y-c.size));
			  }
		  }
		  
		  
		  
	}
	
	public void pe3(Rectangle2D.Double obstacle1,Rectangle2D g1,Spaceship spaceship,Obstacle o,DrawParticle d){
		   if(spaceship.speed_x>0){
			   if((spaceship.x1+spaceship.speed_x+spaceship.size>=o.x)&&(spaceship.x1<=o.x+o.w)&&spaceship.y1>=o.y&&spaceship.y1<=o.y+o.h){
				   spaceship.x1=o.x-spaceship.size;
				   spaceship.speed_x = -spaceship.speed_x;
				   d.add(new Point2D.Double(spaceship.x1,spaceship.y1));
				   MusicThread musicthread = new MusicThread ();
				     musicthread.creatMT("rebound00", 1);
				     musicthread.start();
			   }				   
		   }
	        
		  if(spaceship.speed_x<0){
			  if((spaceship.x1+spaceship.speed_x-spaceship.size<o.x+o.w)&&(spaceship.x1>=o.x)&&spaceship.y1>=o.y&&spaceship.y1<=o.y+o.h){
			       spaceship.x1 = o.x+o.w+spaceship.size;
				   spaceship.speed_x = -spaceship.speed_x;
				   d.add(new Point2D.Double(spaceship.x1,spaceship.y1));
				   MusicThread musicthread = new MusicThread ();
				     musicthread.creatMT("rebound00", 1);
				     musicthread.start();
		   }
		   }
		  
		  if(spaceship.speed_y>0){
			  if(spaceship.y1+spaceship.size+spaceship.speed_y>=o.y&&spaceship.y1<=o.y+o.h&&spaceship.x1>=o.x&&spaceship.x1+spaceship.size<=o.x+o.w){
				  spaceship.y1 = o.y - spaceship.size ;
				  spaceship.speed_y = - spaceship.speed_y;
				  d.add(new Point2D.Double(spaceship.x1,spaceship.y1));
				  MusicThread musicthread = new MusicThread ();
				     musicthread.creatMT("rebound00", 1);
				     musicthread.start();
			  }
		  }
		  
		  if(spaceship.speed_y<0){
			  if(spaceship.y1-spaceship.size+spaceship.speed_y<=o.y+o.h&&spaceship.y1>o.y&&spaceship.x1>=o.x&&spaceship.x1+spaceship.size<=o.x+o.w){
				  spaceship.y1 = o.y+o.h+spaceship.size;
				  spaceship.speed_y = - spaceship.speed_y;
				  d.add(new Point2D.Double(spaceship.x1,spaceship.y1));
				  MusicThread musicthread = new MusicThread ();
				     musicthread.creatMT("rebound00", 1);
				     musicthread.start();
			  }
		  }
		  
		  
		  
	}
	public void pe4(Shape s1,Rectangle2D g1,Spaceship spaceship,double tant){
	
	
		   if(s1.intersects(g1)){
	        	if(spaceship.speed_y>=0&&spaceship.speed_x<=0){
	                  spaceship.speed_x=spaceship.speed_x-0.2;
	                  spaceship.speed_y=spaceship.speed_y-0.2*tant ;
	                  
	        	}		        
	        }
	
	}
	
	
	
	public void pe5(Shape shape,Rectangle2D g1,Spaceship spaceship,double tant){
        if(shape.intersects(g1)){
        	if(spaceship.speed_x>0)
        		spaceship.speed_x-=0.2;


        	if(spaceship.speed_x<=0)
        		spaceship.speed_x+=0.2;

        	if(spaceship.speed_y>=0)
        	    spaceship.speed_y-=Math.abs(0.2*tant);
        	
        	if(spaceship.speed_y<0)
        		spaceship.speed_y+=Math.abs(0.2*tant);
     
        }
		
	}
	
	
	
	public void pe6(Collision c){
		c.x += c.vx ;
		c.y += c.vy ;
	}
	
	public void pe6(ElectricField ef,Collision c,double i){
		if(c.getEllipse().intersects(ef.getElec())){
		c.vy=c.vy+i*0.25;
		}
	}
	
}

