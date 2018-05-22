package gameparts;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import characters.Character;
import characters.Enemy;
import characters.Player;

public class Board {
	private final int SIZEX = 9;
	private final int SIZEY = 9;
	public Square [][]board;
	private CollisionManager collision_manager;
	private List <Character> characters;
	private int enemy_num;

	public Board(int enemy_num) {
		this.enemy_num = enemy_num;
		// 登場キャラの初期化
		characters = new ArrayList<Character>();
		initCharacters();
		//Boardの初期化
		initBoard();
		//衝突判定クラスのインスタンス化
		collision_manager = new CollisionManager();
	}

	private void initCharacters() {
		characters.add(new Player());
		for(int i = 0;i < enemy_num;i++) {
			characters.add(new Enemy());
		}
	}

	private void initBoard() {
		board = new Square[SIZEX+1][SIZEY+1];

		initPlaceCharacters();
	}

	public void draw(Graphics g) {
		for(int y = 0;y < SIZEY;y++) {
			for(int x = 0;x < SIZEX;x++) {
				board[x][y].draw(g,x,y);
			}
		}
		drawCharacters(g);
	}

	public void drawCharacters(Graphics g) {
		for(int i = 0;i < characters.size();i++) {
			characters.get(i).draw(g);
			System.out.println(i + " " + characters.get(i).getX() + characters.get(i).getY());
		}
	}

	private void initPlaceCharacters() {
		List<Square>init_list_board = new ArrayList<Square>();
		int enemy_count = 1;

		for(int i = 0;i < SIZEY * SIZEX;i++) {
			init_list_board.add(new Square());
		}
		init_list_board.get(0).setPlayer(true);
		for(int i = 1;i < characters.size();i++) {
			init_list_board.get(i).setEnemy_num(1);
		}

		Collections.shuffle(init_list_board);

		for(int y = 0;y < SIZEY;y++) {
			for(int x = 0;x < SIZEX;x++) {
				board[x][y] = init_list_board.get((y*SIZEX) + x);
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

	public boolean playerMove(int move_x,int move_y) {
		Player player = (Player) characters.get(0);
		int x = player.getX();
		int y = player.getY();

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

	public void enemyMove() {
		Character player = characters.get(0);

		for(int i = 1;i < characters.size();i++) {
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

	private int calcEnemyMoveX(int p_x,int e_x) {
		if(e_x > p_x) {
			return -1;
		}else if(e_x < p_x){
			return 1;
		}
		return 0;
	}

	private int calcEnemyMoveY(int p_y,int e_y) {
		if(e_y > p_y) {
			return -1;
		}else if(e_y < p_y){
			return 1;
		}
		return 0;
	}

	private boolean isArea(int x,int y) {
		if(x >= 0 && x < SIZEX) {
			if(y >= 0 && y < SIZEY) {
				return true;
			}
		}
		return false;
	}

	private boolean isEnemy(int x,int y) {
		if(board[x][y].isEnemy()) {
			return true;
		}
		return false;
	}

	public void collision() {
		Player player = (Player) characters.get(0);
		collision_manager.collisionPlayerAndEnemy(board[player.getX()][player.getY()]);

		for(int i = 1;i < characters.size();i++) {
			Enemy enemy = (Enemy) characters.get(i);

			if(collision_manager.collisionEnemyAndEnemy(board[enemy.getX()][enemy.getY()])) {
				enemy.setScrap(true);
			}
		}
	}
}
