
package funcion;
/*
 * Excepción cuando no sea posible parsear una expresión
 */

public class ParserException extends Exception {

    public ParserException(String msg) {
        super("No se pudo parsear la expresión: " + msg);
    }

}
