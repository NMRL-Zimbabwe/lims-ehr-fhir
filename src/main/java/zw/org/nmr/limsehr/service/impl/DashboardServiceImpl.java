package zw.org.nmr.limsehr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.org.nmr.limsehr.repository.LaboratoryRequestRepository;
import zw.org.nmr.limsehr.repository.PatientRepository;
import zw.org.nmr.limsehr.service.DashboardService;
import zw.org.nmr.limsehr.service.PatientService;
import zw.org.nmr.limsehr.service.dto.DashboardSummaryDTO;

@Service
@Transactional
public class DashboardServiceImpl implements DashboardService {

    private final Logger log = LoggerFactory.getLogger(PatientService.class);

    PatientRepository patientRepository;
    LaboratoryRequestRepository laboratoryRequestRepository;

    public DashboardServiceImpl(PatientRepository patientRepository, LaboratoryRequestRepository laboratoryRequestRepository) {
        this.patientRepository = patientRepository;
        this.laboratoryRequestRepository = laboratoryRequestRepository;
    }

    @Override
    public DashboardSummaryDTO countSummary() {
        DashboardSummaryDTO stats = new DashboardSummaryDTO();

        stats.setPatients(patientRepository.count());
        stats.setTotalSamples(laboratoryRequestRepository.count());
        stats.setTotalSamplesAcknowledged(laboratoryRequestRepository.countBySampleIdIsNotNull());
        return stats;
    }
}
