package fullstack.website.blog.model.mappper;

import fullstack.website.blog.entity.Account;
import fullstack.website.blog.entity.Comment;
import fullstack.website.blog.entity.Post;
import fullstack.website.blog.model.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CommentMapper implements Function<Comment, CommentDto> {
    @Override
    public CommentDto apply(Comment comment) {
        return CommentDto
                .builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .content(comment.getContent())
                .createAt(comment.getCreateAt())
                .accountId(comment.getAccount().getId())
                .build();
    }

    public Comment applyToComment(CommentDto commentDto, Post post, Account account) {
        return Comment
                .builder()
                .id(commentDto.getId())
                .post(post)
                .account(account)
                .content(commentDto.getContent())
                .createAt(commentDto.getCreateAt())
                .build();
    }
}
