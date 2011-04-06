
package funcion;

/*
 * Nodo para funciones
 */

public class FNode implements Node {

    private Funcion f;
    private Node op1;
    private Node op2;

    public FNode(Funcion f, Node op1, Node op2) {
        this.f = f;
        this.op1 = op1;
        this.op2 = op2;
    }

    public FNode(Funcion f, Node op) {
        this.f = f;
        op1 = op;
        op2 = null;
    }

    public double evaluar() {
        if (op2 != null)
            return f.eval(op1.evaluar(), op2.evaluar());
        else
            return f.eval(op1.evaluar());
    }

    public Funcion getFuncion() {
        return f;
    }

    public Node getOp1() {
        return op1;
    }

    public Node getOp2() {
        return op2;
    }

}
