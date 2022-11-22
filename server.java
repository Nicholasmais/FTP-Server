import java.net.*;
import java.io.*;

public class server  {
      int pt;
      ServerSocket ssocket;
      String lercaixa;
      PrintStream  soutput;
      BufferedReader sinput;
      Socket csk,clisoket;

      public server() {
          //  Programa Servidor
           pt = 4321;
           try  // Excecao
           {
              ssocket = new ServerSocket(pt);

           }
           catch(Exception e) {
              System.err.println(e.getMessage());
              System.exit(0);
           }
			System.out.println("Servidor Ativo ..... ");
            try {
            while(true) {
              clisoket = ssocket.accept();
              csk = clisoket;
              soutput = new PrintStream(csk.getOutputStream());  // Envia uma sequencia de bytes para Cliente
              sinput = new BufferedReader(new InputStreamReader(csk.getInputStream()));

              String men = sinput.readLine(); // pega a linha Enviada pelo Cliente
              System.out.println("Cliente Envia : "+men);
              soutput.println("Servidor Envia : Ola Recebido !!! " + men);                                                     
              }                 
            }
            catch(Exception e) {
               System.err.println(e.getMessage());
            }
        }

       public static void main(String args[]) {
          new server();
       }

  }