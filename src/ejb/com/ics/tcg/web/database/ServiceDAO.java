package com.ics.tcg.web.database;

import java.util.List;

import com.ics.tcg.web.database.bean.AbstractService;
import com.ics.tcg.web.database.bean.Service;
import com.ics.tcg.web.database.bean.User_Service;

public interface ServiceDAO {

	/**
	 * get all abstract service information
	 */
	public List<AbstractService> getServices();

	/**
	 * add one abstract service to user's service list
	 * 
	 * @param user_Service
	 */
	public Integer SaveUserAbService(User_Service user_Service);

	/**
	 * delete one abstract service from user's list
	 * 
	 * @param userid
	 * @param asid
	 */
	public void DeleteUserAbService(int userid, int asid);

	/**
	 * get all abstract service of the user
	 * 
	 * @param userid
	 */
	public List<User_Service> getUserServices(int userid);

	/**
	 * get one abstract service information by it's id
	 * 
	 * @param asid
	 */
	public AbstractService getServiceById(int asid);

	/**
	 * update the abstract service of user
	 * 
	 * @param user_Service
	 */
	public void UpdateUserAbservice(User_Service user_Service);

	/**
	 * get one real service by its id
	 * 
	 * @param sid
	 */
	public Service getRealService(Integer sid);

	/**
	 * check if the abstract service is in the user's list
	 * 
	 * @param record
	 */
	public Integer ifService(Integer userid, Integer abserviceid);
}
