
public class Main {

  public static void main(String[] args) {
    Matrix mMatrix = new Matrix();

    mMatrix.readMatrix();
    mMatrix.writeMatrix();
    System.out.println();
    mMatrix.pivotMatrix();
    // mMatrix.gaussEliminate();
    // mMatrix.rowEchelonForm();
    // mMatrix.writeMatrix();
    // mMatrix.gaussJordanEliminate();
    // mMatrix.checkSolvable();
    // mMatrix.writeMatrix();

    mMatrix.showSolutions();
    mMatrix.writeMatrix();
  }
}
