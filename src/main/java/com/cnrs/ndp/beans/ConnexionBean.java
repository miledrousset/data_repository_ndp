package com.cnrs.ndp.beans;

import com.cnrs.ndp.service.LDAPSecurityService;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


@Named(value = "connexionBean")
@SessionScoped
public class ConnexionBean implements Serializable {

    @Autowired
    private DepotManagerBean depotManager;

    @Autowired
    private LDAPSecurityService ldapSecurityService;

    @Value("${ldap.authentification.enable}")
    private boolean authentificationEnable;

    private boolean isUserConnected;
    private String login, password;
    private String username;
    private String itemMenuSelected;

    

    @PostConstruct
    public void initComposant() {
        isUserConnected = false;
        login = "";
        password = "";
    }
    
    public void connexion() throws Exception {

        if (authentificationEnable) {
            if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
                showMessage(FacesMessage.SEVERITY_ERROR, "Vous devez saisir les deux champs !");
                return;
            }

            if (ldapSecurityService.authentificationLdapCheck(login, password)) {

                isUserConnected = true;

                username = login;
                login = "";
                password = "";

                showMessage(FacesMessage.SEVERITY_INFO, "Bienvenu " + username + "!");
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");

            } else {
                showMessage(FacesMessage.SEVERITY_ERROR, "Echec de connexion !!!");
            }
        } else {
            username = "test";
            isUserConnected = true;
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            showMessage(FacesMessage.SEVERITY_ERROR, "Authentification désactivée !");
        }

    }

    private void showMessage(FacesMessage.Severity messageType, String messageValue) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageType, "", messageValue));
        PrimeFaces.current().ajax().update("messages");
    }
    
    public void deconnexion() {
        username = null;
        isUserConnected = false;
        PrimeFaces.current().executeScript("PF('deconnexionDialog').hide();");
        PrimeFaces pf = PrimeFaces.current();
        pf.ajax().update("headerPanel");
    }
    
    public String getTitleMenuStyle(String item) {
        return item.equalsIgnoreCase(itemMenuSelected) ? "color: #d9232d; font-weight: bold;" : "";
    }

    public boolean isIsUserConnected() {
        return isUserConnected;
    }

    public void setIsUserConnected(boolean isUserConnected) {
        this.isUserConnected = isUserConnected;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setItemMenuSelected(String itemMenuSelected) {
        this.itemMenuSelected = itemMenuSelected;
        if (itemMenuSelected.equals("Gestion du dépôt")) {
            depotManager.initComposant();
        }
    }

    public String getUsername() {
        return username;
    }

}
