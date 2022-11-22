import java.net.*;
import java.io.*;

class clientb {
     BufferedReader br = null;
     BufferedReader cinput;
     PrintStream coutput;
     Socket clisoc;

     public clientb() {
      while (true){
           try {
              clisoc = new Socket("127.0.0.1",4321);
              InputStreamReader isr = new InputStreamReader(System.in);
              br = new BufferedReader(isr);
              cinput = new BufferedReader(new InputStreamReader(clisoc.getInputStream()));
              coutput = new PrintStream(clisoc.getOutputStream());
              
              System.out.println("Nome do arquivo:");
              String str = br.readLine();

              File file = new File("clientes/cliente b/" + str);

              byte[] bytes = new byte[16 * 1024];
              coutput.println(str); // Envia Para o Servidor !!
              InputStream in = new FileInputStream(file);
              
              int count;
              while ((count = in.read(bytes)) > 0) {
                coutput.write(bytes, 0, count);
              }

              String str2 = cinput.readLine(); // Recebe dados do servidor
              System.out.println(str2);
              in.close();             
           }
         catch(Exception e)
         {
             System.err.println(e);
         }
        }
       }
        public static void main(String args[]){
          new clientb();
        }

     }