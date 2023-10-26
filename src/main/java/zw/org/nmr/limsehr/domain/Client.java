package zw.org.nmr.limsehr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A user.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Client extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(name = "client_id", unique = true, nullable = false)
    private String clientId;

    private String name;

    private String path;

    private String portal_type;

    @Column(name = "parent_path")
    private String parentPath;

    private String phone;

    @Column(name = "email_address")
    @JsonIgnore
    private String emailAddress;

    private int activated;

    public boolean isActive() {
        return activated > 0;
    }

    public boolean isInActive() {
        return !isActive();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPortal_type() {
        return portal_type;
    }

    public void setPortal_type(String portal_type) {
        this.portal_type = portal_type;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public int getActivated() {
        return activated;
    }

    public void setActivated(int activated) {
        this.activated = activated;
    }

    @Override
    public String toString() {
        return (
            "Client{" +
            "id='" +
            id +
            '\'' +
            ", clientId='" +
            clientId +
            '\'' +
            ", name='" +
            name +
            '\'' +
            ", path='" +
            path +
            '\'' +
            ", portal_type='" +
            portal_type +
            '\'' +
            ", parentPath='" +
            parentPath +
            '\'' +
            ", phone='" +
            phone +
            '\'' +
            ", emailAddress='" +
            emailAddress +
            '\'' +
            ", activated=" +
            activated +
            '}'
        );
    }
}
