package JAM.EngineEditor;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import JAM.Camera;
import JAM.DefaultShader;
import JAM.GameObject;
import JAM.KeyListener;
import JAM.ResourcePool;
import JAM.Scene;
import JAM.Shader;
import JAM.components.Mesh2D;
import JAM.components.SpriteRenderer;

public class EditorScene extends Scene{

	Shader shader;
	Scene current_Scene;
	
	public void loadScene(Scene scene) {
		current_Scene=scene;
	}
	public Scene getCurrent_Scene() {
		return current_Scene;
	}
	
	@Override
	public void init() 
	{
		super.init();
		camera=new Camera(new Vector3f());
		camera.adjustOrtho();
		shader=new DefaultShader();
	}
	

	@Override
	public void update(float dt) 
	{
		
		default_batch.clearColor(1, 1, 1, 1);
	

	}
	
	@Override
	public void dispose() {
		super.dispose();
		shader.dispose();
	}

}
