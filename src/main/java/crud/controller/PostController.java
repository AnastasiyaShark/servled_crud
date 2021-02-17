package crud.controller;

import com.google.gson.Gson;
import crud.model.Post;
import crud.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;


public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;
    private final Gson gson;


    public PostController(PostService service) {
        this.service = service;
        gson = new Gson();
    }

    public void all(HttpServletResponse response) throws IOException {
        //установить Content Type (application/json) тип содержимого ответа, который мы собираемся отправить
        prepareResponse(response);
        //Collections.emptyList()
        final var data = service.all();
        //Конвертируем полученный List <Post> в json   getWriter() - Возвращает объект PrintWriter, который может отправлять символьный текст клиенту.
        response.getWriter().print(gson.toJson(data));
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        prepareResponse(response);
        final var postById = service.getById(id);
        response.getWriter().print(gson.toJson(postById));

    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        prepareResponse(response);
        final var post = gson.fromJson(body, Post.class);
        final var data = service.save(post);
        response.getWriter().print(gson.toJson(data));
    }

    public void removeById(long id, HttpServletResponse response) {
        prepareResponse(response);
        service.removeById(id);
    }

    private void prepareResponse(HttpServletResponse response) {
        response.setContentType(APPLICATION_JSON);
    }
}
