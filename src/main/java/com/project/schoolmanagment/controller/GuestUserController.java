package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.Exception.BadRequestException;
import com.project.schoolmanagment.entity.concretes.GuestUser;
import com.project.schoolmanagment.payload.request.GuestUserRequest;
import com.project.schoolmanagment.payload.response.GuestUserResponse;
import com.project.schoolmanagment.payload.response.ResponseMessage;
import com.project.schoolmanagment.service.GuestUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("guestUser")
@RequiredArgsConstructor
@CrossOrigin
public class GuestUserController {

    private final GuestUserService guestUserService;

    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> register(@RequestBody @Valid GuestUserRequest guestUserRequest ) {

        return ResponseEntity.ok(guestUserService.register(guestUserRequest));
    }


    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Page<GuestUser>> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }
        Page<GuestUser> author = guestUserService.getAll(pageable);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id) {
        return ResponseEntity.ok(guestUserService.delete(id));
    }

}
