package cn.edu.nju.panels;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import GameMusic.MusicThread;
import cn.edu.nju.components.ExitWhite;
import cn.edu.nju.components.NextWhite;
import cn.edu.nju.components.RestartWhite;
import cn.edu.nju.publicTools.DrawParticle;
import cn.edu.nju.spaceTools.CD;
import cn.edu.nju.spaceTools.ExitInSpace;
import cn.edu.nju.spaceTools.ObjectsInSpace;
import cn.edu.nju.spaceTools.PlanetC;
import cn.edu.nju.spaceTools.PlanetD;
import cn.edu.nju.spaceTools.SM3Engine;
import cn.edu.nju.spaceTools.SpaceShip;

public class SpaceMission3 extends BackgroundPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	File f = new File("solidcolor2.jpg");
	BufferedImage bi;
	
    ArrayList<ObjectsInSpace> objects = new ArrayList<ObjectsInSpace>();
    DrawParticle particles = new DrawParticle();
	
	ArrayList<Ellipse2D> sixballs = new ArrayList<Ellipse2D>();
	Point p1;
	Point p2;
	boolean isPressed = false;
	boolean isReleased = false;
	RestartWhite restart = new RestartWhite();
	ExitWhite exit = new ExitWhite();
	NextWhite next = new NextWhite();
	
	SM3Engine sm3 = new SM3Engine(objects,particles);
	
	public SpaceMission3(){
		
		try {
			bi = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0;i<6;i++){
			sixballs.add(new Ellipse2D.Float());
		}
		
		SpaceLis slis = new SpaceLis();
		this.addMouseListener(slis);
		this.addMouseMotionListener(slis);
		
		this.add(restart);
		this.add(exit);
		this.add(next);
		
		
		SpaceShip spaceship = new SpaceShip(150,360);
		PlanetD planetA = new PlanetD(1280,360,350,0.02,720,100);
		PlanetC planetB = new PlanetC(1280,360,720,0,0,100);
		ExitInSpace exit = new ExitInSpace(1100,360,100);
		objects.add(spaceship);
		objects.add(planetA);
		objects.add(planetB);
		objects.add(exit);
		
	}
	
	public void paintIBuffer(){
		
		sm3.update();
		
		coordinateTrans();
		
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
		gBuffer.setComposite(ac);
		gBuffer.drawImage(bi,0,0,1280,720,null);
		
		paintBackgroundC();
		particles.draw(iBuffer);
		
		
		for(int i=0;i<objects.size();i++){
			objects.get(i).draw(iBuffer);
		}
		
		if(isPressed){
			gBuffer.setColor(Color.WHITE);
			for(int i=0;i<6;i++)
				gBuffer.fill((Ellipse2D)sixballs.get(i));
		}

		
	}
	
	public void paintComponent(Graphics g){
		
		if(restart.isMouseClicked()){
			init();
		}
		
		
		if(sm3.isMissionComplete()||next.isMouseClicked()){
			needChange =true;
			changeTo  = "startPanel";
		}
		
		if(exit.isMouseClicked()){
			needChange = true;
			changeTo = "startPanel";
		}
		
		paintIBuffer();
		g.drawImage(iBuffer, 0, 0, null);
	}
	
	
	private void computeLoc(ArrayList<Ellipse2D> sixballs){
	  	   
	    double deltaX = p2.getX()-p1.getX();
	  	double deltaY = p2.getY()-p1.getY();
	  	       
	  	for(int i =0;i<6;i++){
	  	    double x = p1.getX()+(i/5.0)*deltaX;
	  		double y = p1.getY()+(i/5.0)*deltaY;
	  		Ellipse2D.Float ellipse = (Ellipse2D.Float)sixballs.get(i);
	  		ellipse.setFrameFromCenter(x,y,x+5,y+5);
	  	}
	  	   
	}
	
	public void init(){
		
		super.init();
	    particles = new DrawParticle();
		
		sixballs = new ArrayList<Ellipse2D>();
		isPressed = false;
		isReleased = false;
		
	    sm3 = new SM3Engine(objects,particles);
	    
		
		for(int i=0;i<6;i++){
			sixballs.add(new Ellipse2D.Float());
		}
		
		this.removeAll();
		
		restart = new RestartWhite();
		 exit = new ExitWhite();
		 next = new NextWhite();
		
		this.add(restart);
		this.add(exit);
		this.add(next);
		
		SpaceShip spaceship = new SpaceShip(150,360);
		objects.set(0, spaceship);
		
	}
	
	class SpaceLis extends PanelLis{
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
		
		   isPressed =true;
		   p2 = (Point) e.getPoint();
		   p2 = coordinateTrans(p2);
		   computeLoc(sixballs);
		   double deltax = p2.getX() - p1.getX();
           double deltay = p2.getY() - p1.getY();
           objects.get(0).vx = deltax/20000;
           objects.get(0).vy = deltay/20000;
		   
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
			   isPressed =true;
			   p1 = new Point((int)objects.get(0).x,(int)objects.get(0).y);
			}
		


	    public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
			isPressed = false;
            isReleased = true;
            
            double deltax = p2.getX() - p1.getX();
            double deltay = p2.getY() - p1.getY();
            
            objects.get(0).vx = Math.min(deltax/20, 20);
            objects.get(0).vy = Math.min(deltay/20,20);
            
          //music 
			   MusicThread musicthread = new MusicThread ();
			   musicthread.creatMT("rock00", 1);
			   musicthread.start();
		}
	    

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}




		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
    }
}
	



	


