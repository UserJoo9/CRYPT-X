package sample.classes;

import javax.crypto.Cipher;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

class  RSA {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private PublicKey CA_publicKey;
    private PublicKey sec_publicKey;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String PRIVATE_KEY_STRING;
    private String PUBLIC_KEY_STRING;
    private String CA_PUBLIC_KEY_STRING="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiTzq2C3i2EWOABO0MTN85ybtDMDXbnUVmLJw8y1PcN18+MREgLTv6YZ5c/WxIgcqA68sVc8yny6Jae9QG3OnxxY/bfQr/sQBGFU8mIX++L6x6cjFo5klW2L8QfoTPItVBJaj7h9emOcBxKlD1qJMqj1ms2+x0YPVPg2oolVisGwIDAQAB";
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String PRIVATE_KEY_PATH = "D:\\keys\\private.key";
    private String PUBLIC_KEY_STRINGPATH = "D:\\keys\\public.key";
    private String sec_PUBLIC_KEY_STRINGPATH = "D:\\keys\\sec_public.key";
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    File public_file = new File(PUBLIC_KEY_STRINGPATH);
    File private_file = new File(PRIVATE_KEY_PATH);
    File sec_public_file = new File(sec_PUBLIC_KEY_STRINGPATH);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    RSA() {

        try {
            if (public_file.getParentFile() != null) {
                public_file.getParentFile().mkdirs();
            }
            if (!public_file.exists()) {
                public_file.createNewFile();
            }
            if (!private_file.exists()) {
                private_file.createNewFile();
            }
            if (!sec_public_file.exists()) {
                sec_public_file.createNewFile();

            }

        } catch (Exception v) {
        }
    }


    public void init() {
        try {

            PrintWriter public_writer = new PrintWriter(public_file);
            PrintWriter private_writer = new PrintWriter(private_file);

            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024);
            KeyPair pair = generator.generateKeyPair();
            privateKey = pair.getPrivate();
            publicKey = pair.getPublic();
            public_writer.println(encode(publicKey.getEncoded()));
            private_writer.println(encode(privateKey.getEncoded()));
            private_writer.close();
            public_writer.close();

        } catch (Exception ignored) {

        }
    }

    public void setSec_publicKey_file(String sec_PUBLIC_key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            PrintWriter sec = new PrintWriter(sec_PUBLIC_KEY_STRINGPATH);
            sec.println(sec_PUBLIC_key);
            sec.close();
            initFromStrings();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setSec_public(String sec_public) throws FileNotFoundException {
       File f1=new File(sec_PUBLIC_KEY_STRINGPATH) ;
       PrintWriter pw=new PrintWriter(f1)  ;
       pw.print(sec_public);
       pw.close();
    }

    public boolean check_Sec_publicKey() {
        boolean state=false;
        try {
            Scanner sec_public = new Scanner(sec_public_file);
            if (!sec_public.hasNext()) {
              state=true;
            }
        } catch (Exception e) {
        }
        return state;
    }


    public void initFromStrings() throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
        RSA rsa = new RSA();
        Scanner in = new Scanner(System.in);

        Scanner public_scanner = new Scanner(public_file);
        Scanner private_scanner = new Scanner(private_file);
        Scanner sec_public_scanner = new Scanner(sec_public_file);
        if (!private_scanner.hasNext() || !public_scanner.hasNext()) {
            init();
            JOptionPane.showMessageDialog(null,"there was no essential keys so \n new keys has been generated");
        }


        String Public = public_scanner.nextLine();
        String sec_Public = sec_public_scanner.nextLine();
        String Private = private_scanner.nextLine();
        private_scanner.close();
        public_scanner.close();
        sec_public_scanner.close();
        X509EncodedKeySpec keySpecPublic = new X509EncodedKeySpec(decode(Public));
        X509EncodedKeySpec keySpec_sec_sePublic = new X509EncodedKeySpec(decode(sec_Public));
        PKCS8EncodedKeySpec keySpecPrivate = new PKCS8EncodedKeySpec(decode(Private));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        publicKey = keyFactory.generatePublic(keySpecPublic);
        sec_publicKey = keyFactory.generatePublic(keySpec_sec_sePublic);
        privateKey = keyFactory.generatePrivate(keySpecPrivate);

}



    public String printKeys() throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
        initFromStrings();
        System.err.println("public key\n"+ encode(publicKey.getEncoded()));
        System.err.println("Private key\n"+ encode(privateKey.getEncoded()));
        System.err.println("sec_public key\n"+ encode(sec_publicKey.getEncoded()));
        return encode(publicKey.getEncoded());
    }

    public String encrypt(String message) throws Exception {
        RSA rsa = new RSA();
        initFromStrings();
        byte[] messageToBytes = message.getBytes();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, sec_publicKey);
        byte[] encryptedBytes = cipher.doFinal(messageToBytes);
        return encode(encryptedBytes);
    }
    public String decrypt(String encryptedMessage) throws Exception {
        RSA rsa = new RSA();
        initFromStrings();
        byte[] encryptedBytes = decode(encryptedMessage);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedMessage = cipher.doFinal(encryptedBytes);
        return new String(decryptedMessage, "UTF8");
    }
    public String segnet_encrypt(String sha) throws Exception {
        initFromStrings();
        byte[] messageToBytes = sha.getBytes();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] encryptedBytes = cipher.doFinal(messageToBytes);
        return encode(encryptedBytes);
    }
    public String segnet_decrypt(String encrypted_Sha) throws Exception {
        initFromStrings();
        byte[] encryptedBytes = decode(encrypted_Sha);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedMessage = cipher.doFinal(encryptedBytes);
        return new String(decryptedMessage, "UTF8");
    }
    public String CA_decrypt(String encrypted_ca) throws Exception {

        X509EncodedKeySpec CA_keySpecPublic = new X509EncodedKeySpec(decode(CA_PUBLIC_KEY_STRING));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        CA_publicKey = keyFactory.generatePublic(CA_keySpecPublic);
        byte[] encryptedBytes = decode(encrypted_ca);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, CA_publicKey);
        byte[] decryptedMessage = cipher.doFinal(encryptedBytes);
        return new String(decryptedMessage, "UTF8");
    }
    private static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
    private static byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }

    String[] shaSplit(String Sha){
        char[]sha_char=Sha.toCharArray();
        String p1="";
        String p2="";
        String[]shaArr= {"",""};

        for (int i=0;i<sha_char.length;i++){
            if (i<100) {
                p1 += sha_char[i];
            }else {
                p2 += sha_char[i];
            }
        }
        shaArr[0]=p1;
        shaArr[1]=p2;
        return shaArr;
    }
    String connect_sha(String [] shaArr){
        return (shaArr[0]+shaArr[1]);
    }

    public String HashThisString(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA3-512");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext;
            for(hashtext = no.toString(16); hashtext.length() < 32; hashtext = "0" + hashtext) {
            }
            return hashtext;
        } catch (NoSuchAlgorithmException var5) {
            throw new RuntimeException(var5);
        }
    }

    public String[] hash_enc(String Hash){
        String[]enc_hash=shaSplit(Hash);
        try {
            enc_hash[0]=segnet_encrypt(enc_hash[0]);
            enc_hash[1]=segnet_encrypt(enc_hash[1]);
        }catch (Exception d){}
        return enc_hash;
    }
    public String hash_dec(String []enc_hash){
        String m[]=enc_hash;
        try {
            m[0]=segnet_decrypt(m[0]);
            m[1]=segnet_decrypt(m[1]);
        }catch (Exception d){}
        return connect_sha(m);
    }
    public String CA_dec(String []enc_hash){
        String m[]=enc_hash;
        try {
            m[0]=CA_decrypt(m[0]);
            m[1]=CA_decrypt(m[1]);
        }catch (Exception d){}
        return connect_sha(m);
    }
    String[] SeGSplit(String Sha){
        char[]sha_char=Sha.toCharArray();
        String p1="";
        String p2="";
        String[]shaArr= {"",""};

        for (int i=0;i<sha_char.length;i++){
            if (i<172) {
                p1 += sha_char[i];
            }else {
                p2 += sha_char[i];
            }
        }
        shaArr[0]=p1;
        shaArr[1]=p2;
        return shaArr;
    }
    public String encryption(String Text) throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
        RSA rsa = new RSA();
        initFromStrings();
        String cyphered="";
        try {
            cyphered=rsa.encrypt(Text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cyphered;
    }
    public String decryption(String Enc_text)throws Exception {
        RSA rsa = new RSA();
        initFromStrings();
        String dec_text ="";
        dec_text=decrypt(Enc_text) ;
        return dec_text;
    }

}