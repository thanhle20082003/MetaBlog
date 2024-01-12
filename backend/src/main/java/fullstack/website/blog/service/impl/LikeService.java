package fullstack.website.blog.service.impl;

import fullstack.website.blog.entity.Account;
import fullstack.website.blog.entity.Like;
import fullstack.website.blog.entity.Post;
import fullstack.website.blog.model.dto.LikeDto;
import fullstack.website.blog.model.mappper.LikeMapper;
import fullstack.website.blog.repository.AccountRepository;
import fullstack.website.blog.repository.LikeRepository;
import fullstack.website.blog.repository.PostRepository;
import fullstack.website.blog.service.ILikeService;
import fullstack.website.blog.utils.common.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static fullstack.website.blog.utils.common.Search.getPageable;

@Service
@RequiredArgsConstructor
public class LikeService implements ILikeService {
    private final LikeRepository likeRepository;
    private final AccountRepository accountRepository;
    private final PostRepository postRepository;
    private final LikeMapper likeMapper;

    @Override
    public Page<LikeDto> findAllLike(SearchCriteria searchCriteria){
        Page<Like> likes = likeRepository.findAll(getPageable(searchCriteria));
        return likes.map(likeMapper::apply);
    }
    /*
     * find like by id
     * param id
     * return like
     */
    @Override
    public LikeDto findByLikeId(Long id){
        Optional<Like> like = likeRepository.findById(id);
        return like.map(likeMapper::apply).orElse(null);
    }

    /*
     * find like by post id
     * param id
     * return like
     */
    @Override
    public LikeDto findByPostId(Long postId){
        Optional<Like> like = likeRepository.findByPostId(postId);
        return like.map(likeMapper::apply).orElse(null);
    }


    /*
     * create and save a new like
     * return new like
     */

    @Override
    public LikeDto save(LikeDto likeDto){
        Account account = accountRepository.findById(likeDto.getAccountId()).get();
        Post post = postRepository.findById(likeDto.getPostId()).get();
        return likeMapper.apply(likeRepository.save(likeMapper.applyToLike(likeDto, account, post)));
    }

    @Override
    public Set<LikeDto> findAllByPostId(Long postId) {
        Set<Like> likes = likeRepository.findAllByPostId(postId);
        return likes.stream()
                .map(likeMapper::apply)
                .collect(Collectors.toSet());
    }


    /*
     * delete a like
     * param id
     */

    @Override
    public void delete(Long id) {
        likeRepository.deleteById(id);
    }
}
