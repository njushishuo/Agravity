package cn.edu.nju.panels;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
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
import cn.edu.nju.zhiziTools.ElectricField;
import cn.edu.nju.zhiziTools.Electron;
import cn.edu.nju.zhiziTools.Obstacle;
import cn.edu.nju.zhiziTools.PhysicalEngine;
import cn.edu.nju.zhiziTools.Spaceship;
import cn.edu.nju.zhiziTools.SpeedUp;

public class ZhiziTest3 extends BackgroundPanel {
   GameColor gc = new GameColor();
   PhysicalEngine pe = new PhysicalEngine();
   DrawParticle dp = new DrawParticle();
   ElectricField ef ;
   Electron electron;
   Spaceship spaceship =new Spaceship(50,200,0,0,5,5); 
   Obstacle o1;
   Obstacle o2;
   SpeedUp speedUp1 =new SpeedUp(810, 135);
   SpeedUp speedUp2 =new SpeedUp(810, 335);
   SpeedUp speedUp3 =new SpeedUp(810, 535);
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
   int p=0;
   int i=0;
   int j=0;
   int u=0;
   int index=0;
   float ac_ini =0;
   double ac_delt = 0.2;
   Collision collision1 = new Collision(200, 400, 35, 40, 0, 0);
   Collision collision2 = new Collision(300, 400,33, 40, 0, 0);
   Collision collision3 = new Collision(400, 400, 37, 40, 0, 0);
   Collision collision4 = new Collision(500, 400, 32, 25, 0, 0);
   Collision collision5 = new Collision(1200, 40, 35, 25, 0, 0);
   Collision collision6 = new Collision(900, 700, 15, 25, 0, 0);
   ArrayList<Collision> arrayList = new ArrayList<Collision>();
   ArrayList <Image>s1=new ArrayList<Image>();
   boolean i1 =false;
   boolean i2 =false;
   boolean i3 =false;
   boolean i4 =false;
   boolean i5 =false;
   boolean i6 =false;
   boolean[] isList = new boolean[6] ;
   boolean isFinished = false;
   private Vector shapes = new Vector();
   Image backgroundImage = new ImageIcon("images/zhizibg5.png").getImage();
   
   RestartWhite restart;
	NextWhite next;
	ExitWhite exit;
   
   public ZhiziTest3(){
   	   super();
       setPreferredSize(new Dimension(1080,720));
        ZhiziTestLis Zzlis = new ZhiziTestLis();
		this.addMouseListener(Zzlis);
    	this.addMouseMotionListener(Zzlis);
    	 arrayList.add(collision1);
         arrayList.add(collision2);
         arrayList.add(collision3);
         arrayList.add(collision4);
         arrayList.add(collision5);
         arrayList.add(collision6);
         isList[0] = i1;
         isList[1] = i2;
         isList[2] = i3;
         isList[3] = i4;
         isList[4] = i5;
         isList[5] = i6;
         
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
 	 collision1 = new Collision(200, 400, 35, 40, 0, 0);
 	 collision2 = new Collision(300, 400,33, 40, 0, 0);
 	 collision3 = new Collision(400, 400, 37, 40, 0, 0);
 	 collision4 = new Collision(500, 400, 32, 25, 0, 0);
 	 collision5 = new Collision(1200, 40, 35, 25, 0, 0);
 	  collision6 = new Collision(900, 700, 15, 25, 0, 0);
      arrayList = new ArrayList<Collision>();
      arrayList.add(collision1);
      arrayList.add(collision2);
      arrayList.add(collision3);
      arrayList.add(collision4);
      arrayList.add(collision5);
      arrayList.add(collision6);
      isPressed=false;
      isOk=false;
      isFinished=false;
      spaceship =new Spaceship(50,200,speed_x,speed_y,5,5); 
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
//	    gBuffer.drawImage(backgroundImage, 0, 0, 1280, 720, null);
	    
	    if(isList[0]==true&&isList[1]==true&&isList[2]==true&&isList[3]==true&&isList[4]==true&&isList[5]==true)
	    	  isFinished=true;
	    
	    
	    if(!isFinished||p!=37){
	    o1 = new Obstacle(120, 0, 425, 325);
	    Rectangle2D.Double r1 = o1.getObstacle();
	    Rectangle2D.Double r1_s =o1.getShadow(index);
	    o2 = new Obstacle(120, 470, 425, 400);
	    Rectangle2D.Double r2 = o2.getObstacle();
	    Rectangle2D.Double r2_s = o2.getShadow(index);
	    
	    gBuffer.setColor(gc.darkColors[2]);
	    gBuffer.fill(r1);
	    gBuffer.fill(r2);
	    gBuffer.setComposite(ac);	    
	    gBuffer.fill(r1_s);	    
	    gBuffer.fill(r2_s);
	    gBuffer.setComposite(ac1);
	    
	    
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
	    Ellipse2D.Double e4 = collision4.getEllipse();
	    Ellipse2D.Double e5 = collision5.getEllipse();
	    Ellipse2D.Double e6 = collision6.getEllipse();
	    Ellipse2D.Double e1_s = collision1.getShadow(index);
	    Ellipse2D.Double e2_s = collision2.getShadow(index);
	    Ellipse2D.Double e3_s = collision3.getShadow(index);
	    Ellipse2D.Double e4_s = collision4.getShadow(index);
	    Ellipse2D.Double e5_s = collision5.getShadow(index);
	    Ellipse2D.Double e6_s = collision6.getShadow(index);
	    Ellipse2D.Double oval = spaceship.getSpaceship();
	    
	    gBuffer.setColor(gc.lightColors[2]);
	    gBuffer.draw(oval);
	    gBuffer.fill(oval);
	    
	    
	    gBuffer.setColor(gc.lightColors[5]);
        gBuffer.draw(elec);
        gBuffer.fill(elec);
	    gBuffer.setColor(gc.lightColors[4]);
	    gBuffer.draw(elec2);
	    gBuffer.fill(elec2);
	    gBuffer.setColor(gc.darkColors[4]);
	    gBuffer.draw(elec3);
	    gBuffer.fill(elec3);
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
	    
 		if(isList[3])
 			gBuffer.setColor(gc.lightColors[0]);
 	        else 
 		    gBuffer.setColor(gc.lightColors[1]);	
 		gBuffer.fill(e4);
		gBuffer.setComposite(ac);
		gBuffer.fill(e4_s);		
 		gBuffer.setComposite(ac1);
 		
 		if(isList[4])
 			gBuffer.setColor(gc.lightColors[0]);
 	        else 
 		    gBuffer.setColor(gc.lightColors[1]);	
 		gBuffer.fill(e5);
		gBuffer.setComposite(ac);
		gBuffer.fill(e5_s);		
 		gBuffer.setComposite(ac1);
 		
 		if(isList[5])
 			gBuffer.setColor(gc.lightColors[0]);
 	        else 
 		    gBuffer.setColor(gc.lightColors[1]);	
 		gBuffer.fill(e6);
		gBuffer.setComposite(ac);
		gBuffer.fill(e6_s);		
 		gBuffer.setComposite(ac1);
 		
 		ef = new ElectricField(655, 120, 450, 500, index);
 		Rectangle2D.Double electricfield =ef.getElec();
 		gBuffer.setColor(gc.lightColors[0]);
 		gBuffer.draw(electricfield);
 		
 		Polygon p1 = speedUp1.getPolygon();
 		Polygon p4 = speedUp2.getPolygon();
 		Polygon p3 = speedUp3.getPolygon();
 		
 		ac_ini =(float)Math.sin(index*Math.PI/40);
 		if(ac_ini>=0.95){
 		    ac_ini=(float) 0.95;
 		}
 		if(ac_ini<=0){
 		    ac_ini=(float)0.05;
 		}
 		 AlphaComposite ac2= AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (ac_ini)*1f);
 		gBuffer.setComposite(ac2);
 		gBuffer.draw(p1);
 		gBuffer.draw(p4);
 		gBuffer.draw(p3);
 		gBuffer.setComposite(ac1);
 		pe.pe3(r1, oval.getBounds2D(), spaceship, o1,dp);
	    pe.pe3(r2, oval.getBounds2D(), spaceship, o2,dp);


	    for(int i=0;i<arrayList.size();i++){
	    	pe.pe3(r1,e1.getBounds2D(),arrayList.get(i),o1,dp);
	    	pe.pe3(r2,e1.getBounds2D(),arrayList.get(i),o2,dp);
            pe.pe6(ef,arrayList.get(i),Math.pow(-1, i)*Math.random());

	    }
 	
	    
	    
	    
	    
	    processCollision();
	    dp.draw(iBuffer);
	    p2.setLocation(spaceship.x1, spaceship.y1);
	    }
	    
	    
	    
	    if(isFinished) {

	           if(p<37)
		       p++;
	           
	           if(p==37){
	        	   needChange=true;
	        	   changeTo = "zhiziTest4";
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
			 spaceship.y1 =spaceship.y1-0.6*Math.sin(index*Math.PI/150);
		     
			 x1=initial_x;
			 y1=initial_y;
		}
		     pe.pe1(collision1,dp);
		     pe.pe1(collision2,dp);
		     pe.pe1(collision3,dp);
		     pe.pe1(collision4, dp);
		     pe.pe1(collision5, dp);
		     pe.pe1(collision6, dp);
	
		     collision1.x = collision1.x-0.4*Math.sin(index*Math.PI/30);		   
		     collision2.x = collision2.x+0.4*Math.sin(index*Math.PI/150);		     
		     collision3.x = collision3.x-0.4*Math.sin(index*Math.PI/190);		     
		     collision4.x = collision4.x-0.4*Math.sin(index*Math.PI/190);
		     collision5.x = collision5.x+0.4*Math.sin(index*Math.PI/120);
		     collision6.x = collision6.x-0.8*Math.sin(index*Math.PI/20);
		     collision1.y = collision1.y+0.4*Math.sin(index*Math.PI/150);  
		     collision2.y = collision2.y-0.4*Math.sin(index*Math.PI/150);
		     collision3.y = collision3.y-0.4*Math.sin(index*Math.PI/150);
		     collision4.y = collision4.y+0.4*Math.sin(index*Math.PI/150);
		     collision5.y = collision5.y+0.4*Math.sin(index*Math.PI/120);
		     collision6.y = collision6.y-0.8*Math.sin(index*Math.PI/20);
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
			changeTo = "zhiziTest4";
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
				isPressed =false;
				isOk =true;
				pa.setLocation(spaceship.x1, spaceship.y1);			
	     		initial_x = pa.getX();
				initial_y = pa.getY();
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
