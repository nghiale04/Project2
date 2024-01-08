package com.javaweb.repository;

import java.util.Set;

public interface StaffRepository {
	Set<String> findByStaffId(Integer staffId);
}
