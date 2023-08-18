package zw.org.nmr.limsehr.service;

import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.org.nmr.limsehr.domain.Laboratory;
import zw.org.nmr.limsehr.repository.LaboratoryRepository;
import zw.org.nmr.limsehr.service.dto.LaboratoryDTO;

/**
 * Service class for managing labs.
 */
@Service
@Transactional
public class LaboratoryService {

    private final Logger log = LoggerFactory.getLogger(LaboratoryService.class);

    private final LaboratoryRepository laboratoryRepository;

    public LaboratoryService(LaboratoryRepository laboratoryRepository) {
        this.laboratoryRepository = laboratoryRepository;
    }

    public Laboratory saveLab(LaboratoryDTO labDTO) {
        Optional<Laboratory> exists = laboratoryRepository.findByCode(labDTO.getCode());

        if (exists.isPresent()) {
            return exists.orElseThrow(null);
        }

        Laboratory lab = new Laboratory();

        lab.setId(UUID.randomUUID().toString());
        lab.setCode(labDTO.getCode());
        lab.setName(labDTO.getName());
        lab.setType(labDTO.getType());

        laboratoryRepository.save(lab);
        log.debug("Created Information for lab: {}", lab);
        return lab;
    }

    /**
     * Update all information for a specific lab, and return the modified lab.
     *
     * @param LaboratoryDTO lab to update.
     * @return updated lab.
     */
    public Optional<LaboratoryDTO> updateLab(LaboratoryDTO labDTO) {
        /*
         * Optional<LaboratoryDTO> lab =
         * laboratoryRepository.findByCode(labDTO.getCode());
         *
         * if (lab != null) {
         *
         * lab.setId(UUID.randomUUID().toString()); lab.setCode(labDTO.getCode());
         * lab.setName(labDTO.getName()); lab.setType(labDTO.getType());
         *
         * laboratoryRepository.save(lab); log.debug("Updated Information for lab: {}",
         * lab);
         *
         * return lab; } else { throw new RecordDoesNotExistException(); }
         */

        // TODO
        return Optional
            .of(laboratoryRepository.findById(labDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(lab -> {
                lab.setCode(labDTO.getCode());
                lab.setName(labDTO.getName());
                lab.setType(labDTO.getType());

                log.debug("Changed Information for laboratory: {}", lab);
                return lab;
            })
            .map(LaboratoryDTO::new);
    }

    public void deleteLab(String code) {
        laboratoryRepository
            .findOneByCode(code)
            .ifPresent(lab -> {
                laboratoryRepository.delete(lab);

                log.debug("Deleted Lab: {}", lab);
            });
    }

    @Transactional(readOnly = true)
    public Page<LaboratoryDTO> getAllLabs(Pageable pageable) {
        return laboratoryRepository.findAll(pageable).map(LaboratoryDTO::new);
    }

    public Page<Laboratory> search(Pageable pageable, String text) {
        return laboratoryRepository.findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(pageable, text, text);
    }
}
