package jsnitest;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import jsni.MouseDoubleClickEventDispatcher;
import jsni.MouseSingleClickEvent;
import jsni.MouseClickEvent;
import jsni.MouseClickListener;
import jsni.MouseDoubleClickEvent;
import jsni.MouseScrollEvent;
import jsni.MouseScrollListener;
import jsni.StatusNotifierItem;
import jsni.StatusNotifierItemException;

public class JSniTest {
	static JPopupMenu menu = null;
	static JDialog c = null;

	public static JPopupMenu newPopupMenu(String prefix, MouseListener al) {
		JPopupMenu menu = new JPopupMenu();
		JMenuItem item1 = new JMenuItem(prefix + "Item1");
		JMenuItem item2 = new JMenuItem(prefix + "Item2");
		JMenuItem item3 = new JMenuItem(prefix + "Item3");
		JMenuItem item4 = new JMenuItem(prefix + "Item4");
		JMenuItem item5 = new JMenuItem(prefix + "Item5");
		item1.setUI(new StayOpenMenuItemUI());
		item2.setUI(new StayOpenMenuItemUI());
		item3.setUI(new StayOpenMenuItemUI());
		item4.setUI(new StayOpenMenuItemUI());
		item5.setUI(new StayOpenMenuItemUI());

		if (al != null) {
			item1.addMouseListener(al);
			item2.addMouseListener(al);
			item3.addMouseListener(al);
			item4.addMouseListener(al);
			item5.addMouseListener(al);
		}

		menu.add(item1);
		menu.add(item2);
		menu.add(item3);
		menu.add(item4);
		menu.add(item5);
		menu.setVisible(false);

		menu.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				System.out.println(prefix + " Become Visible");
				// TODO Auto-generated method stub
				c.setVisible(true);

			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				System.out.println(prefix + " Become InVisible");
				// TODO Auto-generated method stub
				c.setVisible(false);

			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// TODO Auto-generated method stub
				System.out.println(prefix + " Become Canceled");
			}
		});
		return menu;
	}

	public static void createMenu() {
		c = new JDialog();
		c.setUndecorated(true);
		c.setVisible(false);

		menu = newPopupMenu("First", new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("Mouse clicked on Menu-Item");
			}
		});
		
		menu.setVisible(false);
	}

	public static void main(String[] args) {
		createMenu();

		try {
			// create new StatusNotifierItem
			StatusNotifierItem sni = new StatusNotifierItem();
			
			sni.setMouseClickEventDispatcher(new MouseDoubleClickEventDispatcher());

			// show the StatusNotificationItem
			sni.show();

			// add listener
			sni.addActivateListener(new MouseClickListener() {
				public void mouseClicked(MouseClickEvent mce) {
//					System.out.println("Activated: X = " + mce.x() + ", Y = " + mce.y());
					MouseDoubleClickEvent lmce = (MouseDoubleClickEvent) mce; 
					System.out.println("Activated: Clicks: " + lmce.clicks() + ", X = " + lmce.x() + ", Y = " + lmce.y() + ", oldX = " + lmce.oldX() + ", oldY = " + lmce.oldY());
					System.out.println("Dialog visibility: " + c.isVisible() + ", undeco: " + c.isUndecorated());
					if (!menu.isVisible()) {
						System.out.println("Show Menu");
						c.setVisible(true);
						menu.show(c, mce.x(), mce.y() - 100);
					} else {
						System.out.println("Hide Menu");
						c.setVisible(false);
						menu.setVisible(false);
					}
				}
			});

			sni.addContextMenuListener(new MouseClickListener() {
				public void mouseClicked(MouseClickEvent mce) {
//					System.out.println("ContextMenu: X = " + mce.x() + ", Y = " + mce.y());
					MouseDoubleClickEvent lmce = (MouseDoubleClickEvent) mce; 
					System.out.println("ContextMenu: Clicks: " + lmce.clicks() + ", X = " + lmce.x() + ", Y = " + lmce.y() + ", oldX = " + lmce.oldX() + ", oldY = " + lmce.oldY());
				}
			});

			sni.addSecondActivateListener(new MouseClickListener() {
				public void mouseClicked(MouseClickEvent mce) {
//					System.out.println("SecondActivated: X = " + mce.x() + ", Y = " + mce.y());
					MouseDoubleClickEvent lmce = (MouseDoubleClickEvent) mce; 
					System.out.println("SecondActivated: Clicks: " + lmce.clicks() + ", X = " + lmce.x() + ", Y = " + lmce.y() + ", oldX = " + lmce.oldX() + ", oldY = " + lmce.oldY());
				}
			});

			sni.addScrollListener(new MouseScrollListener() {
				public void mouseScrolled(MouseScrollEvent mse) {
					System.out.println("Scrolled: Delta = " + mse.delta() + ", Orientation = " + mse.orientation());
				}

			});

			// init the StatusNotifierItem
			sni.set("Category", "ApplicationStatus");
			sni.set("Id", "TV-Browser");
			sni.set("Title", "TV-Browser - a digital TV guide");
			sni.set("Status", "Active");
			sni.set("IconThemePath", "/home/voc/usr/Hagenberg/MTD/LaTeXSlides/workspace/jsni/icons");
			sni.set("IconName", "tvbrowser");

			// sni.set("WindowId", 0);
			// sni.set("ItemIsMenu", false);
			// sni.set("Menu", "");
			// sni.set("OverlayIconName", "");
			// sni.set("AttentionIconName", "");
			// sni.set("AttentionMovieName", "");

		} catch (StatusNotifierItemException e) {
			System.out.println("Message: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
}
