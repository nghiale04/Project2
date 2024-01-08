package com.javaweb.repository;

import java.util.List;
import java.util.Set;

public interface RentTypeRepository {
	Set<String> findByRentTypeCode(List<String> rentTypeCode);
}
