package zw.org.nmr.limsehr.service;

import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.org.nmr.limsehr.domain.EhrTest;
import zw.org.nmr.limsehr.repository.EhrTestRepository;

/**
 * Service class for managing labs.
 */
@Service
@Transactional
public class EhrTestService {

    private final Logger log = LoggerFactory.getLogger(EhrTestService.class);

    @Autowired
    EhrTestRepository ehrTestRepository;

    public EhrTest save(@Valid EhrTest ehrTest) {
        if (ehrTest.getId() == null) {
            ehrTest.setId(UUID.randomUUID().toString());
        }

        return ehrTestRepository.save(ehrTest);
    }

    @Transactional(readOnly = true)
    public Page<EhrTest> findAll(Pageable pageable) {
        return ehrTestRepository.findAll(pageable);
    }

    public Optional<EhrTest> findById(String id) {
        return ehrTestRepository.findById(id);
    }

    public Optional<EhrTest> findByEhrCode(String code) {
        return ehrTestRepository.findByEhrCode(code);
    }

    public EhrTest update(EhrTest ehrTest) {
        Optional<EhrTest> pt = ehrTestRepository.findById(ehrTest.getId());
        if (pt.isPresent()) {
            return null;
        }
        log.debug("Update ehr test: {}", ehrTest.getId());
        return ehrTestRepository.save(ehrTest);
    }
}
