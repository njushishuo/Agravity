package cn.edu.nju.spaceTools;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class ObjectsInSpace implements Serializable{


	private static final long serialVersionUID = -4473549626416682350L;
	

	public double x;
	public double y;
	
	public double vx;
	public double vy;
	
	double ax;
	double ay;
	
	int identity;
	
	AffineTransform asMove = new AffineTransform();
	AffineTransform asRotate = new AffineTransform();
	 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	 AffineTransform transform = new AffineTransform();
	transient Graphics2D gBuffer;
	public void draw(BufferedImage iBuffer){
		
	}
	
	  public void coordinateTrans(){
    	  transform.setToIdentity();    	
    	  transform.scale(screenSize.getWidth()/1280	, screenSize.getHeight()/720);
    	  gBuffer.transform(transform);
    	  gBuffer.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    
      }
	  
	  public int getIdentity(){
		  return identity;
	  }
}
