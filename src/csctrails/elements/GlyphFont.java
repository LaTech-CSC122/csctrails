package csctrails.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GlyphFont {

	public static final int COLOR_WHITE = 0;
	public static final int COLOR_BLACK = 1;
	public static final int COLOR_RED = 2;
	public static final int COLOR_GREEN = 3;
	public static final int COLOR_BLUE = 4;
	public static final int COLOR_ORANGE = 5;
	public static final int COLOR_YELLOW = 6;
	public static final int COLOR_LIGHT_BLUE = 7;
	
	TextureRegion[][] glyphArray;
	
	public GlyphFont(String path) {
		Texture tmp = new Texture(path);
		glyphArray = TextureRegion.split(tmp, 8, 8);
	}
	
	public int draw(SpriteBatch sb, String value, int color, int xpos, int ypos){
		//split value into char[]
		int x = xpos;
		int y = ypos;
		char[] characters = value.toCharArray();
		for(int i=0; i<characters.length; i++){
			try{
				sb.draw(glyphArray[color][characters[i]-32], x, y);
			} catch(IndexOutOfBoundsException e){
				sb.draw(glyphArray[COLOR_WHITE][((int)' ')-32], x, y);
			}
			x += 8;
		}
		return x;
	}

}
