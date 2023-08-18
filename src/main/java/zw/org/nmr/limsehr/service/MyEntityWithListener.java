package zw.org.nmr.limsehr.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MyEntityWithListener {

    private static final Logger log = LoggerFactory.getLogger(MyEntityWithListener.class);
    /*
     * @Autowired LaboratoryRequestMiddlewareResolver
     * laboratoryRequestMiddlewareResolver;
     *
     * @PostUpdate public void auditNewHire(AnalysisRequest analysisRequest) { log.
     * debug("Call integration service to resolve received analysis request :{} ",
     * analysisRequest);
     * //laboratoryRequestMiddlewareResolver.loadLabRequestProcess(analysisRequest);
     * //patientMiddlewareResolver.resolvePatientDetails(); log.
     * debug("Complete Call to integration service to resolve received analysis request"
     * ); }
     */

}
