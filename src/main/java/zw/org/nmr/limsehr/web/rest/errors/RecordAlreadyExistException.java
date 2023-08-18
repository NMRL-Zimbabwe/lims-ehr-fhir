package zw.org.nmr.limsehr.web.rest.errors;

public class RecordAlreadyExistException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public RecordAlreadyExistException() {
        super(ErrorConstants.LOGIN_ALREADY_USED_TYPE, "Record already exist!", "recordManagement", "recordexists");
    }
}
