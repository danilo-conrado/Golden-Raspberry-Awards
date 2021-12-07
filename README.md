# Aplicação Golden Raspberry Awards
## 


Primeiramente para a execução da aplicação, temos algumas observações quanto ao banco de dados.
- A aplicação irá realizar uma busca do arquivo no diretório do usuário, caso não encontre, irá solicitar para que o mesmo seja indicado manualmente.

- O arquivo utilizado como base de dados, deve ser colocado em:
 ```sh
 "%USERPROFILE%\gra" OU "$HOME\gra"
 ```
- O nome do arquivo deve ser:
```sh
movielist.csv
```
- Dentro do arquivo é necessário conter as 5 colunas com os nomes para a criação da base de dados:
```SH
 year;title;studios;producers;winner
 ```

## Ambiente
- A url base para utilização, normalmente fica em :
```sh
http://localhost:8080
```
- É disponibilizado o Swagger com informações sobre os EndPoints no endereço:
```sh
http://localhost:8080/swagger-ui.html
```
- O banco utilizado foi o H2, disponivel no endereço 
```sh
http://localhost:8080/h2-console
```
- Usuario "sa" e não tem senha.
- Para acesso ao banco a URL do JDBC que deve ser informada é :
```sh
"jdbc:h2:mem:gra"
```

## Testes

- Os testes foram feitos utilizando MOCK e também JUNIT contendo apenas uma unica classe a ser executada:
```sh
AwardsTest.class
```
- São eles:
```sh
@Test
    testRequestEndpoint()
```
-Verifica se o ENDPOINT esta com online(status 200).

```sh
@Test
    testDb()
```
-Realiza testes do banco de dados, como integradade tanto salvando um novo candidato como fazendo as requisições diretamente nele.

```sh
@Test
    testGetAward()
```
-Verifica o conteúdo do retorno do ENDPOINT, se a resposta esta conforme a especificação.

## Observações
- O retorno esta com um produtor com maior intervalo e outro com menor,caso exista alguma forma de empate, retornará todos os produtores empatados, assim como solicitado na especificação.
- Foram separados todos os Produtores, considerando a premiação para cada um de forma individual, por exemplo, caso o primeiro prêmio foi ganho em conjunto com outros produtores, ele considera como premio individual para este produtor, caso futuramente ele conquiste mais uma premiação, mesmo sendo o unico produtor do filme, é considerado também.
