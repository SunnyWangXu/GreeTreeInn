package com.greetreeinn.entity;


public class ResponseData
{
	private Integer[] service;

	private Integer id;

	private String name;

	private String phone;

	private String description;

	private String address;

	private String price;

	private HotelDetailImage[] images;

	private HotelRooms[] rooms;

	public Integer[] getService()
	{
		return service;
	}

	public void setService(Integer[] service)
	{
		this.service = service;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getPrice()
	{
		return price;
	}

	public void setPrice(String price)
	{
		this.price = price;
	}

	public HotelDetailImage[] getImages()
	{
		return images;
	}

	public void setImages(HotelDetailImage[] images)
	{
		this.images = images;
	}

	public HotelRooms[] getRooms()
	{
		return rooms;
	}

	public void setRooms(HotelRooms[] rooms)
	{
		this.rooms = rooms;
	}

}
