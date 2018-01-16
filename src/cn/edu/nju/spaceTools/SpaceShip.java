package cn.edu.nju.spaceTools;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class SpaceShip extends ObjectsInSpace{


	static Image bi;
	
	double omega;
	boolean isDead = false;
	boolean isLaunch = false;
	
	public SpaceShip(double x,double y){
		
		identity = 1;
		
		this.x = x;
		this.y = y;
		
		vx = 0;
		vy = 0;
		
		if(bi==null){
			bi = new ImageIcon("spaceship.png").getImage();
		}
		
	}
	
	public void draw(BufferedImage iBuffer){
		if(isDead){
			return;
		}
		
		if(Math.hypot(vx, vy)>0.1){
			isLaunch = true;
		}
		
		gBuffer = iBuffer.createGraphics();
		
		
		if(vx==0)
			vx = 0.00000001;
		omega = Math.atan2(vy,vx);
		
		asMove.setToIdentity();
		asMove.translate(x,y);
		asRotate.rotate(omega);
		
		coordinateTrans();
		gBuffer.transform(asMove);		
		gBuffer.transform(asRotate);
		
		
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
		gBuffer.setComposite(ac);
		gBuffer.drawImage(bi, -50,-50,100,100,null);
		
		asMove.setToIdentity();
		asRotate.setToIdentity();
		
		gBuffer.setTransform(asMove);
		
	
		
	}
	
	
	
	
	public Area spaceshipRect(){
		
			Area rectArea;
			AffineTransform moveRect = new AffineTransform();
			AffineTransform rotateRect = new AffineTransform();
			moveRect.translate(x, y);
			rotateRect.rotate(omega);
			Shape rect = moveRect.createTransformedShape(rotateRect.createTransformedShape(new Rectangle(-40,-15,80,30)));
			rectArea = new Area(rect);
			
			moveRect.setToIdentity();
			rotateRect.setToIdentity();
		
		    return rectArea;
		
		}

	
}
