package csctrails.elements;

import static csctrails.elements.B2DVars.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import csctrails.main.Paths;

/**
 * Boss is a subclass of Model. It represents any of the boss
 * will appear in the game.
 * 
 * Change Log:
 * 15.9.29mhk: First Edition
 * 15.9.29gha: Moved getSprite() and vars from sub to super
 *
 */

public class Boss extends Model{
	
	public Boss(World world, int xpos, int ypos) {
		super(null, null, "filler");
		
		//Sprite
		Texture tex = new Texture(Paths.SPRITE_BOSS_1);
		this.textureHeight = tex.getHeight();
		this.textureWidth = tex.getWidth();
		this.sprite = new Sprite(tex);
		
		//Box2D
		BodyDef BD = new BodyDef(); // create body definition - mhk 15.9.29
		BD.position.set(xpos/PPM, ypos/PPM); 
		BD.type=B2DVars.STATIC;
		PolygonShape Bboss = new PolygonShape();  // create shape - mhk 15.9.29
		Bboss.setAsBox(textureWidth/2/PPM, textureHeight/2/PPM);
		FixtureDef FD = new FixtureDef();  //create fixture definition - mhk 15.9.29
		FD.shape = Bboss;
		this.body = world.createBody(BD); // create body - mhk 15.9.29
		this.body.createFixture(FD); // create fixture - mhk 15.9.29
		
	}

}
