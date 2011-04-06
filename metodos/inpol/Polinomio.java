

package metodos.inpol;


public class Polinomio {

    private double[] coeficientes;

    public Polinomio() {
        coeficientes = new double[1];
        coeficientes[0] = 1;
    }

    public Polinomio(double[] c) {
        coeficientes = c;
    }

    public Polinomio dividir(double c) {
        double res[] = new double[getGrado()];
        for (int i = 0; i < getGrado(); i++)
                res[i] = coeficientes[i] / c;
        return new Polinomio(res);            
    }

    public Polinomio multiplicar(Polinomio p) {
        int n = getGrado(), m = p.getGrado();
        double[] res = new double[n + m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res[i+j] += getCoeficientes()[i] * p.getCoeficientes()[j];
            }
        }
        return new Polinomio(res);
    }

    public double evaluar(double x) {
        double acum = 0;
        for (int i = 0; i < getGrado(); i++) {
            acum += coeficientes[i] * Math.pow(x, i);
        }
        return acum;
    }

    public double[] getCoeficientes() {
        return coeficientes;
    }

    public int getGrado() {
        return coeficientes.length;
    }

}
