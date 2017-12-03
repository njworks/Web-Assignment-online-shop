        <%--
  Created by IntelliJ IDEA.
  User: njosepa
  Date: 18/03/2017
  Time: 14:22
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="shop.Product"%>
<%@ page import="java.util.ArrayList" %>

<jsp:useBean id='db'
             scope='session'
             class='shop.ShopDB' />
<html>
<head>
    <link href="../style.css" rel="stylesheet" type="text/css">
    <title>Search Result</title>
</head>
<body>
<div>
    <jsp:include page="logo.jsp"></jsp:include>
    <jsp:include page="searchmenu.jsp"></jsp:include>
    <jsp:include page="navigation.jsp"></jsp:include>
</div>
<div class="main">
    <%
        String artist = request.getParameter("artist");
        ArrayList arty = null;
        if (artist != null) {
            arty = db.artistSearch(artist);
        }

        String prod = request.getParameter("product");
        if (prod != null) {
            arty = db.productSearch(prod);;
        }
    %>
    <%if (arty != null && arty.size() >= 1 ){%>
    <table>
        <tr>
        <th> Title </th><th>Artist</th> <th> Price </th> <th> Image </th>
        </tr>

      <%      for (int i = 0; i < arty.size(); i++){
                String id = (String) arty.get(i);
                Product product = db.getProduct(id);
       %>
        <tr>
            <td><%=product.title%> </td>
            <td><%=product.artist%> </td>
            <td><%=product.convertprice(product)%> </td>
            <td><a href = '<%="viewProduct.jsp?pid="+product.PID%>'><img src="<%= product.thumbnail %>"/></a> </td>
        </tr>
  <%  }

        %>
    </table>
  <% }else {
      if (artist != null){
          %>
    <p>No Value found for <%= artist%>  </p>
    <%
      }else {%>
        <p>No Value found for <%=prod%>  </p>
        <%
            }
            }
        %>

</div>
</body>
</html>
