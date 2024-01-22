package com.javaweb.repository;

import java.util.List;
import java.util.Map;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.RentAreaEntity;

public interface BuildingRepository {
	List<BuildingEntity> findAll(BuildingSearchBuilder builingSearchBuilder);
}
