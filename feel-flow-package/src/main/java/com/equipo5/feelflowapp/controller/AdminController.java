package com.equipo5.feelflowapp.controller;

import com.equipo5.feelflowapp.dto.users.admin.AdminDTO;
import com.equipo5.feelflowapp.service.users.admin.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;
    public  static final String ADMIN_PATH = "/api/v1/admin";

    @PostMapping()
    public ResponseEntity createAdmin(@Validated @RequestBody AdminDTO admin){

        AdminDTO adminCreated = adminService.createAdmin(admin);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location",ADMIN_PATH+"/"+adminCreated.getUuid().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
}
