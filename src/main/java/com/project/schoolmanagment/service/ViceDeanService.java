package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.BadRequestException;
import com.project.schoolmanagment.Exception.ConflictException;
import com.project.schoolmanagment.Exception.ResourceNotFoundException;
import com.project.schoolmanagment.entity.concretes.ViceDean;
import com.project.schoolmanagment.entity.enums.Role;
import com.project.schoolmanagment.payload.Dto.ViceDeanDto;
import com.project.schoolmanagment.payload.request.ViceDeanRequest;
import com.project.schoolmanagment.payload.response.ViceDeanResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.ViceDeanRepository;
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
public class ViceDeanService {

    private final ViceDeanRepository viceDeanRepository;
    private final UserRoleService userRoleService;
    private final ViceDeanDto viceDeanDto;

    private final PasswordEncoder passwordEncoder;
    public ResponseMessage<ViceDeanResponse> save(ViceDeanRequest viceDeanRequest) {
        if (viceDeanRepository.existsBySsn(viceDeanRequest.getSsn())) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN, viceDeanRequest.getSsn()));

        } else if (viceDeanRepository.existsByPhoneNumber(viceDeanRequest.getPhoneNumber())) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, viceDeanRequest.getPhoneNumber()));
        }
        ViceDean viceDean = createDtoForViceDean(viceDeanRequest);
        viceDean.setUserRole(userRoleService.getUserRole(Role.ASSISTANTMANAGER));
        viceDean.setPassword(passwordEncoder.encode(viceDeanRequest.getPassword()));
        viceDeanRepository.save(viceDean);
        return ResponseMessage.<ViceDeanResponse>builder()
                .message("Vice dean Saved")
                .httpStatus(HttpStatus.CREATED)
                .object(createViceDeanResponse(viceDean)).build();
    }

    public ResponseMessage<ViceDeanResponse> update(ViceDeanRequest newViceDean, Long managerId) {
        Optional<ViceDean> viceDean = viceDeanRepository.findById(managerId);
        if (!viceDean.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER_MESSAGE, managerId));
        }
        else if(!CheckParameterUpdateMethod.checkParameter(viceDean.get(),newViceDean)){
            if (viceDeanRepository.existsBySsn(newViceDean.getSsn())) {
                throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN, newViceDean.getSsn()));

            } else if (viceDeanRepository.existsByPhoneNumber(newViceDean.getPhoneNumber())) {
                throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, newViceDean.getPhoneNumber()));
            }
        }

        ViceDean updatedViceDean = createUpdatedViceDean(newViceDean, managerId);
        updatedViceDean.setPassword(passwordEncoder.encode(newViceDean.getPassword()));
        viceDeanRepository.save(updatedViceDean);
        return ResponseMessage.<ViceDeanResponse>builder()
                .message("Vice dean Updated Successful")
                .httpStatus(HttpStatus.OK)
                .object(createViceDeanResponse(updatedViceDean))
                .build();

    }

    public ResponseMessage deleteViceDean(Long managerId) {
        Optional<ViceDean> viceDean = viceDeanRepository.findById(managerId);
        if (!viceDean.isPresent()) {
            throw new BadRequestException(String.format(Messages.NOT_FOUND_USER_MESSAGE, managerId));

        }
        viceDeanRepository.deleteById(managerId);
        return ResponseMessage.builder().message("Vice dean Deleted")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public ResponseMessage<ViceDeanResponse> getViceDeanById(Long managerId) {
        Optional<ViceDean> viceDean = viceDeanRepository.findById(managerId);
        if (!viceDean.isPresent()) {
            throw new BadRequestException(String.format(Messages.NOT_FOUND_USER_MESSAGE, managerId));

        }
        return ResponseMessage.<ViceDeanResponse>builder()
                .message("Vice dean successfully found")
                .httpStatus(HttpStatus.OK)
                .object(createViceDeanResponse(viceDean.get()))
                .build();
    }

    public List<ViceDeanResponse> getAllViceDean() {
        return viceDeanRepository.findAll().stream().map(this::createViceDeanResponse).collect(Collectors.toList());
    }

    private ViceDean createUpdatedViceDean(ViceDeanRequest viceDeanRequest, Long managerId) {
        return ViceDean.builder().id(managerId)
                .ssn(viceDeanRequest.getSsn())
                .name(viceDeanRequest.getName())
                .surname(viceDeanRequest.getSurname())
                .birthPlace(viceDeanRequest.getBirthPlace())
                .birthDay(viceDeanRequest.getBirthDay())
                .phoneNumber(viceDeanRequest.getPhoneNumber())
                .gender(viceDeanRequest.getGender())
                .userRole(userRoleService.getUserRole(Role.ASSISTANTMANAGER))
                .build();

    }

    private ViceDean createDtoForViceDean(ViceDeanRequest viceDeanRequest) {
        return viceDeanDto.dtoViceDean(viceDeanRequest);
    }

    private ViceDeanResponse createViceDeanResponse(ViceDean viceDean) {
        return ViceDeanResponse.builder().userId(viceDean.getId())
                .name(viceDean.getName())
                .surname(viceDean.getSurname())
                .phoneNumber(viceDean.getPhoneNumber())
                .ssn(viceDean.getSsn())
                .gender(viceDean.getGender())
                .build();
    }
    public Page<ViceDean> search(int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return viceDeanRepository.findAll(pageable);
    }
}
