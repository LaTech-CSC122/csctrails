package grandtheftroster.elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GlyphFont {

	private static class Color{
		private int color;
		public Color(int color){
			this.color = color;
		}
	}

	public static final GlyphFont.Color COLOR_WHITE = new Color(0);
	public static final GlyphFont.Color COLOR_BLACK = new Color(1);
	public static final GlyphFont.Color COLOR_RED = new Color(2);
	public static final GlyphFont.Color COLOR_GREEN = new Color(3);
	public static final GlyphFont.Color COLOR_BLUE = new Color(4);
	public static final GlyphFont.Color COLOR_ORANGE = new Color(5);
	public static final GlyphFont.Color COLOR_YELLOW = new Color(6);
	public static final GlyphFont.Color COLOR_LIGHT_BLUE = new Color(7);
	
	private int size = 8;
	private TextureRegion[][] glyphArray;
	
	
	public GlyphFont(String path, int size) {
		Texture tmp = new Texture(path);
		glyphArray = TextureRegion.split(tmp, size, size);
	}
	
	public int draw(SpriteBatch sb, String value, GlyphFont.Color color, int xpos, int ypos){
		//split value into char[]
		int x = xpos;
		int y = ypos;
		char[] characters = value.toCharArray();
		for(int i=0; i<characters.length; i++){
			try{
				sb.draw(glyphArray[color.color][characters[i]-32], x, y);
			} catch(IndexOutOfBoundsException e){
				sb.draw(glyphArray[COLOR_WHITE.color][((int)' ')-32], x, y);
			}
			x += size;
		}
		return x;
	}

}
