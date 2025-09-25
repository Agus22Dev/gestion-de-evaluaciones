package proyecto.evaluaciones.excepciones;

/**
 * Excepción personalizada para errores de persistencia de datos
 * como problemas al leer/escribir archivos CSV.
 * 
 * SIA2.9 - Segunda clase que extiende de una Excepción personalizada
 */
public class PersistenciaException extends Exception {
    
    private String archivo;
    private String operacion;
    
    public PersistenciaException(String mensaje) {
        super(mensaje);
    }
    
    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
    public PersistenciaException(String archivo, String operacion, String mensaje) {
        super(String.format("Error en %s del archivo '%s': %s", 
              operacion, archivo, mensaje));
        this.archivo = archivo;
        this.operacion = operacion;
    }
    
    public PersistenciaException(String archivo, String operacion, String mensaje, Throwable causa) {
        super(String.format("Error en %s del archivo '%s': %s", 
              operacion, archivo, mensaje), causa);
        this.archivo = archivo;
        this.operacion = operacion;
    }
    
    public String getArchivo() {
        return archivo;
    }
    
    public String getOperacion() {
        return operacion;
    }
    
    /**
     * Retorna un mensaje de error formateado para mostrar al usuario
     */
    public String getMensajeUsuario() {
        if (archivo != null && operacion != null) {
            return String.format("Error al %s el archivo %s. %s", 
                   operacion, archivo, getMessage());
        }
        return "Error de persistencia: " + getMessage();
    }
    
    /**
     * Determina si el error es recuperable (por ejemplo, archivo no encontrado)
     * vs no recuperable (permisos insuficientes)
     */
    public boolean esRecuperable() {
        if (getCause() instanceof java.io.FileNotFoundException) {
            return true;
        }
        if (getCause() instanceof java.io.IOException) {
            String mensaje = getCause().getMessage();
            return mensaje != null && mensaje.toLowerCase().contains("access denied");
        }
        return false;
    }
}