# Crypto-Trading
Crypto-Trading is a very dumbed-down crypto trading platform.It simulates buying and selling cryptocurrencies provided by an external exchange.
The trading is done with the use of fake money as a fiat currency. Additionally, only symbol pairs with USD as the quote currency are listed and are available
for trading.
## Description
The app is composed of 3 separate entities - An angular client, a spring boot server and a dockerized PostgreSQL database that is exposed to the host machine.
The Spring Boot server contains a WebSocket client that subscribes to the Kraken API to get crypto exchange information and listen for real-time updates.

The updates received from Kraken are forwarded to a STOMP message broker, after which they are sent to the client, where they're finally displayed.

The architecture is a standard layered architecture, where controllers manage HTTP and WebSocket interactions,
services encapsulate business logic and the data layer interacts with the database via a JDBC adapter.

The server maintains a concurrent hashmap that stores the top 20 crypto pairs by price. These top 20 pairs are
continuously updated based on the websocket updates received from Kraken.

Error handling is present in the client and also in the server, for example with JSON serialization/deserialization and invalid transactions,
such as a user attempting to sell more than they currently own.

There can definitely be more improvements, like keeping the SQL queries as resources and reading them via DDL scripts, but this
is a toy project and I don't have infinite time. I also have my finals in a week and a half (as of writing this README).

Thanks for checking my work out!

### Dependencies
Docker desktop and/or the Docker CLI should be installed along with Docker Compose.

The availability of the Kraken API is also required.
https://status.kraken.com/

### Installing

```
git clone https://github.com/TsvetislavRangelov/Crypto-Trading
# you can also just run this via start menu or something like that.
docker desktop start
```

### Building & Launching
Run build-and-run.sh
Afterwards, go to localhost:4200.

```
./build-and-run.sh
```
If you wish to restart the services and the DB docker container:
```
./kill-all.sh
./build-and-run.sh
```

## Help

The root directory contains two utility scripts - kill-all.sh and build-and-run.sh. Both are executable. 
build-and-run.sh will build and run the client, server and also create a container hosting a PostgreSQL instance.
The database is configured with init.sql, which creates the schema.

If you're on Windows, it is easier to run the scripts via WSL. You can also run them through a shell to powershell translator and then execute locally.

## Author
Tsvetislav Rangelov
tsvetislav.rangelov@philips.com
tsvetislav.rangelov@gmail.com
