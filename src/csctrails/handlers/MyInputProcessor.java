package csctrails.handlers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;


/**
 * MyInputProcessor (InPr) is an InputAdapter that is passed
 * to the Application when it first begins. The application 
 * calls on it as an event handle for input. The InPr makes 
 * changes to the static fields of MyInput to store data on
 * the state of the buttons.
 *
 * Change Log:
 * 15.9.21gha: First Edition
 * 
 */

public class MyInputProcessor extends InputAdapter {

	
	public boolean keyDown(int k) {
		
		setKey(k, true);
		return true;
	}
	
	public boolean keyUp(int k) {
		setKey(k, false);
		return true;
	}
	
	private void setKey(int i, boolean b){
		if(i == Keys.SPACE) MyInput.setKey(MyInput.BUTTON_SPACE, b);
		else if(i == Keys.ENTER) MyInput.setKey(MyInput.BUTTON_ENTER, b);
		else if(i == Keys.SHIFT_LEFT) MyInput.setKey(MyInput.BUTTON_SHIFT, b);
		else if(i == Keys.SHIFT_RIGHT) MyInput.setKey(MyInput.BUTTON_SHIFT, b);
		else if(i == Keys.ESCAPE) MyInput.setKey(MyInput.BUTTON_ESC, b);
		
		else if(i == Keys.LEFT) MyInput.setKey(MyInput.BUTTON_LEFT, b);
		else if(i == Keys.RIGHT) MyInput.setKey(MyInput.BUTTON_RIGHT, b);
		else if(i == Keys.UP) MyInput.setKey(MyInput.BUTTON_UP, b);
		else if(i == Keys.DOWN) MyInput.setKey(MyInput.BUTTON_DOWN, b);
		
		else if(i == Keys.W) MyInput.setKey(MyInput.BUTTON_W, b);
		else if(i == Keys.A) MyInput.setKey(MyInput.BUTTON_A, b);
		else if(i == Keys.S) MyInput.setKey(MyInput.BUTTON_S, b);
		else if(i == Keys.D) MyInput.setKey(MyInput.BUTTON_D, b);
		
		else if(i == Keys.NUM_0) MyInput.setKey(MyInput.BUTTON_0, b);
		else if(i == Keys.NUM_1) MyInput.setKey(MyInput.BUTTON_1, b);
		else if(i == Keys.NUM_2) MyInput.setKey(MyInput.BUTTON_2, b);
		else if(i == Keys.NUM_3) MyInput.setKey(MyInput.BUTTON_3, b);
	}
}
