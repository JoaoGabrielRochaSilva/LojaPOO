# Sistema de Gestão de Loja

Projeto acadêmico da disciplina de **Programação Orientada a Objetos (POO)**, desenvolvido em **Java** no Apache NetBeans, com persistência em **MySQL** via **JDBC**.

É uma aplicação de **console** (interação por terminal, com `Scanner`) que simula a gestão de uma loja: cadastro de produtos, estoque, carrinho de compras, pedidos, cupons de desconto, entregas e relatórios em PDF. O código é organizado em três pacotes no padrão **MVC** (`control`, `model`, `view`), com classes **DAO** para o acesso ao banco.

## Sumário

- [Funcionalidades](#funcionalidades)
- [Tecnologias e versões](#tecnologias-e-versões)
- [Estrutura do repositório](#estrutura-do-repositório)
- [Pré-requisitos](#pré-requisitos)
- [Instalação e execução](#instalação-e-execução)
- [Dados de exemplo](#dados-de-exemplo)
- [Solução de problemas](#solução-de-problemas)
- [Dependências de terceiros e licenças](#dependências-de-terceiros-e-licenças)
- [Contexto acadêmico](#contexto-acadêmico)
- [Autor](#autor)

## Funcionalidades

As funcionalidades abaixo foram confirmadas nos menus e controladores do código-fonte.

**Menu inicial (sem login)**
- Listar os produtos disponíveis
- Cadastrar usuário
- Fazer login (o usuário com login `ADM` acessa o menu de administrador; os demais acessam o menu de usuário)

**Administrador**
- **Produtos:** adicionar, ativar/desativar, alterar (nome, descrição, preço de venda) e listar
- **Cupons:** adicionar, ativar/desativar, alterar (código, tipo, valor de desconto, valor mínimo, validade) e listar
- **Estoque:** registrar movimentações de entrada e saída e listar as movimentações
- **Calendário:** exibir a data atual do sistema e avançar dias
- **Entregas:** modificar (código de rastreio, transportadora), cancelar e consultar
- **Relatórios:**
  - Vendas: por período, por cliente e por status
  - Faturamento: diário, mensal e anual
  - Pedidos: abertos, pagos e cancelados
- Cadastrar pessoas e usuários

**Usuário**
- **Ir às compras:** escolher produtos e adicioná-los ao carrinho
- **Carrinho:** criar, finalizar, cancelar, listar carrinhos e gerenciar itens (remover, alterar produto, alterar quantidade)
- **Pedidos:** pagar, cancelar, emitir relatório de pedidos e listar todos os itens
- **Entregas:** rastrear e listar as entregas

**Outros recursos**
- **Calendário simulado:** o sistema mantém uma data própria, iniciada com a data do computador. Ao avançar dias pelo menu do administrador, a classe `EventosAutomaticos` verifica carrinhos, pedidos, entregas e cupons com base na nova data e atualiza seus status no banco.
- **Relatórios em PDF:** gerados com a biblioteca iText. O nome do arquivo é formado pelo título do relatório (apenas letras e números, o restante vira `_`), a data no formato `yyyyMMdd` (data do calendário simulado) e a extensão `.pdf`. O código grava o arquivo sem informar uma pasta, ou seja, no diretório de trabalho da execução.

## Tecnologias e versões

| Item | Versão / detalhe | Onde foi verificado |
|---|---|---|
| Java (JDK) | 25 (LTS) | `javac.source` e `javac.target` = 25 em `LojaPOO/nbproject/project.properties` |
| IDE | Apache NetBeans 29 (ou compatível) | Tutorial de instalação do trabalho |
| Tipo de projeto | NetBeans Java com Ant (`build.xml`); nome interno do projeto: `Trabalho-part02` | `LojaPOO/nbproject/project.xml` |
| Banco de dados | MySQL 8.x | O backup `faculdade.sql` foi gerado em um servidor MySQL 8.0.46 |
| Driver JDBC | MySQL Connector/J 8.0.12 | Referência em `project.properties` |
| Relatórios em PDF | iText 5.2.0 (`com.itextpdf`) | Referência em `project.properties` e imports em `RelatorioControler` |
| Acesso ao banco | JDBC puro (`DriverManager`) | `ConectionFactory.java` |
| Codificação do código-fonte | UTF-8 | `project.properties` |

## Estrutura do repositório

```text
.
├── BancoDeDados/
│   └── faculdade.sql              # backup do banco (estrutura + dados de exemplo)
├── bibliotecas/                   # JARs baixados manualmente (NÃO versionados)
│   └── .gitkeep
├── LojaPOO/                       # projeto NetBeans (abra ESTA pasta no NetBeans)
│   ├── build.xml
│   ├── manifest.mf
│   ├── nbproject/
│   │   ├── build-impl.xml
│   │   ├── genfiles.properties
│   │   ├── project.properties
│   │   └── project.xml
│   └── src/
│       └── MVC/
│           ├── control/           # Main, AdmControler, UserControler, PedidoControler,
│           │                      # CarrinhoControler, RelatorioControler, EventosAutomaticos
│           ├── model/             # entidades, DAOs, ConectionFactory, Login e Calendario
│           └── view/              # menus de texto exibidos no terminal
├── .gitattributes
├── .gitignore
├── LICENSE
└── README.md
```

- `BancoDeDados/faculdade.sql`: dump com as 10 tabelas do sistema (`carrinho`, `cupom`, `entrega`, `itens_carrinho`, `itens_pedido`, `movimentacao_estoque`, `pedidos`, `pessoa`, `produto`, `usuario`) e dados de exemplo.
- `bibliotecas/`: pasta onde devem ficar os JARs. O arquivo `.gitkeep` existe apenas para que o Git versione a pasta vazia.
- `LojaPOO/src/MVC/control`: fluxo do programa e regras de cada área (administrador, usuário, pedidos, carrinho, relatórios e eventos automáticos). A classe `Main` contém o ponto de entrada.
- `LojaPOO/src/MVC/model`: classes de modelo, classes DAO (acesso ao MySQL), `ConectionFactory`, `Login` e `Calendario`.
- `LojaPOO/src/MVC/view`: classes que montam e exibem os menus e recebem os dados digitados.

> As pastas `build/` e `dist/` geradas pelo NetBeans e a pasta `nbproject/private/` (configurações da máquina de cada pessoa) não fazem parte do repositório.

## Pré-requisitos

1. Java JDK 25 (LTS) ou versão compatível com o projeto
2. Apache NetBeans 29 ou versão compatível
3. MySQL Server 8.x
4. MySQL Workbench (recomendado)
5. Os dois JARs externos descritos na [etapa 4](#4-baixe-os-jars-das-bibliotecas-externas)
6. Git, caso vá clonar o repositório

## Instalação e execução

### 1. Obtenha o projeto

```bash
git clone https://github.com/JoaoGabrielRochaSilva/LojaPOO.git
```

Também é possível baixar o ZIP pelo GitHub e extraí-lo. O restante deste guia chama de **raiz do repositório** a pasta que contém `BancoDeDados/`, `bibliotecas/` e `LojaPOO/`.

### 2. Instale e inicie o MySQL

Instale o MySQL Server 8.x e confirme que ele está em execução antes de continuar (no Windows, pelo MySQL Installer ou pelo gerenciador de Serviços; no Linux, por exemplo com `sudo systemctl status mysql`; o nome do serviço pode variar conforme a instalação). A porta padrão usada pelo projeto é a `3306`.

### 3. Crie e importe o banco `faculdade`

O arquivo `faculdade.sql` **não contém** `CREATE DATABASE` nem `USE`: o banco precisa existir e ser escolhido na importação.

1. Abra o MySQL Workbench e conecte-se ao servidor local.
2. Crie o banco (se ele ainda não existir):

   ```sql
   CREATE DATABASE faculdade;
   ```

3. Acesse **Server > Data Import**.
4. Selecione **Import from Self-Contained File** e escolha `BancoDeDados/faculdade.sql`.
5. Em **Default Target Schema**, selecione `faculdade`.
6. Clique em **Start Import**.

Alternativa pelo terminal (após criar o banco):

```bash
mysql -u SEU_USUARIO -p faculdade < BancoDeDados/faculdade.sql
```

> **Atenção:** o script executa `DROP TABLE IF EXISTS` antes de criar cada tabela. Se o banco `faculdade` já tiver tabelas com esses nomes, elas serão substituídas. Faça backup do que quiser preservar.

### 4. Baixe os JARs das bibliotecas externas

Os JARs **não estão incluídos no repositório** e devem ser baixados separadamente. Coloque os dois arquivos na pasta `bibliotecas/`, na raiz do repositório (no mesmo nível da pasta `LojaPOO/`), mantendo exatamente estes nomes:

| Biblioteca | Arquivo esperado | Download |
|---|---|---|
| MySQL Connector/J 8.0.12 | `mysql-connector-java-8.0.12.jar` | <https://downloads.mysql.com/archives/c-j/> (escolha a versão 8.0.12 e extraia o `.jar` do pacote baixado) |
| iText 5.2.0 | `itextpdf-5.2.0.jar` | <https://repo1.maven.org/maven2/com/itextpdf/itextpdf/5.2.0/> |

Resultado esperado:

```text
.
├── bibliotecas/
│   ├── itextpdf-5.2.0.jar
│   └── mysql-connector-java-8.0.12.jar
└── LojaPOO/
```

O arquivo `LojaPOO/nbproject/project.properties` já aponta para esses caminhos de forma relativa, então nenhuma configuração adicional é necessária quando os nomes e o local estão corretos:

```properties
file.reference.itextpdf-5.2.0.jar=../bibliotecas/itextpdf-5.2.0.jar
file.reference.mysql-connector-java-8.0.12.jar=../bibliotecas/mysql-connector-java-8.0.12.jar
```

O `.gitignore` do repositório ignora `bibliotecas/*.jar`, evitando que os JARs sejam versionados por engano.

### 5. Abra o projeto no NetBeans

1. Abra o NetBeans e selecione **File > Open Project**.
2. Escolha a pasta **`LojaPOO/LojaPOO`** (a pasta interna, a que o NetBeans reconhece como projeto).
3. Confirme que o NetBeans está usando o JDK 25 (o projeto usa `javac.source=25`).
4. Em **Libraries**, confirme que os dois JARs aparecem sem ícone de erro. Caso algum apareça como referência quebrada, confira os nomes dos arquivos em `bibliotecas/`. Se necessário, remova a referência quebrada e use **Add JAR/Folder** para apontar para o arquivo correto.

### 6. Configure a conexão com o MySQL

Abra `LojaPOO/src/MVC/model/ConectionFactory.java`. A conexão está assim no repositório:

```java
private static final String url = "jdbc:mysql://localhost:3306/faculdade?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo";
private static final String usuario = "seu_usuario";
private static final String senha = "sua_senha";
```

Ajuste **localmente**:

- `usuario` e `senha`: substitua pelos dados do seu MySQL (os valores do repositório são apenas marcadores);
- porta, se o seu MySQL não usar a `3306`;
- nome do banco, se você não usou `faculdade`.

> **Não faça commit de senhas reais.** Antes de cada commit, confira com `git diff` se `ConectionFactory.java` continua com os marcadores.

### 7. Execute o sistema

A classe principal é **`MVC.control.Main`** (é a única classe do projeto com método `main`).

- No NetBeans, clique com o botão direito em `Main.java` e escolha **Run File** (atalho `Shift+F6`).
- A propriedade `main.class` do `project.properties` está vazia no repositório. Por isso, ao usar **Run Project**, o NetBeans pode pedir que você selecione a classe principal; escolha `MVC.control.Main`. Também é possível definir isso em **Project Properties > Run > Main Class**.

O sistema inicia no terminal da IDE exibindo o menu "SEJA BEM VINDO A LOJA". O servidor MySQL precisa estar em execução.

## Dados de exemplo

O `faculdade.sql` inclui produtos, pedidos, carrinhos, cupons, entregas, movimentações de estoque e usuários de exemplo, apenas para demonstração. Para testar o menu de administrador, use o login `ADM` com a senha `ADM`, presentes no dump. Os demais usuários de exemplo estão na tabela `usuario`.

> **Importante:** as senhas são gravadas em texto puro na coluna `senha` (o código não faz hash). Isso é aceitável apenas em um trabalho acadêmico: não reutilize senhas reais neste sistema.

## Solução de problemas

| Sintoma | Causa provável e o que fazer |
|---|---|
| `No suitable driver found for jdbc:mysql://...` | O JAR do MySQL Connector/J não está no classpath. Confira `bibliotecas/mysql-connector-java-8.0.12.jar` e a seção **Libraries** do projeto. |
| `package com.itextpdf... does not exist` ou `NoClassDefFoundError` ao gerar PDF | O JAR `itextpdf-5.2.0.jar` não foi adicionado. Veja a [etapa 4](#4-baixe-os-jars-das-bibliotecas-externas). |
| Ícone de referência quebrada em **Libraries** | Os JARs não estão em `bibliotecas/` (raiz do repositório) ou os nomes diferem dos esperados. |
| `Access denied for user ...` | Usuário ou senha incorretos em `ConectionFactory.java`. |
| `Communications link failure` / conexão recusada | O MySQL não está em execução ou usa outra porta. Inicie o servidor e ajuste a porta na URL, se necessário. |
| `Unknown database 'faculdade'` | O banco não foi criado. Execute `CREATE DATABASE faculdade;` e refaça a importação. |
| Tabelas ausentes ou erro `Table ... doesn't exist` | A importação foi feita sem selecionar o schema `faculdade` (o dump não tem `USE`). Refaça a importação escolhendo o schema correto. |
| Erro de versão do Java (por exemplo, `invalid source release: 25`) | O NetBeans está usando um JDK anterior ao 25. Configure o JDK 25 na IDE (**Tools > Java Platforms**) ou ajuste a versão do projeto, ciente de que isso altera o `project.properties`. |
| O NetBeans pede a classe principal ao executar | Esperado, pois `main.class` está vazio. Selecione `MVC.control.Main`. |
| Não encontro o PDF gerado | O arquivo é gravado no diretório de trabalho da execução (no NetBeans, em geral a pasta do projeto `LojaPOO/LojaPOO`). |

## Dependências de terceiros e licenças

- **Código deste repositório:** licença MIT (arquivo [`LICENSE`](LICENSE)). Ela cobre o código do projeto e **não** altera as licenças das bibliotecas abaixo.
- **iText 5.2.0:** está sujeito à licença **GNU AGPL v3** ou a uma licença comercial. Consulte o texto da AGPL v3 em <https://www.gnu.org/licenses/agpl-3.0.html>. Este repositório não distribui o JAR, e a existência deste projeto não garante conformidade com a AGPL: avalie as obrigações da licença (ou considere a licença comercial) antes de distribuir uma versão empacotada ou disponibilizar o software a terceiros.
- **MySQL Connector/J 8.0.12:** consulte a licença no pacote oficial baixado em <https://downloads.mysql.com/archives/c-j/>.

## Contexto acadêmico

Este projeto foi desenvolvido para a disciplina de **Programação Orientada a Objetos**, como o **Trabalho de POO – Parte 2** (conforme o tutorial de instalação que acompanha o trabalho). Entre os conceitos aplicados no código:

- organização em classes com atributos privados, `getters`/`setters` e métodos como `toString`, `equals` e `hashCode`;
- separação de responsabilidades no padrão MVC (`control`, `model`, `view`);
- classes DAO para isolar o acesso ao banco de dados via JDBC;
- composição de objetos: a classe `Main` cria as views, os DAOs e os controladores e injeta as dependências pelos construtores.

Como é um trabalho acadêmico, algumas funcionalidades e dados têm finalidade demonstrativa.

## Autor

**João Gabriel Rocha Silva**
