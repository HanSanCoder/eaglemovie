package com.hhs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.print.DocFlavor;

/**
 * @author ：何汉叁
 * @date ：2023/11/24 15:50
 * @description：TODO
 */
@Controller
public class PageController {
    @RequestMapping("/buySeat")
    public String buySeat() {
        return "buySeat";
    }

    @RequestMapping("/selectSeat")
    public String selectSeat() {
        return "selectSeat";
    }

    @RequestMapping("/buyTicket")
    public String buyTicket() {
        return "buyTicket";
    }

    @RequestMapping("/center")
    public String center() {
        return "center";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/mainPage")
    public String mainPage() {
        return "mainPage";
    }

    @RequestMapping("/manage")
    public String manage() {
        return "manage";
    }

    @RequestMapping("/movieList")
    public String movieList() {
        return "movieList";
    }

    @RequestMapping("/movieDetail")
    public String movieDetail() {
        return "movieDetail";
    }

    @RequestMapping("/pay")
    public String pay() {
        return "pay";
    }

    @RequestMapping("/payStatus")
    public String payStatus() {
        return "payStatus";
    }
}
