package cn.edu.nju.spaceTools;

import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import GameMusic.MusicThread;
import cn.edu.nju.publicTools.DrawParticle;

public class SpaceEngine {
	ArrayList<ObjectsInSpace> objects;
	DrawParticle part;
	Rectangle2D upBound;
	Rectangle2D  downBound;
	Rectangle2D leftBound ;
	Rectangle2D rightBound;
	boolean isPass = false;
	boolean isMissionComplete = false;
	
	public SpaceEngine(ArrayList<ObjectsInSpace> objects,DrawParticle part){
		leftBound = new Rectangle2D.Double(-1, 0, 1, 720);
		rightBound = new Rectangle2D.Double(1280, 0, 1, 720);
		upBound = new Rectangle2D.Double(0, 0, 1280, 1);
		downBound = new Rectangle2D.Double(0, 720, 1280, 1);
		this.objects = objects;
		this.part = part;
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
		
		
		//飞船碰壁死亡
		Area dropArea = ship.spaceshipRect();
		if(dropArea.intersects(upBound)||dropArea.intersects(downBound)){
			ship.isDead = true;
			part.add(shipPoint);
			
		}
		if((dropArea.intersects(rightBound))||dropArea.intersects(leftBound)){
			
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
	
}
