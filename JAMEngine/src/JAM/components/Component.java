package JAM.components;

import JAM.GameObject;

public abstract class Component {
	public GameObject gameObject;
	
	public void init() {
		
	}
	
	public abstract void update(float dt);
}
