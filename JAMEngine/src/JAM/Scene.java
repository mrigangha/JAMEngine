package JAM;

import java.util.LinkedList;

import JAM.renderer.BatchRenderer2D;

public abstract class Scene {
	protected BatchRenderer2D default_batch;
	protected Camera camera;
	public LinkedList<GameObject> go=new LinkedList<GameObject>();
	public JAMEngine engine;
	
	public void init() {
		default_batch=new BatchRenderer2D(100000);
		default_batch.init();
		go=new LinkedList<>();
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public abstract void update(float dt);
	public void dispose() {default_batch.dispose();System.err.println("INFO: Scene sucessfully disposed!");}
}
