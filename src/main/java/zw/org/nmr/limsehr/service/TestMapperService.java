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

    public Optional<Test> resolveTestName(String labId, String ehrTestName) {
        Optional<Test> labTest = getTestForLab(labId, ehrTestName);
        if (labTest.isPresent()) {
            return labTest;
        }
        return getTestDefault(ehrTestName);
    }

    public Optional<Test> getTestForLab(String labId, String ehrTestName) {
        Optional<TestMapper> mappedTest = testMapperRepository.findByLaboratoryIdAndEhrTestName(labId, ehrTestName);
        if (mappedTest.isPresent()) {
            return testTypeRepository.findByTestId(mappedTest.get().getTestId());
        }
        return Optional.empty();
    }

    public Optional<Test> getTestDefault(String ehrTestName) {
        Optional<TestMapper> mappedTest = testMapperRepository.findByEhrTestNameAndLaboratoryIdIsNull(ehrTestName);
        if (mappedTest.isPresent()) {
            return testTypeRepository.findByTestId(mappedTest.get().getTestId());
        }
        return Optional.empty();
    }
}
