package expr;

public class FractionExpr extends BasicExpr {
  public static final FractionExpr ONE = new FractionExpr(ConstExpr.ONE);
  public static final FractionExpr ZERO = new FractionExpr(ConstExpr.ZERO);
  public static final FractionExpr NEGONE = new FractionExpr(ConstExpr.NEGONE);

  public FractionExpr(Expr term1, Expr term2) {
    super(term1, term2);
  }

  public FractionExpr(Expr term1) {
    this(term1, new ConstExpr(1));
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
