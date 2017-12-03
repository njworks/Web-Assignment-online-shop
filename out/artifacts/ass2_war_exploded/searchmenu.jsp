<%--
  Created by IntelliJ IDEA.
  User: njosepa
  Date: 18/03/2017
  Time: 22:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>searchmenu</title>
    <script>
        function validateForm() {
            var x = document.forms["artistS"]["artist"].value;
            if (x == "") {
                alert("Enter artist name");
                return false;
            }
        }

        function validateFormZ() {
            var x = document.forms["productS"]["product"].value;
            if (x == "") {
                alert("Enter product name or character");
                return false;
            }
        }
    </script>
</head>
<body>
<div class="searchClass">
    <form name="artistS" action="<%="search.jsp?artist="+request.getParameter("artist")%>" onsubmit="return validateForm()">
        <input type="text" name="artist" placeholder="Artist Name">
        <input type="submit" value="Artist Search">
    </form>
</div>

<div class="searchClass2">
    <form name="productS" action="<%="search.jsp?product="+request.getParameter("product")%>" onsubmit="return validateFormZ()">
        <input type="text" name="product" placeholder="Product Title">
        <input type="submit" value="Product Search">
    </form>
</div>

</body>
</html>
