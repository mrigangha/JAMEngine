package JAM;

import static org.lwjgl.opengl.GL33.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;


public class Shader {
    protected HashMap<String, Integer> uniformStore;
	private String vs,fs;
	private int vsID,fsID,program;
	
	public  Shader(String vs_Src,String fs_Src) {
		uniformStore=new HashMap<>();
		vs_Src=vs_Src;
		fs_Src=fs_Src;
		
		vsID=compile(vs_Src, GL_VERTEX_SHADER);
		fsID=compile(fs_Src, GL_FRAGMENT_SHADER);
		program=glCreateProgram();
		glAttachShader(program, vsID);
		glAttachShader(program, fsID);
		glLinkProgram(program);
		glValidateProgram(program);
		
	}
	
	public void start()
	{
		glUseProgram(program);
	}
	
	public void stop() {
		glUseProgram(0);
	}
	
    public void loadFloat(int location,float value)
    {
        glUniform1f(location, value);
    }

    public void loadVec4f(int location, Vector4f value)
    {
        glUniform4f(location, value.x, value.y, value.z, value.w);
    }

    public void loadMat4fv(int loc, Matrix4f mat){FloatBuffer buffer= BufferUtils.createFloatBuffer(16); mat.get(buffer);loadMat4fv(loc,buffer);}
    private void loadMat4fv(int loc,FloatBuffer fb) {
        glUniformMatrix4fv(loc,false,fb);
    }
    public void loadTexture(String varname,int slot)
    {
        start();
        glUniform1i(getUniformLocation(varname),slot);
    }

    public int getUniformLocation(String uniformName)
    {
		if(uniformStore.containsKey(uniformName))
        {
            return uniformStore.get(uniformName);
        }
        uniformStore.put(uniformName, glGetUniformLocation(program, uniformName));
        return uniformStore.get(uniformName);
    }
	
	public int compile(String file,int type)
	{
		StringBuilder shaderSource = new StringBuilder();
		
		try {
			BufferedReader reader=new BufferedReader(new FileReader(file));
			String line;
            try {
                while ((line = reader.readLine())!=null) {
                    shaderSource.append(line).append("\n");
                }

                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int id=glCreateShader(type);
		glShaderSource(id, shaderSource);
		glCompileShader(id);
		if(glGetShaderi(id, GL_COMPILE_STATUS)==GL_FALSE)
		{
			System.err.println("Error: "+glGetShaderInfoLog(id,1024)+" from file "+file);
			System.exit(-1);
		}
		return id;
	}
	
	public void dispose() {
        stop();
        glDetachShader(program, vsID);
        glDetachShader(program, fsID);
        glDeleteShader(vsID);
        glDeleteShader(fsID);
        glDeleteProgram(program);
	}
	
}
