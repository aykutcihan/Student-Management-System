package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concretes.AssistantManager;
import com.project.schoolmanagment.entity.concretes.Role;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.AssistantManagerRepository;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssistantManagerService {

    private final AssistantManagerRepository assistantManagerRepository;
    private final UserRoleService userRoleService;

    public ResponseMessage<AssistantManager> save(AssistantManager assistantManager) {
        assistantManager.setUserRole(userRoleService.getUserRole(Role.ASSISTANTMANAGER));
        AssistantManager savedAssistantManager = assistantManagerRepository.save(assistantManager);
        return ResponseMessage.<AssistantManager>builder()
                .message("Assistant Manager Saved")
                .httpStatus(HttpStatus.CREATED)
                .object(assistantManagerRepository.save(savedAssistantManager)).build();
    }

    public ResponseMessage<AssistantManager> update(AssistantManager newAssistantManager, Long managerId) {
        newAssistantManager.setUserRole(userRoleService.getUserRole(Role.ASSISTANTMANAGER));
        Optional<AssistantManager> assistantManager = assistantManagerRepository.findById(managerId);
        ResponseMessage.ResponseMessageBuilder<AssistantManager> responseMessageBuilder = ResponseMessage.builder();
        if (assistantManager.isPresent()) {
            AssistantManager updatedAssistantManager = createUpdatedManager(newAssistantManager, managerId);
            assistantManagerRepository.save(updatedAssistantManager);
            return responseMessageBuilder
                    .message("Assistant Manager Updated Successful")
                    .httpStatus(HttpStatus.OK)
                    .object(updatedAssistantManager)
                    .build();
        }
        return responseMessageBuilder
                .message(Messages.NOT_FOUND_USER_MESSAGE)
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    public ResponseMessage deleteAssistantManager(Long managerId) {
        Optional<AssistantManager> assistantManager = assistantManagerRepository.findById(managerId);
        ResponseMessage.ResponseMessageBuilder responseMessageBuilder = ResponseMessage.builder();
        if (assistantManager.isPresent()) {
            assistantManagerRepository.deleteById(managerId);
            return responseMessageBuilder.message("Assistant Manager Deleted")
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
        return responseMessageBuilder.message(Messages.NOT_FOUND_USER_MESSAGE)
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    public ResponseMessage<AssistantManager> getAssistantManagerById(Long managerId) {
        Optional<AssistantManager> assistantManager = assistantManagerRepository.findById(managerId);
        ResponseMessage.ResponseMessageBuilder<AssistantManager> responseMessageBuilder = ResponseMessage.builder();
        if (assistantManager.isPresent()) {
            return responseMessageBuilder.message("AssistantManager successfully found")
                    .httpStatus(HttpStatus.OK)
                    .object(assistantManager.get())
                    .build();
        }
        return responseMessageBuilder.message(Messages.NOT_FOUND_USER_MESSAGE)
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    public List<AssistantManager> getAllAssistantManager() {
        return assistantManagerRepository.findAll();
    }

    private AssistantManager createUpdatedManager(AssistantManager assistantManager, Long managerId) {
        return AssistantManager.builder().id(managerId)
                .ssn(assistantManager.getSsn())
                .name(assistantManager.getName())
                .surname(assistantManager.getSurname())
                .birthPlace(assistantManager.getBirthPlace())
                .birthDay(assistantManager.getBirthDay())
                .password(assistantManager.getPassword())
                .phoneNumber(assistantManager.getPhoneNumber())
                .build();

    }

}
