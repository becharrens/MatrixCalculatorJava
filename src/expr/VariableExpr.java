package expr;

public class VariableExpr implements Expr {
  private final String var;

  public VariableExpr(String var) {
    this.var = var;
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
    return new FractionExpr(new ConstExpr(1), this);
  }

  @Override
  public Expr negate() {
    return new MultExpr(new ConstExpr(-1), this);
  }

  @Override
  public Expr simplify() {
    return this;
  }

  @Override
  public Expr eval() {
    return this;
  }
}
