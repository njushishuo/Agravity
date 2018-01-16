package cn.edu.nju.publicTools;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.LinkedList;

public class Emitter {
	
	LinkedList<TriangleParticle> particlelist = new LinkedList<TriangleParticle>();
	int amount = 4+(int)(Math.random()*6);
	int x;
	int y;
	float[] theta;
	Area particleArea;
	
	public float alpha =(float) (0.6+Math.random()*0.2);
	float alphaSpeed = (float)(0.0025+Math.random()*0.01);
	
	AffineTransform asRotate = new AffineTransform();
	AffineTransform asMove = new AffineTransform();
	
	public Emitter(int x,int y){
		
		theta = new float[amount];
		
		for(int i=0;i<amount;i++)
        theta[i] = (float) (4*Math.random()*Math.PI/(float)amount);
		
		this.x = x;
		this.y = y;
		
		for(int i=1;i<=amount;i++)
			particlelist.add(new TriangleParticle());
		
		asMove.translate(x, y);
		
	}
	
	public Area updateparticles(){
		
		alpha = alpha - alphaSpeed;
		if(alpha<0)
			alpha=0;
		
		asRotate.rotate(theta[0]);
		Shape a = asMove.createTransformedShape(asRotate.createTransformedShape(particlelist.get(0).update()));
		particleArea = new Area(a);
		
		for(int i=1;i<particlelist.size();i++){
			asRotate.rotate(theta[i]);
			a = asMove.createTransformedShape(asRotate.createTransformedShape(particlelist.get(i).update()));
			particleArea.add(new Area(a));
		}
		
		asRotate.setToIdentity();
		
		return particleArea;
		
	}

}
