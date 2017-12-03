<%--
  Created by IntelliJ IDEA.
  User: njosepa
  Date: 17/03/2017
  Time: 15:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>redirect</title>
</head>
<body>
<%
//    String item = request.getParameter("addItem");
    request.getRequestDispatcher("basket.jsp").forward(request,response);

%>
</body>
</html>
