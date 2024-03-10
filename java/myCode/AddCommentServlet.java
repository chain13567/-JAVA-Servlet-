package myCode;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "AddCommentServlet", value = "/addComment")
public class AddCommentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 設定請求和回應的字元編碼為 UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 從請求中獲取書籍ID和評論信息
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        String reviewer = request.getParameter("reviewer");
        double rating = Double.parseDouble(request.getParameter("rating"));
        String content = request.getParameter("content");

        // 進行數據驗證，確保評論者名稱和評論內容不為空
        if (reviewer == null || reviewer.trim().isEmpty() || content == null || content.trim().isEmpty()) {
            // 如果輸入無效，設置錯誤信息並轉發到錯誤頁面
            request.setAttribute("error", "評論者和內容不能為空。");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
            return; // 結束方法執行
        }

        // 構建 SQL 語句用於插入評論數據到數據庫
        String sql = "INSERT INTO comment (bookId, reviewer, rating, content) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_review_db?useUnicode=true&characterEncoding=UTF-8", "wayne", "password");
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            // 設置 SQL 語句中的參數
            preparedStatement.setInt(1, bookId);
            preparedStatement.setString(2, reviewer);
            preparedStatement.setDouble(3, rating);
            preparedStatement.setString(4, content);

            // 執行 SQL 更新操作
            preparedStatement.executeUpdate();

            // 評論添加成功後，重定向回書籍顯示頁面
            response.sendRedirect("showBook");
        } catch (SQLException e) {
            e.printStackTrace();
            // 發生 SQL 異常時，設置錯誤信息並轉發到錯誤頁面
            request.setAttribute("error", "評論失敗，請稍後再試。");
            request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
        }
    }
}
