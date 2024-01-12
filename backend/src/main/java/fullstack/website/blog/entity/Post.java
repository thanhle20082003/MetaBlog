package fullstack.website.blog.entity;

import fullstack.website.blog.utils.enums.PostStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "post")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(50) not null")
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT not null")
    private String content;

    @Column(columnDefinition = "nvarchar(255)")
    private String image;

    @Temporal(TemporalType.DATE)
    private Date createAt;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<Like> likes;

    @OneToMany(mappedBy = "post",cascade = CascadeType.PERSIST)
    private Set<ContentImage> contentImages;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", createAt=" + createAt +
                ", status=" + status +
                ", account=" + account +
                ", category=" + category +
                '}';
    }
}
