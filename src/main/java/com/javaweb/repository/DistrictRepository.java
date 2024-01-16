package com.javaweb.repository;

import java.util.List;

import com.javaweb.repository.entity.DistrictEntity;

public interface DistrictRepository {
	DistrictEntity findByDistrictId(Long districtId);
}
