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
 * @ˮ�ιص��������棬ְ�����и��ļ�¼��֪ͨ�Ƿ����屻ˮ�δ�͸,ˮ�εĹ���Ч��������ļ��٣����弰ˮ�ε����ڷ�����
 *
 */
public class CutEngine {

	WaterDrop waterDrop;
	ArrayList<CanBeCutObject> objects;
	DrawParticle part;
	//�����ǹ���ˮ�ι���Ч����һЩ���ݡ�ˮ�ι�����ģ�������ߣ����㶨���ٶȡ�
	
	//Ӧ�仯��vx��xyֵ
	double deltaX;
	double deltaY;
	
	//x��y����ļ��ٶ�
	double accelerationX;
	double accelerationY;
	
	//�ѱ仯��
	double haveChangedX;
	double haveChangedY;
	
	//�������ұ߽�,�Լ��ĸ��߽�
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
		
		//�����ĸ�����������
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
	 * ������һ״̬
	 */
	public void computeNextLoc(){
		
		//ˮ�ε����ڷ���,���ڼ���
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
		//ˮ����45��Ƿ���
		hitCorner();
		
		//���ѭ���������������б���ˮ����������ʱ����¼��һ�����㡣��֪ͨ���壬�Ƿ�ˮ�δ�͸
		for(int i = 0;i<objects.size();i++){
			Point2D dropPoint = new Point((int)waterDrop.x,(int)waterDrop.y);
			Area area = objects.get(i).area;
			
			//��������ڷ�����Ħ����
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
	
	//ˮ�������߽ǣ������õ�����ϵ��ת45��ļ�����ʽ��
	private void hitCorner(){
		//ת��Ϊ��ʱ��45������
		double transX = Math.sqrt(2)/2*(waterDrop.vx+waterDrop.vy);
		double transY = Math.sqrt(2)/2*(waterDrop.vy- waterDrop.vx);
		
		Area temArea = (Area)topLeftCorner.clone();
		Area temArea2 = (Area)bottemRightCorner.clone();
		temArea.intersect(waterDrop.waterDrop);
		temArea2.intersect(waterDrop.waterDrop);
	
		if((!temArea.isEmpty()&&transX<0)||(!temArea2.isEmpty()&&transX>0)){
			transX= -transX;
			//ת��0������
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
			//ת��0������
			waterDrop.vx = Math.sqrt(2)/2*(transX -transY);
			waterDrop.vy = Math.sqrt(2)/2*(transX+transY);
			part.add(new Point2D.Double(waterDrop.x, waterDrop.y));
			MusicThread musicthread = new MusicThread ();
		     musicthread.creatMT("rebound00", 1);
		     musicthread.start();
		}
		
	}
	
	private void turnAround(){
		//����ģ��
		double length = Math.sqrt(deltaX*deltaX+deltaY*deltaY);
		
		//���ٶ���Ϊ2
		double acceRate = 2;
		
		if(length!=0){
			//x��y���ٶȷ���,ƽ����Ϊ1�������ٶȴ�С��Ϊ1
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
		
		
	
	
	//�������ڷ���
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
