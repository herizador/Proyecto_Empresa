package exception;

public class TrabajadorException extends Exception {
    public static final String TRABAJADOR_EXISTENTE = "Ya existe un trabajador con ese DNI, Se ha actualizado los datos";
    public static final String TRABAJADOR_INEXISTENTE = "No existe un trabajador con ese DNI";
    public static final String TRABAJADORES_NO_ENCONTRADOS = "No se encontro trabajador/es con ese filtro";

    public TrabajadorException(String mensaje) {
        super(mensaje);
    }
}
