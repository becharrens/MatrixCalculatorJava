package matrix;

import fraction.DivisionByZeroException;
import fraction.Fraction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MatrixCreate {
  public static Matrix createMatrix() {
    //Returns a matrix of the specified dimensions or null if either dimension
    //is 0
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("How many rows would you like the matrix to have?");
    int nRows = getIntegerIO(false, br);
    System.out.println("How many columns would you like the matrix to have?");
    int nCols = getIntegerIO(false, br);
    if (nRows == 0 || nCols == 0) {
      return null;
    }

    //NOTE: Currently allows input of as many numbers as desired as long as
    //there are at least as many numbers as specified
    System.out.println("Please enter the elements one row at a time, " +
        "separated by commas and without spaces");
    String row[];
    Fraction[][] array = new Fraction[nRows][nCols];
    for (int i = 0; i < nRows; i++) {
      System.out.println("Row " + (i + 1) + ":");
      try {
        //Splits input into numbers assuming that they are separated by commas
        row = br.readLine().split(",");
        for (int j = 0; j < nCols; j++) {
          if (j < row.length) {
            try {
              //Try parsing input as a fraction
              array[i][j] = parseFraction(row[j]);
            } catch (NumberFormatException e) {
              //If it fails, ask user to re-enter the number
              System.out.print("\nElement " + (j + 1) + " was invalid, " +
                  "please enter a valid number: ");
              array[i][j] = getFractionIO(br);
            }
          } else {
            //Prompt user for as many missing numbers as required
            System.out.println("You didn't input element " + (j + 1));
            array[i][j] = getFractionIO(br);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return new Matrix(array);
  }

  public static int getIntegerIO(boolean canBeNegative, BufferedReader br) {
    int integer;
    //Infinite loops which terminates when input is valid and returns
    while (true) {
      try {
        //Read input and remove leading and trailing spaces
        integer = Integer.parseInt(br.readLine().trim());
        //If positive integer is required and negative given ask for new input
        if (integer < 0 && !canBeNegative) {
          System.out.print("\nInput must be a positive integer: ");
        } else {
          //Ff all is fine return
          return integer;
        }
      } catch (IOException e) {
        e.printStackTrace();
      } catch (NumberFormatException e) {
        //If number conversion fails notify user
        System.out.print("\nInput was invalid. Please enter a valid integer: ");
      }
    }
  }

  public static Fraction getFractionIO(BufferedReader br) {
    //Fraction must be given as a/b, where a is numerator and b is the
    //denominator, without spaces in between
    while (true) {
      try {
        System.out.print("\nPlease enter the number as an integer or" +
            " a fraction: ");
        //Try to parse input as fraction and return if is valid. Fractions can
        //be both positive and negative
        return parseFraction(br.readLine().trim());
      } catch (IOException e) {
        e.printStackTrace();
      } catch (NumberFormatException e) {
        //If number conversion fails notify user
        System.out.println("Input was invalid. Please enter valid integers: ");
      } catch (DivisionByZeroException e) {
        //If fraction had a zero in the denominator and fraction creation fails
        //notify user
        System.out.println("Denominator musn't be zero!!");
      }
    }
  }

  public static Fraction parseFraction(String s) {
    //Throws NumberFormatExceptions when the input doesn't comply to the
    //standard expected:
    //  - An integer, positive or negative
    //  - A numerator and a denominator separated by '/' without spaces in
    // between

    //NumberFormatExceptions thrown, including the ones arising from integer
    //conversion are left for caller to handle
    if (s == null || s.length() == 0) {
      throw new NumberFormatException("invalid fraction given");
    }
    //Split fraction
    String[] numDenom = s.split("/");
    if (numDenom.length == 0) {
      //Only '/' given
      throw new NumberFormatException("Empty fraction given.");
    } else if (numDenom.length == 1) {
      //Only numerator, no denominator
      return new Fraction(Integer.parseInt(numDenom[0]));
    } else if (numDenom.length == 2) {
      //Numerator and denominator
      return new Fraction(Integer.parseInt(numDenom[0]),
          Integer.parseInt(numDenom[1]));
    } else {
      //Multiple '/', invalid input
      throw new NumberFormatException("Fractions can only have two terms: " +
          "numerator and denominator");
    }
  }
}
