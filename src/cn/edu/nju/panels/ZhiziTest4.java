package cn.edu.nju.panels;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;

import GameMusic.MusicThread;
import cn.edu.nju.components.ExitButton;
import cn.edu.nju.components.ExitWhite;
import cn.edu.nju.components.Next;
import cn.edu.nju.components.NextWhite;
import cn.edu.nju.components.Restart;
import cn.edu.nju.components.RestartWhite;
import cn.edu.nju.publicTools.DrawParticle;
import cn.edu.nju.publicTools.GameColor;
import cn.edu.nju.zhiziTools.Caculate;
import cn.edu.nju.zhiziTools.Collision;
import cn.edu.nju.zhiziTools.Electron;
import cn.edu.nju.zhiziTools.Obstacle;
import cn.edu.nju.zhiziTools.PhysicalEngine;
import cn.edu.nju.zhiziTools.Spaceship;
import cn.edu.nju.zhiziTools.accelerateIcon;

public class ZhiziTest4 extends BackgroundPanel{
	   boolean isFinished=false;
	   int p =0;
	   double temp_x;
	   Electron electron;
	   accelerateIcon accelerateIcon1 = new accelerateIcon(1000, 200, 1.5, 1.5);
	   accelerateIcon accelerateIcon2 = new accelerateIcon(1000, 600, 1.5, 1.5);
       Obstacle o1;
	   GameColor gc = new GameColor();
	   PhysicalEngine pe = new PhysicalEngine();
	   DrawParticle dp = new DrawParticle();
	   Spaceship spaceship = new Spaceship(1000, 360, 0, 0, 5, 5);
	   Caculate caculate ;
	   public boolean isPressed=false;
	   public boolean isOk=false;
	   public double speed_x;
	   public double speed_y;	 
	   Point p2  = new Point();
	   Point pa = new Point();
	   Point pb = new Point();
	   Point pc = new Point();
	   Point pd = new Point();
	   double x1;
	   double y1;
	   double initial_x;
	   double initial_y;
	   double final_x;
	   double final_y;
	   int index=0;
	   int i=0;
	   int j=0;
	   int u=0;
	   
	   boolean i1 =false;
	   boolean i2 =false;
	   boolean i3 =false;
	   boolean i4 =false;
	   
	   double collision_vx=1.8;
	   double collision_ax=0.8;
	   
	   double collision_vx1=1.8;
	   double collision_ax1=0.8;
	   
	   double collision_vx2=1.8;
	   double collision_ax2=0.8;
	   
	   double collision_vx3=1.8;
	   double collision_ax3=0.8;
	   
	   Collision collision1 = new Collision(300, 100, 45, 20, 0, 0);
	   Collision collision2 = new Collision(700, 300,  45, 20, 0, 0);
	   Collision collision3 = new Collision(300, 475, 45, 20, 0, 0);
	   Collision collision4 = new Collision(700, 625, 45, 20, 0, 0);
	   ArrayList<Collision> arrayList = new ArrayList<Collision>();
	   private Vector shapes = new Vector();
	   boolean[] isList = new boolean[6] ;	   
	   Image backgroundImage = new ImageIcon("images/1.png").getImage();
	   
	   RestartWhite restart;
	   NextWhite next;
	   ExitWhite exit;
	   
	   
	   public ZhiziTest4(){
	   	   super();
	       setPreferredSize(new Dimension(1080,720));
	        ZhiziTestLis Zzlis = new ZhiziTestLis();
			this.addMouseListener(Zzlis);
	    	this.addMouseMotionListener(Zzlis);
	         isList[0] = i1;
	         isList[1] = i2;
	         isList[2] = i3;
	         isList[3] = i4;
	         
	         arrayList.add(collision1);
	         arrayList.add(collision2);
	         arrayList.add(collision3);
	         arrayList.add(collision4);

	         restart = new RestartWhite();
	         next = new NextWhite();
	         exit = new ExitWhite();
	 		this.add(restart);	
	 		this.add(next);
	 		this.add(exit);
	   }
	   
	   
	   public void init(){
		   super.init();
	    	  i=0;
	    	  j=0;
	    	  u=0;
	    	  p=0;
	    	 collision1 = new Collision(300, 100, 45, 20, 0, 0);
	   	     collision2 = new Collision(700, 275,  45, 20, 0, 0);
	         collision3 = new Collision(300, 450, 45, 20, 0, 0);
	   	     collision4 = new Collision(700, 625, 45, 20, 0, 0);
	         arrayList = new ArrayList<Collision>();
	         arrayList.add(collision1);
	         arrayList.add(collision2);
	         arrayList.add(collision3);
	         arrayList.add(collision4);
	         isPressed=false;
	         isOk=false;
	         isFinished=false;
	         spaceship =new Spaceship(1000,360,speed_x,speed_y,5,5); 
	         for(int i=0;i<arrayList.size();i++)
	        	 isList[i]=false;
	         dp =new DrawParticle();
	  
	         removeAll();
	         restart = new RestartWhite();
	         next = new NextWhite();
	         exit = new ExitWhite();
	 		this.add(restart);	
	 		this.add(next);
	 		this.add(exit);
	   }
	   
	   
	   
	   public void paintIBuffer(){
		   nextFrame();
		   if(isList[0]==true&&isList[1]==true&&isList[2]==true&&isList[3]==true)
		    	  isFinished=true;
		    AlphaComposite ac= AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
	        AlphaComposite ac1= AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
	   	    gBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	   	    gBuffer.setColor(gc.darkColors[0]);
		    gBuffer.fillRect(0, 0, iBuffer.getWidth(), iBuffer.getHeight());	
//	        gBuffer.drawImage(backgroundImage, 0, 0, 1280, 720, null);
		    
		    
		    if(!isFinished||p!=37){
	        electron = new Electron(spaceship, 25,15, i,-Math.PI/3);
		     Ellipse2D.Double elec = electron.getE();		    
            electron = new Electron(spaceship,25,15,j,Math.PI/3);
		     Ellipse2D.Double elec2 = electron.getE();
    	     electron = new Electron(spaceship,25,15,u,Math.PI);
            Ellipse2D.Double elec3 = electron.getE();
	        
	        o1 = new Obstacle(850, 150, 50,480 );
	        Rectangle2D.Double r1 = o1.getObstacle();
	        Rectangle2D.Double r1_s =o1.getShadow(index);
	        gBuffer.setColor(gc.darkColors[1]);
	        gBuffer.fill(r1);
	        gBuffer.setComposite(ac);
	        gBuffer.fill(r1_s);
	        gBuffer.setComposite(ac1);
	 		accelerateIcon1.drawAccelerateIcon(iBuffer);
	 		accelerateIcon2.drawAccelerateIcon(iBuffer);
            
            
            
		    for(int i =0;i<6;i++){
				Ellipse2D.Float ellipse = new Ellipse2D.Float();
				ellipse.setFrame(0, 0, 10, 10);
				shapes.add(ellipse);
			    }		    
		    if(isPressed){ 				 
	 	 		computeLoc(shapes); 	 	 
	 	 		for(int j =0;j<6;j++){
	 	 			Ellipse2D.Float ellipse1 = (Ellipse2D.Float)shapes.get(j);
	 	 			gBuffer.setColor(gc.darkColors[1]);					
	 	 			gBuffer.draw(ellipse1);
	 	 			gBuffer.fill(ellipse1);
	 	 			}	
	 	 		}
		    
		    
		    Ellipse2D.Double e1 = collision1.getEllipse();
		    Ellipse2D.Double e2 = collision2.getEllipse();
		    Ellipse2D.Double e3 = collision3.getEllipse();
		    Ellipse2D.Double e4 = collision4.getEllipse();
		    Ellipse2D.Double e1_s = collision1.getShadow(index);
		    Ellipse2D.Double e2_s = collision2.getShadow(index);
		    Ellipse2D.Double e3_s = collision3.getShadow(index);
		    Ellipse2D.Double e4_s = collision4.getShadow(index);
		    Ellipse2D.Double oval = spaceship.getSpaceship();
		    
		    gBuffer.setColor(gc.lightColors[2]);
		    gBuffer.draw(oval);
		    gBuffer.fill(oval);
		    
		    pe.pe3(r1, oval.getBounds2D(), spaceship, o1,dp);
		    for(int i=0;i<arrayList.size();i++)
		    	pe.pe3(r1, oval.getBounds2D(), arrayList.get(i), o1, dp);
		    
		    Rectangle2D.Double r2 = new Rectangle2D.Double();
		    r2.setFrame(1000, 0, 300, 800);
		    
		    if(spaceship.getSpaceship().intersects(r2))
		    	spaceship.speed_x-=1;
		    
		    
		    
		    if(isList[0])
			gBuffer.setColor(gc.lightColors[0]);
	        else 
		    gBuffer.setColor(gc.lightColors[1]);	
		    gBuffer.fill(e1); 	            
			gBuffer.setComposite(ac);
			gBuffer.fill(e1_s);			  		   
	  		gBuffer.setComposite(ac1);
	  		gBuffer.setColor(gc.lightColors[5]);
	        gBuffer.draw(elec);
	        gBuffer.fill(elec);
		    gBuffer.setColor(gc.lightColors[4]);
		    gBuffer.draw(elec2);
		    gBuffer.fill(elec2);
		    gBuffer.setColor(gc.darkColors[4]);
		    gBuffer.draw(elec3);
		    gBuffer.fill(elec3);
	  		if(isList[1])
	  			gBuffer.setColor(gc.lightColors[0]);
	  	        else 
	  		    gBuffer.setColor(gc.lightColors[1]);	
	  		gBuffer.fill(e2);
		    gBuffer.setComposite(ac);
			gBuffer.fill(e2_s);		
	 		gBuffer.setComposite(ac1);
		    
	 		if(isList[2])
	 			gBuffer.setColor(gc.lightColors[0]);
	 	        else 
	 		    gBuffer.setColor(gc.lightColors[1]);	
	 		gBuffer.fill(e3);
			gBuffer.setComposite(ac);
			gBuffer.fill(e3_s);		
	 		gBuffer.setComposite(ac1);
		    
	 		if(isList[3])
	 			gBuffer.setColor(gc.lightColors[0]);
	 	        else 
	 		    gBuffer.setColor(gc.lightColors[1]);	
	 		gBuffer.fill(e4);
			gBuffer.setComposite(ac);
			gBuffer.fill(e4_s);		
	 		gBuffer.setComposite(ac1);
	 		
	 		

	 		processCollision();
		    dp.draw(iBuffer);
		    p2.setLocation(spaceship.x1, spaceship.y1);
		    }
		    
		    
		   if(isFinished) {

		           if(p<37)
			       p++;
		           
		           if(p==37){
		        	   needChange=true;
		        	   changeTo = "startPanel";
		           }
			}
	   }
	   public void processCollision(){
	       for(int index=0;index<arrayList.size();index++){
	       	  if(arrayList.get(index).getP().distance(p2)<arrayList.get(index).size+spaceship.size){
	       		if(predict(arrayList.get(index)).distance(predict())<=arrayList.get(index).getP().distance(p2))  {	       		  
	         	caculate = new Caculate(spaceship.speed_x,spaceship.speed_y,arrayList.get(index).vx,arrayList.get(index).vy,spaceship.m,arrayList.get(index).m,spaceship.x1,spaceship.y1,arrayList.get(index).x,arrayList.get(index).y);		        	
	        	spaceship.speed_x=caculate.getVax();
	         	spaceship.speed_y=caculate.getVay();
	    	    arrayList.get(index).vx=caculate.getVbx();
	    	    arrayList.get(index).vy=caculate.getVby();  
	            isList[index] = true ;
	       	    dp.add(new Point2D.Double(spaceship.x1, spaceship.y1));
	       	    
	       	//music
	        	MusicThread musicthread = new MusicThread ();
			     musicthread.creatMT("drop01", 1);
			     musicthread.start();
	       }
	       }
	      
	       
	       }
	for(int i=0;i<arrayList.size();i++){
		for(int j=i+1;j<arrayList.size();j++){
	       		if(arrayList.get(i).getP().distance(arrayList.get(j).getP())<=arrayList.get(i).size+arrayList.get(j).size){
	       			if(predict(arrayList.get(i)).distance(predict(arrayList.get(j)))<=arrayList.get(i).getP().distance(arrayList.get(j).getP())){
	   			double t = Math.abs((arrayList.get(i).y-arrayList.get(j).y)/(arrayList.get(i).x-arrayList.get(j).x));
	       	        if(arrayList.get(i).x<=arrayList.get(j).x){
	       	        	  if(arrayList.get(i).y<=arrayList.get(j).y)
	       	            dp.add(new Point2D.Double(arrayList.get(i).x+arrayList.get(i).size*Math.cos(Math.atan(t)), arrayList.get(i).y+arrayList.get(i).size*Math.sin(Math.atan(t))));
	       	        	  else
	       	        		  dp.add(new Point2D.Double(arrayList.get(i).x+arrayList.get(i).size*Math.cos(Math.atan(t)), arrayList.get(i).y-arrayList.get(i).size*Math.sin(Math.atan(t))));
							
	       	        }
	       	        else{
	       	        	if(arrayList.get(i).y<=arrayList.get(j).y)
		        	            dp.add(new Point2D.Double(arrayList.get(i).x-arrayList.get(i).size*Math.cos(Math.atan(t)), arrayList.get(i).y+arrayList.get(i).size*Math.sin(Math.atan(t))));
	       	        	 else
	       	        		  dp.add(new Point2D.Double(arrayList.get(i).x-arrayList.get(i).size*Math.cos(Math.atan(t)), arrayList.get(i).y-arrayList.get(i).size*Math.sin(Math.atan(t))));
	       	        }
	       			caculate = new Caculate(arrayList.get(i).vx, arrayList.get(i).vy, arrayList.get(j).vx,arrayList.get(j).vy, arrayList.get(i).m, arrayList.get(j).m, arrayList.get(i).x, arrayList.get(i).y, arrayList.get(j).x, arrayList.get(j).y);
	       			arrayList.get(i).vx=caculate.getVax();
	       			arrayList.get(i).vy=caculate.getVay();
	       			arrayList.get(j).vx=caculate.getVbx();
	       			arrayList.get(j).vy=caculate.getVby();   
	       			isList[i]=true;
	    			isList[j]=true;
	       		}
		}
	}  
	}
	}

	public Point predict(){
		
	   double temp_x = spaceship.x1+spaceship.speed_x;
	   double temp_y = spaceship.y1+spaceship.speed_y;
	   Point temp_p = new Point() ;
	   temp_p.setLocation(temp_x, temp_y);
	   return temp_p;
	}

	public Point predict(Collision c){
		
		double temp_x = c.x+c.vx;
		double temp_y = c.y+c.vy;
				Point temp_p =new Point();
		temp_p.setLocation(temp_x, temp_y);
		return temp_p;
	}
		private void computeLoc(Vector shapes2) {
			// TODO Auto-generated method stub
			    double deltaX = pc.getX()-pd.getX();
		  	    double deltaY = pc.getY()-pd.getY();
		  	       for(int i =0;i<6;i++){
		  		   double x = pd.getX()+(i/5.0)*deltaX;
		  		   double y = pd.getY()+(i/5.0)*deltaY;
		  		   Ellipse2D.Float ellipse = (Ellipse2D.Float)shapes.get(i);
		  		   ellipse.setFrameFromCenter(x,y,x+5,y+5);
		  	   }
		}

		private void nextFrame() {
			// TODO Auto-generated method stub
			i+= 6;
			j+= 6;
			u+= 6;
			index+=4;
			pd.setLocation(spaceship.x1, spaceship.y1);
			if(isOk){	
			     pe.pe1(spaceship,dp);
				
			}
			else{
				 spaceship.y1 =spaceship.y1-Math.sin(index*Math.PI/150);
				
				 x1=initial_x;
				 y1=initial_y;
			}
			     pe.pe1(collision1,dp);
			     pe.pe1(collision2,dp);
			     pe.pe1(collision3,dp);
			     pe.pe1(collision4, dp);

			     collision1.y = collision1.y+1.8*Math.sin(index*Math.PI/150);  
			     collision2.y = collision2.y-1.8*Math.sin(index*Math.PI/150);
			     collision3.y = collision3.y-1.8*Math.sin(index*Math.PI/150);
			     collision4.y = collision4.y+1.8*Math.sin(index*Math.PI/150);

			     if(!isList[0])		{	        
			    	   if(collision1.x<=400&&collision1.x>=300)
					        collision_vx+=collision_ax;
					     if(collision1.x<=700&&collision1.x>=600)
						        collision_vx-=collision_ax;
			    	 
			        if(collision1.x<=700&&collision1.x>=300)
				     collision1.x +=collision_vx;
			        else
			        	collision1.x -=collision_vx;
			        }
			     if(!isList[1])		{	     
			    	 
			    	   if(collision2.x<=400&&collision2.x>=300)
					        collision_vx1-=collision_ax1;
					     if(collision2.x<=700&&collision2.x>=600)
						        collision_vx1+=collision_ax1;
					     
				        if(collision2.x<=700&&collision2.x>=300)
					     collision2.x -=collision_vx1;
				        else
				        	collision2.x +=collision_vx1;
				        }
			     
			     if(!isList[2])		{	        
			    	 
			    	   if(collision3.x<=400&&collision3.x>=300)
					        collision_vx2+=collision_ax2;
					     if(collision3.x<=700&&collision3.x>=600)
						        collision_vx2-=collision_ax2;
				        if(collision3.x<=700&&collision3.x>=300)
					     collision3.x +=collision_vx2;
				        else
				        	collision3.x -=collision_vx2;
				        }
			     if(!isList[3])		{	        
			    	 
			    	   if(collision4.x<=400&&collision4.x>=300)
					        collision_vx3-=collision_ax3;
					     if(collision4.x<=700&&collision4.x>=600)
						        collision_vx3+=collision_ax3;
				        if(collision4.x<=700&&collision4.x>=300)
					     collision4.x -=collision_vx3;
				        else
				        	collision4.x +=collision_vx3;
				        }
			     
				  
			}

	   
	   
	   
	   
	   
	   public void paintComponent(Graphics g){
	       coordinateTrans();
			paintIBuffer();
			g.drawImage(iBuffer,0,0,null);
			
			if(restart.isMouseClicked()){
				init();
			}
			
			if(next.isMouseClicked()){
			
				needChange = true;
				changeTo = "startPanel";
			}
			
			if(exit.isMouseClicked()){
				needChange = true;
				changeTo = "startPanel";
			}
		}
	   
	   
	   
	   class ZhiziTestLis extends PanelLis{
			public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub
				
				   isPressed =true;
				   
		           pc = e.getPoint();
		           pc = coordinateTrans(pc);
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
				   
					pa.setLocation(spaceship.x1, spaceship.y1);			
		     		initial_x = pa.getX();
					initial_y = pa.getY();
					
				}



				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub				
					pb = (Point) e.getPoint();
					pb = coordinateTrans(pb);
					pa.setLocation(spaceship.x1, spaceship.y1);			
		     		initial_x = pa.getX();
					initial_y = pa.getY();
					isPressed =false;
					isOk =true;
					try {			
				    final_x = pb.getX();
				    final_y = pb.getY();
				    
				    
				   speed_x=(final_x - initial_x) *0.06;		    
				   speed_y=(final_y - initial_y) *0.06;
				   
				   if(speed_x>=15)
					   speed_x =15;
				   if(speed_y>=15*720/1280)
				   speed_y = 15*720/1280;
			     if(speed_x<=-15)
					   speed_x=-15;
				  if(speed_y<=-15*720/1280)
				   speed_y=-15*720/1280;
				    spaceship =new Spaceship(spaceship.x1,spaceship.y1,speed_x,speed_y,5,5);
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
