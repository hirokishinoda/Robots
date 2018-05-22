package characters;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends Character{
	private final int cell_size = 32;

	@Override
	public void move(int move_x,int move_y) {
		super.setX(super.getX() + move_x);
		super.setY(super.getY() + move_y);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillOval(super.getX() * cell_size + (cell_size/2)/2,super.getY() * cell_size + (cell_size/2)/2,cell_size/2,cell_size/2);
	}
}
