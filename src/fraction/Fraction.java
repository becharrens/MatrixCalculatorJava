package fraction;

import java.util.ArrayDeque;
import java.util.Deque;

public class Fraction implements Cloneable {
  private int num;
  private int denom;

  public Fraction(int num) {
    this.num = num;
    this.denom = 1;
  }

  public Fraction(int num, int denom) {
    if (denom == 0) {
      throw new DivisionByZeroException();
    }
    this.num = num;
    this.denom = denom;
    simplify();
  }

  public int getNum() {
    return num;
  }

  public int getDenom() {
    return denom;
  }

  //All operations with fractions don't modify their current values, but return
  //a new fraction instead. There is no way to modify the fields from outside
  //the class, and they aren't modified by any method, so they are effectively
  //final, but they aren't final to begin with because after the initial
  //assignment the fraction can be simplified, meaning the values could be
  //overwritten
  public Fraction add(Fraction other) {
    //Add given fraction to the current one
    int newNum = num;
    int newDenom = denom;
    //If denominators aren't equal find the least common multiple
    if (denom != other.getDenom()) {
      int leatsComMult = lcm(denom, other.getDenom());
      //Carry out addition with equivalent fractions of denominator = lcm
      newNum *= leatsComMult / denom;
      newDenom = leatsComMult;
      newNum += other.getNum() * (leatsComMult / other.getDenom());
    } else {
      //No need to modifu anything, just add numerators
      newNum += other.getNum();
    }
    return new Fraction(newNum, newDenom);
  }

  public Fraction subtract(Fraction other) {
    //Subtraction = add complement
    return add(other.negate());
  }


  public Fraction multiply(Fraction other) {
    //Multiply current fraction by the given one
    return new Fraction(num * other.getNum(),
        denom * other.getDenom());
  }

  public Fraction divide(Fraction other) {
    //Divide current fraction by the given one = multiply by reciprocal of the
    //other fraction
    return multiply(other.reciprocal());
  }

  public Fraction reciprocal() {
    //Return d/n where fraction = n/d if n != 0
    if (num == 0) {
      throw new DivisionByZeroException();
    }
    return new Fraction(denom, num);
  }

  public Fraction negate() {
    return new Fraction(-num, denom);
  }

  public void simplify() {
    //If numerator is one simplify denominator directly
    if (num == 0) {
      denom = 1;
      return;
    }
    //Type casting may not be needed
    boolean changeSign = ((int) Math.signum(num) != (int) Math.signum(denom));
    //Work with absolute values for simplicity
    num = Math.abs(num);
    denom = Math.abs(denom);

    //To simplify just multiply by the greatest common divisor of numerator
    //and denominator (if it is greater than one)
    int greatestComDiv = gcd(num, denom);
    if (greatestComDiv > 1) {
      num /= greatestComDiv;
      denom /= greatestComDiv;
    }
    //Restore sign
    if (changeSign) {
      num = -num;
    }
  }

  private static Deque<Integer> getPrimeFactors(int n) {
    //Pre: n > 0
    //Post: returns a queue containing n's prime factors in ascending order
    //excluding 1
    Deque<Integer> factors = new ArrayDeque<>();
    int div = 2;
    int sqrt = (int) Math.sqrt(n);
    //Look for prime factors in the range 2-sqrt(n), and divide by each of them
    //as many times as possible
    while (div <= sqrt) {
      if (n % div == 0) {
        n /= div;
        factors.add(div);
      } else {
        div++;
      }
    }
    //If a factor > 1 still remains, add it. It must be a prime > sqrt(n)
    if (n > 1) {
      factors.add(n);
    }
    return factors;
  }

  public static int gcd(int n1, int n2) {
    //Pre: n1 >= 0 && n2 >= 0
    //Post: returns the greatest common divisor
    int min = Math.min(n1, n2);
    int max = Math.max(n1, n2);
    Deque<Integer> factors = getPrimeFactors(min);
    int minComDiv = 1;
    int factor;
    //Look for all the prime factors of the smaller number which are present in
    //the bigger one
    while (!factors.isEmpty()) {
      factor = factors.poll();
      if (max % factor == 0) {
        max /= factor;
        minComDiv *= factor;
      }
    }
    return minComDiv;
  }

  public static int lcm(int n1, int n2) {
    //Pre: n1 >= 0 && n2 >= 0
    //Post: returns the least common multiple
    return n1 * n2 / gcd(n1, n2);
  }

  @Override
  public String toString() {
    //If denominator is one, simply output numerator
    if (denom == 1) {
      return num + "";
    }
    return num + "/" + denom;
  }

  @Override
  public Fraction clone() {
    return new Fraction(num, denom);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || !(o instanceof Fraction)) {
      return false;
    }
    Fraction fraction = (Fraction) o;
    return  num == fraction.getNum() && denom == fraction.getDenom();
  }

  @Override
  public int hashCode() {
    //Intellij's default hashing function
    int result = num;
    result = 31 * result + denom;
    return result;
  }
}

