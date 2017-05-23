package expr;

public class MultExpr extends BasicExpr {
  private Expr mult1;
  private Expr mult2;

  public MultExpr(Expr term1, Expr term2) {
    super(term1, term2);
  }

  @Override
  public Expr simplify() {
    return null;
  }

  @Override
  public Expr eval() {
    return null;
  }
}
