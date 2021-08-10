package ru.yakunin.efrsbhtmlparser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yakunin.efrsbhtmlparser.entity.ArbitrManager;
import ru.yakunin.efrsbhtmlparser.parser.ArbitrManagerParser;
import ru.yakunin.efrsbhtmlparser.service.ArbitrManagerService;

import java.util.List;

@Controller
public class ArbitrManagerController {
    @Autowired
    private ArbitrManagerService arbitrManagerService;
    @Autowired
    private ArbitrManagerParser arbitrManagerParser;

    @GetMapping("/")
    public String arbitrManagers(
            @RequestParam(name = "sortedType", required = false, defaultValue = "") String sortedType,
            @RequestParam(name = "searchWord", required = false, defaultValue = "") String searchWord,
            Model model) {
        model.addAttribute("searchWord", searchWord);
        model.addAttribute("sortedType", sortedType);
        model.addAttribute("arbitrManagers", arbitrManagerService.getAll(sortedType, searchWord));
        return "arbitr-managers";
    }

    @GetMapping("/arbitr/manager/{id}")
    public String arbitrManagerInfo(@PathVariable("id") ArbitrManager arbitrManager, Model model) {
        model.addAttribute("mng", arbitrManager);
        return "arbitr-manager-info";
    }

    @GetMapping("/parseArbitrManagers")
        public String arbitrManagerI() {
            arbitrManagerParser.parserArbitrManagers(12300, 12315,
                    12315, 12330);
            return "Hello";
    }

}