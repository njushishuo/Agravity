package cn.edu.nju.launcher;


import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import cn.edu.nju.panels.SuperMission;

/**
 * 
 * �й����ŵĿ��������ı����ĳ���x��y���������ŵĶ�̬Ч��,�ڲ��㷨��Ϊ��ѧ֪ʶ����Ϊ����
 * 
 *
 */
public class zoomControl {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public void zoom(double[] zoomStat,SuperMission mission,Point mouse){
		
		//��̬�ķ�Χ����ֹ���Ź���
		double dynRange =  Math.abs(zoomStat[2]);
		
		if(zoomStat[0]<zoomStat[1]+1.8*dynRange&&zoomStat[0]>zoomStat[1]-1.8*dynRange){
			zoomStat[2] = zoomStat[1]-zoomStat[0];  //�ж��Ƿ���������Լ���ڵ�ǰ���ʣ�����ǣ���ô�޸����ʣ�ʹ��һ֡����׼ȷ��Ϊ�������ʡ�
			}
		
			zoomStat[0] = zoomStat[0]+zoomStat[2];
			
		
		if(zoomStat[2]>0){ 			//�Ŵ�
		mission.paint_width = (int)(mission.compute_width*zoomStat[0]);
		mission.paint_height = (int)(mission.compute_height*zoomStat[0]);  //���������Ŵ�
		mission.paint_x = mission.paint_x-(int)(zoomStat[2]*mouse.getX());
		mission.paint_y = mission.paint_y-(int)(zoomStat[2]*mouse.getY());   //�������λ��
		
		
		
		 	}
		else{								//��С��Ϊ�˷�ֹ��Ļ���ֿհף��Ը���
			mission.paint_width = (int)(mission.compute_width*zoomStat[0]);
			mission.paint_height = (int)(mission.compute_height*zoomStat[0]);  //���������Ŵ�
			mission.paint_x = mission.paint_x-(int)(zoomStat[2]*mouse.getX());
			mission.paint_y = mission.paint_y-(int)(zoomStat[2]*mouse.getY()); //�������λ��
			
			if(mission.paint_x>0){
				mission.paint_x=0;
			}
			if(mission.paint_y>0){
				mission.paint_y=0;
			}
			if(mission.paint_x+(int)(mission.fullScreenZoomRate[0]*mission.paint_width)<screenSize.getWidth()){
				mission.paint_x=(int) (screenSize.getWidth()-(int)(mission.fullScreenZoomRate[0]*mission.paint_width));
			}
			if(mission.paint_y+(int)(mission.fullScreenZoomRate[1]*mission.paint_height)<screenSize.getHeight()){
				mission.paint_y=(int) (screenSize.getHeight()-(int)(mission.fullScreenZoomRate[1]*mission.paint_height));
			}
		
		
		}
		
	}
	
}
