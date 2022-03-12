package com.example.webpos.web;

import com.example.webpos.biz.PosService;
import com.example.webpos.model.Category;
import com.example.webpos.model.Item;
import com.example.webpos.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PosController {

    private PosService posService;
    private int currentCategoryId = 0;

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @GetMapping("/")
    public String pos(Model model) {
        //posService.add("PD1",2);
        setDefaultModelAttributes(model);
        return "index";
    }

    @GetMapping("/{category}")
    public String posCategory(@PathVariable("category") int categoryId, Model model) {
        if (categoryId < 0 || categoryId >= posService.categories().size()) {
            throw new IllegalArgumentException("Invalid category id!");
        }
        this.currentCategoryId = categoryId;
        setDefaultModelAttributes(model);
        return "index";
    }



    @RequestMapping("/add")
    public String addProduct(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "amount") Integer amount,
            Model model
    ) {
        Product product = posService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        posService.add(product, amount);
        setDefaultModelAttributes(model);
        return "index";
    }

    @RequestMapping("/remove")
    public String removeProduct(
            @RequestParam(value = "id") String id,
            Model model
    ) {
        Product product = posService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        posService.remove(product);
        setDefaultModelAttributes(model);
        return "index";
    }

    @RequestMapping("/clear")
    public String clearCart(Model model) {
        posService.clearCart();
        setDefaultModelAttributes(model);
        return "index";
    }


    private void setDefaultModelAttributes(Model model) {
        model.addAttribute("currentCategoryId", currentCategoryId);
        model.addAttribute("categories", posService.categories());
        model.addAttribute("products", posService.productsByCategoryId(currentCategoryId));
        model.addAttribute("cart", posService.getCart());

        model.addAttribute("total", posService.total(posService.getCart()));
    }



}
