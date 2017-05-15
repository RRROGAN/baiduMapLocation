package org.rogan.map.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class User
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int id;
  private String uid;
  private String login_name;
  private String name;
  private String password;
  private String salt;
  private char gender;
  private String email;
  private String phone;
  private String icon;
  private Timestamp create_time;
  private char state;
  private String description;
  private String extend_info;
  private int login_count;
  private Timestamp last_visit;
  private Timestamp modify_time;

  public User() {
	// TODO Auto-generated constructor stub
}
  public User(String login_name, String password) {
	  this.login_name = login_name;
	  this.password = password;
}
  public int getId()
  {
    return this.id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

  public String getLogin_name() {
    return this.login_name;
  }
  public void setLogin_name(String login_name) {
    this.login_name = login_name;
  }
  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getPassword() {
    return this.password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getSalt() {
    return this.salt;
  }
  public void setSalt(String salt) {
    this.salt = salt;
  }
  public char getGender() {
    return this.gender;
  }
  public void setGender(char gender) {
    this.gender = gender;
  }
  public String getEmail() {
    return this.email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPhone() {
    return this.phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }
  public String getIcon() {
    return this.icon;
  }
  public void setIcon(String icon) {
    this.icon = icon;
  }
  public Timestamp getCreate_time() {
    return this.create_time;
  }
  public void setCreate_time(Timestamp create_time) {
    this.create_time = create_time;
  }
  public char getState() {
    return this.state;
  }
  public void setState(char state) {
    this.state = state;
  }
  public String getDescription() {
    return this.description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getExtend_info() {
    return this.extend_info;
  }
  public void setExtend_info(String extend_info) {
    this.extend_info = extend_info;
  }
  public int getLogin_count() {
    return this.login_count;
  }
  public void setLogin_count(int login_count) {
    this.login_count = login_count;
  }
  public Timestamp getLast_visit() {
    return this.last_visit;
  }
  public void setLast_visit(Timestamp last_visit) {
    this.last_visit = last_visit;
  }
  public Timestamp getModify_time() {
    return this.modify_time;
  }
  public void setModify_time(Timestamp modify_time) {
    this.modify_time = modify_time;
  }

  public String toString()
  {
    return "User [id=" + this.id + ", login_name=" + this.login_name + ", name=" + this.name + ", password=" + this.password + ", salt=" + 
      this.salt + ", gender=" + this.gender + ", email=" + this.email + ", phone=" + this.phone + ", icon=" + this.icon + 
      ", create_time=" + this.create_time + ", state=" + this.state + ", description=" + this.description + 
      ", extend_info=" + this.extend_info + ", login_count=" + this.login_count + ", last_visit=" + this.last_visit + 
      ", modify_time=" + this.modify_time + "]";
  }
}