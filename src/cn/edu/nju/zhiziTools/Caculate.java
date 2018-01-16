package cn.edu.nju.zhiziTools;



public class Caculate {
	
	double vax;
	double vay;
	double vbx;
	double vby;

	int m1;
	int m2;
	double x1;
	double x2;
	double y1;
	double y2;


	double final_vax;
	double final_vay;
	double final_vbx;
	double final_vby;
	
	double vax_;
	double vay_;
	double vbx_;
	double vby_;
	



	double Vax;
	double Vay;
	double Vbx;
	double Vby;
	double temp_Vax;
	double temp_Vay;
	
	
	
	double tant;
	double theta1;

	double cost;
	double  sint;

	
	public Caculate(double vax, double vay , double vbx ,double vby ,int m1 ,int m2,double x1 ,double y1,double x2,double y2){
		this.vax = vax;
		this.vay = vay;
		this.vbx = vbx;
		this.vby = vby;

	    this.m1  = m1;
	    this.m2  = m2;
	    this.x1  = x1;
	    this.x2  = x2;
	    this.y1  = y1;
	    this.y2  = y2;
	
	
	
	
	
    tant =(x1-x2)/(y1-y2) ;
    theta1 =Math.atan(tant);
    cost =Math.cos(theta1);
    sint = Math.sin(theta1);

    
    
    
    vax_ = vax*cost+vay*sint ;
    vbx_ = vbx*cost+vby*sint ;
    vay_ = -vay*cost+vax*sint ;
    vby_ = -vby*cost+vbx*sint ;

	
	temp_Vax =((m1-m2)*vax_ +2*m2*vbx_)/(m1+m2);
	Vbx =((m2-m1)*vbx_+2*m1*vax_)/(m1+m2);
	Vax =temp_Vax ;
	
	
	
	temp_Vay =((m1-m2)*vay_ +2*m2*vby_)/(m1+m2);
	Vby =((m2-m1)*vby_+2*m1*vay_)/(m1+m2);
	Vay =temp_Vay ;
    
	
	
	
	
	
	final_vax = Vax*cost+Vay*sint;
	final_vbx = Vbx*cost+Vby*sint;
	final_vay = Vax*sint-Vay*cost;
	final_vby = Vbx*sint-Vby*cost;
	
	
   
	
	}
	public double getVax(){
	
		return final_vax;
	}
	public double getVay(){
		return final_vay;
	}
	public double getVbx(){
		return final_vbx;
	}
	public double getVby(){
		return final_vby;
	}
	
}
