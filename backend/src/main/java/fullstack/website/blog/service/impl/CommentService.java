package fullstack.website.blog.service.impl;

import fullstack.website.blog.entity.Account;
import fullstack.website.blog.entity.Comment;
import fullstack.website.blog.entity.Post;
import fullstack.website.blog.model.dto.CommentDto;
import fullstack.website.blog.model.mappper.CommentMapper;
import fullstack.website.blog.repository.AccountRepository;
import fullstack.website.blog.repository.CommentRepository;
import fullstack.website.blog.repository.PostRepository;
import fullstack.website.blog.service.ICommentService;
import fullstack.website.blog.utils.common.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static fullstack.website.blog.utils.common.Search.getPageable;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;
    private final AccountRepository accountRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    @Override
    public Page<CommentDto> findAllComment(SearchCriteria searchCriteria){
        Page<Comment> comments = commentRepository.findAll(getPageable(searchCriteria));
        return comments.map(commentMapper::apply);
    }
    /*
     * find comment by id
     * param id
     * return comment
     */

    @Override
    public CommentDto findByCommentId(Long id){
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.map(commentMapper::apply).orElse(null);
    }


    /*
     * create and save a new comment
     * return new comment
     */
    @Override
    public CommentDto save(CommentDto commentDto){
        Account account = accountRepository.findById(commentDto.getAccountId()).get();
        Post post = postRepository.findById(commentDto.getPostId()).get();
        return commentMapper.apply(commentRepository.save(commentMapper.applyToComment(commentDto, post, account)));
    }


    /*
     * delete a comment
     * param id
     */
    @Override
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
