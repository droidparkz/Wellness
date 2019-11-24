package in.droidparkz.wellness;

public class LogItem {

    private String name;
    private String date;
    private String image;

    public LogItem(String name, String date, String image) {

        this.name = name;
        this.date = date;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getImage() {return image;}

}