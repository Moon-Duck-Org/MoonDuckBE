package moonduck.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Comment("생성날짜")
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Comment("수정날짜")
    @LastModifiedDate
    @Column(name = "modified_at")
    private Timestamp modifiedAt;
}
