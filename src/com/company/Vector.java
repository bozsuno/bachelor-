package com.company;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static java.lang.Math.sqrt;

public class Vector implements Comparable<Vector> {

    protected int dim;
    protected double[] ele;
    protected boolean isVertical;

    public Vector(int n){
        dim=n;
    }


    public double getElement(int n){
        return this.ele[n-1];
    }

    public void setElement(int n, int value){
        this.ele[n-1] = value;
    }


    public void setElement(String element){
        Pattern comma = Pattern.compile(",");
        Matcher Comma = comma.matcher(element);
        int countComma = 0;
        while(Comma.find()) countComma++;
        int dimension = countComma+1;
        if (dimension!=this.dim) {}//throw error
        else
        if (element.matches("([0-9]+[,[0-9]+]*)+")){
            String[] help = element.split(",");
            for (int i=0; i<this.dim ; i++){
                this.ele[i] = Integer.valueOf(help[i]);
            }

        }
        else {
            throw new IllegalArgumentException("String doesn't match");
        }
    }

    boolean isNull(){

        boolean res = true;
        for(int i=0;i<this.dim&&res;i++){
            if(this.ele[i]!=0) res=false;
        }
        return res;
    }

    public void mult(double v){
        for(int i=0;i<this.dim;i++){
            this.ele[i]= this.ele[i]*v;
    }}

    public void div(double v){
        for(int i=0;i<this.dim;i++){
            this.ele[i]= this.ele[i]/v;
        }}


    public void sub(Vector v){
        for(int i=0;i<this.dim;i++){
            this.ele[i]= this.ele[i]-v.ele[i];
        }}

    public void print() {
        int i = 0;
        while (i < dim) {
            System.out.println(Arrays.toString(new double[]{this.ele[i]}));
            i++;
        }
    }

    public double lenght(){
        double l=0;
        for (int i=0;i<this.dim;i++){
            l+= this.ele[i]*this.ele[i];
        }
        return sqrt(l);
    }

    public Vector norm(){
        Vector n = new Vector(this.dim);
        n.div(this.lenght());
        return n;
    }
    @Override
    public int compareTo(Vector o) {
        return 0;
    }
}
