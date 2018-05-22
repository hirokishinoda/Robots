package gameparts;

import java.awt.Graphics;

public class Stage {
	private Board board;

	public Stage(int enemy_num) {
		// フィールドの初期化
		board = new Board(enemy_num);
	}

	public void draw(Graphics g) {
		board.draw(g);
	}

	public void flowProcessing(int player_x,int player_y) {
		// Player移動処理
		if(board.playerMove(player_x,player_y)) {
			// 敵移動処理
			board.enemyMove();
			// 衝突判定
			board.collision();
			// CLEAR判定
			// MAP(描画)更新
		}
	}
}
