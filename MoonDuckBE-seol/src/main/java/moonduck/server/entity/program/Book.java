package moonduck.server.entity.program;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import moonduck.server.entity.Board;
import org.hibernate.annotations.Comment;

@Entity
@DiscriminatorValue("BOOK")
public class Book extends Board {

    @Comment("상품 제목")
    private String thema;

    @Comment("저자")
    private String author;

    @Comment("출판사")
    private String publisher;

    @Comment("출간일")
    private String date;

    public String getThema() {
        return thema;
    }

    public void setThema(String title) {
        this.thema = thema;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Book{" +
                "thema='" + thema + '\'' +
                ", author ='" + author + '\'' +
                ", publisher ='" + publisher + '\'' +
                ", date ='" + date + '\'' +
                '}';
    }
}