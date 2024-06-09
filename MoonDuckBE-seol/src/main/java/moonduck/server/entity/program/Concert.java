package moonduck.server.entity.program;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.hibernate.annotations.Comment;

@Entity
@DiscriminatorValue("CONCERT")
public class Concert extends Program {

    @Comment("상품 제목")
    private String thema;

    @Comment("장소")
    private String place;

    @Comment("날짜")
    private String date;
    @Comment("배우")
    private String actors;

    @Comment("가격")
    private int price;

    public String getThema() {
        return thema;
    }

    public void setThema(String thema) {
        this.thema = thema;
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
                "thema='" + thema + '\'' +
                ", place ='" + place + '\'' +
                ", date ='" + date + '\'' +
                ", actors ='" + actors + '\'' +
                ", price ='" + price + '\'' +
                '}';
    }
}