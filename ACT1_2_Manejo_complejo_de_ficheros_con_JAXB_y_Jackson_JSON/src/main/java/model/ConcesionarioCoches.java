package model;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "concesionario-de-coches")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConcesionarioCoches {

    @XmlElementWrapper(name = "listado-coches")
    @XmlElement(name = "coche")
    private List<Coche> coches;

    public ConcesionarioCoches(List<Coche> coches) {
        this.coches = coches;
    }

    public List<Coche> getCoches() {
        return coches;
    }
    public void setCoches(List<Coche> coches) {
        this.coches = coches;
    }
}
