package zw.org.nmr.limsehr.service.dto.laboratory.request;

import java.util.Arrays;

public class AnalysisProfile {

    private String next;

    private String pages;

    private String previous;

    private String count;

    private String pagesize;

    private String _runtime;

    private String page;

    private AnalysisProfileDetail[] items;

    public String getNext() {
        return next;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String get_runtime() {
        return _runtime;
    }

    public void set_runtime(String _runtime) {
        this._runtime = _runtime;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public AnalysisProfileDetail[] getItems() {
        return items;
    }

    public void setItems(AnalysisProfileDetail[] items) {
        this.items = items;
    }

    public void setNext(String next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return (
            "AnalysisProfile [next=" +
            next +
            ", pages=" +
            pages +
            ", previous=" +
            previous +
            ", count=" +
            count +
            ", pagesize=" +
            pagesize +
            ", _runtime=" +
            _runtime +
            ", page=" +
            page +
            ", items=" +
            Arrays.toString(items) +
            "]"
        );
    }
}
