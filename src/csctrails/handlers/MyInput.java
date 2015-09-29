package csctrails.handlers;

/**
 * MyInput contains the data one which buttons are pressed
 * and if they are being help down. This class is not
 * meant to be instantiated but only referred to statically.
 * 
 * Change Log:
 * 10.9.21gha - First edition.
 *
 */

public class MyInput {
	
	private static boolean[] keys; // Current state of the key - gha 15.9.21
	private static boolean[] pkeys; // Previous state of the key (the last time it was updated) - gha 15.9.21
	
	public static final int NUM_KEYS = 16; //Number of keys being used - gha 15.9.21
	
	public static final int BUTTON_SPACE = 0;
	public static final int BUTTON_SHIFT = 1;
	public static final int BUTTON_ENTER = 2;
	public static final int BUTTON_ESC = 3;
	
	public static final int BUTTON_LEFT = 4;
	public static final int BUTTON_RIGHT = 5;
	public static final int BUTTON_UP = 6;
	public static final int BUTTON_DOWN = 7;
	
	public static final int BUTTON_W = 8;
	public static final int BUTTON_A = 9;
	public static final int BUTTON_S = 10;
	public static final int BUTTON_D = 11;
	
	public static final int BUTTON_0 = 12;
	public static final int BUTTON_1 = 13;
	public static final int BUTTON_2 = 14;
	public static final int BUTTON_3 = 15;
	
	static {
		keys = new boolean[NUM_KEYS];
		pkeys = new boolean[NUM_KEYS];
	}
	
	//This should be called on each time the game is updated - gha 15.9.21
	//I recommend we call on this in the Game class render() method.  - gha 15.9.21 
	public static void update() {
		for(int i = 0; i < NUM_KEYS; i++) {
			pkeys[i] = keys[i]; //move current state to previous state - gha 15.9.21
		}
	}
	
	//Change the current key state - gha 15.9.21
	public static void setKey(int i, boolean b) { keys[i] = b; }
	
	//Returns true if the key is currently held down - gha 15.9.21
	public static boolean isDown(int i) { return keys[i]; }
	
	//Returns true once if the key is pressed - gha 15.9.21
	public static boolean isPressed(int i) { return keys[i] && !pkeys[i]; }
	
}
















