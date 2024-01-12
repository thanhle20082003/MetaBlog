package fullstack.website.blog.model.dto;

import fullstack.website.blog.entity.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class CommentDto {

    private Long id;

    @NotBlank
    private String content;

    private Date createAt;

    @NonNull
    private Long accountId;

    @NonNull
    private Long postId;
}
