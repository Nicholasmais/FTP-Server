import java.net.*;
import java.io.*;

class client {
  private BufferedReader br = null;
  private BufferedReader cinput;
  private PrintStream coutput;
  private InputStream in;
  private InputStreamReader isr;
  private OutputStream  soutput;
  private Socket clisoc;
  private boolean auth = false;
  private String user, pass, conn, method, file;
  private String fileName = "";
  private String [] files;
  private int numberOfFiles, count;
  private byte[] bytes;
  private File arq;
  
  private String dir = "clientes/cliente a/";

  public client() {
    try {
      while (true){
        clisoc = new Socket("127.0.0.1",4321);
        isr = new InputStreamReader(System.in);
        br = new BufferedReader(isr);
        cinput = new BufferedReader(new InputStreamReader(clisoc.getInputStream()));
        coutput = new PrintStream(clisoc.getOutputStream());  
        if (!auth){                        
          System.out.println("Login:");
          user = br.readLine();

          System.out.println("Senha:");
          pass = br.readLine();

          coutput.println(Boolean.toString(auth)+ "," + "_" + "," + "0" + "," + user + "," + pass + "," + this.dir); 
          
          conn = cinput.readLine();
          auth = Boolean.valueOf(conn);
          if (!auth){
            System.out.println(conn);
          }
        }
        else{
          System.out.println("!get para baixar arquivo.\n!mget para baixar múltiplos arquivos.\n!put para enviar arquivo.\n!mput para enviar múltiplos arquivos:");
          method = br.readLine();
          
          while (true){
            System.out.println("Nome do arquivo (0 para sair):");
            file = br.readLine();
            fileName += file + ";";
            if (file.equals("0") || method.indexOf("m") == -1){
              fileName = fileName.substring(0, fileName.length()-1);
              numberOfFiles = fileName.split(",").length;
              files = fileName.split(";");
              break;
            }
          }            
          coutput.println(method + "," + fileName + "," + Integer.toString(numberOfFiles) + "," + user + "," + pass + "," + this.dir); // Envia Para o Servidor !!
          
          if (method.indexOf("put") != -1){
            for (int i = 0; i < numberOfFiles; i++){
              bytes = new byte[16 * 1024];
              arq = new File(this.dir + files[i]);
              in = new FileInputStream(arq);                  
              while ((count = in.read(bytes)) > 0) {
                coutput.write(bytes, 0, count);
              }
              in.close();  
            }
          }

          if (method.indexOf("get") != -1){
            for (int i = 0; i < numberOfFiles; i++){
              soutput = new FileOutputStream(this.dir + files[i]);
              bytes = new byte[16*1024];
              in = clisoc.getInputStream();
              while ((count = in.read(bytes)) > 0) {
                soutput.write(bytes, 0, count);
              }
              System.out.println("Saiu?");
            }
          }

          String str = cinput.readLine();
          System.out.println(str + "\n");
        }
      }
    }
    catch(Exception e){
        System.err.println(e);
    }
  }
  public static void main(String args[]){
    new client();
  }
}
