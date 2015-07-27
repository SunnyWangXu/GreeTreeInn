package com.greetreeinn.entity;

public class Hotels
{
	private Integer result;

	private String message;

	private ResponseData responseData;

	public Integer getResult()
	{
		return result;
	}

	public void setResult(Integer result)
	{
		this.result = result;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public ResponseData getResponseData()
	{
		return responseData;
	}

	public void setResponseData(ResponseData responseData)
	{
		this.responseData = responseData;
	}

	@Override
	public String toString()
	{
		return "Hotels [result=" + result + ", message=" + message
				+ ", responseData=" + responseData + "]";
	}

}
