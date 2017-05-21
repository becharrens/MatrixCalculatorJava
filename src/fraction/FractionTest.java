package fraction;

import org.junit.Test;

import static fraction.Fraction.gcd;
import static fraction.Fraction.lcm;
import static org.junit.Assert.*;


public class FractionTest {
  private Fraction frac1 = new Fraction(1, 11);
  private Fraction frac2 = new Fraction(-33, -363);
  private Fraction frac3 = new Fraction(-2, 4);
  private Fraction frac4 = new Fraction(3, -7);
  private Fraction frac5 = new Fraction(33, 22);

  @Test
  public void addAndSubtractTest() throws Exception {
    assertEquals(frac1.subtract(frac2), new Fraction(0));
    assertEquals(frac2.add(frac3), new Fraction(-9, 22));
    assertEquals(frac5.subtract(frac3), new Fraction(2));
    assertEquals(frac3.add(frac4), new Fraction(-13, 14));
    assertEquals(frac5.add(frac4), new Fraction(15, 14));
  }

  @Test
  public void mcdTest() throws Exception {
    assertTrue(gcd(54, 90) == 18);
    assertTrue(gcd(162, 270) == 54);
    assertTrue(gcd(1, 1000) == 1);
    assertTrue(gcd(12, 36) == 12);
    assertTrue(gcd(342, 66) == 6);
  }

  @Test
  public void mcmTest() throws Exception {
    assertTrue(lcm(8, 10) == 40);
    assertTrue(lcm(7, 21) == 21);
    assertTrue(lcm(7, 13) == 91);
    assertTrue(lcm(128, 336) == 2688);
    assertTrue(lcm(12, 16) == 48);
  }

  @Test
  public void multiplyAndDivide() throws Exception {

    frac2.divide(frac1);
    frac3.multiply(frac3.reciprocal().negate());
    frac4.multiply(frac2);
    frac5.multiply(frac4);
    assertEquals(frac1.multiply(frac5), new Fraction(3, 22));
    assertEquals(frac2.multiply(frac1), new Fraction(1, 121));
    assertEquals(frac3.multiply(frac3.reciprocal().negate()),
        new Fraction(-1));
    assertEquals(frac4.multiply(frac2), new Fraction(-3, 77));
    assertEquals(frac5.multiply(frac4), new Fraction(-9,14));
  }

  @Test
  public void simplify() throws Exception {

    assertTrue(frac1.getNum() == 1 && frac1.getDenom() == 11);
    assertTrue(frac2.getNum() == 1 && frac2.getDenom() == 11);
    assertTrue(frac3.getNum() == -1 && frac3.getDenom() == 2);
    assertTrue(frac4.getNum() == -3 && frac4.getDenom() == 7);
    assertTrue(frac5.getNum() == 3 && frac5.getDenom() == 2);

    System.out.println(frac1);
    System.out.println(frac2);
    System.out.println(frac3);
    System.out.println(frac4);
    System.out.println(frac5);
    for (int i = 1; i < 25; i++) {
      for (int j = 1; j < 25; j++) {
        System.out.println(i + " / " + j + " = " + new Fraction(i, j));
      }
    }
  }

}