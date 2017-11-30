package live.jointheconversation.demo.models;




import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date date;

    @OneToMany (cascade = CascadeType.ALL, mappedBy = "user")
    @JsonBackReference
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonBackReference
    private List<Thread> threads;

    @Column(nullable = true)
    private String userpic_path;

    @Column(nullable = true)
    @JsonIgnore
    private String userbanner_pic_path;

    @Column(nullable = true)
    @JsonIgnore
    private Date birthday;

    public User(){
    }

    public User(String username, String email, String password, Date date, List<Post> posts, String userpic_path, String userbanner_pic_path, Date birthday) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.date = date;
        this.posts = posts;
        this.userpic_path = userpic_path;
        this.userbanner_pic_path = userbanner_pic_path;
        this.birthday = birthday;
    }

    public User(String username, String email, String password, List<Post> posts, String userpic_path, String userbanner_pic_path) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.posts = posts;
        this.userpic_path = userpic_path;
        this.userbanner_pic_path = userbanner_pic_path;
    }

    public User(String username, String email, String password, List<Post> posts) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.posts = posts;
    }
    public User (long id, String username, String email, String password){
        this.username=username.trim();
        this.id=id;
        this.email=email.trim();
        this.password=password.trim();
    }

    public User (String username, String email, String password){
        this.username=username.trim();
        this.email=email.trim();
        this.password=password.trim();
    }

    public User(String username, String email, String password, Date date, List<Post> posts, List<Thread> threads, String userpic_path, String userbanner_pic_path, Date birthday) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.date = date;
        this.posts = posts;
        this.threads = threads;
        this.userpic_path = userpic_path;
        this.userbanner_pic_path = userbanner_pic_path;
        this.birthday = birthday;
    }
    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        email = copy.email;
        username = copy.username;
        password = copy.password;
        userpic_path = copy.userpic_path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getUserpic_path() {
        return userpic_path;
    }

    public void setUserpic_path(String userpic_path) {
        this.userpic_path = userpic_path;
    }

    public String getUserbanner_pic_path() {
        return userbanner_pic_path;
    }

    public void setUserbanner_pic_path(String userbanner_pic_path) {
        this.userbanner_pic_path = userbanner_pic_path;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }
}
