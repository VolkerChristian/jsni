package at.vchrist.jsni;

import java.util.ArrayList;

public class MouseSingleClickEventDispatcher implements MouseClickEventDispatcher {
	public void click(ArrayList<MouseClickListener> newMouseClickListener, int x, int y) {
		fireMouseClickEvent(newMouseClickListener, new MouseSingleClickEvent(x, y));
	}
	
	protected void fireMouseClickEvent(ArrayList<MouseClickListener> cmcl, MouseClickEvent mce) {
		if (cmcl != null) {
			for (MouseClickListener mcl : cmcl) {
				mcl.mouseClicked(mce);
			}
		}
	}
}
