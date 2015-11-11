package grandtheftroster.elements;

import static grandtheftroster.elements.B2DVars.PPM;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Fan extends Model {
	private Animation am;
	private float time;
	private int height;
	public Fan(World world, int xpos, int ypos, int height)
	{
		super(world, "");
		createBody(world, xpos, ypos, height);
		tags.add("MODEL,FAN");
		body.setTransform(xpos/PPM, (ypos+height/2)/PPM, 0);
		loadAnimation();
		this.height = height;
		
	}

	
	private void createBody(World world, int xpos, int ypos, int height){
		BodyDef bdef = new BodyDef();
		bdef.type = B2DVars.STATIC;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(4/PPM, height/2/PPM);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.isSensor = true;
		
		body = world.createBody(bdef);
		body.createFixture(fdef);
		body.setUserData(this);
	}
	
	private void loadAnimation()
	{
		TextureRegion[][] spritesheet = TextureRegion.split(new Texture(new FileHandle("res/images/wind.png")), 32, 32);
		TextureRegion[] cycle = new TextureRegion[spritesheet.length*spritesheet[0].length];
		int index = 0;
		for(int i=0; i < spritesheet.length; i++)
		{
			for(int j=0; j<spritesheet[i].length; j++)
			{
				cycle[index]= spritesheet[i][j];
				index++;
			}
		}
		am = new Animation(.4f, cycle);
		am.setPlayMode(Animation.LOOP);
	}
	
	public void update(float dt)
	{
		super.update(dt);
		time+=dt;
	}
	
	public void draw(SpriteBatch sb)
	{
		super.draw(sb);
		sb.draw(am.getKeyFrame(time), body.getPosition().x*PPM-16,body.getPosition().y*PPM-height/2);
	
	}
}
