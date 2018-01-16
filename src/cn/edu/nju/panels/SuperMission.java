package cn.edu.nju.panels;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;


/**
 * 
 *  ����������Ϸ�����ĸ��࣬��������ԭ���ǣ�ÿһ�����涼��һ��panel��
 *  ��Щpanel����֮ͬ����˫���壨��ֹ������˸��������ϵת����ʵ�����ţ���Ӧȫ����
 *  ��֪ͨ��ѭ���й�����л�����Ϣ��
 *  Ҳ��һЩ��Ҫ�õ������ݣ���x��y���꣬�����.
 */

public abstract class SuperMission extends JPanel{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	  int compute_x =0;
	  int compute_y =0;
	  public int compute_width =1280;
	  public int compute_height =720;
	  public int paint_x =0;
	  public int paint_y =0;
	  public int paint_width =1280;
	  public int paint_height = 720;
	  
	  public boolean needChange = false;
	  public String changeTo;
	
	  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	  //Ϊ����Ӧȫ���ﵽ�ı���
	  public double[] fullScreenZoomRate = new double[2];
	  public PanelLis lis = new PanelLis();
		
		//
		public boolean resizable = false;
	  
	   BufferedImage iBuffer = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_4BYTE_ABGR);
       Graphics2D gBuffer = iBuffer.createGraphics();  
// ˫����
      AffineTransform transform = new AffineTransform();
   	
      public SuperMission(){
    	  this.setLayout(null);   	  
    	  fullScreenZoomRate[0] = screenSize.getWidth()/compute_width;
    	  fullScreenZoomRate[1] = screenSize.getHeight()/compute_height;   	 
    	  //���ÿ����
    	  gBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	//  gBuffer.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    	  this.addMouseListener(lis);
    	  this.addMouseMotionListener(lis);
    	  coordinateTrans();
      }
      
      public void coordinateTrans(){
    	  transform.setToIdentity();
    	  double rate = ((double)paint_width)/(double)compute_width;
    	  transform.scale(rate, rate);
    	  transform.scale(fullScreenZoomRate[0], fullScreenZoomRate[1]);
    	  gBuffer.setTransform(transform);
    	 
    	 
    
      }
      
      public Point coordinateTrans(Point point){
    	  double formerX =  point.getX();
    	  double formerY = point.getY();
    	  
    	  Point point2 = new Point();
    	  point2.setLocation(formerX/fullScreenZoomRate[0], formerY/fullScreenZoomRate[1]);   	 	 
    	  return point2;
      }
      
      public void paintIBuffer(BufferedImage iBuffer){
    	  
      }

      public void init() {
		   	  needChange = false;
	}
      
      /**
       * @author ShenBin ��������Ϊʵ����ק�ļ�����Ҫ��Ӹ��๦����̳д��ڲ��࣬Ȼ�����ط���
       *
       */
		class PanelLis implements MouseListener,MouseMotionListener{
			Point dragMouse = new Point(0,0);  //������ק��mouse
	        int old_x = 0;
	        int old_y = 0;  //��ק�õ���ʼ����
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			// �˴�Ϊ��ק��ʵ�֣����е��ж������Ϊ�˷�ֹ�����ק����
			dragPanel(e);
		}


		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			setStartPoint(e);
		}


		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		private void setStartPoint(MouseEvent e){
			dragMouse = e.getPoint();
			old_x = paint_x;
			old_y = paint_y;
		}
		
		private void dragPanel(MouseEvent e){
			Point afterMouse = e.getPoint();
			
			int deltaX = (afterMouse.x+paint_x)-(dragMouse.x+old_x);
			int deltaY = (afterMouse.y+paint_y)-(dragMouse.y+old_y);
			
			if(old_x+deltaX<0&&old_x+deltaX+(int)(fullScreenZoomRate[0]*paint_width)>screenSize.getWidth()){
				
				
				paint_x = old_x+deltaX;
				
			}
			if(old_x+deltaY<0&&old_y+deltaY+(int)(fullScreenZoomRate[1]*paint_height)>screenSize.getHeight()){
				paint_y = old_y+deltaY;
			}
		}
		
		}
      
      
    	  
}
	
      
      

