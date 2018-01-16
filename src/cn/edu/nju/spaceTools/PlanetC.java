package cn.edu.nju.spaceTools;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class PlanetC extends Planet {
	
	double omegaspeed = 0.015;
	transient static ArrayList<Image> PLANETC_LIST = new ArrayList<Image>();
	
	public PlanetC(double x,double y,int size,double v,double radius,double gravitycontrast){
		
		centerx = x;
		centery = y;
		this.v = v;
		this.size = size;
		this.radius = radius;
		identity = 2;
		this.gravitycontrast = gravitycontrast;
		
		update();
		
		gravitysize = size * 0.8;
		planetsize = size * 0.3;
		
        for(int i = 0; i<50; i++){
			
			String s = Integer.toString(i);
			String filename;
			
			if(i<10)
			filename = "合成 1_0000"+s+".png";
			else
				filename = "合成 1_000"+s+".png";
			
			filelist.add("planetsC/"+filename);
			
		    }
		
        
        if(PLANETC_LIST.isEmpty()){
        try {
			for(int i=0;i<filelist.size();i++){
			//	imageList.add(ImageIO.read(filelist.get(i)));
				Image image = new ImageIcon(filelist.get(i)).getImage();
				
				PLANETC_LIST.add(image);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
        }
	}
	
	public void draw(BufferedImage iBuffer){
		
		if(count==49)
			count = 0;
		else
			count++;
		
		Image bi = PLANETC_LIST.get(count);
		gBuffer = iBuffer.createGraphics();
		update();
		
		asMove.translate(x, y);
		asRotate.rotate(omegaspeed);
		coordinateTrans();
		gBuffer.transform(asMove);
		gBuffer.transform(asRotate);
		
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
		gBuffer.setComposite(ac);
		gBuffer.drawImage(bi,-size/2,-size/2,size,size,null);
		
		asMove.setToIdentity();
		gBuffer.setTransform(asMove);
		
	}
	
	public void update(){
		
		t = (t + v)%(2*Math.PI);
		x = centerx + radius*Math.cos(t);
		y = centery + radius*Math.sin(t);
		
	}
	
}

