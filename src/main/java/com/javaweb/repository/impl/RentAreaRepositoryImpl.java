package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.RentAreaEntity;

@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository {
	static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
	static final String USER = "root";
	static final String PASS = "Nghia132004567";
	@Override
	public List<RentAreaEntity> findByBuildingId(Integer rentAreaFrom, Integer rentAreaTo) {
		StringBuilder sql = new StringBuilder("SELECT * FROM rentarea where 1 = 1 ");
		if (rentAreaFrom != null) {
			sql.append(" AND rentarea.value >= " + rentAreaFrom);
		}
		if (rentAreaTo != null) {
			sql.append(" AND rentarea.value <= " + rentAreaTo);
		}
		List<RentAreaEntity> result = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql.toString()); {
				while (rs.next()) {
					RentAreaEntity rentArea = new RentAreaEntity();
					rentArea.setId(rs.getInt("id"));
					rentArea.setValue(rs.getString("value"));
					rentArea.setBuildingId(rs.getInt("buildingid")); 
					result.add(rentArea);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connected database failed...");
		}
		return result;
	}
}