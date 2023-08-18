package zw.org.nmr.limsehr.service.dto;

public class Sort {

    private String unsorted;

    private String sorted;

    private String empty;

    public String getUnsorted() {
        return unsorted;
    }

    public void setUnsorted(String unsorted) {
        this.unsorted = unsorted;
    }

    public String getSorted() {
        return sorted;
    }

    public void setSorted(String sorted) {
        this.sorted = sorted;
    }

    public String getEmpty() {
        return empty;
    }

    public void setEmpty(String empty) {
        this.empty = empty;
    }

    @Override
    public String toString() {
        return "Sort [unsorted=" + unsorted + ", sorted=" + sorted + ", empty=" + empty + "]";
    }
}
