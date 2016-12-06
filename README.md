# Cirdan-s-Messenger
## Geral
Trabalho de conclusão de semestre para a disciplina de Programação Orientada a Objetos ministrada pela Professora Doutora Diana Cabral Cavalcanti.
## Sobre
O repositório é divido entre cliente, servidor e arquivos compartilhados entre os dois projetos.
## Requisitos do trabalho
- Aplicação com interação com o Facebook.
- Manipulação de dados em banco
- CRUD completo
- Interface gráfica
## Outros itens presentes no trabalho
- Multi-thread
- Objetos distribuidos
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
