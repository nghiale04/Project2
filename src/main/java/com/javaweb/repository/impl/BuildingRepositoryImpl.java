package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
	static final String USER = "root";
	static final String PASS = "Nghia132004567";
	@Override
	public List<BuildingEntity> findAll(Map<String, String> params, List<String> rentTypeCode) {
		StringBuilder sql = new StringBuilder("SELECT * FROM building b WHERE 1 = 1 ");
		BuildingEntity buildingQuery = new BuildingEntity();
		Set<Map.Entry<String, String>> entrySet = params.entrySet();
		for (Map.Entry<String, String> entry : entrySet) {
			if (entry.getKey().equals("name"))
				buildingQuery.setName(entry.getValue().toString());;
			if (entry.getKey().equals("street"))
				buildingQuery.setStreet(entry.getValue().toString());
			if (entry.getKey().equals("ward"))
				buildingQuery.setWard(entry.getValue().toString());
			if (entry.getKey().equals("districtid"))
				buildingQuery.setDistrictId(Integer.parseInt(entry.getValue().toString()));
			if (entry.getKey().equals("floorarea"))
				buildingQuery.setFloorArea(Integer.parseInt(entry.getValue().toString()));
			if (entry.getKey().equals("numberofbasement"))
				buildingQuery.setNumberOfBasement(Integer.parseInt(entry.getValue().toString()));
			if (entry.getKey().equals("direction"))
				buildingQuery.setDirection(entry.getValue().toString());
			if (entry.getKey().equals("level"))
				buildingQuery.setLevel(entry.getValue().toString());
			if (entry.getKey().equals("rentareafrom") && entry.getValue() != null && !entry.getValue().equals(""))
				buildingQuery.setRentAreaFrom(Integer.parseInt(entry.getValue().toString()));;
			if (entry.getKey().equals("rentareato") && entry.getValue() != null && !entry.getValue().equals(""))
				buildingQuery.setRentAreaTo(Integer.parseInt(entry.getValue().toString()));
			if (entry.getKey().equals("rentpricefrom"))
				buildingQuery.setRentPriceFrom(Integer.parseInt(entry.getValue().toString()));
			if (entry.getKey().equals("rentpriceto"))
				buildingQuery.setRentPriceTo(Integer.parseInt(entry.getValue().toString()));
			if (entry.getKey().equals("managername"))
				buildingQuery.setManagerName(entry.getValue().toString());;
			if (entry.getKey().equals("managerphone"))
				buildingQuery.setManagerPhone(entry.getValue().toString());
			if (entry.getKey().equals("staffid"))
				buildingQuery.setStaffId(Integer.parseInt(entry.getValue().toString()));
		}
		buildingQuery.setRentTypeCode(rentTypeCode);
		
		if (buildingQuery.getName() != null && !buildingQuery.getName().equals("")) {
			sql.append(" AND b.name LIKE '%" + buildingQuery.getName() + "%' ");
		}
		if (buildingQuery.getStreet() != null && !buildingQuery.getStreet().equals("")) {
			sql.append(" AND b.street LIKE '%" + buildingQuery.getStreet() + "%' ");
		}
		if (buildingQuery.getWard() != null && !buildingQuery.getWard().equals("")) {
			sql.append(" AND b.ward LIKE '%" + buildingQuery.getWard() + "%' ");
		}
		if (buildingQuery.getDistrictId() != null) {
			sql.append(" AND b.districtid =" + buildingQuery.getDistrictId());
		}
		if (buildingQuery.getFloorArea() != null && !buildingQuery.getFloorArea().equals("")) {
			sql.append(" AND b.floorarea = " + buildingQuery.getFloorArea());
		}
		if (buildingQuery.getNumberOfBasement() != null && !buildingQuery.getNumberOfBasement().equals("")) {
			sql.append(" AND b.numberofbasement =" + buildingQuery.getNumberOfBasement());
		}
		if (buildingQuery.getDirection() != null && !buildingQuery.getDirection().equals("")) {
			sql.append(" AND b.direction LIKE '%" + buildingQuery.getDirection() + "%' ");
		}
		if (buildingQuery.getLevel() != null && !buildingQuery.getLevel().equals("")) {
			sql.append(" AND b.level LIKE '%" + buildingQuery.getLevel() + "%' ");
		}
//		if (buildingQuery.getRentAreaFrom() != null) {
//			sql.append(" AND rentarea.value >=" + buildingQuery.getRentAreaFrom());
//		}
//		if (buildingQuery.getRentAreaTo() != null) {
//			sql.append(" AND rentarea.value <=" + buildingQuery.getRentAreaTo());
//		}
		if( buildingQuery.getRentPriceFrom()!= null) {
			sql.append(" AND b.rentprice >= " + buildingQuery.getRentPriceFrom());
		}
		if (buildingQuery.getRentPriceTo() != null) {
			sql.append(" AND b.rentprice <= " + buildingQuery.getRentPriceTo());
		}
		if (buildingQuery.getManagerName() != null && !buildingQuery.getManagerName().equals("")) {
			sql.append(" AND b.managerName LIKE '%" + buildingQuery.getManagerName() + "%' ");
		}
		if (buildingQuery.getManagerPhone() != null && !buildingQuery.getManagerPhone().equals("")) {
			sql.append(" AND b.managerPhonenumber like '%" + buildingQuery.getManagerPhone() + "%' ");
		}
//		if (buildingQuery.getStaffId() != null) {
//			sql.append(" AND b.staffId =" + buildingQuery.getStaffId());
//		}
//		if () {
//			sql.append(" AND b.rentTypeCode LIKE '%" + buildingQuery.getRentTypeCode() + "%' ");
//		}
		List<BuildingEntity> result = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql.toString()); {
			while (rs.next()) {
				BuildingEntity building = new BuildingEntity();
				building.setName(rs.getString("name"));
				building.setStreet(rs.getString("street"));
				building.setWard(rs.getString("ward"));
				building.setDistrictId(rs.getInt("districtid"));
				building.setFloorArea(rs.getInt("floorarea"));
				building.setNumberOfBasement(rs.getInt("numberofbasement"));
				building.setDirection(rs.getString("direction"));
				building.setLevel(rs.getString("level"));
				building.setRentAreaFrom(buildingQuery.getRentAreaFrom());
				building.setRentAreaTo(buildingQuery.getRentAreaTo());
				building.setRentPriceFrom(rs.getInt("rentprice"));
				building.setRentPriceTo(rs.getInt("rentprice"));
				building.setManagerName(rs.getString("managername"));
				building.setManagerPhone(rs.getString("managerphonenumber"));
				building.setStaffId(buildingQuery.getStaffId());
				building.setRentTypeCode(buildingQuery.getRentTypeCode());
				result.add(building);
			}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connected database failed...");
		}
		return result;
	}
}


