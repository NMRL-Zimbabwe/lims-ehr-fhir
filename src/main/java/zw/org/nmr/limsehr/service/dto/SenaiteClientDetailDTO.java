package zw.org.nmr.limsehr.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SenaiteClientDetailDTO {

    private String expires;

    private String getCountry;

    private String api_url;

    private String description;

    private String title;

    private String portal_type;

    private String uid;

    private String path;

    private String effective;

    private String parent_uid;

    private String getDistrict;

    private String getProvince;

    private String modified;

    private String parent_path;

    private String id;

    private String getClientID;

    private String created;

    private String author;

    private String url;

    private String review_state;

    private String[] tags;

    private String is_folderish;

    private String parent_url;

    private String parent_id;

    private String location;

    private String[] allowedRolesAndUsers;

    private String exclude_from_nav;

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getGetCountry() {
        return getCountry;
    }

    public void setGetCountry(String getCountry) {
        this.getCountry = getCountry;
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

    public String getGetDistrict() {
        return getDistrict;
    }

    public void setGetDistrict(String getDistrict) {
        this.getDistrict = getDistrict;
    }

    public String getGetProvince() {
        return getProvince;
    }

    public void setGetProvince(String getProvince) {
        this.getProvince = getProvince;
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

    public String getGetClientID() {
        return getClientID;
    }

    public void setGetClientID(String getClientID) {
        this.getClientID = getClientID;
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

    public String getIs_folderish() {
        return is_folderish;
    }

    public void setIs_folderish(String is_folderish) {
        this.is_folderish = is_folderish;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String[] getAllowedRolesAndUsers() {
        return allowedRolesAndUsers;
    }

    public void setAllowedRolesAndUsers(String[] allowedRolesAndUsers) {
        this.allowedRolesAndUsers = allowedRolesAndUsers;
    }

    public String getExclude_from_nav() {
        return exclude_from_nav;
    }

    public void setExclude_from_nav(String exclude_from_nav) {
        this.exclude_from_nav = exclude_from_nav;
    }

    @Override
    public String toString() {
        return (
            "SenaiteClientDetailDTO [expires=" +
            expires +
            ", getCountry=" +
            getCountry +
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
            ", getDistrict=" +
            getDistrict +
            ", getProvince=" +
            getProvince +
            ", modified=" +
            modified +
            ", parent_path=" +
            parent_path +
            ", id=" +
            id +
            ", getClientID=" +
            getClientID +
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
            ", is_folderish=" +
            is_folderish +
            ", parent_url=" +
            parent_url +
            ", parent_id=" +
            parent_id +
            ", location=" +
            location +
            ", allowedRolesAndUsers=" +
            Arrays.toString(allowedRolesAndUsers) +
            ", exclude_from_nav=" +
            exclude_from_nav +
            "]"
        );
    }
}
