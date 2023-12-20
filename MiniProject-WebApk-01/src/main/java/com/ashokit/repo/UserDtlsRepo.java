package com.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entity.UserDtlsEntity;

public interface UserDtlsRepo extends JpaRepository<UserDtlsEntity, Integer>
{
	public UserDtlsEntity findByEmailId(String email);
	
	public UserDtlsEntity findByEmailIdAndPwd(String email,String pwd);

}
