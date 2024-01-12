package fullstack.website.blog.model.mappper;

import fullstack.website.blog.entity.Account;
import fullstack.website.blog.entity.Like;
import fullstack.website.blog.entity.Post;
import fullstack.website.blog.model.dto.LikeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class LikeMapper implements Function<Like, LikeDto> {
    @Override
    public LikeDto apply(Like like) {
        return LikeDto
                .builder()
                .id(like.getId())
                .accountId(like.getAccount().getId())
                .createAt(like.getCreateAt())
                .postId(like.getPost().getId())
                .build();
    }

    public Like applyToLike(LikeDto likeDto, Account account, Post post) {
        return Like
                .builder()
                .id(likeDto.getId())
                .createAt(likeDto.getCreateAt())
                .account(account)
                .post(post)
                .build();
    }
}
