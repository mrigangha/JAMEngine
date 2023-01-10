package JAM;

import static org.lwjgl.glfw.GLFW.*;

public class KeyListener {
	  private static boolean []keys=new boolean[350];

	    public static void Key_Callback(long window, int key, int scancode, int action, int mods)
	    {
	        if(key<keys.length)
	        {
	            if(action==GLFW_PRESS)
	            {
	                keys[key]=true;
	            }
	            else if(action==GLFW_RELEASE)
	            {
	                keys[key]=false;
	            }
	        }
	    }

	    public static boolean is_Key_Pressed(int keycode)
	    {
	        return keys[keycode];
	    }
}
