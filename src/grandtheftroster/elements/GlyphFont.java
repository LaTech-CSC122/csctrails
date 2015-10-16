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

	
	private static class Alignment{
		private static int nextValue = 0;
		private int value;
		public Alignment(){
			value = nextValue;
			nextValue ++;
		}
	}

	
	public static final GlyphFont.Alignment ALIGN_LEFT = new Alignment();
	public static final GlyphFont.Alignment ALIGN_CENTER = new Alignment();
	public static final GlyphFont.Alignment ALIGN_RIGHT = new Alignment();
	
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
	private SpriteBatch sb;
	
	public GlyphFont(String path, int size, SpriteBatch sb) {
		Texture tmp = new Texture(path);
		this.size = size;
		glyphArray = TextureRegion.split(tmp, size, size);
		this.sb = sb;
	}
	
	public int draw(String value, GlyphFont.Color color, GlyphFont.Alignment alignment, int xpos, int ypos){
		int x = xpos;
		int y = ypos;
		if(alignment.value == ALIGN_LEFT.value){}
		if(alignment.value == ALIGN_CENTER.value){
			x -= value.length()*size/2;
		}
		if(alignment.value == ALIGN_RIGHT.value){
			x -= value.length()*size;
		}
		
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
	public int draw(String value, GlyphFont.Color color, int xpos, int ypos){
		return draw(value, color, ALIGN_LEFT, xpos, ypos);
	}
	public int draw(String value, GlyphFont.Alignment align, int xpos, int ypos){
		return draw(value, COLOR_WHITE, align, xpos, ypos);
	}
	public int draw(String value, int xpos, int ypos){
		return draw(value, COLOR_WHITE, ALIGN_LEFT, xpos, ypos);
	}
	
}
