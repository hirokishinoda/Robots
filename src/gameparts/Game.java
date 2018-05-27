package gameparts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import common.BoardDefine;
import common.GameMode;
import common.Screen;

public class Game implements GameMode,BoardDefine,Screen{
	private Stage stage;
	private int level;
	private int score;
	private int game_mode;

	/*
	 * Gameクラスのコンストラクタ
	 * */
	public Game() {
		game_mode = CONTINUE;
		level = 1;
		score = 0;
		stage = new Stage(robotsNum());
	}

	/*
	 * ゲーム画面の描画
	 *
	 * ステージとスコア及びレベルの描画
	 * */
	public void draw(Graphics g) {
		stage.draw(g);
		drawScore(g);
	}

	/*
	 * スコアとレベルの表示
	 * */
	private void drawScore(Graphics g) {
		myDrawString(g,Color.BLACK,"Lv:" + level + " SCORE:" + score,10,CELLSIZE*(SIZEY+1));
	}

	/*
	 * 文字列描画処理
	 * */
	private void myDrawString(Graphics g,Color c,String str,int x,int y){
		g.setColor(c);
    	g.setFont(new Font("Arial",Font.BOLD, CELLSIZE/2));
    	g.drawString(str,x,y);
	}

	/*
	 * Game画面におけるキー入力処理
	 *
	 * キー入力処理によってメインとなる処理が
	 * 連鎖的に行われる
	 * */
	public void keyGameAction(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			game_mode = stage.flowProcessing(0, -1,false);
			System.out.println("[W]");
		}else if(e.getKeyCode() == KeyEvent.VK_A) {
			game_mode = stage.flowProcessing(-1, 0,false);
			System.out.println("[A]");
		}else if(e.getKeyCode() == KeyEvent.VK_D) {
			game_mode = stage.flowProcessing(1, 0,false);
			System.out.println("[D]");
		}else if(e.getKeyCode() == KeyEvent.VK_X) {
			game_mode = stage.flowProcessing(0, 1,false);
			System.out.println("[X]");
		}else if(e.getKeyCode() == KeyEvent.VK_S) {
			game_mode = stage.flowProcessing(0, 0,false);
			System.out.println("[S]");
		}else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			game_mode = stage.flowProcessing(0, 0,true);
			System.out.println("[ENTER]");
		}
		System.out.println(game_mode);
		calcEnemyKillScore();
		stageUp();
	}

	/*
	 * ステージアップの処理
	 *
	 * スコアの計算
	 * レベルアップ！
	 * 新たなステージの作成
	 * ゲームモードの設定
	 * */
	private void stageUp() {
		if(game_mode == STAGEUP) {
			score += level * 10 ;
			level++;
			stage = new Stage(robotsNum());
			game_mode = CONTINUE;
		}
	}

	/*
	 * スクラップになった敵の数に応じた
	 * スコアを計算する
	 * */
	private void calcEnemyKillScore() {
		score += stage.getScore();
		stage.setScore(0);
	}

	/*
	 * ロボットの数を決める
	 * */
	private int robotsNum() {
		if(level * 5 < ROBOTSMAX) {
			return level * 5;
		}
		return ROBOTSMAX;
	}

	/*
	 * getter,setterを以下に示す
	 * */
	public int getGame_mode() {
		return game_mode;
	}

	public void setGame_mode(int game_mode) {
		this.game_mode = game_mode;
	}
}
