package zw.org.nmr.limsehr.service.dto.laboratory.request;

import java.time.LocalDate;
import java.util.Arrays;

public class LaboratoryRequestEhrDTO {

    private Test test;

    private String clientReference;

    private String labReference;

    private LocalDate date;

    private String id;

    private Facility facility;

    private StandardDTO laboratory;

    private Sample sample;

    private Status[] status;

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public String getClientReference() {
        return clientReference;
    }

    public void setClientReference(String clientReference) {
        this.clientReference = clientReference;
    }

    public String getLabReference() {
        return labReference;
    }

    public void setLabReference(String labReference) {
        this.labReference = labReference;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public Status[] getStatus() {
        return status;
    }

    public void setStatus(Status[] status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public StandardDTO getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(StandardDTO laboratory) {
        this.laboratory = laboratory;
    }

    @Override
    public String toString() {
        return (
            "LaboratoryRequestEhrDTO [test=" +
            test +
            ", clientReference=" +
            clientReference +
            ", labReference=" +
            labReference +
            ", date=" +
            date +
            ", id=" +
            id +
            ", facility=" +
            facility +
            ", laboratory=" +
            laboratory +
            ", sample=" +
            sample +
            ", status=" +
            Arrays.toString(status) +
            "]"
        );
    }
}
