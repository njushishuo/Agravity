package cn.edu.nju.zhiziTools;



import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Spaceship {
	  
	public double x1;
	public double y1;				

    public double speed_x ;
 	public double speed_y;
 	public int size;
 	public int m;
	Ellipse2D.Double ship = new Ellipse2D.Double();
	
	
	
	public Spaceship(double x1 , double y1 , double speed_x ,double speed_y ,int size,int m){
		this.x1 =x1;
		this.y1 =y1;
		this.speed_x =speed_x;
		this.speed_y =speed_y;
		this.size = size;
        this.m = m;
		
		ship.setFrameFromCenter(x1, y1, x1+size, y1+size);
	}
	
	
	

	
	public Ellipse2D.Double getSpaceship(){
		ship.setFrameFromCenter(x1, y1, x1+size, y1+size);

		return ship;
	}

}

