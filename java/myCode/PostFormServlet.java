package myCode;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "PostFormServlet", value = "/PostFormServlet")
@MultipartConfig // 支持文件上傳
public class PostFormServlet extends HttpServlet {
    private PreparedStatement preparedStatement;

    @Override
    public void init() throws ServletException {
        initializeJDBC();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 設定請求和回應的字元編碼為 UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        response.sendRedirect("forms/myForm.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 設定請求和回應的字元編碼為 UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8"); // 設定內容類型和字元編碼

        // 取得表單數據
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String author = request.getParameter("author");

        // 處理文件上傳
        Part filePart = request.getPart("coverImage");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        String savePath = "C:\\Users\\user\\Desktop\\self practice\\udemy\\java\\projectPic";
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir(); // 如果目錄不存在，建立目錄
        }

        String filePath = savePath + File.separator + fileName;
        filePart.write(filePath); // 保存檔案到伺服器

        // 將書籍資訊保存到資料庫
        try {
            storeBook(name, Integer.parseInt(price), author, fileName); // 增加 filePath 參數
            PrintWriter out = response.getWriter();
            out.println("Book " + name + " with cover image " + fileName + " has been stored in the database.");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeJDBC() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded...");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_review_db?useUnicode=true&characterEncoding=UTF-8", "wayne", "password");
            System.out.println("Database connected...");

            // 在 SQL 插入语句中添加 coverImagePath 字段
            preparedStatement = conn.prepareStatement("INSERT INTO book (name, price, author, imagePath) VALUES (?, ?, ?, ?);");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



    // 從 Part 獲取提交的文件名的方法
    private String getSubmittedFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        return "";
    }

    // storeBook 方法包含 filePath
    private void storeBook(String name, int price, String author, String fileName) {
        try {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, price);
            preparedStatement.setString(3, author);
            preparedStatement.setString(4, fileName); // 文件路徑
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}