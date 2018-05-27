package common;

public interface Screen {
	// 画面の大きさ
	public static final int MY_WIDTH = 1000;
	public static final int MY_HEIGHT = 680;
	// タイトルの表示位置
	public static final int TITLEX = MY_WIDTH/2 - MY_WIDTH/2/2/2;
    public static final int TITLEY = 250;
    //
    public static final int SUBTITLEY = TITLEY + 50;
    public static final int SUBTITLEX = TITLEX + 10;
	// 画面の状態
	public static final int MENU = 0;
	public static  final int GAME = 1;
	// 表示するマスのサイズ
	public final int CELLSIZE = 32;
}
