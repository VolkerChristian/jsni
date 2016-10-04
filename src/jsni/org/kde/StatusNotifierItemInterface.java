package jsni.org.kde;

import org.freedesktop.DBus;
import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.DBusInterfaceName;
import org.freedesktop.dbus.DBusSignal;
import org.freedesktop.dbus.exceptions.DBusException;

@DBusInterfaceName("org.kde.StatusNotifierItem")
public interface StatusNotifierItem extends DBus.Properties, DBusInterface {
    public static class NewTitle extends DBusSignal {
        public NewTitle(String path) throws DBusException {
            super(path);
        }
    }

    public static class NewIcon extends DBusSignal {
        public NewIcon(String path) throws DBusException {
            super(path);
        }
    }

    public static class NewAttentionIcon extends DBusSignal {
        public NewAttentionIcon(String path) throws DBusException {
            super(path);
        }
    }

    public static class NewOverlayIcon extends DBusSignal {
        public NewOverlayIcon(String path) throws DBusException {
            super(path);
        }
    }

    public static class NewToolTip extends DBusSignal {
        public NewToolTip(String path) throws DBusException {
            super(path);
        }
    }

    public static class NewStatus extends DBusSignal {
        public final String status;

        public NewStatus(String path, String status) throws DBusException {
            super(path, status);
            this.status = status;
        }
    }

    public static class NewIconThemePath extends DBusSignal {
        public final String icon_theme_path;

        public NewIconThemePath(String path, String icon_theme_path) throws DBusException {
            super(path, icon_theme_path);
            this.icon_theme_path = icon_theme_path;
        }
    }

    public void ContextMenu(int x, int y);

    public void Activate(int x, int y);

    public void SecondaryActivate(int x, int y);

    public void Scroll(int delta, String orientation);
}