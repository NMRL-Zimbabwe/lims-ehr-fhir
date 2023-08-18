package zw.org.nmr.limsehr.service.dto.unified.lims_request;

public class UnifiedLimsRequestPatientIdentifiersDTO {

    private String Identifier;

    private String IdentifierType;

    public String getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(String Identifier) {
        this.Identifier = Identifier;
    }

    public String getIdentifierType() {
        return IdentifierType;
    }

    public void setIdentifierType(String IdentifierType) {
        this.IdentifierType = IdentifierType;
    }

    public UnifiedLimsRequestPatientIdentifiersDTO(String identifier, String identifierType) {
        super();
        Identifier = identifier;
        IdentifierType = identifierType;
    }

    @Override
    public String toString() {
        return "UnifiedLimsRequestPatientIdentifiersDTO [Identifier=" + Identifier + ", IdentifierType=" + IdentifierType + "]";
    }
}
