package gameparts;

import java.awt.Color;
import java.awt.Graphics;

import common.Screen;

public class Square implements Screen{
	private boolean player;
	private int enemy_num;

	/*
	 * 表示座標の計算を行う
	 * */
	private int pointX(int x){ return x * CELLSIZE;}
	private int pointY(int y){ return y * CELLSIZE;}

	/*
	 * Squareクラスのコンストラクタ
	 * */
	public Square() {
		player = false;
		enemy_num = 0;
	}

	/*
	 * 描画処理を行う
	 * */
	public void draw(Graphics g,int x,int y) {
		myFillRect(g,Color.lightGray,x,y);
	}

	/*
	 * 塗りつぶした四角形を描画するクラス。
	 * */
	private void myFillRect(Graphics g,Color c,int x,int y){
		g.setColor(c);
        g.fillRect(pointX(x), pointY(y), CELLSIZE, CELLSIZE);
        g.setColor(Color.black);
    	g.drawRect(pointX(x), pointY(y), CELLSIZE, CELLSIZE);
	}

	/*
	 * 敵の数を1減らす
	 * */
	public void decreaseEnemyNum() {
		if(enemy_num-1 >= 0)enemy_num--;
	}

	/*
	 * 敵の数を1増やす
	 * */
	public void increaseEnemyNum() {
		enemy_num++;
	}

	/*
	 * 敵がいるのかの判定を行い
	 * 結果を返す
	 * */
	public boolean isEnemy() {
		if(0 < enemy_num) {
			return true;
		}
		return false;
	}

	/*
	 * getter,setterを以下に記述する
	 * */
	public int getEnemy_num() {
		return enemy_num;
	}

	public void setEnemy_num(int enemy_num) {
		this.enemy_num = enemy_num;
	}
	public boolean isPlayer() {
		return player;
	}
	public void setPlayer(boolean player) {
		this.player = player;
	}
}
