package moonduck.server.entity.program;

import jakarta.persistence.Entity;

@Entity
public class Concert extends Program {
    private String title;
    private String place;
    private String date;
    private String actors;
    private int price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return  "Conert{" +
                "title='" + title + '\'' +
                ", place ='" + place + '\'' +
                ", date ='" + date + '\'' +
                ", actors ='" + actors + '\'' +
                ", price ='" + price + '\'' +
                '}';
    }
}