package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concretes.Manager;
import com.project.schoolmanagment.entity.enums.Role;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.ManagerRepository;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;

    private final UserRoleService userRoleService;

    private final PasswordEncoder passwordEncoder;


    public ResponseMessage<Manager> save(Manager manager) {
        manager.setUserRole(userRoleService.getUserRole(Role.MANAGER));
        manager.setPassword(passwordEncoder.encode(manager.getPassword()));
        Manager savedManager = managerRepository.save(manager);
        return ResponseMessage.<Manager>builder()
                .message("Manager Saved")
                .httpStatus(HttpStatus.CREATED)
                .object(managerRepository.save(savedManager)).build();
    }

    public ResponseMessage<Manager> update(Manager newManager, Long managerId) {
        newManager.setUserRole(userRoleService.getUserRole(Role.MANAGER));
        Optional<Manager> manager = managerRepository.findById(managerId);
        ResponseMessage.ResponseMessageBuilder<Manager> responseMessageBuilder = ResponseMessage.builder();
        if (manager.isPresent()) {
            Manager updatedAssistantManager = createUpdatedManager(newManager, managerId);
            managerRepository.save(updatedAssistantManager);
            return responseMessageBuilder
                    .message("Manager updated Successful")
                    .httpStatus(HttpStatus.OK)
                    .object(updatedAssistantManager)
                    .build();
        }
        return responseMessageBuilder
                .message(Messages.NOT_FOUND_USER_MESSAGE)
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    public ResponseMessage deleteManager(Long managerId) {
        Optional<Manager> manager = managerRepository.findById(managerId);
        ResponseMessage.ResponseMessageBuilder responseMessageBuilder = ResponseMessage.builder();
        if (manager.isPresent()) {
            managerRepository.deleteById(managerId);
            return responseMessageBuilder.message("Manager Deleted")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
        return responseMessageBuilder.message(Messages.NOT_FOUND_USER_MESSAGE)
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    public ResponseMessage<Manager> getManagerById(Long managerId) {
        Optional<Manager> manager = managerRepository.findById(managerId);
        ResponseMessage.ResponseMessageBuilder<Manager> responseMessageBuilder = ResponseMessage.builder();
        if (manager.isPresent()) {
            return responseMessageBuilder.message("Manager successfully found")
                    .httpStatus(HttpStatus.OK)
                    .object(manager.get())
                    .build();
        }
        return responseMessageBuilder.message(Messages.NOT_FOUND_USER_MESSAGE)
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    public List<Manager> getAllManager() {
        return managerRepository.findAll();
    }

    private Manager createUpdatedManager(Manager manager, Long managerId) {
        return Manager.builder().id(managerId)
                .ssn(manager.getSsn())
                .name(manager.getName())
                .surname(manager.getSurname())
                .birthPlace(manager.getBirthPlace())
                .birthDay(manager.getBirthDay())
                .password(manager.getPassword())
                .phoneNumber(manager.getPhoneNumber())
                .build();

    }
}
