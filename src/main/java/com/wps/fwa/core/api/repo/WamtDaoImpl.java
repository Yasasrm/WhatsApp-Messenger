package com.wps.fwa.core.api.repo;

import com.wps.fwa.core.api.domain.Wamt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Optional;

/**
 * @author YasasMa
 * @version 1.0.0.0
 */
@Repository
public class WamtDaoImpl implements WamtDao {

    @Autowired
    EntityManager em;

    @Override
    public Optional<Wamt> findTemplate(String templateId) {
        Wamt wamt = null;
        try {
            Query query = em.createNamedQuery("wamt.findTemplate");
            query.setParameter(1, templateId);
            wamt = (Wamt) query.getSingleResult();
        } catch (Exception e) {
        }
        return Optional.ofNullable(wamt);
    }

}
