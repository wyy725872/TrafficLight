import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import javax.swing.*;

/*��������������Ӵ�ͻ���ֳ�������,������Ϊ���������ѭ���� �����е���˫��ѭ����Ҫʱ��
���ǳ����н���ʱ���ǹ̶���,�ᴩ������Ϊ�������ﻹû�ж��泵����� �������Ѿ���ʼ��һ�ε��н�
Ҳ������������8msһ�� ������15msһ��,���ǳ������� 8ms�Ͳ�����
8ms���ϳ�������ʱ������15ms,��������ִ���
*/

class F {
	Car[] Car = new Car[20];
	int s = 0;    //����ͳ�ƴ��������ڲ�����
	Light j1 = new Light(188, 185, false);    //��һ�����̵�
	Light j2 = new Light(428, 185, true);     //�ڶ������̵�

	public void create() {
		JFrame f = new JFrame();
		Random r = new Random();
		f.setBounds(400, 150, 640, 415);//���꣬��ߣ�����ģ���640����415
		f.setLayout(null);//�����Զ���
		JPanel[] p = new JPanel[6];   //6���ɫ�ı�������
		JPanel[] q = new JPanel[6];   //6��Ŀ�ĵر������
		for (int i = 0; i < 6; i++) {
			p[i] = new JPanel();
			f.add(p[i]);
			p[i].setSize(155, 155);    //����ÿ��������Ĵ�С
			p[i].setBackground(Color.LIGHT_GRAY);
			p[i].setLayout(null);
		}
		for (int i = 0; i < 6; i++) {
			q[i] = new JPanel();
			q[i].setSize(20, 20);    //����ÿ��Ŀ�ĵؿ�Ĵ�С
			p[i].add(q[i]);
		}
		
		//���ñ������λ�ã���Ŀ�ĵر�ǿ��λ������ɫ
		
		p[0].setLocation(0, 0);
		p[1].setLocation(240, 0);
		p[2].setLocation(480, 0);
		p[3].setLocation(0, 235);
		p[4].setLocation(240, 235);
		p[5].setLocation(480, 235);
		q[0].setBackground(new Color(250, 128, 114));//���
		q[0].setLocation(0, 135);
		q[1].setBackground(new Color(210, 180, 140));//һ�ֻ�    q1��q2���趨λ����Ϊp��ÿ�����ֶ��ǿ�����Ĭ�����Ͻ�
		q[2].setBackground(new Color(0, 255, 127));//��
		q[3].setBackground(Color.ORANGE);
		q[3].setLocation(135, 131);
		q[4].setBackground(Color.PINK);
		q[4].setLocation(135, 131);
		q[5].setBackground(new Color(135, 206, 235));//��
		q[5].setLocation(135, 0);
		f.setVisible(true);
		f.setResizable(false);//��С���ɵ�
		f.addWindowListener(new WindowAdapter() {    //��Ӵ��ڼ��������ڹرգ��������
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.add(j1);//��Ӻ��̵�
		f.add(j2);//��Ӻ��̵�
		
		//��������
		
		while (true) {
			s = (s + 1) % 100;
			Check2();  //���������յ�ĳ�
			EndCheck();    //·�ھ�ͷ�ļ��
			CrossCheck();   //ʮ��·�ڵļ��
			FrontCkeck();   //ͬ����ļ�⣬����ǰ����
			LightCheck();   //���̵�ǰ�ļ��
			DeadCheck();   //����ʱ�ļ��
			if (s == 99) {//s������Ϊ���ó���ÿ8msִ��һ�ζ��ֲ�ûִ��һ������һ��������sΪ99ʱ����һ����
				int n = r.nextInt(6) + 1;
				Car c;
				while (!Check1(n)) {   //���Ҫ��������·���г�����ѡ����һ��·��
					n = (n) % 6 + 1;//Ϊ�˽��n=6ʱ·��Ϊ0�����
				}
				for (int i = 0; i < Car.length; i++) {
					if (Car[i] == null) {    //�����ǰ�����п�λ��������������λ
						c = new Car(n);
						Car[i] = c;
						f.add(c);
						break;
					}
				}
			}
			try {
				Thread.sleep(8);     //ÿ8msִ��һ��
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	//�����ں��̵Ʒ�Χ�ڣ��ж��Ƿ��ó�ͣ
	
	void LightCheck() {
		for (int i = 0; i < Car.length; i++) {
			if (Car[i] != null) {
				if (((Car[i].getLocation().y - 165 < 80 && Car[i].getLocation().y - 165 >= 0) || (205 - Car[i]
						.getLocation().y < 80 && 205 - Car[i].getLocation().y >= 0))
						&& ((Car[i].getLocation().x - 240 < 3
								&& Car[i].getLocation().x - 240 > 0 && Car[i].root == 6) || (120 - Car[i]
								.getLocation().x < 3
								&& 120 - Car[i].getLocation().x > 0 && Car[i].root == 1))) {

					if (j1.color == 0 && Car[i].l == 1) {//���ʱcar.l��ǰ��ǰ����ת�䣬1Ϊǰ��
						Car[i].T = true;     //ֹͣCar[i]
					}
				}

				if (((Car[i].getLocation().x - 165 < 80 && Car[i].getLocation().x - 165 >= 0) || (210 - Car[i]
						.getLocation().x < 80 && 210 - Car[i].getLocation().x >= 0))
						&& ((Car[i].getLocation().y - 240 < 3
								&& Car[i].getLocation().y - 240 >= 0 && Car[i].root == 2) || (120 - Car[i]
								.getLocation().y < 3
								&& 120 - Car[i].getLocation().y >= 0 && Car[i].root == 5))) {
					if (j1.color == 1 && Car[i].l == 1) {
						Car[i].T = true;
					}
				}

				if (((Car[i].getLocation().y - 165 < 80 && Car[i].getLocation().y - 165 >= 0) || (205 - Car[i]
						.getLocation().y < 80 && 205 - Car[i].getLocation().y >= 0))
						&& ((Car[i].getLocation().x - 480 < 3
								&& Car[i].getLocation().x - 480 >= 0 && Car[i].root == 6) || (360 - Car[i]
								.getLocation().x < 3
								&& 360 - Car[i].getLocation().x >= 0 && Car[i].root == 1))) {
					if (j2.color == 0 && Car[i].l == 1) {
						Car[i].T = true;
					}
				}
				if (((Car[i].getLocation().x - 405 < 80 && Car[i].getLocation().x - 405 >= 0) || (450 - Car[i]
						.getLocation().x < 80 && 450 - Car[i].getLocation().x >= 0))
						&& ((Car[i].getLocation().y - 240 < 3
								&& Car[i].getLocation().y - 240 >= 0 && Car[i].root == 3) || (120 - Car[i]
								.getLocation().y < 3
								&& 120 - Car[i].getLocation().y >= 0 && Car[i].root == 4))) {
					if (j2.color == 1 && Car[i].l == 1) {
						Car[i].T = true;
					}
				}
			}
		}
	}

	//�ж�Ҫ���ɳ���·��λ���Ƿ��г�
	
	boolean Check1(int n) {
		for (int i = 0; i < Car.length; i++) {
			if (Car[i] != null)
				if (Car[i].root == n || Car[i].root + n == 7)//·�ߵı�ţ����=7,123645��ʱ��
					switch (n) {
					case 1:
						if (Car[i].getLocation().x < 45)
							return false;
						break;
					case 2:
						if (Car[i].getLocation().y > 320)
							return false;
						break;
					case 3:
						if (Car[i].getLocation().y > 320)
							return false;
						break;
					case 4:
						if (Car[i].getLocation().y < 45)
							return false;
						break;
					case 5:
						if (Car[i].getLocation().y < 45)
							return false;
						break;
					case 6:
						if (Car[i].getLocation().x > 570)
							return false;
						break;
					}
		}
		return true;
	}

	//����г������յ㣬��������Ϊnull
	
	void Check2() {
		for (int i = 0; i < Car.length; i++) {
			if (Car[i] != null) {
				if (!Car[i].exist) {
					Car[i] = null;
				}
			}
		}
	}
	
    //�жϳ���û���ߵ�·�ھ�ͷ�ķ�Χ
	
	boolean Check3(Car c) {
		switch (7 - c.root) {//����ľ�ͷ
		case 1:
			if (c.getLocation().x < 25)
				return false;
			break;
		case 2:
			if (c.getLocation().y > 340)
				return false;
			break;
		case 3:
			if (c.getLocation().y > 340)
				return false;
			break;
		case 4:
			if (c.getLocation().y < 25)
				return false;
			break;
		case 5:
			if (c.getLocation().y < 25)
				return false;
			break;
		case 6:
			if (c.getLocation().x > 590)
				return false;
			break;
		}
		return true;
	}

	//���·�ھ�ͷ���淽���·����û�г����Ƿ������ͷ
	
	boolean Check4(Car c) {
		switch (c.root) {
		case 1:
			if (c.getLocation().x < 45)
				return false;//�г�
			break;
		case 2:
			if (c.getLocation().y > 320)
				return false;//�г�
			break;
		case 3:
			if (c.getLocation().y > 320)
				return false;
			break;
		case 4:
			if (c.getLocation().y < 45)
				return false;
			break;
		case 5:
			if (c.getLocation().y < 45)
				return false;
			break;
		case 6:
			if (c.getLocation().x > 570)
				return false;
			break;
		}
		return true;
	}

	//���Ƿ��ڶ����ķ�Χ��
	
	boolean Check6(Car c) {
		if (((c.getLocation().x <= 450 && c.getLocation().x >= 405) || (c
				.getLocation().x <= 210 && c.getLocation().x >= 165))
				&& c.getLocation().y <= 205 && c.getLocation().y >= 165) {
			return true;
		}
		return false;
	}

	//����ʱ�ó���ת��
	
	void DeadCheck() {
		for (int i = 0; i < Car.length; i++) {
			if (Car[i] != null) {
				if (Check6(Car[i])) {
					if (Car[i].Count >350) {//�����ͣ�˳���350��˵��ֹͣʱ�䳬������5�뼴���� 
						Car[i].Count = -1;//��Ƕ����������ת
						Car[i].T = false;   //��Car[i]ת�� 
			}
				}
			}
		}
	}

	//��鳵�ߵ���ͷʱ�Ƿ���Ե�ͷ
	
	void EndCheck() {
		for (int i = 0; i < Car.length; i++) {
			if (Car[i] != null && Car[i].Sw == 0) {//�����ڲ���ÿ����ֻ���һ��
				if (!Check3(Car[i])) {//���ߵ�·�ľ�ͷ
					for (int j = 0; j < Car.length; j++)
						if (Car[j] != null && Car[i].root == 7 - Car[j].root
								&& (Car[i].root + Car[i].aid != 7)) {
							if (!Check4(Car[j])) {//�����г�return false
								Car[i].T = true;    //��Car[i]ֹͣ
								Car[j].Sw = 1;//��ǳ��ѱ�ѡ���
							}
						}
				}
			}
		}
	}

	//��ʮ��·���ж��Ƿ��ó�ֹͣ
	void CrossCheck() {
		for (int i = 0; i < Car.length; i++) {
			if (Car[i] != null) {
				for (int j = 0; j < Car.length; j++) {
					if (i != j && Car[j] != null) {
						if (Car[i].root == 1
								&& ((240 - Car[i].getLocation().x < 140 && 240 - Car[i]
										.getLocation().x >= 0) || (480 - Car[i]
										.getLocation().x < 140 && 480 - Car[i]
										.getLocation().x >= 0))) {
							if (Car[j].getLocation().x - Car[i].getLocation().x >= 35
									&& Car[j].getLocation().x
											- Car[i].getLocation().x < 40
									&& Car[j].getLocation().y
											- Car[i].getLocation().y < 25
									&& Car[j].getLocation().y
											- Car[i].getLocation().y > -35) {
								Car[i].T = true;    //��Car[i]ֹͣ
							} else {
								if (Car[j].getLocation().x
										- Car[i].getLocation().x < 35
										&& Car[j].getLocation().x
												- Car[i].getLocation().x >= -25
										&& ((Car[j].getLocation().y
												- Car[i].getLocation().y < -30
												&& Car[j].getLocation().y
														- Car[i].getLocation().y > -40 && (Car[j].root == 5 || Car[j].root == 4)) || (Car[j]
												.getLocation().y
												- Car[i].getLocation().y < 30
												&& Car[j].getLocation().y
														- Car[i].getLocation().y > 25 && (Car[j].root == 2 || Car[j].root == 3)))) {
									Car[j].T = true;
								}
							}
						} else {
							if (Car[i].root == 6
									&& ((Car[i].getLocation().x - 135 < 140 && Car[i]
											.getLocation().x - 135 >= 0) || (Car[i]
											.getLocation().x - 375 < 140 && Car[i]
											.getLocation().x - 375 >= 0))) {
								if (Car[j].getLocation().x
										- Car[i].getLocation().x < -25
										&& Car[j].getLocation().x
												- Car[i].getLocation().x > -30
										&& Car[j].getLocation().y
												- Car[i].getLocation().y < 25
										&& Car[j].getLocation().y
												- Car[i].getLocation().y > -35) {
									Car[i].T = true;
								} else {
									if (Car[j].getLocation().x
											- Car[i].getLocation().x < 35
											&& Car[j].getLocation().x
													- Car[i].getLocation().x >= -25
											&& ((Car[j].getLocation().y
													- Car[i].getLocation().y < -30
													&& Car[j].getLocation().y
															- Car[i].getLocation().y > -40 && (Car[j].root == 5 || Car[j].root == 4)) || (Car[j]
													.getLocation().y
													- Car[i].getLocation().y < 30
													&& Car[j].getLocation().y
															- Car[i].getLocation().y > 25 && (Car[j].root == 2 || Car[j].root == 3)))) {
										Car[j].T = true;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	//ͬ����ǰ���г������泵ͣ
	
	void FrontCkeck() {
		for (int i = 0; i < Car.length; i++) {
			for (int j = 0; j < Car.length; j++) {
				if (i != j && Car[i] != null && Car[j] != null) {
					if (Car[i].root == 1
							&& Car[j].root == 1
							&& Car[i].getLocation().x - Car[j].getLocation().x < 35
							&& Car[i].getLocation().x - Car[j].getLocation().x >= 0) {
						Car[j].T = true;      //��Car[i]ͣ��
					} else {
						if (Car[i].root == 6
								&& Car[j].root == 6
								&& Car[i].getLocation().x
										- Car[j].getLocation().x > -35
								&& Car[i].getLocation().x
										- Car[j].getLocation().x <= 0) {
							Car[j].T = true;
						} else {
							if (((Car[i].root == 2 && Car[j].root == 2) || (Car[i].root == 3 && Car[j].root == 3))
									&& Car[i].getLocation().y
											- Car[j].getLocation().y > -35
									&& Car[i].getLocation().y
											- Car[j].getLocation().y <= 0) {
								Car[j].T = true;
							} else {
								if (((Car[i].root == 4 && Car[j].root == 4) || (Car[i].root == 5 && Car[j].root == 5))
										&& Car[i].getLocation().y
												- Car[j].getLocation().y < 35
										&& Car[i].getLocation().y
												- Car[j].getLocation().y >= 0) {
									Car[j].T = true;
								}
							}
						}
					}
				}
			}
		}
	}
}

public class Road {

	public static void main(String[] args) {
		new F().create();
	}

}