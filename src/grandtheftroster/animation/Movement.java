package grandtheftroster.animation;

public class Movement {

	private static class MovementType{
		private static int nextId = 0;
		private int id;
		private MovementType(){
			id = nextId;
			nextId++;
		}
	}
	
	public static final MovementType LINEAR = new MovementType();
	
	private KeyFrame frameOne;
	private KeyFrame frameTwo;
	private MovementType movementType;
	
	public Movement(KeyFrame frameOne, KeyFrame frameTwo, MovementType movementType){
		this.frameOne = frameOne;
		this.frameTwo = frameTwo;
		this.movementType = movementType;
	}
}
