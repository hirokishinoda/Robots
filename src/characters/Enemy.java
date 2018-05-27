package characters;

import java.awt.Color;
import java.awt.Graphics;

import common.Screen;

public class Enemy extends Character implements Screen{
	private boolean scrap;

	/*
	 * Enemyクラスのコンストラクタ
	 * */
	public Enemy() {
		scrap = false;
	}

	/*
	 * 敵移動処理
	 */
	@Override
	public void move(int move_x, int move_y) {
		// 自分がスクラップでなければ移動
		if(!scrap) {
			super.setX(super.getX() + move_x);
			super.setY(super.getY() + move_y);
		}
	}

	/*
	 * 敵描画処理
	 */
	@Override
	public void draw(Graphics g) {
		// スクラップなら黄色い四角
		// スクラップでないなら黒丸
		if(!scrap) {
			g.setColor(Color.black);
			g.fillOval(super.getX() * CELLSIZE + (CELLSIZE/2)/2,super.getY() * CELLSIZE + (CELLSIZE/2)/2,CELLSIZE/2,CELLSIZE/2);
		}else {
			g.setColor(Color.yellow);
			g.fillRect(super.getX() * CELLSIZE + (CELLSIZE/2)/2,super.getY() * CELLSIZE + (CELLSIZE/2)/2,CELLSIZE/2,CELLSIZE/2);
		}
	}

	/*
	 * getter,setterを以下に記述する
	 * */
	public boolean isScrap() {
		return scrap;
	}

	public void setScrap(boolean scrap) {
		this.scrap = scrap;
	}

}
