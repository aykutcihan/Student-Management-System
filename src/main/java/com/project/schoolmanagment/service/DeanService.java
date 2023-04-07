package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.ResourceNotFoundException;
import com.project.schoolmanagment.entity.concretes.Dean;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.payload.Dto.DeanDto;
import com.project.schoolmanagment.payload.request.DeanRequest;
import com.project.schoolmanagment.payload.response.DeanResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.DeanRepository;
import com.project.schoolmanagment.service.util.CheckParameterUpdateMethod;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeanService {
    private final DeanRepository deanRepository;

    private final UserRoleService userRoleService;

    private final PasswordEncoder passwordEncoder;

    private final DeanDto deanDto;
    private final AdminService adminService;


    public ResponseMessage<DeanResponse> save(DeanRequest deanRequest) {
        adminService. checkDuplicate(deanRequest.getUsername(), deanRequest.getSsn(), deanRequest.getPhoneNumber());

        Dean dean = createDtoForDean(deanRequest);
        dean.setUserRole(userRoleService.getUserRole(RoleType.MANAGER));
        dean.setPassword(passwordEncoder.encode(dean.getPassword()));
        Dean savedDean = deanRepository.save(dean);
        return ResponseMessage.<DeanResponse>builder()
                .message("Dean Saved")
                .httpStatus(HttpStatus.CREATED)
                .object(createDeanResponse(savedDean)).build();
    }

    public ResponseMessage<DeanResponse> update(DeanRequest newDean, Long deanId) {
        Optional<Dean> dean = deanRepository.findById(deanId);
        ResponseMessage.ResponseMessageBuilder<DeanResponse> responseMessageBuilder = ResponseMessage.builder();
        if (!dean.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, deanId));

        } else if (!CheckParameterUpdateMethod.checkParameter(dean.get(), newDean)) {
            adminService. checkDuplicate(newDean.getUsername(), newDean.getSsn(), newDean.getPhoneNumber());
        }
        Dean updatedDean = createUpdatedDean(newDean, deanId);
        updatedDean.setPassword(passwordEncoder.encode(newDean.getPassword()));
        deanRepository.save(updatedDean);
        return responseMessageBuilder
                .message("Dean updated Successful")
                .httpStatus(HttpStatus.OK)
                .object(createDeanResponse(updatedDean))
                .build();

    }

    public ResponseMessage deleteDean(Long deanId) {
        Optional<Dean> dean = deanRepository.findById(deanId);
        if (!dean.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, deanId));
        }
        deanRepository.deleteById(deanId);
        return ResponseMessage.builder().message("Dean Deleted")
                .httpStatus(HttpStatus.OK)
                .build();

    }

    public ResponseMessage<DeanResponse> getDeanById(Long deanId) {
        Optional<Dean> dean = deanRepository.findById(deanId);
        if (!dean.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, deanId));

        }
        return ResponseMessage.<DeanResponse>builder().message("Dean successfully found")
                .httpStatus(HttpStatus.OK)
                .object(createDeanResponse(dean.get()))
                .build();
    }

    public List<DeanResponse> getAllDean() {
        return deanRepository.findAll().stream().map(this::createDeanResponse).collect(Collectors.toList());
    }

    private Dean createUpdatedDean(DeanRequest deanRequest, Long managerId) {
        return Dean.builder().id(managerId)
                .username(deanRequest.getUsername())
                .ssn(deanRequest.getSsn())
                .name(deanRequest.getName())
                .surname(deanRequest.getSurname())
                .birthPlace(deanRequest.getBirthPlace())
                .birthDay(deanRequest.getBirthDay())
                .phoneNumber(deanRequest.getPhoneNumber())
                .gender(deanRequest.getGender())
                .userRole(userRoleService.getUserRole(RoleType.MANAGER))
                .build();

    }

    public Page<DeanResponse> search(int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return deanRepository.findAll(pageable).map(this::createDeanResponse);
    }

    private DeanResponse createDeanResponse(Dean dean) {
        return DeanResponse.builder().userId(dean.getId())
                .username(dean.getUsername())
                .name(dean.getName())
                .surname(dean.getSurname())
                .birthDay(dean.getBirthDay())
                .birthPlace(dean.getBirthPlace())
                .phoneNumber(dean.getPhoneNumber())
                .gender(dean.getGender())
                .ssn(dean.getSsn()).build();
    }

    private Dean createDtoForDean(DeanRequest deanRequest) {
        return deanDto.dtoDean(deanRequest);
    }


}
