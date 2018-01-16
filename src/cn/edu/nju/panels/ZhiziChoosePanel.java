package cn.edu.nju.panels;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import cn.edu.nju.panels.SpaceChoosePanel.StartLis;
import cn.edu.nju.panels.SuperMission.PanelLis;
import cn.edu.nju.publicTools.GameColor;

public class ZhiziChoosePanel extends BackgroundPanel{
	private static final long serialVersionUID = 1L;
	
	Image image = new ImageIcon("images/ѡ�ؽ������ӱ���.jpg").getImage();
	Image textImage = new ImageIcon("images/ѡ�ؽ�������.png").getImage();
	
	ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
	ArrayList<Boolean> isContained = new ArrayList<Boolean>();
	ArrayList<String> panelName = new ArrayList<String>();
	
	public ZhiziChoosePanel() {
		// TODO Auto-generated constructor stub
		gBuffer.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		
		//����
		rectangles.add(new Rectangle(25,24,25,208));
		rectangles.add(new Rectangle(25,256,25,208));
		rectangles.add(new Rectangle(25,488,25,208));
		
		//ѡ�ؼ�
		rectangles.add(new Rectangle(415,135,200,200));
		rectangles.add(new Rectangle(665,135,200,200));
		rectangles.add(new Rectangle(415,385,200,200));
		rectangles.add(new Rectangle(665,385,200,200));
		
		//�����������
		rectangles.add(new Rectangle(1182,12,90,80));
		
		panelName.add("dropChoosePanel");
		panelName.add("spaceChoosePanel");
		panelName.add("zhiziChoosePanel");
		
		panelName.add("zhiziTest1");		
		panelName.add("zhiziTest2");
		panelName.add("zhiziTest3");
		panelName.add("zhiziTest4");
		
		panelName.add("startPanel");
		
		for(int i=0;i<rectangles.size();i++){
			isContained.add(new Boolean(false));
			
		}
		
		StartLis lis = new StartLis();
		this.addMouseMotionListener(lis);		
		this.addMouseListener(lis);
	}
	

	
	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		
		coordinateTrans();
		paintIBuffer();
		g.drawImage(iBuffer, 0, 0,null);
	}
	
	public void paintIBuffer() {
		gBuffer.drawImage(image, 0, 0,1280,720, null);
		
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
		AlphaComposite ac2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
		
		gBuffer.setComposite(ac);
		gBuffer.setColor(Color.RED);

		
		paintBackground(GameColor.lightColors[2]);
	
		gBuffer.setComposite(ac2);
		gBuffer.drawImage(textImage, 0, 0, 1280, 720, null);
		

		
	}
	
	
	
	
	class StartLis extends PanelLis{
		
		
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}



		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			Point p = e.getPoint();
			p = coordinateTrans(p);
			
			for(int i=0;i<rectangles.size();i++){
				if(rectangles.get(i).contains(p)){
					isContained.set(i, true);				
				}else{
					isContained.set(i, false);	
				}
			}
			
		}



		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			for(int i=0;i<panelName.size();i++){
				  
				  if(isContained.get(i)){
						
					  needChange =true;
					  changeTo = panelName.get(i);
					  
				  }
				  
			  }
			  
		}



		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		
		
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
		

			
	}
	
}
