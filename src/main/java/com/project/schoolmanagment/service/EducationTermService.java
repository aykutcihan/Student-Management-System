package com.project.schoolmanagment.service;

import com.project.schoolmanagment.Exception.ResourceNotFoundException;
import com.project.schoolmanagment.entity.concretes.EducationTerm;
import com.project.schoolmanagment.payload.request.EducationTermRequest;
import com.project.schoolmanagment.payload.response.EducationTermResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.EducationTermRepository;
import com.project.schoolmanagment.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationTermService {

    private final EducationTermRepository educationTermRepository;

    public ResponseMessage<EducationTermResponse> save(EducationTermRequest request) {

        if (request.getLastRegistrationDate().isAfter(request.getStartDate()))
            throw new ResourceNotFoundException(String.format(Messages.EDUCATION_START_DATE_IS_SMALLER_THAN_LAST_REGISTRATION_DATE));

        if (request.getEndDate().isBefore(request.getStartDate()))
            throw new ResourceNotFoundException(String.format(Messages.EDUCATION_START_DATE_IS_SMALLER_THAN_END_DATE));

        if (educationTermRepository.existsByTermAndYear(request.getTerm(), request.getStartDate().getYear()))
            throw new ResourceNotFoundException(String.format(Messages.EDUCATION_TERM_NOT_FOUND_MESSAGE_BY_TERM_AND_YEAR));

        EducationTerm savedEducationTerm = educationTermRepository.save(createEducationTerm(request));
        return ResponseMessage.<EducationTermResponse>builder()
                .message("Created Education Term")
                .object(createEducationTermResponse(savedEducationTerm))
                .httpStatus(HttpStatus.CREATED).build();
    }

    public EducationTermResponse get(Long id) {
        if (!educationTermRepository.existsByIdEquals(id)) {
            throw new ResourceNotFoundException(String.format(Messages.EDUCATION_TERM_NOT_FOUND_MESSAGE, id));
        }
        return createEducationTermResponse(educationTermRepository.findByIdEquals(id));
    }

    public List<EducationTermResponse> getAll() {
        return educationTermRepository.findAll()
                .stream()
                .map(this::createEducationTermResponse)
                .collect(Collectors.toList());
    }


    public Page<EducationTermResponse> search(int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return educationTermRepository.findAll(pageable).map(this::createEducationTermResponse);
    }

    public ResponseMessage<EducationTermResponse> update(Long id, EducationTermRequest request) {

        if (!educationTermRepository.existsByIdEquals(id))
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_LESSON_MESSAGE, id));

        if (request.getStartDate() != null && request.getLastRegistrationDate() != null)
            if (request.getLastRegistrationDate().isAfter(request.getStartDate()))
                throw new ResourceNotFoundException(String.format(Messages.EDUCATION_START_DATE_IS_SMALLER_THAN_LAST_REGISTRATION_DATE));

        if (request.getStartDate() != null && request.getEndDate() != null)
            if (request.getEndDate().isBefore(request.getStartDate()))
                throw new ResourceNotFoundException(String.format(Messages.EDUCATION_START_DATE_IS_SMALLER_THAN_END_DATE));

        ResponseMessage.ResponseMessageBuilder<EducationTermResponse> responseMessageBuilder = ResponseMessage.builder();

        EducationTerm updated = createUpdatedEducationTerm(id, request);
        educationTermRepository.save(updated);
        return responseMessageBuilder.object(createEducationTermResponse(updated))
                .message("Education Term updated Successfully").build();

    }


    public EducationTerm createUpdatedEducationTerm(Long id, EducationTermRequest request) {
        return EducationTerm.builder()
                .id(id)
                .term(request.getTerm())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .lastRegistrationDate(request.getLastRegistrationDate())
                .build();
    }

    public EducationTerm createEducationTerm(EducationTermRequest request) {
        return EducationTerm.builder()
                .term(request.getTerm())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .lastRegistrationDate(request.getLastRegistrationDate())
                .build();
    }

    public EducationTermResponse createEducationTermResponse(EducationTerm response) {
        return EducationTermResponse.builder()
                .id(response.getId())
                .term(response.getTerm())
                .startDate(response.getStartDate())
                .endDate(response.getEndDate())
                .lastRegistrationDate(response.getLastRegistrationDate())
                .build();
    }
    public EducationTermResponse createEducationTermResponseWithId(EducationTerm response) {
        return EducationTermResponse.builder()
                .id(response.getId())
                .term(response.getTerm())
                .startDate(response.getStartDate())
                .endDate(response.getEndDate())
                .lastRegistrationDate(response.getLastRegistrationDate())
                .build();
    }


    public EducationTerm getById(Long id) {

        if (!educationTermRepository.existsByIdEquals(id)) {
            throw new ResourceNotFoundException(String.format(Messages.EDUCATION_TERM_NOT_FOUND_MESSAGE, id));
        }
        return educationTermRepository.findByIdEquals(id);
    }

    public ResponseMessage delete(Long id) {
        if (!educationTermRepository.existsByIdEquals(id)) {
            throw new ResourceNotFoundException(String.format(Messages.EDUCATION_TERM_NOT_FOUND_MESSAGE, id));
        }
          educationTermRepository.deleteById(id);
        return ResponseMessage.builder()
                .message("Education Term deleted Successful")
                .httpStatus(HttpStatus.CREATED).build();
    }
}
