package fraction;

public class DivisionByZeroException extends ArithmeticException {
  @Override
  public String getMessage() {
    return "Can't divide a number by zero!!";
  }
}
