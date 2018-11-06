<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>掲示板アプリケーション</h2>

<form:form action="${pageContext.request.contextPath}/article/insertArticle" modelAttribute="articleForm">
	<form:errors path="name" cssStyle="color:red" element="div"/>
	投稿者名:<form:input path="name"/><br>
	<form:errors path="content" cssStyle="color:red" element="div"/>
	投稿内容:<form:input path="content"/><br>
	<input type="submit" value="投稿"><br>
</form:form>
<hr>

	<c:forEach var="article" items="${articleList}">
	投稿ID：<c:out value="${article.id}" /><br>
	投稿者名：<c:out value="${article.name}" /><br>
	投稿内容：<c:out value="${article.content}" /><br>
	<form action="${pageContext.request.contextPath}/article/deleteAllByArticleId" method="post">
	<input type="hidden" name="id" value="${article.id}">
	<input type="submit" value="投稿削除"><br>
	</form>
		
		<c:forEach var="comment" items="${article.commentList}" >
		コメントID：<c:out value="${comment.id}" /><br>
		コメント者名：<c:out value="${comment.name}" /><br>
		コメント内容：<c:out value="${comment.content}" /><br>
		<form action="${pageContext.request.contextPath}/article/deleteComment" method="post">
		<input type="hidden" name="id" value="${article.id}">
		<input type="submit" value="コメント削除"><br>
		</form>
		</c:forEach>
		
		<form:form action="${pageContext.request.contextPath}/article/insertComment" modelAttribute="commentForm">
		<c:if test="${article.id==commentForm.articleId}"><form:errors path="name" cssStyle="color:red" element="div"/></c:if>
		コメント者名:<form:input path="name"/><br>
		<c:if test="${article.id==commentForm.articleId}"><form:errors path="content" cssStyle="color:red" element="div"/></c:if>
		コメント内容:<form:input path="content"/><br>
		<input type="hidden" name="articleId" value="${article.id}">
		<input type="submit" value="コメント投稿"><br>
		</form:form>
		
	<hr>
	</c:forEach>
	

</body>
</html>