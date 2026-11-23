# StockForge

Inventory REST API built with **Java 21**, **Spring Boot 3**, **Spring Data JPA**, and an H2 database.

## Features
- Create, list, retrieve, update and delete inventory products
- Validate unique SKU, nonnegative prices and quantities
- Adjust stock safely, preventing negative inventory
- Persist local data and run JUnit tests

## Run locally
Requires Java 21 and Maven.

```bash
mvn spring-boot:run
```

## Example requests
```bash
curl -X POST http://localhost:8080/api/products -H "Content-Type: application/json" -d '{"name":"Mouse","sku":"MOU-001","price":59.90,"quantity":10}'
curl http://localhost:8080/api/products
```

Update a product using `PUT /api/products/{id}`, change its stock using `PATCH /api/products/{id}/stock` with `{"delta":-1}`, and delete it using `DELETE /api/products/{id}`.

## Tests and Docker
```bash
mvn test
docker build -t stockforge .
docker run --rm -p 8080:8080 stockforge
```

## Notes
This is an educational MVP. The H2 database uses local file storage and the application has no authentication. Do not expose it publicly without authorization, security controls, migrations and production-grade storage. Never commit production data or secrets.

## License
MIT.
