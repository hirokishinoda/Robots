package main;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

import common.Screen;
import panel.GamePanel;
import panel.StartPanel;

public class MyFrame extends JFrame implements Runnable,Screen{
	private Thread main_loop;
	private int state;
	private int old_state;

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

	/*
	 * パネルの交換(画面切り替え)を行う
	 * */
	public void change(JPanel panel) {
		//ContentPaneにはめ込まれたパネルを削除
		getContentPane().removeAll();

		super.add(panel);//パネルの追加
		validate();//更新
		repaint();//再描画
	}

	/*
	 * 画面遷移を監視するループ
	 * */
	@Override
	public void run() {
		while(true) {
			// 画面状態が変化したらパネルを切り替え
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

	/*
	 * getter,setterを以下に記述する
	 * */
	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}
}
