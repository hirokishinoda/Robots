package gameparts;

import java.awt.Graphics;

import common.GameMode;

public class Stage implements GameMode{
	private Board board;
	private int score;

	/*
	 * Stageクラスのコンストラクタ
	 * */
	public Stage(int enemy_num) {
		// フィールドの初期化
		board = new Board(enemy_num);
	}

	/*
	 * 描画処理を行う
	 * */
	public void draw(Graphics g) {
		board.draw(g);
	}

	/*
	 * 一連の処理の流れを行う
	 *
	 * ・Player移動
	 * ・敵移動
	 * ・衝突判定(スコア変更＆GAMEOVER判定)
	 * */
	public int flowProcessing(int player_x,int player_y,boolean warp) {
		boolean player_move = false;

		// Player移動処理
		// warp処理と8方向移動処理を
		// 分けて考える
		if(warp) {
			player_move = board.playerWarp();
		}else {
			player_move = board.playerMove(player_x,player_y);
		}
		// Playerが移動したら敵も移動
		if(player_move) {
			// 敵移動処理
			board.enemyMove();
			// 衝突判定(GAMEOVER処理)と敵撃破のスコア計算
			if(collisionAndCalcScore()) {
				return GAMEOVER;
			}
			// CLEAR判定
			if(board.isClear()) {
				return STAGEUP;
			}
		}
		return CONTINUE;
	}

	/*
	 * 衝突判定を行い、撃破した敵の数に応じた
	 * スコアを計算、反映
	 * */
	private boolean collisionAndCalcScore() {
		int old_enemy_num;

		// 衝突判定前の敵の数
		old_enemy_num = board.getCurrent_enemy_num();
		System.out.println("[old_enemy_num]"+old_enemy_num);
		// 衝突判定
		if(board.collision()) {
			return true;
		}
		// 衝突判定前の敵の数と衝突判定後の敵の数を比較
		// 撃破した敵の数に応じたスコアを加算
		score = (old_enemy_num - board.getCurrent_enemy_num());
		old_enemy_num =  board.getCurrent_enemy_num();

		System.out.println("[BreakEnemy] = " + (old_enemy_num - board.getCurrent_enemy_num()));
		System.out.println("[Score] = " + score);

		return false;
	}

	/*
	 * getter,setterを以下に記述する
	 * */
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
