package com.project.schoolmanagment.service;

import com.project.schoolmanagment.entity.concretes.Parent;
import com.project.schoolmanagment.payload.Dto.ParentRequestDto;
import com.project.schoolmanagment.payload.request.ParentRequest;
import com.project.schoolmanagment.payload.response.ParentResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final ParentRepository parentRepository;
    private final StudentService studentService;
    private final ParentRequestDto parentRequestDto;

    public ResponseMessage<Parent> save(ParentRequest parentRequest){
        if(parentRepository.existsBySsn(parentRequest.getSsn())){
            return ResponseMessage.<Parent>builder()
                    .message("This Parent already register")
                    .httpStatus(HttpStatus.CREATED).build();
        }
        Parent parent = parentRequestToDto(parentRequest);
        //parent.setStudents(studentService.getStudentsByIdList(parentRequest.getStudentIdList()));
        parentRepository.save(parent);
        //Parent savedParent = parentRepository.save(parent);
        return ResponseMessage.<Parent>builder()
                .message("Parent saved Successfully")
                .object(parent)
                .httpStatus(HttpStatus.CREATED).build();
    }
    /*
    public ResponseMessage<Parent> update(Parent parent,Long parentId){

    }



    public ResponseMessage<Parent> deleteParent(Long parentId){

    }



    public ResponseMessage<Parent> getParentById(Long parentId){

    }

     */

    public List<ParentResponse> getAllParent(){
        return createParentResponse(parentRepository.findAll());
    }

    private List<ParentResponse> createParentResponse(List<Parent> parents){
        return parents.stream().map((p)-> ParentResponse.builder()
                        .parentId(p.getId())
                        .surname(p.getSurname())
                        .name(p.getName())
                        .phoneNumber(p.getPhoneNumber())
                        .build())
                .collect(Collectors.toList());
    }

    private Parent parentRequestToDto(ParentRequest parentRequest){
        return parentRequestDto.dtoParent(parentRequest);
    }

}
