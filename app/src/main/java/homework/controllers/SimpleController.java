package homework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SimpleController {
    @GetMapping("/sample")
    public String showForm(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "sample";
    }
}
