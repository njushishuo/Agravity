package cn.edu.nju.spaceTools;

import java.awt.AlphaComposite;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.omg.CORBA.PUBLIC_MEMBER;

public class Cubic extends ObjectsInSpace{
	

	static ArrayList<Image> CUBIC_LIST = new ArrayList<Image>();
	int count = 0;
	int size;
	boolean isEntry;
	
	int key;
	
	public Cubic(double x,double y,int size,boolean isEntry){
		identity =4;
		
		if(CUBIC_LIST.isEmpty()){
		for(int i = 0; i<50; i++){
			
			String s = Integer.toString(i);
			String filename;
			
			if(i<10)
			filename ="cubics/"+ "合成 2_0000"+s+".png";
			else
				filename = "cubics/"+"合成 2_000"+s+".png";
			
			try {

				Image image = new ImageIcon(filename).getImage();
				CUBIC_LIST.add(image);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
		}
		}
		
		this.x = x;
		this.y = y;
		this.size = size;
		this.isEntry = isEntry;
		
		if(isEntry){
			key = (int)(Math.random()*100000);
		}
	}
		
		
	public void draw(BufferedImage iBuffer){
		if(isEntry){	
			if(count==49)
				count = 0;
			else
				count++;
		}
		
		else{
			if(count==0)
				count = 49;
			else{
				count--;
			}
		}
		
			Image bi = CUBIC_LIST.get(count);
			gBuffer = iBuffer.createGraphics();
			
			asMove.translate(x, y);
			coordinateTrans();
			gBuffer.transform(asMove);
			
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
			gBuffer.setComposite(ac);
			gBuffer.drawImage(bi,-size/2,-size/2,size,size,null);
			
			asMove.setToIdentity();
			gBuffer.setTransform(asMove);
			
	}
		
	public void matchTheCubic(Cubic cubic){
		if(isEntry){
			this.key = cubic.key;
		}
		
	
	}
	
	public boolean isMatch(Cubic cubic) {
		
		return (key==cubic.key)&&(!cubic.isEntry);
	
	}

	public Area cubicArea(){

		Area cubicArea;
		AffineTransform moveCubic = new AffineTransform();
		moveCubic.translate(x, y);
		cubicArea = new Area(moveCubic.createTransformedShape(new Rectangle(-size/2,-size/2,size,size)));
		return cubicArea;
		
	}

}


