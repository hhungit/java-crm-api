package com.bys.crm.domain.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bys.crm.domain.erp.model.ADUsers;

@Repository
public interface ADUsersRepository extends JpaRepository< ADUsers, Long>, BaseRepository{
		
	@Override
	@Query("SELECT max(entity.id) FROM ADUsers entity")
	Long findMaxId();
	
	ADUsers findByResetToken(String token);
	
	ADUsers findByIdAndStatus(Long id, String status);
	
	ADUsers findByUsername(String username);
	
	ADUsers findByUsernameAndStatus(String username, String status);
	
	List<ADUsers> findByEmployeeEmailAndStatus(String email, String status);
}
