package cn.edu.nju.publicTools;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;


/**
 *  
 * 背景中用到的六角形的类。功能为，生成六角星，计算它的旋转角度以及位移。
 */
public class SixPointStar {
	//随机生成初始位置，速度，和角速度
	int[] X = {0,3,10,7,10,3,0,-3,-10,-7,-10,-3};
	int[] Y = {12,6,6,0,-6,-6,-12,-6,-6,0,6,6};
	int[] biggerX = new int[12];
	int[] biggerY = new int[12];
	int[] smallerX= new int[12];
	int[] smallerY= new int[12];
	int vx = 2+(int)(Math.random()*7);
	int vy = 2+(int)(Math.random()*7);
	int x;
    int y;
	double angleSpeed = (Math.PI/50.0)*Math.random();
	public float alpha =0.6f;
	float alphaSpeed = (float)(0.003+Math.random()*0.012);
	boolean empty;
	
	AffineTransform asRotate = new AffineTransform();
	AffineTransform asMove = new AffineTransform();
	
	//两个内部六角星，通过平移，旋转，生成新的六角星
	private Polygon biggerSixPoint;
	private Polygon smallerSixPoint; 
	private Area sixPoint;
	
	public SixPointStar(){
		int size = 1+(int)(Math.random()*2);
		
		//随机确定六角星的大小
		for(int i=0;i<12;i++){
			biggerX[i] = X[i]*size;
			biggerY[i] = Y[i]*size;
			
			smallerX[i] = X[i]*(size-1);
			smallerY[i] = Y[i]*(size-1);
		}
		
		//接下来将六角星shape转化为Area，大星减小星
		biggerSixPoint =new Polygon(biggerX,biggerY,12);
		smallerSixPoint = new Polygon(smallerX,smallerY,12);
	    
		//随机确定星的初始位置
		if(Math.random()>=0.5){
			x = (int)((Math.random())*1280)-20*size;
			y=0;
		}else{
			x = 0;
			y= (int)((Math.random())*720)-20*size;
		}
		
		//随机确定是空心还是实心
		if(Math.random()>=0.5){
			empty = true;
		}
		
		//用坐标变换将星移到初始位置
		asMove.translate(x, y);
	    
	}
	
	
	/**
	 * @return 图像更新函数，返回一个图像，则图像变透明，返回null
	 */
	public Area update(){
				
    	 alpha = alpha-alphaSpeed;
		if(alpha<0){
			alpha=0;
		}
		
		
		asMove.translate(vx, vy);
		asRotate.rotate(angleSpeed);
		
		Shape temShape = asMove.createTransformedShape(asRotate.createTransformedShape(biggerSixPoint));
		Shape temShape2 =asMove.createTransformedShape(asRotate.createTransformedShape(smallerSixPoint));
		
		sixPoint = new Area(temShape);
		
		if(empty){
	    sixPoint.subtract(new Area(temShape2));
		}
		
		return sixPoint;
	}
	
	
	
	
	

}
