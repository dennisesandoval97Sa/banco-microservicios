# Banca API - Ejercicio Técnico Backend Java

Este proyecto implementa un sistema de microservicios para la gestión de clientes, cuentas y movimientos bancarios, cumpliendo con los requerimientos del **Ejercicio Técnico Backend Java** (nivel Senior).

## Estructura del Proyecto
```
banco-microservicios/
│── clients-service/         # Microservicio de clientes
│── accounts-service/        # Microservicio de cuentas y movimientos
│── BaseDatos.sql            # Script SQL unificado (clientes + cuentas)
│── docker-compose.yml       # Orquestación de microservicios y dependencias
│── postman_collection.json  # Colección de pruebas en Postman
│── README.md                 # Este archivo
```

## Requisitos previos
- **Java 17**
- **Maven 3.8+**
- **Docker** y **Docker Compose**
- **Postman** (para ejecutar pruebas)

## Instrucciones de ejecución

### 1. Clonar el repositorio
```bash
git clone https://github.com/usuario/banco-microservicios.git
cd banco-microservicios
```

### 2. Levantar los servicios con Docker
```bash
docker-compose up --build
```
Esto iniciará:
- **clients-service** (puerto 8082)
- **accounts-service** (puerto 8083)
- **mysql-clients** (puerto 3308)
- **mysql-accounts** (puerto 3307)
- **rabbitmq** (puerto 15672 para UI)

## Ejecución de pruebas

### Pruebas unitarias y de integración
Desde cada microservicio:
```bash
cd clients-service
./mvnw test

cd ../accounts-service
./mvnw test
```

Las pruebas incluyen:
- **F5**: Unit Test para Cliente (`ClientServiceTest`)
- **F6**: Integration Test (`IntegrationTest`) usando H2

## Endpoints principales

### Clients Service (`localhost:8082`)
- `GET /clients` → Lista todos los clientes.
- `GET /clients/{id}` → Obtiene un cliente por ID.
- `POST /clients` → Crea un cliente (genera cuenta automática vía RabbitMQ).
- `PUT /clients/{id}` → Actualiza un cliente.
- `DELETE /clients/{id}` → Elimina un cliente.

### Accounts Service (`localhost:8083`)
- `GET /accounts` → Lista todas las cuentas.
- `GET /accounts/{id}` → Obtiene cuenta por ID.
- `POST /transactions` → Crea una transacción (deposit/withdraw).
- `GET /transactions/report?start=...&end=...&accountId=...` → Reporte de movimientos por cuenta.
- `GET /transactions/statement?start=...&end=...&clientId=...` → Estado de cuenta consolidado (F4).

## Base de datos
Se incluye un script unificado:  
**`BaseDatos.sql`** → Contiene creación de tablas y datos iniciales para `clients_db` y `accounts_db`.

Para ejecutarlo manualmente:
```bash
docker exec -i banco-microservicios-mysql-clients-1 mysql -uroot -proot < BaseDatos.sql
docker exec -i banco-microservicios-mysql-accounts-1 mysql -uroot -proot < BaseDatos.sql
```

## Colección Postman
En la raíz del repositorio está el archivo:  
**`postman_collection.json`**

Pasos:
1. Importar el archivo en Postman.
2. Ejecutar las solicitudes en el orden indicado:
   - Crear cliente
   - Verificar creación de cuenta en `accounts-service`
   - Registrar transacciones
   - Consultar reportes

## Autor
Desarrollado por: **Dennise Sandoval**  
Fecha: **Agosto 2025**
