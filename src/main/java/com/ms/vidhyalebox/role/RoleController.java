package com.ms.vidhyalebox.role;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/role")
public class RoleController {

private final RoleRepo roleRepo;

    public RoleController(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @PostMapping("/")
    public ResponseEntity<RoleEntity> signup(@RequestBody RoleEntity roleEntity) {
        RoleEntity save = roleRepo.save(roleEntity);
        return ResponseEntity.ok(save);
    }
}
