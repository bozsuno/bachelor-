package com.company;

public class Calculation {
    /**
     * Multiplication of a vector to a matrix
     * @param v a row vector
     * @param m a matrix
     * @return a row vector as a product of multiplication
     */
    private Vector VmM(Vector v,Matrix m){
       if(v.isVertical==true) throw new IllegalArgumentException("Vector must be a row vector");
       else
       {
        if(v.dim!=m.dim.getrows()) throw new IllegalArgumentException("Dimensions don't match");
        else
        {
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
       }
    }

    /**
     * Multiplication of a matrix to a vector
     * @param v a column vector
     * @param m a matrix
     * @return a column vector as a product of multiplikation
     */
    private Vector MmV(Vector v,Matrix m) {
        if(v.isVertical!=true) throw new IllegalArgumentException("Vector must be a column vector");
        else
        {
            if(v.dim!=m.dim.getclms()) throw new IllegalArgumentException("Dimensions don't match");
            else
            {
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
        }
    }

    /**
     *dot product of multiplication of 2 vectors
     * @param h a vector
     * @param v a vector
     * @return scala result of dot product
     */
    private double VmV(Vector h,Vector v){
        if(h.dim!=v.dim) throw new IllegalArgumentException("Dimensions don't match");
        else {
            double n = 0;
            for (int i = 0; i < h.dim; i++) {
                n += h.ele[i] * v.ele[i];
            }
            return n;
        }
    }

    /**
     * Matrix multiplication
     * @param m1 a matrix
     * @param m2 a matrix
     * @return a matrix as a product of multiplication
     */
    private Matrix MmultM(Matrix m1,Matrix m2){
        if(m1.dim.getclms()!=m2.dim.getrows()) throw new IllegalArgumentException("Dimensions don't match");
        else {
            Matrix res = new Matrix(m1.dim.getrows(), m2.dim.getclms());
            for (int i = 0; i < m1.dim.getrows(); i++) {
                for (int j = 0; j < m2.dim.getclms(); j++) {
                    res.ele[i][j] = 0;
                    for (int k = 0; k < m2.dim.getclms(); k++) {
                        res.ele[i][j] += m1.ele[i][k] * m2.ele[k][j];
                    }
                }
            }
            return res;
        }
    }

    /**
     * Vector projection
     * @param v a vector
     * @param w a vector
     * @return
     */
    private Vector project(Vector v,Vector w){
        w = w.norm();
        w.mult(VmV(v,w));
        return w;
    }

    /**
     * Compute the orthonormal basis of subspace <IA>
     * @param A a Automata
     * @return a matrix consisting of all orthonormal basis
     */
    private Matrix getONBforL(Automata A){

        Vector[] B = new Vector[A.dim];
//generates a list of subspace <IA>
        Vector[] Temp = new Vector[A.dim];
        Temp[0] = A.init;
        int help=1;
        for (int i=1;i<A.dim;i++){
            if(i%2==1){ Temp[i]=VmM(Temp[help],A.A0);}
            else { Temp[i]=VmM(Temp[help],A.A1);
                    help++;}
        }
        //using Gram-Schmidt Algorithmus
        int dimB=0;
        for (int i=0;i<A.dim;i++){
            B[i]=Temp[i];
            for (int j=i;j>0;j--){
                B[i].sub(project(B[i],B[j]));
                B[i].norm();
            }
           if(B[i].isNull()==false) {dimB++;}
        }
        Matrix ONB = new Matrix(dimB);
        //create a new matrix with all orthonormal basis
        for(int i=0;i<dimB;i++){
            while(B[i].isNull()) {i++;}
            for(int j=0;j<A.dim;j++){

                ONB.ele[i][j] = B[i].getElement(j);
            }
        }
        return ONB;
    }
    /**
     * Compute the orthonormal basis of subspace <AF>
     * @param A a Automata
     * @return a matrix consisting of all orthonormal basis
     */
    private Matrix getONBforR(Automata A){

        Vector[] B = new Vector[A.dim];
//generates a list of subspace <AF>
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

    /**
     * Check whatif an Automata is minimal
     * @param A an Automata
     * @return
     */
    public boolean isMiniautomat(Automata A){
        if(A.dim==getONBforL(A).dim.getrows() && A.dim==getONBforR(A).dim.getclms()) return true;
        else return false;
    }

    /**
     * Find a minimal Automata equivalent to the Automata
     * @param A an Automata
     */
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

    /**
     * Compute a sub-function P = IA
     * @param s an Input-String
     * @param A an Automata
     * @return a row vector
     */
    public Vector Pfinite(String s,Automata A){
        Vector res = A.init;
        String[] input = s.split("");
        for (String x: input){
            if(x.equals("1")){ res = VmM(res,A.A1); }
            else { if(x.equals("0")){ res = VmM(res,A.A0);}
            else System.out.print("Input not binary");}
        }
        return res;
    }

    /**
     * Compute a word function F = PF = IAF
     * @param s an Input-String
     * @param A an Automata
     * @return a number as a result
     */
    private double Ffinite(String s,Automata A){

        return VmV(Pfinite(s,A),A.fin);
    }

    /**
     * Compute a real function f = lim F
     * @param s an Input-String
     * @param A an Automata
     * @return
     */
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

    /**
     * Convert an input in binary representation to a decimal number
     * @param s a binary Input-String
     * @return a decimal result
     */
    private double toDecimal(String s){
        double res=0;
        String[] input = s.split("");
        int i=0;
        for (String x: input){
          if(x.equals("1")){ res+= Math.pow(2,(-(1+i))); }
          i++;
       }
       return  res;
    }

    /**
     * Generate a list of input from 00000000 to 111111111 and compute
     * for each input a result of real function.
     * @param A an Automata
     */
    public void drawGraph(Automata A){
        StringBuffer zero = new StringBuffer("0000000000");
        double x,y;
        String input;
     for(int i=0;i<=1023;i++) {
         String digit = Integer.toBinaryString(i);
         zero.append(digit);
         input=zero.delete(0, digit.length()).toString();

         y=f_real(input,A);
         x= toDecimal(input);

     }
    }
}
