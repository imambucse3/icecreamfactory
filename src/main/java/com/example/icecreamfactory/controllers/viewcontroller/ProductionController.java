package com.example.icecreamfactory.controllers.viewcontroller;

import java.math.BigDecimal;
import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.icecreamfactory.entity.Product;
import com.example.icecreamfactory.entity.Production;
import com.example.icecreamfactory.repository.ProductRepository;
import com.example.icecreamfactory.repository.ProductionRepository;
import java.time.LocalDate;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProductionController {
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductionRepository productionRepository;
	@GetMapping("/fetchAllIngridient")
	@ResponseBody
	public List<Product> getAllproduct() {

	    List<Product> listOfIngridient = productRepository.findAll();

	    return listOfIngridient;
	}
	@PostMapping("/insertProduction")
	@ResponseBody
	public String insertProduction(@RequestBody Production production) {
	    // Save the production data
	    //productionRepository.save(production);
	    
	    
	 // Extract ingredient quantities from the string
	    String ingradientQuentityString = production.getIngradiant_quentity();
	    String[] ingredientPairs = ingradientQuentityString.split(", ");
	 // Update the amount of each ingredient
	    BigDecimal pCost = BigDecimal.ZERO; // Use BigDecimal.ZERO for initialization

	    for (String pair : ingredientPairs) {
	        String[] parts = pair.split(":");
	        Long ingredientId = Long.parseLong(parts[0]);
	        Integer usedQuantity = Integer.parseInt(parts[1]);
	        
	        java.util.Optional<Product> optionalIngredient = productRepository.findById(ingredientId);
	        if (optionalIngredient.isPresent()) {
	            Product product = optionalIngredient.get();
	            int currentQuantity = product.getQuantity();
	            
	            BigDecimal price = product.getPrice(); // Retrieve the price once
	            BigDecimal usedQuantityDecimal = BigDecimal.valueOf(usedQuantity); // Convert usedQuantity to BigDecimal
	            
	            pCost = pCost.add(price.multiply(usedQuantityDecimal)); // Multiply price by usedQuantity and add to pCost
	            
	            product.setQuantity(currentQuantity - usedQuantity); // Update product quantity
	            
	            productRepository.save(product); // Save product
	            
	            System.out.println("Price: " + price); // Debugging prints
	            System.out.println("Used Quantity: " + usedQuantityDecimal); // Debugging prints
	        }
	    }

	    production.setProduction_cost(pCost); // Set production cost
	    production.setProduction_date(LocalDate.now());
	    productionRepository.save(production); // Save production

	    // Update the amount of the product
	    Long productId = production.getName(); 
	    Long productionQuantity = production.getProduction_quantity(); 
	    
	    
	    java.util.Optional<Product> optionalProduct = productRepository.findById(productId);
	    if (optionalProduct.isPresent()) {
	        Product product = optionalProduct.get();
	        int currentAmount = product.getQuantity();
	        product.setQuantity((int) (currentAmount + productionQuantity));
	        productRepository.save(product);
	    }
	    
	    return "insert successful";
	}

}
