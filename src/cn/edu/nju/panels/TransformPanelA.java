package cn.edu.nju.panels;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.LinkedList;

import cn.edu.nju.publicTools.GameColor;
import cn.edu.nju.publicTools.SixPointStar;

public class TransformPanelA extends BackgroundPanel {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	BackgroundPanel panelA;
	BackgroundPanel panelB;
    float framecount=1f;
    public float frameamount;
    float alpha;
    
    public TransformPanelA(){
    	super();
    	
    	
    }
    
    public void paintComponent(Graphics g) {
    	Graphics2D g2d = (Graphics2D)g;
    	AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
    	
    	g2d.setColor(GameColor.darkColors[5]);
		g2d.fillRect(0, 0, iBuffer.getWidth(), iBuffer.getHeight());
		//paintBackground();
    	
    	alphaChange();

    	
    	if(framecount<=frameamount/2){
    		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
    		g2d.setComposite(ac);
    		try {
				panelA.paintIBuffer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		g2d.drawImage(panelA.iBuffer, 0,0,null);
    	}
    	else{
    		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
    		g2d.setComposite(ac);
    		try {
				panelB.paintIBuffer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		g2d.drawImage(panelB.iBuffer, 0,0,null);
    	}
    	
    }
    
    
    public void setPanel(BackgroundPanel a,BackgroundPanel b){
    	this.panelA=a;
    	this.panelB=b;
    	needChange = false;
    	framecount=1f;
    	frameamount = 18f;
    	alpha =1f;
    }
    
    
    public void alphaChange(){
    	
    	if(framecount==frameamount){
    		changeTo=panelA.changeTo;
    		needChange=true;
    	}
    	
    	alpha = 4*((framecount-frameamount/2)*(framecount-frameamount/2))/(frameamount*frameamount);
    	
    	if(alpha>1f){
    		alpha = 0.99f;
    	}else if(alpha <0.01){
    		alpha = 0.001f;
    	}
    	
    	framecount  = framecount +1f;
    }
    
    public void coordinateTrans() {
		
	}
    
}