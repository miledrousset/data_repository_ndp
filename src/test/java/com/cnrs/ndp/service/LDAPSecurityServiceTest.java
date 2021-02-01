package com.cnrs.ndp.service;

import com.cnrs.ndp.service.impl.LDAPSecurityServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class LDAPSecurityServiceTest {


    @Autowired
    private LDAPSecurityServiceImpl ldapSecurityConfig;


    @Test
    public void authentificationLdapCheckTest() throws Exception {
/*
        if (ldapSecurityConfig.authentificationLdapCheck("firas@martialsomdahotmail.onmicrosoft.com", "7RA~Ugvj+S9;bsz")) {
            System.out.println("Athentification OK !!");
        } else {
            System.out.println("Athentification NOT OK");
        }
*/
    }
}
