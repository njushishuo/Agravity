package cn.edu.nju.publicTools;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import cn.edu.nju.spaceTools.SpaceShip;
import cn.edu.nju.waterDropTools.WaterDrop;

public class Tail {

	WaterDrop waterDrop;
	SpaceShip ship;
	ArrayList<AlphaPoint> tailList = new ArrayList<Tail.AlphaPoint>();
	
	public double[] fullScreenZoomRate = new double[2];
	 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	 AffineTransform transform = new AffineTransform();
	
	public Tail(WaterDrop waterDrop){
		this.waterDrop = waterDrop;		
		fullScreenZoomRate[0] = screenSize.getWidth()/1280;
		 fullScreenZoomRate[1] = screenSize.getHeight()/720;
	}
	
	public Tail(SpaceShip ship){
		this.ship = ship;
		fullScreenZoomRate[0] = screenSize.getWidth()/1280;
		 fullScreenZoomRate[1] = screenSize.getHeight()/720;
	}
	
	public void setWaterDrop(WaterDrop waterDrop){
		this.waterDrop = waterDrop;
	}
	
	public void setSpaceShip(SpaceShip ship){
		this.ship = ship;
	}
	
	public void drawTail(BufferedImage ibuffer){
		Graphics2D gBuffer = ibuffer.createGraphics();
		 gBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		 //坐标转换
	       coordinateTrans(gBuffer);
		
		//每次调用这个方法，创建一个画笔，并且记录最新的水滴的（或飞船的）位置。
	       
	       if(waterDrop!=null){
		tailList.add(new AlphaPoint(new Point2D.Double(waterDrop.x, waterDrop.y)));
		gBuffer.setColor(GameColor.lightColors[5]);
		gBuffer.setStroke(new BasicStroke(2.0f));
	       }
	
		if(ship!=null&&(Math.abs(ship.vx)+Math.abs(ship.vy))>1){
			tailList.add(new AlphaPoint(new Point2D.Double(ship.x,ship.y)));
			gBuffer.setColor(GameColor.lightColors[7]);
			gBuffer.setStroke(new BasicStroke(2.0f));
		}
		
		
		//遍历尾迹表，用描点画线的办法画出尾迹
		for(int i=0;i<tailList.size()-1;i++){
			AlphaPoint onePoint = tailList.get(i);
			AlphaPoint nextPoint = tailList.get(i+1);
		
			double deltaX = nextPoint.point.getX()-onePoint.point.getX();
			double deltaY = nextPoint.point.getY()-onePoint.point.getY();
			double distance = Math.hypot(deltaX, deltaY);
			
			//这里的处理是为了让线段之间减少重叠部分。
			Point2D realPoint = new Point2D.Double(onePoint.point.getX()+2*deltaX/distance, onePoint.point.getY()+2*deltaY/distance);
			
			onePoint.upDate();
			if(!onePoint.willDisappear){
				//设置这一小段线的透明度
				AlphaComposite ac= AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)onePoint.alpha);
				gBuffer.setComposite(ac);
				
				Line2D line = new Line2D.Double(realPoint, nextPoint.point);
				
				//画出这一段线
				gBuffer.draw(line);			
			}
			
		}
		
		
		//遍历列表，移除透明项
		for(int i=0;i<tailList.size();i++){
			AlphaPoint onePoint = tailList.get(i);
			if(onePoint.willDisappear){
				tailList.remove(i);
			}
				
		}
		
	}
	
	 public void coordinateTrans(Graphics2D gBuffer){
	   	  transform.setToIdentity();
	   	  transform.scale(fullScreenZoomRate[0], fullScreenZoomRate[1]);
	   	  gBuffer.setTransform(transform);
	     }
	
	class AlphaPoint{
		double alpha = 1;
		Point2D point;
		boolean willDisappear = false;
		
		public AlphaPoint(Point2D point){
			this.point = point;
			
		}
		
		//为了让尾迹大概持续两秒消失，这里采用指数函数 0.95^n。
		 void upDate(){
				alpha = alpha * 0.95;
				if(alpha<0.05){
					willDisappear =true;
				}	
			
		 }
			
		}
		
		
}
	

