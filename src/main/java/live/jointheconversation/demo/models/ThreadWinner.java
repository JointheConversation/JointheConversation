package live.jointheconversation.demo.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="thread_winner")
public class ThreadWinner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(insertable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date date;

    @Column(updatable = true, nullable = true, columnDefinition = "boolean default true")
    private boolean activeStatus;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "thread_id")
    private Thread thread;

    public ThreadWinner(){}

    public ThreadWinner(Thread thread) {
        this.thread = thread;
    }

    public ThreadWinner(Date date, Boolean activeStatus, Thread thread) {
        this.date = date;
        this.activeStatus = activeStatus;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }
}




