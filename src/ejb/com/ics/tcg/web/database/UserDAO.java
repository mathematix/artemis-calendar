package com.ics.tcg.web.database;

import java.util.List;

import com.ics.tcg.web.database.bean.User;

public interface UserDAO {
	/**
	 * insert a user
	 * 
	 * @param user
	 */
	public void insertUser(User user);

	/**
	 * update the user info
	 * 
	 * @param user
	 */
	public void mergeUser(User user);

	/**
	 * delete the user
	 * 
	 * @param userid
	 */
	public void deleteUser(int userid);

	/**
	 * get a user by his id
	 * 
	 * @param userid
	 */
	public User getUserByID(int userid);

	/**
	 * get all user
	 * 
	 * @param userid
	 */
	public List<User> getUserList();

	/**
	 * check if the account is authenticated
	 * 
	 * @param account
	 * @param password
	 */
	public int ValidateUser(String account, String password);

	/**
	 * get a user's id by his account
	 * 
	 * @param account
	 */
	public int getIDByAccount(String account);

	/**
	 * get a user by his account
	 * 
	 * @param userid
	 */
	public User getUserByAccount(String account);
}
