
package metodos.sistec;

public class GaussSeidel {

    private static double[][] A;
    private static double[] b;

    public static double[] solucionar(double[][] A, double[] b, double[] V, double tol, int iter) {
        GaussSeidel.A = A;
        GaussSeidel.b = b;
        int n = V.length;
        int cont = 0;
        double err = tol + 1;
        while (err > tol && cont <= iter) {
            LibreriaBase.imprimirVector(V);
            double[] aux = V.clone();
            for (int i = 0; i < n; i++) 
                V[i] = g(V, i);
            err = Math.abs(LibreriaBase.norma(V) - LibreriaBase.norma(aux));
            cont++;
        }
        if (err <= tol)
            return V;
        else
            return null;
    }

    public static double g(double[] V, int i) {
        double sum1 = 0;
        for (int p = 0; p < i; p++)
            sum1 += A[i][p] * V[p];
        double sum2 = 0;
        for (int q = i + 1; q < V.length; q++)
            sum2 += A[i][q] * V[q];
        double x = (b[i] - sum1 - sum2) / A[i][i];
        return x;
    }

}
