package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.BadRequestException;
import com.project.schoolmanagment.Exception.ResourceNotFoundException;
import com.project.schoolmanagment.entity.concretes.AssistantManager;
import com.project.schoolmanagment.entity.enums.Role;
import com.project.schoolmanagment.payload.Dto.AssistantManagerDto;
import com.project.schoolmanagment.payload.request.AssistantManagerRequest;
import com.project.schoolmanagment.payload.response.AssistantManagerResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.AssistantManagerRepository;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssistantManagerService {

    private final AssistantManagerRepository assistantManagerRepository;
    private final UserRoleService userRoleService;
    private final AssistantManagerDto assistantManagerDto;

    private final PasswordEncoder passwordEncoder;
    public ResponseMessage<AssistantManagerResponse> save(AssistantManagerRequest assistantManagerRequest) {
        if (assistantManagerRepository.existsBySsn(assistantManagerRequest.getSsn())) {
            throw new BadRequestException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN, assistantManagerRequest.getSsn()));

        } else if (assistantManagerRepository.existsByPhoneNumber(assistantManagerRequest.getPhoneNumber())) {
            throw new BadRequestException(String.format(Messages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, assistantManagerRequest.getPhoneNumber()));
        }
        AssistantManager assistantManager = createDtoForAssistantManager(assistantManagerRequest);
        assistantManager.setUserRole(userRoleService.getUserRole(Role.ASSISTANTMANAGER));
        assistantManager.setPassword(passwordEncoder.encode(assistantManagerRequest.getPassword()));
        assistantManagerRepository.save(assistantManager);
        return ResponseMessage.<AssistantManagerResponse>builder()
                .message("Assistant Manager Saved")
                .httpStatus(HttpStatus.CREATED)
                .object(createAssistantManagerResponse(assistantManager)).build();
    }

    public ResponseMessage<AssistantManagerResponse> update(AssistantManagerRequest newAssistantManager, Long managerId) {
        Optional<AssistantManager> assistantManager = assistantManagerRepository.findById(managerId);
        ResponseMessage.ResponseMessageBuilder<AssistantManagerResponse> responseMessageBuilder = ResponseMessage.builder();
        if (!assistantManager.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, managerId));
        } else if (assistantManagerRepository.existsBySsn(newAssistantManager.getSsn())) {
            throw new BadRequestException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN, newAssistantManager.getSsn()));

        } else if (assistantManagerRepository.existsByPhoneNumber(newAssistantManager.getPhoneNumber())) {
            throw new BadRequestException(String.format(Messages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, newAssistantManager.getPhoneNumber()));
        }


        AssistantManager updatedAssistantManager = createUpdatedManager(newAssistantManager, managerId);
        updatedAssistantManager.setUserRole(userRoleService.getUserRole(Role.ASSISTANTMANAGER));
        assistantManagerRepository.save(updatedAssistantManager);
        return responseMessageBuilder
                .message("Assistant Manager Updated Successful")
                .httpStatus(HttpStatus.OK)
                .object(createAssistantManagerResponse(updatedAssistantManager))
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

    public ResponseMessage<AssistantManagerResponse> getAssistantManagerById(Long managerId) {
        Optional<AssistantManager> assistantManager = assistantManagerRepository.findById(managerId);
        ResponseMessage.ResponseMessageBuilder<AssistantManagerResponse> responseMessageBuilder = ResponseMessage.builder();
        if (!assistantManager.isPresent()) {
            throw new BadRequestException(String.format(Messages.NOT_FOUND_USER_MESSAGE, managerId));

        }
        return responseMessageBuilder.message("AssistantManager successfully found")
                .httpStatus(HttpStatus.OK)
                .object(createAssistantManagerResponse(assistantManager.get()))
                .build();
    }

    public List<AssistantManagerResponse> getAllAssistantManager() {
        return assistantManagerRepository.findAll().stream().map(this::createAssistantManagerResponse).collect(Collectors.toList());
    }

    private AssistantManager createUpdatedManager(AssistantManagerRequest assistantManagerRequest, Long managerId) {
        return AssistantManager.builder().id(managerId)
                .ssn(assistantManagerRequest.getSsn())
                .name(assistantManagerRequest.getName())
                .surname(assistantManagerRequest.getSurname())
                .birthPlace(assistantManagerRequest.getBirthPlace())
                .birthDay(assistantManagerRequest.getBirthDay())
                .password(assistantManagerRequest.getPassword())
                .phoneNumber(assistantManagerRequest.getPhoneNumber())
                .build();

    }

    private AssistantManager createDtoForAssistantManager(AssistantManagerRequest assistantManagerRequest) {
        return assistantManagerDto.dtoAssistantManager(assistantManagerRequest);
    }

    private AssistantManagerResponse createAssistantManagerResponse(AssistantManager assistantManager) {
        return AssistantManagerResponse.builder().assistantManagerId(assistantManager.getId())
                .name(assistantManager.getName())
                .surname(assistantManager.getSurname())
                .phoneNumber(assistantManager.getPhoneNumber())
                .ssn(assistantManager.getSsn()).build();
    }

}
