package com.dauphin.dauphin.Services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dauphin.dauphin.models.RoleGrupo;
import com.dauphin.dauphin.services.RoleGrupoService;

@SpringBootTest
public class RoleGrupoServiceTest {

    @Autowired
    RoleGrupoService roleGrupoService;

    @Test
    void listAllGroupRoles(){
        List<RoleGrupo> roles = roleGrupoService.buscaTodos();
        assertFalse(roles.isEmpty());

        for(RoleGrupo role : roles){
            System.out.println(role.toString());
        }
    }
}
