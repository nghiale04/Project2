package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.converter.BuildingDTOConverter;
import com.javaweb.converter.BuildingSearchBuilderConverter;
import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {
	@Autowired
	private BuildingRepository buildingRepository;

	@Autowired
	private BuildingDTOConverter buildingDTOConverter;

	@Autowired
	private BuildingSearchBuilderConverter builingSearchBuilderConverter;
	@Override
	public List<BuildingDTO> findAll(Map<String, Object> params, List<String> rentTypeCode) {
		BuildingSearchBuilder builingSearchBuilder = builingSearchBuilderConverter.toBuildingSearchBuilder(params, rentTypeCode);
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(builingSearchBuilder);
		List<BuildingDTO> result = new ArrayList<BuildingDTO>();
		for (BuildingEntity item : buildingEntities) {
			BuildingDTO building = buildingDTOConverter.toBuildingDTO(item);
			result.add(building);
		}
		return result;
	}
}