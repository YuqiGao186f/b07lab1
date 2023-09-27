import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Polynomial {
    private double[] coefficients;
    private int[] exponents;

    public Polynomial() {
        this.coefficients = new double[0];
        this.exponents = new int[0];
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        String polynomial = scanner.nextLine();
        scanner.close();

        String[] terms = polynomial.split("(?=[-+])");
        this.coefficients = new double[terms.length];
        this.exponents = new int[terms.length];

        for (int i = 0; i < terms.length; i++) {
            if (terms[i].contains("x")) {
                String[] parts = terms[i].split("x");
                this.coefficients[i] = parts[0].isEmpty() ? 1 : Double.parseDouble(parts[0]);
                this.exponents[i] = parts.length > 1 ? Integer.parseInt(parts[1].substring(1)) : 1;
            } else {
                this.coefficients[i] = Double.parseDouble(terms[i]);
                this.exponents[i] = 0;
            }
        }
    }

    public double evaluate(double x) {
        double result = 0.0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return Math.abs(evaluate(x)) < 1e-10;
    }

    public Polynomial add(Polynomial p) {
        // For simplicity, we'll just sum terms with like exponents. 
        double[] newCoefficients = new double[coefficients.length + p.coefficients.length];
        int[] newExponents = new int[exponents.length + p.exponents.length];

        int index = 0;
        for (int i = 0; i < coefficients.length; i++) {
            newCoefficients[index] = coefficients[i];
            newExponents[index] = exponents[i];
            index++;
        }

        for (int i = 0; i < p.coefficients.length; i++) {
            boolean found = false;
            for (int j = 0; j < exponents.length; j++) {
                if (p.exponents[i] == exponents[j]) {
                    newCoefficients[j] += p.coefficients[i];
                    found = true;
                    break;
                }
            }
            if (!found) {
                newCoefficients[index] = p.coefficients[i];
                newExponents[index] = p.exponents[i];
                index++;
            }
        }

        return new Polynomial(newCoefficients, newExponents);
    }

    public Polynomial multiply(Polynomial p) {
        // Multiply every term with every other term
        double[] newCoefficients = new double[coefficients.length * p.coefficients.length];
        int[] newExponents = new int[exponents.length * p.exponents.length];

        int index = 0;
        for (int i = 0; i < coefficients.length; i++) {
            for (int j = 0; j < p.coefficients.length; j++) {
                newCoefficients[index] = coefficients[i] * p.coefficients[j];
                newExponents[index] = exponents[i] + p.exponents[j];
                index++;
            }
        }

        // Combine terms with like exponents
        for (int i = 0; i < newExponents.length; i++) {
            for (int j = i + 1; j < newExponents.length; j++) {
                if (newExponents[i] == newExponents[j]) {
                    newCoefficients[i] += newCoefficients[j];
                    newCoefficients[j] = 0;
                }
            }
        }

        return new Polynomial(newCoefficients, newExponents);
    }

    public void saveToFile(String filename) throws IOException {
        PrintWriter writer = new PrintWriter(filename);
        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] != 0) {
                if (i != 0 && coefficients[i] > 0) {
                    writer.print("+");
                }
                if (exponents[i] == 0 || (coefficients[i] != 1 && coefficients[i] != -1)) {
                    writer.print(coefficients[i]);
                } else if (coefficients[i] == -1) {
                    writer.print("-");
                }
                if (exponents[i] != 0) {
                    writer.print("x");
                    if (exponents[i] != 1) {
                        writer.print("^" + exponents[i]);
                    }
                }
            }
        }
        writer.close();
    }
}
