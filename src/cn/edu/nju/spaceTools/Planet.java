package cn.edu.nju.spaceTools;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Planet extends ObjectsInSpace{
	
	ArrayList<String> filelist = new ArrayList<String>();
	
	double centerx;
	double centery;
	double t=0;
	double v;
	double radius;
	int count = 0;
	int size;
	double gravitycontrast;
	
	protected double gravitysize;
	protected double planetsize;
	
	public void draw(BufferedImage iBuffer){
		
	}
	
	public Area gravityArea(){
		
		AffineTransform movecircle = new AffineTransform();
		movecircle.translate(x, y);
		
		Area gravitycircle = new Area(movecircle.createTransformedShape(new Ellipse2D.Double(-gravitysize/2,-gravitysize/2,gravitysize,gravitysize)));
		movecircle.setToIdentity();
		
		return gravitycircle;
	}
	
	
    public Area planetArea(){
    	
    	AffineTransform movecircle = new AffineTransform();
		movecircle.translate(x, y);
		
		Area planetcircle = new Area(movecircle.createTransformedShape(new Ellipse2D.Double(-planetsize/2,-planetsize/2,planetsize,planetsize)));
		movecircle.setToIdentity();
		
		return planetcircle;
	}

}
