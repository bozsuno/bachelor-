package com.company;

public class Calculation {

    public Vector VmM(Vector v,Matrix m){
       Vector n = new Vector(m.dim.getrows());

        for (int i=0; i < m.dim.getrows(); i++){
            int help = 0;
            for(int j=0; j< m.dim.getrows(); j++){
                help += v.ele[j]*m.ele[j][i];
            }
            n.ele[i] = help;
            n.isVertical = false;}

        return n;
    }

    public Vector MmV(Vector v,Matrix m) {
        Vector n = new Vector(m.dim.getrows());

        for (int i=0; i < m.dim.getrows(); i++){
            int help = 0;
            for(int j=0; j< m.dim.getrows(); j++){
                help += v.ele[j]*m.ele[i][j];
            }
            n.ele[i] = help;
            n.isVertical = true;
        }
          return n;
    }


    public double VmV(Vector h,Vector v){
        double n = 0;
        for (int i=0; i < h.dim; i++){
            n += h.ele[i]*v.ele[i];
        }
        return n;

    }

    public Matrix MmultM(Matrix m1,Matrix m2){
        Matrix res = new Matrix(m1.dim.getrows(),m2.dim.getclms());
        for (int i=0; i< m1.dim.getrows(); i++){
            for (int j=0; j< m2.dim.getclms(); j++){
                res.ele[i][j] = 0;
                for(int k=0; k< m2.dim.getclms(); k++){
                    res.ele[i][j] += m1.ele[i][k]*m2.ele[k][j];}
            }
        }
        return res;
    }

    public double dot(Vector v,Vector w){
        double m=0;
        for (int j=0;j< v.dim; j++){
            m+= v.ele[j]*w.ele[j];
        }
        return m;
    }

    public Vector project(Vector v,Vector w){
        w = w.norm();
        w.mult(dot(v,w));
        return w;
    }

    public Matrix getONBforL(Automata A){

        Vector[] B = new Vector[A.dim];

        Vector[] Temp = new Vector[A.dim];
        Temp[0] = A.init;
        int help=1;
        for (int i=1;i<A.dim;i++){
            if(i%2==1){ Temp[i]=VmM(Temp[help],A.A0);}
            else { Temp[i]=VmM(Temp[help],A.A1);
                    help++;}
        }
        int dimB=0;
        for (int i=0;i<A.dim;i++){
            B[i]=Temp[i];
            for (int j=i;j>0;j--){
                B[i].sub(project(B[i],B[j]));
            }
           if(B[i].isNull()==false) {dimB++;}
        }
        Matrix ONB = new Matrix(dimB);

        for(int i=0;i<dimB;i++){
            while(B[i].isNull()) {i++;}
            for(int j=0;j<A.dim;j++){

                ONB.ele[i][j] = B[i].getElement(j);
            }
        }
        return ONB;
    }

    public Matrix getONBforR(Automata A){

        Vector[] B = new Vector[A.dim];

        Vector[] Temp = new Vector[A.dim];
        Temp[0] = A.fin;
        int help=1;
        for (int i=1;i<A.dim;i++){
            if(i%2==1){ Temp[i]=MmV(Temp[help],A.A0);}
            else { Temp[i]=MmV(Temp[help],A.A1);
                help++;}
        }
        int dimB=0;
        for (int i=0;i<A.dim;i++){
            B[i]=Temp[i];
            for (int j=i;j>0;j--){
                B[i].sub(project(B[i],B[j]));
            }
            if(B[i].isNull()==false) {dimB++;}
        }
        Matrix ONB = new Matrix(dimB);

        for(int i=0;i<A.dim;i++){
            for(int j=0;j<dimB;j++){
                while(B[j].isNull()) {j++;}
                ONB.ele[i][j] = B[j].getElement(i);
            }
        }
        return ONB;
    }

    public boolean isMiniautomat(Automata A){
        if(A.dim==getONBforL(A).dim.getrows() && A.dim==getONBforR(A).dim.getclms()) return true;
        else return false;
    }

    public void minimize(Automata A){

        Automata res = A;

        int dimL = getONBforL(A).dim.getrows();
        int dimR = getONBforR(A).dim.getclms();
        Matrix B = getONBforL(A);
        Matrix C = getONBforR(A);


        while(isMiniautomat(res)==false) {
            if(dimL<=dimR){
                res = new Automata(dimL);
                res.init = VmM(A.init,B);
                res.A0 = MmultM(MmultM(B,A.A0),B.transpose());
                res.A1 = MmultM(MmultM(B,A.A1),B.transpose());
                res.fin = MmV(A.fin,B);
            }
            else{
                res = new Automata(dimR);
                res.init = VmM(A.init,C);
                res.A0 = MmultM(C.transpose(),MmultM(A.A0,C));
                res.A1 = MmultM(C.transpose(),MmultM(A.A1,C));
                res.fin = MmV(A.fin,C.transpose());
            }
        }
    }

    public Vector Pfinite(String s,Automata A){
        Vector res = A.init;
        String[] input = s.split("");
        for (String x: input){
            if(x.equals("1")){ res = VmM(res,A.A1); }
            else { if(x.equals("0")){ res = VmM(res,A.A1);}
            else System.out.print("Input not binary");}
        for(int i=0;i<s.length();i++){
                int j= s.charAt(i);
            if(j==0){res = VmM(res,A.A1); }
            else { if(x.equals("0")){ res = VmM(res,A.A1);}
            else System.out.print("Input not binary");}

        }

        }return res;
    }

    public double Ffinite(String s,Automata A){
        return VmV(Pfinite(s,A),A.fin);
    }


    public double f_real(String s,Automata A){
        double delta_d=-1;
        double d0 =0;
        double res =0;

        Vector pf = Pfinite(s,A);
        double f_0=VmV(pf,A.fin);
        pf = VmM(pf,A.A0);
        double f_n =VmV(pf,A.fin);
        double dn = f_n-f_0;

        while (delta_d < 0) {
            pf = VmM(pf,A.A0);
            f_n =VmV(pf,A.fin);
            d0 = dn;
            dn = f_n -f_0;
            f_0 = f_n;

            delta_d = dn - d0;

        }

        if (f_0==f_n) {res=f_n;}
        else res=-1;

        return res;
    }


    public double toDecimal(String s){
        double res=0;
        String[] bin = s.split("");
          return res;
    }
}
