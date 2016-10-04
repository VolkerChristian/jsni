package jsni;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.DBusSignal;
import org.freedesktop.dbus.Variant;
import org.freedesktop.dbus.exceptions.DBusException;

public class StatusNotifierItem {
	private StatusNotifierItemD snid = null;

	public StatusNotifierItem() {
		snid = new StatusNotifierItemD();
	}
	
	public MouseClickEventDispatcher setMouseClickEventDispatcher(MouseClickEventDispatcher mced) {
		return snid.setMouseClickEventDispatcher(mced);
	}
	
	public MouseClickEventDispatcher getMouseClickEventDispatcher() {
		return snid.getMouseClickEventDispatcher();
	}

	public boolean isVisible() {
		return snid.isVisible();
	}

	public void show() throws StatusNotifierItemException {
		snid.show();
	}

	public void hide() {
		snid.hide();
	}

	public <A> void set(String name, A value) {
		snid.set(name, value);
	}

	public <A> A get(String name) {
		return snid.get(name);
	}

	public void addActivateListener(MouseClickListener listener) {
		snid.addActivateListener(listener);
	}

	public void addContextMenuListener(MouseClickListener listener) {
		snid.addContextMenuListener(listener);
	}

	public void addSecondActivateListener(MouseClickListener listener) {
		snid.addSecondActivateListener(listener);
	}

	public void addScrollListener(MouseScrollListener listener) {
		snid.addScrollListener(listener);
	}

	private static class StatusNotifierItemD implements jsni.org.kde.StatusNotifierItem {
		private DBusConnection dbus = null;

		private MouseClickEventDispatcher mced = new MouseSingleClickEventDispatcher();
		
		@SuppressWarnings("rawtypes")
		private Hashtable<String, Property> properties = null;
		
		private ArrayList<MouseClickListener> activateListener = new ArrayList<MouseClickListener>();
		private ArrayList<MouseClickListener> contextMenuListener = new ArrayList<MouseClickListener>();
		private ArrayList<MouseClickListener> secondActivateListener = new ArrayList<MouseClickListener>();
		private ArrayList<MouseScrollListener> scrollListener = new ArrayList<MouseScrollListener>();


		@SuppressWarnings("rawtypes")
		private StatusNotifierItemD() {

			properties = new Hashtable<String, Property>();

			addProperty("Category");
			addProperty("Id");
			addProperty("Title");
			addProperty("Status");
			addProperty("WindowId");
			addProperty("ItemIsMenu");
			addProperty("Menu");
			addProperty("IconThemePath");
			addProperty("IconName");
			addProperty("OverlayIconName");
			addProperty("AttentionIconName");
			addProperty("AttentionMovieName");

			// Missing Properties
			// IconPixmap
			// OverlayIconPixmap
			// AttentionIconPixmap
			// ToolTip
		}
		
		private MouseClickEventDispatcher setMouseClickEventDispatcher(MouseClickEventDispatcher mced) {
			MouseClickEventDispatcher oldMced = mced;
			
			this.mced = mced;
			
			return oldMced;
		}

		public MouseClickEventDispatcher getMouseClickEventDispatcher() {
			return mced;
		}

		private void addProperty(String name) {
			try {
				Class<?> innerClass = Class.forName(StatusNotifierItemD.class.getName() + "$" + name);
				Constructor<?> ctor = innerClass.getDeclaredConstructor(this.getClass());
				Property<?> property = (Property<?>) ctor.newInstance(this);
				properties.put(property.name(), property);
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		/* *************** API Methods **************** */
		private boolean isVisible() {
			return dbus != null;
		}

		private void show() throws StatusNotifierItemException {
			if (dbus == null) {
				try {
					dbus = DBusConnection.getConnection(DBusConnection.SESSION);
					dbus.requestBusName("org.tvbrowser");
					dbus.exportObject("/StatusNotifierItem", this);

					jsni.org.kde.StatusNotifierWatcher watcher = dbus.getRemoteObject("org.kde.StatusNotifierWatcher",
							"/StatusNotifierWatcher", jsni.org.kde.StatusNotifierWatcher.class);
					watcher.RegisterStatusNotifierItem("org.tvbrowser");
				} catch (DBusException e) {
					throw new StatusNotifierItemException(e.getMessage());
				}
			}
		}

		private void hide() {
			if (dbus != null) {
				dbus.disconnect();
				dbus = null;
			}
		}

		@SuppressWarnings("unchecked")
		private <A> void set(String name, A value) {
			properties.get(name).set(value);
		}

		@SuppressWarnings("unchecked")
		private <A> A get(String name) {
			return (A) properties.get(name).get();
		}

		private void addActivateListener(MouseClickListener listener) {
			activateListener.add(listener);
		}

		private void addContextMenuListener(MouseClickListener listener) {
			contextMenuListener.add(listener);
		}

		private void addSecondActivateListener(MouseClickListener listener) {
			secondActivateListener.add(listener);
		}

		private void addScrollListener(MouseScrollListener listener) {
			scrollListener.add(listener);
		}

		/* ************* DBusProperties Methods **************** */
		@SuppressWarnings("unchecked")
		@Override
		public <A> A Get(String inter, String prop) {
			System.out.println("Get called");
			return (A) properties.get(prop).get();
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public Map<String, Variant> GetAll(String inter) {
			Hashtable<String, Variant> table = new Hashtable<String, Variant>();

			for (Map.Entry<String, Property> entry : properties.entrySet()) {
				table.put(entry.getKey(), new Variant(entry.getValue().get()));
			}

			return table;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <A> void Set(String inter, String prop, A value) {
			properties.get(prop).set(value);
		}

		/* *********** Properties ************ */
		private class Property<A> {
			A value = null;

			public Property(A value) {
				this.value = value;
			}

			public A get() {
				return value;
			}

			public void set(A value) {
				this.value = value;
			}

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void set(A value, String sig) {
				this.value = value;
				try {
					Class sigClass = Class.forName(StatusNotifierItemD.class.getInterfaces()[0].getName() + "$" + sig);
					Constructor con = sigClass.getConstructor(String.class);
					DBusSignal dbusSignal = (DBusSignal) con.newInstance("/StatusNotifierItem");
					if (dbus != null) {
						dbus.sendSignal(dbusSignal);
					}
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| NoSuchMethodException | SecurityException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
			}

			public String name() {
				return this.getClass().getSimpleName();
			}
		}

		@SuppressWarnings("unused")
		private class Category extends Property<String> {
			public Category() {
				super("");
			}
		}

		@SuppressWarnings("unused")
		private class Id extends Property<String> {
			public Id() {
				super("");
			};
		}

		@SuppressWarnings("unused")
		private class Title extends Property<String> {
			public Title() {
				super("");
			}

			public void set(String value) {
				set(value, "NewTitle");
			}
		}

		@SuppressWarnings("unused")
		private class Status extends Property<String> {
			public Status() {
				super("");
			}

			/*
			 * public void set(String value) { set(value, "NewStatus"); }
			 */
		}

		@SuppressWarnings("unused")
		private class WindowId extends Property<Integer> {
			public WindowId() {
				super(0);
			}
		}

		@SuppressWarnings("unused")
		private class ItemIsMenu extends Property<Boolean> {
			public ItemIsMenu() {
				super(false);
			}
		}

		@SuppressWarnings("unused")
		private class Menu extends Property<String> {
			public Menu() {
				super("");
			}
		}

		@SuppressWarnings("unused")
		private class IconThemePath extends Property<String> {
			public IconThemePath() {
				super("");
			}

			/*
			 * public void set(String value) { set(value, "NewIconThemePath"); }
			 */
		}

		@SuppressWarnings("unused")
		private class IconName extends Property<String> {
			public IconName() {
				super("");
			}

			public void set(String value) {
				set(value, "NewIcon");
			}
		}

		@SuppressWarnings("unused")
		private class OverlayIconName extends Property<String> {
			public OverlayIconName() {
				super("");
			}

			public void set(String value) {
				set(value, "NewOverlayIcon");
			}
		}

		@SuppressWarnings("unused")
		private class AttentionIconName extends Property<String> {
			public AttentionIconName() {
				super("");
			}

			public void set(String value) {
				set(value, "NewAttentionIcon");
			}
		}

		@SuppressWarnings("unused")
		private class AttentionMovieName extends Property<String> {
			public AttentionMovieName() {
				super("");
			}
		}

		/* ********** StatusNotifierInterface Methods *********** */
		@Override
		public boolean isRemote() {
			return false;
		}

		private void fireMouseScrollEvent(ArrayList<MouseScrollListener> msls, int delta, String orientation) {
			for (MouseScrollListener msl : msls) {
				msl.mouseScrolled(new MouseScrollEvent(this, delta, orientation));
			}
		}

		@Override
		public void Activate(int x, int y) {
			mced.click(activateListener,  x, y);
		}

		@Override
		public void ContextMenu(int x, int y) {
			mced.click(contextMenuListener,  x, y);
		}

		@Override
		public void SecondaryActivate(int x, int y) {
			mced.click(secondActivateListener,  x, y);
		}

		@Override
		public void Scroll(int delta, String orientation) {
			fireMouseScrollEvent(scrollListener, delta, orientation);
		}
	}
}
