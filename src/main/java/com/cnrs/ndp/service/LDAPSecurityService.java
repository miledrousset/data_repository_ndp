package com.cnrs.ndp.service;

public interface LDAPSecurityService {

    boolean authentificationLdapCheck(String login, String password);

}
