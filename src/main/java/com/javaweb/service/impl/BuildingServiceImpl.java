package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.RentTypeRepository;
import com.javaweb.repository.StaffRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.repository.entity.RentAreaEntity;
import com.javaweb.service.BuildingService;
import com.mysql.cj.x.protobuf.MysqlxSession.AuthenticateContinue;

@Service
public class BuildingServiceImpl implements BuildingService {
	@Autowired
	private BuildingRepository buildingRepository;
	@Autowired
	private DistrictRepository districtRepository;
	@Autowired
	private RentAreaRepository rentAreaRepository;
	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private RentTypeRepository rentTypeRepository;

	@Override
	public List<BuildingDTO> findAll(Map<String, String> params, List<String> rentTypeCode) {
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(params, rentTypeCode);
		List<BuildingDTO> result = new ArrayList<BuildingDTO>();
		for (BuildingEntity item : buildingEntities) {
			BuildingDTO building = new BuildingDTO();
			DistrictEntity district = districtRepository.findByDistrictId(item.getDistrictid());
			building.setName(item.getName());
			building.setAddress(item.getStreet() + ", " + item.getWard()+","+ district.getName());
			result.add(building);
			}
	return result;
}
}