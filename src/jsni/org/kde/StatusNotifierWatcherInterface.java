package jsni.org.kde;

import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.DBusInterfaceName;

@DBusInterfaceName("org.kde.StatusNotifierWatcher") 
public interface StatusNotifierWatcher extends DBusInterface {
    public void RegisterStatusNotifierItem(String busName);
}