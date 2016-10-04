package jsni;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class MouseDoubleClickEventDispatcher extends MouseSingleClickEventDispatcher {
	private Timer mTimer = null;;
	private ArrayList<MouseClickListener> cmcl = null;
	private int x = 0;
	private int y = 0;

	public MouseDoubleClickEventDispatcher() {
		this(200);
	}
	
	public MouseDoubleClickEventDispatcher(int initialDelay) {
		mTimer = new Timer(initialDelay, new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				synchronized (mTimer) {
					fireMouseClickEvent(new MouseDoubleClickEvent(1, x, y, 0, 0));
				}
			}
		});
	}
	
	public void setInitialDelay(int initialDelay) {
		mTimer.setInitialDelay(initialDelay);
	}

	public void click(ArrayList<MouseClickListener> newMouseClickListener, int x, int y) {
		synchronized (mTimer) {
			if (cmcl != newMouseClickListener) {
				if (mTimer.isRunning()) {
					fireMouseClickEvent(new MouseDoubleClickEvent(1, this.x, this.y, 0, 0));
				}
				firstClick(newMouseClickListener, x, y);
			} else {
				if (mTimer.isRunning()) {
					fireMouseClickEvent(new MouseDoubleClickEvent(2, x, y, this.x, this.y));
				} else {
					firstClick(newMouseClickListener, x, y);
				}
			}
		}
	}

	protected void firstClick(ArrayList<MouseClickListener> newMouseClickListener, int x, int y) {
		cmcl = newMouseClickListener;
		this.x = x;
		this.y = y;
		mTimer.restart();
	}
	
	protected void fireMouseClickEvent(MouseDoubleClickEvent mce) {
		mTimer.stop();
		super.fireMouseClickEvent(cmcl, mce);
	}
}
