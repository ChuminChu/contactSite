package contactSite.programmerTest;

import contactSite.programmer.ProgrammerQueryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@SpringBootTest
public class ProgrammerQueryRepositoryTest {

    @Autowired
    ProgrammerQueryRepository programmerQueryRepository;

    @Test
    void name() {
        programmerQueryRepository.findAll(List.of(), 3, PageRequest.of(1, 20));
    }
}
