package zw.org.nmr.limsehr.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zw.org.nmr.limsehr.domain.SampleType;
import zw.org.nmr.limsehr.domain.SampleTypeMapper;
import zw.org.nmr.limsehr.repository.SampleTypeMapperRepository;
import zw.org.nmr.limsehr.repository.SampleTypeRepository;

/**
 * Service class for managing labs.
 */
@Service
@Transactional
public class SampleTypeMapperService {

    private final Logger log = LoggerFactory.getLogger(SampleTypeMapperService.class);

    private final SampleTypeMapperRepository sampleTypeMapperRepository;

    private final SampleTypeRepository sampleTypeRepository;

    public SampleTypeMapperService(SampleTypeMapperRepository sampleTypeMapperRepository, SampleTypeRepository sampleTypeRepository) {
        this.sampleTypeMapperRepository = sampleTypeMapperRepository;
        this.sampleTypeRepository = sampleTypeRepository;
    }

    public Optional<SampleType> resolveSampleTypeName(String labId, String ehrSampleTypeName) {
        Optional<SampleType> labSampleType = getSampleTypeForLab(labId, ehrSampleTypeName);
        if (labSampleType.isPresent()) {
            return labSampleType;
        }
        return getSampleTypeDefault(ehrSampleTypeName);
    }

    public Optional<SampleType> getSampleTypeForLab(String labId, String ehrSampleTypeName) {
        Optional<SampleTypeMapper> mappedSampleType = sampleTypeMapperRepository.findByLaboratoryIdAndEhrSampleTypeName(
            labId,
            ehrSampleTypeName
        );
        //        if(mappedSampleType.isPresent()) {
        //            return sampleTypeRepository.findBySampleTypeId(mappedSampleType.get().getSampleTypeId());
        //        }
        //        return Optional.empty();
        return mappedSampleType.flatMap(m -> sampleTypeRepository.findBySampleTypeId(mappedSampleType.get().getSampleTypeId()));
    }

    public Optional<SampleType> getSampleTypeDefault(String ehrSampleTypeName) {
        Optional<SampleTypeMapper> mappedSampleType = sampleTypeMapperRepository.findByEhrSampleTypeNameAndLaboratoryIdIsNull(
            ehrSampleTypeName
        );
        return mappedSampleType.flatMap(sampleTypeMapper -> sampleTypeRepository.findById(sampleTypeMapper.getSampleTypeId()));
    }
}
