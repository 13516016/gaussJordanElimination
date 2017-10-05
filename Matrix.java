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
  char x = 'o';
  private int row;
  private int column;
  private double[][] table = new double[maxRow][maxColumn];
  private char[] parameters = new char[50];
  private char[] vars = new char[50];
  private int isSolvable = SOLVABLE;
  private int paramNumber = 0;
  private int paramNow = 0;


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

  int getLeadingIndex(int row){

    for (int j = 0; j < this.column-1; j++) {
      if (this.table[row][j]!=0){
        return j;
      }
    }

    return -1;
  }

  int getMaxColRow(int row, int col){
    double MaxNum = this.table[row][col];
    int idx = row;

    for (int i = row; i < this.row; i++) {
      if (MaxNum > this.table[i][row]){
        MaxNum = this.table[i][col];
        idx = i;
      }
    }

    return idx;
  }

   void pivotMatrix(){
     int maxRow;
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
         this.pivotMatrix();
         if (this.table[i][i]!=0){
            m = this.table[k][i]/this.table[i][i];
         }
         else {
           m = 1;
         }
         for (int j = 0; j < this.column; j++) {
          //  this.writeMatrix();
          //  System.out.println();
          this.table[k][j]-= m*this.table[i][j];
          if (this.table[k][j]< DELTA && this.table[k][j] > DELTA*-1){
            this.table[k][j]=0;
          }
         }
       }
     }
   }

   void rowEchelonForm(){
     int leadCol;

     this.gaussEliminate();
     this.gaussEliminate();
       for (int i = 0; i < this.row; i++) {
         leadCol = getLeadingIndex(i);
         if(leadCol!=-1) {
           div = this.table[i][leadCol];
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

   void secondPivot(){
     for (int i = 0; i < this.row-1; i++) {
      for (int k = i+1; k < this.row; k++) {

        if (getLeadingIndex(i)!=-1){
          if ( getLeadingIndex(i) > getLeadingIndex(k) && getLeadingIndex(k)!=-1) {
            swapRows(i,k);
          }
        }
        else {
          for (int j = i; j < this.row-1; j++) {
            swapRows(j,j+1);

          }
        }


      }
    }

   }

   void gaussJordanEliminate(){
     int leadCol;
       this.rowEchelonForm();
       for (int n = 0; n < 2; n++) {

       for (int i = 0; i < this.row-1; i++) {
         for (int k = i+1; k < this.row; k++) {

           this.pivotMatrix();

           leadCol = getLeadingIndex(k);

           if (leadCol!=-1){
             div = this.table[i][leadCol]/this.table[k][leadCol];
               for (int j = leadCol; j < this.column; j++) {
                 this.table[i][j]-= div * this.table[k][j];
             }

            }

            this.secondPivot();

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

boolean checkRowSolution(int row){

  if (getLeadingIndex(row)==-1){
    return false;
  }
  else {
    for (int i = getLeadingIndex(row); i < this.column-1; i++) {
      if (this.table[row][i]!=0){
        return false;
      };
    }
  }
  return true;
}

int numberOfParams(){
  int count=0;
  for (int i = 0; i < this.column-1; i++) {
    if (getLeadingIndex(i)==-1){
      count++;
    }
  }

  return count;
}
void printRowParams(int row){

  if (this.table[row][this.column]!=0){
    System.out.println(this.table[row][this.column]);
  }

  if (!checkRowSolution(row)){
    if(getLeadingIndex(row)!=-1) {
      for (int j = getLeadingIndex(row)+1; j < this.column-1; j++) {
        if (this.table[row][j]!=0){
          if (parameters[j]=='\0'){
            x++;
            parameters[j] = x;
            vars[paramNumber] = x;
            paramNumber++;
          }
          if(this.table[row][j]<0){
            System.out.print(" + ");
          }
          System.out.print(this.table[row][j]*-1);

          System.out.print(parameters[j]);
        }
      }
    }
    else {
        System.out.print(vars[paramNow]);
        paramNow++;
    }
  }

}
void printExtRowParams(FileWriter w, int row){
  try {
    if (this.table[row][this.column]!=0){
      w.write(Double.toString(this.table[row][this.column]));
      w.write("\r\n");
    }
    if (!checkRowSolution(row)){
      if(getLeadingIndex(row)!=-1) {
        for (int j = getLeadingIndex(row)+1; j < this.column-1; j++) {
          if (this.table[row][j]!=0){
            if (parameters[j]=='\0'){
              x++;
              parameters[j] = x;
              vars[paramNumber] = x;
              paramNumber++;
            }
            if(this.table[row][j]<0){
              w.write(" + ");
            }
              w.write(Double.toString(this.table[row][j]*-1));

              w.write(parameters[j]);
          }
        }
      }
      else {
          w.write(vars[paramNow]);
          paramNow++;
      }
    }
  } catch(IOException e) {
    System.out.println("Failed to write to file");
  }

}


   private void printParameters(){
     paramNow =0;
     boolean isSolved;
     for (int i = 0; i < this.column-1; i++) {
        System.out.printf("x%d = ",i+1);

        if (this.table[i][this.column-1]!=0){
          System.out.print(this.table[i][column-1]);
        }

        if(!checkRowSolution(i)) {
          printRowParams(i);
        }
        System.out.println();
     }
   }

   private void writeExternalParameters(FileWriter w){
     paramNow =0;
     boolean isSolved;

     try {
       for (int i = 0; i < this.column-1; i++) {
          w.write(String.format("x%d = ",i+1));

          if (this.table[i][this.column-1]!=0){
            w.write(Double.toString(this.table[i][column-1]));
          }

          printExtRowParams(w,i);

          w.write("\r\n");
       }
     } catch(IOException e) {
       System.out.println("Failed to write");
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
    this.secondPivot();
    this.checkSolvable();
    this.writeMatrix();
    System.out.println();
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
