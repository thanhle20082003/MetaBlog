package fullstack.website.blog.model.dto;

import fullstack.website.blog.utils.enums.PostStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class PostDto {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String image;

    private MultipartFile imageFile;

    private Date createAt;

    private PostStatus status;

    @NonNull
    private Long accountId;

    @NonNull
    private Long categoryId;

    private Set<ContentImageDto> listContentImages;

}
