package JAM;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import JAM.renderer.Texture;

public class ResourcePool {
	
	private static HashMap<String, Shader> shaders;
	private static HashMap<String, Texture> textures;

	public static void Init() {
		shaders=new HashMap<>();
		textures=new HashMap<>();
		System.err.println("INFO: Resource pool sucessfully Initialished!");
	}
	
	public static Shader getShader(String vs,String fs) {
		if(shaders.containsKey(vs))
		{
			return shaders.get(vs);
		}
		else {
			Shader shader=new Shader(vs, fs);
			shaders.put(vs, shader);
			return shader;
		}
	}
	
	public static Texture getTexture(String fp) {
		if(textures.containsKey(fp))
		{
			return textures.get(fp);
		}
		else {
			Texture texture=new Texture(fp);
			textures.put(fp, texture);
			return texture;
		}
	}
	
	public void loadAbstractedShader(String id,Shader shader) {
		shaders.put(id, shader);
	}
	public Shader getAbstractedShader(String id) {
		if (shaders.containsKey(id)) {
			return shaders.get(id);
		}
		System.err.println("ERROR: No abstracted shader found of id "+ id +" !");
		return null;
		
	}
	
	public static void clean() {
		for (Map.Entry<String, Shader> shader: shaders.entrySet()) {
			shaders.get(shader.getKey()).dispose();
		}
		
		for (Map.Entry<String, Texture> shader: textures.entrySet()) {
			textures.get(shader.getKey()).dispose();
		}
		System.err.println("INFO: Resource pool sucessfully disposed!");
	}

}
