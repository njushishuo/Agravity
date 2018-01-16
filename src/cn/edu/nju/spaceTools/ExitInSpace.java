package cn.edu.nju.spaceTools;

import java.awt.AlphaComposite;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Identity;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ExitInSpace extends ObjectsInSpace{


	transient static ArrayList<Image> EXIT_LIST = new ArrayList<Image>();
	int count = 0;
	int size;
	
	
	public ExitInSpace(double x,double y,int size){
		identity = 3;
		this.x = x;
		this.y = y;
		this.size = size;
		
		String filename;
		
		if (EXIT_LIST.isEmpty()) {
			
		
		for(int i=0;i<10;i++){
			filename ="exit/"+ "ºÏ³É 1_0000"+i+".png";
			Image image = new ImageIcon(filename).getImage();
			EXIT_LIST.add(image);
		}
		
		}
		
	}
	
	public void draw(BufferedImage iBuffer){
		
		if(count==9)
			count = 0;
		else
			count++;
		
		Image bi = EXIT_LIST.get(count);
		gBuffer = iBuffer.createGraphics();
		
		coordinateTrans();
		asMove.translate(x, y);		
		gBuffer.transform(asMove);
		
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
		gBuffer.setComposite(ac);
		
		gBuffer.drawImage(bi,-size/2,-size/2,size,size,null);
		
		asMove.setToIdentity();
		gBuffer.setTransform(asMove);
	}
	
	public Area exitArea(){
		
		Area exitArea;
		AffineTransform moveArea = new AffineTransform();
		moveArea.translate(x, y);
		exitArea = new Area(moveArea.createTransformedShape(new Rectangle(-size/2,-size/4,size,size/2)));
		return exitArea;
		
	}
	
}
