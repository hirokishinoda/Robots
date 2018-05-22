package gameparts;

import java.awt.Color;
import java.awt.Graphics;

public class Square {
	private final int cell_size = 32;
	private boolean player;
	private int enemy_num;

	private int pointX(int x){ return x * cell_size;}
	private int pointY(int y){ return y * cell_size;}

	public Square() {
		player = false;
		enemy_num = 0;
	}

	public void draw(Graphics g,int x,int y) {
		myFillRect(g,Color.lightGray,x,y);
	}

	private void myFillRect(Graphics g,Color c,int x,int y){
		g.setColor(c);
        g.fillRect(pointX(x), pointY(y), cell_size, cell_size);
        g.setColor(Color.black);
    	g.drawRect(pointX(x), pointY(y), cell_size, cell_size);
    	if(player) {

    	}
	}

	public void decreaseEnemyNum() {
		if(enemy_num-1 >= 0)enemy_num--;
	}

	public void increaseEnemyNum() {
		enemy_num++;
	}

	public boolean isEnemy() {
		if(0 < enemy_num) {
			return true;
		}
		return false;
	}

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
