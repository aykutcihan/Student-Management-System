package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concretes.UserRole;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.repository.UserRoleRepository;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRoleService {

	private final UserRoleRepository userRoleRepository;

	public UserRole getUserRole (RoleType roleType){

		//Optional<UserRole> userRole = userRoleRepository.findByEnumRoleEquals(roleType);

//		/**
//		 * check the optional usages in spring boot.
//		 */
//		if(userRole.isPresent()){
//			return userRole.get();
//		} else {
//			throw new ConflictException(Messages.ROLE_NOT_FOUND);
//		}

		return userRoleRepository.findByEnumRoleEquals(roleType).orElseThrow(
				()-> new ConflictException(Messages.ROLE_NOT_FOUND));


	}

	public List<UserRole>getAllUserRole(){
		return userRoleRepository.findAll();
	}

	public UserRole save (RoleType roleType){
		if(userRoleRepository.existsByEnumRoleEquals(roleType)){
			throw new ConflictException(Messages.ROLE_ALREADY_EXIST);
		}
		UserRole userRole = UserRole.builder().roleType(roleType).build();
		return userRoleRepository.save(userRole);
	}





}
