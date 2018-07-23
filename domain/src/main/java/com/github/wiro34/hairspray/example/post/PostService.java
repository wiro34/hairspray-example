package com.github.wiro34.hairspray.example.post;

import com.github.wiro34.hairspray.example.ds.user.User;

import javax.inject.Inject;

public class PostService {
    @Inject
    private PostRepository postRepository;

    public Post createNewPost(User author, String subject, String content) {
        Post post = new Post();
        post.setSubject(subject);
        post.setContent(content);
        post.setAuthor(author);
        postRepository.save(post);
        return post;
    }
}
