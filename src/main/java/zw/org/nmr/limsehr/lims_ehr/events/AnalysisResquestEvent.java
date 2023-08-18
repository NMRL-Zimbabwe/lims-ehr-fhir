package zw.org.nmr.limsehr.lims_ehr.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zw.org.nmr.limsehr.domain.AnalysisRequest;

//@RepositoryEventHandler(AnalysisRequest.class)
public class AnalysisResquestEvent {

    private static final Logger log = LoggerFactory.getLogger(AnalysisResquestEvent.class);

    /*
     * @HandleBeforeCreate public void handleAuthorBeforeCreate(Author author){
     * logger.info("Inside Author Before Create...."); String name =
     * author.getName(); }
     */

    //@HandleAfterCreate
    public void handleAuthorAfterCreate(AnalysisRequest author) {
        log.info(
            "Inside Author After Create ....*************************************************.............................************************8................................................................................................................................................."
        );
        String name = author.getAnalysisRequestId();
    }
    /*
     * @HandleBeforeDelete public void handleAuthorBeforeDelete(Author author){
     * logger.info("Inside Author Before Delete ...."); String name =
     * author.getName(); }
     *
     * @HandleAfterDelete public void handleAuthorAfterDelete(Author author){
     * logger.info("Inside Author After Delete ...."); String name =
     * author.getName(); }
     */
}
