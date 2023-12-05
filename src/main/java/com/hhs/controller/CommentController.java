package com.hhs.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hhs.constant.CodeType;
import com.hhs.entity.Comment;
import com.hhs.entity.User;
import com.hhs.service.CommentService;
import com.hhs.service.MovieService;
import com.hhs.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author ：何汉叁
 * @date ：2023/11/28 23:17
 * @description：评论管理模块
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;
    @Resource
    private UserService userService;
    @Resource
    private MovieService movieService;
    //用户：修改、增加评论
    //管理员：删除评论、修改评论
    //查询用户评论
    @RequestMapping("/findAllComments")
    public JSONObject findAllComments() {
        JSONObject obj = new JSONObject();
        List<Comment> list = commentService.findAllComments();
        for(Comment comment: list) {
            comment.setComment_user(userService.selectByPrimaryKey(comment.getUser_id()));
        }
        obj.put("code", 0);
        obj.put("msg", "");
        obj.put("count", list.size());
        obj.put("data", list);
        return obj;
    }
    @RequestMapping("/findAllCommentsPage")
    @ResponseBody
    public JSONObject findAllCommentsPage(@RequestParam(value="page",defaultValue="1")Integer page,@RequestParam(value="limit",defaultValue="10")Integer limit,String keyword) {
        PageInfo<Comment> info = commentService.findAllCommentsBySplitPage(page, limit, keyword);
        //System.out.println(info);
        for(Comment comment : info.getList()) {
            comment.setComment_user(userService.selectByPrimaryKey(comment.getUser_id()));
        }
        JSONObject obj = new JSONObject();
        obj.put("code", 0);
        obj.put("msg", "");
        obj.put("count", info.getTotal());
        obj.put("data", info.getList());
        return obj;
    }

    @RequestMapping("/addCommentByUser")
    public JSONObject addCommentByUser(@RequestParam("movie_id") long movie_id, @RequestParam("comment_content") String comment_content, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        JSONObject obj = new JSONObject();
        if(user == null) {
            obj.put("code", 200);
            obj.put("msg", "您未登录， 登录后才可评论的~");
        }else  {
            Comment comment = new Comment();
            comment.setUser_id(user.getUser_id());
            comment.setComment_content(comment_content);
            comment.setComment_time(new Date());
            comment.setMovie_id(movie_id);
            Integer res = commentService.insertSelective(comment);
            if(res > 0) {
                Integer res2 = movieService.addMovieCommentCount(movie_id);
                if (res2 > 0) {
                    obj.put("code", 0);
                    obj.put("msg", "评论成功~");
                }else {
                    obj.put("code", 200);
                    obj.put("msg", "评论失败2~");
                }
            }else  {
                obj.put("code", 200);
                obj.put("msg", "评论失败~");
            }
        }
        return obj;
    }
    @RequestMapping("/updateComment")
    public JSONObject updateComment(@RequestParam("comment_id") long comment_id, @RequestParam("comment_content") String comment_content) {
        JSONObject obj = new JSONObject();
        Comment comment = new Comment();
        comment.setComment_id(comment_id);
        comment.setComment_content(comment_content);
        Integer res = commentService.updateByPrimaryKeySelective(comment);
        if(res > 0) {
            obj.put("code", 0);
            obj.put("msg", "修改成功~");
        }else {
            obj.put("code", 200);
            obj.put("msg", "修改失败~");
        }
        return obj;
    }

    @RequestMapping("/deleteComment")
    public JSONObject deleteComment(@RequestParam("comment_id")long comment_id) {
        JSONObject obj = new JSONObject();
        Integer rs2 = movieService.deleteMovieCommentCount(commentService.selectByPrimaryKey(comment_id).getMovie_id());
        Integer rs = commentService.deleteByPrimaryKey(comment_id);
        if(rs > 0) {
            obj.put("code", 0);
            obj.put("msg", "删除成功~");
        }else {
            obj.put("code", 200);
            obj.put("msg", "删除失败~");
        }
        return obj;
    }

    @RequestMapping("/findCommentByUserName")
    public JSONObject findCommentByUserName(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit",defaultValue = "10") int limit,@RequestParam("user_name") String user_name) {
        PageInfo<Comment> info = commentService.findCommentByUserName(page, limit, user_name);
        for(Comment comment: info.getList()) {
            comment.setComment_user(userService.selectByPrimaryKey(comment.getUser_id()));
        }
        JSONObject obj = new JSONObject();
        obj.put("code", 0);
        obj.put("msg", "");
        obj.put("count", info.getTotal());
        obj.put("data", info.getList());
        return obj;
    }
}
