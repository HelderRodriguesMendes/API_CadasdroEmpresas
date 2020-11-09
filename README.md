# API Cadasdro de Empresas
API de cadastro de empresas que é consumida por um sistema Java Desktop (Swing), utiliza arquivos de Log (TXT) para salvar os dados que nela são processados, utiliza também o banco
de dados h2 para exibir os dados processados que estão armazenados nos arquivos de Log.

Tecnologias utilizadas: Spring Boot, Maven, Hibernate, JPA, Spring Data JPA, Java 11, Spring Tool Suite, XAMPP, Postman

Para a execução da API, é necessário alterar: A conexão com o banco H2, que está no arquivo application.properties e, altera o caminho da pasta que será salvo os arquivos de Log,
esta alteração deve ser feita na variável caminhoPasta que está em: pacote br.com.testePratico.log, classe LogConfig, métodos: configCaminhoPasta --> caminhoPasta, config_CaminhoPasta --> caminhoPasta
