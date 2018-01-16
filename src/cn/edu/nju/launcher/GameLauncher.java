package cn.edu.nju.launcher;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;

import GameMusic.MusicThread;
import cn.edu.nju.panels.AnimationPanel;
import cn.edu.nju.panels.BackgroundPanel;
import cn.edu.nju.panels.DropChoosePanel;
import cn.edu.nju.panels.DropMission1;
import cn.edu.nju.panels.DropMission2;
import cn.edu.nju.panels.DropMission3;
import cn.edu.nju.panels.DropMission4;
import cn.edu.nju.panels.LoadingPanel;
import cn.edu.nju.panels.SolidPanel;
import cn.edu.nju.panels.SpaceChoosePanel;
import cn.edu.nju.panels.SpaceEditor;
import cn.edu.nju.panels.SpaceMission1;
import cn.edu.nju.panels.SpaceMission2;
import cn.edu.nju.panels.SpaceMission3;
import cn.edu.nju.panels.SpaceMissionDIY;
import cn.edu.nju.panels.StartPanel;
import cn.edu.nju.panels.SuperMission;
import cn.edu.nju.panels.TransformPanelA;
import cn.edu.nju.panels.TransformPanelB;
import cn.edu.nju.panels.ZhiziChoosePanel;
import cn.edu.nju.panels.ZhiziTest1;
import cn.edu.nju.panels.ZhiziTest1;
import cn.edu.nju.panels.ZhiziTest2;
import cn.edu.nju.panels.ZhiziTest3;
import cn.edu.nju.panels.ZhiziTest4;





/**
 * 
 * 游戏主循环所在类，主要职责为进行游戏主循环，实现面板间切换，以及实现对缩放，拖拽的监听
（由于frame在该类中，而缩放、拖拽必须用frame的坐标系，所以将缩放监听放于此处）
 */


public class GameLauncher {
	JFrame frame = new JFrame();
	BackgroundPanel currentPanel;
	zoomControl zoom = new zoomControl();
	WheelLis lis =  new WheelLis();
	double[]  zoomStat = {1,1,0};  //与缩放有关的数据，[0] 表示当前倍率 [1]表示期望倍率 [2]表示缩放速度
	Point mouse = new Point(0,0);
	int rollTimes =0; //鼠标滚动次数
	MusicThread mt1=new MusicThread();
	MusicThread mt2=new MusicThread();
	MusicThread mt3=new MusicThread();
	MusicThread mt4=new MusicThread();
	
	
	ArrayList<BackgroundPanel> panelList = new ArrayList<BackgroundPanel>();
	   ArrayList<String> panelName = new ArrayList<String>();
	   LoadingPanel loadingPanel = new LoadingPanel();
	
	@SuppressWarnings("unused")
	public void Launch() throws IOException{
		JPanel cardPanel = new JPanel();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		CardLayout card = new CardLayout();
		cardPanel.setLayout(card);
		frame.setUndecorated(true);
		frame.setSize(screenSize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().add(cardPanel);
		cardPanel.setSize(screenSize);
		
		cardPanel.addMouseWheelListener(lis);
		frame.setLocationRelativeTo(null);	
		
		
		
		loadingPanel.setBounds(0, 0, screenSize.width, screenSize.height);		
		frame.getContentPane().add(loadingPanel);
		loading();
		
			//music
		   mt1.creatMT("bgm01",1);
			
		   mt2.creatMT("bgm02", 1);
		
		   mt3.creatMT("bgm00", 1);
		   
		   mt4.creatMT("start00", 1);
	    
	    
	    StartPanel startPanel = new StartPanel();
	    TransformPanelA transformPanelA= new TransformPanelA();
        //水滴四小关
	    DropMission1 dropMission1 = new DropMission1();
        DropMission2 dropMission2 = new DropMission2();
	    DropMission3 dropMission3 = new DropMission3();
	    DropMission4 dropMission4 = new DropMission4();
		//智子四小关
	    ZhiziTest1 zhiziTest1 = new ZhiziTest1();
	    ZhiziTest2 zhiziTest2 = new ZhiziTest2();
	    ZhiziTest3 zhiziTest3 = new ZhiziTest3();
	    ZhiziTest4 zhiziTest4 = new ZhiziTest4();
	    //飞船关
	    SpaceMission1 spaceMission1 = new SpaceMission1();
	    SpaceMission2 spaceMission2 = new SpaceMission2();
	    SpaceMission3 spaceMission3 = new SpaceMission3();
	    SpaceMissionDIY spaceMissionDIY = new SpaceMissionDIY();
	    //选关界面
	    DropChoosePanel dropChoosePanel = new DropChoosePanel();
	    SpaceChoosePanel spaceChoosePanel = new SpaceChoosePanel();
	    ZhiziChoosePanel zhiziChoosePanel = new ZhiziChoosePanel();
		//地图编辑器
	    SpaceEditor spaceEditor = new SpaceEditor(spaceMissionDIY);
	    //
	    
	    
	    currentPanel = startPanel;
	    
	    panelList.add(startPanel);
	    panelList.add(transformPanelA);
	  
	    panelList.add(dropMission1);
	    panelList.add(dropMission2);
	    panelList.add(dropMission3);
	    panelList.add(dropMission4);
	    
	    panelList.add(zhiziTest1);
	    panelList.add(zhiziTest2);
	    panelList.add(zhiziTest3);
	    panelList.add(zhiziTest4);
	    
	    panelList.add(spaceMission1);
	    panelList.add(spaceMission2);
	    panelList.add(spaceMission3);
	    panelList.add(spaceMissionDIY);
	    
	    panelList.add(dropChoosePanel);
	    panelList.add(zhiziChoosePanel);
	    panelList.add(spaceChoosePanel);
	    
	    panelList.add(spaceEditor);
	    
	    panelName.add("startPanel");
	    panelName.add("transformPanelA");
	    
	    panelName.add("dropMission1");
	    panelName.add("dropMission2");
	    panelName.add("dropMission3");
	    panelName.add("dropMission4");
	   
	    panelName.add("zhiziTest1");
	    panelName.add("zhiziTest2");
	    panelName.add("zhiziTest3");
	    panelName.add("zhiziTest4");
	    
	    panelName.add("spaceMission1");
	    panelName.add("spaceMission2");
	    panelName.add("spaceMission3");
	    panelName.add("spaceMissionDIY");
	    
	    panelName.add("dropChoosePanel");
	    panelName.add("zhiziChoosePanel");
	    panelName.add("spaceChoosePanel");
	    
	    panelName.add("spaceEditor");
	    
	    //将面板们加到cardLayout中
	    for(int i=0;i<panelList.size();i++){	    	
	    	cardPanel.add(panelName.get(i), panelList.get(i));	    	
	    }
	    

    
		loadingPanel.isLoadingOver =true;
		
//		  MusicThread musicthread = new MusicThread ();
//		  musicthread.creatMT("bgm01", 5);
		
		while(true){
			long before = Calendar.getInstance().getTimeInMillis();
					
			/*因为全屏要适应不同屏幕大小，所以缩放之后需重新设定panel位置。
			更加面向对象的写法是将此语句写到panel中。但是那样会造成画面卡顿，所以写到主循环中
		    */			
			 currentPanel.setBounds(currentPanel.paint_x,
					 				currentPanel.paint_y,
					 				(int)(currentPanel.fullScreenZoomRate[0]*currentPanel.paint_width),
					 				(int)(currentPanel.fullScreenZoomRate[1]*currentPanel.paint_height) );
			 
			currentPanel.repaint();			
			//Music
			
			playMusic();
			
			//面板切换机制
			if(currentPanel.needChange==true){
					currentPanel.needChange =false;
				if(currentPanel instanceof TransformPanelA){
					card.show(cardPanel, currentPanel.changeTo);										
					currentPanel = panelList.get(panelName.indexOf(currentPanel.changeTo));									
					
					if(currentPanel instanceof StartPanel){
						init();
					}		
					
				}else{
					card.show(cardPanel, "transformPanelA");			
					
					transformPanelA.setPanel(currentPanel,panelList.get(panelName.indexOf(currentPanel.changeTo)));
					
					currentPanel = panelList.get(panelName.indexOf("transformPanelA"));
				}
				

			}
			
			
			
			try{
				Thread.sleep(39);
			}catch(Exception ex){}
			
			long after = Calendar.getInstance().getTimeInMillis();
		   
	//		System.out.println(after-before);
	
		}
	
	}
	
	
	
	
	private void init() {
		// TODO Auto-generated method stub
		for(int i=0;i<panelList.size();i++){
			
			panelList.get(i).init();
		}
		
	}



	public void loading(){
		
		new Thread(new LoadingRunnable()).start();
		
	}
	




	class WheelLis implements MouseWheelListener{
        Point dragMouse = new Point(0,0);  //用于拖拽的mouse
        int old_x = 0;
        int old_y = 0;  //拖拽用的起始坐标

        
		@Override
		public void mouseWheelMoved(MouseWheelEvent wheelEve) {
			if(currentPanel.resizable){
				changeZoomStat(wheelEve);
			}
			
			
			}	
		

		private void changeZoomStat(MouseWheelEvent wheelEve){
			// 这里设置 放大最多2倍，缩小最多至1倍
			rollTimes = wheelEve.getWheelRotation();//返回滚轮滚动次数，向上为负，向下为正,向上放大，向下缩小；
		 
			//为实现缩放动态效果，此处用到 缩放速率，期望缩放倍率，当前缩放倍率 等变量，作为参数传入zoomControl中
			rollTimes = (rollTimes%3);

			if(zoomStat[1]<=1.8||zoomStat[1]>1){
				zoomStat[1]= zoomStat[1] -rollTimes*0.2;
				if(zoomStat[1]>1.8){
					zoomStat[1]=2;
				}
				else if(zoomStat[1]<1){
					zoomStat[1]=1;
				}

				zoomStat[2] = zoomStat[2] - rollTimes*0.01;			
				mouse = wheelEve.getPoint();
			}
		}
	}
     
    
	public void playMusic (){
		  
		if(currentPanel instanceof StartPanel){
			if(mt1.isRunning){
				mt1.stop();
			}
			if(mt2.isRunning){
				mt2.stop();
			}
			if(mt3.isRunning){
				mt3.stop();
			}
			   
			if(!mt4.isRunning){
				mt4.start();
			}
			   
		}
	  
		if((currentPanel instanceof DropMission1)|(currentPanel instanceof DropMission2)|(currentPanel instanceof DropMission3)|(currentPanel instanceof DropMission4)){
			if(mt4.isRunning){
				mt4.stop();
			}
			if(mt2.isRunning){
				mt2.stop();
			}
			if(mt3.isRunning){
				mt3.stop();
			}
			if(!mt1.isRunning){
			
			   mt1.start();
			}
			//return;
		}
		if((currentPanel instanceof SpaceMission1)|(currentPanel instanceof SpaceMission2)|(currentPanel instanceof SpaceMission3)|(currentPanel instanceof SpaceMissionDIY)){
			if(mt4.isRunning){
				mt4.stop();
			}
			if(mt1.isRunning){
				mt1.stop();
			}
			if(mt3.isRunning){
				mt3.stop();
			}
			if(!mt2.isRunning){
			    mt2.start();
			}
			//return;
		}
		if((currentPanel instanceof ZhiziTest1)|(currentPanel instanceof ZhiziTest2)|(currentPanel instanceof ZhiziTest3)|(currentPanel instanceof ZhiziTest4)){
			if(mt4.isRunning){
				mt4.stop();
			}
			if(mt2.isRunning){
				mt2.stop();
			}
			if(mt1.isRunning){
				mt1.stop();
			}
			if(!mt3.isRunning){
			    mt3.start();
			}
			//return;
		}
		
	}
		
	class LoadingRunnable implements Runnable{

	
		
		
		
		public void run(){
			while(!loadingPanel.isLoadingOver){
				
				loadingPanel.repaint();
				
					try {
						Thread.sleep(500);				
					} catch (Exception e) {
					// TODO: handle exception
					}				
			}
			
		}
	
	}
}