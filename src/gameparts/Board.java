package gameparts;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import characters.Character;
import characters.Enemy;
import characters.Player;
import common.BoardDefine;

public class Board implements BoardDefine{
	public Square [][]board;
	private CollisionManager collision_manager;
	private List <Character> characters;
	private int current_enemy_num;
	private Random rnd;

	/*
	 * Boardクラスのコンストラクタ
	 * total_enemy_num : 初期の敵の数
	 * */
	public Board(int total_enemy_num) {
		setCurrent_enemy_num(total_enemy_num);
		// 登場キャラの初期化
		characters = new ArrayList<Character>();
		initCharacters(total_enemy_num);
		//Boardの初期化
		initBoard();
		//衝突判定クラスのインスタンス化
		collision_manager = new CollisionManager();
		//random初期化
		rnd = new Random();
	}

	/*
	 * キャラクターの初期化
	 * プレイヤーと指定数の敵を生成
	 * リストとして管理する
	 * */
	private void initCharacters(int total_enemy_num) {
		characters.add(new Player());
		for(int i = 0;i < total_enemy_num;i++) {
			characters.add(new Enemy());
		}
	}

	/*
	 * Boardの初期化
	 * キャラクターの初期配置も決定
	 * */
	private void initBoard() {
		board = new Square[SIZEX+1][SIZEY+1];
		// 	キャラクターの初期配置決定
		initPlaceCharacters();
	}

	/*
	 * 描画をする
	 * */
	public void draw(Graphics g) {
		// ボード(ます)を描画
		for(int y = 0;y < SIZEY;y++) {
			for(int x = 0;x < SIZEX;x++) {
				board[x][y].draw(g,x,y);
			}
		}
		// キャラクターを描画
		drawCharacters(g);
	}

	/*
	 * キャラクターの描画
	 * */
	public void drawCharacters(Graphics g) {
		for(int i = 0;i < characters.size();i++) {
			characters.get(i).draw(g);
		}
	}

	/*
	 * キャラクターの初期配置を決定する
	 * */
	private void initPlaceCharacters() {
		List<Square>init_list_board = new ArrayList<Square>();
		int enemy_count = 1;

		// ボードをリストとして生成
		for(int i = 0;i < SIZEY * SIZEX;i++) {
			init_list_board.add(new Square());
		}
		// Playerの情報取得＆配置
		init_list_board.get(0).setPlayer(true);
		// 敵の情報を取得＆配置
		for(int i = 1;i < characters.size();i++) {
			init_list_board.get(i).setEnemy_num(1);
		}

		// 配置をシャッフルし、ランダム配置
		Collections.shuffle(init_list_board);

		// 配置が完了したリストから情報を取り出し、配列に格納する。
		// キャラクターのリストにも座標を格納。
		for(int y = 0;y < SIZEY;y++) {
			for(int x = 0;x < SIZEX;x++) {
				// リストから配列へ
				board[x][y] = init_list_board.get((y*SIZEX) + x);
				// Player、敵の座標をキャラクター配列へ格納
				if(init_list_board.get((y*SIZEX) + x).isPlayer()) {
					characters.get(0).setX(x);
					characters.get(0).setY(y);
				}else if(init_list_board.get((y*SIZEX) + x).getEnemy_num() == 1) {
					characters.get(enemy_count).setX(x);
					characters.get(enemy_count++).setY(y);
				}
			}
		}
	}

	/*
	 * プレイヤーの移動処理
	 * */
	public boolean playerMove(int move_x,int move_y) {
		// プレイヤーの情報取得
		Player player = (Player) characters.get(0);
		int x = player.getX();
		int y = player.getY();

		// 移動先がエリア内であるか判定
		// 移動先に敵がいないか判定
		if(isArea(x + move_x,y + move_y)) {
			if(!isEnemy(x + move_x, y + move_y)) {
				// boardから削除
				board[player.getX()][player.getY()].setPlayer(false);
				// playerの座標を移動
				player.move(move_x,move_y);
				// boardに登録
				board[player.getX()][player.getY()].setPlayer(true);

				return true;
			}
		}

		return false;
	}

	/*
	 * プレイヤーのワープ処理
	 * */
	public boolean playerWarp() {
		// プレイヤーの情報取得
		Player player = (Player) characters.get(0);
		List<BucketSquare> board_list = new ArrayList<BucketSquare>();

		// ボードから空白ますだけを取得し、
		// リストを作成する。
		for(int y = 0;y < SIZEY;y++) {
			for(int x = 0;x < SIZEX;x++) {
				if(!isEnemy(x,y)) {
					board_list.add(new BucketSquare(x,y));
				}
			}
		}

		System.out.println("[BoardSize] = " + board_list.size());
		// 空白ますを集めたリストからランダムに移動場所を決定
		int x = board_list.get(rnd.nextInt(board_list.size())).x;
		int y = board_list.get(rnd.nextInt(board_list.size())).y;
		// プレイヤー移動
		playerMove(x - player.getX(),y - player.getY());

		return true;
	}

	/*
	 * 敵の移動処理
	 * */
	public void enemyMove() {
		// プレイヤーを取得
		Character player = characters.get(0);

		// 全ての敵に対して移動処理を行う
		for(int i = 1;i < characters.size();i++) {
			// 敵の情報を取得
			Enemy enemy = (Enemy) characters.get(i);
			int move_x = calcEnemyMoveX(player.getX(),enemy.getX());
			int move_y = calcEnemyMoveY(player.getY(),enemy.getY());
			//board上の現在の敵の位置から削除
			board[enemy.getX()][enemy.getY()].decreaseEnemyNum();
			//敵の座標移動
			enemy.move(move_x,move_y);
			//board上の新たな場所に登録
			board[enemy.getX()][enemy.getY()].increaseEnemyNum();
		}
	}

	/*
	 * 衝突判定の指示を行うクラス
	 * */
	public boolean collision() {
		// Playerの情報取得
		Player player = (Player) characters.get(0);

		// Playerと敵の衝突判定
		if(collision_manager.collisionPlayerAndEnemy(board[player.getX()][player.getY()])) {
			return true;
		}

		// 敵同士の衝突判定
		for(int i = 1;i < characters.size();i++) {
			// 敵の情報取得
			Enemy enemy = (Enemy) characters.get(i);

			// 敵(今調べる対象)がスクラップではないなら、判定を行う
			// 動いている敵にのみ行うという意味
			if(!enemy.isScrap()) {
				// 衝突判定(敵同士)
				if(collision_manager.collisionEnemyAndEnemy(board[enemy.getX()][enemy.getY()])) {
					enemy.setScrap(true); // 衝突したらスクラップ
					current_enemy_num--; //　衝突したら生存している敵の数を減らす
					System.out.println("Scrap!");
				}
			}
		}


		return false;
	}

	/*
	 * 敵のチェイス(追跡)座標計算を行う
	 * X座標
	 * */
	private int calcEnemyMoveX(int p_x,int e_x) {
		if(e_x > p_x) {
			return -1;
		}else if(e_x < p_x){
			return 1;
		}
		return 0;
	}

	/*
	 * 敵のチェイス(追跡)座標計算を行う
	 * Y座標
	 * */
	private int calcEnemyMoveY(int p_y,int e_y) {
		if(e_y > p_y) {
			return -1;
		}else if(e_y < p_y){
			return 1;
		}
		return 0;
	}

	/*
	 * 指定座標がエリア内であるかの確認を行う
	 * */
	private boolean isArea(int x,int y) {
		if(x >= 0 && x < SIZEX) {
			if(y >= 0 && y < SIZEY) {
				return true;
			}
		}
		return false;
	}

	/*
	 * 指定座標に敵がいるかどうかの判定を行う
	 * */
	private boolean isEnemy(int x,int y) {
		if(board[x][y].isEnemy()) {
			return true;
		}
		return false;
	}


	/*
	 * クリアの判定
	 * */
	public boolean isClear() {
		for(int i = 1;i < characters.size();i++) {
			Enemy enemy = (Enemy) characters.get(i);
			if(!enemy.isScrap()) {
				return false;
			}
		}
		return true;
	}

	/*
	 * getter,setterを以下に記述する
	 * */
	public int getCurrent_enemy_num() {
		return current_enemy_num;
	}

	public void setCurrent_enemy_num(int current_enemy_num) {
		this.current_enemy_num = current_enemy_num;
	}
}


/*
 * ワープ先を決定する際に使用するクラス
 * (使用用途は、これのみ)
 *
 * 空白のますの座標を格納する
 * Squareクラスの簡易版
 * */
class BucketSquare{
	int y;
	int x;

	public BucketSquare(int x,int y) {
		this.x = x;
		this.y = y;
	}
}
