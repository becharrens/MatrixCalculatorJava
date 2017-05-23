package expr;

public interface Expr {
  Expr add(Expr other);
  Expr subtract(Expr other);
  Expr multiply(Expr other);
  Expr divide(Expr other);
  Expr reciprocal();
  Expr negate();
  Expr simplify();
  Expr eval();
}
