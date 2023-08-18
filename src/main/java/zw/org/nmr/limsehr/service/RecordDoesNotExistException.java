package zw.org.nmr.limsehr.service;

public class RecordDoesNotExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RecordDoesNotExistException() {
        super("Record does not exist");
    }
}
