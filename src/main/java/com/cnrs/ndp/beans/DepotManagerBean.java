package com.cnrs.ndp.beans;


import com.cnrs.ndp.entity.Depots;
import com.cnrs.ndp.repository.DepotsRepository;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;


@Named(value = "depotManagerBean")
@SessionScoped
public class DepotManagerBean implements Serializable {

    @Autowired
    private DepotsRepository depotsRepository;
    
    private List<Depots> depotsList;
    private Depots depotSelected;

    
    public void initComposant() {
        depotsList = depotsRepository.findAll();
    }
    
    public void modifierDepot() {
        depotsRepository.save(depotSelected);
    }

    public void annuler() {
        depotSelected = depotsRepository.findById(depotSelected.getId()).get();
    }
    
    public List<Depots> getDepotsList() {
        return depotsList;
    }

    public void setDepotsList(List<Depots> depotsList) {
        this.depotsList = depotsList;
    }

    public Depots getDepotSelected() {
        return depotSelected;
    }

    public void setDepotSelected(Depots depotSelected) {
        this.depotSelected = depotSelected;
    }
    
}
