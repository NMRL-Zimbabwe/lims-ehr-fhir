package zw.org.nmr.limsehr.service.dto;

public class Username {

    private String __ac_name;

    private String __ac_password;

    public String get__ac_name() {
        return __ac_name;
    }

    public void set__ac_name(String __ac_name) {
        this.__ac_name = __ac_name;
    }

    public String get__ac_password() {
        return __ac_password;
    }

    public void set__ac_password(String __ac_password) {
        this.__ac_password = __ac_password;
    }

    @Override
    public String toString() {
        return "Username [__ac_name=" + __ac_name + ", __ac_password=" + __ac_password + "]";
    }
}
