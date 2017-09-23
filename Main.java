
public class Main {

  public static void main(String[] args) {
    Matrix mMatrix = new Matrix();

    mMatrix.readMatrix();
    mMatrix.writeMatrix();
    System.out.println();
    mMatrix.gaussJordanEliminate();
    mMatrix.writeMatrix();
  }


}
