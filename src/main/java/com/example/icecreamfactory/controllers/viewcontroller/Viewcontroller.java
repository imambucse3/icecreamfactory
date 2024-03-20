package com.example.icecreamfactory.controllers.viewcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Viewcontroller {
    @RequestMapping("/getallEmployee")
    public String getEmployee(){
        return "employeeTwo";
    }

    @RequestMapping("/product")
    public String getProduct() { return "product"; }

    @RequestMapping("/category")
    public String getCategory() { return "category"; }

}
