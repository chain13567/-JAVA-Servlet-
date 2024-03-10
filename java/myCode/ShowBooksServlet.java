package myCode;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Obj.Book;
import Obj.Comment;

@WebServlet(name = "ShowBooksServlet", value = "/showBook")
public class ShowBooksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 設定請求和回應的字元編碼為 UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        List<Book> books = new ArrayList<>();

        try {
            // 加載 MySQL JDBC 驅動
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 嘗試建立資料庫連接
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_review_db?useUnicode=true&characterEncoding=UTF-8", "wayne", "password");
                 Statement statement = conn.createStatement()) {

                ResultSet rs = statement.executeQuery("SELECT * FROM book");

                while (rs.next()) {
                    Book book = new Book();
                    book.setBookId(rs.getInt("bookId"));
                    book.setName(rs.getString("name"));
                    book.setPrice(rs.getInt("price"));
                    book.setAuthor(rs.getString("author"));
                    book.setimagePath(rs.getString("imagePath"));

                    List<Comment> comments = new ArrayList<>();
                    // 使用新的 PreparedStatement 獲取評論
                    String commentSql = "SELECT * FROM comment WHERE bookId = ?";
                    try (PreparedStatement preparedStatement = conn.prepareStatement(commentSql)) {
                        preparedStatement.setInt(1, book.getBookId());
                        try (ResultSet commentRs = preparedStatement.executeQuery()) {
                            while (commentRs.next()) {
                                Comment comment = new Comment();
                                comment.setCommentId(commentRs.getInt("commentId"));
                                comment.setBookId(commentRs.getInt("bookId"));
                                comment.setReviewer(commentRs.getString("reviewer"));
                                comment.setRating(commentRs.getDouble("rating"));
                                comment.setContent(commentRs.getString("content"));
                                comments.add(comment);
                            }
                        }
                    }
                    book.setComments(comments);
                    books.add(book);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ServletException("資料庫查詢失敗", e);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // 處理異常
            throw new ServletException("MySQL JDBC 驅動加載失敗", e);
        }

        request.setAttribute("books", books);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/showBook.jsp");
        dispatcher.forward(request, response);
    }
}

