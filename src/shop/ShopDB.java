
package shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Iterator;

public class ShopDB {

    static Connection con;
    static int nOrders = 0;
    static ShopDB singleton;

    public static void main(String[] args) throws Exception  {
//         simple method to test that ShopDB works
        System.out.println("Got this far...");
        ShopDB db = new ShopDB();
        System.out.println("created shop db");
        shop.Basket basket = new shop.Basket();
        System.out.println("created the basket");

        System.out.println("Testing getAllProducts");
        Collection c = db.getAllProducts();
        for (Iterator i = c.iterator(); i.hasNext() ; ) {
            Product p = (Product) i.next();
            System.out.println( p );
        }
        System.out.println("Testing getProduct(pid)");
        Product product = db.getProduct("art1");
        System.out.println( product );

        System.out.println("Testing order: ");
        basket.addItem( product );
        System.out.println("added an item");
        db.order( basket , "Simon" );
        System.out.println("order done");

    }

    public ShopDB() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            System.out.println("loaded class");
            con = DriverManager.getConnection("jdbc:hsqldb:file:\\tomcat\\webapps\\ass2\\shopdb", "sa", "");
//            con = DriverManager.getConnection("jdbc:hsqldb:file:\\ass2\\web\\shopdb", "sa", "");
            System.out.println("created con");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public static ShopDB getSingleton() {
        if (singleton == null) {
            singleton = new ShopDB();
        }
        return singleton;
    }

    public ResultSet getProducts() {
        try {
            Statement s = con.createStatement();
            System.out.println("Created statement");
            ResultSet rs = s.executeQuery("Select * from Product");
            System.out.println("Returning result set...");
            return rs;
        }
        catch(Exception e) {
            System.out.println( "Exception in getProducts(): " + e );
            return null;
        }
    }

    public static Collection<Product> getAllProducts() {
        return getProductCollection("Select * from Product");
    }

    public Product getProduct(String pid) {
        try {
            // re-use the getProductCollection method
            // even though we only expect to get a single Product Object
            String query = "Select * from Product where PID = '" + pid + "'";
            Collection<Product> c = getProductCollection( query );
            Iterator<Product> i = c.iterator();
            return i.next();
        }
        catch(Exception e) {
            // unable to find the product matching that pid
            return null;
        }
    }

    public static Collection<Product> getProductCollection(String query) {
        LinkedList<Product> list = new LinkedList<Product>();
        try {
            Statement s = con.createStatement();

            ResultSet rs = s.executeQuery(query);
            while ( rs.next() ) {
                Product product = new Product(
                        rs.getString("PID"),
                        rs.getString("Artist"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getInt("price"),
                        rs.getString("thumbnail"),
                        rs.getString("fullimage")
                );
                list.add( product );
            }
            return list;
        }
        catch(Exception e) {
            System.out.println( "Exception in getProducts(): " + e );
            return null;
        }
    }

    public void order(Basket basket , String customer) {
        try {
            // create a unique order id
            String orderId = System.currentTimeMillis() + ":" + nOrders++;

            // iterate over the basket of contents ...

            Iterator<Product> i = basket.getItems().iterator();
            while (i.hasNext()) {
                Product product = i.next();
                // and place the order for each one
                order( con, product, orderId, customer );
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    private void order(Connection con, Product p, String orderId, String customer) throws Exception {
        PreparedStatement ps = con.prepareStatement("INSERT INTO ORDERS  VALUES (?, ?, ?, ? , ?)");

        int quantity = Basket.store.get(p.PID);
        double price = quantity * p.price;

        ps.setString(1, p.PID);
        ps.setString(2,orderId);
        ps.setString(3,customer);
        ps.setInt(4, quantity);
        ps.setDouble(5, price);

        ps.executeUpdate();

        Statement stmt = con.createStatement();
        String query = "select * from orders";
        ResultSet rs =  stmt.executeQuery(query);

        while (rs.next()) {
            String one = rs.getString("pid");
            String two = rs.getString("orderid");
            String three = rs.getString("email");
            Double four = rs.getDouble("quantity");
            Double five = rs.getDouble("price");
            System.out.println(one + "  " + two+"   "+three + "q: "+ four + "  " + five);
        }

    }

    public static ArrayList artistSearch(String value){
        ArrayList id = new ArrayList();
        String cap = value.substring(0, 1).toUpperCase() + value.substring(1);
        try {
            con = DriverManager.getConnection("jdbc:hsqldb:file:\\tomcat\\webapps\\ass2\\shopdb", "sa", "");
            PreparedStatement search = con.prepareStatement("SELECT PID FROM PRODUCT WHERE ARTIST = (?)");
            search.setString(1, cap);

            ResultSet rs =  search.executeQuery();
            while (rs.next()){
                id.add(rs.getString("pid"));
            }

            }catch (Exception e){
                System.out.println(e);
            }
        return id;
    }

    //searchTerm.match("(row|col) [1-4]");
    //title.match(".*"+searchTerm+".*");
    public static ArrayList productSearch(String value){
        ArrayList id = new ArrayList();
        try {
            ArrayList titles = new ArrayList();
            Collection c = getAllProducts();
            if (value.matches("[a-zA-Z ]*")){
            for (Iterator i = c.iterator(); i.hasNext() ; ) {
                Product p = (Product) i.next();
                String check = p.title;
                String small = check.substring(0, 1).toLowerCase() + check.substring(1);
                String valueS = value.toLowerCase();
                if (small.matches(".*"+valueS+".*")){
                    titles.add(check);
                }
            }

            con = DriverManager.getConnection("jdbc:hsqldb:file:\\tomcat\\webapps\\ass2\\shopdb", "sa", "");
            PreparedStatement prodSearch = con.prepareStatement("SELECT PID FROM PRODUCT WHERE TITLE = (?)");
                ResultSet answers = null;
                for (int i = 0; i < titles.size(); i++){
                    prodSearch.setString(1, String.valueOf(titles.get(i)));
                    answers =  prodSearch.executeQuery();
                    while (answers.next()){
                        id.add(answers.getString("pid"));
                    }
                }
        }
           }catch (Exception e){
        System.out.println(e);
    }
        return id;
    }
}
