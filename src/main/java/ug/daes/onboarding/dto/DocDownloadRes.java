package ug.daes.onboarding.dto;

import java.util.List;

public class DocDownloadRes
{
    private int count;
    private int next;
    private int previous;
    private List<Result> results;

    public DocDownloadRes() {

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getPrevious() {
        return previous;
    }

    public void setPrevious(int previous) {
        this.previous = previous;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "DocDownloadRes{" +
                "count=" + count +
                ", next=" + next +
                ", previous=" + previous +
                ", results=" + results +
                '}';
    }
}
