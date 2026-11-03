package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

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

    /**
     * Constructor vacío necesario para JAXB.
     */
    public Coche() {
    }

    /**
     * Crea un coche con todos sus datos.
     */
    public Coche(int id, String matriculaCoche, String marca, String modelo, List<String> extras) {
        this.id = id;
        this.matriculaCoche = matriculaCoche;
        this.marcaCoche = marca;
        this.modeloCoche = modelo;

        if (extras != null) {
            this.extrasCoche = extras;
        }
    }

    /** Devuelve el identificador del coche. */
    public int getId() {
        return id;
    }

    /** Cambia el identificador del coche. */
    public void setId(int id) {
        this.id = id;
    }

    /** Devuelve la matrícula. */
    public String getMatriculaCoche() {
        return matriculaCoche;
    }

    /** Cambia la matrícula. */
    public void setMatriculaCoche(String matriculaCoche) {
        this.matriculaCoche = matriculaCoche;
    }

    /** Devuelve la marca. */
    public String getMarca() {
        return marcaCoche;
    }

    /** Cambia la marca. */
    public void setMarca(String marca) {
        this.marcaCoche = marca;
    }

    /** Devuelve el modelo. */
    public String getModelo() {
        return modeloCoche;
    }

    /** Cambia el modelo. */
    public void setModelo(String modelo) {
        this.modeloCoche = modelo;
    }

    /** Devuelve la lista de extras. */
    public List<String> getExtrasCoche() {
        return extrasCoche;
    }

    /** Cambia la lista de extras. */
    public void setExtrasCoche(List<String> extrasCoche) {
        if (extrasCoche != null) {
            this.extrasCoche = extrasCoche;
        } else {
            this.extrasCoche = new ArrayList<>();
        }
    }
}
