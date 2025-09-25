package proyecto.evaluaciones.excepciones;

/**
 * Excepción personalizada para errores relacionados con la validación de datos
 * en el sistema de evaluaciones.
 * 
 * SIA2.9 - Clase que extiende de una Excepción personalizada
 */
public class ValidacionException extends Exception {
    
    private String campo;
    private String valorInvalido;
    
    public ValidacionException(String mensaje) {
        super(mensaje);
    }
    
    public ValidacionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
    public ValidacionException(String campo, String valorInvalido, String mensaje) {
        super(String.format("Error en campo '%s' con valor '%s': %s", 
              campo, valorInvalido, mensaje));
        this.campo = campo;
        this.valorInvalido = valorInvalido;
    }
    
    public String getCampo() {
        return campo;
    }
    
    public String getValorInvalido() {
        return valorInvalido;
    }
    
    /**
     * Retorna un mensaje de error formateado para mostrar al usuario
     */
    public String getMensajeUsuario() {
        if (campo != null) {
            return String.format("El campo '%s' tiene un valor inválido. %s", 
                   campo, getMessage());
        }
        return getMessage();
    }
}