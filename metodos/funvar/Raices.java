package metodos.funvar;

import interfaz.NuMethodConstants;
import java.util.ArrayList;

public class Raices {

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

    public static ArrayList<Double> Xn = new ArrayList<Double>();
    public static ArrayList<Double> Yn = new ArrayList<Double>();
    public static ArrayList<Double> DYn = new ArrayList<Double>();
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
    private static void fillTempArrays(int iter, double xn, double yn, double dYn, double error) {
        if (Xn == null) {
            Xn = new ArrayList<Double>();
            Errors = new ArrayList<Double>();
            Yn = new ArrayList<Double>();
            DYn = new ArrayList<Double>();
            Iters = new ArrayList<Integer>();
        }

        Xn.add(iter, xn);
        Yn.add(iter, yn);
        DYn.add(iter, dYn);
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
        DYn = new ArrayList<Double>();
        Errors = new ArrayList<Double>();
        Iters = new ArrayList<Integer>();

        System.runFinalization();
        System.gc();
    }

    public static void print(int i, double x, double y, double dy, double err) {
        System.out.println(i + "   " + x + "   " + y + "   " + dy + "   " + err);
    }

    public static double f(double x) {
        return ManejadorMetodo.evalFunc("f", x);
    }

    public static double df(double x) {
        return ManejadorMetodo.evalFunc("df", x);
    }

    public static double ddf(double x) {
        return ManejadorMetodo.evalFunc("ddf", x);
    }

    public static double[] solucionar(double x0, double tol, int iter) {
        double[] result = new double[2];
        double y0 = f(x0);
        double dy0 = df(x0);
        double ddy0 = ddf(x0);
        double error = tol + 1;
        int cont = 0;
        double x1 = 0;
        while (y0 != 0 && error > tol && dy0 != 0 && cont < iter) {
            print(cont, x0, y0, dy0, error);
            fillTempArrays(cont, x0, y0, dy0, error);
            x1 = x0 - y0 * dy0 / (dy0 * dy0 - y0 * ddy0);
            y0 = f(x1);
            dy0 = df(x1);
            ddy0 = ddf(x0);
            error = Math.abs(x1 - x0);
            x0 = x1;
            cont = cont + 1;
        }
        print(cont, x0, y0, dy0, error);
        fillTempArrays(cont, x0, y0, dy0, error);
        if (y0 == 0) {
            result[0] = x0;
            result[1] = 0;
            System.out.println("Hay una raiz en: " + result[0]);
            status = NuMethodConstants.IS_ROOT;
        } else if (error < tol) {
            result[0] = x0;
            result[1] = error;
            System.out.println("Hay una raiz en: " + result[0] + " con un error de: " + result[1]);
            status = NuMethodConstants.IS_ROOT_PLUS_ERROR;
        } else if (dy0 == 0) {
            result = null;
            System.out.println("Error: la funcion encontro una division por 0");
            status = NuMethodConstants.IS_DIVISION_BY_ZERO;
        } else {
            result = null;
            System.out.println("Error: No se encontro raiz durante los intervalos pedidos");
            status =  NuMethodConstants.IS_ERROR;
        }
        return result;
    }
}
