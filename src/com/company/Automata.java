package com.company;




public class Automata {
    protected int dim;
    protected Vector init;
    protected Matrix A0;
    protected Matrix A1;
    protected Vector fin;

    public Automata(int n){
     dim=n;
        //this.init.dim =n;
       // this.init.isVertical=false;
       // this.fin.dim =n;
      //  this.fin.isVertical=true;
    }

     public void set_init(Vector v){
        if ( v.isVertical == true ){
            if(this.dim==v.dim)
        this.init = v;}
        else; //throws
     }

     public void set_fin(Vector v){
         if ( v.isVertical == false){
             this.init = v;}
         else; //throws
     }

     public void set_A0(Matrix m){
         if (m.dim.getrows() == this.dim){
             this.A0 = m;
         }
     }

    public void set_A1(Matrix m){
        if (m.dim.getrows() == this.dim){
            this.A1 = m;
        }
    }
}
