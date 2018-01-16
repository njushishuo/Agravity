package cn.edu.nju.components;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import cn.edu.nju.publicTools.GameColor;

public class Next extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image next = new ImageIcon("images/next.png").getImage();
	Boolean mouseContained = false;
	public Boolean mouseClicked = false;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public Next(){
		this.setBounds(screenSize.width-110,5 , 50, 50);
		this.addMouseListener(new NextListener());
		
	}
	public boolean isMouseClicked(){
		return mouseClicked;
	}
	
	public void paintComponent(Graphics g){
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		if(!mouseContained){
			this.setBounds(screenSize.width-110,5 , 50, 50);
		g2d.drawImage(next, -5,0, this.getWidth(),this.getWidth(),null);
		
	
		}
		else {
			this.setBounds(screenSize.width-115, 0, 60, 60);
			g2d.setStroke(new BasicStroke(2.0f));
			g2d.setColor(GameColor.darkColors[1]);
			g2d.drawOval(5, 5, this.getWidth()-10, this.getHeight()-10);
			g2d.drawImage(next, 0, 5, this.getWidth()-10,this.getWidth()-10,null);									
			g2d.setStroke(new BasicStroke(1.0f));
		}
		
		
	}
	
	
	class NextListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			mouseClicked = true;
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
			mouseContained = true;
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			mouseContained = false;
		}

	
	}
}