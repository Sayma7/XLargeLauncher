package study.sayma.xlargelauncher.models;

import android.graphics.drawable.Drawable;

public class AppItem {
    private String name, pkg;
    private Drawable icon;

    public AppItem(String name, String pkg, Drawable icon) {
        this.name = name;
        this.pkg = pkg;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "AppItem{" +
                "name='" + name + '\'' +
                ", pkg='" + pkg + '\'' +
                ", icon=" + icon +
                '}';
    }
}