package zw.org.nmr.limsehr.service.dto;

public class Pageable {

    private String paged;

    private String pageNumber;

    private String offset;

    private String pageSize;

    private String unpaged;

    private Sort sort;

    public String getPaged() {
        return paged;
    }

    public void setPaged(String paged) {
        this.paged = paged;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getUnpaged() {
        return unpaged;
    }

    public void setUnpaged(String unpaged) {
        this.unpaged = unpaged;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return (
            "Pageable [paged=" +
            paged +
            ", pageNumber=" +
            pageNumber +
            ", offset=" +
            offset +
            ", pageSize=" +
            pageSize +
            ", unpaged=" +
            unpaged +
            ", sort=" +
            sort +
            "]"
        );
    }
}
