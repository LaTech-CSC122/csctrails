package grandtheftroster.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public interface Activity
{
	public abstract void begin();
	public abstract void end();
	public abstract void draw(SpriteBatch sb);
	public abstract void update(float dt);
	public abstract void dispose();
	public abstract void handleInput();
}
