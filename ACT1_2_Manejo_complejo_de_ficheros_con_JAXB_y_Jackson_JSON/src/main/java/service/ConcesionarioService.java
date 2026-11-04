package service;

import model.Coche;
import model.Concesionario;
import jakarta.xml.bind.JAXBException;

import java.util.List;

/**
 * Reúne las operaciones principales del concesionario.
 */
public class ConcesionarioService {

    private Concesionario concesionario;
    private XmlService xmlService;

    /**
     * Crea el servicio con el concesionario y el servicio XML.
     *
     * @param concesionario concesionario que se va a gestionar
     * @param xmlService servicio encargado de guardar los cambios
     */
    public ConcesionarioService(Concesionario concesionario, XmlService xmlService) {
        this.concesionario = concesionario;
        this.xmlService = xmlService;
    }

    /**
     * Busca un coche por su matrícula.
     *
     * @param matricula matrícula del coche
     * @return coche encontrado o null si no existe
     */
    public Coche buscarCoche(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            return null;
        }

        for (Coche coche : concesionario.getCoches()) {
            if (coche != null && coche.getMatriculaCoche() != null
                    && coche.getMatriculaCoche().equalsIgnoreCase(matricula.trim())) {
                return coche;
            }
        }
        return null;
    }

    /**
     * Inserta un coche nuevo si su matrícula no está registrada.
     *
     * @param coche coche que se quiere insertar
     * @return true si se ha insertado, false si ya existe
     * @throws JAXBException si ocurre un error al guardar el XML
     * @throws IllegalArgumentException si el coche contiene datos obligatorios no válidos
     */
    public boolean insertarCoche(Coche coche) throws JAXBException {
        validarCoche(coche);

        if (buscarCoche(coche.getMatriculaCoche()) != null) {
            return false;
        }

        int nuevoId = 1;

        for (Coche cocheActual : concesionario.getCoches()) {
            if (cocheActual.getId() >= nuevoId) {
                nuevoId = cocheActual.getId() + 1;
            }
        }

        coche.setId(nuevoId);
        concesionario.getCoches().add(coche);
        xmlService.guardarConcesionario(concesionario);

        return true;
    }

    /**
     * Borra un coche utilizando su matrícula.
     *
     * @param matricula matrícula del coche que se quiere borrar
     * @return true si se ha borrado, false si no existe
     * @throws JAXBException si ocurre un error al guardar el XML
     */
    public boolean borrarCoche(String matricula) throws JAXBException {
        Coche coche = buscarCoche(matricula);

        if (coche == null) {
            return false;
        }

        concesionario.getCoches().remove(coche);
        xmlService.guardarConcesionario(concesionario);

        return true;
    }

    /**
     * Modifica los datos de un coche sin cambiar su matrícula.
     *
     * @param matricula matrícula del coche que se quiere modificar
     * @param marca nueva marca
     * @param modelo nuevo modelo
     * @param extras nueva lista de extras
     * @return true si se ha modificado, false si no existe
     * @throws JAXBException si ocurre un error al guardar el XML
     * @throws IllegalArgumentException si la marca o el modelo están vacíos
     */
    public boolean modificarCoche(String matricula, String marca, String modelo,
                                  List<String> extras) throws JAXBException {
        Coche coche = buscarCoche(matricula);

        if (coche == null) {
            return false;
        }

        if (marca == null || marca.trim().isEmpty()
                || modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("La marca y el modelo no pueden estar vacíos.");
        }

        coche.setMarca(marca.trim());
        coche.setModelo(modelo.trim());
        coche.setExtrasCoche(extras);

        xmlService.guardarConcesionario(concesionario);

        return true;
    }

    /**
     * Ordena los coches por matrícula.
     *
     * @throws JAXBException si ocurre un error al guardar el XML
     */
    public void ordenarPorMatricula() throws JAXBException {
        List<Coche> coches = concesionario.getCoches();

        for (int i = 0; i < coches.size() - 1; i++) {
            for (int j = i + 1; j < coches.size(); j++) {
                Coche coche1 = coches.get(i);
                Coche coche2 = coches.get(j);

                if (coche1.getMatriculaCoche().compareToIgnoreCase(coche2.getMatriculaCoche()) > 0) {
                    coches.set(i, coche2);
                    coches.set(j, coche1);
                }
            }
        }

        xmlService.guardarConcesionario(concesionario);
    }

    /**
     * Sustituye los coches actuales por los de otro concesionario.
     *
     * @param nuevo concesionario que se quiere importar
     * @throws JAXBException si ocurre un error al guardar el XML
     * @throws IllegalArgumentException si hay coches incompletos o matrículas duplicadas
     */
    public void reemplazarConcesionario(Concesionario nuevo) throws JAXBException {
        if (nuevo == null) {
            throw new IllegalArgumentException("El concesionario importado está vacío.");
        }

        List<Coche> cochesNuevos = nuevo.getCoches();

        for (Coche coche : cochesNuevos) {
            validarCoche(coche);
        }

        for (int i = 0; i < cochesNuevos.size(); i++) {
            for (int j = i + 1; j < cochesNuevos.size(); j++) {
                String matricula1 = cochesNuevos.get(i).getMatriculaCoche();
                String matricula2 = cochesNuevos.get(j).getMatriculaCoche();

                if (matricula1.equalsIgnoreCase(matricula2)) {
                    throw new IllegalArgumentException(
                            "El JSON contiene matrículas duplicadas: " + matricula1
                    );
                }
            }
        }

        concesionario.setCoches(cochesNuevos);
        xmlService.guardarConcesionario(concesionario);
    }

    /**
     * Comprueba que un coche tenga todos los datos obligatorios.
     *
     * @param coche coche que se va a validar
     * @throws IllegalArgumentException si falta algún dato obligatorio
     */
    private void validarCoche(Coche coche) {
        if (coche == null) {
            throw new IllegalArgumentException("El coche no puede estar vacío.");
        }

        if (coche.getMatriculaCoche() == null || coche.getMatriculaCoche().trim().isEmpty()) {
            throw new IllegalArgumentException("La matrícula no puede estar vacía.");
        }

        if (coche.getMarca() == null || coche.getMarca().trim().isEmpty()) {
            throw new IllegalArgumentException("La marca no puede estar vacía.");
        }

        if (coche.getModelo() == null || coche.getModelo().trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo no puede estar vacío.");
        }

        if (coche.getExtrasCoche() != null) {
            for (String extra : coche.getExtrasCoche()) {
                if (extra == null || extra.trim().isEmpty()) {
                    throw new IllegalArgumentException("La lista de extras contiene un valor vacío.");
                }
            }
        }
    }
}
