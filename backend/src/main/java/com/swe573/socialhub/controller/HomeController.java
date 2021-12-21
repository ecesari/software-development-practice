//package com.swe573.socialhub.controller;
//
//
//import org.springframework.web.bind.annotation.RequestMapping;
//
//public class HomeController {
//
//    // Login form
//    @RequestMapping("/login.html")
//    public String login() {
//        return "login.html";
//    }
//
//    // Login form with error
//    @RequestMapping("/login-error.html")
//    public String loginError(Model model) {
//        model.addAttribute("loginError", true);
//        return "login.html";
//    }
////
////    @Autowired
////    private UserService userService;
////
////    @PostMapping("/authenticate")
////    public AuthResponse createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) throws Exception {
////        return userService.createAuthenticationToken(authenticationRequest);
////    }
//}
