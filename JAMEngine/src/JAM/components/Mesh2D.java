package JAM.components;

import java.util.LinkedList;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.util.tinyexr.TinyEXR;


//can be used directly in an game object  but will need custom renderer;
public class Mesh2D extends Component{
	Vector3f position;
	Vector2f size;
	Vector3f color;
	LinkedList<Vector3f> tex_coord_and_id;
	

	public Mesh2D() {
		tex_coord_and_id=new LinkedList<>();
		position=new Vector3f();
		size=new Vector2f();
		color=new Vector3f();
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void update(float dt) {
		
	}
	
	public Vector3f getColor() {
		return color;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Vector2f getSize() {
		return size;
	}
	
	public static int getStride() {
		return 9*Float.BYTES;
	}
	
	public static int getMeshSizeInBytes() {
		return getStride()*4;
	}
	public static int getMeshSize() {
		return 6*4;
	}
	public void setColor(Vector3f color) {
		this.color = color;
	}
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public void setSize(Vector2f size) {
		this.size = size;
	}
	
	public void setTex_coord_and_id(LinkedList<Vector3f> tex_coord_and_id) {
		this.tex_coord_and_id = tex_coord_and_id;
	}
	
	public void setTex_coord_and_id() {
		this.tex_coord_and_id = new LinkedList<>();
	}
	
	public LinkedList<Vector3f> getTex_coord_and_id() {
		return tex_coord_and_id;
	}
	
	public void setTexture_id(int id) {
		for (int i = 0; i < tex_coord_and_id.size(); i++) {
			tex_coord_and_id.get(i).z=id;
		}
	}

	
}
