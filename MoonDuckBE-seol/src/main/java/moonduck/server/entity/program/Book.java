package moonduck.server.entity.program;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.Comment;

@Entity
@DiscriminatorValue("BOOK")
public class Book extends Program {

    @Comment("상품 제목")
    private String title;

    @Comment("저자")
    private String author;

    @Comment("출판사")
    private String publisher;

    @Comment("출간일")
    private String pubdate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author ='" + author + '\'' +
                ", publisher ='" + publisher + '\'' +
                ", pubdate ='" + pubdate + '\'' +
                '}';
    }
}