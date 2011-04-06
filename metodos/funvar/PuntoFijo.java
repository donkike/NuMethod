package metodos.funvar;

import interfaz.NuMethodConstants;
import java.util.ArrayList;

public class PuntoFijo {

    /**
     * Flag de salida de la aplicacion. utilizado como indicador de las posibles salidas
     * del algoritmo de la Biseccion
     *
     * <p>
     * Posibles Estados:
     * <ol>
     * <li> ROOT: salida sin errores en el calculo
     * <li> ROOT_PLUS_ERROR: salida con errores de procesamiento
     * <li> ERROR: Se supero el numero de iteraciones, el algoritmo ha fallado
     * </ol>
     */
    private static int status;

    public static ArrayList<Double> Xn = new ArrayList<Double>(10);
    public static ArrayList<Double> Errors = new ArrayList<Double>(10);
    public static ArrayList<Double> Fxn = new ArrayList<Double>(10);
    public static ArrayList<Integer> Iters = new ArrayList<Integer>(10);

    /**
     * Metodo que retorna el status de la ultima ejecucion del algoritmo de
     * <br>Biseccion.
     *
     * <p>Este Metodo cuenta solo con 3 posibles resultados:
     * <ol>
     * <li> IS_ROOT
     * <li> IS_ROOT_PLUS_ERROR
     * <li> IS_ERROR
     * </ol>
     *
     * @return status, el indicador de la ultima ejecución
     */
    public static int getLatestStatus() {
        return status;
    }

    //Metodo que llena en la posicion correspondiente los arreglos con los datos que le pase el metodo
    private static void fillTempArrays(int iter, double xi, double fxi, double error) {
        if (Xn == null) {
            Xn = new ArrayList<Double>(10);
            Errors = new ArrayList<Double>(10);
            Fxn = new ArrayList<Double>(10);
            Iters = new ArrayList<Integer>(10);
        }

        Xn.add(iter, xi);
        Fxn.add(iter, fxi);
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
        Fxn = new ArrayList<Double>();
        Errors = new ArrayList<Double>();
        Iters = new ArrayList<Integer>();

        System.runFinalization();
        System.gc();
    }

    public static double g(double x) {
        return ManejadorMetodo.evalFunc("g", x);
    }

    public static double f(double x) {
        return ManejadorMetodo.evalFunc("f", x);
    }

    public static void print(int i, double x, double y, double e) {
        System.out.println(i + "   " + x + "   " + y + "   " + e);
    }

    public static double[] solucionar(double x0, double tol, int iter) {
        double[] res = new double[2];
        double x1, err;
        double y1 = f(x0);
        if (y1 == 0) {
            res[0] = x0;
            res[1] = 0;
            return res;
        }
        x1 = g(x0);
        err = tol + 1;
        for (int i = 0; y1 != 0 && err > tol && i <= iter; i++) {
            print(i, x1, y1, err);
            fillTempArrays(i, x1, y1, err);
            x1 = g(x0);
            y1 = f(x1);
            err = Math.abs(x1 - x0);
            x0 = x1;
            //System.out.println(x0);
        }
        if (y1 == 0) {
            res[0] = x1;
            res[1] = 0;
            System.out.println("se hallo una raiz en: " + res[0]);
            status = NuMethodConstants.IS_ROOT;
        } else if (err <= tol) {
            res[0] = x1;
            res[1] = err;
            System.out.println(res[0] + " con error de " + res[1]);
            status = NuMethodConstants.IS_ROOT_PLUS_ERROR;
        } else {
            status = NuMethodConstants.IS_ERROR;
            return res;
        }

        return res;
    }
}
