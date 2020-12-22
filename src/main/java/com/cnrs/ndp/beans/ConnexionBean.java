package com.cnrs.ndp.beans;

import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;


@Named(value = "connexionBean")
@SessionScoped
public class ConnexionBean implements Serializable {

    @Autowired
    private DepotManagerBean depotManager;

    @Autowired
    private DataDepotBean dataDepotBean;
    
    private boolean isUserConnected;
    private String login, password;
    private String itemMenuSelected;
    
    

    @PostConstruct
    public void initComposant() {
        
        isUserConnected = false;
        login = "";
        password = "";
        
    }
    
    public void connexion() throws IOException {
        isUserConnected = true;
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        PrimeFaces pf = PrimeFaces.current();
        pf.ajax().update("headerPanel");
    }
    
    public void deconnexion() {
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

}
