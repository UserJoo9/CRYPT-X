package sample.security;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;


public class register {

    String user;
    String password;

    register(String user,  String password){
        this.user=user;
        this.password=password;
    }

    String getUser(){
        return user;
    }

    String getPassword(){
        return password;
    }


    void tofile(){
     try {

         PrintWriter pw=new PrintWriter("C:\\cryptxRegistration.txt");
         pw.println(user+":"+password);
         pw.close();
     }catch (Exception n){
         n.printStackTrace();
     }
    }


    public void readOutWriteLogin(String newAccount) {
        try {
            File f1 = new File("C:\\cryptxRegistration.txt");
            File f2 = new File("C:\\cryptxRegistrationTemp.txt");
            if(!f2.exists()&& f1.exists()){
                f2.createNewFile();
                f1.createNewFile();
            }
            PrintWriter pw = new PrintWriter(f1);
            Scanner sc = new Scanner(f2);
            while (sc.hasNext()) {
                String fileRec = sc.nextLine();
                pw.println(fileRec);
            }
            pw.println(newAccount);
            pw.close();
            sc.close();
        } catch (Exception c) {
//            System.out.println(c);
        }
    }


    public void readLoginWriteOut() {
        try {
            File f1 = new File("C:\\cryptxRegistration.txt");
            File f2 = new File("C:\\cryptxRegistrationTemp.txt");
            if(!f2.exists()&& f1.exists()){
                f2.createNewFile();
                f1.createNewFile();
            }
            PrintWriter pw = new PrintWriter(f2);
            Scanner sc = new Scanner(f1);
            while (sc.hasNext()) {
                String fileRec = sc.nextLine();
                pw.println(fileRec);
            }

            pw.close();
            sc.close();
        } catch (Exception c) {
//            System.out.println(c);
        }
    }



}
