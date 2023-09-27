import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        try {
            // 1. Default Constructor Test
            Polynomial p = new Polynomial();
            assert p.evaluate(3) == 0.0 : "Default constructor test failed!";

            // 2. Array Constructor Test
            double[] c1 = {6, 5};
            int[] e1 = {0, 3};
            Polynomial p1 = new Polynomial(c1, e1);
            assert p1.evaluate(1) == 11.0 : "Array constructor test failed!";

            // 3. Add method Test
            double[] c2 = {-2, -9};
            int[] e2 = {1, 4};
            Polynomial p2 = new Polynomial(c2, e2);
            Polynomial s = p1.add(p2);
            assert s.evaluate(0.1) == 6.195 : "Add method test failed!";

            // 4. hasRoot method Test
            assert !s.hasRoot(1) : "hasRoot method test failed for non-root!";
            assert new Polynomial(new double[]{1, -2, 1}, new int[]{0, 1, 2}).hasRoot(1) : "hasRoot method test failed for root!";

            // 5. multiply method Test
            Polynomial product = p1.multiply(p2);
            assert product.evaluate(1) == -2.0 : "Multiply method test failed for x = 1!";
            assert product.evaluate(2) == -32.0 : "Multiply method test failed for x = 2!";
            assert product.evaluate(3) == -162.0 : "Multiply method test failed for x = 3!";

            // 6. File constructor and saveToFile method Test
            p1.saveToFile("poly.txt");
            Polynomial pFromFile = new Polynomial(new File("poly.txt"));
            assert pFromFile.evaluate(1) == 11.0 : "File constructor test failed!";
            assert pFromFile.evaluate(2) == 44.0 : "File constructor test failed!";

            // 7. Edge case: Polynomial with only one term
            Polynomial singleTermPoly = new Polynomial(new double[]{-5}, new int[]{7});
            assert singleTermPoly.evaluate(1) == -5.0 : "Single term polynomial test failed!";

            // 8. Edge case: Zero polynomial multiplication
            Polynomial zeroPoly = new Polynomial();
            Polynomial resultPoly = p1.multiply(zeroPoly);
            assert resultPoly.evaluate(3) == 0.0 : "Zero polynomial multiplication test failed!";

            // 9. Large coefficients and exponents
            double[] c3 = {1e20, 1e20};
            int[] e3 = {1000, 2000};
            Polynomial p3 = new Polynomial(c3, e3);
            assert p3.evaluate(2) == 1e23 : "Large coefficients and exponents test failed!";

            // 10. Floating point precision
            Polynomial nearZeroPoly = new Polynomial(new double[]{1e-10}, new int[]{1});
            assert nearZeroPoly.hasRoot(0) : "Floating point precision test failed!";

            // 11. Dense vs. Sparse Polynomials
            double[] cDense = {1, 2, 3, 4, 5};
            int[] eDense = {1, 2, 3, 4, 5};
            Polynomial densePoly = new Polynomial(cDense, eDense);
            assert densePoly.evaluate(1) != 0 : "Dense polynomial test failed!";

            double[] cSparse = {1, 2};
            int[] eSparse = {1, 1000};
            Polynomial sparsePoly = new Polynomial(cSparse, eSparse);
            assert sparsePoly.evaluate(1) != 0 : "Sparse polynomial test failed!";

            // 12. Polynomial with zero coefficient
            double[] cZero = {0, 5};
            int[] eZero = {2, 3};
            Polynomial zeroCoeffPoly = new Polynomial(cZero, eZero);
            assert zeroCoeffPoly.evaluate(1) == 5 : "Zero coefficient polynomial test failed!";

            System.out.println("All tests passed!");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception ex) {
            System.out.println("Unexpected error: " + ex.getMessage());
        }
    }
}
