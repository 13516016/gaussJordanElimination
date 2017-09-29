import java.util.*;

public class Matrix{

  final int maxRow = 50;
  final int maxColumn = 50;

  final int SOLVABLE = 1;
  final int  INFINITE_SOLUTION = 0;
  final int  NO_SOLUTION = -1;

  final double  DELTA = 9.0E-6;
  float div;
  float m;


  private int row;
  private int column;
  private float[][] table = new float[maxRow][maxColumn];
  private ArrayList<Integer> parameters = new ArrayList<>();
  private int isSolvable = SOLVABLE;


  Scanner input = new Scanner(System.in);

  public Matrix () {
    for (int i = 0; i < maxRow; i++) {
      for (int j = 0; j < maxColumn; j++) {
        this.table[i][j] = 0;
      }
    }
  }

  int getRow(){
    return this.row;
  }

  int getColumn(){
    return this.column;
  }

  void readMatrix(){
    System.out.print("Masukkan jumlah baris: ");
    this.row = input.nextInt();
    System.out.print("Masukkan jumlah kolom: ");
    this.column = input.nextInt();

    for (int i = 0; i < this.row; i++) {
      for (int j = 0; j < this.column; j++) {
        this.table[i][j] = input.nextFloat();
      }
    }
  }

  void writeMatrix(){

    for (int i = 0; i < this.row; i++) {
      System.out.print("|");

      for (int j = 0; j < this.column; j++) {
        // System.out.printf("%.2f",this.table[i][j]);
        System.out.print(this.table[i][j]);
        if (j!= this.column-1) System.out.print(" ");
      }
      System.out.print("|");
      System.out.println();
    }
  }

   void pivotMatrix(){
     for (int i = 0; i < this.row-1; i++) {
       for (int k = i+1; k < this.row; k++) {
         if (this.table[i][i]<this.table[k][i]){
           swapRows (i,k);
         }
       }
     }
   }

   private void swapRows(int i, int k){
     for (int j = 0; j < this.column; j++) {

      float temp = this.table[i][j];
      this.table[i][j] = this.table[k][j];
      this.table[k][j] = temp;

     }
   }

   void gaussEliminate(){
     this.pivotMatrix();

     for (int i = 0; i < this.row-1; i++) {
       for (int k = i+1; k < this.row; k++) {
         if (this.table[i][i]!=0){
            m = this.table[k][i]/this.table[i][i];
         }
         else {
           m = 1;
         }
         for (int j = 0; j < this.column; j++) {
          this.table[k][j]-= m*this.table[i][j];
          if (this.table[k][j]< DELTA && this.table[k][j] > DELTA*-1){
            this.table[k][j]=0;
          }
         }
       }
     }
   }

   void rowEchelonForm(){
     this.gaussEliminate();
       for (int i = 0; i < this.row; i++) {
         if (this.table[i][i]!=0) {
           div = this.table[i][i];
         }
         else {
           div = 1;
         }
         for (int j = 0; j < this.column; j++) {
           if (this.table[i][j]!=0){
             this.table[i][j]/=div;
           }
         }
       }
   }

   void gaussJordanEliminate(){
       this.rowEchelonForm();
       for (int i = 0; i < this.row-1; i++) {
         for (int k = i+1; k < this.row; k++) {
           if (this.table[k][k]!=0){
             div = this.table[i][k]/this.table[k][k];
           }
           else{
             div = 1;
           }
           for (int j = k; j < this.column; j++) {
             this.table[i][j]-= div*this.table[k][j];
           }
         }
       }
   }

   int checkZeroRow(){
    int j=0;
    int count=0;
    int check;

    for (int i = this.row-1; i >= 0; i--) {
        j=0;
      while (j<this.column-1 && (this.table[i][j]==0 || this.table[i][i]==0)){
          j++;
      }

      if (this.table[i][i]==0){
        // this.parameters.add(i+1);
      }

      if (j==this.column-1){
        if (this.table[i][j] == 0){
          count++;
        }
        else {
          this.isSolvable = NO_SOLUTION;
          break;
        }
      }
    }
    return count;
   }


   void checkSolvable(){
      if (this.isSolvable !=NO_SOLUTION){
       if (this.row - this.checkZeroRow() != this.column-1){
         this.isSolvable = INFINITE_SOLUTION;
       }
       else {
         this.row -= this.checkZeroRow();
       }
     }
   }

   private void printParameters(){
     int i;
     int j;

     for (int i = 0; i < this.row; i++) {

       System.out.println("x%d = ",i+1);
       if (this.table[i][this.column-1]!=0){
         System.out.println("%d",this.table[i][this.column-1]);
       }

       for (int j = 0; j < this.column-1; i++) {
         if (i!=j){

            if (this.table[i][j]!=0){
              System.out.println("x%d",j+1);
            }

         }
       }
     }
   }


   void showSolutions(){
    this.gaussJordanEliminate();
    this.checkSolvable();
    if (this.isSolvable == SOLVABLE){
      this.writeMatrix();
      System.out.println("SOLUSI : ");
      int j = this.column-1;
      for (int i = 0; i < this.row; i++) {
        System.out.printf("x%d = ",i+1);
        System.out.println(this.table[i][j]);
      }
    }
    else if (this.isSolvable == INFINITE_SOLUTION){
      System.out.println("Solusi tak hingga banyaknya.");
    }
    else {
      System.out.println("Persamaan tidak memiliki penyelesaian.");
    }
   }
}
