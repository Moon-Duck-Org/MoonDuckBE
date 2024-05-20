package moonduck.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.sql.Insert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;

@Getter
@Setter
@Entity
@Table(name = "category", schema="myschema")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_id;

    @Comment("카테고리")
    @Column(nullable = false)
    private String category;
}
