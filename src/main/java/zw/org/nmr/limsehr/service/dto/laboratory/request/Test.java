package zw.org.nmr.limsehr.service.dto.laboratory.request;

import java.util.Arrays;

public class Test {

    private Coding[] coding;

    private String name;

    private String id;

    public Coding[] getCoding() {
        return coding;
    }

    public void setCoding(Coding[] coding) {
        this.coding = coding;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Test [coding=" + Arrays.toString(coding) + ", name=" + name + ", id=" + id + "]";
    }
}
