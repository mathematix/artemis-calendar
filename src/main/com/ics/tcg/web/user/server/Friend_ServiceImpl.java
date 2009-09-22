package com.ics.tcg.web.user.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ics.tcg.web.database.FriendDAO;
import com.ics.tcg.web.database.UserDAO;
import com.ics.tcg.web.database.bean.Friends;
import com.ics.tcg.web.database.bean.User;
import com.ics.tcg.web.user.client.db.Friends_Client;
import com.ics.tcg.web.user.client.db.User_Client;
import com.ics.tcg.web.user.client.remote.Friend_Service;

@SuppressWarnings("serial")
public class Friend_ServiceImpl extends RemoteServiceServlet implements
		Friend_Service {

	InitialContext ctx_friend;
	InitialContext ctx_user;
	Properties props;
	FriendDAO frienddao;
	UserDAO userdao;

	public Friend_ServiceImpl() {
		props = new Properties();
		props.setProperty("java.naming.factory.initial",
				"org.jnp.interfaces.NamingContextFactory");
		props.setProperty("java.naming.provider.url", "localhost:1099");
		props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
		try {
			ctx_friend = new InitialContext(props);
			ctx_user = new InitialContext(props);
			frienddao = (FriendDAO) ctx_friend.lookup("FriendDAOBean/remote");
			userdao = (UserDAO) ctx_user.lookup("UserDAOBean/remote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Friends_Client> getAllFriends(int userid) {
		List<Friends> friends;
		friends = frienddao.getFriendByID(userid);
		ArrayList<Friends_Client> users = new ArrayList<Friends_Client>();
		if (friends != null) {
			for (int i = 0; i < friends.size(); i++) {
				User user = userdao.getUserByID(friends.get(i).getFriendid());
				Friends_Client temp = new Friends_Client();
				temp.setId(friends.get(i).getId());
				temp.setUserid(userid);
				temp.setFriendid(friends.get(i).getFriendid());
				temp.setValue(friends.get(i).getValue());
				temp.setFriendname(user.getAccount());
				users.add(temp);
			}
			return users;
		} else {
			return null;
		}

	}

	// delete
	@Override
	public String deleteFriend(int userid, int friendid) {
		frienddao.deleteFriend(userid, friendid);
		return "true";
	}

	// add
	@Override
	public Friends_Client saveFriend(int userid, String account) {
		int friendid = userdao.getIDByAccount(account);
		if (friendid == -1) {
			return null;
		} else {
			Friends friends = new Friends();
			friends.setUserid(userid);
			friends.setFriendid(friendid);
			friends.setValue(0.8);
			friends = frienddao.saveFriend(friends);
			Friends_Client friendsClient = new Friends_Client();
			friendsClient.setId(friends.getId());
			friendsClient.setUserid(friends.getUserid());
			friendsClient.setFriendid(friends.getFriendid());
			friendsClient.setValue(friends.getValue());
			friendsClient.setFriendname(userdao.getUserByID(
					friends.getFriendid()).getAccount());
			return friendsClient;
			// if (frienddao.isFriend(userid, friendid) == 0) {
			// Friends friends = new Friends();
			// friends.setUserid(userid);
			// friends.setFriendid(friendid);
			// System.out.println(friends.getUserid()+friends.getFriendid());
			// frienddao.addFriend(friends);
			// return friendid;
			// } else
			// return -1;
		}
	}

	// get a friend's info
	@Override
	public User_Client getFriendInfo(String account) {
		User user = userdao.getUserByAccount(account);
		if (user != null) {
			User_Client user_client = new User_Client();
			user_client.setAccount(user.getAccount());
			user_client.setAge(user.getAge());
			user_client.setEmail(user.getEmail());
			user_client.setTel(user.getTel());
			user_client.setUserid(user.getUserid());
			return user_client;
		} else {
			return null;
		}
	}

	@Override
	public Integer check(Integer userid, Integer id) {
		return frienddao.isFriend(userid, id);
	}

}
