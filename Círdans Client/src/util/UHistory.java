package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import protocol.model.SMessage;

/**
 *
 * @author Marcelo Gomes Martins
 */
public class UHistory {
    
    // Referencia prórpia
    private static UHistory uhistoric;
    
    private UHistory() {
        File path = new File(base);
        path.mkdirs();
    }
    
    public static UHistory getInstance(){
        if(uhistoric == null) {
            uhistoric = new UHistory();
        }
        return uhistoric;
    }
    
    // Arquivo
    private File file;
    // Le dados do arquivo
    private ObjectInputStream reader;
    // Escreve dados do arquivo
    private ObjectOutputStream  writer;
    // Nome base
    private final String base = "messages\\history\\";
    
    
    
    /**
     * Grava mensagem em um arquivo de registro
     * proprio para cada usuário
     * @param message enviada pelo usuário
     */
    public void record(SMessage message, boolean byMe){
        if(message.getSenderId() != null && message.getRecieverId() != null){
            String filename = null;
            try {
                if(byMe){
                    filename = base + message.getSenderId() + "_" + message.getRecieverId() + ".crg";
                } else {
                    filename = base + message.getRecieverId() + "_" + message.getSenderId() + ".crg";
                }
                this.writeBinary(filename, message);
            } catch (IOException ex) {
                System.out.println("record@Historic");
            }
        }
    }
    
    /**
     * Recupera string de relatorio de uma mensagem
     * @param userId id do usuário
     * @return lista de strings
     */
    public ArrayList<String> recover(long myId, long userId){
        ArrayList<String> records = null;
        try {
            String filename = base + myId + "_" + userId + ".crg";
            records = new ArrayList<>();
            ArrayList<SMessage> messages = this.readBinary(filename);
            for(SMessage message : messages){
                records.add(
                        message.getSentAt() + " - " +
                        message.getSenderName() + ": " +
                        message.getMessage()
                );
            }
        } catch (IOException ex) {
            System.out.println("recover@Historic");
        }
        return records;
    }
    
    /**
     * Escreve objeto em arquivo, se já existe, insere binário ao final do arquivo
     * se não existe, cria um novo
     * @param filename nome do arquivo
     * @param obj objeto binário
     * @throws IOException 
     */
    private void writeBinary(String filename, Object obj) throws IOException{
        try{
            this.file = new File(filename);
            if(!file.exists()){
                this.writer = new ObjectOutputStream(new FileOutputStream(filename));
            } else {
                this.writer = new ObjectOutputStream(new FileOutputStream(filename, true));
            }
            this.writer.writeObject(obj);
            this.writer.flush();
        } catch (Exception e){
            System.out.println("writeBinary@Historic");
        } finally {
            if (this.writer != null) {
                this.writer.close();
            }
        }
    }
    
    /**
     * Recupera objeto em arquivo existente
     * @param filename nome do arquivo
     * @return lista de objetos
     * @throws IOException 
     */
    private ArrayList<SMessage> readBinary(String filename) throws IOException{
        this.file = new File(filename);
        ArrayList<SMessage> list = null;
        if(file.exists()){
            try {
                list = new ArrayList<>();
                this.reader = new ObjectInputStream (new FileInputStream(filename));
                Object buffer;
                while((buffer = this.reader.readObject()) != null){
                    list.add((SMessage)buffer);
                }
            } catch (Exception ex) {
                System.out.println("readBinary@Historic");
            } finally {
                if (this.writer != null) {
                    this.writer.close();
                }
                return null;
            }
        }
        return list;
    }
}
