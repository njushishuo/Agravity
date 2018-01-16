package GameMusic;



public class MusicThread {
      String  musictag="00";
      int Looptimes =1;
      public MyRunnable threadJob = new MyRunnable();;
      public Thread thread;
      public myAudioPlayer map;
      public boolean isRunning=false;
     
	public  void choosemusic (String a){
		switch (a){
		case "bgm00": musictag = "bgm00"; break;
		case "bgm01": musictag = "bgm01"; break;
		case "bgm02": musictag = "bgm02"; break;
		case "start00":musictag = "start00"; break;
		
		
		case "cd00":  musictag = "cd00"; break;
		
		case "drop00": musictag = "drop00"; break;
		case "drop01": musictag = "drop01"; break;
		
		case "rock00": musictag = "rock00"; break;
		case "rock01": musictag = "rock01"; break;
		
		case "explo01": musictag = "explo01"; break;
		case "explo02": musictag = "explo02"; break;
		case "explo04": musictag = "explo04"; break;
		
		case "trans00":musictag = "trans00"; break;
		case "trans01":musictag = "trans01"; break;
		
		case "rebound00" : musictag = "rebound00";break;
		
		
		}
	}
	
	public  void changeLoopTimes (int b){
		Looptimes = b;
		
	}
	public  void creatMT(String c , int d){
		choosemusic (c);
		changeLoopTimes(d);
		
		
	    threadJob.musicNum= musictag;
	    threadJob.looptimes=Looptimes;
	   
	   
	   // thread.start();
	  
	}
	public void start (){
		isRunning = true; 
	//	System.out.println("mt start");
		thread = new Thread(threadJob);
	    thread.start();
	    
	    
	}
	public void stop (){
		threadJob.getMAP().stop();
		isRunning = false;
	//	System.out.println ("mt end");
	}
}
