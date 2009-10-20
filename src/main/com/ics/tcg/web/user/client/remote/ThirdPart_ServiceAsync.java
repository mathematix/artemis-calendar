package com.ics.tcg.web.user.client.remote;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ics.tcg.web.user.client.db.Issuer_Client;
import com.ics.tcg.web.user.client.db.ThirdPart_Client;
import com.ics.tcg.web.user.client.db.User_Issuer_Client;
import com.ics.tcg.web.user.client.db.User_ThirdPart_Client;

public interface ThirdPart_ServiceAsync {

	void deleteUser_Issuer(Integer userid, Integer issuerid,
			AsyncCallback<Void> callback);

	void deleteUser_TP(Integer userid, Integer tpid,
			AsyncCallback<Void> callback);

	void getIssuerByID(Integer issuerid, AsyncCallback<Issuer_Client> callback);

	void getIssuers(AsyncCallback<List<Issuer_Client>> callback);

	void getTPs(AsyncCallback<List<ThirdPart_Client>> callback);

	void getThirdPartByID(Integer tpid, AsyncCallback<ThirdPart_Client> callback);

	void getUser_Issuers(Integer userid,
			AsyncCallback<List<User_Issuer_Client>> callback);

	void getUser_TPs(Integer userid,
			AsyncCallback<List<User_ThirdPart_Client>> callback);

	void ifUser_Issuer(Integer userid, Integer issuerid,
			AsyncCallback<Integer> callback);

	void saveUser_Issuer(User_Issuer_Client userIssuer,
			AsyncCallback<Integer> callback);

	void saveUser_TP(User_ThirdPart_Client userThirdPart,
			AsyncCallback<Integer> callback);

	void updateUser_TP(User_ThirdPart_Client userThirdPart,
			AsyncCallback<Void> callback);

	void updateUser_Issuer(User_Issuer_Client userIssuer,
			AsyncCallback<Void> callback);

}
