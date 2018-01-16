package cn.edu.nju.panels;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
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
import cn.edu.nju.panels.SpaceMission1.SpaceLis;
import cn.edu.nju.panels.SuperMission.PanelLis;
import cn.edu.nju.publicTools.DrawParticle;
import cn.edu.nju.publicTools.GameColor;
import cn.edu.nju.publicTools.Tail;
import cn.edu.nju.spaceTools.Cubic;
import cn.edu.nju.spaceTools.ExitInSpace;
import cn.edu.nju.spaceTools.ObjectsInSpace;
import cn.edu.nju.spaceTools.PlanetC;
import cn.edu.nju.spaceTools.PlanetD;
import cn.edu.nju.spaceTools.SM2Engine;
import cn.edu.nju.spaceTools.SpaceEngine;
import cn.edu.nju.spaceTools.SpaceShip;
import cn.edu.nju.waterDropTools.WaterDrop;

public class SpaceMission2 extends BackgroundPanel{

private static final long serialVersionUID = 1L;
	

	BufferedImage bi;
	DrawParticle part = new DrawParticle();
	SM2Engine engine;
	Tail shipTail;
	Tail dropTail;
	WaterDrop waterDrop;
	
	ArrayList<ObjectsInSpace> objects = new ArrayList<ObjectsInSpace>();
	
	ArrayList<Ellipse2D> sixballs = new ArrayList<Ellipse2D>();
	Point p1 = new Point();
	Point p2 = new Point();
	boolean isPressed = false;
	boolean isReleased = false;
	boolean dropMove = false;
	
	RestartWhite restart = new RestartWhite();
	ExitWhite exit = new ExitWhite();
	NextWhite next = new NextWhite();
	
	int launcherNum = 1;
	
	
	public SpaceMission2(){
		
		bi = SpaceMission1.bi;
		
		for(int i=0;i<6;i++){
			sixballs.add(new Ellipse2D.Float());
		}
		
		this.removeAll();
		
		
		this.add(restart);
		this.add(exit);
		this.add(next);
		
		SpaceLis slis = new SpaceLis();
		this.addMouseListener(slis);
		this.addMouseMotionListener(slis);
		
		SpaceShip spaceship = new SpaceShip(150,200);
		
		PlanetD planetD = new PlanetD(640,720,1280,0,0,200);

		ExitInSpace exitInSpace = new ExitInSpace(1100, 360, 100);
		
		Cubic entryCubic = new Cubic(400, 600, 150, true);
		Cubic outCubic = new Cubic(950, 200, 150, false);
		entryCubic.matchTheCubic(outCubic);		
		
		objects.add(spaceship);
		objects.add(planetD);
		
		objects.add(exitInSpace);
		
		objects.add(entryCubic);
		objects.add(outCubic);
		shipTail = new Tail(spaceship);
		
		waterDrop = new WaterDrop(640, 200, 0, 0);
		dropTail = new Tail(waterDrop);
		engine = new SM2Engine(objects, part,waterDrop);
				
	}
	
	
	public void init(){
		super.init();
		isPressed = false;
		isReleased = false;
		dropMove = false;
		launcherNum = 1;
		
		
		waterDrop = new WaterDrop(640, 200, 0, 0);
		dropTail = new Tail(waterDrop);
		engine = new SM2Engine(objects, part,waterDrop);

		this.removeAll();
		 restart = new RestartWhite();
		 exit = new ExitWhite();
		 next = new NextWhite();
		
		this.add(restart);
		this.add(exit);
		this.add(next);
		
		SpaceShip ship = new SpaceShip(150,200);
		objects.set(0, ship);
		shipTail = new Tail(ship);
	}
	
	public void paintIBuffer(){
		
		
		coordinateTrans();
		engine.update();
		
		
		
		
		p1.setLocation(objects.get(0).x,objects.get(0).y);
		
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
		gBuffer.setComposite(ac);
		gBuffer.drawImage(bi,0,0,1280,720,null);
		
		//背景飘星星
		paintBackground();
		
		
		//飞船和水滴尾迹
		shipTail.drawTail(iBuffer);
		dropTail.drawTail(iBuffer);		
		
		//如果玩家已经自动飞船，画水滴
		
		gBuffer.setColor(GameColor.lightColors[0]);
		Area area = waterDrop.getArea();
		gBuffer.fill(area);
		 ac= AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
		gBuffer.setComposite(ac);
		gBuffer.fill(waterDrop.getShadow());		
		ac= AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);	
		gBuffer.setComposite(ac);
			

		
		
		
		for(int i=0;i<objects.size();i++){
			objects.get(i).draw(iBuffer);
		}
		
		if(isPressed){
			gBuffer.setColor(Color.WHITE);
			for(int i=0;i<6;i++)
				gBuffer.fill((Ellipse2D)sixballs.get(i));
		}
		
		part.draw(iBuffer);
	}
	
	public void paintComponent(Graphics g){
		if(restart.isMouseClicked()){
			init();
		}
		
		if(engine.isMissionComplete()||next.isMouseClicked()){
			needChange =true;
			changeTo  = "spaceMission3";
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
	
	
	
	class SpaceLis extends PanelLis{
		
		
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
		
			if(launcherNum>0){
		   isPressed =true;
		   p2 = (Point) e.getPoint();
		  p2 = coordinateTrans(p2);
		  
		  double deltax = p2.getX() - p1.getX();
          double deltay = p2.getY() - p1.getY();
          
          objects.get(0).vx = deltax/100000;
          objects.get(0).vy = deltay/100000;
		  
		   computeLoc(sixballs);
			}
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
			if(launcherNum>0){
			isPressed =true;
			 p1.setLocation(objects.get(0).x, objects.get(0).y);
			}
		}



		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			dropMove = true;
			
			if(launcherNum>0){
			isPressed = false;
            isReleased = true;
            
            double deltax = (p2.getX() - p1.getX())*0.03;
            double deltay = (p2.getY() - p1.getY())*0.03;
            
            objects.get(0).vx = deltax;
            objects.get(0).vy = deltay;
            
            launcherNum--;
			}
			//music 
			MusicThread musicthread = new MusicThread ();
			musicthread.creatMT("rock00", 1);
			musicthread.start();
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
