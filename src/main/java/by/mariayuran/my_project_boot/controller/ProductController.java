package by.mariayuran.my_project_boot.controller;

import by.mariayuran.my_project_boot.dto.ProductDto;
import by.mariayuran.my_project_boot.model.User;
import by.mariayuran.my_project_boot.service.ProductService;
import by.mariayuran.my_project_boot.service.SessionObjectHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final SessionObjectHolder objectHolder;

    public ProductController(ProductService productService, SessionObjectHolder objectHolder) {
        this.productService = productService;
        this.objectHolder = objectHolder;
    }

    @GetMapping
    public String getProducts(Model model) {
        List<ProductDto> productList = productService.getAll();
        model.addAttribute("products", productList);
        return "products";
    }

    @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable Integer id, Principal principal) {
        objectHolder.addClick();
        if (principal == null) {
            return "redirect:/products";
        }
        productService.addToUserBucket(id, principal.getName());
        return "redirect:/products";
    }
}
