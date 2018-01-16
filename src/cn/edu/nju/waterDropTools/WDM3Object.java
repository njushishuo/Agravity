package cn.edu.nju.waterDropTools;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

public class WDM3Object extends CanBeCutObject{

	public WDM3Object(Area area, double x, double y, double vx, double vy) {
		super(area, x, y, vx, vy);
		// TODO Auto-generated constructor stub
	}


	double thetaSpeed = 0;
	double deltaSpeed = 0.0005;
	double deltaTheta = 0;

	Area sinWave(Area oneArea){
		AffineTransform circleRotare = new AffineTransform();
		if(thetaSpeed>0.05&&deltaSpeed>0){
			deltaSpeed = -deltaSpeed;
		}else if(thetaSpeed<-0.05&&deltaSpeed<0){
			deltaSpeed = -deltaSpeed;
		}
			thetaSpeed = thetaSpeed+deltaSpeed;	
			deltaTheta = deltaTheta+thetaSpeed;
			
		circleRotare.rotate(deltaTheta, x[0], y[0]);
		
		oneArea = new Area(circleRotare.createTransformedShape(oneArea));
		return oneArea;
		
	}
	
}
