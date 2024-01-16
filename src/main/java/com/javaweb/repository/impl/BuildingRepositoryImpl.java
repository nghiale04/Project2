package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
	static final String USER = "root";
	static final String PASS = "Nghia132004567";

	public static void joinTable(Map<String, String> params, List<String> rentTypeCode, StringBuilder sql) {
		String staffId = params.get("staffid");
		if (StringUtil.checkString(staffId)) {
			sql.append(" INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
		}
		if (rentTypeCode != null && rentTypeCode.size() != 0) {
			sql.append(" INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
			sql.append(" INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
		}
		String rentAreaTo = params.get("rentareato");
		String rentAreaFrom = params.get("rentareafrom");
		if (StringUtil.checkString(rentAreaFrom) || StringUtil.checkString(rentAreaTo)) {
			sql.append(" INNER JOIN rentarea ON rentarea.buildingid = b.id ");
		}
	}

	public static void queryNormal(Map<String, String> params, StringBuilder where) {
		for (Map.Entry<String, String> it : params.entrySet()) {
			if (!it.getKey().equals("staffid") && !it.getKey().equals("renttypecode")
					&& !it.getKey().startsWith("rentarea") && !it.getKey().startsWith("rentprice")) {
				String value = it.getValue();
				if (StringUtil.checkString(value)) {
					if (NumberUtil.isNumber(value) == true) {
						where.append(" AND b." + it.getKey() + "=" + value + " ");
					} else {
						where.append(" AND b." + it.getKey() + " LIKE '%" + value + "%' ");
					}
				}
			}
		}
	}

	public static void querySpecial(Map<String, String> params, List<String> rentTypeCode, StringBuilder where) {
		String staffId = params.get("staffid");
		if (StringUtil.checkString(staffId)) {
			where.append(" AND assignmentbuilding.staffid = " + staffId);
		}
		String rentAreaTo = params.get("rentareato");
		String rentAreaFrom = params.get("rentareafrom");
		if (StringUtil.checkString(rentAreaFrom) || StringUtil.checkString(rentAreaTo)) {
			if (StringUtil.checkString(rentAreaFrom)) {
				where.append(" AND rentarea.value >= " + rentAreaFrom);
			}
			if (StringUtil.checkString(rentAreaTo)) {
				where.append(" AND rentarea.value <= " + rentAreaTo);
			}
		}
		String rentPriceTo = params.get("rentpriceto");
		String rentPriceFrom = params.get("rentpricefrom");
		if (StringUtil.checkString(rentPriceFrom) || StringUtil.checkString(rentPriceTo)) {
			if (StringUtil.checkString(rentAreaFrom)) {
				where.append(" AND b.rentprice >= " + rentPriceFrom);
			}
			if (StringUtil.checkString(rentAreaTo)) {
				where.append(" AND b.rentprice <= " + rentPriceTo);
			}
		}
		if (rentTypeCode != null && rentTypeCode.size() != 0) {
			List<String> code = new ArrayList<>();
			for (String item: rentTypeCode) {
				code.add("'"+item+"'");
			}
			where.append(" AND renttype.code IN (" + String.join(",", code) + ")");
		}
	}

	@Override
	public List<BuildingEntity> findAll(Map<String, String> params, List<String> rentTypeCode) {
		StringBuilder sql = new StringBuilder(
				"SELECT b.id,b.name,b.districtid,b.street,b.ward,b.numberofbasement,b.floorarea,b.rentprice,b.managername,b.managerphonenumber,b.servicefee,b.brokeragefee FROM building b ");
		joinTable(params, rentTypeCode, sql);
		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
		queryNormal(params, where);
		querySpecial(params, rentTypeCode, where);
		where.append("GROUP BY b.id;");
		sql.append(where);
		System.out.println(sql);
		List<BuildingEntity> result = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());
			{
				while (rs.next()) {
					BuildingEntity building = new BuildingEntity();
					building.setId(rs.getLong("b.id"));
					building.setName(rs.getString("b.name"));
					building.setStreet(rs.getString("b.street"));
					building.setWard(rs.getString("b.ward"));
					building.setDistrictid(rs.getLong("b.districtid"));
					building.setFloorArea(rs.getLong("b.floorarea"));
					building.setNumberOfBasement(rs.getLong("b.numberofbasement"));
					building.setRentPrice(rs.getLong("b.rentprice"));
					building.setServiceFee(rs.getString("b.servicefee"));
					building.setBrokerageFee(rs.getString("brokeragefee"));
					building.setManagerName(rs.getString("b.managername"));
					building.setManagerPhoneNumber(rs.getString("b.managerphonenumber"));
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
