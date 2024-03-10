<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>增加評論</title>
    <meta charset="UTF-8">
    <!-- 引入 Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-5">
    <h2>增加評論</h2>
    <form action="addComment" method="post" class="mt-3">
        <input type="hidden" name="bookId" value="${param.bookId}"/>

        <div class="form-group">
            <label for="reviewer">評論者:</label>
            <input type="text" class="form-control" id="reviewer" name="reviewer">
        </div>

        <div class="form-group">
            <label for="rating">評分:</label>
            <input type="number" class="form-control" id="rating" name="rating" step="0.1">
        </div>

        <div class="form-group">
            <label for="content">評論内容:</label>
            <textarea class="form-control" id="content" name="content" rows="3"></textarea>
        </div>

        <button type="submit" class="btn btn-primary">提交評論</button>
    </form>
</div>

<!-- 可選的引入 Bootstrap JS 文件 -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
