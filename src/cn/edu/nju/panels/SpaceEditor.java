package cn.edu.nju.panels;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box.Filler;
import javax.swing.ImageIcon;

import cn.edu.nju.panels.SpaceMission3.SpaceLis;
import cn.edu.nju.publicTools.GameColor;
import cn.edu.nju.spaceTools.ExitInSpace;
import cn.edu.nju.spaceTools.ObjectsInSpace;
import cn.edu.nju.spaceTools.PlanetC;
import cn.edu.nju.spaceTools.PlanetD;
import cn.edu.nju.spaceTools.SlowArea;
import cn.edu.nju.spaceTools.SpaceShip;

public class SpaceEditor extends BackgroundPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ArrayList<ObjectsInSpace> objects = new ArrayList<ObjectsInSpace>();
	SpaceMissionDIY spaceMissionDIY;
	
	//area0��4Ϊ��ߵ����ʾ��ͼ
	Area area0 = new Area(new Rectangle(0,0,144,144));
	Area area1 = new Area(new Rectangle(0,144,144,144));
	Area area2 = new Area(new Rectangle(0,288,144,144));
	Area area3 = new Area(new Rectangle(0,432,144,144));
	Area area4 = new Area(new Rectangle(0,576,144,144));
	//area5��8Ϊ�ĸ����ƴ�С�������ΰ�ť
	Area area5 = new Area(new Rectangle(144,192,24,24));
	Area area6 = new Area(new Rectangle(144,216,24,24));
	Area area7 = new Area(new Rectangle(144,336,24,24));
	Area area8 = new Area(new Rectangle(144,360,24,24));
	//��10Ϊ�ұߵ��ĸ���ť���ֱ�Ϊ�˳����������л����������á�ȷ��
	Area area9 = new Area(new Rectangle(1136,0,144,144));
	Area area10 = new Area(new Rectangle(1136,144,144,144));
	Area area11 = new Area(new Rectangle(1136,288,144,144));
	Area area12 = new Area(new Rectangle(1136,432,144,144));
	Area area13 = new Area(new Rectangle(1136,576,144,144));
	//area��
	Area[] areaArray = {area0,area1,area2,area3,area4,area5,area6,area7,area8,area9,area10,area11,area12,area13};
	ArrayList<Boolean> isContained = new ArrayList<Boolean>();
	
	
	//������ƴ�С�İ�ť�Ƿ񱻵��
	boolean sizeCUp = false;
	boolean sizeCDown = false;
	boolean sizeDUp = false;
	boolean sizeDDown = false;
	
	boolean hasStorm = true;
	
	//������קʱ�Ļ�ͼ
	boolean isMove;
	Image temImage;
	int size;
	int save;
	boolean isShipExist = false;
	boolean isExitExist = false;
	
	//��������planet�Ĵ�С
	int planetCSize = 300;
	int planetDSize = 500;
	
	//imageList�洢������ͼ���ֱ��Ӧ���ʾ����������קʱ��ʾ
	ArrayList<Image> imageList = new ArrayList<Image>();
	Image background;
	Image icons;
	
	Point2D p;
	
	
	public SpaceEditor(SpaceMissionDIY spaceMissionDIY){
		
		this.spaceMissionDIY = spaceMissionDIY;
		
		String[] imageNames = {"spaceship.png","planetsC/�ϳ� 1_00000.png","planetsD/�ϳ� 2_00000.png","���ٴ�.png","exit/�ϳ� 1_00000.png"};
		
		for(int i=0;i<5;i++){
				imageList.add(new ImageIcon(imageNames[i]).getImage());
		}
			
		for(int i=0;i<areaArray.length;i++){
			isContained.add(false);
		}
	

			background = new ImageIcon("solidcolor.jpg").getImage();
			icons = new ImageIcon("�༭������.png").getImage();
			
		
		SpaceLis slis = new SpaceLis();
		this.addMouseListener(slis);
		this.addMouseMotionListener(slis);
		
	}
	
	
	public void paintIBuffer(){
		
		coordinateTrans();
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f);
		AlphaComposite ac2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.2f);
		gBuffer.setComposite(ac);
		gBuffer.drawImage(background,0,0,1280,720,null);
		
		
		if(!hasStorm){
		paintBackground();
		}else{
			paintBackgroundC();
		}
		
		
		gBuffer.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		gBuffer.drawImage(icons,0,0,1280,720,null);
		
		gBuffer.setComposite(ac2);
		for(int i=0;i<areaArray.length;i++){
			gBuffer.setColor(Color.GRAY);			
			
			if(isContained.get(i)){
				gBuffer.fill(areaArray[i]);
			}
			
		}
		gBuffer.setComposite(ac);
		gBuffer.setColor(GameColor.lightColors[8]);		
		
		
		
	    //�ж��Ƿ����˸ı��С�İ�ť�����иı�
		if(sizeCUp&&planetCSize<=600){
	    	planetCSize = planetCSize + 50;
	    	sizeCUp = false;
	    }
	    if(sizeDUp&&planetDSize<=1000){
	    	planetDSize = planetDSize + 50;
	    	sizeDUp = false;
	    }
	    if(sizeCDown&&planetCSize>=300){
	    	planetCSize = planetCSize - 50;
	    	sizeCDown = false;
	    }
	    if(sizeDDown&&planetDSize>=500){
	    	planetDSize = planetDSize - 50;
	    	sizeDDown = false;
	    }
	    
	    //��ʾ��С
	    String sizeC = Integer.toString(planetCSize);
	    String sizeD = Integer.toString(planetDSize);
	    gBuffer.setFont(new Font("����������",Font.ITALIC,28));
	    gBuffer.drawString(sizeC, 84, 262);
	    gBuffer.drawString(sizeD, 84, 410);
	    
	    //��קʱ��ͼ
	    if(isMove){
	    	double x = p.getX();
	    	double y = p.getY();
	    	gBuffer.drawImage(temImage,(int)(x-size/2),(int)(y-size/2),size,size,null);
	    }
	    
	    //�����Ѿ����ڵ�����
	    for(int i=0;i<objects.size();i++)
	    	objects.get(i).draw(iBuffer);
		
	}
	
	public void paintComponent(Graphics g){
		
		paintIBuffer();
		g.drawImage(iBuffer, 0, 0, null);
		
	}
	
	public void saveObjects() {
		File save = new File("saveFile");
		ObjectOutputStream saveStream;
		try {
			saveStream = new ObjectOutputStream(new FileOutputStream(save));
			saveStream.writeObject(objects);  
			saveStream.writeObject(hasStorm);			
		    saveStream.close();  		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	class SpaceLis extends PanelLis{
		
		public void mouseDragged(MouseEvent e) {
			
			p = e.getPoint();
			p = coordinateTrans((Point) p);
			
		}


		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			Point point = e.getPoint();
			point = coordinateTrans(point);
			for(int i=0;i<isContained.size();i++){
				if(areaArray[i].contains(point)){
					isContained.set(i, true);
				}else{
					isContained.set(i, false);
				}
				
				
			}
			
		}


		@Override
		public void mouseClicked(MouseEvent e) {
			
			p = e.getPoint();
			p = coordinateTrans((Point) p);
			
			if(isContained.get(5))
				sizeCUp = true;
			if(isContained.get(6))
				sizeCDown = true;
			if(isContained.get(7))
				sizeDUp = true;
			if(isContained.get(8))
				sizeDDown = true;
			
			if(isContained.get(9)){
				needChange = true;
				changeTo ="startPanel";
			}
			
			if(isContained.get(10)){
				ObjectsInSpace ob = objects.get(objects.size()-1);
				if(ob.getIdentity()==1){
					isShipExist = false;
				}else if(ob.getIdentity()==3){
					isExitExist = false;
				}
				
				objects.remove(objects.size()-1);
			}
			
			if(isContained.get(11)){				
			hasStorm = !hasStorm;	
			}
			
			if(isContained.get(12)){
				init();
			}
			
			//���ȷ����
			if(isContained.get(13)){
				
				//���ɴ����õ���һ��
				int i=0;
				for(i=0;i<objects.size();i++){
					if(objects.get(i).getIdentity()==1){
						break;
					}
				}
				if(i!=0&&i<objects.size()){
					ObjectsInSpace ob = objects.get(i);
					objects.set(i, objects.get(0));
					objects.set(0, ob);
				}
				
				saveObjects();
				spaceMissionDIY.readSave();
				
				needChange = true;
				changeTo="startPanel";
			}
			
			
		}


		@Override
		public void mousePressed(MouseEvent e) {
			
			p = e.getPoint();
			p = coordinateTrans((Point) p);
			
			for(int i=0;i<5;i++){
				if(areaArray[i].contains(p)){
					if(i==0&&!isShipExist){
						isMove = true;
						temImage = imageList.get(i);
						size = 100;
						save = 0;
						isShipExist = true;
					}
					if(i==3){
						isMove = true;
						temImage = imageList.get(i);
						size = 200;
						save = 3;
					}
					if(i==4&&!isExitExist){
						isMove = true;
						temImage = imageList.get(i);
						size = 100;
						save = 4;
						isExitExist = true;
					}
					if(i==1){
						isMove = true;
						temImage = imageList.get(i);
						size = planetCSize;
						save = 1;
					}
					if(i==2){
						isMove = true;
						temImage = imageList.get(i);
						size = planetDSize;
						save = 2;
					}
					break;
				}
			}
			
		}


		@Override
		public void mouseReleased(MouseEvent e) {
			
			if(isMove){
			    isMove = false;
			    p = e.getPoint();
			    p = coordinateTrans((Point) p);
			    double x = p.getX();
			    double y = p.getY();
			
			    switch(save){
			    case 0:objects.add(new SpaceShip(x,y));break;
			    case 1:objects.add(new PlanetC(x,y,planetCSize,0,0,planetCSize/5));break;
			    case 2:objects.add(new PlanetD(x,y,planetDSize,0,0,planetDSize/5));break;
			    case 3:objects.add(new SlowArea(x,y));break;
			    case 4:objects.add(new ExitInSpace(x,y,100));break;
			    }
			    
			    temImage = null;
			}
			
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
	
	public void init(){
		super.init();
		objects = new ArrayList<ObjectsInSpace>();
		isShipExist = false;
		isExitExist = false;
		planetCSize = 300;
		planetDSize = 500;
		
		for(int i=0;i<isContained.size();i++){			
				isContained.set(i, false);						
		}
		
		
	}

}
