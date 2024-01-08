package com.javaweb.model;

public class BuildingDTO {
	private String name;
	private String address;
	private Integer numberOFBasement;
	private String ManagerName, ManagerPhone, rentArea;
	private Integer floorArea, emptyArea, rentPrice, serviceFee, brokerFee;
	public String getName() {
		return name;
	}
	public Integer getNumberOFBasement() {
		return numberOFBasement;
	}
	public void setNumberOFBasement(Integer numberOFBasement) {
		this.numberOFBasement = numberOFBasement;
	}
	public String getManagerName() {
		return ManagerName;
	}
	public void setManagerName(String managerName) {
		ManagerName = managerName;
	}
	public String getManagerPhone() {
		return ManagerPhone;
	}
	public void setManagerPhone(String managerPhone) {
		ManagerPhone = managerPhone;
	}
	public Integer getFloorArea() {
		return floorArea;
	}
	public void setFloorArea(Integer floorArea) {
		this.floorArea = floorArea;
	}
	public String getRentArea() {
		return rentArea;
	}
	public void setRentArea(String rentArea) {
		this.rentArea = rentArea;
	}
	public Integer getEmptyArea() {
		return emptyArea;
	}
	public void setEmptyArea(Integer emptyArea) {
		this.emptyArea = emptyArea;
	}
	public Integer getRentPrice() {
		return rentPrice;
	}
	public void setRentPrice(Integer rentPrice) {
		this.rentPrice = rentPrice;
	}
	public Integer getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(Integer serviceFee) {
		this.serviceFee = serviceFee;
	}
	public Integer getBrokerFee() {
		return brokerFee;
	}
	public void setBrokerFee(Integer brokerFee) {
		this.brokerFee = brokerFee;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setName(String name) {
		this.name = name;
	}
}
