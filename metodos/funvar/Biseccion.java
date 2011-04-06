package metodos.funvar;

/**
 * Esta clase representa el algoritmo de biseccion para resolver Ecuaciones no lineales
 * <br>
 * @author The NuMethod Team
 * @version 2.0 Alpha 1
 */
import interfaz.NuMethodConstants;
import java.util.ArrayList;

public class Biseccion {

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


    public static ArrayList<Double> Xi = new ArrayList<Double>(10);
    public static ArrayList<Double> Xs = new ArrayList<Double>(10);
    public static ArrayList<Double> Errors = new ArrayList<Double>(10);
    public static ArrayList<Double> Xm = new ArrayList<Double>(10);
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
    private static void fillTempArrays(int iter, double xi, double xs, double xm, double fxn, double error) {
        if (Xi == null) {
            Xi = new ArrayList<Double>(10);
            Xs = new ArrayList<Double>(10);
            Errors = new ArrayList<Double>(10);
            Xm = new ArrayList<Double>(10);
            Fxn = new ArrayList<Double>(10);
            Iters = new ArrayList<Integer>(10);
        }

        Xi.add(iter, xi);
        Xs.add(iter, xs);
        Xm.add(iter, xm);
        Fxn.add(iter, fxn);
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
        Xm = new ArrayList<Double>();
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

    public static double[] solucionar(double x0, double x1, int M, double maxError) {
        int i = 0;
        double f0 = f(x0), f1 = f(x1);
        double xn, xna, fn, err = maxError + 1;
        double[] res = new double[2];
        xn = (x0 + x1) / 2;
        fn = f(xn);
        while (fn != 0 && err >= maxError && i <= M) {
            print(i, x0, x1, xn, fn, err);
            //Llenar los arreglos en cada etapa
            fillTempArrays(i, x0, x1, xn, fn, err);
            xna = xn;
            if (f0 * fn > 0) {
                x0 = xn;
                f0 = fn;
            } else {
                x1 = xn;
                f1 = fn;
            }
            xn = (x0 + x1) / 2;
            fn = f(xn);
            err = Math.abs(xna - xn);
            i++;
        }
        print(i, x0, x1, xn, fn, err);
        
        //Colocando la ultima iteracion del metodo en los arreglos
        fillTempArrays(i, x0, x1, xn, fn, err);
        if (fn == 0) { // raíz sin error
            res[0] = xn;
            res[1] = 0;
            status = NuMethodConstants.IS_ROOT;
        } else if (err < maxError) { // raíz con error
            res[0] = xn;
            res[1] = err;
            System.out.println(res[0] + " con error de " + res[1]);
            status = NuMethodConstants.IS_ROOT_PLUS_ERROR;
        } else { //Error en el procesamiento
            System.out.println("Se supero el Numero de Iteraciones");
            status = NuMethodConstants.IS_ERROR;
            return null;
        }


        return res;
    }
}
