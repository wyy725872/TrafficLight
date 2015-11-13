import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import javax.swing.*;

/*如果将车的数量加大就会出现车辆穿插,这是因为车辆检测是循环的 而且有的是双重循环需要时间
但是车辆行进的时间是固定的,会穿插是因为主程序里还没判断玩车辆检测 车辆就已经开始下一次的行进
也就是主程序是8ms一次 车辆是15ms一次,但是车辆多了 8ms就不够用
8ms加上程序运行时间会大于15ms,这样会出现穿插
*/

class F {
	Car[] Car = new Car[20];
	int s = 0;    //用于统计次数，便于产汽车
	Light j1 = new Light(188, 185, false);    //第一个红绿灯
	Light j2 = new Light(428, 185, true);     //第二个红绿灯

	public void create() {
		JFrame f = new JFrame();
		Random r = new Random();
		f.setBounds(400, 150, 640, 415);//坐标，宽高，整个模块宽640，高415
		f.setLayout(null);//布局自定义
		JPanel[] p = new JPanel[6];   //6块灰色的背景数组
		JPanel[] q = new JPanel[6];   //6块目的地标记数组
		for (int i = 0; i < 6; i++) {
			p[i] = new JPanel();
			f.add(p[i]);
			p[i].setSize(155, 155);    //设置每个背景框的大小
			p[i].setBackground(Color.LIGHT_GRAY);
			p[i].setLayout(null);
		}
		for (int i = 0; i < 6; i++) {
			q[i] = new JPanel();
			q[i].setSize(20, 20);    //设置每个目的地框的大小
			p[i].add(q[i]);
		}
		
		//设置背景框的位置，及目的地标记框的位置与颜色
		
		p[0].setLocation(0, 0);
		p[1].setLocation(240, 0);
		p[2].setLocation(480, 0);
		p[3].setLocation(0, 235);
		p[4].setLocation(240, 235);
		p[5].setLocation(480, 235);
		q[0].setBackground(new Color(250, 128, 114));//肉粉
		q[0].setLocation(0, 135);
		q[1].setBackground(new Color(210, 180, 140));//一种灰    q1，q2不需定位，因为p的每个布局都是空所以默认左上角
		q[2].setBackground(new Color(0, 255, 127));//绿
		q[3].setBackground(Color.ORANGE);
		q[3].setLocation(135, 131);
		q[4].setBackground(Color.PINK);
		q[4].setLocation(135, 131);
		q[5].setBackground(new Color(135, 206, 235));//蓝
		q[5].setLocation(135, 0);
		f.setVisible(true);
		f.setResizable(false);//大小不可调
		f.addWindowListener(new WindowAdapter() {    //添加窗口监听，窗口关闭，程序结束
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.add(j1);//添加红绿灯
		f.add(j2);//添加红绿灯
		
		//产生汽车
		
		while (true) {
			s = (s + 1) % 100;
			Check2();  //消除到达终点的车
			EndCheck();    //路口尽头的检测
			CrossCheck();   //十字路口的检测
			FrontCkeck();   //同方向的检测，车辆前后检测
			LightCheck();   //红绿灯前的检测
			DeadCheck();   //堵死时的检测
			if (s == 99) {//s计数，为了让程序每8ms执行一次而又不没执行一次生成一个车即当s为99时产生一个车
				int n = r.nextInt(6) + 1;
				Car c;
				while (!Check1(n)) {   //如果要产生车的路口有车，则选择下一个路口
					n = (n) % 6 + 1;//为了解决n=6时路口为0的情况
				}
				for (int i = 0; i < Car.length; i++) {
					if (Car[i] == null) {    //如果当前数组有空位，则产生车，填补空位
						c = new Car(n);
						Car[i] = c;
						f.add(c);
						break;
					}
				}
			}
			try {
				Thread.sleep(8);     //每8ms执行一次
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	//车辆在红绿灯范围内，判断是否让车停
	
	void LightCheck() {
		for (int i = 0; i < Car.length; i++) {
			if (Car[i] != null) {
				if (((Car[i].getLocation().y - 165 < 80 && Car[i].getLocation().y - 165 >= 0) || (205 - Car[i]
						.getLocation().y < 80 && 205 - Car[i].getLocation().y >= 0))
						&& ((Car[i].getLocation().x - 240 < 3
								&& Car[i].getLocation().x - 240 > 0 && Car[i].root == 6) || (120 - Car[i]
								.getLocation().x < 3
								&& 120 - Car[i].getLocation().x > 0 && Car[i].root == 1))) {

					if (j1.color == 0 && Car[i].l == 1) {//红灯时car.l当前车前进或转弯，1为前进
						Car[i].T = true;     //停止Car[i]
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

	//判断要生成车的路口位置是否有车
	
	boolean Check1(int n) {
		for (int i = 0; i < Car.length; i++) {
			if (Car[i] != null)
				if (Car[i].root == n || Car[i].root + n == 7)//路线的编号，相加=7,123645逆时针
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

	//如果有车到达终点，将其设置为null
	
	void Check2() {
		for (int i = 0; i < Car.length; i++) {
			if (Car[i] != null) {
				if (!Car[i].exist) {
					Car[i] = null;
				}
			}
		}
	}
	
    //判断车有没有走到路口尽头的范围
	
	boolean Check3(Car c) {
		switch (7 - c.root) {//对面的尽头
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

	//检查路口尽头对面方向的路口有没有车，是否满足掉头
	
	boolean Check4(Car c) {
		switch (c.root) {
		case 1:
			if (c.getLocation().x < 45)
				return false;//有车
			break;
		case 2:
			if (c.getLocation().y > 320)
				return false;//有车
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

	//车是否在堵死的范围内
	
	boolean Check6(Car c) {
		if (((c.getLocation().x <= 450 && c.getLocation().x >= 405) || (c
				.getLocation().x <= 210 && c.getLocation().x >= 165))
				&& c.getLocation().y <= 205 && c.getLocation().y >= 165) {
			return true;
		}
		return false;
	}

	//堵死时让车辆转弯
	
	void DeadCheck() {
		for (int i = 0; i < Car.length; i++) {
			if (Car[i] != null) {
				if (Check6(Car[i])) {
					if (Car[i].Count >350) {//如果车停了超过350次说明停止时间超过正常5秒即堵死 
						Car[i].Count = -1;//标记堵死情况，右转
						Car[i].T = false;   //让Car[i]转弯 
			}
				}
			}
		}
	}

	//检查车走到尽头时是否可以掉头
	
	void EndCheck() {
		for (int i = 0; i < Car.length; i++) {
			if (Car[i] != null && Car[i].Sw == 0) {//车存在并且每个车只检测一次
				if (!Check3(Car[i])) {//车走到路的尽头
					for (int j = 0; j < Car.length; j++)
						if (Car[j] != null && Car[i].root == 7 - Car[j].root
								&& (Car[i].root + Car[i].aid != 7)) {
							if (!Check4(Car[j])) {//对面有车return false
								Car[i].T = true;    //让Car[i]停止
								Car[j].Sw = 1;//标记车已被选择过
							}
						}
				}
			}
		}
	}

	//在十字路口判断是否让车停止
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
								Car[i].T = true;    //让Car[i]停止
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

	//同方向前面有车，后面车停
	
	void FrontCkeck() {
		for (int i = 0; i < Car.length; i++) {
			for (int j = 0; j < Car.length; j++) {
				if (i != j && Car[i] != null && Car[j] != null) {
					if (Car[i].root == 1
							&& Car[j].root == 1
							&& Car[i].getLocation().x - Car[j].getLocation().x < 35
							&& Car[i].getLocation().x - Car[j].getLocation().x >= 0) {
						Car[j].T = true;      //让Car[i]停车
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