package cn.edu.nju.panels;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import javax.swing.JFrame;

import cn.edu.nju.publicTools.DrawParticle;
import cn.edu.nju.zhiziTools.Caculate;
import cn.edu.nju.zhiziTools.Collision;
import cn.edu.nju.zhiziTools.Gravitation;
import cn.edu.nju.zhiziTools.Obstacle;
import cn.edu.nju.zhiziTools.PhysicalEngine;
import cn.edu.nju.zhiziTools.Spaceship;
import cn.edu.nju.zhiziTools.SpeedUp;


public class Window extends BackgroundPanel {
	
	DrawParticle dp=new DrawParticle();
	//初始化引力场
    int gravitation_x=200;
    int gravitation_y=200;
    int gravitation_size=100;
    
    int gravitation2_x =500;
    int gravitation2_y =500;
    int gravitation2_size =50;
    
     //初始化斜碰速度与斜碰体的位置
    double vax=1.4;
    double vay=-1;
    double vbx=-1;
    double vby=-2;
    double ax=300;
    double ay=400;
    double bx=700;
    double by=500;
    Point p1; //斜碰体中心点
    Point p2;
 
    //多边形加速场的初始位置
    int polygon_x=600;
    int polygon_y=150;
    
   
   //记录飞船的坐标与速度
	double x1;
	double y1;
	double speed_x ;
	double speed_y;
	double tant;
	
	//the mouse initial and final position
	double initial_x;
    double initial_y;
    double final_x;
    double final_y;
  
    private Vector shapes = new Vector();//存储速度初始时的六个球
    boolean isPressed = false;
    boolean isOk      = false;
    double radius =80.0;
    
 //to create many instances
   PhysicalEngine pe = new PhysicalEngine();
   Caculate caculate ;
   Collision collision ;
   Gravitation gravitation ;
   SpeedUp speedup;
   Obstacle obstacle;
   Spaceship spaceship =new Spaceship(x1+350,y1+200,speed_x,speed_y,5,10);
   
   JFrame frame = new JFrame();

   
   
	  public Window(){
	    	 super();
	    	    WindowLis winlis = new WindowLis();
				this.addMouseListener(winlis);
		    	this.addMouseMotionListener(winlis);
				setPreferredSize(new Dimension(1080,720));
	     }
		
		
	  
	  
	  
		public void paintIBuffer(){
		nextFrame();	
			
			
	    for(int i =0;i<6;i++){
		Ellipse2D.Float ellipse = new Ellipse2D.Float();
		ellipse.setFrame(0, 0, 10, 10);
		shapes.add(ellipse);
	    }
		
		 gBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		 gBuffer.setColor(Color.white);
		 gBuffer.fillRect(0, 0, iBuffer.getWidth(), iBuffer.getHeight());	
		 
		 
		 
		// create speed up
        speedup = new SpeedUp(polygon_x,polygon_y);
		Polygon s = speedup.getPolygon();		
        AffineTransform tr = new AffineTransform();
        tr.rotate(Math.PI/8, polygon_x+50,polygon_y+50 );
        Shape s1 =tr.createTransformedShape(s);
        
	        
		 //create speed down
	     Rectangle2D.Double rectangle = new Rectangle2D.Double();
		 rectangle.setFrameFromCenter(400, 400, 425, 300);
		 Shape shape =rectangle;
		 
	 
		 //draw the six dots
		 if(isPressed){
			 
		 computeLoc(shapes);
	 
		for(int j =0;j<6;j++){
			Ellipse2D.Float ellipse1 = (Ellipse2D.Float)shapes.get(j);
			gBuffer.setColor(Color.black);					
			gBuffer.draw(ellipse1);
			gBuffer.fill(ellipse1);
			}	
		}
		
		
		
        // draw the spaceship
		Ellipse2D.Double oval = spaceship.getSpaceship();
		Shape ovals =oval;				
	

        // create the gravitation circle
        gravitation = new Gravitation(gravitation_x,gravitation_y,gravitation_size);
        Ellipse2D.Double g1 =gravitation.getEllipse();
        gravitation = new Gravitation(gravitation2_x,gravitation2_y,gravitation2_size);
        Ellipse2D.Double g2 =gravitation.getEllipse();
        gravitation = new Gravitation(gravitation_x,gravitation_y,(int) radius);
        Ellipse2D.Double circles =gravitation.getEllipse();
        //create obstacle
        obstacle = new Obstacle(400,0,50,250);
        Rectangle2D.Double obstacle1 =obstacle.getObstacle();
        
       
        
        //create XIEPENG
        collision = new Collision(ax,ay,50,5,10,10);
        Ellipse2D.Double e1 = collision.getEllipse();
        collision = new Collision(bx,by,50,20,10,10);
        Ellipse2D.Double e2 = collision.getEllipse();
        
        
        
      
        gBuffer.setColor(Color.black);	
        
        //疯狂画图
    	gBuffer.draw(ovals);
        gBuffer.fill(ovals);
	    gBuffer.draw(obstacle1);
	    gBuffer.fill(obstacle1);
	    gBuffer.draw(g1);
        gBuffer.draw(g2);
        gBuffer.draw(circles);
        gBuffer.draw(e1);
        gBuffer.draw(e2);
        gBuffer.fill(e1);
        gBuffer.fill(e2);
        gBuffer.draw(rectangle);
        gBuffer.draw(s1);
        
        
        
        
        
       //use the physical engine	        
       pe.pe2(g1, ovals.getBounds2D(), spaceship.speed_x, spaceship.speed_y, gravitation_x, gravitation_y,spaceship);
       pe.pe2(g2, ovals.getBounds2D(), spaceship.speed_x, spaceship.speed_y, gravitation2_x, gravitation2_y, spaceship);
       pe.pe3(obstacle1, ovals.getBounds2D(), spaceship);        
       pe.pe4(s1, ovals.getBounds2D(), spaceship, tant);	     
       pe.pe5(shape, ovals.getBounds2D(), spaceship, tant);   


       
       
    //如果两个斜碰体碰到一起则带入斜碰计算
        Point p1 = new Point();
        p1.setLocation(ax, ay);
        Point p2 = new Point();
        p2.setLocation(bx, by);
        
        
        if(p1.distance(p2)<=100){
        	caculate = new Caculate(vax,vay,vbx,vby,20,20,ax,ay,bx,by);		        	
        	vax=caculate.getVax();
        	vay=caculate.getVay();
        	vbx=caculate.getVbx();
        	vby=caculate.getVby();  
        
        }
				}	
		
				public void paintComponent(Graphics g){
				
				paintIBuffer();
			    g.drawImage(iBuffer, 0, 0, null);
			  

			}

			
		
			


			
        private void computeLoc(Vector theShapes){
     	   double deltaX = p2.getX()-p1.getX();
     	   double deltaY = p2.getY()-p1.getY();
     	       for(int i =0;i<6;i++){
     		   double x = p1.getX()+(i/5.0)*deltaX;
     		   double y = p1.getY()+(i/5.0)*deltaY;
     		   Ellipse2D.Float ellipse = (Ellipse2D.Float)shapes.get(i);
     		   ellipse.setFrameFromCenter(x,y,x+5,y+5);
     	   }
        }

        
        
        private void nextFrame() {
			
			tant =spaceship.speed_y / spaceship.speed_x  ;
			
			
			//引力场内部的动态线
				radius -=3;
			      if(radius<=0)
				   radius=80;
			      
			      
			//斜碰小球运动
			ax+=vax;
			ay+=vay;
			bx+=vbx;
			by+=vby;
					

			
			//当松开鼠标时飞船开始起飞
			if(isOk){						
		 	     pe.pe1(spaceship,dp);
			}
			else{
			x1=initial_x;
			y1=initial_y;
			}

}
			
			
			
        
        
			class WindowLis extends PanelLis{
				
				
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
			
			   isPressed =true;
			   p2 = (Point) e.getPoint();
	
			}



			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}



			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				  
			}



			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			   
				p1 =e.getPoint();
				

				
				initial_x = e.getPoint().getX();
				initial_y = e.getPoint().getY();
				
			}



			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
				
				isPressed =false;
				isOk =true;
				try {
			    final_x = p2.getX();
			    final_y = p2.getY();
			    speed_x =(final_x - initial_x) *0.01;
			    speed_y =(final_y - initial_y) *0.01;
			    spaceship =new Spaceship(spaceship.x1,spaceship.y1,speed_x,speed_y,5,10);
				} catch (Exception e2) {
					// TODO: handle exception
				}
	
			}



			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}



			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			}
	

      

}
	





