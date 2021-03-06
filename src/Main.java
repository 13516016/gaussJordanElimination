import java.io.*;
import java.util.*;


public class Main {

  public static void main(String[] args) {
    int menu1;
    double input;
    char menu2;
    char menu3;
    char menu4;



    Scanner in = new Scanner (System.in);

    do {
      Matrix mMatrix = new Matrix();

      System.out.println("\nPilihan matriks:");
      System.out.println("1. Membaca matriks dari input pembaca");
      System.out.println("2. Membaca matriks dari file eksternal");
      System.out.println("3. Buat matriks interpolasi");
      System.out.println("4. Buat matriks Hilbert");
      System.out.println("5. fungsi e");
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
        else if (menu1==4){
          mMatrix.makeHilbert();
        }
        else if (menu1==5){
          mMatrix.makeInterpolateEFunction();
        }
        else{
          System.out.println("Input salah. Mohon ulangi.");
        }

      } while( (menu1<=1) && (menu1>=5) );

      System.out.println();
      System.out.println("Matriks :");
      mMatrix.writeMatrix();
      System.out.println();

      System.out.println("Solusi :");
      mMatrix.showSolutions();
      System.out.println();

      if(menu1==3 || menu1==5) {

        mMatrix.interpolationEquation();
        System.out.println();

        System.out.println("Apakah anda ingin menghampiri suatu nilai? (y/n)");
        menu4 = in.next().charAt(0);
        if (menu4 == 'y'){
          System.out.println("Masukkan suatu nilai : ");
          input = in.nextDouble();
          System.out.println("f("+input+") = " + mMatrix.getApproxFunction(input));
        }

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
