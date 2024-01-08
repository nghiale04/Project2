package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.entity.DistrictEntity;

@Repository
public class DistrictRepositoryImpl implements DistrictRepository{
	static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
	static final String USER = "root";
	static final String PASS = "Nghia132004567";
	@Override
	public DistrictEntity findByBuildingId(Integer districtId) {
		StringBuilder sql = new StringBuilder("SELECT district.id, district.name FROM district INNER JOIN building ON building.districtid = district.id");
		if (districtId != null) {
			sql.append(" WHERE district.id = " + districtId);
		}
		DistrictEntity district = new DistrictEntity();
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql.toString()); {
			while (rs.next()) {
				district.setDistrictId(rs.getInt("id"));
				district.setName(rs.getString("name"));
			}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connected database failed...");
		}
		return district;
	}
}
