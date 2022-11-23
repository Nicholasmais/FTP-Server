import java.net.*;
import java.io.*;

class clientb {
  BufferedReader br = null;
  BufferedReader cinput;
  PrintStream coutput;
  Socket clisoc;
  boolean auth = false;
  String user, pass;
  FileOutputStream fos = null;
  BufferedOutputStream bos = null;
  int bytesRead;
  int current = 0;
  private String dir = "clientes/cliente b/";
  public clientb() {
    try {
      while (true){
        clisoc = new Socket("127.0.0.1",4321);
        InputStreamReader isr = new InputStreamReader(System.in);
        br = new BufferedReader(isr);
        cinput = new BufferedReader(new InputStreamReader(clisoc.getInputStream()));
        coutput = new PrintStream(clisoc.getOutputStream());  
        if (!auth){                        
          System.out.println("Login:");
          user = br.readLine();

          System.out.println("Senha:");
          pass = br.readLine();

          coutput.println(Boolean.toString(auth)+ "," + "_" + "," + user + "," + pass + "," + this.dir); 
          String conn = cinput.readLine();
          auth = Boolean.valueOf(conn);
          if (!auth){
            System.out.println(conn);
          }
        }
        else{
          System.out.println("!get para baixar arquivo. !put para enviar arquivo:");
          String method = br.readLine();

          System.out.println("Nome do arquivo:");
          String fileName = br.readLine();

          File file = new File(this.dir + fileName);
          byte[] bytes = new byte[16 * 1024];
          coutput.println(method + "," + fileName + "," + user + "," + pass + "," + this.dir); // Envia Para o Servidor !!
          
          if (method.equals("!put")){
            InputStream in = new FileInputStream(file);
            
            int count;
            while ((count = in.read(bytes)) > 0) {
              coutput.write(bytes, 0, count);
            }
            in.close();  
          }
    
          String str = cinput.readLine(); // Recebe dados do servidor
          System.out.println(str);
        }
      }
    }
    catch(Exception e){
        System.err.println(e);
    }
  }

  public static void main(String args[]){
    new clientb();
  }
  }
