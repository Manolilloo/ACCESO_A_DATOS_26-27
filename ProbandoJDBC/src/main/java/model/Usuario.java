/**
 * @author ManuelPerez
 * @version 1.0
 */

package model;

public class Usuario {
    private int id;
    private String nombre;
    private String pwd;

    public Usuario(int id, String nombre, String pwd) {
        this.id = id;
        this.nombre = nombre;
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                ", nombre='" + nombre + '\'' +
                ", pwd='" + pwd + '\'' +
                "id=" + id +
                '}';
    }
}
