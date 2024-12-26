/*
package models;



import com.example.militanshop.models.Cart;
import com.example.militanshop.models.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartTest {

    @Test
    public void testAddProduct() {
        Cart cart = new Cart();
        Product product = new Product("Test Product", "Description", 10.0);

        cart.addProduct(product);

        assertEquals(1, cart.getProducts().size());
        assertTrue(cart.getProducts().contains(product));
    }

    @Test
    public void testRemoveProduct() {
        Cart cart = new Cart();
        Product product = new Product("Test Product", "Description", 10.0);

        cart.addProduct(product);
        cart.removeProduct(product);

        assertEquals(0, cart.getProducts().size());
        assertFalse(cart.getProducts().contains(product));
    }
}
*/