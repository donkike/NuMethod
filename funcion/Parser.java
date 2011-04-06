

package funcion;

import java.util.HashMap;
import java.util.Stack;

/*
 * Clase principal para el manejo del parser.
 * Reailza el procedimiento de parsear y evaular la función definida.
 */

public class Parser {

    private LibFunciones lib; // librería de funciones
    private static HashMap<String, Double> vars; // hash con los valores de variables
    private String exp; // expresión a parsear

    public Parser() {
        lib = LibFunciones.getLib();
        vars = new HashMap<String, Double>();
        vars.put("pi", Math.PI);
        vars.put("e", Math.E);
    }

    /*
     * Establece la expresión a parsear.
     */

    public void setExpression(String exp) {
        this.exp = exp;
    }

    /*
     * Comienza el parseo de la expresión definida.
     */

    public Node beginParse() {
        try {
            if (exp == null)
                throw new ParserException("La expresión no ha sido definida");
            return parse(exp);
        } catch (ParserException pe) {
            System.out.println("\n" + pe.getMessage());
        }
        return null;
    }


    /*
     * Parsea la expresión ingresada.
     */

    private Node parse(String exp) {
        System.out.println(exp);
        try {
        if (exp.charAt(0) == '(') {
            int pars = 1;
            int i;
            for (i = 1; pars > 0 && i < exp.length(); i++) {
                if (exp.charAt(i) == '(')
                    pars++;
                if (exp.charAt(i) == ')')
                    pars--;
            }
            if (i == exp.length()) {
                if (pars == 0)
                    return parse(exp.substring(1, exp.length()-1));
                else
                    throw new ParserException(exp + ". Paréntesis desbalanceados");
            }
        }
        for (int i = 1; i < exp.length() - 2; i++) {
            if (exp.charAt(i) == '(') {
                int x = i;
                int pars = 1;
                i++;
                while (pars != 0 && i < exp.length()) {
                    if (exp.charAt(i) == '(')
                        pars++;
                    if (exp.charAt(i) == ')')
                        pars--;
                    i++;
                }
                if (pars == 0 && i == exp.length()) {
                    String fName = exp.substring(0, x);
                    for (int j = 5; j < lib.getCount(); j++) {
                        if (fName.equals(lib.getFunciones().get(j).getSim()))
                            return new FNode(lib.getFunciones().get(j), parse(exp.substring(x+1, exp.length()-1)));
                    }
                } else {
                    break;
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < exp.length(); j++) {
                if (exp.charAt(j) == '(') {
                    int pars = 1;
                    j++;
                    while (pars > 0 && j < exp.length()) {
                        if (exp.charAt(j) == '(') 
                            pars++;
                        if (exp.charAt(j) == ')') 
                            pars--;
                        j++;
                    }
                    if (j == exp.length())
                        break;
                }
                if (exp.charAt(j) == lib.getFunciones().get(i).getSim().charAt(0)) {
                    return new FNode(lib.getFunciones().get(i), parse(exp.substring(0, j)), parse(exp.substring(j+1, exp.length())));
                }
            }
            
        }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(exp);
            return null;
        }
        try {
            double val = Double.parseDouble(exp);
            return new NNode(val);
        } catch (NumberFormatException nfe) {
            return new VNode(exp);
        }
    }

    /*
     * Establece el valor de una variable.
     */

    public void setVar(String name, double val) {
        vars.put(name, val);
    }

    /*
     * Elimina los espacios en blanco de la expresión.
     */

    public String eliminarEspacios(String exp) {
        String nExp = "";
        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) != ' ')
                nExp += exp.charAt(i);
        }
        return nExp;
    }

    public static HashMap<String, Double> getVars() {
        return vars;
    }
}
