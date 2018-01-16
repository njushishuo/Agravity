package cn.edu.nju.panels;

import java.awt.Graphics;
import java.io.IOException;
import java.util.LinkedList;

import cn.edu.nju.publicTools.SixPointStar;

public class TransformPanelB extends BackgroundPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	BackgroundPanel panelA;
	BackgroundPanel panelB;
	float framecount=1;
	float frameamount;
	float xPoint;
	
	public TransformPanelB(BackgroundPanel panelA,BackgroundPanel panelB,float frameamount){
		this.panelA=panelA;
		this.panelB=panelB;
		this.frameamount=frameamount;
		panelB.sixPointList=panelA.sixPointList;
		panelA.sixPointList = new LinkedList<SixPointStar>();
	}
	
	public void paintComponent(Graphics g){
		
		xPointChange();
		
		
		try {
			panelA.paintIBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			panelB.paintIBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(panelA.iBuffer,(int)xPoint-panelA.iBuffer.getWidth(),0,null);
		g.drawImage(panelB.iBuffer,(int)xPoint,0,null);
		
	}
	
	public void xPointChange(){
		
		if(framecount==frameamount){
			needChange=true;
		}
		
		if(framecount<=frameamount/2)
		    xPoint = iBuffer.getWidth()- 2*(float) ((iBuffer.getWidth()*Math.pow(framecount/frameamount,2)));
		else
			xPoint = iBuffer.getWidth()/2-2*(float)((iBuffer.getWidth()*Math.pow(framecount/frameamount-0.5f,2)));
		
		framecount++;
	}

}
