package contactSite.company;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import contactSite.Field;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QCompany company = QCompany.company;

    public CompanyQueryRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Company> findAll(
            List<Field> fieldNames,  //선택 분야
            String address      //선택 지역
    ){
        return jpaQueryFactory
                .selectFrom(company)
                .where(
                        // 분야 필터링
                        fieldNameCriteria(fieldNames),
//                        //지역 필터링
                        addressCriteria(address)
                )
                //좋아요 순 정렬(내림차순)
                .orderBy(company.likeCount.desc())
                .fetch();
    }

    //분야 다중 선택
    private BooleanExpression fieldNameCriteria(List<Field> fieldNames){
        if (fieldNames == null || fieldNames.isEmpty()){
            return null;
        }
        //return company.field.in(fieldNames);
        return null;
    }

    //지역 필터링
    private BooleanExpression addressCriteria(String address){
        if (address == null){
            return null;
        }
        return company.address.eq(address);
    }
}
