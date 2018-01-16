package cn.edu.nju.zhiziTools;


import java.awt.*;
import java.util.ArrayList;



public class SpeedUp {
	
	
	
	
	ArrayList<Point> pointlist =new ArrayList<Point>();
	
	Polygon s = new Polygon();
	public SpeedUp(int polygon_x,int polygon_y){
		
		
	pointlist.add(new Point(polygon_x,polygon_y));
	pointlist.add(new Point(polygon_x+50,polygon_y+50));
	pointlist.add(new Point(polygon_x+100,polygon_y));
	pointlist.add(new Point(polygon_x+100,polygon_y+25));
	pointlist.add(new Point(polygon_x+50,polygon_y+75));
	pointlist.add(new Point(polygon_x,polygon_y+25));
	pointlist.add(new Point(polygon_x,polygon_y));
	
	
    for(int i=0;i<pointlist.size();i++)
    	((Polygon) s).addPoint(((Point) pointlist.get(i)).x, ((Point) pointlist.get(i)).y);
	}
	
	
	
	public Polygon getPolygon(){
		return s;
	}
	

}

