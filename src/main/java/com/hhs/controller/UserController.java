package com.hhs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hhs.constant.CodeType;
import com.hhs.entity.User;
import com.hhs.service.UserService;
import com.hhs.utils.UUIDUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * @author ：何汉叁
 * @date ：2023/11/24 13:37
 * @description：用户管理控制层
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/register")
    @ResponseBody
    public String register(User user) {
        List<User> userList = userService.selectByUserName(user.getUser_name());
        if(userList.size() > 0) {
            return "fail";
        }else {
            Integer res = userService.insertSelective(user);
            if(res > 0) {
                return "success";
            }else {
                return "fail";
            }
        }

    }

    @RequestMapping("/login")
    @ResponseBody
    public JSONObject login(String user_name,String user_pwd, HttpServletRequest request) {
        JSONObject job = new JSONObject();
        User userTemp = userService.selectByUserPwd(user_name, user_pwd);
        if(userTemp != null) {
            //将user信息放入到session中去
            HttpSession session = request.getSession();
            session.setAttribute("user", userTemp);
            if (userTemp.getUser_role() == 0) {
                job.put("code", 0);
                job.put("msg", "usersuccess");
                job.put("data", userTemp);
                return job;
            }else {
                job.put("code", 0);
                job.put("msg", "adminsuccess");
                job.put("data", userTemp);
                return job;
            }
        }
        job.put("msg", "fail");
        return job;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
       HttpSession session = request.getSession();
       session.removeAttribute("user");
       return "index";
   }

    @RequestMapping("/updateUser")
    @ResponseBody
    public String updateUser(User user) {
        Integer res = userService.updateByPrimaryKeySelective(user);
        if(res > 0) {
            return "success";
        }else {
            return "fail";
        }
    }

    @RequestMapping("/modifyUserPwd")
    @ResponseBody
    public String modifyUserPwd(@RequestParam("oldPwd") String oldPwd, @RequestParam("newPwd") String newPwd, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if(user.getUser_pwd().equals(oldPwd)) {
            user.setUser_pwd(newPwd);
            userService.insertSelective(user);
            return "success";
        }else {
            return "fail";
        }
    }

    @RequestMapping("/findAllUserInfos")
    @ResponseBody
    public JSONObject findAllUser(@RequestParam(value = "page", defaultValue = "1")Integer page, @RequestParam(value = "limit",defaultValue = "10")Integer limit, String keyword) {
        PageInfo<User> info = userService.findUserBySplitPage(page, limit, keyword);
        JSONObject obj = new JSONObject();
        obj.put("code", 0);
        obj.put("msg", "");
        obj.put("count", info.getSize());
        obj.put("data", info);
        return obj;
    }

    @RequestMapping("/findAllUser")
    @ResponseBody
    public JSONObject findAllUserInfos() {
        JSONObject obj = new JSONObject();
        List<User> list = userService.findAllUser();
        obj.put("code", 0);
        obj.put("msg", "");
        obj.put("count", list.size());
        obj.put("data", list);
        return obj;
    }

    @RequestMapping("/findUserInfosByName")
    @ResponseBody
    public JSONObject findUserInfosByName(@RequestParam("user_name")String user_name) {
        JSONObject obj = new JSONObject();
        List<User> list = userService.selectByUserName(user_name);
        obj.put("msg","");
        obj.put("code",0);
        obj.put("count",list.size());
        obj.put("data", list);
        return obj;
    }

    @RequestMapping("/uploadHeadImg")
    @ResponseBody
    public JSONObject uploadHeadImg(@RequestParam(value = "file", required = false)MultipartFile file, User user, HttpServletRequest request) throws IOException {
        JSONObject obj = new JSONObject();
        if(file != null) {
            String str = file.getOriginalFilename();
            System.out.println("file:" + str);
            String name = UUIDUtil.getUUID() + str.substring(str.lastIndexOf("."));
            System.out.println("name:" + name);
            String path = request.getServletContext().getRealPath("/upload/head") + "/" + name;
            System.out.println("path:" + path);
            String filePath = "../upload/head/" + name;
            user.setUser_headImg(filePath);
            file.transferTo(new File(path));
            System.out.println("文件写入成功,Path:" + path);
        }else {
            user.setUser_headImg(this.userService.selectByPrimaryKey(user.getUser_id()).getUser_headImg());
        }
        int res = userService.updateByPrimaryKeySelective(user);
        if(res > 0) {
            obj.put("code", 0);
            obj.put("msg", "");
            obj.put("data", user);
        }else {
            obj.put("code", 200);
            obj.put("msg", "");
        }
        return obj;
    }
}
