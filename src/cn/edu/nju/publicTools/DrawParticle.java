package cn.edu.nju.publicTools;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

public class DrawParticle {
	
	public ArrayList<Emitter> emitterlist = new ArrayList<Emitter>();
	
	public void draw(BufferedImage iBuffer){
		
	       Graphics2D gBuffer = iBuffer.createGraphics(); 
	       gBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	       
	       for(int i=0;i<emitterlist.size();i++){
	    	   
	    	   AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, emitterlist.get(i).alpha);
	    	   gBuffer.setComposite(ac);
	    	   
	    	   gBuffer.setColor(GameColor.lightColors[2]);
	    	   
	    	   gBuffer.fill(emitterlist.get(i).updateparticles());
	    	   
	       }
	       
	       for(int i=0;i<emitterlist.size();i++)
	    	   if(emitterlist.get(i).alpha==0)
	    		   emitterlist.remove(i);
	       
	       
	}
	
	public void add(Point2D point){
		emitterlist.add(new Emitter((int)point.getX(),(int)point.getY()));
	}

}
