package fullstack.website.blog.model.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostRequest {

    private Long id;
    private String title;
    private String content;
    private Long accountId;
    private Long categoryId;
    private MultipartFile imageFile;
    private List<MultipartFile> listImageFiles;
}
