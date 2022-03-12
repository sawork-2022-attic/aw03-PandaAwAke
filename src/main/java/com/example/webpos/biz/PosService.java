package com.example.webpos.biz;

import com.example.webpos.model.Cart;
import com.example.webpos.model.Category;
import com.example.webpos.model.Product;

import java.util.List;
import java.util.Optional;

public interface PosService {
    public Cart getCart();

    public Cart newCart();

    public void checkout(Cart cart);

    public double total(Cart cart);

    public boolean add(Product product, int amount);

    public boolean add(String productId, int amount);

    public boolean remove(Product product);


    public List<Product> products();
    public List<Product> productsByCategoryId(int categoryId);

    public List<Category> categories();

    public Optional<Product> findById(String productId);

    public void clearCart();
}
