package cn.edu.nju.waterDropTools;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

/**
 * @可被切割的物体
 *
 */
public class CanBeCutObject {
	
	double waterDrop_vx;
	double waterDrop_vy;
	double formerArea ;
	double[] vx = new double[3];
	double[] vy = new double[3];
	
	public float[] alpha = {1,1,1};
	
	
	double[] x = new double[3];
	double[] y = new double[3];
	public Area area;
	public Area cutArea1 = new Area();
	public Area cutArea2 = new Area();
	
	ArrayList<Point2D> pointList = new ArrayList<Point2D>();	
	public ArrayList<Point2D> sawtooth = new ArrayList<Point2D>();
	
	int[]  xList ;
	int[]  yList ;
	boolean isPassed = false;
	
	int sawtoothIndex = 0;
	
	AffineTransform asMove1 = new AffineTransform();
	AffineTransform asMove2 = new AffineTransform();
	AffineTransform asMove3 = new AffineTransform();
	public CanBeCutObject(Area area, double x,double y,double vx,double vy){
		for(int i=0;i<3;i++){
			this.x[i] = x;
			this.y[i] = y;
			this.vx[i] = vx;
			this.vy[i] = vy;
		}
		this.area = area;
		formerArea = computeArea(area.getBounds());
		asMove1.translate(this.x[0], this.y[0]);
		this.area = new Area(asMove1.createTransformedShape(area));
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
			
			return sinWave(area);
		}
		else{
			//生成锯齿状边缘
			
			cutTheArea(sawtooth);
			
			asMove1.setToIdentity();
			x[0] = x[0]+vx[0];
			y[0] = y[0]+vy[0];
			
			asMove1.translate(vx[0], vy[0]);			
			area = new Area(asMove1.createTransformedShape(area));

			
			return sinWave(area);
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

		return sinWave(cutArea1);
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
		return sinWave(cutArea2);
		
	}
	
	
	protected void cutTheArea(ArrayList<Point2D> sawtooth) {
		// TODO Auto-generated method stub
		
		
		int x = (int)pointList.get(0).getX()-(int)pointList.get(pointList.size()-1).getX();
		int y = (int)pointList.get(0).getY()-(int)pointList.get(pointList.size()-1).getY();
		

		
	    int formerX = (int)pointList.get(pointList.size()-1).getX();
		int formerY = (int)pointList.get(pointList.size()-1).getY();
	
		//切割添加的第一个点，为水滴离开时的点，延切割方向平移一大段距离
		Point2D firstPoint = new Point(formerX-10*x, formerY-10*y);
		sawtooth.add(firstPoint);
		
		//假如方向为从左上穿过到右下， 先右移一个很大的值，再上移一个很大的值
		int xDirection = 1;
		int yDirection = 1;
		if(x<0){									//使其与x，y同号
			xDirection = -1;
		}
		
		
		sawtooth.add(new Point((int)firstPoint.getX()-xDirection*1000,(int)firstPoint.getY()));
		sawtooth.add(new Point((int)firstPoint.getX()-xDirection*1000,(int)firstPoint.getY()-yDirection*5000));
		sawtooth.add(new Point((int)firstPoint.getX()+xDirection*2000,(int)firstPoint.getY()-yDirection*5000));
		
		
		formerX = (int)pointList.get(0).getX();
		formerY = (int)pointList.get(0).getY();
	
		//切割添加的最后一个点，为水滴离开时的点，延切割反方向平移一大段距离
		Point2D lastPoint =  new Point(formerX+10*x, formerY+10*y);
		sawtooth.add(lastPoint);
		
		xList = new int[sawtooth.size()];
		yList = new int[sawtooth.size()];
		
		
		//为了多边形的声明，将x，y坐标都变成一个int[]
		for(int i =0;i<sawtooth.size();i++){
			
			xList[i] = (int)sawtooth.get(i).getX();
			yList[i] = (int)sawtooth.get(i).getY();
		}
		
		
		//开始切割
		Polygon cutPolygon = new Polygon(xList, yList,sawtooth.size());
		Area cutArea = new Area(cutPolygon);
				
		cutArea.intersect(area);  //这里cutArea是切下来的部分，永远靠上
		area.subtract(cutArea);  //这里area是剩下的部分，靠下
		
		//如果被切下来部分的面积大于原面积的0.75，那么把cutArea1变为另一部分 ，把原area变为该area，假如两个都不大于原来面积的0.75，那么都变为cutArea（即将消失的部分）
		//顺便把每一部分的速度改一下，速度是将水滴的速度方向向上旋转30°，和向下旋转30°，生成两个新的速度向量，叠加到原来的速度上。
		
		     //生成两个新向量,式子是通过方程推导出来的，index 0为靠下部分速度，1为靠上部分速度
		double[] deltavx = { 	Math.sqrt(3)*waterDrop_vx*0.5-0.5*(Math.abs(waterDrop_vx)/waterDrop_vx)*waterDrop_vy, 				
								Math.sqrt(3)*waterDrop_vx*0.5+0.5*(Math.abs(waterDrop_vx)/waterDrop_vx)*waterDrop_vy};
			
		
		
		double[] deltavy ={
					Math.sqrt(3)*waterDrop_vy*0.5+0.5*(Math.abs(waterDrop_vx)),
					Math.sqrt(3)*waterDrop_vy*0.5-0.5*(Math.abs(waterDrop_vx))
			};
			
		
		if((computeArea(area.getBounds())<formerArea*0.3)&&(computeArea(cutArea.getBounds()))>formerArea*0.8){
			cutArea1 = area;
			area = cutArea;
			 vx[0] = vx[0]+ deltavx[1];  vy[0] = vy[0]+ deltavy[1];
			 vx[1] = vx[1]+ deltavx[0];  vy[1] = vy[1]+ deltavy[0];
			
		}
		else if((computeArea(cutArea.getBounds())<formerArea*0.3)&&(computeArea(area.getBounds()))>formerArea*0.8){
			cutArea1 = cutArea;
			
			vx[1] = vx[1]+ deltavx[1];  vy[1] = vy[1] + deltavy[1];
			vx[0] = vx[0]+ deltavx[0];  vy[0] = vy[0] + deltavy[0];
			
			
		}
		else{
			
			cutArea1 = cutArea;
			cutArea2 = area;
			area = new Area();
			
			vx[1] = vx[1]+ deltavx[1];  vy[1] = vy[1] + deltavy[1];
			vx[2] = vx[2]+ deltavx[0];  vy[2] = vy[2] + deltavy[0];
		}
		
		pointList = new ArrayList<Point2D>();
		 this.sawtooth = new ArrayList<Point2D>();
		isPassed = false;
		sawtoothIndex = 0;
		
	}

	
	//正弦波动
	double delta = 2*Math.random()*3.14;
	AffineTransform asSin = new AffineTransform();
	 Area sinWave(Area oneArea){
		double thetaSpeed = 0.04;
		double height = 0.4;
		delta = delta+thetaSpeed;
		asSin.translate(0,height*Math.cos(delta));
		oneArea = new Area(asSin.createTransformedShape(oneArea));
		return oneArea;
		
	}
	
	//计算面积的方法
	protected double computeArea(Rectangle rect) {		
		// TODO Auto-generated method stub
		return (rect.getWidth()*rect.getHeight());
			
	}

	int grap = 2; 
	protected void makeSawtooth() {
		// TODO Auto-generated method stub
		 //规定一个取点间隙
		
		for(int i =0;i<sawtooth.size();i++){
			Point2D point =  sawtooth.get(i);
			point.setLocation(point.getX()+vx[0], point.getY()+vy[0]);
		}
		
		
		try{
			int x = (int)pointList.get(sawtoothIndex).getX()-(int)pointList.get(sawtoothIndex+grap).getX();
			int y = (int)pointList.get(sawtoothIndex).getY()-(int)pointList.get(sawtoothIndex+grap).getY();
		
		
			double rate = Math.random()/2;
			
		    int formerX = (int)pointList.get(sawtoothIndex+grap/2).getX();
		    int formerY = (int)pointList.get(sawtoothIndex+grap/2).getY();
		    
		    if((sawtoothIndex%(2*grap))==0){
		    	rate=-rate;
		    }
		    pointList.get(sawtoothIndex+grap/2).setLocation(formerX+rate*y, formerY-rate*x);
		
	
		    sawtooth.add(pointList.get(sawtoothIndex));
		    sawtooth.add(pointList.get(sawtoothIndex+grap/2));
		    sawtooth.add(pointList.get(sawtoothIndex+grap));
		   
		    sawtoothIndex=sawtoothIndex+ grap;
		}catch(Exception ex){}

		
	}
	

}
