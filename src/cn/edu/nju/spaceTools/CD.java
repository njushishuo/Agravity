package cn.edu.nju.spaceTools;
import java.awt.*;
// CD:Cool Down
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import cn.edu.nju.publicTools.GameColor;
import GameMusic.*;


public class CD {
   public static final  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    
	  public  int t;
	    int r;
	    int xcenter;  //圆心坐标
	    int ycenter;
	    int x;   //圆的左上角坐标
	    int y;
	   
	    Shape sector;    //扇形
	    Ellipse2D.Double circle ;
        File f   ;
        BufferedImage img;
        
      public  boolean isCDtime=false;
      public  boolean isContained = false ;
        
        
    public CD(){
    	 r=30;
 	     t=360;	       
 	     xcenter=80;  
 	     ycenter=665;
 	     x=xcenter-r;   //圆的左上角坐标
	     y=ycenter-r;
	     
	     circle = new Ellipse2D.Double(x,y, 2*r, 2*r);
    	 try{
	   	     f= new File("CD.png");     //  路径需要调一下。
             img =ImageIO.read(f);
	   	 }catch (Exception e ){};
	   	 
    }
   
    public  void CDupdate(){  	
         t-=3;
         
         if(t==0){
        	 //music 
           MusicThread musicthread = new MusicThread ();
  	       musicthread.creatMT("cd00", 1); 
  	       musicthread.start();
          t=360;
          isCDtime=false;
         }
         
    }
    
    
    
	public  void paintCD(BufferedImage iBuffer){
		 //准备工作
		 Graphics2D gBuffer = iBuffer.createGraphics();
		 AffineTransform af =new  AffineTransform();
  		 af.setToScale(screenSize.getWidth()/1280, screenSize.getHeight()/720);
	   	 gBuffer.setTransform(af);
	   	 gBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	   	gBuffer.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	   	 //画图片
	   	    
	 	    gBuffer.drawImage(img, x-8,y-7,2*(r+5),2*(r+5),null);

	 	 
	 	  if(isCDtime){
	 		
	 		 sector = new Arc2D.Double(x,y, 2*r, 2*r, -270,t,Arc2D.PIE);
		    AlphaComposite ac= AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.4);
            gBuffer.setComposite(ac);
            gBuffer.setColor(GameColor.lightColors[1]);
            gBuffer.fill(sector);
            CDupdate();
	 	  }
	}

	public Shape getCdArea() {
		// TODO Auto-generated method stub
		return circle;
	}
	  
	

    
}  

