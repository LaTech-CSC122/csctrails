package grandtheftroster.elements;

import static grandtheftroster.elements.B2DVars.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import grandtheftroster.utilities.Configuration;

public class MovingPlatform extends Model implements MovingElement {

	private static Configuration cfg;
	
	static{
		cfg = new Configuration();
		cfg.loadConfiguration("res/config/paths/");
	}
	
	private float time;
	private float speed;
	private int range;
	private int center;
	private int width;
	private Texture movingPlatform;
	
	public MovingPlatform(World world, int width, int range, float speed, int xpos, int ypos) {
		super(world, "");
		this.speed = speed;
		this.center = xpos-Math.abs(range/2);
		this.range = range;
		this.width = width;
		
		
		createBody(world,width, xpos, ypos);
		addTag("MODEL,MOVING_PLATFORM,GROUND");
		setPosition(xpos,ypos,0);
		
		movingPlatform = new Texture(cfg.getProperty("MOVING_PLATFORM@PATHS:SPRITES"));
	}
		private void createBody(World world, int width, int xpos, int ypos){
			BodyDef bdef = new BodyDef();
			bdef.type = B2DVars.STATIC;
			//bdef.position.set(0, -length/2/PPM);
			
			
			PolygonShape shape = new PolygonShape();
			shape.setAsBox(width/2/PPM, 0/PPM);
			
			
			FixtureDef fdef = new FixtureDef();
			fdef.shape = shape;
			//fdef.isSensor = true;
			
			body = world.createBody(bdef);
			body.createFixture(fdef);
			body.setUserData(this);
		
		}
	public void setLocation()
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
		
	}
	@Override
	public float getChangeX(float dt) {
		
		float velocity = (float) -((range/2/PPM)*Math.sin(time*speed))*speed;
		return velocity*dt;
	}
	@Override
	public float getChangeY(float dt) {
		
		return 0;
	}
	
	@Override
	public void draw(SpriteBatch sb)
	{
		if(!body.isActive()){return;}
		//Vector2 pos = body.getPosition()
		int repeat = width/movingPlatform.getWidth();
		float xpos = body.getPosition().x*PPM;
		float ypos = body.getPosition().y*PPM - movingPlatform.getHeight();
		xpos -= width/2;
		for(int i=0; i<repeat; i++)
		{
		sb.draw(movingPlatform,(int)(xpos+i*movingPlatform.getWidth()),(int)(ypos));
		}
	}

	/*@Override
	public void switchState(boolean b) {
		// TODO Auto-generated method stub
		
		setVisible(b);
		
	}*/

}
