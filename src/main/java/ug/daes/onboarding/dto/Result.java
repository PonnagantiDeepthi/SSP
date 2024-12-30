package ug.daes.onboarding.dto;

public class Result
{
    private String checksum;
    private String comment;
    private String document_url;
    private String download_url;
    private String encoding;
    private String file;
    private String filename;
    private int id;
    private String mimetype;
    private String page_list_url;
    private int size;
    private String timestamp;
    private String url;

    public Result() { }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDocument_url() {
        return document_url;
    }

    public void setDocument_url(String document_url) {
        this.document_url = document_url;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getPage_list_url() {
        return page_list_url;
    }

    public void setPage_list_url(String page_list_url) {
        this.page_list_url = page_list_url;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Result{" +
                "checksum='" + checksum + '\'' +
                ", comment='" + comment + '\'' +
                ", document_url='" + document_url + '\'' +
                ", download_url='" + download_url + '\'' +
                ", encoding='" + encoding + '\'' +
                ", file='" + file + '\'' +
                ", filename='" + filename + '\'' +
                ", id=" + id +
                ", mimetype='" + mimetype + '\'' +
                ", page_list_url='" + page_list_url + '\'' +
                ", size=" + size +
                ", timestamp='" + timestamp + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
