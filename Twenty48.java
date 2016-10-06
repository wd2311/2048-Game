package twenty48;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Twenty48 extends JFrame implements KeyListener{
	
	int[][] board = new int[4][4];
	int[][] a = new int[4][4];
	Random r = new Random();
	static int score;
	static int biggestTile;
	
	public static void main(String[] args) {
		go();
	}//main
	
	private static void go(){
		Twenty48 instance = new Twenty48();
		instance.setLayout(null);
		instance.setTitle("2048 -- by Will David");
		instance.setResizable(false);
		instance.setSize(460,530);
		instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		instance.setBackground(Color.WHITE);
		instance.setVisible(true);
		instance.run();
	}//go
	
	private void run(){
		addKeyListener(this);
		try{
			Thread.sleep(100);
		}catch(InterruptedException e){
			e.printStackTrace();
		}//try catch
		createBufferStrategy(2);
		graphicStuff();
	}//run
	
	private void graphicStuff(){
		BufferStrategy b = getBufferStrategy();
		Graphics2D g = (Graphics2D)b.getDrawGraphics();
		g.setFont(new Font("", Font.BOLD, 24));
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 80, 460, 450);
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				if(board[i][j] > 0){
					g.setColor(new Color(255-getPowersOfTwo(board[i][j])*255/11 , getPowersOfTwo(board[i][j])*255/11, 0*getPowersOfTwo(board[i][j])*255/11));
				}else{
					g.setColor(Color.LIGHT_GRAY);
				}
				g.fillRoundRect(i*105 + 30, j*105 + 100, 85, 85, 20, 20);//end location
				if(board[i][j] > 0){
					if(board[i][j] >= 8){
						g.setColor(Color.WHITE);
					}else{//if it's 8 or higher
						g.setColor(Color.BLACK);
					}//if it's 2 or 4
					g.drawString("" + board[i][j], i * 105 + 45, j * 105 + 150);
				}//if there's a tile
			}//for j
		}//for i
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, 460, 80);
		g.setColor(Color.RED);
		g.drawString("Score: " + score, 40, 65);
		if(checkLoss()){
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, 460, 530);
			g.setColor(Color.MAGENTA);
			g.drawString("You Lose!", 170, 100);
			g.setColor(Color.CYAN);
			g.drawString("Press 'r' to Play Again!", 100, 150);
			g.setColor(Color.RED);
			g.drawString("Your Score was: " + score + "!", 80, 225);
			g.setColor(Color.BLUE);
			g.drawString("Your Highest Tile was: " + biggestTile + "!", 80, 275);
		}
		b.show();
	}//init
	
	public Twenty48(){
		spawnTwoOrFour();
    	spawnTwoOrFour();
	}//Constructor
	
	public void spawnTwoOrFour(){
		int counter = 0;
		for(int x = 0; x < 4; x++){
			for(int y = 0; y < 4; y++){
				if(board[x][y] == 0){
					counter =+ 1;
				}
			}
		}
		if(counter > 0){
			int t = r.nextInt(10);
			int x = r.nextInt(4);
			int y = r.nextInt(4);
			if(board[x][y] == 0){
				if(t == 9){
					board[x][y] = 4;
				}else{
					board[x][y] = 2;
				}//10% chance spawn a 4, 90% chance spawn a 2
			}else{
				spawnTwoOrFour();
			}//if it's not an open spot, re-run
		}//if there's an open spot on the board at all
	}//spawnTwoOrFour
	
	public void boardUp(){
		for(int x = 0; x < 4; x ++){
			for(int i = 0; i < 3; i ++){
				if(board[x][0] == 0){
					board[x][0] = board[x][1];
					board[x][1] = board[x][2];
					board[x][2] = board[x][3];
					board[x][3] = 0;
				}
			}
			for(int i = 0; i < 2; i ++){
				if(board[x][1] == 0){
					board[x][1] = board[x][2];
					board[x][2] = board[x][3];
					board[x][3] = 0;
				}
			}
			if(board[x][2] == 0){
				board[x][2] = board[x][3];
				board[x][3] = 0;
			}
		}
		for(int x = 0; x < 4; x++){
			for(int y = 0; y < 4; y ++){
				if(y < 3){
					if(board[x][y] > 0){
						if(board[x][y] == board[x][y + 1]){
							board[x][y] = board[x][y]*2;
							board[x][y + 1] = 0;
							score = score + board[x][y];
						}//equal to below
					}//a number is there
				}
			}//for y
		}//for x
		for(int x = 0; x < 4; x ++){
			for(int i = 0; i < 3; i ++){
				if(board[x][0] == 0){
					board[x][0] = board[x][1];
					board[x][1] = board[x][2];
					board[x][2] = board[x][3];
					board[x][3] = 0;
				}
			}
			for(int i = 0; i < 2; i ++){
				if(board[x][1] == 0){
					board[x][1] = board[x][2];
					board[x][2] = board[x][3];
					board[x][3] = 0;
				}
			}
			if(board[x][2] == 0){
				board[x][2] = board[x][3];
				board[x][3] = 0;
			}
		}
	}//boardUp
	
	public void boardDown(){
		for(int x = 0; x < 4; x ++){
			for(int i = 0; i < 3; i ++){
				if(board [x][3] == 0){
					board[x][3] = board[x][2];
					board[x][2] = board[x][1];
					board[x][1] = board[x][0];
					board[x][0] = 0;
				}
			}
			for(int i = 0; i < 2; i++){
				if(board[x][2] == 0){
					board[x][2] = board[x][1];
					board[x][1] = board[x][0];
					board[x][0] = 0;
				}
			}
			if(board[x][1] == 0){
				board[x][1] = board[x][0];
				board[x][0] = 0;
			}
		}
		for(int x = 0; x < 4; x++){
			for(int y = 3; y >= 0; y --){
				if(y > 0){
					if(board[x][y] > 0){
						if(board[x][y] == board[x][y - 1]){
							board[x][y] = board[x][y]*2;
							board[x][y - 1] = 0;
							score = score + board[x][y];
						}//equal to below
					}//a number is there
				}
			}//for y
		}//for x
		for(int x = 0; x < 4; x ++){
			for(int i = 0; i < 3; i ++){
				if(board [x][3] == 0){
					board[x][3] = board[x][2];
					board[x][2] = board[x][1];
					board[x][1] = board[x][0];
					board[x][0] = 0;
				}
			}
			for(int i = 0; i < 2; i++){
				if(board[x][2] == 0){
					board[x][2] = board[x][1];
					board[x][1] = board[x][0];
					board[x][0] = 0;
				}
			}
			if(board[x][1] == 0){
				board[x][1] = board[x][0];
				board[x][0] = 0;
			}
		}
	}//boardDown
	
	public void boardRight(){
		for(int y = 0; y < 4; y++){
			for(int i = 0; i < 3; i ++){
				if(board[3][y] == 0){
					board[3][y] = board[2][y];
					board[2][y] = board[1][y];
					board[1][y] = board[0][y];
					board[0][y] = 0;
				}
			}
			for(int i = 0; i < 2; i ++){
				if(board[2][y] == 0){
					board[2][y] = board[1][y];
					board[1][y] = board[0][y];
					board[0][y] = 0;
				}
			}
			if(board[1][y] == 0){
				board[1][y] = board[0][y];
				board[0][y] = 0;
			}
		}
		for(int x = 3; x >= 0; x--){
			for(int y = 0; y < 4; y ++){
				if(x > 0){
					if(board[x][y] > 0){
						if(board[x][y] == board[x - 1][y]){
							board[x][y] = board[x][y]*2;
							board[x - 1][y] = 0;
							score = score + board[x][y];
						}//equal to below
					}//a number is there
				}
			}//for y
		}//for x
		for(int y = 0; y < 4; y++){
			for(int i = 0; i < 3; i ++){
				if(board[3][y] == 0){
					board[3][y] = board[2][y];
					board[2][y] = board[1][y];
					board[1][y] = board[0][y];
					board[0][y] = 0;
				}
			}
			for(int i = 0; i < 2; i ++){
				if(board[2][y] == 0){
					board[2][y] = board[1][y];
					board[1][y] = board[0][y];
					board[0][y] = 0;
				}
			}
			if(board[1][y] == 0){
				board[1][y] = board[0][y];
				board[0][y] = 0;
			}
		}
	}//boardRight
	
	public void boardLeft(){
		for(int y = 0; y < 4; y++){
			for(int i = 0; i < 3; i ++){
				if(board[0][y] == 0){
					board[0][y] = board[1][y];
					board[1][y] = board[2][y];
					board[2][y] = board[3][y];
					board[3][y] = 0;
				}
			}
			for(int i = 0; i < 2; i ++){
				if(board[1][y] == 0){
					board[1][y] = board[2][y];
					board[2][y] = board[3][y];
					board[3][y] = 0;
				}
			}
			if(board[2][y] == 0){
				board[2][y] = board[3][y];
				board[3][y] = 0;
			}
		}
		for(int x = 0; x < 4; x++){
			for(int y = 0; y < 4; y ++){
				if(x < 3){
					if(board[x][y] > 0){
						if(board[x][y] == board[x + 1][y]){
							board[x][y] = board[x][y]*2;
							board[x + 1][y] = 0;
							score = score + board[x][y];
						}//equal to below
					}//a number is there
				}
			}//for y
		}//for x
		for(int y = 0; y < 4; y++){
			for(int i = 0; i < 3; i ++){
				if(board[0][y] == 0){
					board[0][y] = board[1][y];
					board[1][y] = board[2][y];
					board[2][y] = board[3][y];
					board[3][y] = 0;
				}
			}
			for(int i = 0; i < 2; i ++){
				if(board[1][y] == 0){
					board[1][y] = board[2][y];
					board[2][y] = board[3][y];
					board[3][y] = 0;
				}
			}
			if(board[2][y] == 0){
				board[2][y] = board[3][y];
				board[3][y] = 0;
			}
		}
	}//boardLeft
	
	public boolean checkLoss(){
		for(int x = 0; x < 4; x++){
			for(int y = 0; y < 4; y++){
				if(board[x][y] == 0) return false;
				if(x < 3) if(board[x][y] == board[x + 1][y]) return false;
				if(x > 0) if(board[x][y] == board[x - 1][y]) return false;
				if(y > 0) if(board[x][y] == board[x][y - 1]) return false;
				if(y < 3) if(board[x][y] == board[x][y + 1]) return false;
			}//for y
		}//for x
		return true;
	}//checkLoss
	
	private static int getPowersOfTwo(int num) {
		if(num >= 2){
			return 1 + getPowersOfTwo(num / 2);
		}
		if(num == 1){
			return 0;
		}
		return 0;
	}//getPowersOfTwo
	
	public void keyPressed(KeyEvent p) {
		for(int x = 0; x < 4; x++){
			for(int y = 0; y < 4; y++){
				a[x][y] = board[x][y];
			}//for y
		}//for x
		if((p.getKeyCode() == KeyEvent.VK_UP) || (p.getKeyCode() == KeyEvent.VK_W)){
			boardUp();
		}//UpArrowKey
		if((p.getKeyCode() == KeyEvent.VK_DOWN) || (p.getKeyCode() == KeyEvent.VK_S)){
			boardDown();
		}//DownArrowKey
		if((p.getKeyCode() == KeyEvent.VK_RIGHT) || (p.getKeyCode() == KeyEvent.VK_D)){
			boardRight();
		}//RightArrowKey
		if((p.getKeyCode() == KeyEvent.VK_LEFT) || (p.getKeyCode() == KeyEvent.VK_A)){
			boardLeft();
		}//leftArrowKey
		for(int x = 0; x < 4; x ++){
			for(int y = 0; y < 4; y ++){
				if(board[x][y] > biggestTile) biggestTile = board[x][y];
			}//for y
		}//for x
		if(p.getKeyCode() == KeyEvent.VK_R){
			for(int x = 0; x < 4; x++) for(int y = 0; y < 4; y++) board[x][y] = 0;
			score = 0;
			biggestTile = 0;
			spawnTwoOrFour();
			spawnTwoOrFour();
		}
		graphicStuff();
		if(p.getKeyCode() != KeyEvent.VK_R){
			int counter = 0;
			for(int x = 0; x < 4; x++){
				for(int y = 0; y < 4; y++){
					if(board[x][y] == a[x][y]) counter = counter + 1;
				}//for y
			}//for x
			if(counter < 16){ 
				try{
					Thread.sleep(200);
				}catch(Exception e){
				}
				spawnTwoOrFour(); 
				graphicStuff();
				try{
					Thread.sleep(25);
				}catch(Exception e){
				}
			}
		}
	}//keyPressed
	public void keyReleased(KeyEvent r) {
	}//keyReleased
	public void keyTyped(KeyEvent t) {
	}//keyTyped
}//class