package expr;

public class AddExpr extends BasicExpr {
  private Expr sum1;
  private Expr sum2;

  public AddExpr(Expr term1, Expr term2) {
    super(term1, term2);
  }

  @Override
  public Expr simplify() {
//    if (other instanceof ConstExpr) {
//      return new ConstExpr(getValue() - ((ConstExpr) other).getValue());
//    } else if (other instanceof FractionExpr) {
//      return new FractionExpr(this).subtract(other);
//    } else {
//      return new SubExpr(this, other);
//    }
    return null;
  }

  @Override
  public Expr eval() {
    return null;
  }
}
