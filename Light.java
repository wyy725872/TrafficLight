import javax.swing.*;
import java.awt.*;

public class Light extends JPanel {

	private static final long serialVersionUID = 1L;
	int color;    //���̵���ɫ�ı�ǣ�0Ϊ��ɫ��1Ϊ��ɫ
	Integer time;     //���̵Ƶ�ʱ�估���̵���ʾ��ʱ��
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

	//��ʱ���ı���̵Ƶ���ɫ
	
	void ChangeCol() {
		if (color == 0) {//��Ϊ�����Ϊ��
			this.setBackground(Color.GREEN);
			time = 5;
			j.setText(time.toString());
		} else {
			this.setBackground(Color.RED);
			time = 5;
			j.setText(time.toString());
		}

		color = 1 - color;//�ı���ɫ���
	}

	//ͳ�ƺ��̵ƽ��е�ʱ��
	
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
