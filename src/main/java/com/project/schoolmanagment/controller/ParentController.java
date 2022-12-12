package com.project.schoolmanagment.controller;

import com.project.schoolmanagment.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("parent")
@RequiredArgsConstructor
public class ParentController {


    private final ParentService parentService;

}
