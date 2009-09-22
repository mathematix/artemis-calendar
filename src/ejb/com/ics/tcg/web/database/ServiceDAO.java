package com.ics.tcg.web.database;

import java.util.List;

import com.ics.tcg.web.database.bean.AbstractService;
import com.ics.tcg.web.database.bean.Service;
import com.ics.tcg.web.database.bean.User_Service;

public interface ServiceDAO {
	public List<AbstractService> getServices();

	public Integer SaveUserAbService(User_Service user_Service);

	public void DeleteUserAbService(int userid, int asid);

	public List<User_Service> getUserServices(int userid);

	public AbstractService getServiceById(int asid);

	// update
	public void UpdateUserAbservice(User_Service user_Service);

	// get service
	public Service getRealService(Integer sid);

	// judge if is in the list
	public Integer ifService(Integer userid, Integer abserviceid);
}
