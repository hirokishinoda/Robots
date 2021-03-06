package characters;

import java.awt.Graphics;

public abstract class Character {
	private int x;
	private int y;

	// 抽象メソッド
	public abstract void move(int move_x,int move_y);
	public abstract void draw(Graphics g);

	/*
	 * getter,setterを以下に記述
	 */
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
