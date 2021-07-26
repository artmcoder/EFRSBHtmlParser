package ru.yakunin.efrsbhtmlparser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yakunin.efrsbhtmlparser.entity.ArbitrManager;
import ru.yakunin.efrsbhtmlparser.entity.MessageTorgi;
import ru.yakunin.efrsbhtmlparser.entity.MessageTorgiDetails;
import ru.yakunin.efrsbhtmlparser.service.MessageTorgiService;

@Controller
@RequestMapping("/messages")
public class MessageTorgiController {
    @Autowired
    private MessageTorgiService messageTorgiService;

    @GetMapping
    public String messages(
            @RequestParam(required = false, defaultValue = "", name = "searchByLotDesWord")
                    String searchByLotDesWord,
            @RequestParam(required = false, defaultValue = "", name = "town") String town,
            @RequestParam(name = "region", required = false, defaultValue = "") String region,
            Model model) {
        model.addAttribute("town", town);
        model.addAttribute("region", region);
        model.addAttribute("searchByLotDesWord", searchByLotDesWord);
        model.addAttribute("messages", messageTorgiService
                .getAllMessageTorgiDetails(searchByLotDesWord, town, region));
        model.addAttribute("towns", ControllerUtils.getAllTowns());
        model.addAttribute("regions", ControllerUtils.getAllRegions());
        return "messages";
    }

    @GetMapping("/{id}")
    public String messageInfo(@PathVariable("id") MessageTorgiDetails messageTorgiDetails,
                              Model model) {
        model.addAttribute("msgTr", messageTorgiDetails.getMessageTorgi());
        model.addAttribute("msgTrDets", messageTorgiDetails.getMessageTorgi().getMessageTorgiDetails());
        model.addAttribute("mngId", messageTorgiDetails.getMessageTorgi().getArbitrManager().getId());
        return "message-info";
    }

    @GetMapping("/arbitr/manager/{id}")
    public String arbitrManagerMessages(@PathVariable("id") ArbitrManager arbitrManager,
                                        Model model) {
//        model.addAttribute("messages", messageTorgiService.getMessagesByArbitrManager(arbitrManager));
        return "messages";
    }

}
