package zw.org.nmr.limsehr.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.org.nmr.limsehr.domain.Developer;
import zw.org.nmr.limsehr.repository.DeveloperRepository;
import zw.org.nmr.limsehr.service.DeveloperService;

/**
 * Service Implementation for managing {@link Developer}.
 */
@Service
@Transactional
public class DeveloperServiceImpl implements DeveloperService {

    private final Logger log = LoggerFactory.getLogger(DeveloperServiceImpl.class);

    private final DeveloperRepository developerRepository;

    public DeveloperServiceImpl(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    @Override
    public Developer save(Developer developer) {
        log.debug("Request to save Developer : {}", developer);
        return developerRepository.save(developer);
    }

    @Override
    public Developer update(Developer developer) {
        log.debug("Request to update Developer : {}", developer);
        return developerRepository.save(developer);
    }

    @Override
    public Optional<Developer> partialUpdate(Developer developer) {
        log.debug("Request to partially update Developer : {}", developer);

        return developerRepository
            .findById(developer.getId())
            .map(existingDeveloper -> {
                if (developer.getStreetAddress() != null) {
                    existingDeveloper.setStreetAddress(developer.getStreetAddress());
                }
                if (developer.getPostalCode() != null) {
                    existingDeveloper.setPostalCode(developer.getPostalCode());
                }
                if (developer.getCity() != null) {
                    existingDeveloper.setCity(developer.getCity());
                }
                if (developer.getStateProvince() != null) {
                    existingDeveloper.setStateProvince(developer.getStateProvince());
                }

                return existingDeveloper;
            })
            .map(developerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Developer> findAll(Pageable pageable) {
        log.debug("Request to get all Developers");
        return developerRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Developer> findOne(Long id) {
        log.debug("Request to get Developer : {}", id);
        return developerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Developer : {}", id);
        developerRepository.deleteById(id);
    }
}
