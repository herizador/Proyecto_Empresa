/**
 *
 */
package modelo;

/**
 * @author alumno
 */
public class Trabajador {
    private static String SEPARADOR = ";";
    private String dni;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String puesto;

    /**
     * @param dni
     * @param nombre
     * @param apellidos
     * @param direccion
     * @param telefono
     * @param puesto
     */
    public Trabajador(String dni, String nombre, String apellidos, String direccion, String telefono, String puesto) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.puesto = puesto;
    }

    public String toStringWithSeparators() {
        return this.dni + SEPARADOR + this.nombre + SEPARADOR + this.apellidos + SEPARADOR + this.direccion + SEPARADOR + this.telefono + SEPARADOR + this.puesto;
    }

    /**
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * @param dni the dni to set
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the puesto
     */
    public String getPuesto() {
        return puesto;
    }

    /**
     * @param puesto the puesto to set
     */
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    @Override
    public String toString() {
        return "Trabajador{" + "dni='" + dni + '\'' + ", nombre='" + nombre + '\'' + ", apellidos='" + apellidos + '\'' + ", direccion='" + direccion + '\'' + ", telefono='" + telefono + '\'' + ", puesto='" + puesto + '\'' + '}';
    }
}
