

package funcion;

import java.util.HashMap;


public class ManejadorFuncion {

    private class DefFuncion {
        private Node node;
        private String var;
        public DefFuncion(String var, Node node) {
            this.node = node;
            this.var = var;
        }
        public String getVar() {
            return var;
        }
        public Node getNode() {
            return node;
        }
    }

    private HashMap<String,DefFuncion> funciones;
    private Parser p;
    private static ManejadorFuncion mf;

    public static ManejadorFuncion getInstance() {
        if (mf == null)
            mf = new ManejadorFuncion();
        return mf;
    }

    private ManejadorFuncion() {
        p = new Parser();
        funciones = new HashMap<String,DefFuncion>();
    }

    public void addFuncion(String name, String var, String exp) {
        p.setExpression(exp);
        funciones.put(name, new DefFuncion(var, p.beginParse()));
    }

    public double eval(String f, double val) {
        try {
        p.setVar(funciones.get(f).getVar(), val);
        return funciones.get(f).getNode().evaluar();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("=========> " + f);
            return Double.NaN;
        }
    }

    public static void main(String[] args) {
        getInstance().addFuncion("f", "x", "2*x*cos(2*x)-(x+1)^2");
        System.out.println(getInstance().eval("f", -0.79));
    }

}
