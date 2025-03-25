package contactSite.programmer;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import contactSite.Field;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ProgrammerQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QProgrammer programmer = QProgrammer.programmer;
    private final ProgrammerRepository programmerRepository;

    public ProgrammerQueryRepository(JPAQueryFactory jpaQueryFactory, ProgrammerRepository programmerRepository) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.programmerRepository = programmerRepository;
    }

    /*public List<Programmer> findAll(
            List<Field> fieldNames,  // 선택된 분야들
            Integer personalHistory,   // 선택된 경력
            Pageable pageable
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }*/

    //2단계
    //문제가 조인과 페이징을 같이 해야되는데 같이 안됨 둘중에 하나만 하면 ㄱㅊ
    //1. sql이 너무 많이 실행됨
    //2. 조인을 해서 sql을 줄임
    //3. 페이징하니까 같이가 안되네,,,
    //4. 페이징을 먼저 하면 되지
    //5. 근데 쿼리dsl이 서브쿼리를 지원 안하네?
    // 데이터베이스에
    //페이징을 나중에하면 너무 많은 데이터니까 ㅂㄹ
    //페이징을 하고 join
   public List<Programmer> findAll(
            List<Field> fieldNames,
            Integer personalHistory,
            Pageable pageable
    ) {
        // 1. 페이징된 ID 목록 가져오기
        List<String> programmerIds = jpaQueryFactory
                .select(programmer.id)
                .from(programmer)
                .where(
                        fieldNameCriteria(fieldNames),
                        personalHistoryCriteria(personalHistory)
                )
                .orderBy(programmer.likeCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if (programmerIds.isEmpty()) {
            return new ArrayList<>();
        }

        // 2. ID로 필터링된 전체 데이터 로드 (페치 조인 사용)
        return jpaQueryFactory
                .selectFrom(programmer)
                .join(programmer.fieldName).fetchJoin()
                .distinct()
                .where(programmer.id.in(programmerIds))
                .orderBy(programmer.likeCount.desc())
                .fetch();
    }

//    //3단계
//    public List<Programmer> findAll(
//            List<Field> fieldNames,
//            Integer personalHistory,
//            Pageable pageable
//    ) {
//        // 1. ID 목록만 조회 (페이징 적용)
//        List<String> programmerIds = jpaQueryFactory
//                .select(programmer.id)
//                .from(programmer)
//                .where(
//                        fieldNameCriteria(fieldNames),
//                        personalHistoryCriteria(personalHistory)
//                )
//                .orderBy(programmer.likeCount.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        if (programmerIds.isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        // 2. ID로 Programmer 엔티티 전체 로드 (Batch Fetching 활용)
//        return programmerRepository.findByIdIn(programmerIds);
//    }
    // 4단계
    /*public List<Programmer> findAll(
            List<Field> fieldNames,
            Integer personalHistory,
            Pageable pageable
    ) {
        return jpaQueryFactory.selectFrom(programmer)
                .where(
                        fieldNameCriteria(fieldNames),
                        personalHistoryCriteria(personalHistory)
                )
                .orderBy(programmer.likeCount.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .leftJoin(programmer.fieldName).fetchJoin()
                .fetch();
    }*/

    //join 후 page -> 1.9초?
    /*public List<Programmer> findAll(
            List<Field> fieldNames,
            Integer personalHistory,
            Pageable pageable
    ) {
        // 1. JOIN을 포함한 기본 쿼리 작성 (ID만 가져오는 방식이 아니라 전체 엔티티 조회)
        JPAQuery<Programmer> query = jpaQueryFactory
                .selectFrom(programmer)
                .leftJoin(programmer.fieldName).fetchJoin()
                .where(
                        fieldNameCriteria(fieldNames),
                        personalHistoryCriteria(personalHistory)
                )
                .orderBy(programmer.likeCount.desc())
                .distinct();

        // 2. 페이징 적용
        List<Programmer> programmers = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return programmers;
    }*/


    // 분야 다중 선택 메서드
    private BooleanExpression fieldNameCriteria(List<Field> fieldNames) {
        if (fieldNames == null || fieldNames.isEmpty()) {
            return null;
        }
        return programmer.fieldName.any().in(fieldNames);
    }

    // 경력 필터링 메서드
    private BooleanExpression personalHistoryCriteria(Integer personalHistory) {
        if (personalHistory == null) {
            return null;
        }
        return programmer.personalHistory.goe(personalHistory);
    }


    public long countFiltered(List<Field> fieldNames, Integer personalHistory, Pageable pageable) {
        Long count = jpaQueryFactory
                .select(programmer.count())
                .from(programmer)
                .where(
                        fieldNameCriteria(fieldNames),
                        personalHistoryCriteria(personalHistory)
                )
                .fetchOne();
        return count != null ? count : 0L;
    }
}
