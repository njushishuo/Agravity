package cn.edu.nju.publicTools;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

public class Glow {
	
	int[] X = {-6,-3,3,6,3,-3};
	int[] Y = {0,5,5,0,-5,-5};
	
	int[] biggerX = new int[6];
	int[] biggerY = new int[6];
	int[] smallerX = new int[6];
	int[] smallerY = new int[6];
	
	Polygon outerhexagon;
	Polygon innerhexagon;
    Area at;
	
	double x;
	double y;
	
	double vx;
	double vy;
	
	double size = 10;
	double omega = 0.1;
	float alpha = 0.8f;
	
	AffineTransform asRotate = new AffineTransform();
	AffineTransform asMove = new AffineTransform();
	
	boolean expand;
	
	
	public Glow(double x,double y){
		
		this.x = x;
		this.y = y;
		
		asMove.translate(x,y);
		
	}
	
	
	public void updateSpaceShip(){
		
		if(alpha<=0){
			size = 10;
			alpha = 0.8f;
		}
		else{
			size = size + 0.1;
			alpha = alpha - 0.024f;
		}
		
		asMove.translate(vx, vy);
		asRotate.rotate(omega);
		
		for(int i=0;i<6;i++){
			biggerX[i] = (int) (X[i]*size);
			smallerX[i] = (int) (X[i]*(size-1));
			biggerY[i] = (int) (Y[i]*size);
			smallerY[i] = (int) (Y[i]*(size-1));
		}
		
		outerhexagon = new Polygon(biggerX,biggerY,6);
		innerhexagon = new Polygon(smallerX,smallerY,6);
		
		Shape temshape1 = asMove.createTransformedShape(asRotate.createTransformedShape(outerhexagon));
		Shape temshape2 = asMove.createTransformedShape(asRotate.createTransformedShape(innerhexagon));
		
		at = new Area(temshape1);
		at.subtract(new Area(temshape2));
		
		
		
	}
	
	public void drawSpaceShip(BufferedImage bi){
		
		Graphics2D gBuffer = bi.createGraphics(); 
		
		updateSpaceShip();
		
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
		gBuffer.setComposite(ac);
		
		gBuffer.fill(at);
		
	}
	
}
