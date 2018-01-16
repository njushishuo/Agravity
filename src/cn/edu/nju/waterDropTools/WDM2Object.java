package cn.edu.nju.waterDropTools;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class WDM2Object extends CanBeCutObject{

	double theta;
	double speedUpPoint;
	public WDM2Object(Area area, double x, double y, double vx, double vy,double theta) {
		super(area, x, y, vx, vy);
		this.theta = theta;		
		speedUpPoint =- theta/1.05;		
		AffineTransform polarTransform = new AffineTransform();
		double deltaX = 180*Math.cos(theta);
		double deltaY = 180*Math. sin(theta);
		polarTransform.translate(deltaX, deltaY);
		this.area = new Area(polarTransform.createTransformedShape(this.area));
		
	}

       public Area getArea(){
		
		if(!isPassed){
			
			asMove1.setToIdentity();
			x[0] = x[0]+vx[0];
			y[0] = y[0]+vy[0];
			
		
			if((pointList.size()-1)%grap==0){
				makeSawtooth();
			}
			
			asMove1.translate(vx[0], vy[0]);			
			area = new Area(asMove1.createTransformedShape(area));
			area = sinWave(area);
			
			return area;
		}
		else{
			//生成锯齿状边缘
			
			cutTheArea(sawtooth);
			
			asMove1.setToIdentity();
			x[0] = x[0]+vx[0];
			y[0] = y[0]+vy[0];
			
			asMove1.translate(vx[0], vy[0]);			
			area = new Area(asMove1.createTransformedShape(area));
			area = sinWave(area);
			
			return area;
		}
		
	}
	
       public Area getCutArea1(){
   		
   		if(alpha[1]<0.08f){
   			//假如这部分已经基本透明，那么就把它变成空区域，并把它的位置速度变得和area部分一样，这样下次切割就不会出错。
   			cutArea1 = new Area();
   			x[1] = x[0];
   			y[1] = y[0];
   			vx[1] = vx[0];
   			vy[1] = vy[0];
   			asMove2.setToIdentity();
   			asMove2.translate(x[1], y[1]);
   			cutArea1 = new Area(asMove2.createTransformedShape(cutArea1));
   		}
   		
   		if(cutArea1.isEmpty()){
   			alpha[1] = 1f;
   		}
   		else{
   		
   			alpha[1] = alpha[1]-0.04f;		
   		
   		}
   		asMove2.setToIdentity();
   		x[1] = x[1]+vx[1];
   		y[1] = y[1]+vy[1];
   		
   	
   		asMove2.translate(vx[1], vy[1]);			
   		cutArea1 = new Area(asMove2.createTransformedShape(cutArea1));

   		
   		return cutArea1;
   	}
   	
   	public Area getCutArea2(){
   		
   		if(alpha[2]<0.08f){
   			cutArea2 = new Area();
   			x[2] = x[0];
   			y[2] = y[0];
   			vx[2] = vx[0];
   			vy[2] = vy[0];
   			asMove3.setToIdentity();
   			asMove3.translate(x[2], y[2]);
   			cutArea2 = new Area(asMove3.createTransformedShape(cutArea2));
   			
   			
   		}
   		if(cutArea2.isEmpty()){
   			alpha[2] =1f;
   			
   		}
   		else{
   		
   			alpha[2] = alpha[2]-0.04f;		
   		}
   		
   		
   		asMove3.setToIdentity();
   		x[2] = x[2]+vx[2];
   		y[2] = y[2]+vy[2];
   		
   		asMove3.translate(vx[2], vy[2]);			
   		cutArea2 = new Area(asMove3.createTransformedShape(cutArea2));

   		return cutArea2;
   		
   	}
	
   	protected void cutTheArea(ArrayList<Point2D> sawtooth){
   		
   		super.cutTheArea(sawtooth);
   		vx[0] = 0;
   		vy[0] = 0;
   		
   	}
   	
   	double thetaSpeed = 0.01;
	Area sinWave(Area oneArea){
		AffineTransform polarTransform = new AffineTransform();
		
		double deltaSpeed = 0.005;
		
		double deltaX = 180*Math.cos(theta);
		double deltaY = 180*Math. sin(theta);
		polarTransform.translate(-deltaX, -deltaY);
		Shape shape =polarTransform.createTransformedShape(oneArea);
		polarTransform.setToIdentity();
		
			
		if(theta>0-speedUpPoint&&theta<(Math.PI+Math.PI*(1.0/1.5))/2-speedUpPoint){
			thetaSpeed = thetaSpeed + deltaSpeed;
		}
		else if(theta>(Math.PI+Math.PI*(1.0/1.5))/2-speedUpPoint&&theta<Math.PI+Math.PI*(1.0/1.5)-speedUpPoint){
			thetaSpeed = thetaSpeed - deltaSpeed;
		}
				
		theta = theta+thetaSpeed;
		

						
		
		if(theta>Math.PI+Math.PI*(1.0/1.5)-speedUpPoint-0.15){
			theta = theta-2*Math.PI;
			thetaSpeed = 0.01;
		}
		
		//极坐标变直角坐标
		deltaX = 180*Math.cos(theta);
		deltaY = 180*Math.sin(theta);
		polarTransform.translate(deltaX, deltaY);
		oneArea = new Area(polarTransform.createTransformedShape(shape));
		return oneArea;
		
	}
	
	
}
