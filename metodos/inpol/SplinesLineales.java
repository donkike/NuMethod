
package metodos.inpol;

public class SplinesLineales {

    private static Ecuacion[] splines;
    
    /*
     * Clase que representa una ecuación de la forma:
     * Y = a * X + B    limInf <= X < limSup
     */
    public static class Ecuacion {
        private double limInf, limSup, a, b;
        public Ecuacion(double a1, double b1, double li, double ls) {
            limInf = li;
            limSup = ls;
            a = a1;
            b = b1;
        }
        public double getLimInf() { return limInf; }
        public double getLimSup() { return limSup; }
        public double getA() { return a; }
        public double getB() { return b; }
    }

    public static Ecuacion[] solucionar(double[] xs, double[] ys) {
        int n = xs.length;
        splines = new Ecuacion[n-1];

        for (int i = 0; i < n-1; i++) {
            double m = (ys[i+1] - ys[i]) / (xs[i+1] - xs[i]);
            double b = ys[i] - xs[i] * m;
            splines[i] = new Ecuacion(m, b, xs[i], xs[i+1]);
        }

        return splines;
    }


}
