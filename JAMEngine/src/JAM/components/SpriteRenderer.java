package JAM.components;

import java.util.LinkedList;

import org.joml.Vector3f;

import JAM.renderer.Texture;

public class SpriteRenderer extends Component{
	private boolean has_texture;
	private Texture texture;
	private Mesh2D mesh;
	
	public SpriteRenderer(Mesh2D mesh,Texture texture) {
		this.mesh=mesh;
		this.texture=texture;
		has_texture=true;
	}
	
	public SpriteRenderer(Texture texture) {
		this.mesh=new Mesh2D();
		this.texture=texture;
		LinkedList<Vector3f> tex_coord_and_id=new LinkedList<Vector3f>();
		tex_coord_and_id.add(new Vector3f(1,1,0));
		tex_coord_and_id.add(new Vector3f(1,0,0));
		tex_coord_and_id.add(new Vector3f(0,0,0));
		tex_coord_and_id.add(new Vector3f(0,1,1));
		mesh.setTex_coord_and_id(tex_coord_and_id);
		has_texture=true;
	}
	
	public void LoadTexture(Texture tex,LinkedList<Vector3f> tex_coord_and_id)
	{
		this.mesh=new Mesh2D();
		this.texture=texture;
		mesh.setTex_coord_and_id(tex_coord_and_id);
		has_texture=true;
	}
	
	public SpriteRenderer(Texture texture,LinkedList<Vector3f> tex_coord_and_id) {
		this.mesh=new Mesh2D();
		mesh.setTex_coord_and_id(tex_coord_and_id);
		this.texture=texture;
		has_texture=true;
	}
	public SpriteRenderer() {
		this.mesh=new Mesh2D();
		this.texture=null;
		this.has_texture=false;
	}
	
	public Mesh2D getMesh() {
		return mesh;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public boolean has_Texture() {
		return has_texture;
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
	}
	

	
}
