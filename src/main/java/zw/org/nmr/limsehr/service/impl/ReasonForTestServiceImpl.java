package zw.org.nmr.limsehr.service.impl;

import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.org.nmr.limsehr.domain.ReasonForTest;
import zw.org.nmr.limsehr.repository.ReasonForTestRepository;
import zw.org.nmr.limsehr.service.PatientService;
import zw.org.nmr.limsehr.service.ReasonForTestService;
import zw.org.nmr.limsehr.service.dto.ReasonForTestDTO;

@Service
@Transactional
public class ReasonForTestServiceImpl implements ReasonForTestService {

    private final Logger log = LoggerFactory.getLogger(PatientService.class);

    private final ReasonForTestRepository reasonForTestRepository;

    public ReasonForTestServiceImpl(ReasonForTestRepository reasonForTestRepository) {
        this.reasonForTestRepository = reasonForTestRepository;
    }

    @Override
    public ReasonForTest saveReasonForTest(@Valid ReasonForTest patient) {
        if (patient.getId() == null) {
            patient.setId(UUID.randomUUID().toString());
        }

        return reasonForTestRepository.save(patient);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ReasonForTest> getAllReasonForTests(Pageable pageable) {
        return reasonForTestRepository.findAll(pageable);
    }

    public Optional<ReasonForTest> getReasonForTestByReasonForTestId(String reasonForTestId) {
        return reasonForTestRepository.findByReasonForTestId(reasonForTestId);
    }

    /*
     * Update patient details
     */
    @Override
    public ReasonForTest updateReasonForTest(ReasonForTestDTO reasonForTestDTO) {
        Optional<ReasonForTest> pt = reasonForTestRepository.findByReasonForTestId(reasonForTestDTO.getReasonForTestId());
        if (pt.isPresent()) {
            return null;
        }
        log.debug("Update ReasonForTest: {}", reasonForTestDTO.getReasonForTestId());

        ReasonForTest reason = new ReasonForTest();

        reason.setReasonForTestId(reasonForTestDTO.getReasonForTestId());
        reason.setName(reasonForTestDTO.getName());
        reason.setEhrCode(reasonForTestDTO.getEhrCode());
        reason.setLoincCode(reasonForTestDTO.getLoincCode());

        return reasonForTestRepository.save(reason);
    }

    @Override
    public Optional<ReasonForTest> getOne(String patientId) {
        return reasonForTestRepository.findByReasonForTestId(patientId);
    }

    @Override
    public Page<ReasonForTest> searchReasonForTest(Pageable pageable, String text) {
        return reasonForTestRepository.findByNameContainingIgnoreCaseOrReasonForTestIdContainingIgnoreCase(pageable, text, text);
    }
}
