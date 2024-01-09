package com.javaweb.repository;

import java.util.List;
import java.util.Map;

import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.RentAreaEntity;

public interface BuildingRepository {
	List<BuildingEntity> findAll(Map<String, String> params, List<String> rentTypeCode);
	Integer findByDistrictId(Integer districtId);
	List<RentAreaEntity> findByRentArea(Integer rentAreaFrom, Integer rentAreaTo);
}
