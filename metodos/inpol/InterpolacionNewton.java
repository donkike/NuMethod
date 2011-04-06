

package metodos.inpol;

public class InterpolacionNewton {

    private static double[] b;
    private static boolean[][] yaCalculado;     // ¿El valor ya fue calculado?
    private static double[][] calculoPrevio;    // Valor previamente calculado
    private static double[] x;
    private static double[] fx;

    public static double[] solucionar(double[] x, double[] fx) {
        int n = x.length;
        b = new double[n];
        yaCalculado = new boolean[n][n];
        calculoPrevio = new double[n][n];
        InterpolacionNewton.x = x;
        InterpolacionNewton.fx = fx;
        b[0] = fx[0];
        diferenciasDivididas(x.length-1, 0);
        return b;
    }

    public static double diferenciasDivididas(int i, int k) {
        if (i == k) return fx[i];
        double f1 = 0;
        if (yaCalculado[i-1][k]) {
            f1 = calculoPrevio[i-1][k];
        } else {
            f1 = diferenciasDivididas(i-1, k);
        }
        double f2 = 0;
        if (yaCalculado[i][k+1]) {
            f2 = calculoPrevio[i][k+1];
        } else {
            f2 = diferenciasDivididas(i, k+1);
        }
        double dd = (f1 - f2) / (x[k] - x[i]);
        yaCalculado[i][k] = true;
        calculoPrevio[i][k] = dd;
        if (k == 0)     // b[i] = f[i,0]
            b[i] = dd;
        return dd;
    }

    public static double calcularValor(double x, double[] b, double[] xs) {
        double res = 0;
        for (int i = 0; i < b.length; i++) {
            double acum = b[i];
            for (int j = 0; j < i; j++)
                acum *=  (x - xs[j]);
            res += acum;
        }
        return res;
    }


}
