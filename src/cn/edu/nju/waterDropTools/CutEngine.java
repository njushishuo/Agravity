package cn.edu.nju.waterDropTools;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import GameMusic.MusicThread;
import cn.edu.nju.publicTools.DrawParticle;


/**
 * @水滴关的物理引擎，职责是切割点的记录，通知是否物体被水滴穿透,水滴的拐弯效果，物体的减速，物体及水滴的碰壁反弹。
 *
 */
public class CutEngine {

	WaterDrop waterDrop;
	ArrayList<CanBeCutObject> objects;
	DrawParticle part;
	//下面是关于水滴拐弯效果的一些数据。水滴拐弯是模拟抛物线，即恒定加速度。
	
	//应变化的vx，xy值
	double deltaX;
	double deltaY;
	
	//x，y方向的加速度
	double accelerationX;
	double accelerationY;
	
	//已变化量
	double haveChangedX;
	double haveChangedY;
	
	//上下左右边界,以及四个边角
	Rectangle2D upBound;
	Rectangle2D  downBound;
	Rectangle2D leftBound ;
	Rectangle2D rightBound;
	Area topLeftCorner;
	Area topRightCorner;
	Area bottomLeftCorner;
	Area bottemRightCorner;
	public Rectangle2D[] bounds = new Rectangle2D[4];
	public Area[] corners = {topLeftCorner,topRightCorner,bottomLeftCorner,bottemRightCorner};
	
	public CutEngine(WaterDrop waterDrop,ArrayList<CanBeCutObject> objects,DrawParticle part){
		this.waterDrop = waterDrop;
		this.objects = objects;
		this.part = part;
		
		leftBound = new Rectangle2D.Double(-1, 0, 1, 720);
		rightBound = new Rectangle2D.Double(1280, 0, 1, 720);
		upBound = new Rectangle2D.Double(0, 0, 1280, 50);
		downBound = new Rectangle2D.Double(0, 670, 1280, 50);
		bounds[0] = leftBound;
		bounds[1] = rightBound;
		bounds[2] = upBound;
		bounds[3] = downBound;
		
		//构造四个角落三角形
		int[][] x={ {0,0,120}, {1280-100,1280,1280},{0,0,100},{1280-100,1280,1280}};
		int[][] y={ {0,100,0}, {0,0,100},{720-100,720,720},{720,720,720-100}};
		
		for(int i=0;i<4;i++){
			corners[i] = new Area(new Polygon(x[i], y[i], 3));
		}
		topLeftCorner = corners[0];
		topRightCorner = corners[1];
		bottomLeftCorner = corners[2];
		bottemRightCorner = corners[3];
		
		
	}
	
	public void setDelta(double vx,double vy){
		deltaX = vx-waterDrop.vx;
		deltaY = vy-waterDrop.vy;
	}
	
	/**
	 * 计算下一状态
	 */
	public void computeNextLoc(){
		
		//水滴的碰壁反弹,碰壁减速
		Area dropArea = waterDrop.waterDrop;
		if((dropArea.intersects(upBound)&&waterDrop.vy<0)||(dropArea.intersects(downBound)&&waterDrop.vy>0)){
			
			waterDrop.vy = -waterDrop.vy*0.9;
			waterDrop.vx = waterDrop.vx*0.9;
			part.add(new Point2D.Double(waterDrop.x, waterDrop.y));
			MusicThread musicthread = new MusicThread ();
		    musicthread.creatMT("rebound00", 1);
		    musicthread.start();
			
					
		}
		if((dropArea.intersects(rightBound)&&waterDrop.vx>0)||(dropArea.intersects(leftBound)&&waterDrop.vx<0)){
			
			waterDrop.vx = -waterDrop.vx*0.9;
			waterDrop.vy = waterDrop.vy*0.9;

			part.add(new Point2D.Double(waterDrop.x, waterDrop.y));
			MusicThread musicthread = new MusicThread ();
		    musicthread.creatMT("rebound00", 1);
		    musicthread.start();
		}
		//水滴碰45°角反弹
		hitCorner();
		
		//这个循环用来遍历物体列表，当水滴在物体中时，记录下一个个点。并通知物体，是否被水滴穿透
		for(int i = 0;i<objects.size();i++){
			Point2D dropPoint = new Point((int)waterDrop.x,(int)waterDrop.y);
			Area area = objects.get(i).area;
			
			//物体的碰壁反弹，摩擦力
			objectsRebound(objects.get(i));			
			
			
			if(area.contains(dropPoint)){
				objects.get(i).pointList.add(dropPoint);
				objects.get(i).waterDrop_vx = waterDrop.vx;
				objects.get(i).waterDrop_vy = waterDrop.vy;
			}
			else if(!objects.get(i).pointList.isEmpty()){
				
				objects.get(i).isPassed = true;
				part.add(dropPoint);
				MusicThread musicthread = new MusicThread ();
			     musicthread.creatMT("drop00", 1);
			     musicthread.start();
			}
			
			
		}
		
		turnAround();
		
		
	}
	
	//水滴碰到边角，这里用到坐标系旋转45°的几个公式。
	private void hitCorner(){
		//转换为逆时针45°坐标
		double transX = Math.sqrt(2)/2*(waterDrop.vx+waterDrop.vy);
		double transY = Math.sqrt(2)/2*(waterDrop.vy- waterDrop.vx);
		
		Area temArea = (Area)topLeftCorner.clone();
		Area temArea2 = (Area)bottemRightCorner.clone();
		temArea.intersect(waterDrop.waterDrop);
		temArea2.intersect(waterDrop.waterDrop);
	
		if((!temArea.isEmpty()&&transX<0)||(!temArea2.isEmpty()&&transX>0)){
			transX= -transX;
			//转回0°坐标
			waterDrop.vx = Math.sqrt(2)/2*(transX -transY);
			waterDrop.vy = Math.sqrt(2)/2*(transX+transY);
			part.add(new Point2D.Double(waterDrop.x, waterDrop.y));
			MusicThread musicthread = new MusicThread ();
		     musicthread.creatMT("rebound00", 1);
		     musicthread.start();
		}
		
	
		
		temArea = (Area)bottomLeftCorner.clone();
		temArea2 = (Area)topRightCorner.clone();
		
		temArea.intersect(waterDrop.waterDrop);
		temArea2.intersect(waterDrop.waterDrop);
	
		if((!temArea.isEmpty()&&transY>0)||(!temArea2.isEmpty()&&transY<0)){
			transY= -transY;
			//转回0°坐标
			waterDrop.vx = Math.sqrt(2)/2*(transX -transY);
			waterDrop.vy = Math.sqrt(2)/2*(transX+transY);
			part.add(new Point2D.Double(waterDrop.x, waterDrop.y));
			MusicThread musicthread = new MusicThread ();
		     musicthread.creatMT("rebound00", 1);
		     musicthread.start();
		}
		
	}
	
	private void turnAround(){
		//向量模长
		double length = Math.sqrt(deltaX*deltaX+deltaY*deltaY);
		
		//加速度设为2
		double acceRate = 2;
		
		if(length!=0){
			//x，y加速度分量,平方和为1，即加速度大小恒为1
			accelerationX = acceRate*deltaX/length;
			accelerationY = acceRate*deltaY/length;
			
			
			if(deltaX/(haveChangedX+accelerationX)<=1){					
				accelerationX = deltaX-haveChangedX;
				deltaX=0;
				haveChangedX=0;
			}else{
				haveChangedX = haveChangedX+accelerationX;
			}
			waterDrop.vx = waterDrop.vx + accelerationX;
			
			
			if(deltaY/(haveChangedY+accelerationY)<=1){					
				accelerationY = deltaY-haveChangedY;
				deltaY=0;
				haveChangedY=0;
			}else{
			
				haveChangedY = haveChangedY+accelerationY;
			}
				waterDrop.vy= waterDrop.vy + accelerationY;
		
		}
}
		
		
	
	
	//物体碰壁反弹
	private void objectsRebound(CanBeCutObject ob){
		for(int i= 0;i<3;i++){
		ob.vx[i] = 0.97*ob.vx[i];
		ob.vy[i] = 0.97*ob.vy[i];
		}
		if((ob.area.intersects(upBound)&&ob.vy[0]<0)||(ob.area.intersects(downBound)&&ob.vy[0]>0)){
			
			ob.vy[0] = -ob.vy[0];
		}
		
		if((ob.area.intersects(rightBound)&&ob.vx[0]>0)||(ob.area.intersects(leftBound)&&ob.vx[0]<0)){
			
			ob.vx[0] = -ob.vx[0];
		}
		
		
	}
}
