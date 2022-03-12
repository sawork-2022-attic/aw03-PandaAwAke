package com.example.webpos.biz;

import com.example.webpos.db.PosDB;
import com.example.webpos.model.Cart;
import com.example.webpos.model.Category;
import com.example.webpos.model.Item;
import com.example.webpos.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PosServiceImp implements PosService {

    private PosDB posDB;

    @Autowired
    public void setPosDB(PosDB posDB) {
        this.posDB = posDB;
    }

    @Override
    public Cart getCart() {

        Cart cart = posDB.getCart();
        if (cart == null){
            cart = this.newCart();
        }
        return cart;
    }

    @Override
    public Cart newCart() {
        return posDB.saveCart(new Cart());
    }

    @Override
    public void checkout(Cart cart) {

    }

    @Override
    public double total(Cart cart) {
        double total = 0;

        for (int i = 0; i < cart.getItems().size(); i++) {
            total += cart.getItems().get(i).getQuantity() * cart.getItems().get(i).getProduct().getPrice();
        }

        return total;
    }

    @Override
    public boolean add(Product product, int amount) {
        if (product == null) return false;

        for (Item item : getCart().getItems()) {
            if (item.getProduct().equals(product)) {
                int quantity = item.getQuantity() + amount;
                if (quantity <= 0) {
                    getCart().getItems().remove(item);
                } else {
                    item.setQuantity(quantity);
                }
                return true;
            }
        }

        getCart().getItems().add(new Item(product, amount));
        return true;
    }

    @Override
    public boolean add(String productId, int amount) {
        Product product = posDB.getProduct(productId);
        return add(product, amount);
    }

    @Override
    public boolean remove(Product product) {
        if (product == null) {
            return false;
        }
        for (Item item : getCart().getItems()) {
            if (item.getProduct().equals(product)) {
                getCart().getItems().remove(item);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Product> products() {
        return posDB.getProducts();
    }

    @Override
    public List<Product> productsByCategoryId(int categoryId) {
        return posDB.getProductsByCategory(categoryId);
    }

    @Override
    public List<Category> categories() {
        return posDB.getCategories();
    }

    @Override
    public Optional<Product> findById(String productId) {
        return Optional.ofNullable(posDB.getProduct(productId));
    }

    @Override
    public void clearCart() {
        getCart().getItems().clear();
    }
}
