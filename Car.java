import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class Car extends JPanel {
	private static final long serialVersionUID = 1L;
	int root;    //����ǰ����·�ߵı��
	int aid;    //����Ŀ�ı��
	int n = 0;     //��Road����ĳ�����·�ڵı�ţ�����ǰѡ��W[]����ĵڼ�����
	int w[] = { 1, 0, 1, 1, 0, 1, 0, 1 };    //�����н��ķ�ʽ
	int g = 0;   //�鿴���Ƿ�����ת�������
	int l = 1;    //ָʾ��ǰ����ǰ����1����ת�䣨0��
	int flag = 0;    //ѡ����ת���·��
	int Sw = 0;     //��ǳ����Ƿ�ѡ���
	int Count = 0;     //���㳵ͣ�Ĵ���
	boolean T = false;   //�ó���ֹͣ��ǰ����trueֹͣ��false�н�
	boolean exist = true;    //�����Ƿ��������
	Random r = new Random();

	public Car(int n) {
		root = n;
		aid = r.nextInt(6) + 1;
		while (n == aid)    //�ó���Ŀ�ĵز���һ��
			aid = r.nextInt(6) + 1;
		this.setSize(20, 30);
		
		//���ݳ�������·�ڣ����ó���λ��
		
		switch (n) {
		case 1:
			this.setBounds(0, 205, 30, 20);
			break;
		case 2:
			this.setLocation(210, 355);
			break;
		case 3:
			this.setLocation(450, 355);
			break;
		case 6:
			this.setBounds(604, 165, 30, 20);
			break;
		case 4:
			this.setLocation(405, 0);
			break;
		case 5:
			this.setLocation(165, 0);
			break;
		}
		
		//���ݳ���Ŀ�ĵ����ó�����ɫ
		
		switch (aid) {
		case 1:
			this.setBackground(new Color(250, 128, 114));
			break;
		case 2:
			this.setBackground(Color.ORANGE);
			break;
		case 3:
			this.setBackground(Color.PINK);
			break;
		case 6:
			this.setBackground(new Color(135, 206, 235));
			break;
		case 4:
			this.setBackground(new Color(0, 255, 127));
			break;
		case 5:
			this.setBackground(new Color(210, 180, 140));
			break;
		}
		move();
	}

	//�жϳ��Ƿ���·��ͷ
	
	public boolean check() {
		if ((this.getLocation().x > 604 && this.getLocation().y == 205)
				|| ((this.getLocation().x == 210 || this.getLocation().x == 450) && this
						.getLocation().y < 0)
				|| ((this.getLocation().x == 405 || this.getLocation().x == 165) && this
						.getLocation().y > 355)
				|| (this.getLocation().x < 0 && this.getLocation().y == 165))
			return true;
		return false;
	}

	//��ͷ����
	
	public void turnAround() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		switch (this.root) {
		case 1:
			this.setLocation(this.getLocation().x, this.getLocation().y - 20);
			this.setSize(this.getSize().height, this.getSize().width);
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.setLocation(604, 165);
			this.setSize(this.getSize().height, this.getSize().width);
			this.root = 6;
			break;
		case 2:
			this.setLocation(this.getLocation().x - 20, this.getLocation().y);
			this.setSize(this.getSize().height, this.getSize().width);
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.setLocation(165, 0);
			this.setSize(this.getSize().height, this.getSize().width);
			this.root = 5;
			break;
		case 3:
			this.setLocation(this.getLocation().x - 20, this.getLocation().y);
			this.setSize(this.getSize().height, this.getSize().width);
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.setLocation(405, 0);
			this.setSize(this.getSize().height, this.getSize().width);
			this.root = 4;
			break;
		case 6:
			this.setLocation(this.getLocation().x, this.getLocation().y + 20);
			this.setSize(this.getSize().height, this.getSize().width);
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.setLocation(0, 205);
			this.setSize(this.getSize().height, this.getSize().width);
			this.root = 1;
			break;
		case 4:
			this.setLocation(this.getLocation().x + 20, this.getLocation().y);
			this.setSize(this.getSize().height, this.getSize().width);
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.setLocation(450, 355);
			this.setSize(this.getSize().height, this.getSize().width);
			this.root = 3;
			break;
		case 5:
			this.setLocation(this.getLocation().x + 20, this.getLocation().y);
			this.setSize(this.getSize().height, this.getSize().width);
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.setLocation(210, 355);
			this.setSize(this.getSize().height, this.getSize().width);
			this.root = 2;
			break;
		}
		this.g = 0;
	}

	//���ݳ���ǰ�������ѡ����ֱ�߻���ת��
	
	public int ChangeK(int k) {
		switch (aid) {
		case 1:
			if ((root == 5 || root == 4) && 165 - this.getLocation().y < 3
					&& 165 - this.getLocation().y > 0) {
				return 0;
			} else if (root == 6)
				return 1;
			break;
		case 2:
			if (root == 1 && 165 - this.getLocation().x < 3
					&& 165 - this.getLocation().x > 0) {
				flag = 0;
				return 0;
			} else if (root == 5)
				return 1;
			break;
		case 3:
			if (root == 1 && 405 - this.getLocation().x < 3
					&& 405 - this.getLocation().x > 0) {
				flag = 1;
				return 0;
			} else if (root == 4)
				return 1;
			break;
		case 4:
			if (root == 6 && this.getLocation().x - 450 < 3
					&& this.getLocation().x - 450 > 0) {
				flag = 0;
				return 0;
			} else if (root == 3)
				return 1;
			break;
		case 5:
			if (root == 6 && this.getLocation().x - 210 < 3
					&& this.getLocation().x - 210 > 0) {
				flag = 1;
				return 0;
			} else if (root == 2)
				return 1;
			break;
		case 6:
			if ((root == 3 || root == 2) && this.getLocation().y - 205 < 3
					&& this.getLocation().y - 205 > 0) {
				return 0;
			} else if (root == 1)
				return 1;
			break;
		}
		return k;
	}

	//��ת����
	
	public void turnRight(int k) {
		k = ChangeK(k);
		switch (this.root) {
		case 1:
			if (this.flag == 0) {
				if ((165 - this.getLocation().x < 3 && 165 - this.getLocation().x > 0)
						|| Count == -1) {
					n = (n + 1) % 8;
					l = k;
					if (k == 1) {
						return;
					} else {
						this.setLocation(165, this.getLocation().y);
						this.setSize(20, 30);
						this.root = 5;
						this.g = 1;
					}
				}
			} else {
				if ((405 - this.getLocation().x < 3 && 405 - this.getLocation().x > 0)
						|| Count == -1) {
					n = (n + 1) % 8;
					l = k;
					if (k == 1) {
						return;
					} else {
						this.setLocation(405, this.getLocation().y);
						this.setSize(20, 30);
						this.root = 4;
						this.flag = 0;
						this.g = 1;
					}
				}
			}
			break;
		case 2:
			if ((this.getLocation().y - 205 < 3 && this.getLocation().y - 205 > 0)
					|| Count == -1) {
				n = (n + 1) % 8;
				l = k;
				if (k == 1) {
					return;
				} else {
					this.setLocation(this.getLocation().x, 205);
					this.setSize(30, 20);
					this.root = 1;
					this.flag = 1;
					this.g = 1;
				}
			}
			break;
		case 3:
			if ((this.getLocation().y - 205 < 3 && this.getLocation().y - 205 > 0)
					|| Count == -1) {
				n = (n + 1) % 8;
				l = k;
				if (k == 1) {
					return;
				} else {
					this.setLocation(this.getLocation().x, 205);
					this.setSize(30, 20);
					this.root = 1;
					this.g = 1;
				}
			}
			break;
		case 6:
			if (this.flag == 0) {
				if ((this.getLocation().x - 450 < 3 && this.getLocation().x - 450 > 0)
						|| Count == -1) {
					n = (n + 1) % 8;
					l = k;
					if (k == 1) {
						return;
					} else {
						this.setLocation(450, this.getLocation().y);
						this.setSize(20, 30);
						this.root = 3;
						this.g = 1;
					}
				}
			} else {
				if ((this.getLocation().x - 210 < 3 && this.getLocation().x - 210 > 0)
						|| Count == -1) {
					n = (n + 1) % 8;
					l = k;
					if (k == 1) {
						return;
					} else {
						this.setLocation(210, this.getLocation().y);
						this.setSize(20, 30);
						this.root = 2;
						this.flag = 0;
						this.g = 1;
					}
				}
			}
			break;
		case 4:
			if ((165 - this.getLocation().y < 3 && 165 - this.getLocation().y > 0)
					|| Count == -1) {
				n = (n + 1) % 8;
				l = k;
				if (k == 1) {
					return;
				} else {
					this.setLocation(this.getLocation().x, 165);
					this.setSize(30, 20);
					this.root = 6;
					this.flag = 1;
					this.g = 1;
				}
			}
			break;
		case 5:
			if ((165 - this.getLocation().y < 3 && 165 - this.getLocation().y > 0)
					|| Count == -1) {
				n = (n + 1) % 8;
				l = k;
				if (k == 1) {
					return;
				} else {
					this.setLocation(this.getLocation().x, 165);
					this.setSize(30, 20);
					this.root = 6;
					this.g = 1;
				}
			}
			break;
		}
	}

	//�������ƶ�����
	
	public void move() {
		final Car c = this;
		new Thread() {    //�������߳�
			public void run() {
				int u = 0;
				while (true) {
					try {
						sleep(15);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					u++;
					c.Sw = 0;
					if (c.T) {    //�жϳ���ͣ����ǣ�������ǰ�Ƿ�Ҫͣ��
						try {
							Thread.sleep(30);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						c.Count++;
						c.T = false;    //�ó����ı�Ǹ�Ϊ�н�
						u = 0;
					}
					if (u != 0) {
						if (c.Count == -1) {      //��������ʱ���ó���ת��
							turnRight(0);
						}
						c.Count = 0;
						switch (c.root) {     //�ó����������ڵ�·�ߣ�������Ӧ���˶�
						case 1:
							c.setLocation(c.getLocation().x + 2,
									c.getLocation().y);
							break;
						case 2:
							c.setLocation(c.getLocation().x,
									c.getLocation().y - 2);
							break;
						case 3:
							c.setLocation(c.getLocation().x,
									c.getLocation().y - 2);
							break;
						case 6:
							c.setLocation(c.getLocation().x - 2,
									c.getLocation().y);
							break;
						case 4:
							c.setLocation(c.getLocation().x,
									c.getLocation().y + 2);
							break;
						case 5:
							c.setLocation(c.getLocation().x,
									c.getLocation().y + 2);
							break;
						}
						if (c.getLocation().x == c.getLocation().y
								&& c.getLocation().y == 0)
							c.exist = false;
						if (((405 - c.getLocation().x < 5 && c.getLocation().y == 205) || (c
								.getLocation().x - 210 < 5 && c.getLocation().y == 165))
								&& c.flag == 1) {       //�жϳ����Ƿ񵽴�ת���λ��
							c.g = 0;
						}
						if (c.g == 0) {
							turnRight(w[n]);     //���ݵ�ǰ������洢��ֵ��ѡ������ǰ����ת��
						}
						if (check()) {
							if ((c.root == 1 && c.aid == 6)
									|| (c.root == 2 && c.aid == 5)
									|| (c.root == 3 && c.aid == 4)
									|| (c.root == 6 && c.aid == 1)
									|| (c.root == 4 && c.aid == 3)
									|| (c.root == 5 && c.aid == 2)) {
								c.setVisible(false);     //�жϳ����Ƿ񵽴�Ŀ�ĵأ��������������������ʧ��·��
								c.exist = false;
								c.getParent().remove(c);
								break;
							} else
								turnAround();
						}
					}
				}
			}
		}.start();

	}
}
