package fullstack.website.blog.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentImageDto {

    private Long id;

    private String image;

    private MultipartFile imageFile;

    private Long postId;
}
