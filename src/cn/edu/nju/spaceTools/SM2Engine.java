package cn.edu.nju.spaceTools;

import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import GameMusic.MusicThread;
import cn.edu.nju.publicTools.DrawParticle;
import cn.edu.nju.waterDropTools.WaterDrop;

public class SM2Engine {
	ArrayList<ObjectsInSpace> objects;
	DrawParticle part;
	Rectangle2D upBound;
	Rectangle2D  downBound;
	Rectangle2D leftBound ;
	Rectangle2D rightBound;
	boolean isPass = false;
	boolean isMissionComplete = false;
	WaterDrop waterDrop;
	private double deltaX;
	private double deltaY;
	private double accelerationX;
	private double accelerationY;
	private double haveChangedX;
	private double haveChangedY;
	
	public SM2Engine(ArrayList<ObjectsInSpace> objects,DrawParticle part,WaterDrop waterDrop){
		leftBound = new Rectangle2D.Double(-1, 0, 1, 720);
		rightBound = new Rectangle2D.Double(1280, 0, 1, 720);
		upBound = new Rectangle2D.Double(0, 0, 1280, 1);
		downBound = new Rectangle2D.Double(0, 720, 1280, 1);
		this.objects = objects;
		this.part = part;
		this.waterDrop = waterDrop;
	}
	
	public void update(){
		//首先判断星球对飞船的影响
		
		SpaceShip ship = (SpaceShip)objects.get(0);
		ship.ax = 0;
		ship.ay = 0;
		Point2D.Double shipPoint = new Point2D.Double(ship.x, ship.y);
		
		//如果已经抵达终点，那么慢慢调头，然后飞出屏幕
		if(isPass){
			if(ship.vy<Math.pow(0.1, 3)&&ship.vy>-Math.pow(0.1, 3)){
			ship.vx = 20;	
			ship.x += ship.vx;
			ship.y += ship.vy;
				if(ship.x>1300){
					isMissionComplete =true;
				}
				
			}
			//调头
			else{
			
			ship.vy = ship.vy/1.5;
			}
		
			return;
		}
		
		
		
		
		for(int i=1;i<objects.size();i++){
			//引力影响和碰到星球死亡
			if(objects.get(i).identity==2){			
				Planet planet = (Planet)objects.get(i);
				Area shipArea = ship.spaceshipRect();
				shipArea.intersect(planet.planetArea());
				if(!shipArea.isEmpty()){
					ship.isDead = true;
					part.add(shipPoint);
								
				}				
					
				
				if(planet.gravityArea().contains(shipPoint)){
					
					
					
					double[] a = computeGra(shipPoint, planet);
					ship.ax += a[0];
					ship.ay += a[1];
				}
			
			}
			
			else if(objects.get(i).identity==3){
				ExitInSpace exitInSpace = (ExitInSpace)objects.get(i);
				Area shipArea = ship.spaceshipRect();
				shipArea.intersect(exitInSpace.exitArea());
				if(!shipArea.isEmpty()){
					isPass = true;					
				}
				
			}
			
		}
		
		//飞船进入传送门		
		for(int i=0;i<objects.size();i++){
			Cubic cub;
			Cubic outCub = null;
			if(objects.get(i).identity==4){
				cub = (Cubic)objects.get(i);
				if(cub.isEntry){
					
					for(int j=0;j<objects.size();j++){
						
						if(objects.get(j).identity==4){
							outCub = (Cubic)objects.get(j);
						}else {
							continue;
						}
						
						if(cub.isMatch(outCub)&&(cub.cubicArea().contains(shipPoint))){
		
							ship.x = outCub.x;
							ship.y = outCub.y;
							//music 
							MusicThread musicthread = new MusicThread ();
							musicthread.creatMT("trans00", 1);
							musicthread.start();
						}
						
					}
					
					
				}
			}	
			
		}
		
		
		//飞船碰壁死亡
		Area shipArea = ship.spaceshipRect();
		if(shipArea.intersects(upBound)||shipArea.intersects(downBound)){
			ship.isDead = true;
			part.add(shipPoint);
			
		}
		if((shipArea.intersects(rightBound))||shipArea.intersects(leftBound)){
			
			ship.isDead = true;
			part.add(shipPoint);
		}
		
		
		
		//水滴追击
		setDelta();
		turnAround();
		

		//飞船碰水滴死亡
		shipArea = ship.spaceshipRect();
		shipArea.intersect(waterDrop.waterDrop);
		if(!shipArea.isEmpty()){
			ship.isDead = true;
			part.add(shipPoint);
		}
		
		
		if(ship.isDead){
			ship.x = -100;
			ship.y = -100;
			ship.vx = 0;
			ship.vy = 0;
			MusicThread musicthread = new MusicThread ();  // music 
			musicthread.creatMT("explo04",1);
			musicthread.start();
		    ship.isDead = false;
			return;
		}
		
		
		ship.vx += ship.ax;
		ship.vy += ship.ay;
		ship.x += ship.vx;
		ship.y += ship.vy;
		
	}
	
	public double[] computeGra(Point2D shipPoint,Planet planet){
		
		//加速度x，y
		double[] a = new double[2];
		//计算引力
		double gravity = planet.gravitycontrast/shipPoint.distance(planet.x, planet.y);
		double theta = Math.atan2(planet.y-shipPoint.getY(),planet.x-shipPoint.getX());
		a[0] = gravity*Math.cos(theta);
		a[1] = gravity*Math.sin(theta);
		return a;
	}

	public boolean isMissionComplete(){
		return isMissionComplete;
	}
	
	private void setDelta() {
		SpaceShip ship = (SpaceShip)objects.get(0);
		
		deltaX = ship.x+2*ship.vx-waterDrop.x;
		deltaY = ship.y+2*ship.vy-waterDrop.y;
		

	}
	
	private void turnAround(){
		//向量模长
		
		//如果飞船还没有动，或者已经死亡，或者已经过关，不要计算速度
		SpaceShip ship = (SpaceShip)objects.get(0);
		if(Math.hypot(ship.vx, ship.vy)<0.1||ship.isDead||isPass){
			return;
		}
		
		double length = Math.hypot(deltaX, deltaY);		
		
		//加速度设为2
		double acceRate = 3;
		
		if(length!=0){
			//x，y加速度分量,平方和为1，即加速度大小恒为1
			accelerationX = acceRate*deltaX/length;
			accelerationY = acceRate*deltaY/length;
			
			
			if(deltaX/(haveChangedX+accelerationX)<=1){					
			//	accelerationX = deltaX-haveChangedX;
				deltaX=0;
				haveChangedX=0;
			}else{
				haveChangedX = haveChangedX+accelerationX;
			}
			waterDrop.vx = waterDrop.vx + accelerationX;
			
			
			if(deltaY/(haveChangedY+accelerationY)<=1){					
			//	accelerationY = deltaY-haveChangedY;
				deltaY=0;
				haveChangedY=0;
			}else{
			
				haveChangedY = haveChangedY+accelerationY;
			}
				waterDrop.vy= waterDrop.vy + accelerationY;

				
				
		}
}
	
}
