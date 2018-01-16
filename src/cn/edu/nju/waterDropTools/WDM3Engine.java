package cn.edu.nju.waterDropTools;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import GameMusic.MusicThread;
import cn.edu.nju.publicTools.DrawParticle;

public class WDM3Engine {
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
	
	//以及四个边角
	
	Area topLeftCorner;
	Area topRightCorner;
	Area bottomLeftCorner;
	Area bottemRightCorner;
	public Rectangle2D[] bounds = new Rectangle2D[4];
	public Area[] corners = {topLeftCorner,topRightCorner,bottomLeftCorner,bottemRightCorner};
	
	public WDM3Engine(WaterDrop waterDrop,ArrayList<CanBeCutObject> objects,DrawParticle part){
		this.waterDrop = waterDrop;
		this.objects = objects;
		this.part = part;
		
		//构造四个角落三角形
		int[][] x={ {0,0,640}, {640,1280,1280},{0,0,640},{640,1280,1280}};
		int[][] y={ {0,360,0}, {0,0,360},{360,720,720},{720,720,360}};
		
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
			
		//水滴碰45°角反弹
		hitCorner();
		
		//这个循环用来遍历物体列表，当水滴在物体中时，记录下一个个点。并通知物体，是否被水滴穿透
		for(int i = 0;i<objects.size();i++){
			Point2D dropPoint = new Point((int)waterDrop.x,(int)waterDrop.y);
			Area area = objects.get(i).area;
			
			//物体摩擦力，碰壁反弹
			hitCornerAndForce(objects.get(i));			
			
			
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
	
	//水滴碰到边角，这里用到坐标系旋转的几个公式。
	private void hitCorner(){
		//转换坐标系
		double sintheta = Math.sin(Math.atan(16.0/9));
		double costheta = Math.cos(Math.atan(16.0/9));				
		double transX = waterDrop.vx*costheta + waterDrop.vy*sintheta;
		double transY = waterDrop.vy*costheta  - waterDrop.vx*sintheta;
		
		
		Area temArea = (Area)topLeftCorner.clone();
		Area temArea2 = (Area)bottemRightCorner.clone();
		temArea.intersect(waterDrop.waterDrop);
		temArea2.intersect(waterDrop.waterDrop);
	
		if((!temArea.isEmpty()&&transX<0)||(!temArea2.isEmpty()&&transX>0)){
			transX= -transX;
			//转回0°坐标
			waterDrop.vx = transX*costheta - transY*sintheta;
			waterDrop.vy = transY*costheta + transX*sintheta;
			
			waterDrop.vx = waterDrop.vx*0.9;
			waterDrop.vy = waterDrop.vy*0.9;
			
			part.add(new Point2D.Double(waterDrop.x, waterDrop.y));
			MusicThread musicthread = new MusicThread ();
		     musicthread.creatMT("rebound00", 1);
		     musicthread.start();
		}
		
		//重新转换坐标
		sintheta = Math.sin(Math.atan(9.0/16));
		costheta = Math.cos(Math.atan(9.0/16));
		
		transX = waterDrop.vx*costheta + waterDrop.vy*sintheta;
		transY = waterDrop.vy*costheta  - waterDrop.vx*sintheta;
		
		temArea = (Area)bottomLeftCorner.clone();
		temArea2 = (Area)topRightCorner.clone();
		
		temArea.intersect(waterDrop.waterDrop);
		temArea2.intersect(waterDrop.waterDrop);
	
		if((!temArea.isEmpty()&&transY>=0)||(!temArea2.isEmpty()&&transY<=0)){
			transY= -transY;
			//转回0°坐标
			waterDrop.vx = transX*costheta - transY*sintheta;
			waterDrop.vy = transY*costheta + transX*sintheta;
			
			waterDrop.vx = waterDrop.vx*0.9;
			waterDrop.vy = waterDrop.vy*0.9;
			
			part.add(new Point2D.Double(waterDrop.x, waterDrop.y));
			MusicThread musicthread = new MusicThread ();
		     musicthread.creatMT("rebound00", 1);
		     musicthread.start();
		}
		
	}
	
	//物体碰壁反弹并摩擦减速
	private void hitCornerAndForce(CanBeCutObject ob) {
		
		ob.vx[0] = ob.vx[0]  * 0.95;
		ob.vy[0] = ob.vy[0] *0.95;
		
		//转换坐标系
		double sintheta = Math.sin(Math.atan(16.0/9));
		double costheta = Math.cos(Math.atan(16.0/9));				
		double transX = ob.vx[0]*costheta + ob.vy[0]*sintheta;
		double transY = ob.vy[0]*costheta  - ob.vx[0]*sintheta;
		
		
		Area temArea = (Area)topLeftCorner.clone();
		Area temArea2 = (Area)bottemRightCorner.clone();
		temArea.intersect(ob.area);
		temArea2.intersect(ob.area);
	
		if((!temArea.isEmpty()&&transX<0)||(!temArea2.isEmpty()&&transX>0)){
			transX= -transX;
			//转回0°坐标
			ob.vx[0] = transX*costheta - transY*sintheta;
			ob.vy[0] = transY*costheta + transX*sintheta;
			
			ob.vx[0] = ob.vx[0]*0.9;
			ob.vy[0]=  ob.vy[0]*0.9;
			
			part.add(new Point2D.Double(ob.x[0], ob.y[0]));
			MusicThread musicthread = new MusicThread ();
		     musicthread.creatMT("rebound00", 1);
		     musicthread.start();
		}
		
		//重新转换坐标
				sintheta = Math.sin(Math.atan(9.0/16));
				costheta = Math.cos(Math.atan(9.0/16));
				
				transX = ob.vx[0]*costheta + ob.vy[0]*sintheta;
				transY = ob.vy[0]*costheta  - ob.vx[0]*sintheta;
		
		temArea = (Area)bottomLeftCorner.clone();
		temArea2 = (Area)topRightCorner.clone();
		
		temArea.intersect(ob.area);
		temArea2.intersect(ob.area);
	
		if((!temArea.isEmpty()&&transY>0)||(!temArea2.isEmpty()&&transY<0)){
			transY= -transY;
			//转回0°坐标
			ob.vx[0] = transX*costheta - transY*sintheta;
			ob.vy[0] = transY*costheta + transX*sintheta;
			
			ob.vx[0] = ob.vx[0]*0.9;
			ob.vy[0]= ob.vy[0]*0.9;
			
			part.add(new Point2D.Double(ob.x[0], ob.y[0]));
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
		
	
	
}
