package GameMusic;




public class MyRunnable implements Runnable {
	    public myAudioPlayer map;
		public String musicNum = "00";
		public int looptimes =1;
    public void run (){
    	 map = new myAudioPlayer();
         for (int i=0; i<looptimes;i++){
    	 map.play(musicNum);
    	}
         
   
    }
    
    public 	myAudioPlayer getMAP(){
    	return map;
    }
    	
}
