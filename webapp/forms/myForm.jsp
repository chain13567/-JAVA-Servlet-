<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>輸入書籍</title>
  <meta charset="UTF-8">
  <!-- 引入 Bootstrap CSS -->
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5"> <!-- 使用 Bootstrap 容器和一些邊距來佈局 -->
  <h2>Book Form</h2>
  <form action="/PostFormServlet" method="post" enctype="multipart/form-data" class="mt-3">
    <div class="form-group"> <!-- 使用 Bootstrap 表單組類 -->
      <label for="name">Book Name:</label>
      <input type="text" class="form-control" id="name" name="name"> <!-- 應用 Bootstrap 輸入類 -->
    </div>
    <div class="form-group">
      <label for="price">Price:</label>
      <input type="number" class="form-control" id="price" name="price">
    </div>
    <div class="form-group">
      <label for="author">Author:</label>
      <input type="text" class="form-control" id="author" name="author">
    </div>
    <div class="form-group">
      <label for="coverImage">Cover Image:</label>
      <input type="file" class="form-control-file" id="coverImage" name="coverImage">
    </div>
    <button type="submit" class="btn btn-primary">Submit</button> <!-- 應用 Bootstrap 按鈕類 -->
  </form>
</div>

<!-- 引入 Bootstrap JS 文件 -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
