package sample;

public class ListData {

    private int id;
    private String data;
    private String date;

    public ListData(int id, String data, String date) {
        this.id = id;
        this.data = data;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
