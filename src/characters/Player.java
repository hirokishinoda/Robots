package characters;

import java.awt.Color;
import java.awt.Graphics;

import common.Screen;

public class Player extends Character implements Screen{

	/*
	 * Player移動処理
	 */
	@Override
	public void move(int move_x,int move_y) {
		super.setX(super.getX() + move_x);
		super.setY(super.getY() + move_y);
	}

	/*
	 * プレイヤー描画処理(赤丸)
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillOval(super.getX() * CELLSIZE + (CELLSIZE/2)/2,super.getY() * CELLSIZE + (CELLSIZE/2)/2,CELLSIZE/2,CELLSIZE/2);
	}
}
