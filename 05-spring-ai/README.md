# Projeto Spring AI - Controle Financeiro

Projeto do módulo de Spring AI da trilha de Spring Boot da DIO.

É uma API de controle de gastos que recebe comandos de voz. Eu falo uma frase como
"gastei 80 reais na farmácia", mando o áudio pra aplicação e ela registra a transação.
A resposta volta em áudio.

## Como funciona

```
áudio -> transcrição (Whisper) -> Spring AI -> Tool Calling -> transação -> resposta em áudio
```

O texto transcrito vai pro ChatClient. Os use cases da aplicação são anotados com `@Tool`
e ficam registrados no `defaultTools(...)`, então o modelo escolhe qual função chamar de
acordo com a frase. No fim, o texto da resposta vira MP3.

## O que eu adicionei

O projeto original tinha duas ferramentas: registrar transação e listar transações por
categoria. Eu adicionei mais duas consultas.

**Total por categoria** (`sum-transactions-by-category`)

Soma quanto foi gasto em uma categoria. Dá pra perguntar "quanto eu gastei com farmácia".
Também tem um endpoint: `GET /transactions/{categoria}/total`.

**Resumo financeiro** (`get-financial-summary`)

Retorna total de receitas, total de despesas e o saldo. Dá pra perguntar "qual é o meu
saldo" ou "quanto eu recebi". Também tem um endpoint: `GET /transactions/summary`.

Pra isso eu precisei criar a categoria `INCOME`, porque o projeto só tinha categorias de
gasto e não existia nenhuma forma de representar dinheiro entrando. Quem diz o que é
receita é o método `isIncome()` da enum `Category`.

Além disso corrigi uma conta no `TransactionOutput`: o valor é guardado em centavos, mas
o código só aplicava `setScale(2)` sem dividir por 100, então R$ 80,00 aparecia como
8000.00 na resposta da API.

## Tecnologias

- Java 25
- Spring Boot
- Spring AI (OpenAI)
- Spring Data JPA
- MySQL
- Docker Compose
- Gradle

## Como executar

Precisa de uma chave da OpenAI e do Docker rodando.

```bash
export OPENAI_API_KEY="sua_chave_aqui"
./gradlew bootRun
```

O banco sobe sozinho pelo `compose.yml`. A tabela é criada automaticamente
(`ddl-auto=update`).

## Como testar

Criar uma transação (valor em centavos):

```bash
curl -X POST http://localhost:8080/transactions \
  -H "Content-Type: application/json" \
  -d '{"description":"Remédio","category":"PHARMA","amount":8000}'
```

Consultas:

```bash
curl http://localhost:8080/transactions/PHARMA
curl http://localhost:8080/transactions/PHARMA/total
curl http://localhost:8080/transactions/summary
```

Categorias: `GROCERIES`, `PHARMA`, `AUTO` e `INCOME`.

Enviar áudio:

```bash
curl -X POST http://localhost:8080/transactions/ai \
  -F "file=@src/test/resources/audio/recording-1.m4a" \
  --output resposta.mp3
```

Exemplos de frases que funcionam:

- "gastei 80 reais na farmácia"
- "recebi 3000 reais de salário"
- "quanto eu gastei com farmácia"
- "qual é o meu saldo"

## Estrutura

```
src/main/java/dio/budgeting/
├── domain/           # Transaction, Category, TransactionRepository
├── application/      # use cases (que também são as tools), input/ e output/
└── infrastructure/
    ├── http/         # controller e request/response
    └── persistence/  # entidade e repositório JPA
```