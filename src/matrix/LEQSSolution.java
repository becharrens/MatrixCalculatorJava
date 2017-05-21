package matrix;

import fraction.Fraction;

public class LEQSSolution {
  private final Fraction[] particularSol;
  private final Fraction[][] generalSol;


  public LEQSSolution(Fraction[] particularSol, Fraction[][] generalSol) {
    this.particularSol = particularSol;
    this.generalSol = generalSol;
  }

  public Fraction[] getParticularSol() {
    return particularSol;
  }

  public Fraction[][] getGeneralSol() {
    return generalSol;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Particular: ");
    sb.append('[');
    for (int c = 0; c < particularSol.length; c++) {
      sb.append(particularSol[c]);
      if (c < particularSol.length - 1) {
        sb.append(", ");
      }
    }
    sb.append(']');
    if (generalSol.length > 0) {
      sb.append("\nGeneral :");
      sb.append(new Matrix(generalSol));
    }
    return sb.toString();
  }
}
