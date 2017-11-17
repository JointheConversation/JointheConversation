package live.jointheconversation.demo.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name="thread_winner")
public class ThreadWinner {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="thread_id")
    private Thread thread;

    public ThreadWinner(Thread thread) {
        this.thread = thread;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}
