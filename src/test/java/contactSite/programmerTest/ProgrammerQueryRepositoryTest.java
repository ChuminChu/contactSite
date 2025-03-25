package contactSite.programmerTest;

import contactSite.programmer.ProgrammerQueryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

//db에 접근하려고 한다.
@ActiveProfiles("test")
@SpringBootTest
public class ProgrammerQueryRepositoryTest {

    //리포지토리는 db에 접근하는 거니까 이 테스트는 db가 꼭 있어야한다. 근데 ci는 독립된 리눅스?에서 하니까 당연히 db를 안된다 test용 db가 있어야한다.
    @Autowired
    ProgrammerQueryRepository programmerQueryRepository;

    @Test
    void name() {
        programmerQueryRepository.findAll(List.of(), 3, PageRequest.of(1, 20));
    }
}
