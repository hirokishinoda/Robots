package characters;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy extends Character{
	private final int cell_size = 32;
	private boolean scrap;

	public Enemy() {
		scrap = false;
	}

	@Override
	public void move(int move_x, int move_y) {
		super.setX(super.getX() + move_x);
		super.setY(super.getY() + move_y);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillOval(super.getX() * cell_size + (cell_size/2)/2,super.getY() * cell_size + (cell_size/2)/2,cell_size/2,cell_size/2);
	}

	public boolean isScrap() {
		return scrap;
	}

	public void setScrap(boolean scrap) {
		this.scrap = scrap;
	}

}
