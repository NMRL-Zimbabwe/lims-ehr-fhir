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
import zw.org.nmr.limsehr.domain.SampleType;
import zw.org.nmr.limsehr.repository.SampleTypeRepository;

/**
 * Service class for managing labs.
 */
@Service
@Transactional
public class LimsSampleTypeService {

    private final Logger log = LoggerFactory.getLogger(LimsSampleTypeService.class);

    @Autowired
    SampleTypeRepository sampleTypeRepository;

    public SampleType save(@Valid SampleType sampleType) {
        if (sampleType.getId() == null) {
            sampleType.setId(UUID.randomUUID().toString());
        }

        return sampleTypeRepository.save(sampleType);
    }

    @Transactional(readOnly = true)
    public Page<SampleType> findAll(Pageable pageable) {
        return sampleTypeRepository.findAll(pageable);
    }

    public Optional<SampleType> findById(String id) {
        return sampleTypeRepository.findById(id);
    }

    public SampleType update(SampleType sampleType) {
        Optional<SampleType> pt = sampleTypeRepository.findById(sampleType.getId());
        if (pt.isPresent()) {
            return null;
        }
        log.debug("Update lims test: {}", sampleType.getId());
        return sampleTypeRepository.save(sampleType);
    }
}
