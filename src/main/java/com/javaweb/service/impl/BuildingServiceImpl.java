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
			DistrictEntity district = districtRepository.findByBuildingId(item.getDistrictId());
			List<RentAreaEntity> rentAreaEntities = rentAreaRepository.findByBuildingId(item.getRentAreaFrom(),item.getRentAreaTo());
			Set<String> staff = staffRepository.findByStaffId(item.getStaffId());
			Set<String> typeCode = rentTypeRepository.findByRentTypeCode(item.getRentTypeCode());
			if (staff != null && !staff.contains(item.getName()) && staff.size() != 0) {
				continue;
			}
			if (typeCode != null && !typeCode.contains(item.getName()) && typeCode.size() != 0 ) {
				continue;
			} 
			StringBuilder sb = new StringBuilder("");
				for (RentAreaEntity z : rentAreaEntities) {
					if (z.getBuildingId() == item.getBuildingId()) {
						sb.append(z.getValue() + ", ");
					}
				}
			if (sb.toString().equals("")) {
					continue;
			}
			sb.deleteCharAt(sb.length() - 2);
			sb.deleteCharAt(sb.length() - 1);
			building.setName(item.getName());
			building.setNumberOFBasement(item.getNumberOfBasement());
			building.setAddress(item.getStreet() + ", " + item.getWard() + ", " + district.getName());
			building.setFloorArea(item.getFloorArea());
			building.setRentArea(sb.toString());
			building.setRentPrice(item.getRentPriceFrom());
			building.setManagerName(item.getManagerName());
			building.setManagerPhone(item.getManagerPhone());
			result.add(building);
			}
	return result;
}
}