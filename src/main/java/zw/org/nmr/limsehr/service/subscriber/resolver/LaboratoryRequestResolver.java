package zw.org.nmr.limsehr.service.subscriber.resolver;

import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.r4.model.Task.ParameterComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import zw.org.nmr.limsehr.domain.LaboratoryRequest;
import zw.org.nmr.limsehr.domain.Patient;
import zw.org.nmr.limsehr.repository.LaboratoryRequestRepository;
import zw.org.nmr.limsehr.service.enums.FhirLimsCodes;
import zw.org.nmr.limsehr.service.utility.DateUtility;

@Service
public class LaboratoryRequestResolver {

    private final Logger log = LoggerFactory.getLogger(LaboratoryRequestResolver.class);

    LaboratoryRequestRepository laboratoryRequestRepository;

    DateUtility dateUtility;

    public LaboratoryRequestResolver(LaboratoryRequestRepository laboratoryRequestRepository, DateUtility dateUtility) {
        this.laboratoryRequestRepository = laboratoryRequestRepository;
        this.dateUtility = dateUtility;
    }

    public LaboratoryRequest resolveAndSaveLaboratoryRequest(
        Task task,
        ServiceRequest serviceRequest,
        Specimen specimen,
        Patient patient,
        Location laboratory,
        Location facility
    ) {
        LaboratoryRequest labRequest = new LaboratoryRequest();

        // Task
        labRequest.setMiddlewareAnalysisRequestUuid(task.getIdElement().getIdPart());

        labRequest.setLaboratoryRequestId(serviceRequest.getIdElement().getIdPart());
        labRequest.setMiddlewareClientUuid(serviceRequest.getIdElement().getIdPart());
        labRequest.setPatient(patient);

        // Test Requested
        for (Coding cod : serviceRequest.getCode().getCoding()) {
            if (cod.getSystem().equals(FhirLimsCodes.IMPILO_CODE_URN.getValue())) {
                labRequest.setTestId(cod.getCode());
                // TODO: must save new mapped ehr test code instead of test name
                labRequest.setTestName(cod.getDisplay());
            }
        }

        if (specimen.hasCollection() && specimen.getCollection().hasCollected()) {
            Type collected = specimen.getCollection().getCollected();
            if (collected instanceof DateTimeType dateTimeTypeCollected) {
                labRequest.setDateCollected(dateUtility.dateToLocalDateTime(dateTimeTypeCollected.getValue()));
            }
        }

        String impiloLaboratoryCode = laboratory
            .getIdentifier()
            .stream()
            .filter(i -> i.getSystem().equals(FhirLimsCodes.IMPILO_CODE_URN.getValue()))
            .map(Identifier::getValue)
            .findFirst()
            .orElse(null);

        labRequest.setLabId(impiloLaboratoryCode);
        labRequest.setLabName(laboratory.getName());
        labRequest.setClientId(facility.getIdElement().getIdPart());
        labRequest.setClient(facility.getName());

        if (!serviceRequest.getReasonCode().isEmpty()) {
            String impiloTestReasonCode = serviceRequest
                .getReasonCode()
                .get(0)
                .getCoding()
                .stream()
                .filter(i -> i.getSystem().equals(FhirLimsCodes.IMPILO_CODE_URN.getValue()))
                .map(Coding::getCode)
                .findFirst()
                .orElse(null);
            labRequest.setReasonForTest(impiloTestReasonCode);
        }

        // TODO Add pregnant and breast feeding
        // TODO CASE ART start date
        for (ParameterComponent input : task.getInput()) {
            for (Coding coding : input.getType().getCoding()) {
                coding.getSystem(); // labRequest.setPregnant(input.getId("valueBoolean"));
            }
        }
        /*
         * task.getInput().stream().filter(null).getType().getCoding().stream().filter(i
         * -> i.getSystem().equals("urn:sys:pregnant")) .map(Coding::get)
         */

        log.debug("Specimen {}", specimen.getType().getCoding());

        // sample type
        for (Coding testCode : specimen.getType().getCoding()) {
            log.debug("sample type {}", testCode);
            if (testCode.getSystem().equals(FhirLimsCodes.IMPILO_CODE_URN.getValue())) {
                labRequest.setSampleTypeId(testCode.getCode());
                // TODO: must save new mapped sample type ehr code instead of test name
                labRequest.setSampleTypeName(testCode.getDisplay());
            }
        }

        // client sample id
        for (Identifier identifier : specimen.getIdentifier()) {
            if (identifier.getSystem().equals(FhirLimsCodes.IMPILO_CODE_CSID.getValue())) {
                labRequest.setClientSampleId(identifier.getValue());
            }
        }

        return laboratoryRequestRepository.save(labRequest);
    }
}
