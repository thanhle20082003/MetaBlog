package fullstack.website.blog.model.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class LikeDto {

    private Long id;

    private Date createAt;

    @NonNull
    private Long postId;

    @NonNull
    private Long accountId;
}
