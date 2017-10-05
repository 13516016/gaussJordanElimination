import java.io.*;
import java.util.*;


public class Main {

  public static void main(String[] args) {
    int menu1;
    char menu2;
    char menu3;

    Matrix mMatrix = new Matrix();
    Scanner in = new Scanner (System.in);

    do {
      System.out.println("\nPilihan matriks:");
      System.out.println("1. Membaca matriks dari input pembaca");
      System.out.println("2. Membaca matriks dari file eksternal");
      System.out.println("3. Buat matriks interpolasi");
      System.out.println();

      do  {
        System.out.print("Pilihan: ");
        menu1 = in.nextInt();
        System.out.println();
        if (menu1 == 1) {
          mMatrix.readMatrix();
        }
        else if (menu1 == 2){
          mMatrix.readFileMatrix();
        }
        else if (menu1==3){
          mMatrix.makeInterpolateData();
        }
        else{
          System.out.println("Input salah. Mohon ulangi.");
        }

      } while(menu1!=1 && menu1!=2 && menu1!=3);

      System.out.println();
      System.out.println("Matriks :");
      System.out.println();

      System.out.println("Solusi :");
      // mMatrix.gaussEliminate();
      mMatrix.showSolutions();
      mMatrix.writeMatrix();
      // System.out.println("GAUSS JORDAN");
      // mMatrix.gaussJordanEliminate();
      // mMatrix.pivotMatrix();

      if(menu1==3) {
        mMatrix.interpolationEquation();
      }

      System.out.println();

      do {
        System.out.println("Simpan matriks di file external? (y/n)");
        menu2 = in.next().charAt(0);
        if (menu2 == 'y'){
          mMatrix.writeFileMatrix();
        }
        else if (menu2 == 'n') {
          break;
        }
        else {
          System.out.println("Input salah. Ulangi input.");
        }

      } while (menu2!='y' && menu2 !='n');

      do {
        System.out.println("Ulang program? (y/n)");
        menu3 = in.next().charAt(0);
      }while (menu3!='y' && menu3!='n');
      mMatrix = null;

    } while(menu3!='n');

   }

}
