package grandtheftroster.elements;

import static grandtheftroster.elements.B2DVars.PPM;
import static grandtheftroster.elements.B2DVars.PPM;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.World;

public class Switch extends Model implements Switchable{
	private float time;
	private float speed;
	private int range;
	private int center;
	private boolean isOn;
	private ArrayList<Switchable> switchable;
	public Switch(World world, int width, int range, float speed, int xpos, int ypos) {
		super(world, "MODEL:KEY");
		this.speed = speed;
		this.center = xpos-Math.abs(range/2);
		this.range = range;
		body.setTransform(xpos/PPM, ypos/PPM, 0);
		isOn = false;
		setPosition(xpos,ypos,0);
		body.setActive(false);
		switchable = new ArrayList<Switchable>();
		
		
		
		
		
	}
	
	
	
	public boolean getState()
	{
		return isOn;
		
	}
	public void setState(boolean state)
	{
		isOn = state;
		
	}
	private void setLocation()
	{ 
		float xpos = (float) ((range/2/PPM)*Math.cos(time*speed))+center/PPM;
		float ypos = body.getPosition().y; 
		body.setTransform(xpos, ypos,0);
	}
	
	public  void update(float dt)
	{
		super.update(dt);
		time+=dt;
		setLocation();
		for(Switchable s: switchable)
		{
			s.switchState(isOn);
		}
		
		
	}
	
	
	
	//responsible for keys on
	
	public void setSwitchable(ArrayList<Switchable> s) 
	{
		switchable.addAll(s);
	}

	public ArrayList<Switchable> getSwitchable()
	{
		ArrayList<Switchable> h = new ArrayList<>();
		h.addAll(switchable);
		return h;
	}



	@Override
	public void switchState(boolean b) {
		// TODO Auto-generated method stub
		setVisible(b);
		
	}
	
	//grabs variable from lvl2contactlist
	
	

}
