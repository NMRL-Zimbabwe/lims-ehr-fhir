package zw.org.nmr.limsehr.service.dto;

public class Age {

    private String months;

    private String years;

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    @Override
    public String toString() {
        return "Age [months=" + months + ", years=" + years + "]";
    }
}
