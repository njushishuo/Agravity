package cn.edu.nju.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import cn.edu.nju.publicTools.DrawParticle;
import cn.edu.nju.publicTools.Emitter;
import cn.edu.nju.publicTools.GameColor;

public class SolidPanel extends BackgroundPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DrawParticle drawparticle = new DrawParticle();
	
	public SolidPanel(){
		
		drawparticle.emitterlist.add(new Emitter(500,500));
		
	}

	public void paintIBuffer(){
		gBuffer.setColor(GameColor.darkColors[5]);
		gBuffer.fillRect(0, 0, iBuffer.getWidth(), iBuffer.getHeight());
		paintBackground();
		drawparticle.draw(iBuffer);
	}
	
	public void paintComponent(Graphics g){
		paintIBuffer();
		g.drawImage(iBuffer, 0, 0, null);
	}
	
	

}
