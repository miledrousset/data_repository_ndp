package com.cnrs.ndp.beans;

import com.cnrs.ndp.service.LDAPSecurityService;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;


@Named(value = "connexionBean")
@SessionScoped
public class ConnexionBean implements Serializable {

    @Autowired
    private DepotManagerBean depotManager;

    @Autowired
    private DataDepotBean dataDepotBean;

    @Autowired
    private LDAPSecurityService ldapSecurityService;
    
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

        if (ldapSecurityService.authentificationLdapCheck(login, password)) {

            isUserConnected = true;

            username = login;

            showMessage(FacesMessage.SEVERITY_INFO, "Bienvenu " + login + "!");

            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            PrimeFaces pf = PrimeFaces.current();
            pf.ajax().update("headerPanel");

        } else {
            showMessage(FacesMessage.SEVERITY_ERROR, "Erreur de connexiokn !");
        }

    }

    private void showMessage(FacesMessage.Severity messageType, String messageValue) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageType, "", messageValue));
        PrimeFaces pf = PrimeFaces.current();
        pf.ajax().update("messages");
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

    public String getItemMenuSelected() {
        return itemMenuSelected;
    }

    public void setItemMenuSelected(String itemMenuSelected) {
        this.itemMenuSelected = itemMenuSelected;
        if (itemMenuSelected.equals("Gestion du dépôt")) {
            depotManager.initComposant();
        } else if (itemMenuSelected.equals("Depot")) {
            dataDepotBean.initComposant();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
