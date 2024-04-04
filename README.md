# Minio com Spring Boot

## Sobre o projeto
Este projeto é para apresentar como armazenar arquivo utilizando Minio com Spring boot. 

## Tecnologias utilizadas
### Back end
- Java
- Spring Boot
- Maven
- Minio

## Como executar o projeto

### Pré-requisitos
Java 17
Docker
### Enpoint
Listarei abaixo os endpoints.

```java
//uploads // POST GET
//uploads/{nomeArquivo} // GET
//uploads/{nomeArquivo}  DELETE 
```

### Docker Minio
-  Para levantar o Minio precisa criar arquivo docker-compose.yml
-  Link para acessar minio: http://localhost:9001/login
-  Usuario: minio   Password: minio#123

```   
    version: '2'
    services:
      minio:
        image: docker.io/bitnami/minio:2024
        ports:
          - '9000:9000'
          - '9001:9001'
        networks:
          - minionetwork
        volumes:
          - 'minio_data:/bitnami/minio/data'
        environment:
          - MINIO_ROOT_USER=minio
          - MINIO_ROOT_PASSWORD=minio#123
          - MINIO_DEFAULT_BUCKETS=artigo
    volumes:
      minio_data:
        driver: local
        
    networks:
      minionetwork:
        driver: bridge
```    

### Clonar projeto
```bash
# clonar repositório
git clone https://github.com/adrianoaguiardez/minio-spring.git
```

## Autor

Adriano de Aguiar
