

package metodos.sistec;

public class MatrizBanda {

    public static double[] solucionar(double[] dp, double[] di, double[] dss, double[] dst, double[] b) {
        int n = dp.length;
        for (int k = 0; k < n - 1; k++) {
            double m = di[k] / dp[k];
            dp[k + 1] = dp[k + 1] - m * dss[k];
            dss[k + 2] = dss[k + 2] - m * dst[k+1];
            b[k + 1] = b[k + 1] - m * b[k];
        }

        double[] x = new double[n];
        x[n - 1] = b[n - 1] / dp[n - 1];
        x[n - 2] = (b[n - 2] - dss[n - 2] * x[n - 1]) / dp[n - 2];
        for (int i = n - 3; i >= 0; i--) {
            x[i] = (b[i] - (dss[i] * x[i + 1] - dst[i] * x[i + 2])) / dp[i];
        }

        return x;
    }

}
