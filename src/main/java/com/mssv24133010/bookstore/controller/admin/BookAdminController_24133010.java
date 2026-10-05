package com.mssv24133010.bookstore.controller.admin;

import com.mssv24133010.bookstore.entity.Book_24133010;
import com.mssv24133010.bookstore.service.AuthorService_24133010;
import com.mssv24133010.bookstore.service.BookService_24133010;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** Cau 6: CRUD + phan trang cho bang Books (vung admin). */
@WebServlet("/admin/books")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class BookAdminController_24133010 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final BookService_24133010 bookService = new BookService_24133010();
    private final AuthorService_24133010 authorService = new AuthorService_24133010();
    private static final int ADMIN_PAGE_SIZE = 6;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                req.setAttribute("allAuthors", authorService.getAllAuthors());
                req.getRequestDispatcher("/WEB-INF/views/admin/book_form.jsp").forward(req, resp);
                break;
            case "edit":
                int editId = Integer.parseInt(req.getParameter("id"));
                Book_24133010 book = bookService.getBookById(editId);
                req.setAttribute("book", book);
                req.setAttribute("selectedAuthorIds",
                        bookService.getAuthorsOfBook(editId).stream().map(a -> a.getAuthorId()).toList());
                req.setAttribute("allAuthors", authorService.getAllAuthors());
                req.getRequestDispatcher("/WEB-INF/views/admin/book_form.jsp").forward(req, resp);
                break;
            case "delete":
                int delId = Integer.parseInt(req.getParameter("id"));
                bookService.deleteBook(delId);
                resp.sendRedirect(req.getContextPath() + "/admin/books");
                break;
            case "view":
                int viewId = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("book", bookService.getBookById(viewId));
                req.setAttribute("authors", bookService.getAuthorsOfBook(viewId));
                req.getRequestDispatcher("/WEB-INF/views/admin/book_view.jsp").forward(req, resp);
                break;
            default:
                int page = 1;
                try { page = Integer.parseInt(req.getParameter("page")); } catch (Exception ignored) {}
                req.setAttribute("pageResult", bookService.getBooksPageForAdmin(page, ADMIN_PAGE_SIZE));
                req.getRequestDispatcher("/WEB-INF/views/admin/book_list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Book_24133010 book = new Book_24133010();
        String idParam = req.getParameter("bookId");
        boolean isEdit = idParam != null && !idParam.isEmpty();
        if (isEdit) book.setBookId(Integer.parseInt(idParam));

        book.setIsbn(req.getParameter("isbn"));
        book.setTitle(req.getParameter("title"));
        book.setPublisher(req.getParameter("publisher"));
        book.setDescription(req.getParameter("description"));
        try { book.setPrice(new BigDecimal(req.getParameter("price"))); } catch (Exception e) { book.setPrice(BigDecimal.ZERO); }
        try { book.setQuantity(Integer.parseInt(req.getParameter("quantity"))); } catch (Exception e) { book.setQuantity(0); }
        try { book.setPublishDate(LocalDate.parse(req.getParameter("publish_date"))); } catch (Exception ignored) {}

        String uploadedFileName = handleCoverUpload(req);
        if (uploadedFileName != null) {
            book.setCoverImage(uploadedFileName);
        } else if (isEdit) {
            Book_24133010 existing = bookService.getBookById(book.getBookId());
            book.setCoverImage(existing != null ? existing.getCoverImage() : null);
        }

        String[] authorIdParams = req.getParameterValues("authorIds");
        List<Integer> authorIds = new ArrayList<>();
        if (authorIdParams != null) {
            for (String s : authorIdParams) authorIds.add(Integer.parseInt(s));
        }

        if (isEdit) {
            bookService.updateBook(book, authorIds);
        } else {
            bookService.createBook(book, authorIds);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/books");
    }

    private String handleCoverUpload(HttpServletRequest req) throws IOException, ServletException {
        Part filePart = req.getPart("coverImageFile");
        if (filePart == null || filePart.getSize() == 0) return null;

        String uploadDir = getServletContext().getRealPath("/assets/uploads");
        Path uploadPath = Path.of(uploadDir);
        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

        String original = filePart.getSubmittedFileName();
        String ext = original != null && original.contains(".") ? original.substring(original.lastIndexOf('.')) : "";
        String newName = UUID.randomUUID() + ext;

        try (InputStream in = filePart.getInputStream()) {
            Files.copy(in, uploadPath.resolve(newName), StandardCopyOption.REPLACE_EXISTING);
        }
        return newName;
    }
}
