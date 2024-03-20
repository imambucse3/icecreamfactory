package com.example.icecreamfactory.controllers.viewcontroller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.icecreamfactory.entity.Product;
import com.example.icecreamfactory.entity.Production;
import com.example.icecreamfactory.entity.ProductionReport;
import com.example.icecreamfactory.repository.ProductRepository;
import com.example.icecreamfactory.repository.ProductionRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReportController {
	@Autowired
	ProductionRepository productionRepository;
	@Autowired
	ProductRepository productRepository;
	@GetMapping("/products/seals/report")
	public String report() 
	{ 
		return "report";
	}
	@GetMapping("/getReport")
	@ResponseBody
	public List<ProductionReport> getAllProduction(Model m, HttpSession session) {
	    List<Production> listOfProduction = productionRepository.findAll();
	    List<ProductionReport> reportList = new ArrayList<>();

	    for (Production production : listOfProduction) {
	        // Fetch the product from the Product table using its ID
	        Long productId = production.getName(); // Assuming this method exists in Production entity
	        java.util.Optional<Product> optionalProduct = productRepository.findById(productId);
	        
	        if (optionalProduct.isPresent()) {
	            Product product = optionalProduct.get();
	            BigDecimal sellingPrice = product.getPrice(); // Assuming this method exists in Product entity
	            
	            Long pq = production.getProduction_quantity();
	            BigDecimal totalCost = production.getProduction_cost();
	            BigDecimal costPerProduct = totalCost.divide(BigDecimal.valueOf(pq), 2, RoundingMode.HALF_UP);

	            ProductionReport report = new ProductionReport();
	            report.setProduct(product.getName());
	            report.setProduction_quantity(production.getProduction_quantity());
	            report.setProduction_cost(production.getProduction_cost());
	            report.setSelling_price(sellingPrice);
	            report.setCost_per_product(costPerProduct);
	            report.setProfite(sellingPrice.subtract(costPerProduct));
	            
	            report.setProduction_date(production.getProduction_date());

	            reportList.add(report);
	        }
	    }

	    return reportList;
	}


	
}
