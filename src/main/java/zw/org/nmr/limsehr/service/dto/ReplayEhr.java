package zw.org.nmr.limsehr.service.dto;

public class ReplayEhr {

    private String number;

    private String last;

    private String numberOfElements;

    private String size;

    private String totalPages;

    private Pageable pageable;

    private Sort sort;

    private Content[] content;

    private String first;

    private String totalElements;

    private String empty;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(String numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Content[] getContent() {
        return content;
    }

    public void setContent(Content[] content) {
        this.content = content;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(String totalElements) {
        this.totalElements = totalElements;
    }

    public String getEmpty() {
        return empty;
    }

    public void setEmpty(String empty) {
        this.empty = empty;
    }

    @Override
    public String toString() {
        return (
            "ReplayEhr [number=" +
            number +
            ", last=" +
            last +
            ", numberOfElements=" +
            numberOfElements +
            ", size=" +
            size +
            ", totalPages=" +
            totalPages +
            ", pageable=" +
            pageable +
            ", sort=" +
            sort +
            ", first=" +
            first +
            ", totalElements=" +
            totalElements +
            ", empty=" +
            empty +
            "]"
        );
    }
}
