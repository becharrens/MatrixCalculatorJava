package expr;

public abstract class BasicExpr implements Expr {
  private Expr term1;
  private Expr term2;

  public BasicExpr(Expr term1, Expr term2) {
    this.term1 = term1;
    this.term2 = term2;
    simplify();
  }

  protected Expr getTerm1() {
    return term1;
  }

  protected Expr getTerm2() {
    return term2;
  }

  @Override
  public Expr add(Expr other) {
    return new AddExpr(this, other);
  }

  @Override
  public Expr subtract(Expr other) {
    return new SubExpr(this, other);
  }

  @Override
  public Expr multiply(Expr other) {
    return new MultExpr(this, other);
  }

  @Override
  public Expr divide(Expr other) {
    return new FractionExpr(this, other);
  }

  @Override
  public Expr reciprocal() {
    return new FractionExpr(ConstExpr.ONE, this);
  }

  @Override
  public Expr negate() {
    return new MultExpr(ConstExpr.NEGONE, this);
  }
}
