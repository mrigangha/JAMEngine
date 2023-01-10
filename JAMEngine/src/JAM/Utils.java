package JAM;

import static org.lwjgl.opengl.GL33.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

public class Utils {
	public static int width,height;
	 public static Matrix4f getTransform(Vector3f offset,Vector3f rotation,float scale)
	    {
	        Matrix4f WorldMatrix=new Matrix4f();
	        WorldMatrix.identity();
	        WorldMatrix.translate(offset).rotateX((float)Math.toRadians(rotation.x))
	                .rotateY((float)Math.toRadians(rotation.y))
	                .rotateZ((float)Math.toRadians(rotation.z))
	                .scale(scale);
	        return WorldMatrix;
	    }

	    public static void clean()
	    {
	        while(glGetError()!=0);
	    }
	    public static void check(int i)
	    {
	        int error=glGetError();
	        if(error!=0)
	        {
	            System.out.println(glGetError()+"from "+i);
	        }
	        clean();
	    }
	    
	    public static IntBuffer createIntBuffer(int[] indices)
	    {
	        IntBuffer buffer= BufferUtils.createIntBuffer(indices.length);
	        buffer.put(indices);
	        buffer.flip();
	        return buffer;
	    }

	    public static FloatBuffer createFloatBuffer(float[] data)
	    {
	        FloatBuffer buffer=BufferUtils.createFloatBuffer(data.length);
	        buffer.put(data);
	        buffer.flip();
	        return buffer;
	    }
	    
}
