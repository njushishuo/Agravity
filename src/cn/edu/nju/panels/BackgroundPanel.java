package cn.edu.nju.panels;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;




import cn.edu.nju.publicTools.GameColor;
import cn.edu.nju.publicTools.SixPointStar;
import cn.edu.nju.publicTools.Square;
import cn.edu.nju.publicTools.Triangle;



/**
 *  
 *   ���๦����ʵ�ֱ�����������Ʈ������ת��Ч����                                                                                
 *     ������Ҫ�˱�����panel�̳������ɡ�
 *             
 *  
 */
public class BackgroundPanel extends SuperMission{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//�����ǵ��б�Ҫ���ϱ���/ɾ�������Բ���LinkedList
    public LinkedList<SixPointStar> sixPointList = new LinkedList<SixPointStar>();
    public LinkedList<Triangle> triangleList = new LinkedList<Triangle>();
    public LinkedList<Square> squareList = new LinkedList<Square>();
	
	int framesCounter = 0; //����һ��֡����¼��
	
	public BackgroundPanel(){
		super();
	}
	
	public void paintIBuffer() throws IOException{
		super.coordinateTrans();

		paintBackground();
		
		}
	
	public void paintComponent(Graphics g){
		try {
			paintIBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(iBuffer, 0, 0, null);
	}
	
	
	public void paintBackground(){	
		
		 AlphaComposite ac;
		
		//ÿ��0.25�룬����������0��2��
		if(framesCounter==10){
			int amountToAdd = (int)(Math.random()*3); //���ӵ�����
			for(int i =0;i<amountToAdd;i++){
				sixPointList.add(new SixPointStar());
			}
			framesCounter=0;
		}
		
//����һ���б�����ÿ����
		for(int i=0;i<sixPointList.size();i++){
		Area sixPoint = sixPointList.get(i).update();
		
         ac= AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)sixPointList.get(i).alpha);
	     gBuffer.setComposite(ac);
	     
	     gBuffer.setColor(GameColor.lightColors[8]);
	     gBuffer.fill(sixPoint);
	     
	     
		}

		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
		gBuffer.setComposite(ac);
//�ٱ���һ�� ɾ��alpha��͸���ȣ�Ϊ0��ȫ͸�����Ķ���
		for(int i=0;i<sixPointList.size();i++){
			if(sixPointList.get(i).alpha==0){
			    	   sixPointList.remove(i);
		   }
			
		}
		
		framesCounter++;
	
		
	}
	
	
	
	public void paintBackground(Color starColor){	
		
		 AlphaComposite ac;
		
		//ÿ��0.25�룬����������0��2��
		if(framesCounter==10){
			int amountToAdd = (int)(Math.random()*5); //���ӵ�����
			for(int i =0;i<amountToAdd;i++){
				sixPointList.add(new SixPointStar());
			}
			framesCounter=0;
		}
		
//����һ���б�����ÿ����
		for(int i=0;i<sixPointList.size();i++){
		Area sixPoint = sixPointList.get(i).update();
		
        ac= AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)sixPointList.get(i).alpha);
	     gBuffer.setComposite(ac);
	     
	     gBuffer.setColor(starColor);
	     gBuffer.fill(sixPoint);
	     
	     
		}

		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
		gBuffer.setComposite(ac);
//�ٱ���һ�� ɾ��alpha��͸���ȣ�Ϊ0��ȫ͸�����Ķ���
		for(int i=0;i<sixPointList.size();i++){
			if(sixPointList.get(i).alpha==0){
			    	   sixPointList.remove(i);
		   }
			
		}
		
		framesCounter++;
	
		
	}
	
	
	//paintBackgroundBΪ����ת������
	
	public void paintBackgroundB(){
		
		 AlphaComposite ac;
		

		if(framesCounter==10){
			int amountToAdd = 1+(int)(Math.random()*3);
			for(int i =0;i<amountToAdd;i++){
				triangleList.add(new Triangle());
			}
			framesCounter=0;
		}
		

		for(int i=0;i<triangleList.size();i++){
		Area triangle = triangleList.get(i).update();
		
        ac= AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)triangleList.get(i).alpha);
	     gBuffer.setComposite(ac);
	     
	     gBuffer.setColor(GameColor.lightColors[2]);
	     gBuffer.fill(triangle);
	     
	     
		}

		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
		gBuffer.setComposite(ac);

		for(int i=0;i<triangleList.size();i++){
			if(triangleList.get(i).alpha==0){
				triangleList.remove(i);
		   }
			
		}
		
		framesCounter++;
	
		
	}
	
	
	
	//paintBackgroundCΪ������ķ���������ʵҲ����̫�ÿ�=��=
	
	public void paintBackgroundC(){
		
		 AlphaComposite ac;
		

		if(framesCounter==10){
			int amountToAdd = 4+(int)(Math.random()*3);
			for(int i =0;i<amountToAdd;i++){
				squareList.add(new Square());
			}
			framesCounter=0;
		}
		

		for(int i=0;i<squareList.size();i++){
		 
		 Area square = squareList.get(i).update();
		
         ac= AlphaComposite.getInstance(AlphaComposite.SRC_OVER, squareList.get(i).alpha);
	     gBuffer.setComposite(ac);
	     
	     gBuffer.setColor(GameColor.lightColors[2]);
	     gBuffer.fill(square);
	     
	     
		}
		
		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
		gBuffer.setComposite(ac);

		for(int i=0;i<squareList.size();i++){
			if(squareList.get(i).x>1280){
				squareList.remove(i);
		   }
			
		}
		
		framesCounter++;
	
		
	}

	


	
	
	
}
