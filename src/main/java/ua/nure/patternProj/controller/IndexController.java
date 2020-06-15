package ua.nure.patternProj.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @RequestMapping({"/", "/index"})
    public String home(Model model, HttpServletRequest request){

        return "index";
    }
}
