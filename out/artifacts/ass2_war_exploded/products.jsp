<%@ page import="shop.Product"%>

<jsp:useBean id='db'
             scope='session'
             class='shop.ShopDB' />

<html>
<head>
    <link href="../style.css" rel="stylesheet" type="text/css">
<title>My Shop</title>
</head>
<body>
<div>
    <jsp:include page="logo.jsp"></jsp:include>
    <jsp:include page="searchmenu.jsp"></jsp:include>
    <jsp:include page="navigation.jsp"></jsp:include>
</div>
<div class="main">
<table>
<tr>
 <th> Title </th> <th>Artist</th> <th> Price </th> <th> Picture </th>
</tr>
<%
    for (Product product : db.getAllProducts() ) {
        // now use HTML literals to create the table
        // with JSP expressions to include the live data
        // but this page is unfinished - the thumbnail
        // needs a hyperlink to the product description,
        // and there should also be a way of selecting
        // pictures from a particular artist
        //art4: https://upload.wikimedia.org/wikipedia/commons/7/75/CTPgifsm.gif
        //art5: http://orig15.deviantart.net/a1da/f/2012/023/3/6/tai_running_animation_by_master_charizard-d4nfq6z.gif
        //art6:http://orig09.deviantart.net/2a00/f/2012/338/a/6/booster__s_animated_matrix_by_boost_master-d5n0mw6.gif
        //art7:http://media.tumblr.com/ac922bed246d0379655da6e980b9b5dc/tumblr_inline_mhd6zejPTB1qz4rgp.gif
        //art8:https://s-media-cache-ak0.pinimg.com/originals/30/94/29/309429dbcd3c5da822e02119f664f384.gif
%>
        <tr>
             <td> <%= product.title %> </td>
            <td> <%= product.artist %> </td>
             <td> <%= product.convertprice(product) %> </td>
             <td> <a href = '<%="viewProduct.jsp?pid="+product.PID%>'> <img src="<%= product.thumbnail %>"/> </a> </td>
        </tr>
        <%
    }
 %>
 </table>
</div>
</body>
</html>
