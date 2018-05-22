package gameparts;

public class CollisionManager {

	public CollisionManager() {

	}

	public boolean collisionPlayerAndEnemy(Square square) {
		if(square.isEnemy()) {
			return true;
		}
		return false;
	}

	public boolean collisionEnemyAndEnemy(Square square) {
		if(square.getEnemy_num() > 1) {
			return true;
		}
		return false;
	}

}
