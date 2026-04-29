package exception;

public class TrabajadorException extends Exception {
    public static final String TRABAJADOR_EXISTENTE = "Ya existe un trabajador con ese DNI, Se ha actualizado los datos";


    public TrabajadorException(String mensaje) {
        super(mensaje);
    }
}
