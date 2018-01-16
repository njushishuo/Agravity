package cn.edu.nju.publicTools;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;


/**
 *  
 * �������õ��������ε��ࡣ����Ϊ�����������ǣ�����������ת�Ƕ��Լ�λ�ơ�
 */
public class SixPointStar {
	//������ɳ�ʼλ�ã��ٶȣ��ͽ��ٶ�
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
	
	//�����ڲ������ǣ�ͨ��ƽ�ƣ���ת�������µ�������
	private Polygon biggerSixPoint;
	private Polygon smallerSixPoint; 
	private Area sixPoint;
	
	public SixPointStar(){
		int size = 1+(int)(Math.random()*2);
		
		//���ȷ�������ǵĴ�С
		for(int i=0;i<12;i++){
			biggerX[i] = X[i]*size;
			biggerY[i] = Y[i]*size;
			
			smallerX[i] = X[i]*(size-1);
			smallerY[i] = Y[i]*(size-1);
		}
		
		//��������������shapeת��ΪArea�����Ǽ�С��
		biggerSixPoint =new Polygon(biggerX,biggerY,12);
		smallerSixPoint = new Polygon(smallerX,smallerY,12);
	    
		//���ȷ���ǵĳ�ʼλ��
		if(Math.random()>=0.5){
			x = (int)((Math.random())*1280)-20*size;
			y=0;
		}else{
			x = 0;
			y= (int)((Math.random())*720)-20*size;
		}
		
		//���ȷ���ǿ��Ļ���ʵ��
		if(Math.random()>=0.5){
			empty = true;
		}
		
		//������任�����Ƶ���ʼλ��
		asMove.translate(x, y);
	    
	}
	
	
	/**
	 * @return ͼ����º���������һ��ͼ����ͼ���͸��������null
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
