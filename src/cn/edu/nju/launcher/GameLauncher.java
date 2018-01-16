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
 * ��Ϸ��ѭ�������࣬��Ҫְ��Ϊ������Ϸ��ѭ����ʵ�������л����Լ�ʵ�ֶ����ţ���ק�ļ���
������frame�ڸ����У������š���ק������frame������ϵ�����Խ����ż������ڴ˴���
 */


public class GameLauncher {
	JFrame frame = new JFrame();
	BackgroundPanel currentPanel;
	zoomControl zoom = new zoomControl();
	WheelLis lis =  new WheelLis();
	double[]  zoomStat = {1,1,0};  //�������йص����ݣ�[0] ��ʾ��ǰ���� [1]��ʾ�������� [2]��ʾ�����ٶ�
	Point mouse = new Point(0,0);
	int rollTimes =0; //����������
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
        //ˮ����С��
	    DropMission1 dropMission1 = new DropMission1();
        DropMission2 dropMission2 = new DropMission2();
	    DropMission3 dropMission3 = new DropMission3();
	    DropMission4 dropMission4 = new DropMission4();
		//������С��
	    ZhiziTest1 zhiziTest1 = new ZhiziTest1();
	    ZhiziTest2 zhiziTest2 = new ZhiziTest2();
	    ZhiziTest3 zhiziTest3 = new ZhiziTest3();
	    ZhiziTest4 zhiziTest4 = new ZhiziTest4();
	    //�ɴ���
	    SpaceMission1 spaceMission1 = new SpaceMission1();
	    SpaceMission2 spaceMission2 = new SpaceMission2();
	    SpaceMission3 spaceMission3 = new SpaceMission3();
	    SpaceMissionDIY spaceMissionDIY = new SpaceMissionDIY();
	    //ѡ�ؽ���
	    DropChoosePanel dropChoosePanel = new DropChoosePanel();
	    SpaceChoosePanel spaceChoosePanel = new SpaceChoosePanel();
	    ZhiziChoosePanel zhiziChoosePanel = new ZhiziChoosePanel();
		//��ͼ�༭��
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
	    
	    //������Ǽӵ�cardLayout��
	    for(int i=0;i<panelList.size();i++){	    	
	    	cardPanel.add(panelName.get(i), panelList.get(i));	    	
	    }
	    

    
		loadingPanel.isLoadingOver =true;
		
//		  MusicThread musicthread = new MusicThread ();
//		  musicthread.creatMT("bgm01", 5);
		
		while(true){
			long before = Calendar.getInstance().getTimeInMillis();
					
			/*��Ϊȫ��Ҫ��Ӧ��ͬ��Ļ��С����������֮���������趨panelλ�á�
			������������д���ǽ������д��panel�С�������������ɻ��濨�٣�����д����ѭ����
		    */			
			 currentPanel.setBounds(currentPanel.paint_x,
					 				currentPanel.paint_y,
					 				(int)(currentPanel.fullScreenZoomRate[0]*currentPanel.paint_width),
					 				(int)(currentPanel.fullScreenZoomRate[1]*currentPanel.paint_height) );
			 
			currentPanel.repaint();			
			//Music
			
			playMusic();
			
			//����л�����
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
        Point dragMouse = new Point(0,0);  //������ק��mouse
        int old_x = 0;
        int old_y = 0;  //��ק�õ���ʼ����

        
		@Override
		public void mouseWheelMoved(MouseWheelEvent wheelEve) {
			if(currentPanel.resizable){
				changeZoomStat(wheelEve);
			}
			
			
			}	
		

		private void changeZoomStat(MouseWheelEvent wheelEve){
			// �������� �Ŵ����2������С�����1��
			rollTimes = wheelEve.getWheelRotation();//���ع��ֹ�������������Ϊ��������Ϊ��,���ϷŴ�������С��
		 
			//Ϊʵ�����Ŷ�̬Ч�����˴��õ� �������ʣ��������ű��ʣ���ǰ���ű��� �ȱ�������Ϊ��������zoomControl��
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