package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "concesionario-de-coches")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"coches"})
public class Concesionario {

    @XmlElementWrapper(name = "listado-coches")
    @XmlElement(name = "coche")
    private List<Coche> coches = new ArrayList<>();

    /**
     * Constructor vacío necesario para JAXB.
     */
    public Concesionario() {
    }

    /**
     * Crea un concesionario con una lista de coches.
     */
    public Concesionario(List<Coche> coches) {
        if (coches != null) {
            this.coches = coches;
        }
    }

    /** Devuelve la lista de coches. */
    public List<Coche> getCoches() {
        return coches;
    }

    /** Cambia la lista de coches. */
    public void setCoches(List<Coche> coches) {
        if (coches != null) {
            this.coches = coches;
        } else {
            this.coches = new ArrayList<>();
        }
    }
}
