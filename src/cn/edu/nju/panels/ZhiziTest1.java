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
import javax.swing.JFrame;

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
import cn.edu.nju.zhiziTools.Gravitation;
import cn.edu.nju.zhiziTools.Obstacle;
import cn.edu.nju.zhiziTools.PhysicalEngine;
import cn.edu.nju.zhiziTools.Spaceship;

public class ZhiziTest1 extends BackgroundPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
GameColor gc = new GameColor();
   int p=0;
   int i=0;
   int j=0;
   int u=0;
   Electron electron;
   Caculate caculate;
   Obstacle obstacle;
   Obstacle obstacle2;
   public boolean isPressed=false;
   public boolean isOk=false;
   public double speed_x;
   public double speed_y;
   DrawParticle dp =new DrawParticle();
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
   Spaceship spaceship =new Spaceship(initial_x+200,initial_y+200,speed_x,speed_y,5,5); 
  
   int gravitation_x =1200;
   int gravitation_y =300;
   int gravitation_size =150;
   Gravitation gravitation ;
   int radius = 80;
   
   int index = 0;
   
   double vax=0;
   double vay=0;
   double vbx=0;
   double vby=0;
   double vcx=0;
   double vcy=0;
   double vdx=0;
   double vdy=0;
   double vex=0;
   double vey=0;
   double ax = 300;
   double ay = 300;   
   double bx = 1000;
   double by=500;   
   double cx = 750;
   double cy = 150;   
   double dx = 550;
   double dy =650;
  
   boolean i1 =false;
   boolean i2 =false;
   boolean i3 =false;
   boolean i4 =false;
   boolean isFinished=false;
   boolean[] isList = new boolean[6] ;	
   PhysicalEngine pe = new PhysicalEngine();
   JFrame frame = new JFrame();
   
   Collision collision1 = new Collision(ax,ay,40,35,vax,vay);
   Collision collision2 = new Collision(bx,by,35,20,vbx,vby);
   Collision collision3 = new Collision(cx,cy,45,13,vcx,vcy);
   Collision collision4 = new Collision(dx,dy,35,18,vdx,vdy);

   
   Image starImage = new ImageIcon("images/star.png").getImage();
   ArrayList<Collision> arrayList = new ArrayList<Collision>();
   private Vector shapes = new Vector();//存储速度初始时的六个球
   Image backgroundImage = new ImageIcon("images/zhizibg5.png").getImage();
   ArrayList <Image>s1=new ArrayList<Image>();
  
   RestartWhite restart;
   NextWhite next;
   ExitWhite exit;
   
   
    public ZhiziTest1(){
    	    super();
    	    ZhiziTestLis Zzlis = new ZhiziTestLis();
			this.addMouseListener(Zzlis);
	    	this.addMouseMotionListener(Zzlis);
			setPreferredSize(new Dimension(1080,720));
	         arrayList.add(collision1);
	         arrayList.add(collision2);
	         arrayList.add(collision3);
	         arrayList.add(collision4);
	         isList[0] = i1;
	         isList[1] = i2;
	         isList[2] = i3;
	         isList[3] = i4;
	         
	         for(int i=1;i<=38;i++){
	        	 Image temp = new ImageIcon("images/"+i+".png").getImage();
	        	 s1.add(temp);
	         }
 
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
    	  p=0;
    	  j=0;
    	  u=0;
    	 collision1 = new Collision(ax,ay,40,35,vax,vay);
    	 collision2 = new Collision(bx,by,35,20,vbx,vby);
    	 collision3 = new Collision(cx,cy,45,13,vcx,vcy);
    	 collision4 = new Collision(dx,dy,35,18,vdx,vdy);
         arrayList = new ArrayList<Collision>();
         arrayList.add(collision1);
         arrayList.add(collision2);
         arrayList.add(collision3);
         arrayList.add(collision4);
         isPressed=false;
         isOk=false;
         isFinished=false;
         spaceship =new Spaceship(200,200,speed_x,speed_y,5,5);        
       for(int i=0;i<arrayList.size();i++)
    	isList[i]=false;
         removeAll();  
         dp =new DrawParticle();
         restart = new RestartWhite();
	 		next = new NextWhite();
	 		exit = new ExitWhite();
 		this.add(restart);	
 		this.add(next);
 		this.add(exit);
    }
    
    public void paintIBuffer(){
    	nextFrame();
    	AlphaComposite ac= AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
    	AlphaComposite ac1= AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
    	
    	
    	 for(int i =0;i<6;i++){
    			Ellipse2D.Float ellipse = new Ellipse2D.Float();
    			ellipse.setFrame(0, 0, 10, 10);
    			shapes.add(ellipse);
    		    }
    	
    	
 	         gBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	         gBuffer.setColor(gc.darkColors[0]);
	         gBuffer.fillRect(0, 0, iBuffer.getWidth(), iBuffer.getHeight());	
//          gBuffer.drawImage(backgroundImage, 0, 0, 1280, 720, null);
	      
            if(isList[0]==true&&isList[1]==true&&isList[2]==true&&isList[3]==true)
	    	  isFinished=true;
	
            
            if(!isFinished||p!=37){
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
         Ellipse2D.Double e1_s=collision1.getShadow(index);
         Ellipse2D.Double e2 = collision2.getEllipse();
         Ellipse2D.Double e2_s=collision2.getShadow(index);
         Ellipse2D.Double e3 = collision3.getEllipse();
         Ellipse2D.Double e3_s=collision3.getShadow(index);
         Ellipse2D.Double e4 = collision4.getEllipse();
         Ellipse2D.Double e4_s=collision4.getShadow(index);

         Ellipse2D.Double oval = spaceship.getSpaceship();
         
         electron = new Electron(spaceship, 25,15, i,-Math.PI/3);
	     Ellipse2D.Double elec = electron.getE();		    
         electron = new Electron(spaceship,25,15,j,Math.PI/3);
	     Ellipse2D.Double elec2 = electron.getE();
 	     electron = new Electron(spaceship,25,15,u,Math.PI);
         Ellipse2D.Double elec3 = electron.getE();
         gravitation = new Gravitation(gravitation_x,gravitation_y,gravitation_size);
         Ellipse2D.Double g1 =gravitation.getEllipse();
         gravitation = new Gravitation(gravitation_x,gravitation_y,(int) radius);
         Ellipse2D.Double circles =gravitation.getEllipse();
 	     

 	        p2.setLocation(spaceship.x1, spaceship.y1);
 	    
 	        processCollision();
 	        
 	        obstacle = new Obstacle(485, 0, 85, 250);
		    Rectangle2D.Double r1 = obstacle.getObstacle();
		    Rectangle2D.Double r1_s =obstacle.getShadow(index);

		    obstacle2 = new Obstacle(685, 470, 85, 250);
		    Rectangle2D.Double r2 = obstacle2.getObstacle();
		    Rectangle2D.Double r2_s =obstacle2.getShadow(index);
		    
		    
		    
		    gBuffer.setColor(GameColor.lightColors[5]);
	        gBuffer.draw(elec);
	        gBuffer.fill(elec);
		    gBuffer.setColor(GameColor.lightColors[4]);
		    gBuffer.draw(elec2);
		    gBuffer.fill(elec2);
		    gBuffer.setColor(gc.darkColors[4]);
		    gBuffer.draw(elec3);
		    gBuffer.fill(elec3);
		    
		    
 	        gBuffer.setColor(gc.lightColors[0]);
 	        gBuffer.draw(g1);
 	        gBuffer.draw(circles);
 	        
 	        
 	        
	 	       if(isList[0])
 				gBuffer.setColor(gc.lightColors[0]);
 		        else 
 			    gBuffer.setColor(gc.lightColors[1]);	
 	       

 	              gBuffer.draw(e1);
 	              gBuffer.fill(e1);
 	     
 	              gBuffer.setComposite(ac);
	              gBuffer.fill(e1_s);
 	              gBuffer.setComposite(ac1);
 	               
 	       if(isList[1])
 				gBuffer.setColor(gc.lightColors[0]);
 		        else 
 			    gBuffer.setColor(gc.lightColors[1]);	
              gBuffer.draw(e2);
 	               gBuffer.fill(e2);
 	               gBuffer.setComposite(ac);
 	               gBuffer.fill(e2_s);
 	               gBuffer.setComposite(ac1);

 	    
 	               
 	       if(isList[2])
 				gBuffer.setColor(gc.lightColors[0]);
 		        else 
 			    gBuffer.setColor(gc.lightColors[1]);	
 	              gBuffer.draw(e3);
 	              gBuffer.fill(e3);
 	              gBuffer.setComposite(ac);
                  gBuffer.fill(e3_s);
                  gBuffer.setComposite(ac1);
 	        
 	       if(isList[3])
 				gBuffer.setColor(gc.lightColors[0]);
 		        else 
 			    gBuffer.setColor(gc.lightColors[1]);	
 	               gBuffer.draw(e4);
	               gBuffer.fill(e4);
	               gBuffer.setComposite(ac);
                   gBuffer.fill(e4_s);
                   gBuffer.setComposite(ac1);
                   
                   
	        
 //	        for(int index=0;index<4;index++)
 //	        	 gBuffer.drawImage(starImage, (int)(arrayList.get(index).x-2*arrayList.get(index).size), (int)(arrayList.get(index).y-2*arrayList.get(index).size), 4*(int)arrayList.get(index).size, 4*(int)arrayList.get(index).size, null);
 	        
 	        
 	        gBuffer.setColor(gc.lightColors[2]);
 	        gBuffer.draw(oval);
 	        gBuffer.fill(oval);
 	        gBuffer.setColor(gc.lightColors[2]);
 	        gBuffer.fill(r1);
 	        gBuffer.fill(r2);
 	        

 	        gBuffer.setComposite(ac);
 	        gBuffer.setColor(gc.lightColors[2]);
 	        gBuffer.fill(r1_s);
 	        gBuffer.fill(r2_s);
 	        gBuffer.setComposite(ac1);
 	        
   
 	        
 	        
 	       pe.pe2(g1, oval.getBounds2D(), spaceship.speed_x, spaceship.speed_y, gravitation_x, gravitation_y,spaceship);
 	       for(int i=0;i<arrayList.size();i++){
 	    	   pe.pe2(g1, arrayList.get(i).getEllipse().getBounds2D(), arrayList.get(i).vx, arrayList.get(i).vy, (int) gravitation_x,(int)gravitation_y, arrayList.get(i)); 
 	       }
 	       
		    pe.pe3(r1, oval.getBounds2D(), spaceship, obstacle,dp);
		    pe.pe3(r2, oval.getBounds2D(), spaceship, obstacle2,dp);
		    for(int i=0;i<arrayList.size();i++){
		    	pe.pe3(r1,e1.getBounds2D(),arrayList.get(i),obstacle,dp);
		    	pe.pe3(r2,e1.getBounds2D(),arrayList.get(i),obstacle2,dp);
		    }
 	       
 	       
 		      dp.draw(iBuffer);
 
            }
            
            
             if(isFinished){
         	
           if(p<37)
	       p++;
           
           if(p==37){
        	   needChange=true;
        	   changeTo = "zhiziTest2";
           }
           
            }
    }
 
    
  
		// TODO Auto-generated method stub
    	
		
		
	

    
    

	  private void computeLoc(Vector theShapes){
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
    	index+=5;
		radius -=3;
	      if(radius<=0)
		   radius=80;
	      
         i+=6;
         j+=6;
         u+=6;
	      gravitation_x-=4;
	      gravitation_y+=2;

	      
	      
	      
	      if(gravitation_x-gravitation_size<=0)
	         gravitation_x = 1280-gravitation_size;
	      if(gravitation_y+gravitation_size>=720)
		         gravitation_y = gravitation_size;

	      
		if(isOk){						
	 	    pe.pe1(spaceship,dp);
	 	    pe.pe1(collision1,dp);
	        pe.pe1(collision2,dp);
	        pe.pe1(collision3,dp);
	        pe.pe1(collision4,dp);

		}
		else{
		x1=initial_x;
		y1=initial_y;
		}
		 spaceship.x1 = spaceship.x1+0.2*Math.sin(index*Math.PI/150);
	     spaceship.y1 =spaceship.y1-0.6*Math.sin(index*Math.PI/150);
	     collision1.y = collision1.y+0.8*Math.sin(index*Math.PI/30);
	     collision1.x = collision1.x-0.2*Math.sin(index*Math.PI/30);
	     collision2.y = collision2.y-0.6*Math.sin(index*Math.PI/150);
	     collision2.x = collision2.x+0.2*Math.sin(index*Math.PI/150);
	     collision3.y = collision3.y+0.5*Math.sin(index*Math.PI/190);
	     collision3.x = collision3.x-0.2*Math.sin(index*Math.PI/190);
         pd.setLocation(spaceship.x1, spaceship.y1);
      
     
         

   	        

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
			changeTo = "zhiziTest2";
		}
		
		if(exit.isMouseClicked()){
			needChange = true;
			changeTo = "startPanel";
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
	        	dp.add(new Point2D.Double(spaceship.x1, spaceship.y1));
	        	
	        	//music
	        	MusicThread musicthread = new MusicThread ();
			    musicthread.creatMT("drop01", 1);
			    musicthread.start();
			     
	        	isList[index] = true ;
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
	        			isList[i] = true ;
	        			isList[j] = true ;
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
		    
		    
		   speed_x=(final_x - initial_x) *0.03;		    
		   speed_y=(final_y - initial_y) *0.03;
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
