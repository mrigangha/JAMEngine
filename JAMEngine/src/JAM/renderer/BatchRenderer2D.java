package JAM.renderer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL40.*;

import java.nio.FloatBuffer;
import java.util.LinkedList;

import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;



import JAM.Camera;
import JAM.Shader;
import JAM.Utils;
import JAM.components.Mesh2D;
import JAM.components.SpriteRenderer;
import JAM.components.Transform;

public class BatchRenderer2D {
	private int maxBatchSize;
	private int count;
	private int size_loaded;
	private FloatBuffer vertices;
	private Shader shader;
	private int vao,vbo,ibo;
	private boolean begin;
	private int []samplers;
	int texSlot;
	private LinkedList<Texture> textureList;
	
	public BatchRenderer2D(int mbs) {
		textureList=new LinkedList<Texture>();
		maxBatchSize=mbs+10;
		samplers=new int[] {
				0,1,2,3,4,5,6,7
		};
	}
	
	public void init()
	{
		vao=glGenVertexArrays();
		glBindVertexArray(vao);
		
		vbo=glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER,Mesh2D.getMeshSizeInBytes()*maxBatchSize , GL_DYNAMIC_DRAW);
		
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Mesh2D.getStride(), 0);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, Mesh2D.getStride(), 3*Float.BYTES);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, Mesh2D.getStride(), 6*Float.BYTES);
		glEnableVertexAttribArray(2);
		
		
		int[] indices=generateIndices();
		ibo=glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER,Utils.createIntBuffer(indices), GL_STATIC_DRAW);
		vertices=BufferUtils.createFloatBuffer(Mesh2D.getMeshSize()*maxBatchSize);
		texSlot=0;
	}
	
	private int[] generateIndices() {
		int[] indices=new int[maxBatchSize*6];
		
		for(int i=0;i<maxBatchSize;i++)
		{
			 int offsetArray=6*i;
			 int offset=4*i;
			 
			 //Traingle 1
			 indices[offsetArray]=offset+3;
			 indices[offsetArray+1]=offset+2;
			 indices[offsetArray+2]=offset+0;
			 
			 //Traingle 2
			 indices[offsetArray+3]=offset+0;
			 indices[offsetArray+4]=offset+2;
			 indices[offsetArray+5]=offset+1;
		}
		
		return indices;
	}

	public void begin()
	{
		texSlot=0;
		if(begin==true)
		{
			System.err.println("ERROR: Not ended the rendering frame by doing .end() method!");
			System.exit(-1);
		}
		begin=true;
		count=0;
		size_loaded=0;
		glBindVertexArray(vao);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
	}
	
	public void submit(Mesh2D mesh) {
		if(size_loaded>maxBatchSize-10)
		{
			System.err.println("Batch Overload");
			return;
		}
	    float x1 = mesh.getPosition().x;
	    float y1 = mesh.getPosition().y;
	    float x2 = mesh.getPosition().x + mesh.getSize().x;
	    float y2 =mesh.getPosition().y +  mesh.getSize().y;
	    float z=mesh.getPosition().z;
	    
	    float r = mesh.getColor().x;
	    float g = mesh.getColor().y;
	    float b = mesh.getColor().z;
	    
	    /* Put data into buffer */
	    vertices.put(x1).put(y1).put(z).put(r).put(g).put(b).put(0).put(0).put(-1);
	    vertices.put(x1).put(y2).put(z).put(r).put(g).put(b).put(0).put(0).put(-1);
	    vertices.put(x2).put(y2).put(z).put(r).put(g).put(b).put(0).put(0).put(-1);
	    vertices.put(x2).put(y1).put(z).put(r).put(g).put(b).put(0).put(0).put(-1);
	    size_loaded++;
	    count=count+6;
	}
	public void submit(SpriteRenderer sprite) {
		if(size_loaded>maxBatchSize-10)
		{
			System.err.println("Batch Overload");
			return;
		}
	    float x1 = sprite.getMesh().getPosition().x;
	    float y1 = sprite.getMesh().getPosition().y;
	    float x2 = sprite.getMesh().getPosition().x + sprite.getMesh().getSize().x;
	    float y2 =sprite.getMesh().getPosition().y + sprite.getMesh().getSize().y;
	    float z=sprite.getMesh().getPosition().z;

	    float r = sprite.getMesh().getColor().x;
	    float g = sprite.getMesh().getColor().y;
	    float b = sprite.getMesh().getColor().z;
	    Vector4f v1=new Vector4f(sprite.getMesh().getTex_coord_and_id().get(0),0);
	    Vector4f v2=new Vector4f(sprite.getMesh().getTex_coord_and_id().get(1),0);
	    Vector4f v3=new Vector4f(sprite.getMesh().getTex_coord_and_id().get(2),0);
	    Vector4f v4=new Vector4f(sprite.getMesh().getTex_coord_and_id().get(3),0);
	    Transform objTransform=(Transform)sprite.gameObject.getComponent(Transform.class);
	    if(objTransform!=null)
	    {
	    	
	    	int in=0;
		    Vector4f sv1=new Vector4f(x1,y1,z,0);//.mul(Utils.getTransform(objTransform.getPosition(), objTransform.getRotation(), objTransform.getScale()));
		    Vector4f sv2=new Vector4f(x1,y2,z,0);//.mul(Utils.getTransform(objTransform.getPosition(), objTransform.getRotation(), objTransform.getScale()));
		    Vector4f sv3=new Vector4f(x2,y2,z,0);//.mul(Utils.getTransform(objTransform.getPosition(), objTransform.getRotation(), objTransform.getScale()));
		    Vector4f sv4=new Vector4f(x2,y1,z,0);//.mul(Utils.getTransform(objTransform.getPosition(), objTransform.getRotation(), objTransform.getScale()));
		    //sv1.mul(Utils.getTransform(new Vector3f(sv1.x,sv1.y,z), new Vector3f(10,0,0),100));
		    //sv2.mul(Utils.getTransform(new Vector3f(sv2.x,sv2.y,z), new Vector3f(10,0,0),100));
		    //sv3.mul(Utils.getTransform(new Vector3f(sv3.x,sv3.y,z), new Vector3f(10,0,0),100));
		    //sv4.mul(Utils.getTransform(new Vector3f(sv4.x,sv4.y,z), new Vector3f(10,0,0),100));
		    if(sprite.has_Texture())
		    {
		    	sprite.getTexture().setCurrent_tslot(texSlot);
		    	boolean bo=false;
		    	for (int i = 0; i < textureList.size(); i++) {
					if(sprite.getTexture().getFilepath()==textureList.get(i).getFilepath())
					{
						sprite.getTexture().setCurrent_tslot(i);
						bo=true;
						break;
					}
				}
		    	
		    	if(texSlot>7)
		    	{
		    		//System.err.println("ERROR:texture OverLoad!");
		    		return;
		    	}
			    /* Put data into buffer*/
	    
			    vertices.put(sv1.x).put(sv1.y).put(sv1.z).put(r).put(g).put(b).put(v1.x).put(v1.y).put(sprite.getTexture().getCurrent_tslot());
			    vertices.put(sv2.x).put(sv2.y).put(sv2.z).put(r).put(g).put(b).put(v2.x).put(v2.y).put(sprite.getTexture().getCurrent_tslot());
			    vertices.put(sv3.x).put(sv3.y).put(sv3.z).put(r).put(g).put(b).put(v3.x).put(v3.y).put(sprite.getTexture().getCurrent_tslot());
			    vertices.put(sv4.x).put(sv4.y).put(sv4.z).put(r).put(g).put(b).put(v4.x).put(v4.y).put(sprite.getTexture().getCurrent_tslot());
			    size_loaded++;
			    count=count+6;
			    if(bo==false)
			    {
			    	textureList.add(sprite.getTexture());
			    }
		    }
		    return;
	    }

	    if(sprite.has_Texture())
	    {
	    	sprite.getTexture().setCurrent_tslot(texSlot);
	    	boolean bo=false;
	    	for (int i = 0; i < textureList.size(); i++) {
				if(sprite.getTexture().getFilepath()==textureList.get(i).getFilepath())
				{
					sprite.getTexture().setCurrent_tslot(i);
					bo=true;
					break;
				}
			}
	    	
	    	if(texSlot>7)
	    	{
	    		//System.err.println("ERROR:texture OverLoad!");
	    		return;
	    	}
		    /* Put data into buffer */
		    vertices.put(x1).put(y1).put(z).put(r).put(g).put(b).put(v1.x).put(v1.y).put(sprite.getTexture().getCurrent_tslot());
		    vertices.put(x1).put(y2).put(z).put(r).put(g).put(b).put(v2.x).put(v2.y).put(sprite.getTexture().getCurrent_tslot());
		    vertices.put(x2).put(y2).put(z).put(r).put(g).put(b).put(v3.x).put(v3.y).put(sprite.getTexture().getCurrent_tslot());
		    vertices.put(x2).put(y1).put(z).put(r).put(g).put(b).put(v4.x).put(v4.y).put(sprite.getTexture().getCurrent_tslot());
		    size_loaded++;
		    count=count+6;
		    if(bo==false)
		    {
		    	textureList.add(sprite.getTexture());
		    }
	    }
	    else {
		    vertices.put(x1).put(y1).put(z).put(r).put(g).put(b).put(0).put(0).put(-1);
		    vertices.put(x1).put(y2).put(z).put(r).put(g).put(b).put(0).put(0).put(-1);
		    vertices.put(x2).put(y2).put(z).put(r).put(g).put(b).put(0).put(0).put(-1);
		    vertices.put(x2).put(y1).put(z).put(r).put(g).put(b).put(0).put(0).put(-1);
		    size_loaded++;
		    count=count+6;
		}
	}
	
	public void end(Camera camera,Shader shader) 
	{
		if(begin!=true)
		{
			System.err.println("ERROR: Not started the rendering frame by doing .begin() method!");
			System.exit(-1);
		}
		for (int i = 0; i <textureList.size(); i++) {
			if(i>7)
			{
				//System.out.println("ERROR:texture OverLoad!");
				break;
			}
			else {
				textureList.get(i).bind(i);
			}
		}
        vertices.flip();
		glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
		shader.start();
		shader.loadVec4f(shader.getUniformLocation("c"), new Vector4f(1,0,0,1));
		shader.loadMat4fv(shader.getUniformLocation("uView"), camera.getViewMatrix());
		shader.loadMat4fv(shader.getUniformLocation("uProjection"), camera.getProjectionMatrix());
		glDrawElements(GL_TRIANGLES, count,GL_UNSIGNED_INT,0);
		for (int i = 0; i <textureList.size(); i++) {
			if(i>7)
			{
				//System.err.println("ERROR:texture OverLoad!");
				break;
			}
			else {
				textureList.get(i).unbind();
			}
		}
		vertices.clear();
		textureList.clear();
		count=0;
		begin=false;
		
	}
	
	public void clearColor(float r,float g,float b,float a) {
		glClearColor(r, g, b, a);
	}
	
	public void dispose() {
		if(begin==true)
		{
			System.err.println("ERROR: Never ended the rendering frame by doing .end() method!");
			System.exit(-1);
		}
		glDeleteBuffers(vbo);
		glDeleteBuffers(ibo);
		glDeleteVertexArrays(vao);
		begin=false;
	}
}
