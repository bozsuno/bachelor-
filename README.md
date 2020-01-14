# bachelor-
Programm must allow -to input "Automata" also an Object with 5 Elements
a Automata consist of 1. Dimension n (Integer) 
                      2. Init. Vector(double[]) of lenght = n
                      3. Matrix A0 (double[][]) n x n Matrix for Input = 0
                      4. Matrix A1 (double[][]) n x n Matrix for Input = 1
                      5. Final Vector(double[]) of lenght = n
Initial is to input 1.n Dimension 
then another Arrays Objects, which pattern of input must be 
= double,double, ... also using "comma" to devide each element of Vector
and for Matrix using "semi-collon" to devide each row 

firstly after receiving an String-input, program have to check if number of elements matches "n (Dimension)" given before

After all elements of Automata are correctly received and saved,

then programm have to 

<first>
-calculate Function F(finite) and f(infinite) for String-input from 000000000 - 111111111 (0-2^10)
and then plot a 2D xy-Graph, 

x =  0.input to decimal
y =  output of function f of input

<second
 -Check if this recieved Automata "minimal" by prosedure
  
  = Yes : output => already minimal
  = No : minimize Automat til get a minimal Automat => output a new minimal Automat 

                     
