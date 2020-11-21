package pl.unityt.recruitment.github.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RepositoriesInfo {

    @JsonProperty("fullName")
    private String fullName;
    private String description;
    @JsonProperty("cloneUrl")
    private String cloneUrl;
    @JsonProperty("stars")
    private int stars;
    @JsonProperty("createdAt")
    private String createdAt;

    public RepositoriesInfo() {
    }

    public RepositoriesInfo(String fullName, String description, String cloneUrl, int stars, String createdAt) {
        this.fullName = fullName;
        this.description = description;
        this.cloneUrl = cloneUrl;
        this.stars = stars;
        this.createdAt = createdAt;
    }

    @JsonProperty("fullName")
    public String getFullName() {
        return fullName;
    }

    @JsonProperty("full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("cloneUrl")
    public String getCloneUrl() {
        return cloneUrl;
    }

    @JsonProperty("clone_url")
    public void setCloneUrl(String cloneUrl) {
        this.cloneUrl = cloneUrl;
    }

    @JsonProperty("stars")
    public int getStars() {
        return stars;
    }

    @JsonProperty("subscribers_count")
    public void setStars(int stars) {
        this.stars = stars;
    }

    @JsonProperty("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "RepositoriesInfo{" +
                "fullName='" + fullName + '\'' +
                ", description='" + description + '\'' +
                ", cloneUrl='" + cloneUrl + '\'' +
                ", stars=" + stars +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
