package com.mssv24133010.bookstore.controller.admin;

import com.mssv24133010.bookstore.entity.Author_24133010;
import com.mssv24133010.bookstore.service.AuthorService_24133010;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

/** Cau 6: CRUD + phan trang cho bang Authors (vung admin). */
@WebServlet("/admin/authors")
public class AuthorAdminController_24133010 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final AuthorService_24133010 authorService = new AuthorService_24133010();
    private static final int ADMIN_PAGE_SIZE = 6;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                req.getRequestDispatcher("/WEB-INF/views/admin/author_form.jsp").forward(req, resp);
                break;
            case "edit":
                int editId = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("author", authorService.getAuthorById(editId));
                req.getRequestDispatcher("/WEB-INF/views/admin/author_form.jsp").forward(req, resp);
                break;
            case "delete":
                int delId = Integer.parseInt(req.getParameter("id"));
                authorService.deleteAuthor(delId);
                resp.sendRedirect(req.getContextPath() + "/admin/authors");
                break;
            default:
                int page = 1;
                try { page = Integer.parseInt(req.getParameter("page")); } catch (Exception ignored) {}
                req.setAttribute("pageResult", authorService.getAuthorsPage(page, ADMIN_PAGE_SIZE));
                req.getRequestDispatcher("/WEB-INF/views/admin/author_list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Author_24133010 author = new Author_24133010();
        String idParam = req.getParameter("authorId");
        boolean isEdit = idParam != null && !idParam.isEmpty();
        if (isEdit) author.setAuthorId(Integer.parseInt(idParam));

        author.setAuthorName(req.getParameter("author_name"));
        try { author.setDateOfBirth(LocalDate.parse(req.getParameter("date_of_birth"))); } catch (Exception ignored) {}

        if (isEdit) {
            authorService.updateAuthor(author);
        } else {
            authorService.createAuthor(author);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/authors");
    }
}
