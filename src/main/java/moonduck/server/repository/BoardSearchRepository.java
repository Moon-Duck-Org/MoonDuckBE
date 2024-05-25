package moonduck.server.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import moonduck.server.entity.Board;
import moonduck.server.entity.QBoard;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardSearchRepository {

    private final JPAQueryFactory queryFactory;

    public BoardSearchRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Board> findByUserIdWithFilter(Long userId, String filter) {
        QBoard board = QBoard.board;

        return queryFactory
                .selectFrom(board)
                .where(board.user.id.eq(userId))
                .leftJoin(board.user).fetchJoin()
                .orderBy(getOrderSpecifier(filter))
                .fetch();
    }

    private OrderSpecifier getOrderSpecifier(String filter) {
        PathBuilder<Board> entityPath = new PathBuilder<>(Board.class, "board");

        if (filter == null || filter.equals("LATEST")) {
            return new OrderSpecifier(Order.DESC, entityPath.get("createdAt"));
        } else if (filter.equals("OLDEST")) {
            return new OrderSpecifier(Order.ASC, entityPath.get("createdAt"));
        } else if (filter.equals("RATE")) {
            return new OrderSpecifier(Order.DESC, entityPath.get("score"));
        } else {
            return new OrderSpecifier(Order.DESC, entityPath.get("createdAt"));
        }
    }
}
