package zw.org.nmr.limsehr.service.dto.laboratory.request;

import java.util.Arrays;

public class AnalysisProfileDetail {

    private String expires;

    private String api_url;

    private String description;

    private String title;

    private String portal_type;

    private String uid;

    private String path;

    private String effective;

    private String parent_uid;

    private String modified;

    private String parent_path;

    private String id;

    private String created;

    private String author;

    private String url;

    private String review_state;

    private String[] tags;

    private String parent_url;

    private String parent_id;

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
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

    public String getPortal_type() {
        return portal_type;
    }

    public void setPortal_type(String portal_type) {
        this.portal_type = portal_type;
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

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getParent_path() {
        return parent_path;
    }

    public void setParent_path(String parent_path) {
        this.parent_path = parent_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReview_state() {
        return review_state;
    }

    public void setReview_state(String review_state) {
        this.review_state = review_state;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getParent_url() {
        return parent_url;
    }

    public void setParent_url(String parent_url) {
        this.parent_url = parent_url;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public String toString() {
        return (
            "AnalysisProfileDetail [expires=" +
            expires +
            ", api_url=" +
            api_url +
            ", description=" +
            description +
            ", title=" +
            title +
            ", portal_type=" +
            portal_type +
            ", uid=" +
            uid +
            ", path=" +
            path +
            ", effective=" +
            effective +
            ", parent_uid=" +
            parent_uid +
            ", modified=" +
            modified +
            ", parent_path=" +
            parent_path +
            ", id=" +
            id +
            ", created=" +
            created +
            ", author=" +
            author +
            ", url=" +
            url +
            ", review_state=" +
            review_state +
            ", tags=" +
            Arrays.toString(tags) +
            ", parent_url=" +
            parent_url +
            ", parent_id=" +
            parent_id +
            "]"
        );
    }
}
