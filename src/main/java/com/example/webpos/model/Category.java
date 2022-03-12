package com.example.webpos.model;


import lombok.Data;
import java.util.function.Predicate;


/**
 * Author: Ma Yingshuo
 * Time: 2022/3/12 20:58
 *
 * Product Category class.
 */
@Data
public class Category {
    private int id;
    private String name;
    private Predicate<Category> judgeFunc;

    public Category(int id, String name, Predicate<Category> judgeFunc)
    {
        this.id = id;
        this.name = name;
        this.judgeFunc = judgeFunc;
    }

    public Category(int id, String name)
    {
        // Matches itself by default
        this(id, name, category -> name.equals(category.getName()));
    }

    /**
     * This function will judge if this category satisfies target category.
     * For example, if this is "Phone" category, it will satisfies target "Electronic Equipment" category;
     * if this is "Furniture" category, it will satisfies target "All" category.
     * This function will use target category's judgeFunc to judge this category.
     * @param target
     * @return
     */
    public boolean satisfy(Category target)
    {
        return target.judgeFunc.test(this);
    }

}
