package contactSite.company;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;


public class CustomIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        String prefix = "C";
        String query = "SELECT COUNT(*) FROM Company";
        Long count = ((Number) session.createNativeQuery(query).getSingleResult()).longValue();
        return prefix + (count + 1);
    }

}
