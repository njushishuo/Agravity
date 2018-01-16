package cn.edu.nju.publicTools;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Square {

	double size = 15+(Math.random()*10);
	public double x;
	double y;
	double vx;
	double amplitude;
	double frequency;
	double omega;
	double initialPhase = 2*Math.random()*Math.PI;
	public float alpha = (float) (0.2f+(float)Math.random()*0.3);
	
	private Rectangle2D.Double rectangle;
	private Area floatRec;
	

	
	public Square(){
		
		vx = 9+(Math.random()*6);
		amplitude = 20 + (Math.random()*5);
		frequency = 100+Math.random()*50;
		x = -size;
		y = (Math.random()*720) ;
		rectangle = new Rectangle2D.Double(x, y, size/2, size/2);
		
	}
	
	public Area update(){
		
		double realY;
		AffineTransform asMove = new AffineTransform();
		AffineTransform asRotate = new AffineTransform();
		
		x = x + vx;
		double randomAmplitude  = amplitude + Math.random()*6;
		y = y + vx * (randomAmplitude)*Math.cos(x/frequency+initialPhase)/frequency;
		//realY = y + (randomAmplitude*Math.sin(x/frequency));
		omega = Math.atan((randomAmplitude)*Math.cos(x/frequency+initialPhase)/frequency);
		rectangle = new Rectangle2D.Double(x, y, size/2, size/2);
		
		asMove.translate(x, y);
		asRotate.rotate(omega,x+size/4,y+size/4);
		Shape temRec = asMove.createTransformedShape(asRotate.createTransformedShape(rectangle));
		
		floatRec = new Area(temRec);
		
		
		return floatRec;
	}
	
	
}
