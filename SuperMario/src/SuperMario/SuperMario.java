package SuperMario;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.*;
import javax.sound.sampled.*;

class Frame extends JFrame{
	Container c = getContentPane();
	static Clip clip;
	
	static JPanel startPane = new JPanel();
		BufferedImage StartImage = null;
		BufferedImage GameImage = null;
		JButton start = new JButton("GAME START");
	static JPanel GamePane = new JPanel();	
		static JPanel Top = new JPanel();
		static JLabel[] TopLabel = new JLabel[8];
		static ImageIcon mario = new ImageIcon("images/mario.png");
	
	class MyPanel extends JPanel{
		public void paint(Graphics g) {
			g.drawImage(StartImage,0,0,null);
		}
	}
	class MyPanel2 extends JPanel{
		public void paint(Graphics g) {
			g.drawImage(GameImage,0,0,null);
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
		GamePane.setLayout(new BorderLayout());
		GamePane.setSize(1480,875);
		GamePane.setLocation(0,0);
		try {
			GameImage = ImageIO.read(new File("images/GameImage.jpg"));
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null, "스타트 이미지 불러오기 실패");
			System.exit(0);
		}
		MyPanel2 GamePanel = new MyPanel2();
		GamePanel.setSize(1480,875);
		GamePane.add(GamePanel, BorderLayout.CENTER);
		
		Top.setLayout(new GridLayout(2,4,5,1));
		Top.setBackground(new Color(121,202,249,255));
		for(int i=0; i<TopLabel.length; i++) {
			TopLabel[i]=new JLabel("",SwingConstants.CENTER);
			TopLabel[i].setFont(TopLabel[i].getFont().deriveFont(55.0f));
			Top.add(TopLabel[i]);
		}
		TopLabel[0].setText("MARIO");
		TopLabel[1].setText("");
		TopLabel[2].setText("WORLD");
		TopLabel[3].setText("TIME");
		TopLabel[4].setText("000000");
		TopLabel[5].setText("coin X 00");
		TopLabel[6].setText("1-1");
		TopLabel[7].setText("300");
		
		Top.setLocation(0,0);
		Top.setSize(1480,150);
		GamePane.add(Top, BorderLayout.NORTH);
	}
	
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==start) {
				startPane.hide();
				c.removeAll();		
				stop_sound();
				sound("sounds/GameBgm.wav");
				c.add(GamePane);
			}
		}
	}
}
public class SuperMario {
	public static void main(String[] args) {
		Frame frame= new Frame();
	}
}
