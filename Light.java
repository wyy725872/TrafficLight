import javax.swing.*;
import java.awt.*;

public class Light extends JPanel {

	private static final long serialVersionUID = 1L;
	int color;    //红绿灯颜色的标记，0为红色，1为绿色
	Integer time;     //红绿灯的时间及红绿灯显示的时间
	JLabel j;

	public Light(int x, int y, boolean flag) {
		this.setLocation(x, y);
		this.setSize(20, 20);
		if (flag) {
			time = 5;
			color = 0;
			j = new JLabel(time.toString());
			this.setBackground(Color.RED);
		} else {
			time = 5;
			color = 1;
			j = new JLabel(time.toString());
			this.setBackground(Color.GREEN);
		}
		this.add(j);
		Count();
	}

	//到时间后改变红绿灯的颜色
	
	void ChangeCol() {
		if (color == 0) {//若为红则改为绿
			this.setBackground(Color.GREEN);
			time = 5;
			j.setText(time.toString());
		} else {
			this.setBackground(Color.RED);
			time = 5;
			j.setText(time.toString());
		}

		color = 1 - color;//改变颜色标记
	}

	//统计红绿灯进行的时间
	
	void Count() {
		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.getMessage();
					}
					time--;
					j.setText(time.toString());
					if (time == -1) {
						ChangeCol();
					}
				}
			}
		}.start();
	}
}
