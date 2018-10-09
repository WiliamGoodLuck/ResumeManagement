package com.code.resumemanagement.utils;

public class ResumeException extends Exception {

  private static final long serialVersionUID = 2751452497334238378L;
  private String status; // 消息码
  private String message;// 消息说明

  public ResumeException(String msg) {
    super(msg);
  }

  public ResumeException(String status, String message) {
    this.status = status;
    this.message = message;
  }

  public ResumeException() {
    super();
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  public ResumeException(Throwable cause) {
    super(cause.getMessage());
  }

  public ResumeException(String message, Throwable cause) {
    super(message, cause);
  }

}
