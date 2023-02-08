package com.ndptest.tool;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @RequestMapping(value="/main", method={RequestMethod.GET, RequestMethod.POST})
    public String main(Model model){

        return "tool_main";
    }
}
