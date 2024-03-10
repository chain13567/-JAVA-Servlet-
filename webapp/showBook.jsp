<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>書籍列表</title>
    <meta charset="UTF-8">
    <!-- 引入 Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-3">
    <h2>書籍列表</h2>
    <div class="row">
        <c:forEach var="book" items="${books}" varStatus="status">
        <div class="col-md-4 mb-3">
            <div class="card">
                <img src="${pageContext.request.contextPath}/pic/${book.imagePath}" class="card-img-top" alt="封面圖片" style="height: 300px; object-fit: cover;"/>
                <div class="card-body">
                    <h5 class="card-title">${book.name} - ${book.author}</h5>
                    <p class="card-text">價格: ${book.price}</p>
                    <h4>評論:</h4>
                    <c:forEach var="comment" items="${book.comments}">
                        <p>${comment.reviewer} (${comment.rating}): ${comment.content}</p>
                    </c:forEach>
                    <a href="addComment.jsp?bookId=${book.bookId}" class="btn btn-primary">添加評論</a>
                </div>
            </div>
        </div>
        <!-- 每3本書換行 -->
        <c:if test="${status.index % 3 == 2}">
    </div><div class="row">
    </c:if>
    </c:forEach>
</div>
</div>

<!-- 可選的引入 Bootstrap JS 文件 -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
