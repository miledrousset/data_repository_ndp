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

        System.setProperty("javax.net.ssl.keyStore", keyStorePath);
        System.setProperty("javax.net.ssl.trustStorePassword", keyStorePassword);

        System.setProperty("javax.net.ssl.trustStore", trustStorePath);
        System.setProperty("javax.net.ssl.keyStorePassword", trustStorePassword);

        //1er tentative de connexion avec la valeur de DN = ou=people,dc=huma-num,dc=fr
        try {
            new InitialDirContext(createEnvironnementBean("uid="+login+",ou=people,dc=huma-num,dc=fr", password));
            return true;
        } catch (Exception e) {
            System.out.println(">> Erreur 1 : " + e.getMessage());
        }

        //2eme tentative de connexion avec la valeur de DN = ou=people,ou=notre-dame-paris,dc=huma-num,dc=fr
        try {
            new InitialDirContext(createEnvironnementBean("uid="+login+",ou=people,ou=notre-dame-paris,dc=huma-num,dc=fr", password));
            return true;
        } catch (Exception e) {
            System.out.println(">> Erreur 2 : " + e.getMessage());}

        //3eme tentative de connexion sans la valeur de DN
        try {
            new InitialDirContext(createEnvironnementBean(login, password));
            return true;
        } catch (Exception e) {
            System.out.println(">> Erreur 3 : " + e.getMessage());}

        return false;
    }

    private Hashtable<String,String> createEnvironnementBean(String login, String password) {
        Hashtable<String,String> env = new Hashtable <String,String>();
        env.put(Context.SECURITY_AUTHENTICATION, ldapSecurityAuthentication);
        env.put(Context.SECURITY_PRINCIPAL, login);
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.INITIAL_CONTEXT_FACTORY, ldapInitialContextFactory);
        env.put(Context.PROVIDER_URL, LDAPS_PROTOCOL + ldapAdServer);
        return env;
    }

}
