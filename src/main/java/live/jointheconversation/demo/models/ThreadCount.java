package live.jointheconversation.demo.models;

public class ThreadCount {
    private long count;
    private String threadTitle;

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
}
