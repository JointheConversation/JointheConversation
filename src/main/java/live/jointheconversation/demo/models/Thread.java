package live.jointheconversation.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="threads")
public class Thread {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String imageUrlPath;

    @Column(insertable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date date;

    @Column(updatable = true, nullable = true, columnDefinition = "boolean default true")
    private boolean activeStatus;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="category_id")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "thread")
    @JsonBackReference
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "thread")
    @JsonBackReference
    private List<ThreadWinner> threadWinners;


    public Thread(){

    }
    public Thread(String title, String imageUrlPath, Date date, User user) {
        this.title = title;
        this.imageUrlPath=imageUrlPath;
        this.date = date;
        this.user = user;
    }

    public Thread(String title, String imageUrlPath, User user) {
        this.title = title;
        this.imageUrlPath=imageUrlPath;
        this.user = user;
    }

    public Thread(String title, String imageUrlPath, Date date, Boolean activeStatus, User user, Category category, List<Post> posts, List<ThreadWinner> threadWinners) {
        this.title = title;
        this.imageUrlPath=imageUrlPath;
        this.date = date;
        this.activeStatus = activeStatus;
        this.user = user;
        this.category = category;
        this.posts = posts;
        this.threadWinners = threadWinners;
    }

    public Long getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<ThreadWinner> getThreadWinners() {
        return threadWinners;
    }

    public void setThreadWinners(List<ThreadWinner> threadWinners) {
        this.threadWinners = threadWinners;
    }

    public boolean getActiveStatus() {
        return activeStatus;
    }


    public String getImageUrlPath() {
        return imageUrlPath;
    }

    public void setImageUrlPath(String imageUrlPath) {
        this.imageUrlPath = imageUrlPath;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }
}
