package moonduck.server.entity.program;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("book")
@Table(name = "book", schema = "myschema")
@Schema(description = "책 엔티티")
public class Book extends Program{

    @Comment("저자")
    @Column(nullable = false)
    private String author;

    @Comment("출판사")
    @Column(nullable = false)
    private String publisher;

    public Book(String title, String date, String author, String publisher) {
        super(title, date);
        this.author = author;
        this.publisher = publisher;
    }
}
