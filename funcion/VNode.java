
package funcion;

/*
 * Nodo para variables
 */

public class VNode implements Node {

    private String var;    

    public VNode(String var) {
        this.var = var;
    }

    public double evaluar() {
        return Parser.getVars().get(var);
    }

}
