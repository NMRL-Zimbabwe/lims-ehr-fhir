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
import zw.org.nmr.limsehr.domain.Test;
import zw.org.nmr.limsehr.repository.TestTypeRepository;

/**
 * Service class for managing labs.
 */
@Service
@Transactional
public class LimsTestService {

    private final Logger log = LoggerFactory.getLogger(LimsTestService.class);

    @Autowired
    TestTypeRepository testTypeRepository;

    public Test save(@Valid Test test) {
        if (test.getId() == null) {
            test.setId(UUID.randomUUID().toString());
        }

        return testTypeRepository.save(test);
    }

    @Transactional(readOnly = true)
    public Page<Test> findAll(Pageable pageable) {
        return testTypeRepository.findAll(pageable);
    }

    public Optional<Test> findById(String id) {
        return testTypeRepository.findById(id);
    }

    public Test update(Test test) {
        Optional<Test> pt = testTypeRepository.findById(test.getId());
        if (pt.isPresent()) {
            return null;
        }
        log.debug("Update lims test: {}", test.getId());
        return testTypeRepository.save(test);
    }
}
