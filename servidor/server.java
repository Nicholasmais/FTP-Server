import java.net.*;

import java.io.*;

public class server  {
      int pt;
      ServerSocket ssocket;
      String lercaixa;
      PrintStream  soutput;
      OutputStream  soutput2, soutput3;
      InputStream in, in2;
      BufferedReader sinput;
      Socket csk,clisoket, socket;
      byte[] bytes, bytes2;
      int count, count2;
      FileInputStream fis = null;
      BufferedInputStream bis = null;

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
                  if(!arr[2].equals(this.user1) || !arr[3].equals(this.pass1)){
                    soutput.println("Credenciais incorretas.");               
                  }
                  else{
                    soutput.println("true");               
                  }
                }
                else{
                  System.out.println(arr[0]);
                  switch(arr[0]){    
                    case "!put":
                      soutput.println("Server response: File "+arr[1]+" uploaded succefully.");               
                      soutput2 = new FileOutputStream("servidor/"+arr[1]);
                      bytes = new byte[16*1024];
                      in = clisoket.getInputStream();
                      while ((count = in.read(bytes)) > 0) {
                        soutput2.write(bytes, 0, count);
                      }
                      break;
                                        
                    case "!get":
                      soutput.println("Server response: File "+arr[1]+" downloaded succefully.");                                          
                      soutput2 = new FileOutputStream(arr[4] + arr[1]);
                      bytes2 = new byte[16*1024];
                      InputStream file = new FileInputStream("servidor/"+arr[1]);
                      while ((count2 = file.read(bytes2)) > 0) {
                        soutput2.write(bytes2, 0, count2);
                      }
                      break;                    
                    
                    default:
                      soutput.println("Server response: Invalid command." + arr[0]);
                      break;
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