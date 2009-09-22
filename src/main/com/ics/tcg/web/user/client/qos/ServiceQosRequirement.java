package com.ics.tcg.web.user.client.qos;

import java.io.Serializable;

/**
 * The service qos requirement set by the user.
 * 
 * @author Administrator
 * 
 */
public class ServiceQosRequirement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9139983678206019779L;

	/** Service name */
	private String serviceName;

	/** The service rank, usually ABC */
	private ServiceRank serviceRank;

	/** The non standard requirement, such as discount */
	private NoStandardsRequirement noStandardsRequirement;

	/** The special need in the standard requirement */
	private UserCustomStandards userCustomStandards;

	public ServiceQosRequirement() {
		serviceName = null;
		serviceRank = new ServiceRank();
		noStandardsRequirement = new NoStandardsRequirement();
		userCustomStandards = new UserCustomStandards();
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public ServiceRank getServiceRank() {
		return serviceRank;
	}

	public void setServiceRank(ServiceRank serviceRank) {
		this.serviceRank = serviceRank;
	}

	public NoStandardsRequirement getNoStandardsRequirement() {
		return noStandardsRequirement;
	}

	public void setNoStandardsRequirement(
			NoStandardsRequirement noStandardsRequirement) {
		this.noStandardsRequirement = noStandardsRequirement;
	}

	public UserCustomStandards getUserCustomStandards() {
		return userCustomStandards;
	}

	public void setUserCustomStandards(UserCustomStandards userCustomStandards) {
		this.userCustomStandards = userCustomStandards;
	}
}
