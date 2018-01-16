package cn.edu.nju.launcher;
import java.io.IOException;

import GameMusic.MusicThread;


public class Main {

	public static void main(String[] args) throws IOException{
		//MUSIC 
		new Main().go();
		
				   
	}

	public void go() throws IOException{
		
		new GameLauncher().Launch();
		
	}
	
	
}
