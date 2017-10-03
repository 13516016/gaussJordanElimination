import java.io.*;
import java.util.*;
import java.lang.*;

public class Matrix{

  final int maxRow = 50;
  final int maxColumn = 50;

  final int SOLVABLE = 1;
  final int  INFINITE_SOLUTION = 0;
  final int  NO_SOLUTION = -1;

  final Double  DELTA = 9.0E-6;
  double div;
  double m;

  private int row;
  private int column;
  private double[][] table = new double[maxRow][maxColumn];

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
    System.out.print("Tulis jumlah baris: ");
    this.row = input.nextInt();
    System.out.print("Tulis jumlah kolom: ");
    this.column = input.nextInt();

    for (int i = 0; i < this.row; i++) {
      for (int j = 0; j < this.column; j++) {
        this.table[i][j] = input.nextDouble();
      }
    }
  }

  void makeInterpolateData(){
    int n;
    Double[] data= new Double[maxColumn];
    Double x;
    Double y;

    System.out.print("Tulis jumlah data : ");
    n = input.nextInt();
    this.row = n;
    this.column = n+1;

    for (int i = 0; i < row; i++) {
      System.out.printf("x%d = ",i+1);
      x = input.nextDouble();
      for (int j = 0; j < column-1; j++) {
        this.table[i][j] = Math.pow(x,j);
      }
      System.out.printf("f(x%d) = ",i+1);
      y = input.nextDouble();
      this.table[i][this.column-1] = y;
    }
  }

  void readFileMatrix(){

    String fileName;
    System.out.print("Tuliskan nama file: ");
    fileName = input.next();
  	try{
  	Scanner in  = new Scanner(new File(fileName));
  	int row = 0; int col = 0;
  	//algoritma hitung baris dan kolom
  	//baca baris pertama
  	if(in.hasNextLine()){
  	row++;
  	Scanner test = new Scanner(in.nextLine());
  	//baca kolom pertama
  	while (test.hasNextDouble())
  		{++col; test.nextDouble();}
  	//baca baris berikutnya
  	while(in.hasNextLine()){
  		row++;
  	//baca kolom berikutnya
  		while (test.hasNextDouble())
  			{++col; test.nextDouble();}
  		in.nextLine();
  		}
  	//buat matriks baru dari jumlah baris & kolom yang dibaca
  	this.row = row;
    this.column = col;
  	in.close();
  	//algoritma baca matriks
  	in  = new Scanner(new File(fileName));
  	for(int i=0; i<row; i++)
  	{for(int j=0;j<col;j++)
  		{if(in.hasNextDouble())
  			this.table[i][j] = in.nextDouble();}
  		}
  	in.close();
    	}
  	}catch (IOException x){
      System.out.println("File not found.");
      this.readFileMatrix();
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

      Double temp = this.table[i][j];
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
      while (j<this.column-1 && (this.table[i][j]==0 )){
          j++;
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
     int zeroRows = this.checkZeroRow();

      if (this.isSolvable !=NO_SOLUTION){
       if (this.row - zeroRows != this.column-1){
         this.isSolvable = INFINITE_SOLUTION;
       }
       else {
         this.row -= this.checkZeroRow();
       }
     }
   }

   boolean isZeroRow(int i){
     int j=0;
     boolean check = false;
     while (j<this.column-1 && this.table[i][j]==0){
         j++;
     }

     if (this.table[i][j]==0){
       check = true;
     }
     return check;
   }

   private void printParameters(){
     char x = 'o';
     char[] parameters = new char[50];

     for (int i = 0; i < this.column-1; i++) {
       if (!isZeroRow(i)){
         System.out.printf("x%d = ",i+1);
         if (this.table[i][this.column-1]!=0){
           System.out.print(this.table[i][this.column-1]);
         }

         for (int j = 0; j < this.column-1; j++) {
           if (i!=j){
              if (this.table[i][j]!=0){
                if (parameters[j]=='\0'){
                  x++;
                  parameters[j] = x;
                };
                if (this.table[i][j]<0){
                  if(this.table[i][this.column-1]!=0){
                    System.out.printf(" +");
                  }
                }
                if (this.table[i][j]!=1 && this.table[i][j]!=-1){
                  System.out.printf(" ");
                  System.out.print((this.table[i][j])*-1);
                }
                if (this.table[i][j]==1){
                  System.out.printf("-%c",parameters[j]);
                }
                else {
                  System.out.printf("%c",parameters[j]);
                }

              }
           }
         }
       }
       else {
         System.out.printf("x%d = ",i+1);
         System.out.printf("%c",parameters[i]);
       }
       System.out.println();
     }
   }
   void interpolationEquation(){
     if (this.isSolvable == SOLVABLE){
       int j = this.column-1;
       for (int i = 0; i < this.row; i++) {

         if(this.table[i][j]!=0){
           if (this.table[i][j]>=0 && i!=0){
             System.out.print(" +");
           }
           System.out.print(" ");
           if (this.table[i][j]!=1){
             System.out.printf(Double.toString(this.table[i][j]));
           }
           if (i!=0){
             System.out.printf("x^%d",i);
           }
         }
       }

     }
   }

   void showSolutions(){
    this.gaussJordanEliminate();
    this.checkSolvable();
    if (this.isSolvable == SOLVABLE){
      int j = this.column-1;
      for (int i = 0; i < this.row; i++) {
        System.out.printf("x%d = ",i+1);
        System.out.println(this.table[i][j]);
      }
    }
    else if (this.isSolvable == INFINITE_SOLUTION){
      this.printParameters();
    }
    else {
      System.out.println("Persamaan tidak memiliki penyelesaian.");
    }
   }

   void writeExternalParameters(FileWriter w){
     char x = 'o';
     char[] parameters = new char[50];

     try{
       for (int i = 0; i < this.column-1; i++) {
         if (!isZeroRow(i)){
           w.write(String.format("x%d = ",i+1));
           if (this.table[i][this.column-1]!=0){
             w.write(Double.toString(this.table[i][this.column-1]));
           }

           for (int j = 0; j < this.column-1; j++) {
             if (i!=j){
                if (this.table[i][j]!=0){
                  if (parameters[j]=='\0'){
                    x++;
                    parameters[j] = x;
                  };
                  if (this.table[i][j]<0){
                    if(this.table[i][this.column-1]!=0){
                      w.write(" +");
                    }
                  }
                  if (this.table[i][j]!=1 && this.table[i][j]!=-1){
                    w.write(" ");
                    w.write(Double.toString((this.table[i][j]*-1)));
                  }
                  if (this.table[i][j]==1){
                    w.write(String.format("-%c",parameters[j]));
                  }
                  else {
                    w.write(String.format("%c",parameters[j]));
                  }

                }
             }
           }
         }
         else {
           w.write(String.format("x%d = ",i+1));
           w.write(String.format("%c",parameters[i]));
         }
         w.write("\r\n");
       }
     }catch (IOException e){};
   }

   void writeFileMatrix() {
     String matrixFileName;
     String resultFileName;

    System.out.print("Tuliskan nama file matriks: ");
    matrixFileName = input.next();

    System.out.print("Tuliskan nama file hasil: ");
    resultFileName = input.next();

   try{
     FileWriter w = new FileWriter(matrixFileName,false);
     for(int i = 0; i<this.row; i++){
       for(int j = 0; j<this.column; j++)
         { w.write(Double.toString(this.table[i][j])+" ");}
     w.write("\r\n");}
     w.close();
   } catch (IOException x){System.err.println("Failed to save file.");}

   try{
     FileWriter w = new FileWriter(resultFileName,false);
     writeExternalParameters( w );
     w.write("\r\n");
     w.close();
   } catch (IOException x){System.err.println("Failed to save file.");}

  }


}
