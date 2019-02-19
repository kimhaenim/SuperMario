import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MouseEventExam extends JFrame{

	JPanel p ;

	public MouseEventExam() {

		super("색상 선택기");

		Container container = this.getContentPane();

		container.setLayout(null);

		

		p = new JPanel();

		p.setLayout(null);

		p.setBounds(100, 100, 100, 30);

		

		p.addMouseListener(new MouseListener() {

			

			@Override

			public void mouseReleased(MouseEvent e) {

				// TODO Auto-generated method stub

				

			}

			

			@Override

			public void mousePressed(MouseEvent e) {

				

			}

			

			@Override

			public void mouseExited(MouseEvent e) {

				// TODO Auto-generated method stub

				

			}

			

			@Override

			public void mouseEntered(MouseEvent e) {

				// TODO Auto-generated method stub

				

			}

			

			@Override

			public void mouseClicked(MouseEvent e) {

				System.out.println("마우스가 클릭되었습니다.");

				Color color = JColorChooser.showDialog

(MouseEventExam.this, "배경색 선택", Color.white);

				p.setBackground(color);
				System.out.println(color);
				System.out.println(JColorChooser.showDialog(MouseEventExam.this,"",Color.white));
			}
		});

		

		p.setBounds(0, 0, 300, 300);

		container.add(p);

		

		this.setBounds(0,0,400,400);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);

	}

	public static void main(String[] args) {

		// TODO Auto-generated method stub

		new MouseEventExam();

	}

}


