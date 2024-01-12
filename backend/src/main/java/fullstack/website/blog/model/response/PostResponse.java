package fullstack.website.blog.model.response;

import fullstack.website.blog.entity.Account;
import fullstack.website.blog.entity.Category;
import fullstack.website.blog.model.dto.AccountDto;
import fullstack.website.blog.model.dto.CategoryDto;
import fullstack.website.blog.model.dto.ContentImageDto;
import fullstack.website.blog.model.dto.PostDto;
import fullstack.website.blog.utils.enums.PostStatus;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private Long id;

    private String title;

    private String content;

    private String image;

    private Date createAt;

    private PostStatus status;

    private AccountDto account;

    private CategoryDto category;

    private Set<ContentImageDto> imageContent;

    private Long totalLike;

}
