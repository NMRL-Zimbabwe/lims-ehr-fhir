package zw.org.nmr.limsehr.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.org.nmr.limsehr.domain.Test;
import zw.org.nmr.limsehr.domain.TestMapper;
import zw.org.nmr.limsehr.repository.TestMapperRepository;
import zw.org.nmr.limsehr.repository.TestTypeRepository;

/**
 * Service class for managing labs.
 */
@Service
@Transactional
public class TestMapperService {

    private final Logger log = LoggerFactory.getLogger(TestMapperService.class);

    private final TestMapperRepository testMapperRepository;

    private final TestTypeRepository testTypeRepository;

    public TestMapperService(TestMapperRepository testMapperRepository, TestTypeRepository testTypeRepository) {
        this.testMapperRepository = testMapperRepository;
        this.testTypeRepository = testTypeRepository;
    }

    public Optional<Test> resolveTestName(String labId, String ehrTestCode, String sampleTypeId) {
        Optional<Test> labTest = getTestForLab(labId, ehrTestCode, sampleTypeId);
        if (labTest.isPresent()) {
            return labTest;
        }
        return getTestDefault(ehrTestCode, sampleTypeId);
    }

    public Optional<Test> getTestForLab(String labId, String ehrTestCode, String sampleTypeId) {
        Optional<TestMapper> mappedTest = testMapperRepository.findByLaboratoryIdAndEhrTestNameAndSampleTypeId(
            labId,
            ehrTestCode,
            sampleTypeId
        );
        return mappedTest.flatMap(testMapper -> testTypeRepository.findById(testMapper.getTestId()));
    }

    public Optional<Test> getTestDefault(String ehrTestCode, String sampleTypeId) {
        Optional<TestMapper> mappedTest = testMapperRepository.findByEhrTestNameAndSampleTypeIdAndLaboratoryIdIsNull(
            ehrTestCode,
            sampleTypeId
        );
        return mappedTest.flatMap(testMapper -> testTypeRepository.findById(testMapper.getTestId()));
    }
}
