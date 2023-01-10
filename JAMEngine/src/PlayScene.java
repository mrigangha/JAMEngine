import JAM.Camera;


import JAM.DefaultShader;
import JAM.GameObject;
import JAM.JAMEngine;
import JAM.KeyListener;
import JAM.ResourcePool;
import JAM.Scene;
import JAM.Shader;
import JAM.Utils;
import JAM.components.Mesh2D;
import JAM.components.SpriteRenderer;
import JAM.components.Transform;
import JAM.renderer.BatchRenderer2D;

import static org.lwjgl.opengl.GL33.*;

import java.nio.channels.NonWritableChannelException;
import java.util.LinkedList;
import java.util.Random;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;



public class PlayScene extends Scene{


	Shader shader;
	Mesh2D mesh2d;
	Mesh2D mesh;
	int vao,vbo,ibo;
	GameObject gameObject;

	SpriteRenderer sprite2;
	Random ran=new Random();
	
	@Override
	public void init() 
	{
	
		
		super.init();
		camera=new Camera(new Vector3f());
		camera.adjustOrtho();
		shader=new DefaultShader();
		
		/*camera=new Camera(new Vector3f());
		camera.adjustOrtho();
		gameObject=new GameObject();
		gameObject.scene=this;
		gameObject.addComponent(new Mesh2D());
		shader=new DefaultShader();
		mesh=new Mesh2D();
		mesh.getPosition().x=200;
		mesh.getPosition().y=400;
		mesh.setColor(new Vector3f(1,0,0));
		mesh.setSize(new Vector2f(100,100));
		mesh2d=new Mesh2D();
		mesh2d.getPosition().x=200;
		mesh2d.getPosition().y=200;
		mesh2d.setColor(new Vector3f(0,1,0));
		mesh2d.setSize(new Vector2f(100,100));
		sprite=new SpriteRenderer(ResourcePool.getTexture("res/Texture/testImage.png"));
		sprite.getMesh().getPosition().x=600;
		sprite.getMesh().getPosition().y=200;
		sprite.getMesh().setColor(new Vector3f(0,1,0));
		sprite.getMesh().setSize(new Vector2f(100,100));
		sprite2=new SpriteRenderer(ResourcePool.getTexture("res/Texture/testImage.png"));
		sprite2.getMesh().getPosition().x=600;
		sprite2.getMesh().getPosition().y=400;
		sprite2.getMesh().setColor(new Vector3f(0,1,0));
		sprite2.getMesh().setSize(new Vector2f(100,100));
		ran=new Random();*/
		for (int j = 0; j < 6730; j++) {
			SpriteRenderer sprite=new SpriteRenderer(ResourcePool.getTexture("res/Texture/testImage.png"));
			sprite.getMesh().getPosition().x=ran.nextInt(800);
			sprite.getMesh().getPosition().y=ran.nextInt(600);
			sprite.getMesh().getSize().x=10;
			sprite.getMesh().getSize().y=20;
			sprite.getMesh().setColor(new Vector3f(0,1,0));
			GameObject gObject=new GameObject();
			gObject.addComponent(sprite);

			go.add(gObject);
			
			JAM.components.Transform t=((Transform)gObject.getComponent(JAM.components.Transform.class));
			t.getPosition().x=100;t.getPosition().y=100;
			sprite.getMesh().setSize(new Vector2f(100,100));
			for (int i = 0; i < this.go.size(); i++) {
				GameObject tempObject=go.get(i);
				tempObject.init();
			}
		}

	}
	

	@Override
	public void update(float dt) 
	{
		if(KeyListener.is_Key_Pressed(GLFW.GLFW_KEY_B))
		{
			int i=0;
		}
		default_batch.begin();
		for (int i = 0; i < this.go.size(); i++) {
			GameObject tempObject=go.get(i);
			tempObject.update(dt);
			if(tempObject.getComponent(SpriteRenderer.class)!=null)
			{
				default_batch.submit((SpriteRenderer)tempObject.getComponent(SpriteRenderer.class));
			}
		}
		default_batch.end(camera, shader);
		
		camera.adjustOrtho();
		default_batch.clearColor(1, 1, 1, 1);
		

	}
	
	@Override
	public void dispose() {
		super.dispose();
		shader.dispose();
	}

}
