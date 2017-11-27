package live.jointheconversation.demo.models;

public class ThreadCount {
    private long count;
    private String threadTitle;
    private long id;

    public ThreadCount(long count, String threadTitle, long id) {
        this.count = count;
        this.threadTitle = threadTitle;
        this.id=id;
    }

    public ThreadCount(long count, String threadTitle) {
        this.count = count;
        this.threadTitle = threadTitle;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getThreadTitle() {
        return threadTitle;
    }

    public void setThreadTitle(String threadTitle) {
        this.threadTitle = threadTitle;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
