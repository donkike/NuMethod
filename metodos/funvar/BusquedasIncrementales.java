package metodos.funvar;

import interfaz.NuMethodConstants;
import java.util.ArrayList;

public class BusquedasIncrementales {

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
    public static ArrayList<Double> Fxi = new ArrayList<Double>(10);
    public static ArrayList<Integer> Iters = new ArrayList<Integer>(10);

    /**
     * Metodo que retorna el status de la ultima ejecucion del algoritmo de
     * <br>busquedas Incrementales.
     *
     * <p>Este Metodo cuenta solo con 3 posibles resultados:
     * <ol>
     * <li> IS_ROOT : Se encontro una raiz en el procesamiento
     * <li> IS_ROOT_BETWEEN : Se hallo una raiz entre 2 valores
     * <li> IS_ERROR : se supero el numero maximo de iteraciones
     * </ol>
     *
     * @return status, el indicador de la ultima ejecución
     */
    public static int getLatestStatus() {
        return status;
    }

    //Metodo que llena en la posicion correspondiente los arreglos con los datos que le pase el metodo
    private static void fillTempArrays(int iter, double xi, double yi) {
        if (Xi == null) {
            Xi = new ArrayList<Double>(10);
            Fxi = new ArrayList<Double>(10);
            Iters = new ArrayList<Integer>(10);
        }

        Xi.add(iter, xi);
        Fxi.add(iter, yi);
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
        Fxi = new ArrayList<Double>();
        Iters = new ArrayList<Integer>();

        System.runFinalization();
        System.gc();
    }

    public static void print(int i, double x, double y) {
        System.out.println(i + "   " + x + "   " + y);
    }

    public static double f(double x) {
        return ManejadorMetodo.evalFunc("f", x);
    }

    public static double[] solucionar(double x0, double delta, int iter) {
        double x1, f1;
        int i = 0;
        double f0 = f(x0);
        double[] res;
        if (f0 == 0) { // x0 es raiz
            res = new double[1];
            res[0] = x0;
            return res;
        }
        x1 = x0 + delta;
        f1 = f(x1);
        fillTempArrays(i, x1, f1);
        i++;
        while (f0 * f1 > 0 && i <= iter) {
            print(i, x1, f1);
            fillTempArrays(i, x1, f1);
            x0 = x1;
            f0 = f1;
            x1 = x0 + delta;
            f1 = f(x1);
            i++;
        }
        print(i, x1, f1);
        fillTempArrays(i, x1, f1);
        if (f1 == 0) { // x1 es raiz
            System.out.println("Hay una raíz en x = " + x1);
            res = new double[1];
            res[0] = x1;
            status = NuMethodConstants.IS_ROOT;
            return res;
        }
        if (f0 * f1 < 0) { // Existe raiz en [x0,x1]
            System.out.println("Hay una raíz en el intervalo [" + x0 + "," + x1 + "]");
            res = new double[2];
            res[0] = x0;
            res[1] = x1;
            status = NuMethodConstants.IS_ROOT_BETWEEN;
            return res;
        }
        System.out.println("No se encontró un intervalo con raíz.");
        status = NuMethodConstants.IS_ERROR;
        return null; // No se encontró el intervalo
    }
}
