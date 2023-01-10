package JAM;

import java.util.LinkedList;
import JAM.components.Component;
import JAM.components.Transform;

public class GameObject { 
	public Scene scene;
	protected LinkedList<Component> components;
	public GameObject()
	{
		components=new LinkedList<>();
		Transform transform=new Transform();
		components.add(transform);
	}
	
	public void init()
	{
		for (int i = 0; i < components.size(); i++) {
			components.get(i).init();
		}
	}
	
	public void update(float dt)
	{
		for (int i = 0; i < components.size(); i++) {
			components.get(i).update(dt);
		}
	}
	
	public <T extends Component>Component getComponent(Class<T> componentClass) {
		for (int i = 0; i < components.size(); i++) {
			if(componentClass.isAssignableFrom(components.get(i).getClass()))
			{
                try {
                    return componentClass.cast(components.get(i));
                }catch (ClassCastException e)
                {
                    e.printStackTrace();
                    System.err.println("casting component exception!");
                    System.exit(-1);
                }
			}
		}
		return null;
	}
	
	public <T extends Component>void removeComponent(Class<T> componentClass) {
		for (int i = 0; i < components.size(); i++) {
			if(componentClass.isAssignableFrom(components.get(i).getClass()))
			{
				components.remove(i);
			}
		}
	}
	
	public void addComponent(Component c) {
		components.add(c);
		c.gameObject=this;
	}
}
