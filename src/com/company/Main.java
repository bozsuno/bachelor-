package com.company;
import java.util.Scanner;

import java.io.*;
import com.company.Matrix;
import com.company.Vector;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);
        System.out.println("Key a dimension of automata");
        int dim = input.nextInt();
        System.out.println("You entered " + dim);

        Automata A = new Automata(dim);

        String I_input;

       try{
          BufferedReader temp = new BufferedReader(new InputStreamReader(System.in));
           System.out.println("Key a initial distribution");
            I_input = temp.readLine();
          System.out.print(I_input);

         

       }
        catch (IOException e){
          System.out.print(e);
     }



       // int dim = Dim_input;
       // Automata A = new Automata(Dim_input);


    }
}
