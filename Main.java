import java.io.*;
import java.util.*;

public class Main {

  public static void main(String[] args) {
    Matrix mMatrix = new Matrix();
    Scanner in = new Scanner (System.in);

    // // mMatrix.readMatrix();
    // mMatrix.readFileMatrix();
    // mMatrix.writeMatrix();
    // System.out.println();
    // // mMatrix.pivotMatrix();
    // // mMatrix.gaussEliminate();
    // // mMatrix.rowEchelonForm();
    // // mMatrix.writeMatrix();
    // // mMatrix.gaussJordanEliminate();
    // // // mMatrix.checkSolvable();
    // // mMatrix.writeMatrix();
    System.out.println("opsi matriks:");
    System.out.println("1. membaca matriks dari input pembaca & menampilkan hasil");
    System.out.println("2. membaca matriks dari file eksternal & menampilkan hasil");
    System.out.println("3. membaca matriks dari input pembaca & menyimpan matriks ke file eksternal");
    System.out.println("4. membaca matriks dari file eksternal & menyimpan matriks ke file eksternal");
    System.out.print("opsi: "); int x = in.nextInt();
    if (x == 1) {
      mMatrix.readMatrix();
      System.out.println("Matriks:");
      mMatrix.showSolutions();
      mMatrix.writeMatrix();
    }
    else if (x == 2){
      mMatrix.readFileMatrix();
      System.out.println("Matriks:");
      mMatrix.showSolutions();
      mMatrix.writeMatrix();
    }
    else if (x == 3){
      mMatrix.readMatrix();
      mMatrix.showSolutions();
      mMatrix.writeFileMatrix();
    }
    else if (x == 4){
      mMatrix.readFileMatrix();
      mMatrix.showSolutions();
      mMatrix.writeFileMatrix();
    }
    else {
      System.out.print("Input tidak sesuai");
     }

   }
}
