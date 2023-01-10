package JAM.GameObjects;

import JAM.GameObject;
import JAM.components.BoxCollider;

public class JPlayer extends GameObject{
	
	public JPlayer() {
		
	}
	
	protected void OnPlayerUpdate() {
		
	}
	
	
	protected void PlayerOnCollsion(BoxCollider collider)
	{
	}
	
	protected void PlayerInit() {
	}
	
	private void JPlayerUpdate() {
		
	}
	
	@Override
	public void init()
	{
		PlayerInit();
		super.init();
	}
	
	@Override
	public void update(float dt)
	{
		JPlayerUpdate();
		OnPlayerUpdate();
		super.update(dt);
	}
	
	
}
