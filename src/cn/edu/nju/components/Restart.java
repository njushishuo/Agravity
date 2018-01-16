package cn.edu.nju.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import cn.edu.nju.publicTools.GameColor;

/**
 * @关于退出键的功能，动画效果封装。
 *
 */
public class Restart extends JLabel{

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		Image exitImage = new ImageIcon("images/restart.png").getImage();
		Boolean mouseContained = false;
		Boolean mouseClicked = false;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public Restart(){
		
		this.setBounds(10, 20, 50, 50);
		this.addMouseListener(new ExitListener());
	}
	
	public boolean isMouseClicked(){
		return mouseClicked;
	}
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		 
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		if(!mouseContained){
			this.setBounds(10, 20, 50, 50);
		g2d.drawImage(exitImage, -5,0, this.getWidth(),this.getWidth(),null);
		
	
		}
		else {
			this.setBounds(5, 10, 60, 60);
			g2d.setStroke(new BasicStroke(2.0f));
			g2d.setColor(GameColor.darkColors[1]);
			g2d.drawOval(5, 5, this.getWidth()-10, this.getHeight()-10);
			g2d.drawImage(exitImage, 0, 5, this.getWidth()-10,this.getWidth()-10,null);									
			g2d.setStroke(new BasicStroke(1.0f));
		}
		
	
	}
	
	class ExitListener implements MouseListener{

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
