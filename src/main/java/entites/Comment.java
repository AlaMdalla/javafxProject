package entites;

import java.sql.Time;
import java.time.LocalDate;

public class Comment {
    public int getId() {
        return id;
    }

    public Comment(String name, String contenu, LocalDate date, Time time) {
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

    public LocalDate getDate() {
        return date;
    }



    public Time getTime() {
        return time;
    }
    public void setDate() {
        this.date = LocalDate.now();
    }

    public void setTimeToCurrent() {
        this.time = new Time(System.currentTimeMillis());
    }

    int id;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contenu='" + contenu + '\'' +
                ", date=" + date +
                ", time=" + time +
                '}';
    }

    public Comment(String name, String contenu) {
        this.name = name;
        this.contenu = contenu;
    }

    String name;
    String contenu;
    LocalDate date;
    Time time;
    int id_post;

    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public Comment(int id, int idPost, String name, String contenu, LocalDate date, Time time) {
        this.id = id;
        this.name = name;
        this.contenu = contenu;
        this.date = date;
        this.time = time;
        this.id_post = idPost;

    }

    public Comment(int id, String name, String contenu, LocalDate date, Time time, int id_post) {
        this.id = id;
        this.name = name;
        this.contenu = contenu;
        this.date = date;
        this.time = time;
        this.id_post = id_post;
    }
}
