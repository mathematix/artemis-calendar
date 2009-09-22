package com.ics.tcg.web.user.client.db;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class User_Client implements IsSerializable {
	private Integer userid;
	private String account;
	private String password;
	private boolean sex;
	private Integer age;
	private Date birthday;
	private String email;
	private String tel;
	private boolean bymail;
	private boolean bymobile;

	public boolean isBymail() {
		return bymail;
	}

	public void setBymail(boolean bymail) {
		this.bymail = bymail;
	}

	public boolean isBymobile() {
		return bymobile;
	}

	public void setBymobile(boolean bymobile) {
		this.bymobile = bymobile;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public User_Client() {

	}

	public User_Client(User_Client result) {
		setAccount(result.account);
		setAge(result.age);
		setBirthday(result.birthday);
		setBymail(result.bymail);
		setBymobile(result.bymobile);
		setEmail(result.email);
		setPassword(result.password);
		setSex(result.sex);
		setTel(result.tel);
		setUserid(result.userid);
	}
}
