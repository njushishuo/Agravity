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
	//���ٴ����ٶ������ɵ�A(40,0),B(40,20)ȷ������Bָ��A
	GameColor gColor;
	 Shape   acc;
	int []xs={-40,0,40,40,0,-40};
	int []ys={20,0,20,40,20,40};
	private double x =0;
	private double y= 0;
	double cx=1;
	double cy=1; //���Ŵ�С�ı���
	double a=0;      //û����������
	Polygon  aceIcon ;
	Shape rectangle;

	Area partOne;  //�����ͷ
	Area partTwo;
	Area partThere;
	
	Area temp;  //���쳤��
	Area next;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public accelerateIcon(double inputx,double inputy,double cx,double cy){
		//�γ��������ټ�ͷ
		setLocation(inputx,inputy);  //      Ĭ�ϳ�ʼλ��  
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
        //�γɶ������
    	   
        Rectangle re = new Rectangle(-40,0,80,3);
        next=new Area(re);
        for(int i=0;i<30;i++){      
        	 re.translate(0,6);	 
             temp=new Area(re);
             next.add(temp);       
        }	  
         
        
	}
	
	public accelerateIcon(){
		//�γ��������ټ�ͷ
		setLocation(0,0);  //      Ĭ�ϳ�ʼλ��  
		aceIcon= new Polygon(xs,ys,6);	   
        aceIcon.translate(0, 0);
        partOne=new Area(aceIcon);
        aceIcon.translate(0, 25);
        partTwo=new Area(aceIcon);
        aceIcon.translate(0, 25);
        partThere=new Area(aceIcon);
        partOne.add(partTwo);
        partOne.add(partThere);
        //�γɶ������
        
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
	     //ʹ���ٴ�  ������
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
	   	 
	   	   
	   	  
	       gBuffer.translate(x, y);  //ֻ��ִ��һ��
	       
	       AffineTransform e =new AffineTransform();
	       e.rotate(-Math.PI/2, 0,0);
	   	   AffineTransform   c = setSize(cx,cy);    //���Ŵ�С
	       acc  = e.createTransformedShape(c.createTransformedShape(partOne)) ;
           
          gBuffer.setColor(Color.yellow);        
          gBuffer.fill(acc);  //������ͷ
         
          AffineTransform  b = setSize(cx,cy);
	      Shape rec=  e.createTransformedShape(b.createTransformedShape(rectangle));  //���Ŵ�С

	   
	      

	      
         gBuffer.setColor(gColor.darkColors[0]);  
         gBuffer.fill(rec);  //��������
    
        
	}
	
	public Shape getShape() {
		   AffineTransform   c = setSize(cx,cy);    //���Ŵ�С
	       acc  =  c.createTransformedShape(partOne);
		return acc;
	}
}
