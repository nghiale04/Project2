package com.javaweb.repository.custom.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.custom.BuildingRepositoryCustom;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.ConnectionJDBCUtil;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;

@Repository
@PropertySource("classpath:application.properties")
@Primary
public class BuildingRepositoryImpl implements BuildingRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Value("${spring.datasource.url}")
	private String DB_URL;
	@Value("${spring.datasource.username}")
	private String USER;
	@Value("${spring.datasource.password}")
	private String PASS;

	public static void joinTable(BuildingSearchBuilder builingSearchBuilder, StringBuilder sql) {
		List<String> rentTypeCode = builingSearchBuilder.getRentTypeCode();
		if (rentTypeCode != null && rentTypeCode.size() != 0) {
			sql.append(" INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
			sql.append(" INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
		}	
	}

	public static void queryNormal(BuildingSearchBuilder builingSearchBuilder, StringBuilder where) {
		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			for (Field item : fields) {
				item.setAccessible(true);
				String fieldName = item.getName();
				if (!fieldName.equals("staffId") && !fieldName.equals("rentTypeCode")
						&& !fieldName.startsWith("area") && !fieldName.startsWith("rentPrice")){
					 Object value = item.get(builingSearchBuilder);
					 if (value != null) {
							if (item.getType().getName().equals("java.lang.Long") || item.getType().getName().equals("java.lang.Integer")) {
								where.append(" AND b." + fieldName + "=" + value + " ");
							} else if (item.getType().getName().equals("java.lang.String")){
								where.append(" AND b." + fieldName + " LIKE '%" + value + "%' ");
							}
						}
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void querySpecial(BuildingSearchBuilder builingSearchBuilder, StringBuilder where) {
		Long staffId = builingSearchBuilder.getStaffId();
		if (staffId!=null) {
			where.append(" AND EXISTS (SELECT * FROM assignmentbuilding WHERE assignmentbuilding.buildingid = b.id ");
			where.append(" AND assignmentbuilding.staffid = " + staffId);
			where.append(" ) ");
		}
		
		Long rentAreaTo = builingSearchBuilder.getAreaTo();
		Long rentAreaFrom = builingSearchBuilder.getAreaFrom();
		if ((rentAreaFrom !=null) || (rentAreaTo!=null)) {
			where.append(" AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid ");
			if (rentAreaFrom != null) {
				where.append(" AND r.value >= " + rentAreaFrom);
			}
			if (rentAreaTo != null) {
				where.append(" AND r.value <= " + rentAreaTo);
			}
			where.append(" ) ");
		}
		Long rentPriceTo = builingSearchBuilder.getRentPriceTo();
		Long rentPriceFrom = builingSearchBuilder.getRentPriceFrom();
		if (rentPriceFrom !=null || rentPriceTo !=null) {
			if (rentPriceFrom !=null) {
				where.append(" AND b.rentprice >= " + rentPriceFrom);
			}
			if (rentPriceTo !=null) {
				where.append(" AND b.rentprice <= " + rentPriceTo);
			}
		}
		List<String> rentTypeCode = builingSearchBuilder.getRentTypeCode();
		if (rentTypeCode != null && rentTypeCode.size() != 0) {
			where.append(" AND( ");
			String sql = rentTypeCode.stream().map(it -> "renttype.code LIKE"+"'%"+it+"%' ").collect(Collectors.joining(" OR "));
			where.append(sql);
			where.append(" ) ");
		}
	}

	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder builingSearchBuilder) {
		StringBuilder sql = new StringBuilder("SELECT b.* FROM building b ");
		joinTable(builingSearchBuilder, sql);
		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
		queryNormal(builingSearchBuilder, where);
		querySpecial(builingSearchBuilder, where);
		where.append(" GROUP BY b.id;");
		sql.append(where);
		Query query = entityManager.createNativeQuery(sql.toString(),BuildingEntity.class);
		return query.getResultList();
		
//		StringBuilder sql = new StringBuilder(
//				"SELECT b.id,b.name,b.districtid,b.street,b.ward,b.numberofbasement,b.floorarea,b.rentprice,b.managername,b.managerphonenumber,b.servicefee,b.brokeragefee FROM building b ");
//		joinTable(builingSearchBuilder, sql);
//		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
//		queryNormal(builingSearchBuilder, where);
//		querySpecial(builingSearchBuilder, where);
//		where.append(" GROUP BY b.id;");
//		sql.append(where);
//		System.out.println(sql);
//		List<BuildingEntity> result = new ArrayList<>();
//		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);) {
//			Statement stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery(sql.toString());
//			{
//				while (rs.next()) {
//					BuildingEntity building = new BuildingEntity();
//					building.setId(rs.getLong("b.id"));
//					building.setName(rs.getString("b.name"));
//					building.setStreet(rs.getString("b.street"));
//					building.setWard(rs.getString("b.ward"));
////					building.setDistrictid(rs.getLong("b.districtid"));
//					building.setFloorArea(rs.getLong("b.floorarea"));
//					building.setNumberOfBasement(rs.getLong("b.numberofbasement"));
//					building.setRentPrice(rs.getLong("b.rentprice"));
//					building.setServiceFee(rs.getString("b.servicefee"));
//					building.setBrokerageFee(rs.getString("brokeragefee"));
//					building.setManagerName(rs.getString("b.managername"));
//					building.setManagerPhoneNumber(rs.getString("b.managerphonenumber"));
//					result.add(building);
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("Connected database failed...");
//		}
//		return null;
	}

}
