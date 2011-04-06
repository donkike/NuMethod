
package funcion;

/*
 * Clase representa una función.
 */

public class Funcion {

    protected String sim; // símbolo de la función
    protected int numParams; // número de parámetros

    public Funcion() {}

    public Funcion(String sim, int numParams) {
        this.sim = sim;
        this.numParams = numParams;
    }

    public String getSim() {
        return sim;
    }

    public int getNumParams() {
        return numParams;
    }

    /*
     * Evaluación de función con 1 variable
     */

    public double eval(double op) {
        return -1;
    }

    /*
     * Evaluación de función con 2 variables
     */

    public double eval(double op1, double op2) {
        return -1;
    }

    /*
     * Evaluación de función con n variables
     */

    public double eval(double[] params) {
        return -1;
    }


}
