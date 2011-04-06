

package funcion;

/*
 * Nodo para números
 */

public class NNode implements Node {

    private double val;

    public NNode(double val) {
        this.val = val;
    }

    public double evaluar() {
        return val;
    }

}
