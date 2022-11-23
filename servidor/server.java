import java.net.*;

import java.io.*;

public class server  {
  int pt, count, count2, numFiles;
  ServerSocket ssocket;
  String lercaixa, men;
  String [] arr, files;
  PrintStream  soutput;
  OutputStream  soutput2, soutput3;
  InputStream in, in2, file;
  BufferedReader sinput;
  Socket csk,clisoket, socket;
  byte[] bytes, bytes2;      
  FileInputStream fis = null;
  BufferedInputStream bis = null;
  
  private String user1 = "user";
  private String pass1 = "1234";

  public server() {
    pt = 4321;
    try{
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
        soutput = new PrintStream(csk.getOutputStream());
    
        men = sinput.readLine();
        arr = men.split(",");
        files = arr[1].split(";");
        numFiles = Integer.parseInt(arr[2]);
        
        if (arr[0].equals("false")){
          if(!arr[3].equals(this.user1) || !arr[4].equals(this.pass1)){
            soutput.println("Credenciais incorretas.");               
          }
          else{
            soutput.println("true");               
          }
        }

        else{
          switch(arr[0]){    
            case "!put":
            case "!mput":
              for (int i = 0; i <= numFiles; i++){
                soutput.println("Server response: File "+files[i]+" uploaded succefully.");               
                soutput2 = new FileOutputStream("servidor/"+files[i]);
                bytes = new byte[16*1024];
                in = clisoket.getInputStream();
                while ((count = in.read(bytes)) > 0) {
                  soutput2.write(bytes, 0, count);
                }
              }
              break;                                        
            case "!get":
            case "!mget":
              for (int i = 0; i <= numFiles; i++){
                soutput.println("Server response: File "+files[i]+" downloaded succefully.");                                          
                soutput2 = new FileOutputStream(arr[5] + files[i]);
                bytes2 = new byte[16*1024];
                file = new FileInputStream("servidor/"+files[i]);
                while ((count2 = file.read(bytes2)) > 0) {
                  soutput2.write(bytes2, 0, count2);
                }
              }
              break;            
            default:
              soutput.println("Server response: Invalid command." + arr[0] + "\n");
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