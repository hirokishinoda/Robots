package gameparts;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Game {
	private Stage stage;

	public Game() {
		stage = new Stage(1);
	}

	public void draw(Graphics g) {
		stage.draw(g);
	}

	public void keyGameAction(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			stage.flowProcessing(0, -1);
			System.out.println("[W]");
		}else if(e.getKeyCode() == KeyEvent.VK_A) {
			stage.flowProcessing(-1, 0);
			System.out.println("[A]");
		}else if(e.getKeyCode() == KeyEvent.VK_D) {
			stage.flowProcessing(1, 0);
			System.out.println("[D]");
		}else if(e.getKeyCode() == KeyEvent.VK_X) {
			stage.flowProcessing(0, 1);
			System.out.println("[X]");
		}
	}
}
