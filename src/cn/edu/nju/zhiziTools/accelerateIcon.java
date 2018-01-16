package cn.edu.nju.zhiziTools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import cn.edu.nju.publicTools.GameColor;

public class accelerateIcon {
	//加速带的速度向量由点A(40,0),B(40,20)确定，从B指向A
	GameColor gColor;
	 Shape   acc;
	int []xs={-40,0,40,40,0,-40};
	int []ys={20,0,20,40,20,40};
	private double x =0;
	private double y= 0;
	double cx=1;
	double cy=1; //缩放大小的倍率
	double a=0;      //没有属性意义
	Polygon  aceIcon ;
	Shape rectangle;

	Area partOne;  //构造箭头
	Area partTwo;
	Area partThere;
	
	Area temp;  //构造长条
	Area next;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public accelerateIcon(double inputx,double inputy,double cx,double cy){
		//形成三个加速箭头
		setLocation(inputx,inputy);  //      默认初始位置  
		this.cx =cx;
		this.cy =cy;
		aceIcon= new Polygon(xs,ys,6);	   
      //  aceIcon.translate(0, 0);
        partOne=new Area(aceIcon);
        aceIcon.translate(0, 25);
        partTwo=new Area(aceIcon);
        aceIcon.translate(0, 25);
        partThere=new Area(aceIcon);
        partOne.add(partTwo);
        partOne.add(partThere);
        //形成多个长条
    	   
        Rectangle re = new Rectangle(-40,0,80,3);
        next=new Area(re);
        for(int i=0;i<30;i++){      
        	 re.translate(0,6);	 
             temp=new Area(re);
             next.add(temp);       
        }	  
         
        
	}
	
	public accelerateIcon(){
		//形成三个加速箭头
		setLocation(0,0);  //      默认初始位置  
		aceIcon= new Polygon(xs,ys,6);	   
        aceIcon.translate(0, 0);
        partOne=new Area(aceIcon);
        aceIcon.translate(0, 25);
        partTwo=new Area(aceIcon);
        aceIcon.translate(0, 25);
        partThere=new Area(aceIcon);
        partOne.add(partTwo);
        partOne.add(partThere);
        //形成多个长条
        
        Rectangle re = new Rectangle(-40,0,80,3);
        next=new Area(re);
        for(int i=0;i<30;i++){      
        	 re.translate(0,6);	 
             temp=new Area(re);
             next.add(temp);
             
          }	  
        
	}
	public void setLocation(double x,double y){
		 this.x=x;
		 this.y=y;
	}
	
	public AffineTransform setSize (double cx,double cy ){
	    AffineTransform af = new AffineTransform();
	    af.scale(cx, cy);
	    return af;
	}
	
	public void drawAccelerateIcon(BufferedImage iBuffer){
		   
  		 Graphics2D gBuffer = iBuffer.createGraphics();
		 AffineTransform af =new  AffineTransform();
  		 af.setToScale(screenSize.getWidth()/1280, screenSize.getHeight()/720);
	   	 gBuffer.setTransform(af);
	   	 gBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	     //使加速带  动起来
	   	 if(a<90){
	   		AffineTransform bf =new  AffineTransform();
             bf.setToTranslation(0,-a);
             rectangle = bf.createTransformedShape(next);
             a+=1;
          }else{ 
        	  AffineTransform bf =new  AffineTransform();
              bf.setToTranslation(0,90);
 	        rectangle = af.createTransformedShape(next);	  
 	        a=0;	       
 	   	 }
	   	 
	   	   
	   	  
	       gBuffer.translate(x, y);  //只用执行一次
	       
	       AffineTransform e =new AffineTransform();
	       e.rotate(-Math.PI/2, 0,0);
	   	   AffineTransform   c = setSize(cx,cy);    //缩放大小
	       acc  = e.createTransformedShape(c.createTransformedShape(partOne)) ;
           
          gBuffer.setColor(Color.yellow);        
          gBuffer.fill(acc);  //画出箭头
         
          AffineTransform  b = setSize(cx,cy);
	      Shape rec=  e.createTransformedShape(b.createTransformedShape(rectangle));  //缩放大小

	   
	      

	      
         gBuffer.setColor(gColor.darkColors[0]);  
         gBuffer.fill(rec);  //画出长条
    
        
	}
	
	public Shape getShape() {
		   AffineTransform   c = setSize(cx,cy);    //缩放大小
	       acc  =  c.createTransformedShape(partOne);
		return acc;
	}
}
