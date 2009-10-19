package com.ics.tcg.web.database;

import java.util.List;

import com.ics.tcg.web.database.bean.Issuer;
import com.ics.tcg.web.database.bean.ThirdPart;
import com.ics.tcg.web.database.bean.User_Issuer;
import com.ics.tcg.web.database.bean.User_ThirdPart;

public interface ThirdPartDAO {

	/**
	 * get thirdpart by id
	 * 
	 * @param tpid
	 */
	public ThirdPart getThirdPartByID(Integer tpid);

	/**
	 * get issuer by id
	 * 
	 * @param issuerid
	 */
	public Issuer getIssuerByID(Integer issuerid);

	/**
	 * get all thridparts
	 */
	public List<ThirdPart> getTPs();

	/**
	 * get all issuers
	 */
	public List<Issuer> getIssuers();

	/**
	 * get user's concerned thridparts
	 * 
	 * @param userid
	 */
	public List<User_ThirdPart> getUser_TPs(Integer userid);

	/**
	 * get user's concerned issuers
	 * 
	 * @param userid
	 * @return List<User_Issuer>
	 */
	public List<User_Issuer> getUser_Issuers(Integer userid);

	/**
	 * save user thirdpart
	 * 
	 * @param userThirdPart
	 */
	public Integer saveUser_TP(User_ThirdPart userThirdPart);

	/**
	 * save user issuer
	 * 
	 * @param userIssuer
	 */
	public Integer saveUser_Issuer(User_Issuer userIssuer);

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
