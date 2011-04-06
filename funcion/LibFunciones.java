
package funcion;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * Librería de funciones que pueden ser utilizadas por el parser. Se puede extender.
 */

public class LibFunciones {

    private static LibFunciones lib;
    private ArrayList<Funcion> funciones;
    private HashMap<String, Funcion> mapFuncion;
    private int count;

    private LibFunciones() {
        funciones = new ArrayList<Funcion>();
        mapFuncion = new HashMap<String, Funcion>();
        //Suma suma = new Suma();
        funciones.add(suma);
        funciones.add(resta);
        funciones.add(mul);
        funciones.add(div);
        funciones.add(pow);
        funciones.add(neg);
        funciones.add(sin);
        funciones.add(cos);
        funciones.add(tan);
        funciones.add(ln);
        funciones.add(log);
        mapFuncion.put(suma.getSim(), suma);
        mapFuncion.put(resta.getSim(), resta);
        mapFuncion.put(mul.getSim(), mul);
        mapFuncion.put(div.getSim(), div);
        mapFuncion.put(pow.getSim(), pow);
        mapFuncion.put(neg.getSim(), neg);
        mapFuncion.put(sin.getSim(), sin);
        mapFuncion.put(cos.getSim(), cos);
        mapFuncion.put(tan.getSim(), tan);
        mapFuncion.put(ln.getSim(), ln);
        mapFuncion.put(log.getSim(), log);
        count = 11;
    }

    private Funcion suma = new Funcion("+", 2) {
        public double eval(double op1, double op2) {
            return op1 + op2;
        }
    };

    private Funcion resta = new Funcion("-", 2) {
        public double eval(double op1, double op2) {
            return op1 - op2;
        }
    };

    private Funcion mul = new Funcion("*", 2) {
        public double eval(double op1, double op2) {
            return op1 * op2;
        }
    };

    private Funcion div = new Funcion("/", 2) {
        public double eval(double op1, double op2) {
            return op1 / op2;
        }
    };

    private Funcion pow = new Funcion("^", 2) {
        public double eval(double op1, double op2) {
            return Math.pow(op1, op2);
        }
    };

    private Funcion neg = new Funcion("less", 1) {
        public double eval(double op) {
            return -op;
        }
    };

    private Funcion sin = new Funcion("sin", 1) {
        public double eval(double op) {
            return Math.sin(op);
        }
    };

    private Funcion cos = new Funcion("cos", 1) {
        public double eval(double op) {
            return Math.cos(op);
        }
    };

    private Funcion tan = new Funcion("tan", 1) {
        public double eval(double op) {
            return Math.tan(op);
        }
    };

    private Funcion ln = new Funcion("ln", 1) {
        public double eval(double op) {
            return Math.log(op);
        }
    };

    private Funcion log = new Funcion("log", 1) {
        public double eval(double op) {
            return Math.log10(op);
        }
    };

    public int getCount() {
        return count;
    }

    public void addFuncion(Funcion f) {
        funciones.add(f);
        mapFuncion.put(f.getSim(), f);
        count++;
    }
    public ArrayList<Funcion> getFunciones() {
        return funciones;
    }

    public static LibFunciones getLib() {
        if (lib == null) {
            lib = new LibFunciones();
        }
        return lib;
    }

}
