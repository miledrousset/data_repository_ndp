package com.cnrs.ndp.outils;


import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class LdapExample {


    String ldapAdServer = "ldaps.somdatechnology.onmicrosoft.com";
    String ldapSearchBase = "OU=AADDC Users,DC=SOMDATECHNOLOGY,DC=ONMICROSOFT,DC=COM";

    String ldapUsername = "uxiaapp@martialsomdahotmail.onmicrosoft.com";
    String ldapPassword = "%N4gZ%&jcB=-6p3";

    String ldapAccountToLookup = "(|(userPrincipalName=firas@martialsomdahotmail.onmicrosoft.com))";

    String keyStorePassword = "changeit";
    String keyStorePath = "/Library/Java/JavaVirtualMachines/jdk-13.0.1.jdk/Contents/Home/lib/security/cacerts";

    String trustStorePassword = "changeit";
    String trustStorePath = "/Library/Java/JavaVirtualMachines/jdk-13.0.1.jdk/Contents/Home/lib/security/cacerts";

    String initialContextFactory = "com.sun.jndi.ldap.LdapCtxFactory";


    public boolean authentificationLdapCheck(String login, String password) throws Exception {
        Hashtable<String,String> env = new Hashtable <String,String>();
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, login);
        env.put(Context.SECURITY_CREDENTIALS, password);

        try {
            env.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
            env.put(Context.PROVIDER_URL, "ldaps://"+ ldapAdServer);
            new InitialDirContext(env);
        }
        catch (javax.naming.AuthenticationException e) {
            return false;
        }
        return true;
    }



    public static void main(String[] args) {

        LdapContext ctx = null;
        try {

            String ldapAdServer = "ldaps.somdatechnology.onmicrosoft.com";
            String ldapSearchBase = "OU=AADDC Users,DC=SOMDATECHNOLOGY,DC=ONMICROSOFT,DC=COM";

            String ldapUsername = "uxiaapp@martialsomdahotmail.onmicrosoft.com";
            String ldapPassword = "%N4gZ%&jcB=-6p3";

            String ldapAccountToLookup = "(|(userPrincipalName=firas@martialsomdahotmail.onmicrosoft.com))";

            String keyStorePassword = "changeit";
            String keyStorePath = "/Library/Java/JavaVirtualMachines/jdk-13.0.1.jdk/Contents/Home/lib/security/cacerts";

            String trustStorePassword = "changeit";
            String trustStorePath = "/Library/Java/JavaVirtualMachines/jdk-13.0.1.jdk/Contents/Home/lib/security/cacerts";

            String initialContextFactory = "com.sun.jndi.ldap.LdapCtxFactory";

            System.setProperty("javax.net.ssl.keyStore", keyStorePath);
            System.setProperty("javax.net.ssl.trustStorePassword", keyStorePassword);

            System.setProperty("javax.net.ssl.trustStore", trustStorePath);
            System.setProperty("javax.net.ssl.keyStorePassword", trustStorePassword);

            Hashtable env = new Hashtable();

            if(ldapUsername != null) {
                env.put(Context.SECURITY_PRINCIPAL, ldapUsername);
            }

            if(ldapPassword != null) {
                env.put(Context.SECURITY_CREDENTIALS, ldapPassword);
            }

            env.put(Context.SECURITY_PROTOCOL, "ssl");
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put("java.naming.ldap.attributes.binary", "objectSID");
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

            if( "ssl".equals(env.get(Context.SECURITY_PROTOCOL)) ) {
                env.put(Context.PROVIDER_URL, "ldaps://"+ldapAdServer);
            } else {
                env.put(Context.PROVIDER_URL, "ldap://"+ldapAdServer);
            }


            ctx = new InitialLdapContext( env, null );

            if (testBind("firas@martialsomdahotmail.onmicrosoft.com", "7RA~Ugvj+S9;bsz")) {
                System.out.println("IS OK !!");
            } else {
                System.out.println("IS NOT OK");
            }

            NamingEnumeration list = new LdapExample().findAccount(ctx, ldapSearchBase, ldapAccountToLookup);
            listResult( list );

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(ctx!=null) {
                    ctx.close();
                }
            } catch(Exception e) { }
        }
    }

    public static boolean testBind (String dn, String password) throws Exception {
        Hashtable<String,String> env = new Hashtable <String,String>();
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, dn);
        env.put(Context.SECURITY_CREDENTIALS, password);

        try {
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldaps://"+ "ldaps.somdatechnology.onmicrosoft.com");
            InitialDirContext test = new InitialDirContext(env);
        }
        catch (javax.naming.AuthenticationException e) {
            return false;
        }
        return true;
    }

    public NamingEnumeration findAccount(DirContext ctx,
                                         String ldapSearchBase, String searchFilter) throws NamingException {

        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        NamingEnumeration results = ctx.search(ldapSearchBase, searchFilter, searchControls);

        return results;
    }

    public static void listResult(NamingEnumeration results) throws NamingException
    {
        int i = 1;
        SearchResult searchResult = null;
        while(results.hasMoreElements())
        {
            searchResult = (SearchResult) results.nextElement();
            System.out.println("============ record "+i+" ============");
            System.out.println( searchResult.getNameInNamespace() );
            Attributes attrs = searchResult.getAttributes();
            NamingEnumeration enumer = attrs.getAll();
            Attribute attrObj;
            while( enumer.hasMore() )
            {
                attrObj = (Attribute)enumer.next();
                System.out.print( "name: "+attrObj.getID()+", " );
                System.out.println( "value: "+attrObj.get() );
            }
            i++;
        }
    }
}