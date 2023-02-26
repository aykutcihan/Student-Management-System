package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.ConflictException;
import com.project.schoolmanagment.entity.concretes.GuestUser;
import com.project.schoolmanagment.entity.enums.Role;
import com.project.schoolmanagment.payload.request.GuestUserRequest;
import com.project.schoolmanagment.payload.response.GuestUserResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.GuestUserRepository;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestUserService {
    private final GuestUserRepository repository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;


    public ResponseMessage register(GuestUserRequest request) {
        if (repository.existsByUsername(request.getUsername())) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_USERNAME, request.getUsername()));
        } else if (repository.existsBySsn(request.getSsn())) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, request.getSsn()));
        } else if (repository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, request.getPhoneNumber()));
        }
        GuestUser guest = createGuest(request);
        guest.setUserRole(userRoleService.getUserRole(Role.GUESTUSER));
        guest.setPassword(passwordEncoder.encode(guest.getPassword()));
        GuestUser savedData = repository.save(guest);
        return ResponseMessage.<GuestUserResponse>builder()
                .message("Guest User registered.")
                .httpStatus(HttpStatus.CREATED)
                .object(createResponse(savedData)).build();
    }

    private GuestUserResponse createResponse(GuestUser user) {
        return GuestUserResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .ssn(user.getSsn()).build();
    }


    private GuestUser createGuest(GuestUserRequest request) {
        return GuestUser.builder()
                .username(request.getUsername())
                .name(request.getName())
                .surname(request.getSurname())
                .password(request.getPassword())
                .ssn(request.getSsn())
                .birthDay(request.getBirthDay())
                .birthPlace(request.getBirthPlace())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())
                .build();
    }


    public Page<GuestUser> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public ResponseMessage delete(Long id) {
        if (!repository.existsByIdEquals(id))
            throw new ConflictException(String.format(Messages.NOT_FOUND_GUESTUSER_BY_ID));
        repository.deleteById(id);
        return ResponseMessage.<GuestUserResponse>builder()
                .message(" Guest User deleted Successful")
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
