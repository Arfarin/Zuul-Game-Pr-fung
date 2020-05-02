package ZuulBad;

public class Player {
	
	Inventory backpack;
	private int lifeBar;
	private int foodBar;

	public Player() {
		backpack = new Inventory();
	}


	public void lookAround(Room currentRoom) {
		
		System.out.println(currentRoom.getLongDescription());

	}
	public void eat() {
		
	}
//	
//		public void chooseBackpack(int maxWeight){
//		backpack.setMaxWeight(maxWeight);
//	}
	
	public void useWeapon() {
		
	}
}
