<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>List Students</title>
	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>
<div id="wrapper">
	<div id="header">
		<h2>Lista Studentów</h2>
	</div>
</div>
<div id="container">
	<div id="content">
		<input type="button" value="Add Student"
		onclick="window.location.href='add-student-form.jsp';return false;"
		class="add-student-button">


		<table class="table">
			<tr>
				<th>Id</th>
				<th>Imię</th>
				<th>Nazwisko</th>
				<th>Email</th>
				<th>Action</th>
			</tr>
			<c:forEach  var="student" items="${STUDENT_LIST}" >
				<c:url var="link" value="ServletControllerStudents">
					<c:param name="command" value="LOAD"/>
					<c:param name="studentId" value="${student.id}"/>
				</c:url>
					<c:url var="deletelink" value="ServletControllerStudents">
						<c:param name="command" value="DELETE"/>
						<c:param name="studentId" value="${student.id}"/>
					</c:url>
				<tr>
					<td>${student.id}</td>
					<td>${student.firstName}</td>
					<td>${student.lastName}</td>
					<td>${student.email}</td>
					<td><a href="${link}">Update</a>
					|
					<a href="${deletelink}"
					onclick="if(!(confirm('Are You sure You want to delete this student'))) return false">Delete</a> </td>

				</tr>
			</c:forEach>
		</table>
	</div>
</div>
</body>

</html>
