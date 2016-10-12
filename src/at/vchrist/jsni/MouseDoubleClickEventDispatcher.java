package at.vchrist.jsni;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class MouseDoubleClickEventDispatcher extends MouseSingleClickEventDispatcher {
	public final static int DEFAULT_DELAY = 200;
	private Timer mTimer = null;;
	private ArrayList<MouseClickListener> cmcl = null;
	private int x = 0;
	private int y = 0;

	public MouseDoubleClickEventDispatcher() {
		this(DEFAULT_DELAY);
	}
	
	public MouseDoubleClickEventDispatcher(int delay) {
		mTimer = new Timer(delay, new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				synchronized (mTimer) {
					fireMouseClickEvent(new MouseDoubleClickEvent(1, x, y, 0, 0));
				}
			}
		});
	}
	
	public int setDelay(int delay) {
		int oldDelay = mTimer.getInitialDelay();
		
		mTimer.setInitialDelay(delay);
		
		return oldDelay;
	}
	
	public int getDelay() {
		return mTimer.getInitialDelay();
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
