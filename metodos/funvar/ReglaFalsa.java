package metodos.funvar;

import interfaz.NuMethodConstants;
import java.util.ArrayList;

public class ReglaFalsa {

    /**
     * Flag de salida de la aplicacion. utilizado como indicador de las posibles salidas
     * del algoritmo de las Busquedas Incrementales
     *
     * <p>
     * Posibles Estados:
     * <ol>
     * <li> ROOT: se encontro una raiz
     * <li> ROOT BETWEEN: Se encontro una posible raiz entre 2 valores
     * <li> ERROR: Se supero el numero de iteraciones, el algoritmo ha fallado
     * </ol>
     */
    private static int status;

    public static ArrayList<Double> Xi = new ArrayList<Double>(10);
    public static ArrayList<Double> Xs = new ArrayList<Double>(10);
    public static ArrayList<Double> Xn = new ArrayList<Double>(10);
    public static ArrayList<Double> Fxn = new ArrayList<Double>(10);
    public static ArrayList<Double> Errors = new ArrayList<Double>(10);
    public static ArrayList<Integer> Iters = new ArrayList<Integer>(10);

    /**
     * Metodo que retorna el status de la ultima ejecucion del algoritmo de
     * <br>la Regla Falsa.
     *
     * <p>Este Metodo cuenta solo con 3 posibles resultados:
     * <ol>
     * <li> IS_ROOT : Se encontro una raiz en el procesamiento
     * <li> IS_ROOT_PLUS_ERROR :Hay una raiz con un error
     * <li> IS_ERROR : se supero el numero maximo de iteraciones
     * </ol>
     *
     * @return status, el indicador de la ultima ejecución
     */
    public static int getLatestStatus() {
        return status;
    }

    ///Metodo que llena en la posicion correspondiente los arreglos con los datos que le pase el metodo
    private static void fillTempArrays(int iter, double xi, double xs, double xn, double yi, double error) {
        if (Xi == null) {
            Xi = new ArrayList<Double>(10);
            Xs = new ArrayList<Double>(10);
            Xn = new ArrayList<Double>(10);
            Fxn = new ArrayList<Double>(10);
            Iters = new ArrayList<Integer>(10);
        }

        Xi.add(iter, xi);
        Xs.add(iter, xs);
        Xn.add(iter, xn);
        Fxn.add(iter, yi);
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
        Xi = new ArrayList<Double>();
        Xs = new ArrayList<Double>();
        Xn = new ArrayList<Double>();
        Fxn = new ArrayList<Double>();
        Errors = new ArrayList<Double>();
        Iters = new ArrayList<Integer>();

        System.runFinalization();
        System.gc();
    }

    public static void print(int i, double x1, double x2, double xn, double y, double err) {
        System.out.println(i + "   " + x1 + "   " + x2 + "   " + xn + "   " + y + "   " + err);
    }

    public static double f(double x) {
        return ManejadorMetodo.evalFunc("f", x);
    }

    public static double[] solucionar(double xi, double xs, int M, double maxError) {
        int i = 0;
        double f0 = f(xi);
        double f1 = f(xs);
        double xn, xna, fn, err = maxError + 1;
        double[] res = new double[2];
        xn = xi - (((f0) * (xs - xi)) / (f1 - f0));
        fn = f(xn);
        while (fn != 0 && err >= maxError && i <= M) {
            print(i, xi, xs, xn, fn, err);
            fillTempArrays(i, xi, xs, xn, fn, err);
            xna = xn;
            if (f0 * fn > 0) {
                xi = xn;
                f0 = fn;
            } else {
                xs = xn;
                f1 = fn;
            }
            xn = xi - (((f0) * (xs - xi)) / (f1 - f0));
            fn = f(xn);
            err = Math.abs(xna - xn);
            i++;
        }
        print(i, xi, xs, xn, fn, err);
        fillTempArrays(i, xi, xs, xn, fn, err);
        if (fn == 0) {
            res[0] = xn;
            res[1] = 0;
            status = NuMethodConstants.IS_ROOT;
        } else if (err < maxError) {
            res[0] = xn;
            res[1] = err;
            System.out.println(res[0] + " con error de " + res[1]);
            status = NuMethodConstants.IS_ROOT_PLUS_ERROR;
        } else {
            System.out.println("Se supero el # de iteraciones");
            status = NuMethodConstants.IS_ERROR;
        }
        return null;
    }
}
