package com.mssv24133010.bookstore.controller;

import com.mssv24133010.bookstore.entity.Book_24133010;
import com.mssv24133010.bookstore.entity.User_24133010;
import com.mssv24133010.bookstore.service.BookService_24133010;
import com.mssv24133010.bookstore.service.RatingService_24133010;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/** Cau 4: trang chi tiet 1 cuon sach + danh sach review + form them review. */
@WebServlet("/book")
public class BookDetailController_24133010 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final BookService_24133010 bookService = new BookService_24133010();
    private final RatingService_24133010 ratingService = new RatingService_24133010();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int bookId = parseId(req);
        if (bookId <= 0) { resp.sendRedirect(req.getContextPath() + "/home"); return; }

        Book_24133010 book = bookService.getBookById(bookId);
        if (book == null) { resp.sendRedirect(req.getContextPath() + "/home"); return; }

        req.setAttribute("book", book);
        req.setAttribute("authors", bookService.getAuthorsOfBook(bookId));
        req.setAttribute("reviews", ratingService.getReviewsForBook(bookId));
        req.setAttribute("reviewCount", ratingService.countReviews(bookId));
        req.setAttribute("avgRating", ratingService.averageRating(bookId));
        req.getRequestDispatcher("/WEB-INF/views/book/book_detail.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int bookId = parseId(req);
        HttpSession session = req.getSession(false);
        User_24133010 user = (session != null) ? (User_24133010) session.getAttribute("user") : null;

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login?redirect=/book?id=" + bookId);
            return;
        }

        Book_24133010 book = bookService.getBookById(bookId);
        if (book == null) { resp.sendRedirect(req.getContextPath() + "/home"); return; }

        int ratingValue = 5;
        try { ratingValue = Integer.parseInt(req.getParameter("rating")); } catch (Exception ignored) {}
        if (ratingValue < 1) ratingValue = 1;
        if (ratingValue > 5) ratingValue = 5;
        String reviewText = req.getParameter("review_text");

        ratingService.submitReview(user, book, ratingValue, reviewText);
        resp.sendRedirect(req.getContextPath() + "/book?id=" + bookId);
    }

    private int parseId(HttpServletRequest req) {
        try {
            return Integer.parseInt(req.getParameter("id"));
        } catch (Exception e) {
            return -1;
        }
    }
}
