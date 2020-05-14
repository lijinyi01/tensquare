package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.server.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.bouncycastle.math.ec.rfc7748.X25519;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserClient userClient;

    @RequestMapping(value = "/like/{friendid}/{type}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid,@PathVariable String type){
        //验证是否登录 和当前用户id
        Claims claims = (Claims) request.getAttribute("claims_user");
        System.out.println(claims);
        System.out.println(friendid);
        System.out.println(type);
        if(claims==null){

         return  new Result(false,StatusCode.LOGINERROR,"权限不足");
        }
        String userid = claims.getId();
        System.out.println(userid);
        if(type!=null){
            if(type.equals("1")){
                //   添加好友
              int flag=  friendService.addFriend(userid,friendid);
              if(flag==0){
                  return new Result(false, StatusCode.ERROR,"重复添加");
              }
               if(flag==1){
                   userClient.updatefanscountandfollowcount(userid,friendid,1);
                   return new Result(false, StatusCode.ERROR,"添加成功");
                }

            }else if(type.equals("2")){
                System.out.println("controller"+type);
               // 添加非好友
               int flag= friendService.addNoFriend(userid,friendid);
                System.out.println(flag+"HAHAHAHA");
                System.out.println(flag+"flag");
                if(flag==0){
                    return new Result(false, StatusCode.ERROR,"不能重复添加非好友");
                }
                if(flag==1){
                    return new Result(false, StatusCode.ERROR,"添加成功");
                }
            }
            return new Result(false, StatusCode.ERROR,"参数异常");
        }else {
            return new Result(false, StatusCode.ERROR,"参数异常");
        }

    }

    @RequestMapping(value = "/{friendid}")
    public Result deleteFriend(@PathVariable String friendid){

        Claims claims = (Claims) request.getAttribute("claims_user");
        System.out.println(claims);
        System.out.println(friendid);
       // System.out.println(type);
        if(claims==null){

            return  new Result(false,StatusCode.LOGINERROR,"权限不足");
        }
        String userid = claims.getId();
        friendService.deleteFriendid(userid,friendid);
        userClient.updatefanscountandfollowcount(userid,friendid,-1);

        return  new Result(true,StatusCode.LOGINERROR,"删除成功");
    }


}
