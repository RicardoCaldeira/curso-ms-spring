# curso-ms-spring
Repositório para desenvolvimento do projeto do curso Java com Spring Boot, Kafka e Microserviços - Udemy. 
O projetos disponibiliza serviços backend para venda de carros, onde estarão disponíveis nos bancos de dados as informações dos proprietários, dos posts de vendas e estatísticas sobre as marcas e modelos dos carros postados.

A relação comunicação entre os serviços criados é ilustrada pela imagem abaixo, onde teremos 3 microserviços:<br>
- Uma Main RestAPI, que receberá requisições REST e enviará mensagens ao cluster kafka.<br>
- Um Car Store Service que comunicará com a api através de requisições REST, escutará o cluster kafka e fará operações com um banco próprio.<br>
- Por ultimo um Car Analytics Service, que somente escutará as mensagens recebidas no cluster e fará operações com um banco prório.
![image](https://github.com/RicardoCaldeira/curso-ms-spring/assets/34627524/3d4c9a42-6992-4930-9e11-769d163eae8a)


# Configuração do Projeto de Microsserviços em Spring Boot

Este arquivo README contém as instruções para configurar e executar o projeto de microsserviços em Spring Boot, que utiliza as seguintes tecnologias: Java 17, Maven, Docker, Apache Kafka, Conduktor e PostgreSQL. O ambiente de execução considerado é o Windows.

## Pré-requisitos

Antes de iniciar a configuração do projeto, certifique-se de ter as seguintes ferramentas instaladas em seu ambiente:

- Java 17: [Download](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- Maven: [Download](https://maven.apache.org/download.cgi)
- Docker Desktop para Windows: [Download](https://www.docker.com/products/docker-desktop)
- Conduktor: [Download](https://www.conduktor.io/download)
- PostgreSQL: [Download](https://www.postgresql.org/download/windows/)

## Configuração do Banco de Dados PostgreSQL

Para criar um banco de dados PostgreSQL através do Docker Desktop no Windows, siga as etapas abaixo:

1. Abra o Docker Desktop.
2. Verifique se o Docker está em execução e funcionando corretamente.
3. Abra o terminal (Prompt de Comando ou PowerShell) do Windows.
4. Execute o seguinte comando para criar um contêiner do PostgreSQL:

```shell
docker run -d --name postgres-container -p 5432:5432 -e POSTGRES_PASSWORD=secretpassword postgres
```

5. Aguarde até que o contêiner seja criado e iniciado com sucesso.
6. Abra uma ferramenta de gerenciamento de banco de dados PostgreSQL de sua preferência (por exemplo, dbeaver).
Conecte-se ao servidor PostgreSQL utilizando as seguintes informações:
Host: localhost
Port: 5432
Username: postgres
Password: postgres

Crie os dois bancos de dados necessários para a execução da aplicação, executando os seguintes comandos SQL:
```shell
CREATE DATABASE car_post_analytics;
CREATE DATABASE car_post_storage;
```

9. Os bancos de dados "car_post_analytics" e "car_post_storage" estão prontos para serem utilizados pela aplicação.


## Configuração do Cluster Kafka com o Conduktor
Para iniciar um Cluster local do Kafka usando o Conduktor, siga as etapas abaixo:

Abra o Conduktor.
Clique "Start local Kafka Cluster.
Preencha as seguintes informações:
Cluster Name (Nome do Cluster): car-sales-portal
Cluster Version: Faça download da ultima versão disponível
Clique em "Start Cluster".
O Cluster "car-sales-portal" foi criado e está pronto para uso.


## Executando o Projeto de Microsserviços
O projeto de microsserviços consiste em três microserviços: "api", "car" e "data". Siga as instruções abaixo para configurar e executar cada um dos microserviços:

Siga os passos abaixo para a execução de todos os microserviços disponíveis.

Abra um terminal na pasta raiz do microserviço em questão.
Execute o seguinte comando para compilar o projeto:
```shell
mvn clean install
```

Após a conclusão da compilação, execute o seguinte comando para iniciar o microserviço:
```shell
mvn spring-boot:run
```


Agora, com os três microserviços estão configurados e em execução. Você pode acessar o microserviço "api" através de requisições REST na porta 8080 e interagir com o sistema de microsserviços.

Lembre-se de que você pode ajustar as configurações dos microserviços e do Kafka conforme necessário, como endereços de hosts, portas e propriedades de segurança, de acordo com o seu ambiente específico.

## Rotas disponíveis para utilização dos serviços:

POST http://localhost:8085/owner
Descrição: Cria um novo proprietário para venda de veículos.
```
{
    "name": "Arthur",
    "type": "Particular",
    "contactNumber": "999999999"
}
```

POST http://localhost:8085/api/car/post
Descrição: Cria um novo post de veículo para venda.

```
{
    "model": "Celica",
    "brand": "Toyota",
    "price": 100000.00,
    "description": "Prêmio pela conquista do mundial de 81 com o Flamengo",
    "engineVersion": "2.0",
    "city": "Rio de Janeiro",
    "createdDate": "",
    "ownerId": 3,
    "ownerName": "Arthur",
    "ownerType": "Profissional",
    "contact": "999999999"
}
```
