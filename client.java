import java.lang.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;

class client {

    BufferedReader cinput;
     PrintStream coutput;
     Socket clisoc;

     public client(String mensagem) {
      //while (true){
           try {
              clisoc = new Socket("127.0.0.1",4321);
              cinput = new BufferedReader(new InputStreamReader(clisoc.getInputStream()));
              coutput = new PrintStream(clisoc.getOutputStream());
              
              String str = mensagem;
              coutput.println(str); // Envia Para o Servidor !!

              String str2 = cinput.readLine(); // Recebe dados do servidor
              System.out.println(str2);              
           }
         catch(Exception e)
         {
             System.err.println(e);
         }
        }
       //}
        public static void main(String args[]){
          new client("teste");
        }

     }
