<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Results Page</title>
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>
<body>
<div class="container">
<h1>Here are your results</h1>
<!--Adds a button to rerun test-->
    <div class="col-md-12">
        <div id="rerun" class="row">
                <a class="btn btn-danger" href="/setupTest" role="button">Rerun Test
                </a>
        </div>
    </div>
<!--Iterates through the list and displays the test results in the table and will display a message if no results are found-->
<c:choose>
<c:when test="${testResults != null}">
    <table class="table table-striped">
    <thead class="thead-inverse">
    <tr>
        <th>ID</th>
        <th>Test Specification</th>
        <th>Test System</th>
        <th>Result</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="testsValue" items="${testResults.getTests()}">
        <td> <c:out value="${testsValue.testID}"/></td>
        <td> <c:out value="${testsValue.expectedTransition}"/></td>
        <td><c:out value="${testsValue.actualTransition}"/></td>
        <td class="<c:out value="${testsValue.result.name().toLowerCase()}"/>"><c:out value="${testsValue.result}"/></td>
        <tr></tr>
    </c:forEach>
    </tbody>
    </table>
</c:when>
    <c:otherwise><h1>No Test Results Found</h1></c:otherwise>
</c:choose>
</div>
</body>
<script src="<c:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</html>