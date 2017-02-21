# Cirdan-s-Messenger
## Geral
Trabalho de conclusão de semestre para a disciplina de Programação Orientada a Objetos ministrada pela Profª. Diana Cabral Cavalcanti.
## Sobre
O repositório é divido entre cliente, servidor e arquivos compartilhados entre os dois projetos.
## Requisitos do trabalho

- Aplicação com interação com o Facebook.
- Manipulação de banco de dados
- CRUD completo
- Interface gráfica JavaFX

## Outros itens presentes no trabalho

- Multi-thread
- Objetos distribuídos

### Servidor
O servidor é uma aplicação de console em Java, utilizando banco de dados MySQL.
### Cliente
O cliente é uma aplicação JavaFX que interage com o servidor por meio de um protocolo serializavel.
### Classe do protocolo:
``` Java
public class CProtocol implements Serializable {
    private InetAddress sender;
    private long senderId;
    private long recieverId;
    private Date timestamp;
    private EResponse type;
    private Object payload;
    ...
    GETTERS AND SETTERS
    ...
}
```

## Instalação
### Pré-requisitos:  
- Servidor MySQL
- Java Platform SE 7
### Como instalar:
- Descompactar os arquivos executáveis
- Executar o Script de criação de banco de dados [app_cirdan_server.sql](https://github.com/marcelogm/Cirdan-s-Messenger/blob/master/Dist/app_cirdan_server.sql)
- Executar o servidor com “java -jar C_rdans_Server.jar” ou pelo script run.bat
- Conectar com o cliente

Obs.: o executável foi compilado em modo de depuração, caso queira eliminar a pergunta de IP do servidor (em [Main.java](https://github.com/marcelogm/Cirdan-s-Messenger/blob/master/C%C3%ADrdans%20Client/src/app/console/Main.java) na flag DEBUG) ou definir um IP fixo de servidor nos clientes (em [Engine.java](https://github.com/marcelogm/Cirdan-s-Messenger/blob/master/C%C3%ADrdans%20Client/src/engine/Engine.java) no método construtor), basta abrir o projeto com o NetBeans e recopila-lo com as configurações adequadas.
