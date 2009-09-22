package com.ics.tcg.web.database;

import java.util.List;

import com.ics.tcg.web.database.bean.Friends;

public interface FriendDAO {

	/**
	 * get all friends
	 * 
	 * @param userid
	 */
	public List<Friends> getFriendByID(int userid);

	/**
	 * delete one friend
	 * 
	 * @param userid
	 * @param friendid
	 */
	public void deleteFriend(int userid, int friendid);

	/**
	 * add one friend
	 * 
	 * @param friend
	 */
	public Friends saveFriend(Friends friend);

	/**
	 * check if one person is a friend
	 * 
	 * @param userid
	 * @param friendid
	 */
	public int isFriend(int userid, int friendid);

}
