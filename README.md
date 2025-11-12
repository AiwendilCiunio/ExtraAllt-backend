# Stocksimulator – Backend

Det här är backend‑delen av **Stocksimulator**, en applikation som simulerar aktiemarknaden i realtid.  
Backend tillhandahåller REST API, Stripe‑betalningsflöde och WebSocket‑uppdateringar för frontend‑klienten.
Hör ihop med frontend-delen (https://github.com/AiwendilCiunio/ExtraAllt-frontend)

---

## Tekniker

- **Spring Boot 3 (Java 17)**  
- **MySQL** via MAMP (lokal databas `stocksimulator`)  
- **Stripe API** för betalningar  
- **WebSocket / STOMP** för live‑uppdateringar  

---

## Köra applikationen lokalt

### Förutsättningar

- **Java 17**  
- **Maven**  
- **MySQL‑server** (körs t.ex. via MAMP på `localhost:3306`)  
- **Stripe Secret Key** (ange i miljövariabel `STRIPE_API_KEY`)  
- (valfritt) Frontend som körs på `http://localhost:4200`. Frontend-repo finns här:
  https://github.com/AiwendilCiunio/ExtraAllt-frontend

---

### Steg för steg

1. Klona detta repo:  

    ```bash
    git clone https://github.com/AiwendilCiunio/ExtraAllt-backend
    ```

2. Starta din lokala MySQL‑instans (MAMP startar både Apache och MySQL).  
   Se till att det finns en databas med namnet `stocksimulator`.

3. (Valfritt) Ange miljövariabler i systemet eller lägg till i IDE‑run‑konfigurationen:  

    ```text
    MYSQL_HOST=localhost
    MYSQL_USER=stocksimulator
    MYSQL_PASSWORD=stocksimulator
    STRIPE_API_KEY=<din Stripe secret key börja med sk_test_ eller sk_live_>
    ```

4. Starta applikationen från din IDE eller via Maven:  

    ```bash
    mvn spring-boot:run
    ```

5. Applikationen startas på **http://localhost:8080**

---

## Databaskonfiguration (i `application.properties`)

```properties
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/stocksimulator
spring.datasource.username=${MYSQL_USER:stocksimulator}
spring.datasource.password=${MYSQL_PASSWORD:stocksimulator}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

Vid lokal körning hanteras databasen via MAMP.  
Vid produktion ansluter applikationen till DigitalOcean Managed MySQL.

---

## Utvecklaranteckning

- **Port :** 8080  
- Stripe‑nycklar bör inte checkas in i Git (använd miljövariabler).  
- Starta frontend på `http://localhost:4200` för att kommunicera med API:et.

