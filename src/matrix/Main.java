package matrix;

import javax.naming.OperationNotSupportedException;

import static matrix.MatrixCreate.createMatrix;

public class Main {
  public static void main(String[] args) {
    Matrix m = createMatrix();
    Matrix m2 = createMatrix();
    try {
      System.out.println(m.multiply(m2));
    } catch (OperationNotSupportedException e) {
      System.out.println(e.getMessage());
    }
  }
}
