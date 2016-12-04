package Client.ui;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sun YuHao on 2016/12/3.
 */
public class MMenu {
    private JMenu menu;
    private Map<String, JMenuItem> items;

    public MMenu(String name) {
        this.menu = new JMenu(name);
        this.items = new HashMap<>();
    }

    void addItem(String name, JMenuItem item) {
        items.put(name, item);
        menu.add(item);
    }

    JMenu getMenu() {
        return menu;
    }

    void deleteItem(String name) {
        menu.remove(items.remove(name));
    }

    JMenuItem getItem(String name) {
        return items.get(name);
    }
}
