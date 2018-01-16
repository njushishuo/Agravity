package cn.edu.nju.spaceTools;

import java.awt.AlphaComposite;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SlowArea extends ObjectsInSpace{

	File f = new File("¼õËÙ´ø.png");
	static BufferedImage bi;
	
    public SlowArea(double x,double y){
		
		identity = 5;
		
		this.x = x;
		this.y = y;
		
		vx = 0;
		vy = 0;
		
		if(bi==null){
			try {
				bi = ImageIO.read(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
    
    public void draw(BufferedImage iBuffer){
    	
    	gBuffer = iBuffer.createGraphics();
    	
    	asMove.translate(x,y);
    	coordinateTrans();
    	gBuffer.transform(asMove);
    	AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
    	gBuffer.setComposite(ac);
    	gBuffer.drawImage(bi,-100,-100,200,200,null);
    	asMove.setToIdentity();
    	gBuffer.setTransform(asMove);
    	
    }
    
    public Area slowArea(){
    	
    	asRotate.rotate(Math.PI/4);
    	asMove.translate(x,y);
    	Shape temshape = asMove.createTransformedShape(asRotate.createTransformedShape(new Rectangle(-100,-100,200,200)));
    	Area slowarea = new Area(temshape);
    	asMove.setToIdentity();
    	asRotate.setToIdentity();
    	
    	return slowarea;
    	
    }



}
