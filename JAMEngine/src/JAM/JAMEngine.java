package JAM;

import static org.lwjgl.opengl.GL33.*;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public class JAMEngine implements Runnable {

	
	private int h,w;
	private String title;
	private Thread thread;
	private long window;
	private Scene current_Scene;
	
	public JAMEngine(String title,int width,int height,Scene scene)
	{
		current_Scene=scene;
		scene.engine=this;
		this.title=title;
		this.w=width;
		this.h=height;
		Utils.width=width;
		Utils.height=height;
	}
	
	public void loadScene(Scene scene) {
		current_Scene.dispose();
		current_Scene=scene;
		scene.engine=this;
		current_Scene.init();
	}
	
	public synchronized void start()
	{
		thread=new Thread(this,"Game");
		thread.start();
	}
	
	
	@Override
	public void run() {
		if(!glfwInit())
			System.err.println("GLFW not initialished! ");
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        
		window=glfwCreateWindow(w, h, title, 0, 0);
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		glfwSetFramebufferSizeCallback(window, JAMEngine::frame_buffer_size_Callback);
		glfwSetKeyCallback(window, KeyListener::Key_Callback);
		
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
		
        long LastTime=System.nanoTime();
        final double ns=1000000000.0 /60.0;
        double deltaTime=0.0;
        int frames=0;
        long Timer=System.currentTimeMillis();

        ResourcePool.Init();
        current_Scene.init();
        while(!glfwWindowShouldClose(window))
        {
            long now=System.nanoTime();
            deltaTime +=(now -LastTime)/ns;
            while(deltaTime>=1)
            {
                deltaTime--;
            }
            glfwPollEvents();
            glfwSwapBuffers(window);
            glEnable(GL_DEPTH_TEST);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            current_Scene.update((float)deltaTime);
            frames++;
            if(System.currentTimeMillis()-Timer>1000)
            {
                Timer+=1000;
                System.out.println("fps  "+frames);
                frames=0;
            }

            LastTime=now;

        }
        current_Scene.dispose();
        ResourcePool.clean();
        glfwTerminate();
        
        System.err.println("INFO: Process sucessfully ended!");
		
	}
	
	public void exit() {
		System.err.println("INFO: Ending the Engine Process by doing engine.exit() method!");
		glfwSetWindowShouldClose(window, true);
	}
	
	private static void frame_buffer_size_Callback(long win,int w,int h)
	{
		Utils.width=w;
		Utils.height=h;
		glViewport(0, 0, w, h);
	}

}
