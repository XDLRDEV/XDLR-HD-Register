package com.xdlr.temperaturerecord.controller;

import com.xdlr.temperaturerecord.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/huanda")
public class HomeController {

    @Autowired
    private UserController userController;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("instructions", "register");
        model.addAttribute("action", "register");
        return "register";
    }

    @GetMapping("/edit")
    public String edit(Model model) {
        model.addAttribute("instructions", "edit");
        model.addAttribute("action", "edit");
        return "register";
    }

    @PostMapping(path = "/user/register")
    public String registerNewUser(
            @RequestParam String idNo, @RequestParam String name, @RequestParam String phone,
            @RequestParam String company, @RequestParam String companyAddress,
            @RequestParam(required = false, defaultValue = "false") boolean toHubei,
            @RequestParam(required = false, defaultValue = "false") boolean toWuhan,
            @RequestParam(required = false, defaultValue = "false") boolean isFever,
            @RequestParam(required = false, defaultValue = "false") boolean isNewCoronavirus,
            Model model) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        String result = userController.registerNewUser(idNo, name, phone, company, companyAddress, toHubei, toWuhan, isFever, isNewCoronavirus);
        if (Constants.MSG_USER_EXISTS.equals(result)) {
            model.addAttribute("error", "身份证已经登记过了，不能重复登记");
            return "error";
        } else if (Constants.MSG_SAVED.equals(result)) {
            model.addAttribute("qrcodeMessage", idNo + " " + name);
            return "greeting";
        } else {
			model.addAttribute("error", "系统异常");
			return "error";
        }
    }

    @PostMapping(path = "/user/edit")
    public String editUser(
            @RequestParam String idNo, @RequestParam String name, @RequestParam String phone,
            @RequestParam String company, @RequestParam String companyAddress,
            @RequestParam(required = false, defaultValue = "false") boolean toHubei,
            @RequestParam(required = false, defaultValue = "false") boolean toWuhan,
            @RequestParam(required = false, defaultValue = "false") boolean isFever,
            @RequestParam(required = false, defaultValue = "false") boolean isNewCoronavirus,
            Model model) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        String result = userController.editUser(idNo, name, phone, company, companyAddress, toHubei, toWuhan, isFever, isNewCoronavirus);
        if (Constants.MSG_USER_NOT_EXISTS.equals(result)) {
            model.addAttribute("error", "身份证尚未登记，请先登记");
            return "error";
        } else if (Constants.MSG_SAVED.equals(result)) {
            model.addAttribute("qrcodeMessage", idNo + " " + name);
            return "greeting";
        } else {
            model.addAttribute("error", "系统异常");
            return "error";
        }
    }
}
