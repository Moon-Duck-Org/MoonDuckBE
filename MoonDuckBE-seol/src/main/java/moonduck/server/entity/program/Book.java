package moonduck.server.entity.program;

import jakarta.persistence.Entity;

@Entity
public class Book extends Program {
    private String title;
    private String author;
    private String publisher;
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