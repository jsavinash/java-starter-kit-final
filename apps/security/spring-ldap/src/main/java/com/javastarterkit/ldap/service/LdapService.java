// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.ldap.service;

import com.javastarterkit.ldap.entity.LdapUser;
import java.util.List;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

@Service
public class LdapService {

    private final LdapTemplate ldapTemplate;

    public LdapService(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public List<LdapUser> searchUsers(String filter) {
        return ldapTemplate.search("(uid=" + filter + ")", (attributes) -> {
            LdapUser user = new LdapUser();
            user.setUid(attributes.get("uid").get().toString());
            user.setCn(attributes.get("cn").get().toString());
            user.setSn(attributes.get("sn").get().toString());
            user.setEmail(attributes.get("mail").get().toString());
            return user;
        });
    }

    public LdapUser findByUid(String uid) {
        return ldapTemplate
                .search("(uid=" + uid + ")", (attributes) -> {
                    LdapUser user = new LdapUser();
                    user.setUid(attributes.get("uid").get().toString());
                    user.setCn(attributes.get("cn").get().toString());
                    user.setSn(attributes.get("sn").get().toString());
                    user.setEmail(attributes.get("mail").get().toString());
                    return user;
                })
                .stream()
                .findFirst()
                .orElse(null);
    }
}
