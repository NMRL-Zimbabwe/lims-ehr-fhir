package zw.org.nmr.limsehr.service.impl;

import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.org.nmr.limsehr.domain.Laboratory;
import zw.org.nmr.limsehr.repository.LaboratoryRepository;
import zw.org.nmr.limsehr.service.LaboratoryService;
import zw.org.nmr.limsehr.service.dto.LaboratoryDTO;

@Service
@Transactional
public class LaboratoryServiceImpl implements LaboratoryService {

    private final Logger log = LoggerFactory.getLogger(LaboratoryServiceImpl.class);

    private final LaboratoryRepository laboratoryRepository;

    public LaboratoryServiceImpl(LaboratoryRepository laboratoryRepository) {
        this.laboratoryRepository = laboratoryRepository;
    }

    @Override
    public Page<LaboratoryDTO> getAllLabs(Pageable pageable) {
        log.debug("Request to List all Labs");
        return laboratoryRepository.findAll(pageable).map(LaboratoryDTO::new);
    }

    @Override
    public Page<Laboratory> search(Pageable pageable, String text) {
        return laboratoryRepository.findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(pageable, text, text);
    }

    @Override
    public Laboratory saveLab(@Valid LaboratoryDTO labDTO) {
        Optional<Laboratory> exists = laboratoryRepository.findByCode(labDTO.getCode());

        if (exists.isPresent()) {
            return exists.get();
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

    @Override
    public Optional<LaboratoryDTO> updateLab(@Valid LaboratoryDTO labDTO) {
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

    @Override
    public Optional<Laboratory> resolveByCode(String labId) {
        return laboratoryRepository.findByEhrCodeOrCode(labId, labId);
    }

    @Override
    public Optional<Laboratory> getOne(String id) {
        return laboratoryRepository.findById(id);
    }
}
