package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.ConnectionJDBCUtil;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {

	public static void joinTable(Map<String, String> params, List<String> rentTypeCode, StringBuilder sql) {
		if (rentTypeCode != null && rentTypeCode.size() != 0) {
			sql.append(" INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
			sql.append(" INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
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
			where.append(" AND EXISTS (SELECT * FROM assignmentbuilding WHERE assignmentbuilding.buildingid = b.id ");
			where.append(" AND assignmentbuilding.staffid = " + staffId);
			where.append(" ) ");
		}
		String rentAreaTo = params.get("rentareato");
		String rentAreaFrom = params.get("rentareafrom");
		if (StringUtil.checkString(rentAreaFrom) || StringUtil.checkString(rentAreaTo)) {
			where.append(" AND EXISTS (SELECT * FROM reanarea r WHERE b.id = r.buildingid ");
			if (StringUtil.checkString(rentAreaFrom)) {
				where.append(" AND rentarea.value >= " + rentAreaFrom);
			}
			if (StringUtil.checkString(rentAreaTo)) {
				where.append(" AND rentarea.value <= " + rentAreaTo);
			}
			where.append(" ) ");
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
			where.append(" AND( ");
			String sql = rentTypeCode.stream().map(it -> "renttype.code LIKE"+"'%"+it+"%' ").collect(Collectors.joining(" OR "));
			where.append(sql);
			where.append(" ) ");
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
		where.append(" GROUP BY b.id;");
		sql.append(where);
		System.out.println(sql);
		List<BuildingEntity> result = new ArrayList<>();
		try (Connection conn = ConnectionJDBCUtil.getConnection();) {
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
