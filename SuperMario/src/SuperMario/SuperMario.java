package SuperMario;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.*;
import javax.sound.sampled.*;
class cg_mario extends Thread{
	private JLabel M;
	ImageIcon mario = new ImageIcon("images/mario.png");
	public cg_mario(JLabel M) {
		this.M = M;
	}
	public void run() {
		int n=500;
		while(true) {
			M.setIcon(mario);
		try {
			Thread.sleep(n);
			}catch(InterruptedException e) {
				return;
			}
		}
	}
}
class cg_coin extends Thread{
	private JLabel COIN;
	ImageIcon COIN1 = new ImageIcon("images/COIN1.png");
	ImageIcon COIN2 = new ImageIcon("images/COIN2.png");
	public cg_coin(JLabel COIN) {
		this.COIN = COIN;
	}
	public void run() {
		int n=30;
		while(true) {
			n--;
			if(n==20) {
				COIN.setIcon(COIN2);
			}
			if(n==10) {
				COIN.setIcon(COIN1);
			}
			if(n==0) {
				n=30;
			}
		try {
			Thread.sleep(n);
			}catch(InterruptedException e) {
				return;
			}
		}
	}
}
class jump_mario extends Thread{
	private JLabel M;
	public jump_mario(JLabel M) {
		this.M=M;
	}
	public void run() {
		int n=20;
		while(true) {
			n--;
			for(int i=19; i>=0; i--) {
			if(n==i) {
				M.setLocation(M.getX(), M.getY()+10);
				}
			}
			if(n==0)
				return;
		try {
			Thread.sleep(n);
			}catch(InterruptedException e) {
				return;
			}
		}
	}
}
class Frame extends JFrame{
	Container c = getContentPane();
	static Clip clip;
	
	static JPanel startPane = new JPanel();
		static BufferedImage StartImage = null;
		static BufferedImage GameImage = null;
		JButton start = new JButton("GAME START");
	
	static JPanel GamePane = new JPanel() {
		public void paintComponent(Graphics g) {
			g.drawImage(GameImage,0,0,null);
			setOpaque(false);
			super.paintComponent(g);
		}
	};
		static JPanel Top = new JPanel();
		static JLabel[] TopLabel = new JLabel[8];
		static ImageIcon marioface = new ImageIcon("images/marioface.png");
		static ImageIcon coin = new ImageIcon("images/coin.png");
		static ImageIcon COIN1 = new ImageIcon("images/COIN1.png");
		static ImageIcon COIN2 = new ImageIcon("images/COIN2.png");
		static ImageIcon mario = new ImageIcon("images/mario.png");
		static ImageIcon marioright = new ImageIcon("images/marioright.png");
		static ImageIcon marioleft = new ImageIcon("images/marioleft.png");
		static ImageIcon mariodown = new ImageIcon("images/mariodown.png");
		
		static JLabel M = new JLabel();
		static JLabel COIN = new JLabel();
		static cg_coin CC = new cg_coin(COIN);
		static cg_mario CM = new cg_mario(M);
		
	class MyPanel extends JPanel{
		public void paint(Graphics g) {
			g.drawImage(StartImage,0,0,null);
		}
	}
	public static void sound(String file) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void stop_sound() {
		clip.stop();
		clip.close();
	}
	private class MyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                int keyCode = e.getKeyCode();
    			switch(keyCode) {
    			case KeyEvent.VK_UP:
    				//M.setLocation(M.getX(), M.getY()-10);
    				break;
    			case KeyEvent.VK_DOWN:
    				M.setIcon(mariodown);
    				if(CM.getState()==Thread.State.NEW) {
    					CM.start();
    				}
    				break;
    			case KeyEvent.VK_RIGHT:
    				M.setLocation(M.getX()+10, M.getY());
    				M.setIcon(marioright);
    				if(CM.getState()==Thread.State.NEW) {
    					CM.start();
    				}
    				break;
    			case KeyEvent.VK_LEFT:
    				M.setLocation(M.getX()-10, M.getY());
    				M.setIcon(marioleft);
    				if(CM.getState()==Thread.State.NEW) {
    					CM.start();
    				}
    				break;
    			case KeyEvent.VK_SPACE: //jump
    				jump_mario JM = new jump_mario(M);
    				M.setLocation(M.getX(), M.getY()-200);
    				sound("sounds/JumpBgm.wav");
    				if(JM.getState()==Thread.State.NEW) {
    					JM.start();
    				}	
    				break;
    			}
            } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                //released
            } else if (e.getID() == KeyEvent.KEY_TYPED) {
                //typed
            }
            return false;
        }
    }
	public Frame() {
		setTitle("!super mario!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		//StartPane
		sound("sounds/StartBgm.wav");
		try {
			StartImage = ImageIO.read(new File("images/start.jpg"));
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "스타트 이미지 불러오기 실패");
			System.exit(0);
		}
		MyPanel panel = new MyPanel();
		panel.setSize(1480,875);
		startPane.setLayout(null);
		startPane.add(panel);
		startPane.setSize(1480,875);
		startPane.setLocation(0,0);
		start.setFont(start.getFont().deriveFont(39.0f));
		start.setBackground(Color.BLACK);
		start.setForeground(Color.WHITE);
		start.addActionListener(new MyActionListener());
		start.setSize(500,100);
		start.setLocation(130, 370);
		startPane.add(start);
		c.add(startPane);
		setSize(1480,875);
		setVisible(true);
		
		//GamePane
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyDispatcher());
		GamePane.setLayout(null);
		try {
			GameImage = ImageIO.read(new File("images/GameImage.jpg"));
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "게임배경 이미지 불러오기 실패");
			System.exit(0);
		}
		
		Top.setLayout(new GridLayout(2,4,5,1));
		Top.setBackground(new Color(121,202,249,255));
		for(int i=0; i<TopLabel.length; i++) {
			TopLabel[i]=new JLabel("",SwingConstants.CENTER);
			TopLabel[i].setFont(TopLabel[i].getFont().deriveFont(55.0f));
			Top.add(TopLabel[i]);
		}
		TopLabel[0].setIcon(marioface);
		TopLabel[0].setSize(75,75);
		TopLabel[1].setIcon(coin);
		TopLabel[1].setSize(60,60);
		TopLabel[2].setText("WORLD");
		TopLabel[3].setText("TIME");
		TopLabel[4].setText("3");
		TopLabel[5].setText("00");
		TopLabel[6].setText("1-1");
		TopLabel[7].setText("300");
		
		Top.setLocation(0,0);
		Top.setSize(1480,150);
		GamePane.add(Top);
		
		COIN.setIcon(COIN1);
		COIN.setSize(70,70);
		COIN.setLocation(300,400);
		GamePane.add(COIN);
		M.setIcon(mario);
		M.setLocation(70,580);
		M.setSize(76,97);
		GamePane.add(M);
		CC.start();
	}
	
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==start) {
				startPane.hide();
				c.removeAll();
				stop_sound();
				sound("sounds/GameBgm.wav");
				c.add(GamePane);
				GamePane.setBounds(0,0,1480,876);
				GamePane.show();
			}
		}
	}
	
}
public class SuperMario {
	public static void main(String[] args) {
		Frame frame= new Frame();
	}
}
