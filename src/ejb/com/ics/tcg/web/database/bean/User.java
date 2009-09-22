package com.ics.tcg.web.database.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "User")
public class User implements Serializable {

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

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	// account
	@Column(nullable = false, length = 32)
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	// password
	@Column(nullable = false, length = 32)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// sex
	@Column(nullable = true)
	public boolean getSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	// age
	@Column(nullable = true)
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	// birthday
	@Temporal(value = TemporalType.DATE)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	// email
	@Column(nullable = true, length = 32)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// tel
	@Column(nullable = true, length = 16)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	// if notify by mail
	@Column(nullable = true)
	public boolean isBymail() {
		return bymail;
	}

	public void setBymail(boolean bymail) {
		this.bymail = bymail;
	}

	// is notify by mobile
	@Column(nullable = true)
	public boolean isBymobile() {
		return bymobile;
	}

	public void setBymobile(boolean bymobile) {
		this.bymobile = bymobile;
	}
}
