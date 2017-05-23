package expr;

public class SubExpr extends BasicExpr {
  public SubExpr(Expr term1, Expr term2) {
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
