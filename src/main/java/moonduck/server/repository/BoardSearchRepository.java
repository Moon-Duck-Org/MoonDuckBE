package moonduck.server.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import moonduck.server.entity.QBoard;
import moonduck.server.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardSearchRepository {

    private final JPAQueryFactory queryFactory;

    public BoardSearchRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<Board> findByUserIdWithFilter(Long userId, String filter, Pageable pageable) {
        QBoard board = QBoard.board;

        List<Board> boards = queryFactory
                .selectFrom(board)
                .where(board.user.id.eq(userId))
                .leftJoin(board.user).fetchJoin()
                .orderBy(getOrderSpecifier(filter))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(board.count())
                .from(board)
                .where(board.user.id.eq(userId));

        return PageableExecutionUtils.getPage(boards, pageable, countQuery::fetchOne);
    }

    public Page<Board> findByUserIdAndCategoryWithFilter(Long userId, Board category, String filter, Pageable pageable) {
        QBoard board = QBoard.board;

        List<Board> boards = queryFactory
                .selectFrom(board)
                .where(
                        board.user.id.eq(userId),
                        board.category.eq(category)
                )
                .leftJoin(board.user).fetchJoin()
                .orderBy(getOrderSpecifier(filter))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(board.count())
                .from(board)
                .where(
                        board.user.id.eq(userId),
                        board.category.eq(category)
                );

        return PageableExecutionUtils.getPage(boards, pageable, countQuery::fetchOne);
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
