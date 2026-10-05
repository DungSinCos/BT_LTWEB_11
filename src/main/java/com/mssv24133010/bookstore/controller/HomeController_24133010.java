package com.mssv24133010.bookstore.controller;

import com.mssv24133010.bookstore.entity.Author_24133010;
import com.mssv24133010.bookstore.entity.Book_24133010;
import com.mssv24133010.bookstore.service.BookService_24133010;
import com.mssv24133010.bookstore.service.RatingService_24133010;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@WebServlet({"/", "/home"})
public class HomeController_24133010 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final BookService_24133010 bookService = new BookService_24133010();
    private final RatingService_24133010 ratingService = new RatingService_24133010();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int page = 1;
        String pageParam = req.getParameter("page");
        if (pageParam != null) {
            try { page = Integer.parseInt(pageParam); } catch (NumberFormatException ignored) {}
        }

        var pageResult = bookService.getBooksPage(page);

      
        Map<Integer, Long> reviewCountMap = new HashMap<>();
        Map<Integer, Double> avgRatingMap = new HashMap<>();
        Map<Integer, String> authorNamesMap = new HashMap<>();
        for (Book_24133010 b : pageResult.getItems()) {
            reviewCountMap.put(b.getBookId(), ratingService.countReviews(b.getBookId()));
            avgRatingMap.put(b.getBookId(), ratingService.averageRating(b.getBookId()));
            List<Author_24133010> authors = bookService.getAuthorsOfBook(b.getBookId());
            authorNamesMap.put(b.getBookId(),
                    authors.stream().map(Author_24133010::getAuthorName).collect(Collectors.joining(", ")));
        }

        req.setAttribute("pageResult", pageResult);
        req.setAttribute("reviewCountMap", reviewCountMap);
        req.setAttribute("avgRatingMap", avgRatingMap);
        req.setAttribute("authorNamesMap", authorNamesMap);
        req.getRequestDispatcher("/WEB-INF/views/book/home.jsp").forward(req, resp);
    }
}
