package com.littleSNSMS.domain.service;

import com.littleSNSMS.domain.Post;
import com.littleSNSMS.domain.PostRepository;
import com.littleSNSMS.domain.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Long post(PostDTO.Create dto) {
        return postRepository.save(Post.post(dto));
    }
}
