package com.cnrs.ndp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.naming.Context;
import javax.naming.directory.*;
import java.util.Hashtable;


@Configuration
public class LDAPSecurityService {

    private final static String LDAPS_PROTOCOL = "ldaps://";


    @Value("${ldap.server.url}")
    private String ldapAdServer;

    @Value("${ldap.base.dn}")
    private String ldapSearchBase;

    @Value("${key.store.password}")
    private String keyStorePassword;

    @Value("${key.store.path}")
    private String keyStorePath;

    @Value("${trust.store.password}")
    private String trustStorePassword;

    @Value("${trust.store.path}")
    private String trustStorePath;

    @Value("${ldap.initial.context.factory}")
    private String ldapInitialContextFactory;

    @Value("${ldap.security.authentication}")
    private String ldapSecurityAuthentication;


    public boolean authentificationLdapCheck(String login, String password) {

        try {
            System.setProperty("javax.net.ssl.keyStore", keyStorePath);
            System.setProperty("javax.net.ssl.trustStorePassword", keyStorePassword);

            System.setProperty("javax.net.ssl.trustStore", trustStorePath);
            System.setProperty("javax.net.ssl.keyStorePassword", trustStorePassword);

            Hashtable<String,String> env = new Hashtable <String,String>();
            env.put(Context.SECURITY_AUTHENTICATION, ldapSecurityAuthentication);
            env.put(Context.SECURITY_PRINCIPAL, "uid="+login+",ou=people,ou=notre-dame-paris,dc=huma-num,dc=fr");
            env.put(Context.SECURITY_CREDENTIALS, password);
            env.put(Context.INITIAL_CONTEXT_FACTORY, ldapInitialContextFactory);
            env.put(Context.PROVIDER_URL, LDAPS_PROTOCOL + ldapAdServer);
            new InitialDirContext(env);
            return true;
        }
        catch (Exception e) {
            System.out.println(">> " + e);
            return false;
        }
    }

}
