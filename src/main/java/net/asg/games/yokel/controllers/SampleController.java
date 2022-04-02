package net.asg.games.yokel.controllers;

import net.asg.games.yokel.objects.YokelPlayer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SampleController {

    @RequestMapping("testJPAHome")
    public String loadHomePage(Model m) {
        m.addAttribute("name", "CodeTutr");
        return "TestJPAForm";
    }

    @RequestMapping(value="/testJPA", method=RequestMethod.GET)
    public String handlePost(@RequestParam String action, Model m) {
        if( action.equals("save") ){
            //handle save
            System.out.println("You clicked Save!!!!!!!");
        }
        else if( action.equals("renew") ){
            //handle renew
            System.out.println("You clicked renew!!!!!!!");
        }
        m.addAttribute("name", "change");
        return "TestJPAForm";
    }

    /*
    @GetMapping("/greeting")
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
        model.addAttribute("greeting", greeting);
        return "result";
    }*/
}