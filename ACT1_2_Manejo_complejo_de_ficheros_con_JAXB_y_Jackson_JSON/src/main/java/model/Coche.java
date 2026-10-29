package model;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "coche")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"matriculaCoche", "marcaCoche", "modeloCoche", "extrasCoche"})
public class Coche {

    @XmlAttribute(name = "id")
    private int id;

    @XmlElement(name = "matricula-coche")
    private String matriculaCoche;

    @XmlElement(name = "marca")
    private String marcaCoche;

    @XmlElement(name = "modelo")
    private String modeloCoche;

    @XmlElementWrapper(name = "equipamiento")
    @XmlElement(name = "extra")
    private List<String> extrasCoche = new ArrayList<>();

    public Coche() {
    }

    public Coche(int id, String matriculaCoche, String marca, String modelo, List<String> extras) {
        this.id = id;
        this.matriculaCoche = matriculaCoche;
        this.marcaCoche = marca;
        this.modeloCoche = modelo;
        this.extrasCoche = extras;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatriculaCoche() {
        return matriculaCoche;
    }

    public void setMatriculaCoche(String matriculaCoche) {
        this.matriculaCoche = matriculaCoche;
    }

    public String getMarca() {
        return marcaCoche;
    }

    public void setMarca(String marca) {
        this.marcaCoche = marca;
    }

    public String getModelo() {
        return modeloCoche;
    }

    public void setModelo(String modelo) {
        this.modeloCoche = modelo;
    }

    public List<String> getExtrasCoche() {
        return extrasCoche;
    }

    public void setExtrasCoche(List<String> extrasCoche) {
        this.extrasCoche = extrasCoche;
    }
}