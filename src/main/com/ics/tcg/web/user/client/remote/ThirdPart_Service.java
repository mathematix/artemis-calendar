package com.ics.tcg.web.user.client.remote;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.ics.tcg.web.user.client.db.Issuer_Client;
import com.ics.tcg.web.user.client.db.ThirdPart_Client;
import com.ics.tcg.web.user.client.db.User_Issuer_Client;
import com.ics.tcg.web.user.client.db.User_ThirdPart_Client;

@RemoteServiceRelativePath("thirdpart")
public interface ThirdPart_Service extends RemoteService {

	/**
	 * get thirdpart by id
	 * 
	 * @param tpid
	 */
	public ThirdPart_Client getThirdPartByID(Integer tpid);

	/**
	 * get issuer by id
	 * 
	 * @param issuerid
	 */
	public Issuer_Client getIssuerByID(Integer issuerid);

	/**
	 * get all thridparts
	 */
	public List<ThirdPart_Client> getTPs();

	/**
	 * get all issuers
	 */
	public List<Issuer_Client> getIssuers();

	/**
	 * get user's concerned thridparts
	 * 
	 * @param userid
	 */
	public List<User_ThirdPart_Client> getUser_TPs(Integer userid);

	/**
	 * get user's concerned issuers
	 * 
	 * @param userid
	 * @return List<User_Issuer>
	 */
	public List<User_Issuer_Client> getUser_Issuers(Integer userid);

	/**
	 * save user thirdpart
	 * 
	 * @param userThirdPart
	 */
	public Integer saveUser_TP(User_ThirdPart_Client userThirdPart);

	/**
	 * save user issuer
	 * 
	 * @param userIssuer
	 */
	public Integer saveUser_Issuer(User_Issuer_Client userIssuer);

	/**
	 * update user thirdpart
	 * 
	 * @param userThirdPart
	 */
	public void updateUser_TP(User_ThirdPart_Client userThirdPart);

	/**
	 * save user issuer
	 * 
	 * @param userIssuer
	 */
	public void updateUser_Issuer(User_Issuer_Client userIssuer);

	/**
	 * delete user thirdpart
	 * 
	 * @param userid
	 * @param tpid
	 */
	public void deleteUser_TP(Integer userid, Integer tpid);

	/**
	 * delete user issuer
	 * 
	 * @param userid
	 * @param issuerid
	 */
	public void deleteUser_Issuer(Integer userid, Integer issuerid);

	/**
	 * check if is user's issuer
	 * 
	 * @param userid
	 * @param issuerid
	 */
	public Integer ifUser_Issuer(Integer userid, Integer issuerid);

}
