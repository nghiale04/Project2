package com.javaweb.repository.entity;

import java.util.List;

public class BuildingEntity {
	private Integer buildingId;
	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}
	private String name, street, ward;
	private Integer districtId, numberOfBasement, floorArea;
	private String direction, level;
	private Integer rentAreaFrom, rentAreaTo, rentPriceFrom, rentPriceTo, staffId;
	private String managerName, managerPhone;
	private List<String> rentTypeCode;
	public Integer getBuildingId() {
		return buildingId;
	}
	public List<String> getRentTypeCode() {
		return rentTypeCode;
	}
	public void setRentTypeCode(List<String> rentTypeCode) {
		this.rentTypeCode = rentTypeCode;
	}
	public Integer getStaffId() {
		return staffId;
	}
	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getFloorArea() {
		return floorArea;
	}
	public void setFloorArea(Integer floorArea) {
		this.floorArea = floorArea;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public Integer getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}
	public Integer getNumberOfBasement() {
		return numberOfBasement;
	}
	public void setNumberOfBasement(Integer numberOfBasement) {
		this.numberOfBasement = numberOfBasement;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}

	public Integer getRentAreaFrom() {
		return rentAreaFrom;
	}
	public void setRentAreaFrom(Integer rentAreaFrom) {
		this.rentAreaFrom = rentAreaFrom;
	}
	public Integer getRentAreaTo() {
		return rentAreaTo;
	}
	public void setRentAreaTo(Integer rentAreaTo) {
		this.rentAreaTo = rentAreaTo;
	}
	public Integer getRentPriceFrom() {
		return rentPriceFrom;
	}
	public void setRentPriceFrom(Integer rentPriceFrom) {
		this.rentPriceFrom = rentPriceFrom;
	}
	public Integer getRentPriceTo() {
		return rentPriceTo;
	}
	public void setRentPriceTo(Integer rentPriceTo) {
		this.rentPriceTo = rentPriceTo;
	}
	public void setRentPriceTo(int rentPriceTo) {
		this.rentPriceTo = rentPriceTo;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getManagerPhone() {
		return managerPhone;
	}
	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}
}
