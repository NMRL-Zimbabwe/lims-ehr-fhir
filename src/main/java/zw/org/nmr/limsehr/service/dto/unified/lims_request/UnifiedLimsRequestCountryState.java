package zw.org.nmr.limsehr.service.dto.unified.lims_request;

public class UnifiedLimsRequestCountryState {

    private String country;

    private String district;

    private String state;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "UnifiedLimsRequestCountryState [country=" + country + ", district=" + district + ", state=" + state + "]";
    }
}
