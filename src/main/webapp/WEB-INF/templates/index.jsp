<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Complex-Machine Home</title>
   <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
</head>
<body>
<h1 id="welcome" class="text-center"><strong>Welcome Developers</strong></h1>
<h2 class="text-center"><small><em>A simple tool for complex systems.</em></small></h2>
<div id="intro" class="container">
    <div class="row">
        <div class="col-md-12">
            <h3 class="text-center">Load your Specification, load your Test System and find:</h3>
            <h3 class="text-center"><strong>Missing states,<br>
                Extra states,<br>
                Missing Transitions,<br>
                Extra Transitions</strong>
            </h3>
        </div>
    </div>
</div>
<br>
<div id="mainButton" class="container">
    <div class="row">
        <div class="text-center">
            <a class="btn btn-primary btn-lg" href="setupTest" role="button">
                Start Complex-Machine</h1>
            </a>
        </div>
    </div>
</div>
</body>
<script src="<c:url value="/resources/js/jquery-3.1.1.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</html>