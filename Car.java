import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class Car extends JPanel {
	private static final long serialVersionUID = 1L;
	int root;    //车当前所在路线的编号
	int aid;    //车的目的编号
	int n = 0;     //有Road传入的车产生路口的编号，及当前选择到W[]数组的第几个数
	int w[] = { 1, 0, 1, 1, 0, 1, 0, 1 };    //车辆行进的方式
	int g = 0;   //查看车是否满足转弯的条件
	int l = 1;    //指示当前车辆前进（1）或转弯（0）
	int flag = 0;    //选择车辆转弯的路口
	int Sw = 0;     //标记车辆是否被选择过
	int Count = 0;     //计算车停的次数
	boolean T = false;   //让车辆停止或前进，true停止，false行进
	boolean exist = true;    //车辆是否完成任务
	Random r = new Random();

	public Car(int n) {
		root = n;
		aid = r.nextInt(6) + 1;
		while (n == aid)    //让车与目的地不是一个
			aid = r.nextInt(6) + 1;
		this.setSize(20, 30);
		
		//根据车产生的路口，放置车的位置
		
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
		
		//根据车的目的地设置车的颜色
		
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

	//判断车是否到了路尽头
	
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

	//掉头函数
	
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

	//根据车当前的情况，选择是直走还是转弯
	
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

	//右转函数
	
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

	//车辆的移动函数
	
	public void move() {
		final Car c = this;
		new Thread() {    //车辆的线程
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
					if (c.T) {    //判断车的停车标记，决定当前是否要停车
						try {
							Thread.sleep(30);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						c.Count++;
						c.T = false;    //让车辆的标记改为行进
						u = 0;
					}
					if (u != 0) {
						if (c.Count == -1) {      //车辆堵死时，让车辆转弯
							turnRight(0);
						}
						c.Count = 0;
						switch (c.root) {     //让车辆根据所在的路线，进行相应的运动
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
								&& c.flag == 1) {       //判断车辆是否到达转弯的位置
							c.g = 0;
						}
						if (c.g == 0) {
							turnRight(w[n]);     //根据当前数组里存储的值，选择车辆的前进或转弯
						}
						if (check()) {
							if ((c.root == 1 && c.aid == 6)
									|| (c.root == 2 && c.aid == 5)
									|| (c.root == 3 && c.aid == 4)
									|| (c.root == 6 && c.aid == 1)
									|| (c.root == 4 && c.aid == 3)
									|| (c.root == 5 && c.aid == 2)) {
								c.setVisible(false);     //判断车辆是否到达目的地，如果车满足条件，则消失在路口
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
