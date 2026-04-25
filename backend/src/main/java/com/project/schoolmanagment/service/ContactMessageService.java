package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concretes.ContactMessage;
import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.payload.request.ContactMessageRequest;
import com.project.schoolmanagment.payload.response.ContactMessageResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.ContactMessageRepository;
import com.project.schoolmanagment.utils.Messages;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest) {

        //it is expected to create one message in a day with the same email
        boolean isSameMessageWithSameEmailForToday =
                contactMessageRepository.existsByEmailEqualsAndDateEquals(contactMessageRequest.getEmail(), LocalDate.now());

        if (isSameMessageWithSameEmailForToday) {
            throw new ConflictException(Messages.ALREADY_SEND_A_MESSAGE_TODAY);
        }

        ContactMessage contactMessage = createContactMessage(contactMessageRequest);


        ContactMessage savedData = contactMessageRepository.save(contactMessage);
        return ResponseMessage.<ContactMessageResponse>builder()
                // this message should be moved to Messages class and called from there.
                .message("Contact Message Created Successfully")
                .httpStatus(HttpStatus.CREATED)
                .object(createResponse(savedData))
                .build();
    }


    public Page<ContactMessageResponse> getAll(int page, int size, String sort, String type) {
        // in this solution type prop. should be instance of Direction
        // method signature -> getAll(int page,int size, String sort, Direction type)
        //Pageable myPageable  = PageRequest.of(page,size,Sort.by(type,sort));

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return contactMessageRepository.findAll(pageable).map(this::createResponse);
    }

    public Page<ContactMessageResponse> searchByEmail(String email, int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return contactMessageRepository.findByEmailEquals(email, pageable).map(this::createResponse);
    }

    public Page<ContactMessageResponse> searchBySubject(String subject, int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return contactMessageRepository.findBySubjectEquals(subject, pageable).map(this::createResponse);
    }

    private ContactMessageResponse createResponse(ContactMessage contactMessage) {
        return ContactMessageResponse.builder()
                .name(contactMessage.getName())
                .subject(contactMessage.getSubject())
                .message(contactMessage.getMessage())
                .email(contactMessage.getEmail())
                .date(LocalDate.now())
                .build();
    }

    //TODO please check builder design pattern
    //I would give this method a name like mapContactMessageRequestToContactMessage
    private ContactMessage createContactMessage(ContactMessageRequest contactMessageRequest) {
        return ContactMessage.builder()
                .name(contactMessageRequest.getName())
                .subject(contactMessageRequest.getSubject())
                .message(contactMessageRequest.getMessage())
                .email(contactMessageRequest.getEmail())
                .date(LocalDate.now())
                .build();
    }


}
