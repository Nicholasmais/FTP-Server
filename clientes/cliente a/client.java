import java.net.*;
import java.io.*;

class client {
    BufferedReader br = null;
    BufferedReader cinput;
    PrintStream coutput;
    Socket clisoc;
    boolean auth = false;
    String user, pass;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    int bytesRead, numberOfFiles;
    int current = 0;
    String fileName = "";
    String [] files;
    private String dir = "clientes/cliente a/";
    public client() {
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

            coutput.println(Boolean.toString(auth)+ "," + "_" + "," + "0" + "," + user + "," + pass + "," + this.dir); 
            String conn = cinput.readLine();
            auth = Boolean.valueOf(conn);
            if (!auth){
              System.out.println(conn);
            }
          }
          else{
            System.out.println("!get para baixar arquivo.\n!mget para baixar múltiplos arquivos.\n!put para enviar arquivo.\n!mput para enviar múltiplos arquivos:");
            String method = br.readLine();
            
            while (true){
              System.out.println("Nome do arquivo:");
              String file = br.readLine();
              fileName += file + ";";
              if (file.equals("0") || method.indexOf("m") == -1){
                fileName = fileName.substring(0, fileName.length()-2);
                numberOfFiles = fileName.split(",").length;
                files = fileName.split(";");
                break;
              }
            }            
            coutput.println(method + "," + fileName + "," + Integer.toString(numberOfFiles) + "," + user + "," + pass + "," + this.dir); // Envia Para o Servidor !!
            
            if (method.indexOf("put") != -1){
              for (int i = 0; i < numberOfFiles; i++){
                byte[] bytes = new byte[16 * 1024];
                File file = new File(this.dir + files[i]);
                InputStream in = new FileInputStream(file);
                
                int count;
                while ((count = in.read(bytes)) > 0) {
                  coutput.write(bytes, 0, count);
                }
                in.close();  
              }
            }
      
            String str = cinput.readLine(); // Recebe dados do servidor
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
