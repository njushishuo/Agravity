package cn.edu.nju.publicTools;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

public class TriangleParticle {
	
	int[] X = {-3,0,3};
	int[] Y = {-2,3,-2};
	int[] biggerX = new int[3];
	int[] biggerY = new int[3];
	int[] smallerXA= new int[3];
	int[] smallerYA= new int[3];
	int[] smallerXB= new int[3];
	int[] smallerYB= new int[3];
	int size = 3 + (int)(Math.random()*6);
	int x = 0;
	int vx = 2+(12-size);
	double initialPhase = (2*Math.random()*Math.PI/3);
	double omega =  (float)(1.0-size*0.1);
	public float alpha =(float) (0.6+Math.random()*0.2);
	float alphaSpeed = (float)(0.005+Math.random()*0.02);
	int type;
	
	AffineTransform asRotate = new AffineTransform();
	AffineTransform asMove = new AffineTransform();
	
	private Polygon biggerTriangle;
	private Polygon smallerTriangleA; 
	private Polygon smallerTriangleB;
	private Area triangle;
	
	public TriangleParticle(){
		
		for(int i=0;i<3;i++){
			biggerX[i] = X[i]*size;
			biggerY[i] = Y[i]*size;
			
			smallerXA[i] = X[i]*(size-1);
			smallerYA[i] = Y[i]*(size-1);
			
			smallerXB[i] = X[i]*(size-2);
			smallerYB[i] = Y[i]*(size-2);
		}
		
		biggerTriangle =new Polygon(biggerX,biggerY,3);
		smallerTriangleA = new Polygon(smallerXA,smallerYA,3);
		smallerTriangleB = new Polygon(smallerXB,smallerYB,3);
		
		

		if(Math.random()<=0.4)
			type = 1;
		else
			if(Math.random()<=0.7)
				type = 2;
			else
				type = 3;
		
		asRotate.rotate(initialPhase);
	    
	}
	
	
	/**
	 * @return 图像更新函数，返回一个图像，则图像变透明，返回null
	 */
	public Area update(){
				
    	 alpha = alpha-alphaSpeed;
		
        if(alpha<0){
			alpha=0;
		}
		
		
		asMove.translate(vx, 0);
		asRotate.rotate(omega);
		
		Shape temShape = asMove.createTransformedShape(asRotate.createTransformedShape(biggerTriangle));
		Shape temShape2 =asMove.createTransformedShape(asRotate.createTransformedShape(smallerTriangleA));
		Shape temShape3 =asMove.createTransformedShape(asRotate.createTransformedShape(smallerTriangleB));
		
		triangle = new Area(temShape);
		
		switch(type){
		case 2:triangle.subtract(new Area(temShape2));break;
		case 3:triangle.subtract(new Area(temShape3));break;
		}
		
		return triangle;
	}

}

