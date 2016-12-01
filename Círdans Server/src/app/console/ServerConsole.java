package app.console;


import java.io.Console;
import java.io.IOException;
import java.util.Scanner;
import util.DBUtil;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 00271922
 */
public class ServerConsole {
    public static int menu(){
        int response = -1;
        
        System.out.println("Escolha uma opção:");
        System.out.println("1 - Iniciar serviço");
        System.out.println("2 - Iniciar serviço em modo depuração");
        System.out.println("3 - Iniciar serviço em modo depuração com SQL");
        System.out.println("4 - Configurar Banco de Dados e iniciar\n");
        System.out.print("Digite: ");
        int value = ServerConsole.readInput();
        switch(value){
            case 1:
                System.out.println("Iniciando serviço...");
                response = 1;
                break;
            case 2:
                System.out.println("Iniciando serviço em modo depuração...");
                response = 2;
                break;
            case 3:
                System.out.println("Iniciando serviço em modo depuração com SQL...");
                response = 3;
                break;
            case 4:
                response = databaseConfiguration();
                break;
            default:
                ServerConsole.clearConsole();
                System.out.println("Valor inválido.");
                break;
        }
        return response;
    }
    
    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")){
                Runtime.getRuntime().exec("cls");
            }else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final IOException e) {}
    }
    
    public static int databaseConfiguration(){
        int response = -1;
        DBUtil.urlConnection = ServerConsole.databaseString();
        DBUtil.user = ServerConsole.databaseUsername();
        DBUtil.password = ServerConsole.databasePassword();
        
        if(DBUtil.tryConnection()){
            System.out.println("Banco de dados configurado.");
            response = 1;
        } else {
            System.out.println("Conexão não estabelecida.");
        }
        return response;
    }
    
    public static String databaseString(){
        Scanner input = new Scanner(System.in);
        System.out.println("Digite a URL de conexão:");
        System.out.println("Exemplo: [hostname]:[port]/[database name]");
        System.out.println("Observação: somente utilize banco de dados MySQL");
        return input.nextLine();
    }
    
    public static String databaseUsername(){
        Scanner input = new Scanner(System.in);
        System.out.println("Digite o usuário:");
        return input.nextLine();
    }
    
    public static String databasePassword(){
        Console console = System.console();
        if (console == null) {
            System.out.println("Algo não ocorreu como desejado.");
            System.exit(0);
        }
        char passwordArray[] = console.readPassword("Digite a senha:\n");
        String password = String.valueOf(passwordArray);
        return password;
    }
    
    public static void header(){
        System.out.println("Círdan's Messenger Server");
        System.out.println("");
    }
    
    private static int readInput(){
        Scanner input = new Scanner(System.in);
        int value = -1;
        try{
            value = input.nextInt();
        } catch (Exception e){
            System.out.println("Opção inválida, digite um valor correto.");
        }
        return value;
    }
}
