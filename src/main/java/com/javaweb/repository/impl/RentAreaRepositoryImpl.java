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
import com.javaweb.utils.ConnectionJDBCUtil;

@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository {

	@Override
	public List<RentAreaEntity> findByBuildingId(Long id) {
		StringBuilder sql = new StringBuilder("SELECT * FROM rentarea r WHERE r.buildingid = "+ id);
		List<RentAreaEntity> result = new ArrayList<>();
		try (Connection conn = ConnectionJDBCUtil.getConnection();) {
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