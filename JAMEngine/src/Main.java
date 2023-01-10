import JAM.JAMEngine;

public class Main {

	public static void main(String[] args) {
		JAMEngine engine=new JAMEngine("Game", 800, 600,new PlayScene());
		engine.start();
	}

}
