<%@ page import="shop.Product"%>

<jsp:useBean id='db'
             scope='session'
             class='shop.ShopDB' />

<html>
<head><link href="../style.css" rel="stylesheet" type="text/css">
<title>Item</title>
</head>
<body>
<div>
    <jsp:include page="logo.jsp"></jsp:include>
    <jsp:include page="searchmenu.jsp"></jsp:include>
    <jsp:include page="navigation.jsp"></jsp:include>
</div>
<div class="main">
<%
    String pid = request.getParameter("pid");
    Product product = db.getProduct(pid);
    if (product == null) {
        out.println( product );
    }
    else {
        %>
        <div align="center">
        <h2> <%= product.title %>  by <%= product.artist %> </h2>
        <img src="<%= product.fullimage %>" height="300" width="300" />
        <p> <%= product.description %> </p>
        <p><%= product.convertprice(product) %></p>
        <form action="<%="basket.jsp?addItem="+product.PID%>" method="post">
            <input type="submit" name="click" value="Add to Basket">
        </form>
        </div>
        <%
    }
    String sub = request.getParameter("click");
        if (sub == null){
        }else {
            request.getRequestDispatcher("redirect.jsp?addItem="+product.PID);
        }

%>
</div>
</body>
</html>
