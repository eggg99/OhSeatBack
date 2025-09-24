package com.ohseat.ohseatback.service;

import com.ohseat.ohseatback.domain.CineSquare;
import com.ohseat.ohseatback.repository.CineSquareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CineSquareService {

    private final CineSquareRepository cineSquareRepository;

    public void createPost(CineSquare post) { cineSquareRepository.insertPost(post); }

    public CineSquare getPost(Integer postId) {
        return cineSquareRepository.selectPostById(postId);
    }

    public List<CineSquare> getAllPosts(Integer categoryId) {
        return cineSquareRepository.selectAllPosts(categoryId);
    }

    public void updatePost(CineSquare post) {
        cineSquareRepository.updatePost(post);
    }

    public void deletePost(Integer postId, Integer authorId) {
        cineSquareRepository.deletePost(postId, authorId);
    }

}
