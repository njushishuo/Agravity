package cn.edu.nju.waterDropTools;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class AreaMaker {

	public Area getRectangle(int width,int height,double theta){
		Area rectangle = new Area(new Rectangle(-(width/2),-(height/2),width, height));		
		AffineTransform as = new AffineTransform();
		
		//Ðý×ª
		as.rotate(theta);
		Shape s = as.createTransformedShape(rectangle);
				
		return (new Area(s));
		
	}
	
	public Area getEllipse( double width,double height){
		Area ellipse = new Area(new Ellipse2D.Double(-(width/2),-(height/2),width, height));
		return ellipse;		
	}

	public Area getTritangle(int width,int height,double theta ){
		int xArray[] = {0,width/2,-width/2};
		int yArray[] = {-height/2,height/2,height/2};
		
		Polygon triangle = new Polygon(xArray, yArray, 3);
		AffineTransform as = new AffineTransform();
		
		//Ðý×ª
		as.rotate(theta);
		Shape s = as.createTransformedShape(triangle);
				
		return (new Area(s));
	}



}
