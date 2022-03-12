package com.example.webpos.db;

import com.example.webpos.model.Cart;
import com.example.webpos.model.Category;
import com.example.webpos.model.Product;

import java.util.List;

public interface PosDB {

    public List<Product> getProducts();

    public Cart saveCart(Cart cart);

    public Cart getCart();

    public Product getProduct(String productId);

    public List<Category> getCategories();

    public List<Product> getProductsByCategory(int categoryId);
    public List<Product> getProductsByCategory(Category category);

}
