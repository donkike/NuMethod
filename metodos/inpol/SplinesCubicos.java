
package metodos.inpol;

import metodos.Util;
import metodos.sistec.GaussianaPivParcial;

public class SplinesCubicos {

    private static double[][] splines;
    public static double[] ind;

    public static void empalme(double x, double y, int i, int ec) {
        for (int exp = 3, j = ec * 4; exp >= 0; exp--, j++) {
            splines[i][j] = Math.pow(x, exp);
            splines[i+1][j+4] = Math.pow(x, exp);
        }
        splines[i][splines[i].length-1] = y;
        splines[i+1][splines[i].length-1] = y;
    }

    public static void empalmeExtremo(double x, double y, int i, boolean first) {
        if (first) {
            for (int exp = 3, j = 0; exp >= 0; exp--, j++) {
                splines[i][j] = Math.pow(x, exp);
            }
            splines[i][splines[i].length - 1] = y;
        } else {
            for (int exp = 3, j = splines[i].length - 5; exp >= 0; exp--, j++) {
                splines[i][j] = Math.pow(x, exp);
            }
            splines[i][splines[i].length - 1] = y;
            
        }
    }

    public static void empalmePrimeraDerivada(double x, double y, int i, int ec) {
        for (int exp = 2, j = ec * 4; exp >= 0; exp--, j++) {
            splines[i][j] = (exp + 1) * Math.pow(x, exp);
            splines[i][j + 4] = - (exp + 1) * Math.pow(x, exp);
        }
    }

    public static void empalmeSegundaDerivada(double x, double y, int i, int ec) {
        int j = ec * 4;
        splines[i][j] = 6 * x;
        splines[i][j + 1] = 2;
        splines[i][j + 4] = -6 * x;
        splines[i][j + 5] = -2;
    }

    public static void empalmeExtremoSegundaDerivada(double x, double y, int i, boolean first) {
        if (first) {
            splines[i][0] = 6 * x;
            splines[i][1] = 2;
        } else {
            splines[i][splines[i].length - 5] = 6 * x;
            splines[i][splines[i].length - 4] = 2;
        }
    }

    public static double[][] solucionar(double[] xs, double[] ys) {
        // TODO ordenar por elementos de xs de menor a mayor
        int n = xs.length;
        splines = new double[4*(n-1)][4*(n-1) + 1];
        ind = new double[4*(n-1)];
        int ec = 1, i = 0;
        while (ec < n-1) {
            empalme(xs[ec], ys[ec], i, ec-1);
            ind[i] = ys[ec];
            ind[i + 1] = ys[ec];
            i += 2;
            ec++;
        }
        empalmeExtremo(xs[0], ys[0], i++, true);
        ind[i - 1] = ys[0];
        empalmeExtremo(xs[n-1], ys[n-1], i++, false);
        ind[i - 1] = ys[n-1];
        ec = 1;
        while (ec < n-1) {
            empalmePrimeraDerivada(xs[ec], ys[ec], i, ec-1);
            ind[i] = 0;
            i++;
            ec++;
        }
        ec = 1;
        while (ec < n-1) {
            empalmeSegundaDerivada(xs[ec], ys[ec], i, ec-1);
            ind[i] = 0;
            ec++;
            i++;
        }
        empalmeExtremoSegundaDerivada(xs[0], ys[0], i++, true);
        ind[i-1] = 0;
        empalmeExtremoSegundaDerivada(xs[n-1], ys[n-1], i++, false);
        ind[i-1] = 0;
        return splines;
    }


}
