package com.javaweb.converter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.utils.MapUtil;
@Component
public class BuildingSearchBuilderConverter {
	public BuildingSearchBuilder toBuildingSearchBuilder(Map<String, Object> params, List<String> rentTypeCode) {
		BuildingSearchBuilder buildingSearchBuilder = new BuildingSearchBuilder.Builder() 
																				.setName(MapUtil.getObject(params, "name", String.class))
																				.setFloorArea(MapUtil.getObject(params, "floorarea", Long.class))
																				.setWard(MapUtil.getObject(params, "ward", String.class))
																				.setStreet(MapUtil.getObject(params, "street", String.class))
																				.setDistrictId(MapUtil.getObject(params, "districtid", Long.class))
																				.setNumberOfBasement(MapUtil.getObject(params, "numberofbasement", Integer.class))
																				.setRentTypeCode(rentTypeCode)
																				.setManagerName(MapUtil.getObject(params, "managername", String.class))
																				.setManagerPhoneNumber(MapUtil.getObject(params, "managerphonenumber", String.class))
																				.setRentPriceFrom(MapUtil.getObject(params, "rentpricefrom", Long.class))
																				.setRentPriceTo(MapUtil.getObject(params, "rentpriceto", Long.class))
																				.setAreaFrom(MapUtil.getObject(params, "rentareafrom", Long.class))
																				.setAreaTo(MapUtil.getObject(params, "rentareato", Long.class))
																				.setStaffId(MapUtil.getObject(params, "staffid", Long.class))
																				.build();
																				
																				
																				
		return buildingSearchBuilder;
	}
}
