package metodos.funvar;
import interfaz.NuMethodConstants;
import java.util.ArrayList;

/**
 * Clase que representa el algoritmo de la secante para resolver ecuaciones no lineales
 * @author The NuMethod Team
 *
 * @version 2.0 Alpha 1
 */
public class Secante {
    /**
     * Flag de salida de la aplicacion.
     * 
     * <p>
     * Este flag puede contener 1 de 4 Estados posibles
     * <ol>
     * <li> IS_ROOT: salida sin errores
     * <li> IS_ROOT_PLUS_ERROR: salida mas un error de calculo en el procesamiento
     * <li> IS_DIVISION_BY_ZERO: Se hallo un error en el procesamiento debido a una division por cero
     * <li> IS_ERROR: Hubo un error, no se logro encontrar un resultado con el numero de iteraciones pedidas
     * </ol> 
     */
    private static int status; ///El posible estado de la ultima ejecucion del algoritmo

    //TODO construir documentacion para estas cosas
    public static ArrayList<Double> Xn = new ArrayList<Double>();
    public static ArrayList<Double> Yn = new ArrayList<Double>();
    public static ArrayList<Double> Errors = new ArrayList<Double>();
    public static ArrayList<Integer> Iters = new ArrayList<Integer>();

    /**
     * Metodo que devuelve el estado de la ultima ejecucion
     * @return el resultado de la ultima ejecucion
     * <br> el cual puede ser IS_ROOT, IS_ROOT_PLUS_ERROR, IS_DIVISION_BY_ZERO o IS_ERROR
     */
    public static int getLatestStatus() {
        return status;
    }

    //Metodo que llena en la posicion correspondiente los arreglos con los datos
    private static void fillTempArrays(int iter, double xn, double yn, double error) {
        if (Xn == null) {
            Xn = new ArrayList<Double>(10);
            Errors = new ArrayList<Double>(10);
            Yn = new ArrayList<Double>(10);
            Iters = new ArrayList<Integer>(10);
        }

        Xn.add(iter, xn);
        Yn.add(iter, yn);
        Errors.add(iter, error);
        Iters.add(iter, iter);
    }

    /**
     * Metodo que destruye los datos de la ultima ejecucion.
     * <p>Este metodo es utilizado para garantizar que los arreglos no contengan residuos
     * <br>de ejecuciones anteriores
     * <p>WARNING: antes de utilizar este metodo asegurarse de copiar los datos necesarios a otros contenedores.
     */
    public static void flushTempArrays() {
        Xn = new ArrayList<Double>();
        Yn = new ArrayList<Double>();
        Errors = new ArrayList<Double>();
        Iters = new ArrayList<Integer>();

        System.runFinalization();
        System.gc();
    }
    
    public static void print(int i, double x, double y, double err) {
        System.out.println(i + "   " + x + "   " + y + "   " + err);
    }

    public static double f(double x) {
        return ManejadorMetodo.evalFunc("f", x);
    }

    public static double[] solucionar(double x0, double x1, double tol, int iter) {
        double[] result = new double[2];
        double y1 = f(x1);
        double y0 = f(x0);
        if (y0 == 0) {
            result[0] = x0;
            result[1] = 0;
            System.out.println(x0 + " es una raiz");
        } else {
            double x2 = 0;
            double error = tol + 1;
            int cont = 0;
            double den = y1 - y0;
            while (y1 != 0 && error > tol && den != 0 && cont < iter) {
                print(cont, x1, y1, error);
                fillTempArrays(cont, x1, y1, error);
                x2 = x1 - (y1 * (x1 - x0)) / den;
                y1 = f(x2);
                error = Math.abs(x1 - x0);
                den = y1 - y0;
                x0 = x1;
                y0 = y1;
                x1 = x2;
                cont = cont + 1;
            }
            print(cont, x1, y1, error);
            fillTempArrays(cont, x1, y1, error);
            if (y1 == 0) {
                result[0] = x0;
                result[1] = 0;
                System.out.println("Hay una raiz en: " + result[0]);
                status = NuMethodConstants.IS_ROOT;
            } else if (error < tol) {
                result[0] = x0;
                result[1] = error;
                System.out.println("Hay una raiz en: " + result[0] + " con un error de: " + result[1]);
                status = NuMethodConstants.IS_ROOT_PLUS_ERROR;
            } else if (den == 0) {
                result = null;
                System.out.println("Error: la funcion encontro una division por 0");
                status = NuMethodConstants.IS_DIVISION_BY_ZERO;
            } else {
                result = null;
                System.out.println("Error: No se encontro raiz durante los intervalos pedidos");
                status = NuMethodConstants.IS_ERROR;
            }

        }
        return result;
    }
}
