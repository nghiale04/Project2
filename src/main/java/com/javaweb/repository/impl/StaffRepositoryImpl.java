package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.StaffRepository;
@Repository
public class StaffRepositoryImpl implements StaffRepository{
	static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
	static final String USER = "root";
	static final String PASS = "Nghia132004567";
	@Override
	public Set<String> findByStaffId(Integer staffId) {
		StringBuilder sql = new StringBuilder("SELECT staffid, building.name FROM assignmentbuilding INNER JOIN building ON assignmentbuilding.buildingid = building.id WHERE 1=1 ");
		if (staffId != null) {
			sql.append(" AND staffid = " + staffId);
		}
		else return null;
		Set<String> result = new LinkedHashSet<>();
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql.toString()); {
				while (rs.next()) {
					String name = "";
					name = rs.getString("name");
					result.add(name);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connected database failed...");
		}
		return result;
	}

}
