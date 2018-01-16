package cn.edu.nju.panels;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;

import cn.edu.nju.panels.SuperMission.PanelLis;

public class StartPanel extends BackgroundPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image image = new ImageIcon("images/main panel.jpg").getImage();
	Image textImage = new ImageIcon("images/main panel text.png").getImage();
	ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
	ArrayList<Boolean> isContained = new ArrayList<Boolean>();
	ArrayList<String> panelName = new ArrayList<String>();
	
	public StartPanel() {
		// TODO Auto-generated constructor stub
		gBuffer.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		
		rectangles.add(new Rectangle(295,240,615,110));
		rectangles.add(new Rectangle(295,410,615,110));
		rectangles.add(new Rectangle(295,585,625,110));
		rectangles.add(new Rectangle(1170,610,95,95));
		rectangles.add(new Rectangle(1182,12,90,80));
		
		panelName.add("dropChoosePanel");		
		panelName.add("spaceChoosePanel");
		panelName.add("zhiziChoosePanel");
		//·É´¬¹Ø±à¼­Æ÷
		panelName.add("spaceEditor");
		
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
		for(int i=0;i<rectangles.size();i++){
			gBuffer.setColor(Color.GRAY);
			if(i==rectangles.size()-1){
				gBuffer.setColor(Color.RED);
			}
			
			if(isContained.get(i)){
				gBuffer.fill(rectangles.get(i));
			}
			
		}
		
		paintBackgroundB();
		
		gBuffer.drawImage(textImage, 0, 0, 1280, 720, null);
		
		gBuffer.setComposite(ac2);
		
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
					  
					  for(int j=0;j<isContained.size();j++){
							isContained.set(j, false);
						}
					  
				  }
				  
			  }
		
			  if(isContained.get(isContained.size()-1)){
				  System.exit(0);				  
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
