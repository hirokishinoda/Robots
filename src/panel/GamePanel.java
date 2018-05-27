package panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import common.GameMode;
import common.Screen;
import gameparts.Game;
import main.MyFrame;

public class GamePanel extends JPanel implements Runnable,KeyListener,GameMode,Screen{
	private Game game;
	private MyFrame my_frame;
	private Thread game_loop;

	public GamePanel(MyFrame frame) {
		my_frame = frame;
		game = new Game();
		// フレームにキー入力を追加
		my_frame.addKeyListener(this);
		// Layoutを無効化
		setLayout(null);
		// 背景色の設定
		setBackground(Color.white);
		// パネルサイズの設定
		setPreferredSize(new Dimension(MY_WIDTH, MY_HEIGHT));
		// ゲームループの開始
		game_loop = new Thread(this);
		game_loop.start();
	}

	// 描画処理
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.draw(g);
	}

	/*
	 * ゲームループ
	 * */
	@Override
	public void run() {
		// ゲームオーバーでない限りゲーム続行
		while(game.getGame_mode() != GAMEOVER) {

			repaint();
			try{
				Thread.sleep(20);
			}catch(InterruptedException e){
				System.out.println(e);
			}
		}

		my_frame.removeKeyListener(this);
		my_frame.setState(MENU);
		System.out.println("GO");
	}

	/*
	 * キー入力処理
	 * */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		game.keyGameAction(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
