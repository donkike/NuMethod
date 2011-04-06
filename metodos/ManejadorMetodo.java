
package metodos;

import funcion.ManejadorFuncion;

public class ManejadorMetodo {

 
    public static double evalFunc(String f, double val) {
        return ManejadorFuncion.getInstance().eval(f, val);
    }


}
