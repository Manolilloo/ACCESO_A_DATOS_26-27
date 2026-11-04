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
     *
     * @param id identificador del coche
     * @param matriculaCoche matrícula del coche
     * @param marca marca del vehículo
     * @param modelo modelo del vehículo
     * @param extras lista de extras del vehículo
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

    /** @return identificador del coche */
    public int getId() {
        return id;
    }

    /**
     * Cambia el identificador del coche.
     *
     * @param id nuevo identificador
     */
    public void setId(int id) {
        this.id = id;
    }

    /** @return matrícula del coche */
    public String getMatriculaCoche() {
        return matriculaCoche;
    }

    /**
     * Cambia la matrícula.
     *
     * @param matriculaCoche nueva matrícula
     */
    public void setMatriculaCoche(String matriculaCoche) {
        this.matriculaCoche = matriculaCoche;
    }

    /** @return marca del coche */
    public String getMarca() {
        return marcaCoche;
    }

    /**
     * Cambia la marca.
     *
     * @param marca nueva marca
     */
    public void setMarca(String marca) {
        this.marcaCoche = marca;
    }

    /** @return modelo del coche */
    public String getModelo() {
        return modeloCoche;
    }

    /**
     * Cambia el modelo.
     *
     * @param modelo nuevo modelo
     */
    public void setModelo(String modelo) {
        this.modeloCoche = modelo;
    }

    /** @return lista de extras del coche */
    public List<String> getExtrasCoche() {
        return extrasCoche;
    }

    /**
     * Cambia la lista de extras.
     *
     * @param extrasCoche nueva lista de extras
     */
    public void setExtrasCoche(List<String> extrasCoche) {
        if (extrasCoche != null) {
            this.extrasCoche = extrasCoche;
        } else {
            this.extrasCoche = new ArrayList<>();
        }
    }
}
