package service;

import config.Configuracion;
import model.Concesionario;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

/**
 * Se encarga de leer y guardar el concesionario en XML.
 */
public class XmlService {

    private Configuracion configuracion;

    /**
     * Crea el servicio XML con la configuración de las rutas.
     *
     * @param configuracion configuración de los archivos
     */
    public XmlService(Configuracion configuracion) {
        this.configuracion = configuracion;
    }

    /**
     * Carga el concesionario desde el archivo XML.
     *
     * @return concesionario con sus coches
     * @throws JAXBException si ocurre un error al leer el XML
     */
    public Concesionario cargarConcesionario() throws JAXBException {

        File archivo = new File(configuracion.getRutaXML());

        JAXBContext contexto = JAXBContext.newInstance(Concesionario.class);
        Unmarshaller lector = contexto.createUnmarshaller();

        return (Concesionario) lector.unmarshal(archivo);
    }

    /**
     * Guarda el concesionario en el archivo XML.
     *
     * @param concesionario concesionario que se quiere guardar
     * @throws JAXBException si ocurre un error al generar el XML
     */
    public void guardarConcesionario(Concesionario concesionario) throws JAXBException {

        File archivo = new File(configuracion.getRutaXML());

        JAXBContext contexto = JAXBContext.newInstance(Concesionario.class);
        Marshaller escritor = contexto.createMarshaller();

        escritor.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        escritor.marshal(concesionario, archivo);
    }
}
