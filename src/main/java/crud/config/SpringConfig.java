package crud.config;

import crud.controller.PostController;
import crud.repository.PostRepository;
import crud.service.PostService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringConfig {

    @Bean
    public PostRepository postRepository (){
        return new PostRepository();
    }
    @Bean
    public PostService postService (){
        return new PostService(postRepository());
    }
    @Bean
    public PostController postController (){
        return new PostController(postService());
    }


}
