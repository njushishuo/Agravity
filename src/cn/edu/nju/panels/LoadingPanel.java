package cn.edu.nju.panels;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class LoadingPanel extends BackgroundPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean isLoadingOver = false;
	int count =0;
	ArrayList<Image> loading = new ArrayList<Image>();
	
	public LoadingPanel(){
		gBuffer.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		for(int i=1;i<=5;i++){
			loading.add(new ImageIcon("loading/loading"+i+".png").getImage());			
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		
		coordinateTrans();
		paintIBuffer();
		g.drawImage(iBuffer, 0, 0, null);
	
	}
	public void paintIBuffer(){
		if(count==5){
			count =0;
		}
		
		gBuffer.drawImage(loading.get(count), 0, 0, 1280, 720, null);
		count++;
	}
	
	
}
