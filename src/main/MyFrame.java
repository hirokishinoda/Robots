package main;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import panel.GamePanel;
import panel.StartPanel;

public class MyFrame extends JFrame implements Runnable{
	private Thread main_loop;
	private int state;
	private int old_state;

	private final int MENU = 0;
	private final int GAME = 1;


	public MyFrame(String title) {
		setTitle(title); // タイトル設定
		//setResizable(false);// サイズ変更の禁止
		// ×ボタンで閉じる設定
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Panelの設定
		Container content_panel = getContentPane();
		content_panel.add(new StartPanel(this));
		pack();
		// 状態の初期化
		old_state = state = MENU;
		// プログラム全体(画面遷移などを含む)ループの開始
		main_loop = new Thread(this);
		main_loop.start();
	}

	public void change(JPanel panel) {
		//ContentPaneにはめ込まれたパネルを削除
		getContentPane().removeAll();

		super.add(panel);//パネルの追加
		validate();//更新
		repaint();//再描画
	}

	@Override
	public void run() {

		while(true) {
			if(state != old_state) {
				if(state == MENU) {
					change(new StartPanel(this));
				}else if(state == GAME) {
					change(new GamePanel(this));
				}
				old_state = state;
			}

			try{
				Thread.sleep(20);
			}catch(InterruptedException e){
				System.out.println(e);
			}
		}
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}
}
