package ug.daes.onboarding.dto;

public class DocumentType
{
    private String delete_time_period;
    private String delete_time_unit;
    private String filename_generator_backend;
    private String filename_generator_backend_arguments;
    private int id;
    private String label;
    private String quick_label_list_url;
    private String trash_time_period;
    private String trash_time_unit;
    private String url;

    public DocumentType() { }

    public String getDelete_time_period() {
        return delete_time_period;
    }

    public void setDelete_time_period(String delete_time_period) {
        this.delete_time_period = delete_time_period;
    }

    public String getDelete_time_unit() {
        return delete_time_unit;
    }

    public void setDelete_time_unit(String delete_time_unit) {
        this.delete_time_unit = delete_time_unit;
    }

    public String getFilename_generator_backend() {
        return filename_generator_backend;
    }

    public void setFilename_generator_backend(String filename_generator_backend) {
        this.filename_generator_backend = filename_generator_backend;
    }

    public String getFilename_generator_backend_arguments() {
        return filename_generator_backend_arguments;
    }

    public void setFilename_generator_backend_arguments(String filename_generator_backend_arguments) {
        this.filename_generator_backend_arguments = filename_generator_backend_arguments;
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

    public String getQuick_label_list_url() {
        return quick_label_list_url;
    }

    public void setQuick_label_list_url(String quick_label_list_url) {
        this.quick_label_list_url = quick_label_list_url;
    }

    public String getTrash_time_period() {
        return trash_time_period;
    }

    public void setTrash_time_period(String trash_time_period) {
        this.trash_time_period = trash_time_period;
    }

    public String getTrash_time_unit() {
        return trash_time_unit;
    }

    public void setTrash_time_unit(String trash_time_unit) {
        this.trash_time_unit = trash_time_unit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "DocumentType{" +
                "delete_time_period='" + delete_time_period + '\'' +
                ", delete_time_unit='" + delete_time_unit + '\'' +
                ", filename_generator_backend='" + filename_generator_backend + '\'' +
                ", filename_generator_backend_arguments='" + filename_generator_backend_arguments + '\'' +
                ", id=" + id +
                ", label='" + label + '\'' +
                ", quick_label_list_url='" + quick_label_list_url + '\'' +
                ", trash_time_period='" + trash_time_period + '\'' +
                ", trash_time_unit='" + trash_time_unit + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
