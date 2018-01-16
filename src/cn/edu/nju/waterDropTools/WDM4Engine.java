package cn.edu.nju.waterDropTools;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import GameMusic.MusicThread;
import cn.edu.nju.publicTools.DrawParticle;

public class WDM4Engine {
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
	
	Area topLeftCorner;
	Area topRightCorner;
	Area bottomLeftCorner;
	Area bottemRightCorner;
	Area upBorder;
	Area downBorder;
	public Rectangle2D[] bounds = new Rectangle2D[4];
	public Area[] corners = {topLeftCorner,topRightCorner,bottomLeftCorner,bottemRightCorner,upBorder,downBorder};

	
	public WDM4Engine(WaterDrop waterDrop,ArrayList<CanBeCutObject> objects,DrawParticle part){
		this.waterDrop = waterDrop;
		this.objects = objects;
		this.part = part;

		
		
		//����6������������
		int[][] corner_x={ {-360,360,0,-360}, {1280-360,1280+360,1280+360,1280},{0,-360,-360,360}, {1280,1280+360,1280+360,1280-360}};
		int[][] corner_y={ {0,0,360,360},        {0,0,360,360},                                     {360,360,720,720},{360,360,720,720}};
		int[][] middle_x = {{360,1280-360,640},{360,640,1280-360}};
		int[][] middle_y = {{0,0,280},{720,720-280,720}};
		
		
		for(int i=0;i<4;i++){
			corners[i] = new Area(new Polygon(corner_x[i], corner_y[i], 4));
		}
		for(int i=0;i<2;i++){
			corners[i+4] = new Area(new Polygon(middle_x[i], middle_y[i], 3));
		}
		
		topLeftCorner = corners[0];
		topRightCorner = corners[1];
		bottomLeftCorner = corners[2];
		bottemRightCorner = corners[3];
		upBorder = corners[4];
		downBorder = corners[5];
		

		
	}
	
	
	
	public void setDelta(double vx,double vy){
		deltaX = vx-waterDrop.vx;
		deltaY = vy-waterDrop.vy;
	}
	
	/**
	 * ������һ״̬
	 */
	public void computeNextLoc(){
		
		//ˮ����45��Ƿ���
		hitCorner();
		
		//���ѭ���������������б���ˮ����������ʱ����¼��һ�����㡣��֪ͨ���壬�Ƿ�ˮ�δ�͸
		for(int i = 0;i<objects.size();i++){
			Point2D dropPoint = new Point((int)waterDrop.x,(int)waterDrop.y);
			Area area = objects.get(i).area;
			
			//��������ڷ���
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
		
		borderMove();
		
	}
	
	//ˮ�������߽ǣ������õ�����ϵ��ת45��ļ�����ʽ��
	private void hitCorner(){
		//ת��Ϊ��ʱ��45������
		double transX = Math.sqrt(2)/2*(waterDrop.vx+waterDrop.vy);
		double transY = Math.sqrt(2)/2*(waterDrop.vy- waterDrop.vx);
		
		 
		 //���ĸ��߽�����ֽ⣬��߸�������ٶ��ж���ײ
		double deltaDistance = 140*Math.sin(theta);
		 double leftBoundSpeed = deltaDistance*thetaSpeed;
		 double  rightBoundSpeed = -deltaDistance*thetaSpeed;
		 double upBoundSpeed = -deltaDistance*thetaSpeed;
		 double downBoundSpeed = deltaDistance*thetaSpeed;
		 
		 double[] leftTransSpeed =   speedSplit(leftBoundSpeed, 0);
		 double[] rightTransSpeed = speedSplit(rightBoundSpeed, 0);
		 double[] upTransSpeed = speedSplit(0, upBoundSpeed);
		 double[] downTransSpeed = speedSplit(0, downBoundSpeed);
		
		Area temArea = (Area)topLeftCorner.clone();
		Area temArea2 = (Area)bottemRightCorner.clone();
		temArea.intersect(waterDrop.waterDrop);
		temArea2.intersect(waterDrop.waterDrop);
	

		if(!temArea.isEmpty()&&transX-leftTransSpeed[0]<0){
			transX= -transX+2*leftTransSpeed[0];
			//ת��0������
			waterDrop.vx = Math.sqrt(2)/2*(transX -transY);
			waterDrop.vy = Math.sqrt(2)/2*(transX+transY);
			part.add(new Point2D.Double(waterDrop.x, waterDrop.y));
			MusicThread musicthread = new MusicThread ();
		     musicthread.creatMT("rebound00", 1);
		     musicthread.start();
		}else if(!temArea2.isEmpty()&&transX-rightTransSpeed[0]>0){
			transX= -transX+2*rightTransSpeed[0];
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
	
	
		if(!temArea.isEmpty()&&transY-leftTransSpeed[1]>0){
			transY= -transY+2*leftTransSpeed[1];
			//ת��0������
			waterDrop.vx = Math.sqrt(2)/2*(transX -transY);
			waterDrop.vy = Math.sqrt(2)/2*(transX+transY);
			part.add(new Point2D.Double(waterDrop.x, waterDrop.y));
			MusicThread musicthread = new MusicThread ();
		     musicthread.creatMT("rebound00", 1);
		     musicthread.start();
		}else if(!temArea2.isEmpty()&&transY-rightTransSpeed[1]<0){
			transY= -transY+2*rightTransSpeed[1];
			waterDrop.vx = Math.sqrt(2)/2*(transX -transY);
			waterDrop.vy = Math.sqrt(2)/2*(transX+transY);
			part.add(new Point2D.Double(waterDrop.x, waterDrop.y));
			MusicThread musicthread = new MusicThread ();
		     musicthread.creatMT("rebound00", 1);
		     musicthread.start();
		}
		
		temArea = (Area)upBorder.clone();
		temArea2 = (Area)downBorder.clone();
		
		temArea.intersect(waterDrop.waterDrop);
		temArea2.intersect(waterDrop.waterDrop);
		
		if(!temArea.isEmpty()){
			if(waterDrop.x<640&&transY-upTransSpeed[1]<0){
				transY= -transY+2*upTransSpeed[1];
				part.add(new Point2D.Double(waterDrop.x, waterDrop.y));
				MusicThread musicthread = new MusicThread ();
			     musicthread.creatMT("rebound00", 1);
			     musicthread.start();
			}
			else if(waterDrop.x>640&&transX-upTransSpeed[0]<0){
				transX= -transX+2*upTransSpeed[0];
				part.add(new Point2D.Double(waterDrop.x, waterDrop.y));
				MusicThread musicthread = new MusicThread ();
			     musicthread.creatMT("rebound00", 1);
			     musicthread.start();
			}
			waterDrop.vx = Math.sqrt(2)/2*(transX -transY);
			waterDrop.vy = Math.sqrt(2)/2*(transX+transY);
			
		}
		
		if(!temArea2.isEmpty()){
			if(waterDrop.x<640&&transX-downTransSpeed[0]>0){
				transX= -transX+2*downTransSpeed[0];
				part.add(new Point2D.Double(waterDrop.x, waterDrop.y));
				MusicThread musicthread = new MusicThread ();
			     musicthread.creatMT("rebound00", 1);
			     musicthread.start();
			}
			else if(waterDrop.x>640&&transY-downTransSpeed[1]>0){
				transY = -transY+2*downTransSpeed[1];
				part.add(new Point2D.Double(waterDrop.x, waterDrop.y));
				MusicThread musicthread = new MusicThread ();
			     musicthread.creatMT("rebound00", 1);
			     musicthread.start();
			}
			waterDrop.vx = Math.sqrt(2)/2*(transX -transY);
			waterDrop.vy = Math.sqrt(2)/2*(transX+transY);
			
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
		
	
	
	double theta =0;
	double thetaSpeed = 0.04;
	
	//�������Һ������������ƶ�
	private void borderMove(){
		AffineTransform asMoveLeft = new AffineTransform();
		AffineTransform asMoveRight = new AffineTransform();
		AffineTransform asMoveUp = new AffineTransform();
		AffineTransform asMoveDown = new AffineTransform();
	    if(theta>Math.PI*2){
	    	theta = theta-Math.PI*2;
	    }
	    //�Ƕ�����
		theta = theta +thetaSpeed;
		
       double deltaDistance = 140*Math.sin(theta);
		
		
		//�ĸ��任�ֱ�ƽ��
		asMoveLeft.translate(deltaDistance*thetaSpeed, 0);
		asMoveRight.translate(-deltaDistance*thetaSpeed, 0);
		asMoveUp.translate(0, -deltaDistance*thetaSpeed);
		asMoveDown.translate(0, deltaDistance*thetaSpeed);
		
		
		//ƽ��6������
		topLeftCorner = new Area(asMoveLeft.createTransformedShape(topLeftCorner));
		bottomLeftCorner = new Area(asMoveLeft.createTransformedShape(bottomLeftCorner));
	    
		topRightCorner = new Area(asMoveRight.createTransformedShape(topRightCorner));
		bottemRightCorner = new Area(asMoveRight.createTransformedShape(bottemRightCorner));
		
		upBorder = new Area(asMoveUp.createTransformedShape(upBorder));
		downBorder = new Area(asMoveDown.createTransformedShape(downBorder));
		
		
		corners[0] = topLeftCorner;
		corners[1] = topRightCorner;
		corners[2] = bottomLeftCorner;
		corners[3] = bottemRightCorner;
		corners[4] = upBorder;
		corners[5] = downBorder;
		
	}	
	
	
	
	//�������ڷ���
	private void objectsRebound(CanBeCutObject ob){
		//ת��Ϊ��ʱ��45������
		

		if(ob.vx[0]==0&&ob.vy[0]==0){
			return;
		}
		
		 double deltaDistance = 140*Math.sin(theta);
		
		 
		 //���ĸ��߽�����ֽ⣬��߸�������ٶ��ж���ײ
		 double leftBoundSpeed = deltaDistance*thetaSpeed;
		 double  rightBoundSpeed = -deltaDistance*thetaSpeed;
		 double upBoundSpeed = -deltaDistance*thetaSpeed;
		 double downBoundSpeed = deltaDistance*thetaSpeed;
		 
		 double[] leftTransSpeed =   speedSplit(leftBoundSpeed, 0);
		 double[] rightTransSpeed = speedSplit(rightBoundSpeed, 0);
		 double[] upTransSpeed = speedSplit(0, upBoundSpeed);
		 double[] downTransSpeed = speedSplit(0, downBoundSpeed);
		
		double transX = Math.sqrt(2)/2*(ob.vx[0]+ob.vy[0]);
		double transY = Math.sqrt(2)/2*(ob.vy[0]- ob.vx[0]);
				
				Area temArea = (Area)topLeftCorner.clone();
				Area temArea2 = (Area)bottemRightCorner.clone();
				temArea.intersect(ob.area);
				temArea2.intersect(ob.area);
			
			//	if((!temArea.isEmpty()&&transX-leftTransSpeed[0]<0)||(!temArea2.isEmpty()&&transX-rightTransSpeed[0]>0)){
				if(!temArea.isEmpty()&&transX-leftTransSpeed[0]<0){
				transX= -transX+2*leftTransSpeed[0];
			
					//ת��0������
					ob.vx[0] = Math.sqrt(2)/2*(transX -transY);
					ob.vy[0] = Math.sqrt(2)/2*(transX+transY);
					part.add(new Point2D.Double(ob.x[0], ob.y[0]));
				}
				else if(!temArea2.isEmpty()&&transX-rightTransSpeed[0]>0){
					transX= -transX+2*rightTransSpeed[0];
	
					//ת��0������
					ob.vx[0] = Math.sqrt(2)/2*(transX -transY);
					ob.vy[0] = Math.sqrt(2)/2*(transX+transY);
					part.add(new Point2D.Double(ob.x[0], ob.y[0]));
				}
				
			
				
				temArea = (Area)bottomLeftCorner.clone();
				temArea2 = (Area)topRightCorner.clone();
				
				temArea.intersect(ob.area);
				temArea2.intersect(ob.area);
			
			//	if((!temArea.isEmpty()&&transY>0)||(!temArea2.isEmpty()&&transY<0)){;
				if(!temArea.isEmpty()&&transY-leftTransSpeed[1]>0){
					transY= -transY+2*leftTransSpeed[1];
					//ת��0������
					ob.vx[0] = Math.sqrt(2)/2*(transX -transY);
					ob.vy[0] = Math.sqrt(2)/2*(transX+transY);
					part.add(new Point2D.Double(ob.x[0], ob.y[0]));
				}
				else if(!temArea2.isEmpty()&&transY-rightTransSpeed[1]<0){
					transY= -transY+2*rightTransSpeed[1];
					//ת��0������
					ob.vx[0] = Math.sqrt(2)/2*(transX -transY);
					ob.vy[0] = Math.sqrt(2)/2*(transX+transY);
					part.add(new Point2D.Double(ob.x[0], ob.y[0]));
					
				}
				
				temArea = (Area)upBorder.clone();
				temArea2 = (Area)downBorder.clone();
				
				temArea.intersect(ob.area);
				temArea2.intersect(ob.area);
				
				if(!temArea.isEmpty()){
					if(ob.x[0]<640&&transY-upTransSpeed[1]<0){
						transY= -transY+2*upTransSpeed[1];
						part.add(new Point2D.Double(ob.x[0], ob.y[0]));
					}
					else if(ob.x[0]>640&&transX-upTransSpeed[0]<0){
						transX= -transX+2*upTransSpeed[0];
						part.add(new Point2D.Double(ob.x[0], ob.y[0]));
					}
					ob.vx[0] = Math.sqrt(2)/2*(transX -transY);
					ob.vy[0] = Math.sqrt(2)/2*(transX+transY);
					
				}
				
				if(!temArea2.isEmpty()){
					if(ob.x[0]<640&&transX-downTransSpeed[0]>0){
						transX= -transX+2*downTransSpeed[0];
						part.add(new Point2D.Double(ob.x[0], ob.y[0]));
					}
					else if(ob.x[0]>640&&transY-downTransSpeed[1]>0){
						transY = -transY+2*downTransSpeed[1];
						part.add(new Point2D.Double(ob.x[0], ob.y[0]));
					}
					ob.vx[0] = Math.sqrt(2)/2*(transX -transY);
					ob.vy[0] = Math.sqrt(2)/2*(transX+transY);
				
				}
		
		//Ħ����
				if(Math.hypot(ob.vx[0], ob.vy[0])>10){
					ob.vx[0]=0.9*ob.vx[0];
					ob.vy[0]=0.9*ob.vy[0];
				}
				
	}

	//�ٶȷֽ�,����������ת45����ٶ�������
	private double[] speedSplit(double speedX,double speedY){
		double[] transSpeed = new double[2];
		transSpeed[0] = Math.sqrt(2)/2*(speedX+speedY);
		transSpeed[1] = Math.sqrt(2)/2*(speedY- speedX);
		
		
		return transSpeed;
	
	}
	
		
}
