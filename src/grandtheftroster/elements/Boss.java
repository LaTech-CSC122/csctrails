package grandtheftroster.elements;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import static grandtheftroster.elements.B2DVars.PPM;

import grandtheftroster.handlers.AnimationManager;
import grandtheftroster.utilities.Configuration;

public class Boss extends Model {
	private AnimationManager am;
	public static final int PUSHINGLEFT = 0;
	public static final int PUSHINGRIGHT = 1; 
	public static final int FACINGLEFT = 2;
	public static final int FACINGRIGHT = 3;
	public static Configuration cfg;
	
	static
	{
		cfg = new Configuration();
		cfg.loadConfiguration("res/config/paths/");
	}

	
	
	private int state;
	
	public Boss(World world, int state, int xpos, int ypos) {
		super(world, "MODEL:BOSS");
		body.setTransform(xpos/PPM, ypos/PPM, 0);
		loadAnimation();
		this.state = state;
		am.setState(state);
		
	}

	private void loadAnimation() {
		am = new AnimationManager();
		am.addAnimation(AnimationManager.createAnimation(5/18f, 48, 48, false, false,
				cfg.getProperty("BOSS_PUSHING@PATHS:SPRITES")), PUSHINGLEFT);
		am.addAnimation(AnimationManager.createAnimation(.1f, 48, 48, false, false,
				cfg.getProperty("BOSS_PUSHING@PATHS:SPRITES")), FACINGLEFT);
		am.addAnimation(AnimationManager.createAnimation(.1f, 48, 48, true, false,
				cfg.getProperty("BOSS_PUSHING@PATHS:SPRITES")), PUSHINGRIGHT);
		am.addAnimation(AnimationManager.createAnimation(.1f, 48, 48, true, false,
				cfg.getProperty("BOSS_PUSHING@PATHS:SPRITES")), FACINGRIGHT);
		am.setState(FACINGLEFT);
		am.resetAnimation();
		
	}
	
	public void update(float dt)
	{
		super.update(dt);
		//am.setState(state);
		if(state == PUSHINGLEFT || state == PUSHINGRIGHT)
		{
			am.update(dt);
		}
		else
		{
			am.resetAnimation();
		}
	}
	
	public void draw(SpriteBatch sb)
	{
		float x = body.getPosition().x*PPM;
		float y = body.getPosition().y*PPM;
		am.draw(sb, x, y);
		
	}

}
