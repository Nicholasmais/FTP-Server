import java.net.*;
import java.io.*;

public class server  {
      int pt;
      ServerSocket ssocket;
      String lercaixa;
      PrintStream  soutput;
      OutputStream  soutput2;
      InputStream in;
      BufferedReader sinput;
      Socket csk,clisoket, socket;

      private String user1 = "user";
      private String pass1 = "1234";

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
              while (true){
                clisoket = ssocket.accept();
                csk = clisoket;

                sinput = new BufferedReader(new InputStreamReader(csk.getInputStream()));
                soutput = new PrintStream(csk.getOutputStream());  // Envia uma sequencia de bytes para Cliente
            
                String men = sinput.readLine(); // pega a linha Enviada pelo Cliente
                String[] arr = men.split(",");

                if (arr[0].equals("false")){
                  if(!arr[1].equals(this.user1) || !arr[2].equals(this.pass1)){
                    soutput.println("Credenciais incorretas.");               
                  }
                  else{
                    soutput.println("true");               
                  }
                }
                else{               
                  soutput.println("Server response: File "+arr[0]+" uploaded succefully.");               
                  soutput2 = new FileOutputStream("servidor/"+arr[0]);  // Envia uma sequencia de bytes para Cliente

                  byte[] bytes = new byte[16*1024];
                  in = clisoket.getInputStream();
                  int count;
                  while ((count = in.read(bytes)) > 0) {
                    soutput2.write(bytes, 0, count);                    
                  }                                                           
                }          
              }       
            }
            catch(Exception e) {
               System.err.println(e);
            }
        }

       public static void main(String args[]) {
          new server();
       }

  }