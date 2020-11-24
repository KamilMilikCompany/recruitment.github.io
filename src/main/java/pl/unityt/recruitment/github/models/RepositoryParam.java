package pl.unityt.recruitment.github.models;

public class RepositoryParam {

    private String owner;
    private String repositoryName;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    @Override
    public String toString() {
        return "RepositoryParam{" +
                "owner='" + owner + '\'' +
                ", repositoryName='" + repositoryName + '\'' +
                '}';
    }
}
