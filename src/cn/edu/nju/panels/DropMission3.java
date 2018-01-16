package cn.edu.nju.panels;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;

import cn.edu.nju.components.ExitButton;
import cn.edu.nju.components.Next;
import cn.edu.nju.components.Restart;
import cn.edu.nju.publicTools.DrawParticle;
import cn.edu.nju.publicTools.GameColor;
import cn.edu.nju.publicTools.Tail;
import cn.edu.nju.waterDropTools.AreaMaker;
import cn.edu.nju.waterDropTools.CanBeCutObject;
import cn.edu.nju.waterDropTools.WDM3Engine;
import cn.edu.nju.waterDropTools.WDM3Object;
import cn.edu.nju.waterDropTools.WaterDrop;

public class DropMission3 extends BackgroundPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ArrayList<CanBeCutObject> objects = new ArrayList<CanBeCutObject>();
	AreaMaker maker = new AreaMaker();
	WaterDrop drop = new WaterDrop(350, 360, 0, 0);
	WDM3Engine engine ;
	boolean isPressed = false;
	int launchNumber = 4;
	CutLis lis = new CutLis();
	Restart restart = new Restart();
	DrawParticle part;
	Tail tail =  new Tail(drop);

	private ExitButton exit;

	private Next next;
	
	public DropMission3(){
		this.setLayout(null);
		part = new DrawParticle();
		
		Area eightPoint = maker.getRectangle(150, 150, 0);
		eightPoint.add(maker.getRectangle(150, 150, Math.PI/4));
		
		CanBeCutObject ob = new WDM3Object(eightPoint, 640, 360, 0, 0);
		objects.add(ob);
		
		

		
		int[] x = {200,640,1080,640};
		int[] y = {360,100,360,620};
		
		for(int i=0;i<4;i++){
			ob = new CanBeCutObject(maker.getRectangle(100, 100, Math.PI/4), x[i], y[i], 0, 0);
			objects.add(ob);
		}
		
		engine = new WDM3Engine(drop, objects,part);
		
		this.removeAll();
		this.addMouseListener(lis);
		this.addMouseMotionListener(lis);		

		restart = new Restart();
		next = new Next();
		exit = new ExitButton();
		this.add(restart);	
		this.add(next);
		this.add(exit);	
	}
	
	public void init(){
		super.init();
		 objects = new ArrayList<CanBeCutObject>();
		 maker = new AreaMaker();
		 drop = new WaterDrop(350, 360, 0, 0);
		 
		 isPressed = false;
		 launchNumber = 4;		 
		 
		 tail.setWaterDrop(drop);		 
		
		 this.setLayout(null);
		 part = new DrawParticle();
		 	
		 
		 	Area eightPoint = maker.getRectangle(150, 150, 0);
			eightPoint.add(maker.getRectangle(150, 150, Math.PI/4));
			CanBeCutObject ob = new WDM3Object(eightPoint, 640, 360, 0, 0);
			objects.add(ob);
			
			
			
			int[] x = {200,640,1080,640};
			int[] y = {360,100,360,620};
			
			for(int i=0;i<4;i++){
				ob = new CanBeCutObject(maker.getRectangle(100, 100, Math.PI/4), x[i], y[i], 0, 0);
				objects.add(ob);
			}
			
			engine = new WDM3Engine(drop, objects,part);
			
			this.removeAll();
			restart = new Restart();
			next = new Next();
			exit = new ExitButton();
			this.add(restart);	
			this.add(next);
			this.add(exit);	
	
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		coordinateTrans();
		paintIBuffer();
		g.drawImage(iBuffer, 0, 0, null);

		if(restart.isMouseClicked()){
			init();
		}
		
		if(isMissionComplete()||next.isMouseClicked()){
			if(Math.random()<0.1){
			part.add(new Point((int)drop.x,(int)drop.y));
			}
					
			needChange = true;
			changeTo = "dropMission4";
			
		}
		if(exit.isMouseClicked()){
			needChange = true;
			changeTo = "startPanel";
		}
	
	}
	
	@Override
	public void paintIBuffer() {
		// TODO Auto-generated method stub
		
		//»­±³¾°
		gBuffer.setColor(GameColor.darkColors[10]);
		//gBuffer.setColor(Color.BLACK);
		gBuffer.fillRect(0, 0, paint_width, paint_height);
		
		//»­±ß½ç
		gBuffer.setColor(GameColor.lightColors[0]);
		for(int i=0;i<4;i++){
			gBuffer.fill(engine.corners[i]);			
		}
		
		//»­ËéÆ¬
		part.draw(iBuffer);
		
		//»­Î²¼£
		tail.drawTail(iBuffer);				
		
		//»­6¸öÇò
		if(isPressed){
			lis.initial_x = drop.x;
			lis.initial_y = drop.y;
			computeLoc();
			 gBuffer.setColor(Color.black);
     	      for(int i1 =0;i1<6;i1++){
     	   	  gBuffer.fill(lis.theShapes.get(i1));
		}
		}
		
		//»­Ë®µÎºÍË®µÎµ¹Ó°
		engine.computeNextLoc();
		gBuffer.setColor(GameColor.lightColors[0]);
		Area area = drop.getArea();
		gBuffer.fill(area);
		AlphaComposite ac= AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
		gBuffer.setComposite(ac);
		gBuffer.fill(drop.getShadow());		
		ac= AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
			
		//»­ÎïÌå
		for(int i=0;i<objects.size();i++){
			CanBeCutObject ob = objects.get(i);
			
			ac= AlphaComposite.getInstance(AlphaComposite.SRC_OVER,ob.alpha[1]);
			
			gBuffer.setColor(GameColor.lightColors[0]);	
			gBuffer.setComposite(ac);
			gBuffer.fill(ob.getCutArea1());
			
			ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,ob.alpha[2]);
			gBuffer.setComposite(ac);
			gBuffer.fill(ob.getCutArea2());
			
			ac= AlphaComposite.getInstance(AlphaComposite.SRC_OVER,ob.alpha[0]);
		     gBuffer.setComposite(ac);
			gBuffer.fill(ob.getArea());			
			
			
			
			//»­ÁÑºÛ
			gBuffer.setColor(Color.black);
			gBuffer.setStroke(new BasicStroke(3.0f));	
			for(int j =0; j<ob.sawtooth.size()-1 ;j++){
				Line2D.Double line = new Line2D.Double(ob.sawtooth.get(j), ob.sawtooth.get(j+1));
				gBuffer.draw(line);
			}
			gBuffer.setStroke(new BasicStroke(1.0f));		
		}
		
		
		
		
		//»­Ê£Óà·¢Éä´ÎÊý
		gBuffer.setColor(GameColor.darkColors[0]);
		gBuffer.setFont(new Font("Á¥Êé",Font.BOLD,40));
		gBuffer.drawString(launchNumber+"", 10, 700);

	}
	
	protected boolean isMissionComplete() {
		for(int i=0;i<objects.size();i++){
			CanBeCutObject ob = objects.get(i);
			boolean completeCut = (ob.area.isEmpty())&&(ob.cutArea1.isEmpty())&&(ob.cutArea2.isEmpty());
			if(completeCut){
				objects.remove(i);
				i--;
			}
			
		}
		if(objects.isEmpty()){
			return true;
		}else{
			return false;
		}
		
	}
	
	private void computeLoc(){
		
  	   double deltaX = lis.final_x-lis.initial_x;
  	   double deltaY = lis.final_y-lis.initial_y;
  	       for(int i =0;i<6;i++){
  		   double x = lis.initial_x+(i/5.0)*deltaX;
  		   double y = lis.initial_y+(i/5.0)*deltaY;
  		   Ellipse2D.Float ellipse = lis.theShapes.get(i);
  		   ellipse.setFrameFromCenter(x,y,x+5,y+5);
  	   }
	
	
	}
	
	class CutLis extends PanelLis{
		double initial_x;
		double initial_y;
		double final_x;
		double final_y;
		Vector<Ellipse2D.Float> theShapes = new Vector<Ellipse2D.Float>();
		
		public CutLis() {
			// TODO Auto-generated constructor stub
			for(int i=0;i<6;i++){
				theShapes.add(new Ellipse2D.Float());
			}
		}
		
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		if(launchNumber>0){
			isPressed =true;
		   Point p2 = (Point) e.getPoint();
		   p2 = coordinateTrans(p2);

		   final_x = p2.getX();
		   final_y = p2.getY();
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
		
			if(launchNumber>0){
				Point2D.Double p = new Point2D.Double(drop.x, drop.y);				
							
				initial_x = p.getX();
				initial_y = p.getY();
				
				
			}
	}


		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
			if(launchNumber>0){
			isPressed =false;
			   
		    double speed_x =(final_x - initial_x) *0.03;
		    double speed_y =(final_y - initial_y) *0.03;
		    
		    
		    
		    engine.setDelta(speed_x, speed_y);
		    
		    launchNumber = launchNumber-1;

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
