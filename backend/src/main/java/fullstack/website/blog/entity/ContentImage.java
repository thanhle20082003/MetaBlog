package fullstack.website.blog.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "content_image")
@Builder

public class ContentImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(255)")
    private String image;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
