package panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import common.Screen;
import main.MyFrame;


public class StartPanel extends JPanel implements KeyListener,Screen{
	private MyFrame my_frame;

	public StartPanel(MyFrame frame) {
		this.my_frame = frame;

		my_frame.addKeyListener(this);

		setLayout(null);

		setBackground(Color.black);
		// パネルサイズの設定
		setPreferredSize(new Dimension(MY_WIDTH, MY_HEIGHT));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		myDrawString(g,Color.white,"Robots!",TITLEX,TITLEY,75);
		myDrawString(g,Color.white,"--Press [SPACE]key--",SUBTITLEX,SUBTITLEY,25);
	}

	/*
	 * 文字列を指定位置、指定色で描画するメソッド
	 */
	private void myDrawString(Graphics g,Color c,String str,int x,int y,int str_size){
		g.setColor(c);
    	g.setFont(new Font("Arial",Font.BOLD, str_size));
    	g.drawString(str, x, y);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			my_frame.removeKeyListener(this);
			my_frame.setState(GAME);
			System.out.println(e.getKeyCode()+"MENU");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}


}
