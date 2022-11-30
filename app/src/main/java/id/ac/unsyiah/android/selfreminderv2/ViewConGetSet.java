package id.ac.unsyiah.android.selfreminderv2;

public class ViewConGetSet {
    private String name;
    private String time;
    private String icon;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ViewConGetSet(String name, String time, String icon, int id) {
        this.name = name;
        this.time = time;
        this.icon = icon;
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
