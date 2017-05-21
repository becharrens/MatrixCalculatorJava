package matrix;

import fraction.Fraction;

import javax.naming.OperationNotSupportedException;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

public class Matrix {
  private static final Fraction one = new Fraction(1);
  private static final Fraction zero = new Fraction(0);
  private final Fraction[][] matrix;

  public Matrix(Fraction[][] matrix) {
    if (matrix == null) {
      throw new InvalidParameterException("Matrix can't be null");
    }
    this.matrix = matrix;
  }

  public int nRows() {
    return matrix.length;
  }

  public int nCols() {
    return matrix[0].length;
  }

  public Fraction get(int r, int c) {
    if (r < 0 || r >= matrix.length || c < 0 && c >= matrix[r].length) {
      throw new ArrayIndexOutOfBoundsException();
    }
    return matrix[r][c];
  }

  //Arithmetic Operations

  public Matrix add(Matrix other) throws OperationNotSupportedException {
    //Adds the given matrix from the given one if their dimensions match
    if (other == null) {
      return null;
    }
    if (other.nRows() != nRows() || other.nCols() != nCols()) {
      throw new OperationNotSupportedException("Matrix dimensions don't match");
    }
    Fraction[][] res = new Fraction[nRows()][nCols()];
    for (int i = 0; i < nRows(); i++) {
      for (int j = 0; j < nCols(); j++) {
        res[i][j] = get(i, j).add(other.get(i,j));
      }
    }
    return new Matrix(res);
  }

  public Matrix subtract(Matrix other) throws OperationNotSupportedException {
    //Subtracts the given matrix from the given one if their dimensions match
    if (other == null) {
      return null;
    }
    if (other.nRows() != nRows() || other.nCols() != nCols()) {
      throw new OperationNotSupportedException("Matrix dimensions don't match");
    }
    Fraction[][] res = new Fraction[nRows()][nCols()];
    for (int i = 0; i < nRows(); i++) {
      for (int j = 0; j < nCols(); j++) {
        res[i][j] = get(i, j).subtract(other.get(i,j));
      }
    }
    return new Matrix(res);
  }

  public Matrix scalarMult(Fraction k) {
    //Multiplies matrix by a scalar
    Fraction[][] res = new Fraction[nRows()][nCols()];
    for (int i = 0; i < nRows(); i++) {
      for (int j = 0; j < nCols(); j++) {
        res[i][j] = get(i, j).multiply(k);
      }
    }
    return new Matrix(res);
  }

  public Matrix multiply(Matrix other) throws OperationNotSupportedException {
    //If the operation is defined, it multiplies the two matrices according
    //to the algorithm
    if (other == null) {
      return null;
    }
    if (other.nRows() != nCols()) {
      throw new OperationNotSupportedException("Adjacent matrix" +
          " dimensions don't match");
    }
    Fraction[][] res = new Fraction[nRows()][other.nCols()];
    Fraction sum;
    for (int i = 0; i < nRows(); i++) {
      for (int j = 0; j < other.nCols(); j++) {
        sum = zero;
        for (int k = 0; k < nCols(); k++) {
          sum = sum.add(get(i, k).multiply(other.get(k, j)));
        }
        res[i][j] = sum;
      }
    }
    return new Matrix(res);
  }

  public Matrix transpose() {
    //Transposes the matrix, it writes its rows as columns;
    Fraction[][] res = new Fraction[nCols()][nRows()];
    for (int i = 0; i < nRows(); i++) {
      for (int j = 0; j < nCols(); j++) {
        res[j][i] = get(i, j);
      }
    }
    return new Matrix(res);
  }

  public static Matrix identity(int order) {
    //Returns the identity matrix of the specified order
    //Throws error if order < 1
    if (order < 1) {
      throw new NegativeArraySizeException();
    }
    Fraction[][] res = new Fraction[order][order];
    for (int i = 0; i < order; i++) {
      for (int j = 0; j < order; j++) {
        res[i][j] = (i == j) ? one : zero;
      }
    }
    return new Matrix(res);
  }

  //Determinant
  public Fraction determinant() throws OperationNotSupportedException {
    if (matrix.length != matrix[0].length) {
      throw new
          OperationNotSupportedException("Determinant is " +
          "only defined on square matrices");
    }
    return determinantHelper(matrix);
  }

  private Fraction determinantHelper(Fraction[][] m) {
    //Calculate determinant recursively by applying Laplace expansion
    if (m.length == 1) {
      return m[0][0];
    }
    //Apply Laplace Expansion along first row
    Fraction adj;
    Fraction det = zero;
    Fraction[][] minor = new Fraction[m.length - 1][m.length - 1];
    for (int i = 0; i < m.length; i++) {
      //Copy elements onto the matrix to calculate the determinant
      for (int r = 1; r < m.length; r++) {
        for (int c = 0; c < m.length; c++) {
          if (c < i) {
            minor[r - 1][c] = m[r][c];
          } else if (c > i) {
            minor[r - 1][c - 1] = m[r][c];
          }
        }
      }
      adj = m[0][i];
      //Negate if i + j odd, i.e. i is odd, because i == 0
      if (i % 2 == 1) {
        adj = adj.negate();
      }
      //Calculate determinant recursively
       adj = adj.multiply(determinantHelper(minor));
      det = det.add(adj);
    }
    return det;
  }

  public Fraction GaussDeterminant() throws OperationNotSupportedException {
    //Triangulate matrix by Gaussian elimination so that the determinant can be
    //easily calculated as the product of the diagonal
    if (matrix.length != matrix[0].length) {
      throw new
          OperationNotSupportedException("Determinant is " +
          "only defined on square matrices");
    }
    Fraction[][] matrixCopy = arrayCopy();
    boolean pivotFound = false;
    //Fraction to multiply by pivot to cancel elements
    Fraction scalar;
    for (int i = 0; i < matrix.length; i++) {
      //If pivot is 0, look for a row which doesn't have a zero in that column
      if (matrixCopy[i][i].getNum() == 0) {
        for (int j = i; j < matrixCopy.length; j++) {
          if (matrixCopy[j][i].getNum() == 0) {
            swapRows(matrixCopy, i, j);
            pivotFound = true;
            break;
          }
        }
        //If there isn't one, then the determinant is 0
        if (!pivotFound) {
          return zero;
        }
      }

      //Apply Gaussian elimination to make all elements under the pivot zero
      for (int r = i + 1; r < matrixCopy.length; r++) {
        if (matrixCopy[r][i].getNum() != 0) {
          scalar = matrixCopy[r][i].negate().divide(matrixCopy[i][i]);
          for (int c = i; c < matrixCopy[0].length; c++) {
            matrixCopy[r][c] = matrixCopy[r][c].add(matrixCopy[i][c]
                .multiply(scalar));
          }
        }
      }
    }
    //Matrix is now triangular, so the determinant is the product of the
    //elements in the diagonal
    Fraction det = one;
    for (int i = 0; i < matrixCopy.length; i++) {
      det = det.multiply(matrixCopy[i][i]);
    }
    return det;
  }

  public Matrix getInverse() throws OperationNotSupportedException {
    if (nRows() != nCols()) {
      throw new OperationNotSupportedException("Non-square matrices don't " +
          "have an inverse");
    }
    Matrix identity = identity(nRows());
    Fraction[][] gaussMatrix = new Fraction[nRows()][2 * nCols()];
    for (int i = 0; i < nRows(); i++) {
      System.arraycopy(matrix[i], 0, gaussMatrix[i], 0, nCols());
      System.arraycopy(identity.matrix[i], 0, gaussMatrix[i], nCols(), nCols());
    }

    boolean pivotFound = false;
    //Fraction to multiply by pivot to cancel elements
    Fraction scalar;
    for (int i = 0; i < nRows(); i++) {
      //If pivot is 0, look for a row which doesn't have a zero in that column
      if (gaussMatrix[i][i].getNum() == 0) {
        for (int j = i + 1; j < nRows(); j++) {
          if (gaussMatrix[j][i].getNum() == 0) {
            swapRows(gaussMatrix, i, j);
            pivotFound = true;
            break;
          }
        }
        //If there isn't one, then the determinant is 0 and the inverse
        //doesn't exist
        if (!pivotFound) {
          return null;
        }
      }

      //Make pivot 1 and update row accordingly if necessary
      if (!gaussMatrix[i][i].equals(one)) {
        scalar = gaussMatrix[i][i].reciprocal();
        for (int j = i; j < gaussMatrix[i].length; j++) {
          gaussMatrix[i][j] = gaussMatrix[i][j].multiply(scalar);
        }
      }

      //Apply Gaussian elimination to make all elements under the pivot zero
      for (int r = 0; r < gaussMatrix.length; r++) {
        if (r == i) {
          continue;
        }
        if (gaussMatrix[r][i].getNum() != 0) {
          scalar = gaussMatrix[r][i].negate();
          for (int c = i; c < gaussMatrix[r].length; c++) {
            gaussMatrix[r][c] = gaussMatrix[r][c].add(gaussMatrix[i][c]
                .multiply(scalar));
          }
        }
      }
    }
    Fraction[][] res = new Fraction[nRows()][nCols()];
    for (int i = 0; i < nRows(); i++) {
      System.arraycopy(gaussMatrix[i], nCols(), res[i], 0, nCols());
    }
    return new Matrix(res);
  }

  public LEQSSolution solveSystem() {
    //Pre: matrix is a valid LEQS (all elements are no null,
    // and the matrix itself is not null)

    //Solves LEQS, returning all its solutions, if any, or null otherwise.
    //Aplies Gaussian elimination
    int pivotCol = 0;
    int pivotRow = 0;
    Fraction[][] matrixCopy = arrayCopy();
    Set<Integer> nonPivots = new HashSet<>();
    boolean pivotFound = false;

    //Fraction to multiply by pivot to cancel elements
    int nPivots = Math.min(matrixCopy.length, matrixCopy[0].length - 1);
    Fraction scalar;

    while ((pivotCol < matrixCopy[0].length - 1) && pivotRow < matrixCopy.length) {
      //If pivot is 0, look for a row which doesn't have a zero in that column
      if (matrixCopy[pivotRow][pivotCol].getNum() == 0) {
        for (int j = pivotRow + 1; j < matrixCopy.length; j++) {
          if (matrixCopy[j][pivotCol].getNum() == 0) {
            swapRows(matrixCopy, pivotRow, j);
            pivotFound = true;
            break;
          }
        }
        //If there isn't one, then there is no pivot in this column, and the
        //loop must continue with the next iteration
        if (!pivotFound) {
          nonPivots.add(pivotCol);
          pivotCol++;
          continue;
        }
      }

      //Make pivot 1 and update row accordingly if necessary
      if (!matrixCopy[pivotRow][pivotCol].equals(one)) {
        scalar = matrixCopy[pivotRow][pivotCol].reciprocal();
        for (int j = pivotCol; j < matrixCopy[pivotRow].length; j++) {
          matrixCopy[pivotRow][j] = matrixCopy[pivotRow][j].multiply(scalar);
        }
      }

      //Use row operations to make everything under and above the pivot 0
      for (int r = 0; r < matrixCopy.length; r++) {
        if (r == pivotRow) {
          continue;
        }
        if (matrixCopy[r][pivotCol].getNum() != 0) {
          scalar = matrixCopy[r][pivotCol].negate();
          for (int c = pivotCol; c < matrixCopy[0].length; c++) {
            matrixCopy[r][c] = matrixCopy[r][c].add(matrixCopy[pivotRow][c]
                .multiply(scalar));
          }
        }
      }
      pivotCol++;
      pivotRow++;
    }

    //Check to see if the system is incompatible
    if (incompatibleCheck(matrixCopy)) {
      System.out.println("Incompatible System, no solution");
      return null;
    }

    //Add any possible columns which haven't been covered during the #
    //Gaussian elimination. Only required when you have less equations than
    //variables
    for (int i = pivotCol; i < matrixCopy[0].length - 1; i++) {
      nonPivots.add(i);
    }

    //The LEQS is now in RREF

    Fraction[] particular =
        new Fraction[matrixCopy[0].length - 1];
    Fraction[][] general =
        new Fraction[nonPivots.size()][matrixCopy[0].length - 1];
    int offset = 0;
    Fraction negOne = new Fraction(-1);

    //Read off the particular solution for Ax = b
    for (int i = 0; i < matrixCopy[0].length - 1; i++) {
      if (nonPivots.contains(i)) {
        particular[i] = zero;
        offset--;
      } else if (i > matrixCopy.length) {
        particular[i] = zero;
      } else {
        particular[i] = matrixCopy[i + offset][matrixCopy[0].length - 1];
      }
    }

    //Apply -1 trick and get non trivial solutions for Ax = 0
    int solIdx = 0;
    for (Integer c : nonPivots) {
      offset = 0;
      for (int i = 0; i < matrixCopy[0].length - 1; i++) {
        if (nonPivots.contains(i)) {
          if (i == c) {
            general[solIdx][i] = negOne;
          } else {
            general[solIdx][i] = zero;
          }
          offset--;
        } else if (i > matrixCopy.length) {
          general[solIdx][i] = zero;
        } else {
          general[solIdx][i + offset] = matrixCopy[i][c];
        }
      }
      solIdx++;
    }
    return new LEQSSolution(particular, general);
  }

  private static boolean incompatibleCheck(Fraction[][] augmentedSys) {
    //incompatible system when 0 times all variables gives something other than
    //zero. (Inconsistent system)
    boolean allZeros;
    for (int i = augmentedSys.length - 1; i >= 0; i--) {
      allZeros = true;
      for (int j = augmentedSys[0].length - 2; j >= 0; j--) {
        if (augmentedSys[i][j].getNum() != 0) {
          allZeros = false;
          break;
        }
      }
      if (allZeros &&
          augmentedSys[i][augmentedSys[0].length - 1].getNum() != 0) {
        return true;
      }
    }
    return false;
  }

  private static void swapRows(Fraction[][] arr, int r1, int r2) {
    //Pre: r1 and r2 are within bounds of the array and array is not jagged
    if (r1 == r2) {
      return;
    }
    Fraction temp;
    for (int c = 0; c < arr[0].length; c++) {
      temp = arr[r1][c];
      arr[r1][c] = arr[r2][c];
      arr[r2][c] = temp;
    }
  }

  public Fraction[][] arrayCopy() {
    //Copy the matrix into a new array to prevent changes in the matrix to be
    //reflected on the stored matrix
    Fraction[][] matrixCopy = new Fraction[matrix.length][matrix[0].length];
    for (int i = 0; i < matrix.length; i++) {
      System.arraycopy(matrix[i], 0, matrixCopy[i], 0, matrix[0].length);
    }
    return matrixCopy;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append('[');
    for (int r = 0; r < matrix.length; r++) {
      sb.append('[');
      for (int c = 0; c < matrix[r].length; c++) {
        sb.append(matrix[r][c]);
        if (c < matrix[r].length - 1) {
          sb.append(", ");
        }
      }
      sb.append(']');
      if (r < matrix.length - 1) {
        sb.append('\n');
      }
    }
    sb.append(']');
    return sb.toString();
  }
}
