package cn.edu.nju.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class AnimationPanel extends BackgroundPanel{

	ArrayList<Image> images = new ArrayList<Image>();
	int count=0;
	public AnimationPanel(){
		// TODO Auto-generated constructor stub
		
		for(int i=2;i<=53;i++){
			images.add(new ImageIcon("StartAnimation/"+i+".png").getImage());
		}
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		if(count==52){
			count =0;
		}
		
		super.paintComponent(g);
		g2d.drawImage(images.get(count), 0, 0,this.getWidth(),this.getHeight(),null);
		count++;
	}
	
}
