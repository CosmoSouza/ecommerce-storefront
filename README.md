Storefront (E-commerce) — Spring Boot 3

API de Storefront (catálogo e compra de produtos) construída com Spring Boot 3, Java 21 e Gradle (Kotlin DSL).
O serviço expõe endpoints REST, documentados com Swagger/OpenAPI, persiste dados em H2 (perfil de dev) e integra com RabbitMQ para receber eventos de alteração de disponibilidade de produtos.
Em cenários de microsserviços, o Storefront comunica-se com o serviço Warehouse via HTTP.

Demo: Swagger em http://localhost:8081/storefront/swagger-ui/index.html
H2 Console: http://localhost:8081/storefront/h2-console/login.jsp
JDBC URL: jdbc:h2:mem:storefront • usuário: sa • senha: (em branco)

✨ Funcionalidades

CRUD simplificado de Produtos

Compra de produto (POST /produtos/{id}/compra)

Consulta de detalhes e lista de produtos

OpenAPI/Swagger UI para teste dos endpoints

Integração RabbitMQ (fila product.change.availability.queue) para atualização de disponibilidade

DevTools e reinício automático via trigger.txt

Pronto para Docker e Docker Compose (com rede externa ecommerce-net)

⚙️ Stack Técnica

Java 21, Spring Boot 3.5.x

Spring Web, Spring Data JPA (Hibernate), H2

Spring AMQP (RabbitMQ)

springdoc-openapi-ui (Swagger)

MapStruct (mapeamento DTOs)

Lombok

Gradle Kotlin DSL (build.gradle.kts)

Docker & Docker Compose

📁 Estrutura (resumo)
storefront/
 ├─ src/main/java/br/com/dio/storefront
 │   ├─ config/
 │   │   ├─ AMQPConfig.java
 │   │   ├─ OpenAPIConfig.java
 │   │   └─ WarehouseClientConfig.java
 │   ├─ controller/
 │   │   └─ ProductController.java
 │   ├─ dto/ (DTOs de request/response)
 │   ├─ entity/
 │   │   └─ ProductEntity.java        # id: UUID, name, active, price...
 │   ├─ repository/
 │   │   └─ ProductRepository.java
 │   ├─ service/
 │   │   ├─ IProductService.java
 │   │   └─ impl/
 │   │       ├─ ProductServiceImpl.java
 │   │       └─ ProductChangeAvailabilityConsumerImpl.java
 │   └─ StorefrontApplication.java
 ├─ src/main/resources/
 │   ├─ application.yml               # portas, H2, RabbitMQ, Warehouse
 │   ├─ application-dev.yml           # overrides do perfil dev
 │   └─ trigger.txt
 ├─ docker-compose.yml
 ├─ Dockerfile
 └─ start-dev.sh

🌐 Endpoints (exemplos)

Base path (perfil dev): http://localhost:8081/storefront

Método	Caminho	Descrição
GET	/produtos	Lista produtos
POST	/produtos	Cria produto
GET	/produtos/{id}	Detalha produto
POST	/produtos/{id}/compra	Realiza compra

Swagger: http://localhost:8081/storefront/swagger-ui/index.html

cURL rápido
# Listar
curl -s http://localhost:8081/storefront/produtos | jq

# Criar
curl -X POST http://localhost:8081/storefront/produtos \
  -H 'Content-Type: application/json' \
  -d '{
        "name": "Notebook",
        "price": 5600.00,
        "active": true
      }'

# Detalhar
curl -s http://localhost:8081/storefront/produtos/{uuid-do-produto} | jq

# Comprar
curl -X POST http://localhost:8081/storefront/produtos/{uuid-do-produto}/compra

▶️ Como executar
Opção A) Docker Compose

Requer Docker e Docker Compose instalados.

Crie a rede externa (usada pelos serviços do e-commerce):

docker network create ecommerce-net


Na raiz do projeto, suba o serviço:

docker compose up -d --build
# ou:
# docker-compose up -d --build


Acesse:

Swagger: http://localhost:8081/storefront/swagger-ui/index.html

H2 Console: http://localhost:8081/storefront/h2-console/login.jsp

RabbitMQ (se estiver usando via Docker): o host configurado no application.yml é rabbitmq (nome do serviço/container) — dentro da rede ecommerce-net.

Opção B) Execução local (sem Docker)

Requer JDK 21 e Gradle Wrapper.

# na raiz do projeto
./gradlew bootRun
# Windows PowerShell:
# .\gradlew.bat bootRun


Perfil ativo: dev (via SPRING_PROFILES_ACTIVE=dev no compose; se rodar local, ajuste se necessário).

Debug remoto: porta 5005 (configurada no bootRun/compose).

H2 mem: jdbc:h2:mem:storefront — usuário sa, senha (em branco).

🔌 Integração com Warehouse

application.yml define:

warehouse:
  base-path: http://warehouseapp:8080/warehouse


Em Docker, warehouseapp deve ser o hostname/nome do serviço do container do Warehouse na mesma rede ecommerce-net.

Se rodar o Warehouse local na máquina, ajuste para http://localhost:8080/warehouse.

📨 Mensageria (RabbitMQ)

Host/porta (dev): rabbitmq:5672 (no Docker) ou localhost:5672 (local).

Fila utilizada:

spring:
  rabbitmq:
    queue:
      product-change-availability: product.change.availability.queue


O consumidor atualiza a disponibilidade dos produtos com base nos eventos recebidos.

🧪 Testes
./gradlew test

🚀 Roadmap / Próximos passos

Autenticação/Autorização (Spring Security / Keycloak)

Observabilidade (Actuator + Prometheus/Grafana)

Banco relacional externo (PostgreSQL) para ambientes além de dev

Testes de contrato e integração

🧩 Troubleshooting

Could not resolve placeholder 'spring.rabbitmq.queue.product-change-availability'
Verifique o application.yml / application-dev.yml e se a chave:

spring.rabbitmq.queue.product-change-availability


está definida corretamente (sem typos) no mesmo perfil em que a app está rodando.

Não conecta no RabbitMQ local
Use localhost no application-dev.yml quando não estiver em Docker; no Docker, o host deve ser o nome do serviço rabbitmq.

H2 Console 404
Confirme a URL: http://localhost:8081/storefront/h2-console/login.jsp e o JDBC URL: jdbc:h2:mem:storefront.

📄 Licença

Este projeto é disponibilizado sob a licença MIT. Sinta-se à vontade para usar e contribuir.

🤝 Contribuição

Faça um fork

Crie uma feature branch: git checkout -b feature/minha-feature

Commit: git commit -m "feat: minha feature"

Push: git push origin feature/minha-feature

Abra um Pull Request

🙌 Autor

Lucas Souza (CosmoSouza)
Se curtiu o projeto, deixe uma ⭐ no repositório e compartilhe no LinkedIn!
