package crud.repository;

import crud.exception.NotFoundException;
import crud.model.Post;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Vector;



public class PostRepository {
    private final List<Post> allPosts;
    private long idCounter;

    public PostRepository() {
        this.allPosts = new Vector<>();
        idCounter = 0;
    }

    public List<Post> all() {
        return allPosts;
    }

    public Optional<Post> getById(long id) {

        for (Post post : allPosts) {
            if (id == post.getId()) {
                return Optional.of(post);
            }
        }
        return Optional.empty();
    }

    public Post save(Post savePost) {
        long idSavePost = savePost.getId();

        if (idSavePost == 0) {
            savePost.setId(++idCounter);
            allPosts.add(savePost);
            return savePost;
        } else {
            Optional<Post> newPost = getById(idSavePost);
            if (newPost.isPresent()) {
                Post post = newPost.get();
                post.setContent(savePost.getContent());
                return post;
            } else {
                throw new NotFoundException(String.format("Пост с ID = %d невозможно сохранить!", idSavePost));
            }
        }
    }

    public void removeById(long id) {
        allPosts.removeIf(post -> id == post.getId());
    }
}
