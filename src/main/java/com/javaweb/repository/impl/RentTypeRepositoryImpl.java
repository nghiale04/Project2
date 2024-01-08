package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.RentTypeRepository;

@Repository
public class RentTypeRepositoryImpl implements RentTypeRepository{
	static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
	static final String USER = "root";
	static final String PASS = "Nghia132004567";
	@Override
	public Set<String> findByRentTypeCode(List<String> rentTypeCode) {
		StringBuilder sql = new StringBuilder(
				"SELECT building.name, renttype.code, buildingrenttype.buildingid, buildingrenttype.renttypeid FROM buildingrenttype"
				+ " INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeid"
				+ " INNER JOIN building ON building.id = buildingrenttype.buildingid WHERE 2 = 1");
		if (rentTypeCode != null) {
		for (String x : rentTypeCode) {
		if (x != null && !x.equals("")) {
			sql.append(" OR renttype.code LIKE '%" + x + "%' ");
		}
		}
		}
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
