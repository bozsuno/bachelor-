package com.company;
import java.util.Scanner;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.company.Matrix;
import com.company.Vector;

public class Main {

    public static void main(String[] args) throws IOException {

       // Scanner input = new Scanner(System.in);
       // System.out.println("Key a dimension of automata");
        //int dim = input.nextInt();
       // System.out.println("You entered " + dim);

       // Automata A = new Automata(dim);

        String I_input;
        /**
       try{
          BufferedReader temp = new BufferedReader(new InputStreamReader(System.in));
           System.out.println("Key a initial distribution");
            I_input = temp.readLine();
          System.out.print(I_input);
           String[] input1 = I_input.split("");
*/
          //test input matrix
             String element="14,222;3,4;5,6;";
           Pattern comma = Pattern.compile(";");
           Matcher Comma = comma.matcher(element);
           int countComma = 0;
           while(Comma.find()) countComma++;
            if (element.matches("([0-9]+[,[0-9]+]*[;])+")){
               String[] help = element.split(";");
               for (int i=0; i<countComma+1; i++){
                   System.out.println(Integer.valueOf(help[i]));
               }
               }





      // }
     //   catch (IOException e){
   //       System.out.print(e);
   // }



       // int dim = Dim_input;
       // Automata A = new Automata(Dim_input);


    }
}
