package gameparts;

public class CollisionManager {

	/*
	 * 敵とプレイヤーの衝突判定を行う
	 * */
	public boolean collisionPlayerAndEnemy(Square square) {
		if(square.isEnemy()) {
			return true;
		}
		return false;
	}

	/*
	 * 敵同士の衝突判定を行う
	 * */
	public boolean collisionEnemyAndEnemy(Square square) {
		if(square.getEnemy_num() > 1) {
			return true;
		}
		return false;
	}

}
