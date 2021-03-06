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

public class ZhiziTest2 extends BackgroundPanel{
  	   GameColor gc;
	   PhysicalEngine pe = new PhysicalEngine();
	   Obstacle o1;
	   Obstacle o2;
	   Obstacle o3;
	   Obstacle o4;
	   Caculate caculate;
	   DrawParticle dp = new DrawParticle();
	   int p=0;
	   int i=0;
	   int j=0;
	   int u=0;
	   int index=0;
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
	   Spaceship spaceship =new Spaceship(initial_x+140,initial_y+260,speed_x,speed_y,8,8); 
	   Electron electron;
	   boolean i1 =false;
	   boolean i2 =false;
	   boolean i3 =false;
	   
	   double vax=0;
	   double vay=0;
	   double vbx=0;
	   double vby=0;
	   double vcx=0;
	   double vcy=0;	   
	   double ax = 800;
	   double ay = 600;	   
	   double bx = 1000;
	   double by=500;	   
	   double cx = 800;
	   double cy = 150;
	   ArrayList <Image>s1=new ArrayList<Image>();
	   Collision collision1 = new Collision(ax,ay,35,15,vax,vay);
	   Collision collision2 = new Collision(bx,by,40,20,vbx,vby);
	   Collision collision3 = new Collision(cx,cy,45,13,vcx,vcy);
       boolean isFinished = false;
       boolean[] isList = new boolean[3] ;
	   ArrayList<Collision> arrayList = new ArrayList<Collision>();
	   private Vector shapes = new Vector();

	   
	   RestartWhite restart;
	   NextWhite next;
	   ExitWhite exit;
	   
	  public ZhiziTest2(){
   	   super();
       setPreferredSize(new Dimension(1080,720));
        ZhiziTestLis Zzlis = new ZhiziTestLis();
		this.addMouseListener(Zzlis);
    	this.addMouseMotionListener(Zzlis);
    	 arrayList.add(collision1);
         arrayList.add(collision2);
         arrayList.add(collision3);
         isList[0] = i1;
         isList[1] = i2;
         isList[2] = i3;
         
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
	    	 collision1 = new Collision(ax,ay,30,15,vax,vay);
	    	 collision2 = new Collision(bx,by,40,20,vbx,vby);
	    	 collision3 = new Collision(cx,cy,45,13,vcx,vcy);

	         arrayList = new ArrayList<Collision>();
	         arrayList.add(collision1);
	         arrayList.add(collision2);
	         arrayList.add(collision3);
	         isPressed=false;
	         isOk=false;
	         isFinished=false;
	         spaceship =new Spaceship(160,240,speed_x,speed_y,8,8); 

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
	    	
	    	AlphaComposite ac= AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
	    	AlphaComposite ac1= AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
	    	gBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    	gBuffer.setColor(gc.darkColors[0]);
		    gBuffer.fillRect(0, 0, iBuffer.getWidth(), iBuffer.getHeight());	
//		    gBuffer.drawImage(backgroundImage, 0, 0, 1280, 720, null);
		    
		    
		    if(isList[0]==true&&isList[1]==true&&isList[2]==true)
		    	  isFinished=true;
		    
		    
		    if(!isFinished||p!=37){
             electron = new Electron(spaceship, 25,15, i,-Math.PI/3);
		     Ellipse2D.Double elec = electron.getE();		    
             electron = new Electron(spaceship,25,15,j,Math.PI/3);
		     Ellipse2D.Double elec2 = electron.getE();
     	     electron = new Electron(spaceship,25,15,u,Math.PI);
             Ellipse2D.Double elec3 = electron.getE();
		    
		    
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
	         Ellipse2D.Double e1_s = collision1.getShadow(index);
	         Ellipse2D.Double e2_s = collision2.getShadow(index);
	         Ellipse2D.Double e3_s = collision3.getShadow(index);
             Ellipse2D.Double oval = spaceship.getSpaceship();
             
             
             
             
		    o1 = new Obstacle(260, 0, 100, 400);
		    Rectangle2D.Double r1 = o1.getObstacle();
		    Rectangle2D.Double r1_s =o1.getShadow(index);
		    o2= new Obstacle(260, 550, 100,600);
		    Rectangle2D.Double r2 = o2.getObstacle();
		    Rectangle2D.Double r2_s =o2.getShadow(index);
		    o3 = new Obstacle(460, 0, 150, 200);
		    Rectangle2D.Double r3 = o3.getObstacle();
		    Rectangle2D.Double r3_s =o3.getShadow(index);
		    o4 = new Obstacle(460, 320, 150, 400);
		    Rectangle2D.Double r4 = o4.getObstacle();
		    Rectangle2D.Double r4_s =o4.getShadow(index);
		    gBuffer.setColor(gc.lightColors[2]);
		    gBuffer.setComposite(ac);
	  		gBuffer.fill(r1_s);			  		   
	        gBuffer.setComposite(ac1);
		    gBuffer.fill(r1);
		    gBuffer.setComposite(ac);
	  		gBuffer.fill(r2_s);			  		   
	        gBuffer.setComposite(ac1);
		    gBuffer.fill(r2);
		    gBuffer.setComposite(ac);
	  		gBuffer.fill(r3_s);			  		   
	        gBuffer.setComposite(ac1);
		    gBuffer.fill(r3);
		    gBuffer.setComposite(ac);
	  		gBuffer.fill(r4_s);			  		   
	        gBuffer.setComposite(ac1);
		    gBuffer.fill(r4);

     	    gBuffer.setColor(gc.lightColors[5]);
	        gBuffer.draw(elec);
	        gBuffer.fill(elec);
		    gBuffer.setColor(gc.lightColors[4]);
		    gBuffer.draw(elec2);
		    gBuffer.fill(elec2);
		    gBuffer.setColor(gc.darkColors[4]);
		    gBuffer.draw(elec3);
		    gBuffer.fill(elec3);
		    gBuffer.setColor(gc.lightColors[2]);
		    gBuffer.draw(oval);
		    gBuffer.fill(oval);
	 
		   
		    
		    
		    
		    
		    
		    
		    if(isList[0])
		       gBuffer.setColor(gc.lightColors[0]);
                else 
				gBuffer.setColor(gc.lightColors[1]);	
 	            gBuffer.fill(e1); 	            
 	  		    gBuffer.setComposite(ac);
 	  		    gBuffer.fill(e1_s);			  		   
 	     		gBuffer.setComposite(ac1);
 	     		
 	     		
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
 	            
 	            
		    pe.pe3(r1, oval.getBounds2D(), spaceship, o1,dp);
		    pe.pe3(r2, oval.getBounds2D(), spaceship, o2,dp);
		    pe.pe3(r3, oval.getBounds2D(), spaceship, o3,dp);
		    pe.pe3(r4, oval.getBounds2D(), spaceship, o4,dp);

		    for(int i=0;i<arrayList.size();i++){
		    	pe.pe3(r1,e1.getBounds2D(),arrayList.get(i),o1,dp);
		    	pe.pe3(r2,e1.getBounds2D(),arrayList.get(i),o2,dp);
		    	pe.pe3(r3,e1.getBounds2D(),arrayList.get(i),o3,dp);
		    	pe.pe3(r4,e1.getBounds2D(),arrayList.get(i),o4,dp);

		    }
		    
		    p2.setLocation(spaceship.x1, spaceship.y1);
		    processCollision();
		    dp.draw(iBuffer);
		    
		    }
		    
		    
		    if(isFinished)
		    {

		           if(p<37)
			       p++;
		           
		           if(p==37){
		        	   needChange=true;
		        	   changeTo = "zhiziTest3";
		           }
		           
		    }
	  }

	  
	  
	  
	  
	  public void processCollision(){
	        for(int index=0;index<arrayList.size();index++){
	        if(arrayList.get(index).getP().distance(p2)<arrayList.get(index).size+spaceship.size+5){
	        	if(predict(arrayList.get(index)).distance(predict())<=arrayList.get(index).getP().distance(p2))  {
	     //   	if(arrayList.get(index).getEllipse().intersects(spaceship.getSpaceship().getBounds2D())){
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
	        if(arrayList.get(i).getP().distance(arrayList.get(j).getP())<arrayList.get(i).size+arrayList.get(j).size+5){
			//if(arrayList.get(i).getEllipse().intersects(arrayList.get(j).getEllipse().getBounds2D())){
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
			
			x1=initial_x;
			y1=initial_y;
		}
		     pe.pe1(collision1,dp);
		     pe.pe1(collision2,dp);
		     pe.pe1(collision3,dp);
		     spaceship.x1 = spaceship.x1+0.2*Math.sin(index*Math.PI/150);
		     spaceship.y1 =spaceship.y1-0.6*Math.sin(index*Math.PI/150);
		     collision1.y = collision1.y+0.8*Math.sin(index*Math.PI/30);
		     collision1.x = collision1.x-0.2*Math.sin(index*Math.PI/30);
		     collision2.y = collision2.y-0.6*Math.sin(index*Math.PI/150);
		     collision2.x = collision2.x+0.2*Math.sin(index*Math.PI/150);
		     collision3.y = collision3.y+0.5*Math.sin(index*Math.PI/190);
		     collision3.x = collision3.x-0.2*Math.sin(index*Math.PI/190);
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
			changeTo = "zhiziTest3";
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
			    
			    
			   speed_x=(final_x - initial_x) *0.03;		    
			   speed_y=(final_y - initial_y) *0.03;
			   
/*			   if(speed_x>=20)
				   speed_x = 20;
			   if(speed_y>=20*720/1280)
			   speed_y = 20*720/1280;
		     if(speed_x<=-20)
				   speed_x=-20;
			  if(speed_y<=-20*720/1280)
			   speed_y=-20*720/1280;
*/			    spaceship =new Spaceship(spaceship.x1,spaceship.y1,speed_x,speed_y,8,8);
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
