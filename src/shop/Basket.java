package shop;

import java.util.*;

public class Basket {

    Collection<Product> items;
    ShopDB db;
    public static Map<String, Integer > store;

    public static void main(String[] args) {
        Basket b = new Basket();
        b.addItem("art1");
        System.out.println( b.getTotalString() );
        b.clearBasket();
        System.out.println( b.getTotalString() );
        // check that adding a null String causes no problems
        String pid = null;
        b.addItem( pid );
        System.out.println( b.getTotalString() );
    }

    public Basket() {
        db = ShopDB.getSingleton();
        items = new ArrayList<Product>();
        store = new HashMap<String, Integer>();
    }

    /**
     *
     * @return Collection of Product items that are stored in the basket
     *
     * Each item is a product object - need to be clear about that...
     *
     * When we come to list the Basket contents, it will be much more
     * convenient to have all the product details as items in this way
     * in order to calculate that product totals etc.
     *
     */
    public Collection<Product> getItems() {
        return items;
    }

    /**
     * empty the basket - the basket should contain no items after calling this method
     */
    public void clearBasket() {
        items.clear();
        store.clear();
    }

    /**
     *
     *  Adds an item specified by its product code to the shopping basket
     *
     * @param pid - the product code
     */
    public void addItem(String pid) {

        // need to look the product name up in the
        // database to allow this kind of item adding...
        addItem( db.getProduct( pid ) );
    }

    public void addItem(Product p) {
        // ensure that we don't add any nulls to the item list
        if (p != null ) {
            items.add(p);
            store.put(p.PID,1);
        }
    }

    /**
     *
     * @return the total value of items in the basket in pence
     */
    public int getTotal() {
        // iterate over the set of products...
        int total = 0;
        for (Product p: items){
//            System.out.printf("%s , %s%n",p,store);
            if(!store.containsKey(p.PID)) return 0;
            int quantity = store.get(p.PID);
            int price = p.price*quantity;
            total += price;
        }
        return total;
    }

    /**
     *
     * @return the total value of items in the basket as
     * a pounds and pence String with two decimal places - hence
     * suitable for inclusion as a total in a web page
     */
    public String getTotalString() {
        int total = getTotal();
        int pound = total/100;
        int pence = total % 100;
        return "£ "+ pound+"."+pence;
    }

    public String eachItemTotal(Product p){
        if(!store.containsKey(p.PID)) return "£ "+0D;
        int quantity = store.get(p.PID);
        int price = p.price;
        int total = price * quantity;
        int pound = total / 100;
        int pence = total % 100;
        String ftotal = "£ "+pound + "." + pence;
        return ftotal;
    }

    public void addMap(Product p, String q){
        int quantity = Integer.parseInt(q);
        if (store.get(p.PID) != null){
            store.put(p.PID, quantity);
        }else {
            store.put(p.PID, quantity);
        }
    }

    public void removeItem(String p){
        for (Iterator<Product> it = items.iterator(); it.hasNext();){
            if (p.equals(it.next().PID)){
                it.remove();
                break;
            }
        }
//        items.stream().peek(System.out::print).filter(item->p.equals(item.PID)).peek(System.out::println).findFirst().ifPresent(items::remove);
        store.remove(p);
    }


}
