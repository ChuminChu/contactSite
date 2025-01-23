package contactSite.company;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository <Company, String> {

    Company findByUserId(String userId);
}
