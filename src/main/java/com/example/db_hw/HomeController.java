package com.example.db_hw;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    List<String> persons = new ArrayList();


    @GetMapping("/")
    public String home(Model model){
        if(persons.isEmpty()){
            MyDB myDB = new MyDB(GetConnection.ipAddress, GetConnection.port, GetConnection.pass);
            persons = myDB.getPersons();
        }
        model.addAttribute("persons", persons);
        return "index";
    }
}
