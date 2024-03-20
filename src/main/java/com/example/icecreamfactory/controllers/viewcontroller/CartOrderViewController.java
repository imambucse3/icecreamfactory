package com.example.icecreamfactory.controllers.viewcontroller;

import com.example.icecreamfactory.dao.OrderRequest;
import com.example.icecreamfactory.entity.*;
import com.example.icecreamfactory.entity.util.CheckoutForm;
import com.example.icecreamfactory.repository.ProductRepository;
import com.example.icecreamfactory.repository.UserRepository;
import com.example.icecreamfactory.service.CartItemService;
import com.example.icecreamfactory.service.OrderService;
import com.example.icecreamfactory.service.ProductService;
import com.example.icecreamfactory.service.impl.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class CartOrderViewController {
    private final CartService cartService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    private final ProductService productService;

    public CartOrderViewController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }
    @Autowired
    private CartItemService cartItemService;

    // View product details and add to cart
    @GetMapping("/product/{id}")
    public String viewProductDetails(@PathVariable("id") Long productId, Model model) {
        Optional<Product> product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "product_details";
    }

    // Add item to cart
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, Principal principal,  RedirectAttributes redirectAttributes) {
        String email = principal.getName();
        Product product = productRepository.findById(productId).orElse(null);
        if(product != null && product.getQuantity() < quantity) {
            String message = "This product , " + product.getName() + " is not available";
            redirectAttributes.addFlashAttribute("notavailableProduct", message); // Adding the greetingMessage attribute for redirection
            return "redirect:/cart";
        }
        cartItemService.addItemToCart(productId, quantity, email);
        productService.decreaseProductQuantity(productId, quantity);
        return "redirect:/cart";
    }

    // Increase product Quantity in CartItem
    @PostMapping("/cart/increase")
    public String IncreaseCartItemQuantity(@RequestParam int quantity, @RequestParam int quantityOne, @RequestParam Long cartItemId,@RequestParam Long productId, Principal principal,  RedirectAttributes redirectAttributes) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        Product product = productRepository.findById(productId).orElse(null);

        CartItem cartItem = cartItemService.findByUserAndProduct(user, product);
        if(product != null && (product.getQuantity() < quantity || product.getQuantity()<quantityOne)) {
            String message = "This product Quanity " + product.getName() + " is not available";
            redirectAttributes.addFlashAttribute("notavailableProduct", message); // Adding the greetingMessage attribute for redirection
            return "redirect:/cart";
        }
        if (quantity>0){
            cartItemService.addItemToCart(productId, quantity, email);
        } else {
            cartItemService.addItemToCart(productId, quantityOne, email);
        }

        productService.decreaseProductQuantity(productId, quantity);
        return "redirect:/cart";
    }

    // Increase product Quantity in CartItem
    @PostMapping("/cart/decrease")
    public String decreaseCartItemQuantity(@RequestParam int quantity, @RequestParam int quantityOne, @RequestParam Long cartItemId,@RequestParam Long productId, Principal principal,  RedirectAttributes redirectAttributes) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        Product product = productRepository.findById(productId).orElse(null);

        CartItem cartItem = cartItemService.findByUserAndProduct(user, product);
//        if(product != null && (product.getQuantity() < quantity || product.getQuantity()<quantityOne)) {
//            String message = "This product Quanity " + product.getName() + " is not available";
//            redirectAttributes.addFlashAttribute("notavailableProduct", message); // Adding the greetingMessage attribute for redirection
//            return "redirect:/cart";
//        }
        if (quantity>0){
            cartItemService.minusItemToCart(productId, quantity, email);
        } else {
            cartItemService.minusItemToCart(productId, quantityOne, email);
        }

        productService.decreaseProductQuantity(productId, quantity);
        return "redirect:/cart";
    }

    // View cart
    @GetMapping("/cart")
    public String viewCart(Model model, Principal principal) {
        String userEmail = principal.getName();
        List<CartItem> cartItems = cartItemService.getCartItems(userEmail);

        // Calculate the total price of the cart
        BigDecimal totalPrice = cartItemService.calculateTotalPrice(cartItems);

        // Add the cart items and total price to the model
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    @GetMapping("/cart/remove")
    public String removeCartItem(@RequestParam(value = "cartItemId") Long cartItemId, Principal principal) {
        String username = principal.getName();
        // Remove the cart item from the user's cart
        cartItemService.increaseProductQuanity(cartItemId);
        cartItemService.removeCartItem(username, cartItemId);
        return "redirect:/cart"; // Redirect to the cart page after removing the item
    }

    @GetMapping("/checkout")
    public String showCheckoutForm(Model model, Principal principal) {
        String username = principal.getName();

        // Get the cart items associated with the user
        List<CartItem> cartItems = cartItemService.getCartItems(username);

        // Calculate the total price of the order
        BigDecimal totalPrice = cartItemService.calculateTotalPrice(cartItems);

        // Create a new CheckoutForm object and add it to the model
        CheckoutForm checkoutForm = new CheckoutForm();
        model.addAttribute("checkoutForm", checkoutForm);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);

        return "checkout"; // This will render the "checkout.html" Thymeleaf template
    }

    @PostMapping("/place/order")
    public String processCheckout(@ModelAttribute("checkoutForm") CheckoutForm checkoutForm, Principal principal) {
        String username = principal.getName();
        String shippingAddress = checkoutForm.getShippingAddress();
        String paymentMethod = checkoutForm.getPaymentMethod();
        BigDecimal totalPrice = checkoutForm.getTotalPrice();
        // Get the cart items associated with the user
        List<CartItem> cartItems = cartItemService.getCartItems(username);

        // Place the order
        orderService.placeOrder(username, cartItems, shippingAddress, paymentMethod, totalPrice );
        cartItemService.clearCart(username);
        return "redirect:/orders"; // Redirect to the orders page after placing the order
    }

    @GetMapping("/orders")
    public String showOrderList(Model model, Principal principal) {
        String username = principal.getName();
        // Get a list of orders for the current user
        List<Order> orders = orderService.getOrdersByUsername(username);
        model.addAttribute("orders", orders);

        return "orders"; // This will render the "order-list.html" Thymeleaf template
    }

    @GetMapping("/orders/all")
    public String showAllOrderList(Model model, Principal principal) {
        // Get a list of orders for the current user
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);

        return "allOrders"; // This will render the "order-list.html" Thymeleaf template
    }

    // Place order
//    @PostMapping("/order/place")
//    public String placeOrder(@ModelAttribute OrderRequest orderRequest) {
//        Long orderId = orderService.placeOrder(orderRequest);
//        return "redirect:/order/confirm/" + orderId;
//    }
//
//    // View order confirmation
//    @GetMapping("/order/confirm/{id}")
//    public String viewOrderConfirmation(@PathVariable("id") Long orderId, Model model) {
//        Order order = orderService.getOrderById(orderId);
//        model.addAttribute("order", order);
//        return "order_confirmation";
//    }
//
//    // Confirm order
//    @PostMapping("/order/confirm")
//    public String confirmOrder(@RequestParam Long orderId) {
//        orderService.confirmOrder(orderId);
//        return "redirect:/order/confirm/" + orderId;
//    }
}
