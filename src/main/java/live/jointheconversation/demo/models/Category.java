package live.jointheconversation.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="categories")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @JsonIgnore
    private String image_url_path;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    @JsonBackReference
    private List<Thread> threads;

    public Category(){}

    public Category(String title, String image_url_path) {
        this.title = title;
        this.image_url_path = image_url_path;
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

    public List<Thread> getThreads() {
        return threads;
    }

    public void setThreads(List<Thread> threads) {
        this.threads = threads;
    }
}
