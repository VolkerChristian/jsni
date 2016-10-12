package at.vchrist.jsni;

import java.util.ArrayList;

public interface MouseClickEventDispatcher {
	public void click(ArrayList<MouseClickListener> newMouseClickListener, int x, int y);
}
