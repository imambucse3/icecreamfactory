package com.example.icecreamfactory.controllers.viewcontroller;

import com.example.icecreamfactory.dao.ProductDto;
import com.example.icecreamfactory.entity.Category;
import com.example.icecreamfactory.entity.Product;
import com.example.icecreamfactory.repository.CategoryRepository;
import com.example.icecreamfactory.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProductViewController {
    private ProductService productService;
    @Autowired
    private CategoryRepository categoryRepository;

    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/list")
    public String getProducts(Model model){
        List<ProductDto> products = productService.getAllSalesProduct();
        model.addAttribute("products", products);
        return "productList";
    }

    @GetMapping("/products/purchase/list")
    public String getProductPurchaseLists(Model model){
        List<ProductDto> products = productService.getAllPurchaseProduct();
        model.addAttribute("products", products);
        return "purchaseProductList";
    }
    @GetMapping("/products/purchase/production")
    public String production(Model model){
        
    	List<ProductDto> products = productService.getAllPurchaseProduct();
        //model.addAttribute("products", products);
        return "production";
    }

    @DeleteMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(id);

            redirectAttributes.addFlashAttribute("message", "The Product with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/product/list";
    }

    @GetMapping("/products/form")
    public String showProductForm(Model model){
        // create model object to store form data
        Product product = new Product();
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "addProduct";
    }

    @GetMapping("/products/purchase/form")
    public String showPurchaseProductForm(Model model){
        // create model object to store form data
        Product product = new Product();
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "addPurchaseProduct";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/products/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
                               BindingResult result,
                               Model model){
//        Product existingproduct = userService.findUserByEmail(userDto.getEmail());
//
//        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
//            result.rejectValue("email", null,
//                    "There is already an account registered with the same email");
//        }
//
//        if(result.hasErrors()){
//            model.addAttribute("user", userDto);
//            return "/register";
//        }

        productService.saveProduct(product);
        return "redirect:/products/form?success";
    }

    @PostMapping("/products/purchase/save")
    public String savePurchaseProduct(@Valid @ModelAttribute("product") Product product,
                              BindingResult result,
                              Model model){
//        Product existingproduct = userService.findUserByEmail(userDto.getEmail());
//
//        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
//            result.rejectValue("email", null,
//                    "There is already an account registered with the same email");
//        }
//
//        if(result.hasErrors()){
//            model.addAttribute("user", userDto);
//            return "/register";
//        }

        productService.savePurchaseProduct(product);
        return "redirect:/products/purchase/form?success";
    }

    @GetMapping("/edit/product/{id}")
    public String editProductForm(@PathVariable("id") Long id, Model model){
        // create model object to store form data
        ProductDto productDto = productService.findByProduct(id);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("product", productDto);
        model.addAttribute("categories", categories);
        return "editProduct";
    }

    @GetMapping("/edit/purchase/product/{id}")
    public String editPurchaseProductForm(@PathVariable("id") Long id, Model model){
        // create model object to store form data
        ProductDto productDto = productService.findByProduct(id);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("product", productDto);
        model.addAttribute("categories", categories);
        return "editPurchaseProduct";
    }

    @PostMapping ("/update/product/{id}")
    public String updateProduct(@PathVariable("id") Long id, @Valid @ModelAttribute("product") Product product,
                             BindingResult result, Model model) {
        ProductDto existingProduct = productService.findByProduct(id);
        if (existingProduct != null) {
            Product product1 = productService.updateProduct(product, id);
            return "redirect:/products/list?success";
        }
        return "redirect:/products/list?danger";
    }

    @PostMapping ("/update/purchase/product/{id}")
    public String updatePurchaseProduct(@PathVariable("id") Long id, @Valid @ModelAttribute("product") Product product,
                             BindingResult result, Model model) {
        ProductDto existingProduct = productService.findByProduct(id);
        if (existingProduct != null) {
            Product product1 = productService.updateProduct(product, id);
            return "redirect:/products/purchase/list?success";
        }
        return "redirect:/products/purchase/list?danger";
    }

    @GetMapping("/products/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        List<Product> searchResults = productService.getProductsContainingName(keyword);
        model.addAttribute("products", searchResults);
        return "productList";
    }
}
