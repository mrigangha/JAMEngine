package JAM.components;

import org.joml.Vector3f;

public class Transform extends Component{
	
	private Vector3f position;
	private Vector3f rotation;
	private int scale;
	
	
	public Transform() {
		position=new Vector3f();
		scale=0;
		rotation=new Vector3f();
	}
	
	public Transform(Vector3f pos,Vector3f rot,int sca) {
		position=pos;
		scale=sca;
		rotation=rot;
	}
	@Override
	public void init() {
	}
	
	@Override
	public void update(float dt) {
		
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public int getScale() {
		return scale;
	}
	
}
