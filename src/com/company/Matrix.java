package com.company;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Matrix  implements Comparable<Matrix>
{
    protected Dimension dim;
    protected double[][] ele;

    public Matrix(int r){
        this.dim = new Dimension(r,r);
        this.ele = new  double[r][r];
    }

    public Matrix(int r,int c){
        this.dim = new Dimension(r,c);
        this.ele = new double[dim.getrows()][dim.getclms()];
    }

    public void setElement(String element) {
        Pattern semi = Pattern.compile(";");
        Matcher Semi = semi.matcher(element);
        int countSemi = 0;
        while (Semi.find()) countSemi++;

        if (this.dim.getrows() != countSemi + 1) throw new IllegalArgumentException("Number of rows doesn't match");
        else {
            for (int i = 0; i <= countSemi; i++) {
                String[] help = element.split(";");

                Pattern comma = Pattern.compile(",");
                Matcher Comma = comma.matcher(help[i]);
                int countComma = 0;
                while (Comma.find()) countComma++;

                if (countComma != this.dim.getclms() - 1)
                    throw new IllegalArgumentException("Number of columns doesn't match");
                else {

                    if (help[i].matches("([0-9]+[,[0-9]+]*)+")) {
                        String[] help2 = help[i].split(",");
                        for (int j = 0; j < this.dim.getclms(); j++) {
                            this.ele[i][j] = Integer.valueOf(help2[j]);
                        }
                    } else throw new IllegalArgumentException("Pattern doesn't match");
                }
            }
        }
    }


    public double getElement(int r,int c){
        return this.ele[r-1][c-1];
    }

    public void setElement(int r,int c, int value){
        this.ele[r-1][c-1] = value;
    }

    public double[] getRow(int r){
        return this.ele[r-1];
    }

    public double[] getCol(int c){
        double[] token = new double[this.dim.getrows()];
        int i = 0;
        while (i< this.dim.getrows()){
            token[i] = this.ele[i][c-1];
            i++;
        }
        return token;
    }

    public void AddToRow(int r, int value) {
        for (int i=0; i < this.dim.getrows(); i++){
            this.ele[r-1][i] += value;
        }
    }

    public void SubToRow(int r, int value) {
        for (int i=0; i < this.dim.getrows(); i++){
            this.ele[r-1][i] -= value;
        }
    }

    public void MultToRow(int r, int value) {
        for (int i=0; i < this.dim.getrows(); i++){
            this.ele[r-1][i] *= value;
        }
    }

    public void RowAddRow(int r, int rDes){
        for (int i=0; i< this.dim.getrows(); i++){
            this.ele[rDes-1][i] += this.ele[r-1][i];
        }
    }

    public void RowSubRow(int r, int rDes){
        for (int i=0; i< this.dim.getrows(); i++){
            this.ele[rDes-1][i] -= this.ele[r-1][i];
        }
    }

    public void RowMultRow(int r, int rDes){
        for (int i=0; i <this.dim.getrows(); i++){
            this.ele[rDes-1][i] *= this.ele[r-1][i];
        }
    }

    public void AddToCol(int c, int value) {
        for (int i=0; i < this.dim.getrows(); i++){
            this.ele[i][c-1] += value;
        }
    }

    public void SubToCol(int c, int value) {
        for (int i=0; i < this.dim.getrows(); i++){
            this.ele[i][c-1] -= value;
        }
    }
    public void MultToCol(int c, int value) {
        for (int i=0; i < this.dim.getrows(); i++){
            this.ele[i][c-1] *= value;
        }
    }

    public void ColMultCol(int c, int cDes){
        for (int i=0; i< this.dim.getrows(); i++){
            this.ele[i][cDes] *= this.ele[i][c-1];
        }
    }
    public void ColAddCol(int c, int cDes){
    for (int i=0; i< this.dim.getrows(); i++){
        this.ele[i][cDes] += this.ele[i][c-1];
        }
    }
    public void ColSubtCol(int c, int cDes){
        for (int i=0; i< this.dim.getrows(); i++){
            this.ele[i][cDes] -= this.ele[i][c-1];
        }
    }

    /**
     * Transpose a matrix
     * @return
     */
    public Matrix transpose(){
        Matrix help = new Matrix(this.dim.getclms(),this.dim.getrows());
        for (int i=0; i< this.dim.getrows(); i++){
           for (int j=0; j< this.dim.getrows(); j++){
               help.ele[i][j] = this.ele[j][i];
           }
        }
        return help;
    }


    @Override
    public int compareTo(Matrix o) {
        return 0;
    }
}
