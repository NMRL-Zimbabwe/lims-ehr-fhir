package zw.org.nmr.limsehr.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Analysis request
 */
@Entity
@Table(name = "analysis_profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AnalysisProfile extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    private String api_url;

    private String description;

    private String title;

    private String uid;

    private String path;

    private String effective;

    private String parent_uid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApi_url() {
        return api_url;
    }

    public void setApi_url(String api_url) {
        this.api_url = api_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getEffective() {
        return effective;
    }

    public void setEffective(String effective) {
        this.effective = effective;
    }

    public String getParent_uid() {
        return parent_uid;
    }

    public void setParent_uid(String parent_uid) {
        this.parent_uid = parent_uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnalysisProfile)) {
            return false;
        }
        return id != null && id.equals(((AnalysisProfile) o).id);
    }

    @Override
    public String toString() {
        return (
            "AnalysisProfile [id=" +
            id +
            ", api_url=" +
            api_url +
            ", description=" +
            description +
            ", title=" +
            title +
            ", uid=" +
            uid +
            ", path=" +
            path +
            ", effective=" +
            effective +
            ", parent_uid=" +
            parent_uid +
            "]"
        );
    }
}
