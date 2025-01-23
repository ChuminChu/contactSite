package contactSite.programmer;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import contactSite.Field;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProgrammerQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QProgrammer programmer = QProgrammer.programmer;

    public ProgrammerQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Programmer> findAll(
            List<Field> fieldNames,  // 선택된 분야들
            Integer personalHistory   // 선택된 경력
    ) {
        return jpaQueryFactory
                .selectFrom(programmer)
                .where(
                        // 분야 여러개 선택 필터링
                        fieldNameCriteria(fieldNames),

                        // 경력 필터링
                        personalHistoryCriteria(personalHistory)
                )
                // 좋아요 순 정렬 (내림차순)
                .orderBy(programmer.likeCount.desc())
                .fetch();
    }

    // 분야 다중 선택 메서드
    private BooleanExpression fieldNameCriteria(List<Field> fieldNames) {
        if (fieldNames == null || fieldNames.isEmpty()) {
           return null;
        }
        return programmer.fieldName.in(fieldNames.stream().map(f -> f.name()).toList());
    }

    // 경력 필터링 메서드
    private BooleanExpression personalHistoryCriteria(Integer personalHistory) {
        if (personalHistory == null) {
            return null;
        }
        return programmer.personalHistory.goe(personalHistory);
    }



}
