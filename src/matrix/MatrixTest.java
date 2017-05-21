package matrix;

import fraction.Fraction;
import org.junit.Test;

import static matrix.MatrixCreate.createMatrix;
import static org.junit.Assert.*;

public class MatrixTest {
  private Fraction f1 = new Fraction(1);

  private Fraction f2 = new Fraction(2);
  private Fraction f3 = new Fraction(3);
  private Fraction f4 = new Fraction(4);
  private final Fraction[][] arr1 = {{f1, f2, f3, f4},
      {f2.negate(), f1, f4.negate(), f3},
      {f3, f4.negate(), f1.negate(), f2},
      {f4, f3, f2.negate(), f1.negate()}};
  private final Fraction[][] arr2 = {{f1, f2, f3, f4},
      {f2.negate(), f1, f4.negate(), f3}};
  private final Matrix m1 = new Matrix(arr1);
  private final Matrix m2 = new Matrix(arr2);

  @Test
  public void determinantTests() throws Exception {
    assertTrue(m1.determinant().equals(new Fraction(900)));
    assertTrue(m1.GaussDeterminant().equals(new Fraction(900)));
  }

  @Test
  public void solveSystem() throws Exception {
    LEQSSolution sol = m2.solveSystem();
    System.out.println(sol);
  }

  @Test
  public void matrixToStringTest() throws Exception {
    Matrix m = createMatrix();
    System.out.println(m);
  }

  @Test
  public void inverseTest() throws Exception {
    //Test that the inverse properties
    System.out.println(m1.getInverse());
    System.out.println(m1.multiply(m1.getInverse()));
    System.out.println(m1.getInverse().multiply(m1));
    Matrix m4 = m1.getInverse();
    System.out.println(m4.getInverse());
  }

}