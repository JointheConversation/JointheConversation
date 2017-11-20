package live.jointheconversation.demo.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="threads")
public class Thread {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String image_url_path;

    @Column(insertable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date date;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "thread")
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "thread")
    private List<ThreadWinner> threadWinners;

    public Thread(){

    }
    public Thread(String title, String image_url_path, Date date, User user) {
        this.title = title;
        this.image_url_path = image_url_path;
        this.date = date;
        this.user = user;
    }

    public Thread(String title, String image_url_path, User user) {
        this.title = title;
        this.image_url_path = image_url_path;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url_path() {
        return image_url_path;
    }

    public void setImage_url_path(String image_url_path) {
        this.image_url_path = image_url_path;
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
}
