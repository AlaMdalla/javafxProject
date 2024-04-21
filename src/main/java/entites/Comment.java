package entites;

import java.sql.Time;
import java.util.Date;

public class Comment {
    public int getId() {
        return id;
    }

    public Comment(String name, String contenu, Date date, Time time) {
        this.name = name;
        this.contenu = contenu;
        this.date = date;
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    int id;
    String name;
    String contenu;
    Date date;
    Time time;

}
