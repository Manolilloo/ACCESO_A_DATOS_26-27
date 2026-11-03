package model;

import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "concesionario-de-coches")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"coches"})
public class Concesionario {

    @XmlElementWrapper(name = "listado-coches")
    @XmlElement(name = "coche")
    private List<Coche> coches = new ArrayList<>();

    public Concesionario() {
    }

    public Concesionario(List<Coche> coches) {
        this.coches = coches != null ? coches : new ArrayList<>();
    }

    public List<Coche> getCoches() {
        if (coches == null) {
            coches = new ArrayList<>();
        }
        return coches;
    }

    public void setCoches(List<Coche> coches) {
        this.coches = coches != null ? coches : new ArrayList<>();
    }
}
