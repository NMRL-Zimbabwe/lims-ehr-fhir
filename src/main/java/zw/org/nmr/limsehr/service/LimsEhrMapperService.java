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
import zw.org.nmr.limsehr.domain.*;
import zw.org.nmr.limsehr.repository.LimsEhrMapperRepository;
import zw.org.nmr.limsehr.service.dto.EhrLImsMappingDTO;
import zw.org.nmr.limsehr.service.dto.EhrToLImsDTO;
import zw.org.nmr.limsehr.service.dto.LaboratoryDTO;
import zw.org.nmr.limsehr.service.dto.MapperDataDTO;

/**
 * Service class for managing LimsEhrMapperService.
 */
@Service
@Transactional
public class LimsEhrMapperService {

    private final Logger log = LoggerFactory.getLogger(LimsEhrMapperService.class);

    @Autowired
    LimsEhrMapperRepository limsEhrMapperRepository;

    @Autowired
    EhrTestService ehrTestService;

    @Autowired
    EhrSampleTypeService ehrSampleTypeService;

    @Autowired
    LimsTestService limsTestService;

    @Autowired
    LimsSampleTypeService limsSampleTypeService;

    @Autowired
    LaboratoryService laboratoryService;

    public LimsEhrMapper save(@Valid LimsEhrMapper mapping) {
        log.debug("save mapping: {}", mapping.getId());
        if (mapping.getId() == null) {
            mapping.setId(UUID.randomUUID().toString());
        }

        return limsEhrMapperRepository.save(mapping);
    }

    @Transactional(readOnly = true)
    public Page<EhrLImsMappingDTO> findAll(Pageable pageable) {
        Page<LimsEhrMapper> mappings = limsEhrMapperRepository.findAll(pageable);
        return mappings.map(mapping -> {
            EhrLImsMappingDTO dd = new EhrLImsMappingDTO();
            dd.setId(mapping.getId());
            Optional<EhrSampleType> ehrSampleType = ehrSampleTypeService.findById(mapping.getEhrSampleTypeId());
            Optional<EhrTest> ehrTest = ehrTestService.findById(mapping.getEhrTestId());
            Optional<SampleType> sampleType = limsSampleTypeService.findById(mapping.getLimsSampleTypeId());
            Optional<Test> test = limsTestService.findById(mapping.getLimsTestId());
            dd.setEhrSampleTypeId(mapping.getEhrSampleTypeId());
            dd.setEhrSampleType(ehrSampleType.get());
            dd.setEhrTestId(mapping.getEhrTestId());
            dd.setEhrTest(ehrTest.get());
            dd.setLimsSampleTypeId(mapping.getLimsSampleTypeId());
            dd.setLimsSampleType(sampleType.get());
            dd.setLimsTestId(mapping.getLimsTestId());
            dd.setLimsTest(test.get());
            return dd;
        });
    }

    public Optional<LimsEhrMapper> findById(String id) {
        return limsEhrMapperRepository.findById(id);
    }

    public LimsEhrMapper update(LimsEhrMapper mapping) {
        Optional<LimsEhrMapper> pt = limsEhrMapperRepository.findById(mapping.getId());
        if (pt.isPresent()) {
            return null;
        }
        log.debug("Update lims mapping: {}", mapping.getId());
        return limsEhrMapperRepository.save(mapping);
    }

    public Optional<EhrToLImsDTO> resolveTestAndSampleType(String labId, String ehrTestCode, String ehrSampleTypeCode) {
        log.info("Resolving from ehr-code of labid, sample type and tests: [{}, {}, {}]", labId, ehrSampleTypeCode, ehrTestCode);
        Optional<EhrSampleType> ehrSampleType = ehrSampleTypeService.findByEhrCode(ehrSampleTypeCode);
        Optional<EhrTest> ehrTest = ehrTestService.findByEhrCode(ehrTestCode);

        if (ehrSampleType.isEmpty()) {
            log.info("ehr sample type not found: {}", ehrSampleTypeCode);
            return Optional.empty();
        }
        if (ehrTest.isEmpty()) {
            log.info("ehr test not found: {}", ehrTestCode);
            return Optional.empty();
        }

        Optional<EhrToLImsDTO> labMapping = getMappingForLab(labId, ehrTest.get().getId(), ehrSampleType.get().getId());
        if (labMapping.isPresent()) {
            log.info("Laboratory specific mapping found: {}", labMapping.get());
            return labMapping;
        }
        return getDefaultMapping(ehrTest.get().getId(), ehrSampleType.get().getId());
    }

    private Optional<EhrToLImsDTO> getMappingForLab(String labId, String ehrTestCode, String sampleTypeId) {
        log.info("Resolving Laboratory specific mapping ...: [{}, {}, {}]", labId, sampleTypeId, ehrTestCode);
        Optional<LimsEhrMapper> mapping = limsEhrMapperRepository.findByEhrSampleTypeIdAndEhrTestIdAndLaboratoryId(
            sampleTypeId,
            ehrTestCode,
            labId
        );
        if (mapping.isEmpty()) {
            log.info("Laboratory specific mapping not found");
            return Optional.empty();
        }
        return mapToLims(mapping.get());
    }

    private Optional<EhrToLImsDTO> getDefaultMapping(String ehrTestCode, String sampleTypeId) {
        log.info("Resolving default mapping ...: [{}, {}]", sampleTypeId, ehrTestCode);
        Optional<LimsEhrMapper> mapping = limsEhrMapperRepository.findByEhrSampleTypeIdAndEhrTestIdAndLaboratoryIdIsNull(
            sampleTypeId,
            ehrTestCode
        );
        if (mapping.isEmpty()) {
            log.info("Default mapping not found");
            return Optional.empty();
        }
        return mapToLims(mapping.get());
    }

    private Optional<EhrToLImsDTO> mapToLims(LimsEhrMapper mapper) {
        Optional<SampleType> sampleType = limsSampleTypeService.findById(mapper.getLimsSampleTypeId());
        Optional<Test> test = limsTestService.findById(mapper.getLimsTestId());

        if (sampleType.isEmpty()) {
            return Optional.empty();
        }
        if (test.isEmpty()) {
            return Optional.empty();
        }

        EhrToLImsDTO ehrToLims = new EhrToLImsDTO();
        ehrToLims.setTest(test.get());
        ehrToLims.setSampleType(sampleType.get());
        return Optional.of(ehrToLims);
    }

    public MapperDataDTO getMapperData(Pageable pageable) {
        Page<EhrSampleType> ehrSampleTyes = ehrSampleTypeService.findAll(pageable);
        Page<EhrTest> ehrTests = ehrTestService.findAll(pageable);
        Page<SampleType> sampleTyes = limsSampleTypeService.findAll(pageable);
        Page<Test> tests = limsTestService.findAll(pageable);
        Page<LaboratoryDTO> labs = laboratoryService.getAllLabs(pageable);
        MapperDataDTO data = new MapperDataDTO();
        data.setEhrSampleTypes(ehrSampleTyes.getContent());
        data.setEhrTests(ehrTests.getContent());
        data.setLimsSampleTypes(sampleTyes.getContent());
        data.setLimsTests(tests.getContent());
        data.setLaboratories(labs.getContent());
        return data;
    }
}
