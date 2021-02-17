package crud.servlet;

import crud.config.SpringConfig;
import crud.controller.PostController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    public static final String API_POSTS = "/api/posts";
    public static final String API_POSTS_D = "/api/posts/\\d+";
    public static final String STR = "/";
    private PostController controller;

    @Override
    public void init() {
       AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        controller = context.getBean ("postController",PostController.class);
        context.close();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var method = req.getMethod();
            if (method.equals("GET")) {
                doGet(req, resp);
                return;
            }
            if (method.equals("POST")) {
                doPost(req, resp);
                return;
            }
            if (method.equals("DELETE")) {

                doDelete(req, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

            resp.getWriter().printf("method '%s' or path '%s' not found", method, req.getRequestURI());
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var path = req.getRequestURI();

        if (path.equals(API_POSTS)) {
            controller.all(resp);
            return;
        }
        if (path.matches(API_POSTS_D)) {
            final var id = Long.parseLong(path.substring(path.lastIndexOf(STR)));
            controller.getById(id, resp);
            return;
        }
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var path = req.getRequestURI();
        if (path.equals(API_POSTS)) {
            controller.save(req.getReader(), resp);
            return;
        }
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final var path = req.getRequestURI();
        if (path.matches(API_POSTS_D)) {
            final var id = Long.parseLong(path.substring(path.lastIndexOf(STR)));
            controller.removeById(id, resp);
            return;
        }
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}

