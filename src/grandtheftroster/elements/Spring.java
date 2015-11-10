package grandtheftroster.elements;

import static grandtheftroster.elements.B2DVars.PPM;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;


public class Spring extends Model {
	private Animation am;
	private TextureRegion currentFrame;
	private float time;
	
	public Spring(World world, int xpos, int ypos)
	{
		
		super(world, "MODEL:SPRING");
		body.setTransform(xpos/PPM, ypos/PPM, 0);
		loadAnimation();
	}

	
	private void loadAnimation(){
		float speed = 0.05f;
		TextureRegion[][] spritesheet = TextureRegion.split(new Texture(new FileHandle("res/images/spring_board.png")), 40, 40);
		TextureRegion[] cycle = new TextureRegion[spritesheet.length*spritesheet[0].length];
		int index = 0;
		for(int i=0; i<spritesheet.length; i++){
			for(int j=0; j<spritesheet[i].length; j++){
				cycle[index] = spritesheet[i][j];
				index++;
			}
		}
		am = new Animation(speed, cycle);
		am.setPlayMode(Animation.NORMAL);
		time = speed*cycle.length;
	}
	
	@Override
	public void update(float dt){
		time += dt;
		currentFrame = am.getKeyFrame(time);
	}
	
	@Override
	public void draw(SpriteBatch sb){
		float xpos = body.getPosition().x*PPM - currentFrame.getRegionWidth()/2;
		float ypos = body.getPosition().y*PPM - currentFrame.getRegionHeight() +14;
		sb.draw(currentFrame, xpos, ypos);
	}
	
	public void action(){
		time = 0;
	}
	
}