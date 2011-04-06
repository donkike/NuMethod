
package metodos.inpol;

import metodos.Util;

public class SplinesCuadraticos {

    public static double[][] splines;
    public static double[] ind;

    public static void empalme(double x, double y, int i, int ec) {
        for (int exp = 2, j = ec * 3; exp >= 0; exp--, j++) {
            splines[i][j] = Math.pow(x, exp);
            splines[i+1][j+3] = Math.pow(x, exp);
        }
        splines[i][splines[i].length-1] = y;
        splines[i+1][splines[i].length-1] = y;
    }

    public static void empalmeExtremo(double x, double y, int i, boolean first) {
        if (first) {
            for (int exp = 2, j = 0; exp >= 0; exp--, j++) {
                splines[i][j] = Math.pow(x, exp);
            }
            splines[i][splines[i].length-1] = y;
        } else {
            for (int exp = 2, j = splines[i].length-4; exp >= 0; exp--, j++) {
                splines[i][j] = Math.pow(x, exp);
            }
            splines[i][splines[i].length-1] = y;
            
        }
    }

    public static void empalmeDerivada(double x, double y, int i, int ec) {
        for (int exp = 1, j = ec*3; exp >= 0; exp--, j++) {
            splines[i][j] = (exp+1) * Math.pow(x, exp);
            splines[i][j+3] = -(exp+1) * Math.pow(x, exp);
        }        
    }

    public static double[][] solucionar(double[] xs, double[] ys) {
        int n = xs.length;
        splines = new double[3*(n-1)][3*(n-1)+1];
        ind = new double[3*(n-1)];
        int ec = 1, i = 0;
        while (ec < n-1) {
            empalme(xs[ec], ys[ec], i, ec-1);
            //TODO verificar
            //Estas lineas agregan los valores al arreglo de datos independientes
            ind[i] = ys[ec];
            ind[i + 1] = ys[ec];
            ec++;
            i += 2;
        }
        empalmeExtremo(xs[0], ys[0], i++, true);
        ind[i - 1] = ys[0];
        empalmeExtremo(xs[n-1], ys[n-1], i++, false);
        ind[i - 1] = ys[n-1];
        ec = 1;
        while (ec < n-1) {
            empalmeDerivada(xs[ec], ys[ec], i, ec-1);
            ind[i] = 0;
            ec++;
            i++;
        }
        splines[i++][0] = 1;
        ind[i - 1] = 0;
        return splines;
    }


}
