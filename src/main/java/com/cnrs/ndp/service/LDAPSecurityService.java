package com.cnrs.ndp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.naming.Context;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;


@Configuration
public class LDAPSecurityService {

    private final static String LDAP_PROTOCOL = "ldap://";
    private final static String LDAPS_PROTOCOL = "ldaps://";


    @Value("${ldap.server.url}")
    private String ldapAdServer;

    @Value("${ldap.base.dn}")
    private String ldapSearchBase;

    @Value("${ldap.admin.username}")
    private String ldapUsername;

    @Value("${ldap.admin.password}")
    private String ldapPassword;

    @Value("${ldap.acount.search}")
    private String ldapAcountSearch;

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

    @Value("${ldap.security.protocol}")
    private String ldapSecurityProftocol;

    @Value("${ldap.security.authentication}")
    private String ldapSecurityAuthentication;

    private LdapContext ldapContext;


    @PostConstruct
    public void initLdapContext() {
        System.setProperty("javax.net.ssl.keyStore", keyStorePath);
        System.setProperty("javax.net.ssl.trustStorePassword", keyStorePassword);

        System.setProperty("javax.net.ssl.trustStore", trustStorePath);
        System.setProperty("javax.net.ssl.keyStorePassword", trustStorePassword);

        try {
            Hashtable env = new Hashtable();
            env.put(Context.SECURITY_PROTOCOL, ldapSecurityProftocol);
            env.put(Context.SECURITY_AUTHENTICATION, ldapSecurityAuthentication);
            env.put("java.naming.ldap.attributes.binary", "objectSID");
            env.put(Context.INITIAL_CONTEXT_FACTORY, ldapInitialContextFactory);

            if(ldapUsername != null) {
                env.put(Context.SECURITY_PRINCIPAL, ldapUsername);
            }

            if(ldapPassword != null) {
                env.put(Context.SECURITY_CREDENTIALS, ldapPassword);
            }

            if( "ssl".equals(env.get(Context.SECURITY_PROTOCOL)) ) {
                env.put(Context.PROVIDER_URL, LDAPS_PROTOCOL + ldapAdServer);
            } else {
                env.put(Context.PROVIDER_URL, LDAP_PROTOCOL + ldapAdServer);
            }

            ldapContext = new InitialLdapContext( env, null );

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(ldapContext!=null) {
                    ldapContext.close();
                }
            } catch(Exception e) { }
        }
    }

    public boolean authentificationLdapCheck(String login, String password) throws Exception {
        Hashtable<String,String> env = new Hashtable <String,String>();
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, login);
        env.put(Context.SECURITY_CREDENTIALS, password);

        try {
            env.put(Context.INITIAL_CONTEXT_FACTORY, ldapInitialContextFactory);
            env.put(Context.PROVIDER_URL, LDAPS_PROTOCOL + ldapAdServer);
            new InitialDirContext(env);
        }
        catch (javax.naming.AuthenticationException e) {
            return false;
        }
        return true;
    }

}
