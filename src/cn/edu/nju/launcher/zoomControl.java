package cn.edu.nju.launcher;


import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import cn.edu.nju.panels.SuperMission;

/**
 * 
 * 有关缩放的控制器，改变面板的长宽，x，y，控制缩放的动态效果,内部算法多为数学知识，较为复杂
 * 
 *
 */
public class zoomControl {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public void zoom(double[] zoomStat,SuperMission mission,Point mouse){
		
		//动态的范围，防止缩放过度
		double dynRange =  Math.abs(zoomStat[2]);
		
		if(zoomStat[0]<zoomStat[1]+1.8*dynRange&&zoomStat[0]>zoomStat[1]-1.8*dynRange){
			zoomStat[2] = zoomStat[1]-zoomStat[0];  //判断是否期望倍率约等于当前倍率，如果是，那么修改速率，使下一帧倍率准确变为期望倍率。
			}
		
			zoomStat[0] = zoomStat[0]+zoomStat[2];
			
		
		if(zoomStat[2]>0){ 			//放大
		mission.paint_width = (int)(mission.compute_width*zoomStat[0]);
		mission.paint_height = (int)(mission.compute_height*zoomStat[0]);  //长宽按比例放大
		mission.paint_x = mission.paint_x-(int)(zoomStat[2]*mouse.getX());
		mission.paint_y = mission.paint_y-(int)(zoomStat[2]*mouse.getY());   //调整面板位置
		
		
		
		 	}
		else{								//缩小，为了防止屏幕出现空白，略复杂
			mission.paint_width = (int)(mission.compute_width*zoomStat[0]);
			mission.paint_height = (int)(mission.compute_height*zoomStat[0]);  //长宽按比例放大
			mission.paint_x = mission.paint_x-(int)(zoomStat[2]*mouse.getX());
			mission.paint_y = mission.paint_y-(int)(zoomStat[2]*mouse.getY()); //调整面板位置
			
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
