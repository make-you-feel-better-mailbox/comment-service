package com.onetwo.commentservice.adapter.out.persistence.repository.comment;

import com.onetwo.commentservice.adapter.out.persistence.entity.CommentEntity;
import com.onetwo.commentservice.application.port.in.command.CommentFilterCommand;
import com.onetwo.commentservice.common.GlobalStatus;
import com.onetwo.commentservice.common.utils.QueryDslUtil;
import com.onetwo.commentservice.common.utils.SliceUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.onetwo.commentservice.adapter.out.persistence.entity.QCommentEntity.commentEntity;

public class QCommentRepositoryImpl extends QuerydslRepositorySupport implements QCommentRepository {

    private final JPAQueryFactory factory;

    public QCommentRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(CommentEntity.class);
        this.factory = jpaQueryFactory;
    }

    @Override
    public List<CommentEntity> sliceByCommand(CommentFilterCommand commentFilterCommand) {
        return factory.select(commentEntity)
                .from(commentEntity)
                .where(filterCondition(commentFilterCommand),
                        commentEntity.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED))
                .limit(SliceUtil.getSliceLimit(commentFilterCommand.getPageable().getPageSize()))
                .offset(commentFilterCommand.getPageable().getOffset())
                .orderBy(commentEntity.id.desc())
                .fetch();
    }

    private Predicate filterCondition(CommentFilterCommand commentFilterCommand) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QueryDslUtil.ifConditionExistAddEqualPredicate(commentFilterCommand.getCategory(), commentEntity.category, booleanBuilder);
        QueryDslUtil.ifConditionExistAddEqualPredicate(commentFilterCommand.getTargetId(), commentEntity.targetId, booleanBuilder);
        QueryDslUtil.ifConditionExistAddEqualPredicate(commentFilterCommand.getUserId(), commentEntity.userId, booleanBuilder);
        QueryDslUtil.ifConditionExistAddLikePredicate(commentFilterCommand.getContent(), commentEntity.content, booleanBuilder);
        QueryDslUtil.ifConditionExistAddGoePredicate(commentFilterCommand.getFilterStartDate(), commentEntity.createdAt, booleanBuilder);
        QueryDslUtil.ifConditionExistAddLoePredicate(commentFilterCommand.getFilterEndDate(), commentEntity.createdAt, booleanBuilder);

        return booleanBuilder;
    }
}
