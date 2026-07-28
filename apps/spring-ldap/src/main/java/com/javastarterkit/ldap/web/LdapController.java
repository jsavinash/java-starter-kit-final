package com.javastarterkit.ldap.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javastarterkit.ldap.entity.LdapUser;
import com.javastarterkit.ldap.service.LdapService;

@RestController
@RequestMapping("/api/ldap")
public class LdapController {

    private final LdapService ldapService;

    public LdapController(LdapService ldapService) {
        this.ldapService = ldapService;
    }

    @GetMapping("/users/{uid}")
    public ResponseEntity<LdapUser> findByUid(@PathVariable String uid) {
        LdapUser user = ldapService.findByUid(uid);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/search/{filter}")
    public ResponseEntity<List<LdapUser>> searchUsers(@PathVariable String filter) {
        return ResponseEntity.ok(ldapService.searchUsers(filter));
    }
}