package org.rogan.map.entity;

public class ResponseMsg<T>
{
  public static final String OK = "����ɹ�";
  public static final String ERR = "�û������벻һ��";
  public static final String VERIFY = "��֤�����";
  public static final String OK_CODE = "100200";
  public static final String ERR_CODE = "100401";
  public static final String VERIFY_CODE = "100600";
  private String code;
  private boolean success;
  private String msg;
  private T data;

  public static <T> ResponseMsg<T> resp(String code, boolean isSuccess, String msg, T data)
  {
    ResponseMsg responseMsg = new ResponseMsg();
    responseMsg.setCode(code);
    responseMsg.setSuccess(isSuccess);
    responseMsg.setMsg(msg);
    responseMsg.setData(data);
    return responseMsg;
  }
  public static ResponseMsg err(String msg) {
    return resp("100401", false, msg, null);
  }

  public static ResponseMsg err() {
    return resp("100401", false, "�û������벻һ��", null);
  }
  
  public static <T> ResponseMsg<T> err(T data) {
	    return resp("100401", false, null, data);
	  }

  public static <T> ResponseMsg<T> ok(String msg, T data) {
    msg = msg == null ? "����ɹ�" : msg;
    return resp("100200", true, msg, data);
  }

  public <T> ResponseMsg<T> respOk(String msg, T data) {
    return ok(msg, data);
  }

  public <T> ResponseMsg<T> respOk(String msg) {
    return ok(msg, null);
  }

  public String getCode() {
    return this.code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public boolean isSuccess() {
    return this.success;
  }
  public void setSuccess(boolean success) {
    this.success = success;
  }
  public String getMsg() {
    return this.msg;
  }
  public void setMsg(String msg) {
    this.msg = msg;
  }
  public T getData() {
    return this.data;
  }
  public void setData(T data) {
    this.data = data;
  }
}