package cn.edu.nju.panels;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.*;

import cn.edu.nju.publicTools.GameColor;
import cn.edu.nju.publicTools.SixPointStar;


public class TransformPanel extends BackgroundPanel {	
	
	static float alpha;
	int framecount = 1;
	int frameamount;
	BackgroundPanel panelA;
	BackgroundPanel panelB;

	
	public TransformPanel(BackgroundPanel panelA,BackgroundPanel panelB,int frameamount){
		this.panelA=panelA;
		this.panelB=panelB;
		this.frameamount=frameamount;
		panelB.sixPointList = panelA.sixPointList;
		this.sixPointList=panelA.sixPointList;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g){
		
		alphaChange(frameamount);

		Graphics2D g2d = (Graphics2D)g;
		
		panelB.gBuffer=iBuffer.createGraphics();
		AlphaComposite ac =AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
		panelB.gBuffer.setComposite(ac);
		gBuffer.setColor(GameColor.darkColors[5]);
		gBuffer.fillRect(0, 0, iBuffer.getWidth(), iBuffer.getHeight());
		paintBackground();
		try {
			panelB.paintIBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//g2d.drawImage(panelB.iBuffer,0,0,null);
		

		panelA.gBuffer=iBuffer.createGraphics();
		ac =AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
		panelA.gBuffer.setComposite(ac);
		try {
			panelA.paintIBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//g2d.drawImage(panelA.iBuffer,0,0,null);
		
		
		g2d.drawImage(iBuffer,0,0,null);
		
		panelB.gBuffer=panelB.iBuffer.createGraphics();
		panelA.gBuffer=panelA.iBuffer.createGraphics();
		
		
	}
	
	public void alphaChange(int frameamount){
		if(framecount==frameamount){
			needChange=true;
		//	changeNum=2;
		}
		alpha=(float)(1-(float)framecount/(float)frameamount);
		framecount++;
	}

	
}
