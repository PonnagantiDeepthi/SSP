package ug.daes.onboarding.dto;

public class DocumentResponse
{
    private String datetime_created;
    private String description;
    private String document_change_type_url;
    private DocumentType document_type;
    private String file_list_url;
    private int id;
    private String label;
    private String language;
    private String file_latest;
    private String url;
    private String uuid;
    private String version_active;
    private String version_list_url;

    public String getDatetime_created() {
        return datetime_created;
    }

    public void setDatetime_created(String datetime_created) {
        this.datetime_created = datetime_created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocument_change_type_url() {
        return document_change_type_url;
    }

    public void setDocument_change_type_url(String document_change_type_url) {
        this.document_change_type_url = document_change_type_url;
    }

    public DocumentType getDocument_type() {
        return document_type;
    }

    public void setDocument_type(DocumentType document_type) {
        this.document_type = document_type;
    }

    public String getFile_list_url() {
        return file_list_url;
    }

    public void setFile_list_url(String file_list_url) {
        this.file_list_url = file_list_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFile_latest() {
        return file_latest;
    }

    public void setFile_latest(String file_latest) {
        this.file_latest = file_latest;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVersion_active() {
        return version_active;
    }

    public void setVersion_active(String version_active) {
        this.version_active = version_active;
    }

    public String getVersion_list_url() {
        return version_list_url;
    }

    public void setVersion_list_url(String version_list_url) {
        this.version_list_url = version_list_url;
    }

    @Override
    public String toString() {
        return "CreateDocResDto{" +
                "datetime_created='" + datetime_created + '\'' +
                ", description='" + description + '\'' +
                ", document_change_type_url='" + document_change_type_url + '\'' +
                ", document_type=" + document_type +
                ", file_list_url='" + file_list_url + '\'' +
                ", id=" + id +
                ", label='" + label + '\'' +
                ", language='" + language + '\'' +
                ", file_latest='" + file_latest + '\'' +
                ", url='" + url + '\'' +
                ", uuid='" + uuid + '\'' +
                ", version_active='" + version_active + '\'' +
                ", version_list_url='" + version_list_url + '\'' +
                '}';
    }
}
