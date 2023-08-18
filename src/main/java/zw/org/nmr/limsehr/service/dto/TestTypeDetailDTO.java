package zw.org.nmr.limsehr.service.dto;

public class TestTypeDetailDTO {

    private String title;

    private String uid;

    private String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TestDetailDTO [title=" + title + ", uid=" + uid + ", id=" + id + "]";
    }
}
