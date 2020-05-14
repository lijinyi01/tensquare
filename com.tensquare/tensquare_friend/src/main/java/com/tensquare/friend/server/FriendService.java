package com.tensquare.friend.server;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class FriendService {

     @Autowired
    public FriendDao friendDao;
    @Autowired
    public NoFriendDao noFriendDao;

    public int addFriend(String userid, String friendid) {
        //userid 里friendID 是否有数据 有重复0
        Friend friend = friendDao.findByUseridAndFriendid(userid,friendid);
        if(friend!=null){
            return 0;
        }

        // 直接添加好友userid-friedid type=0
               friend=new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        System.out.println(userid+"userid");
        System.out.println(friendid+"friend");
        friendDao.save(friend);
        //friend-useid有数据把type都改1

        if(friendDao.findByUseridAndFriendid(friendid,userid)!=null){
                friendDao.updateIslike("1",userid,friendid);
            friendDao.updateIslike("1",friendid,userid);
        }
   return 1;
    }

    public int addNoFriend(String userid, String friendid) {
       // System.out.println("fangfajinlai");
        NoFriend nofriend = noFriendDao.findByUseridAndFriendid(userid,friendid);
        System.out.println(nofriend);
        if(nofriend!=null){
            return 0;
        }
        nofriend=new NoFriend();
        nofriend.setUserid(userid);
        nofriend.setFriendid(friendid);
      noFriendDao.save(nofriend);
      //  System.out.println(save+"SAVE");
        System.out.println(userid+"service");
        System.out.println(friendid+"service");
        return 1;

    }

    public void deleteFriendid(String userid, String friendid) {
        //删除好从userid到friend 记录
        friendDao.deleteFriend(userid,friendid);


        //更新friend 到 userid islike=0
        friendDao.updateIslike("0",friendid,userid);

        //非好友中添加数据
        NoFriend noFriend=new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);

        noFriendDao.save(noFriend);

    }
}
