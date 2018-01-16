package cn.edu.nju.publicTools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bubble {

	File f = new File("bubble.png");
	public BufferedImage bubble;
	public double x,y;
	double vy;
	public double size;
	public float currentAlpha;
	float alpha;
	public int lifespan;
	public int framecount = 1;
	
	public Bubble() throws IOException{
		
		bubble = ImageIO.read(f);
		size = 70 + Math.random()*30;
		x = Math.random()*1280;
		y = 720 + size;
		vy = 3 + Math.random()*4;
		alpha = (float)(0.6 + Math.random()*0.2);
		lifespan = (int)(75 + Math.random()*50);
		
	}
	
	public void update(){
		
		y = y - vy;
		currentAlpha = alpha*(1-((float)framecount/(float)lifespan));
		framecount++;
		
	}
	
}
