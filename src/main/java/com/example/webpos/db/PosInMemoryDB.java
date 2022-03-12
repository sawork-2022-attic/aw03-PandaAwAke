package com.example.webpos.db;

import com.example.webpos.model.Cart;
import com.example.webpos.model.Category;
import com.example.webpos.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PosInMemoryDB implements PosDB {
    private final List<Product> products = new ArrayList<>();
    private final List<Category> categories = new ArrayList<Category>();

    private Cart cart;

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public Cart saveCart(Cart cart) {
        this.cart = cart;
        return this.cart;
    }

    @Override
    public Cart getCart() {
        return this.cart;
    }

    @Override
    public Product getProduct(String productId) {
        for (Product p : getProducts()) {
            if (p.getId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Category> getCategories() {
        return this.categories;
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId) {
        if (categoryId < 0 || categoryId >= this.categories.size()) {
            return null;
        }
        return getProductsByCategory(this.categories.get(categoryId));
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return products.stream()
                .filter(product -> product.getCategory().satisfy(category))
                .collect(Collectors.toList());
    }

    private PosInMemoryDB() {
        Category categoryAll = new Category(0, "全部", category -> true);
        Category categoryElectronic = new Category(1, "智能电子设备");
        Category categoryFurniture = new Category(2, "家具/日用品");
        this.categories.add(categoryAll);
        this.categories.add(categoryElectronic);
        this.categories.add(categoryFurniture);

        this.products.add(new Product("PD1", "电熨斗", categoryFurniture, 199, "1.jpg"));
        this.products.add(new Product("PD2", "这是啥豆浆机吗", categoryFurniture, 499, "2.jpg"));
        this.products.add(new Product("PD3", "LV", categoryFurniture, 29499, "3.jpg"));
        this.products.add(new Product("PD4", "iPhone 114514", categoryElectronic, 9499, "4.jpg"));
        this.products.add(new Product("PD5", "Sofa", categoryFurniture, 999, "5.jpg"));
        this.products.add(new Product("PD6", "支架椅子", categoryFurniture, 249, "6.jpg"));
        this.products.add(new Product("PD7", "过年买的表", categoryElectronic, 949, "7.jpg"));
        this.products.add(new Product("PD8", "电视", categoryElectronic, 2949, "comp.png"));
    }



}
