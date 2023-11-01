package zw.org.nmr.limsehr.service.enums;

public enum FhirLimsCodes {
    LIMS_DEVICE_URN("url:lims:device"),
    TEST_METHOD("TEST_METHOD"),
    TEST_METHOD_CODE("test-method"),
    CRITICAL_RESULT("CRITICAL_RESULT"),

    REJECTION_REASON_CODE("rejection-reason"),
    CRITICAL_RESULT_CODE("critical-result"),
    RESULT_INTERPRETATION("RESULT_INTERPRETATION"),

    RESULT_INTERPRETATION_CODE("result-intepretation"),
    RESULT_REMARKS("RESULT_REMARKS"),
    RESULT_REMARKS_CODE("result-remarks"),
    IMPILO_CODE_URN("urn:impilo:code"),
    IMPILO_CODE_CSID("urn:impilo:cid"),
    URN_TASK_CRITICAL_RESULT_ID_PREFIX("criticalResult"),
    URN_TASK_CRITICAL_RESULT("urn:task:criticalResult"),

    STATUS_REASONS_EXTENSION_URL("urn:status:reasons"),
    LIMS_CODE_URN("urn:lims:code");

    private String value;

    FhirLimsCodes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
