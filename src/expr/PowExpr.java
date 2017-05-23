package expr;

public class PowExpr extends BasicExpr {
  public PowExpr(Expr term1, Expr term2) {
    super(term1, term2);
  }

  @Override
  public Expr simplify() {
    if (getTerm2().eval() instanceof ConstExpr &&
        ((ConstExpr) getTerm2()).getValue() == 1) {
      return getTerm1();
    } else if (getTerm2().eval() instanceof ConstExpr &&
        ((ConstExpr) getTerm2().eval()).getValue() == 0) {
      return ConstExpr.ONE;
    }
    return null;
  }

  @Override
  public Expr eval() {
    return null;
  }
}
