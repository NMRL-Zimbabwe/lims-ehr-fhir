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
import zw.org.nmr.limsehr.domain.EhrSampleType;
import zw.org.nmr.limsehr.repository.EhrSampleTypeRepository;

/**
 * Service class for managing EhrSampleType.
 */
@Service
@Transactional
public class EhrSampleTypeService {

    private final Logger log = LoggerFactory.getLogger(EhrSampleTypeService.class);

    @Autowired
    EhrSampleTypeRepository ehrSampleTypeRepository;

    public EhrSampleType save(@Valid EhrSampleType ehrSampleType) {
        if (ehrSampleType.getId() == null) {
            ehrSampleType.setId(UUID.randomUUID().toString());
        }

        return ehrSampleTypeRepository.save(ehrSampleType);
    }

    @Transactional(readOnly = true)
    public Page<EhrSampleType> findAll(Pageable pageable) {
        return ehrSampleTypeRepository.findAll(pageable);
    }

    public Optional<EhrSampleType> findById(String id) {
        return ehrSampleTypeRepository.findById(id);
    }

    public Optional<EhrSampleType> findByEhrCode(String code) {
        return ehrSampleTypeRepository.findByEhrCode(code);
    }

    public EhrSampleType update(EhrSampleType ehrSampleType) {
        Optional<EhrSampleType> pt = ehrSampleTypeRepository.findById(ehrSampleType.getId());
        if (pt.isPresent()) {
            return null;
        }
        log.debug("Update ehr test: {}", ehrSampleType.getId());
        return ehrSampleTypeRepository.save(ehrSampleType);
    }
}
