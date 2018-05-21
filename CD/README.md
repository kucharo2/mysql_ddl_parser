# Návod na spustenie projektu Debezium

Spustenie Debeziua vyžaduje 3 základné služby: Zookeeper, Kafku a služby konektorov od Debezia. Na spustenie jednotlivých služieb v rámci tohto návodu sa použije [Docker](https://www.docker.com/get-docker).

## Zostavenie projektu Debezium
Toto CD obsahuje zostavené _tar.gz_ súbory projektu v zložke [compiled](/compiled). Na zostavenie nových _tar.gz_ súborov z aktuálneho zdrojového kódu je možné použiť Maven s profilom **assembly**. Počas zostavovania sa automaticky spustia Unit a integračné testy. Integračné testy vyžadujú spustenie pripraveného MySQL kontajneru v Dockeru, ktorého spustenie v rámci buildu sa občas nepodarí. Z tohto dôvodu doporučujem integračné testy preskočit pomocou prepínača **-DskipITs**.

```shell
mvn clean install -DskipITs -Passembly
```    

### Príprava docker obrazov 
Zo zostavených _tar.gz_ súborov si pomocou **docker build** príkazu vytvoríme docker obrazy, ktoré použijeme pri spustení projektu Debezium. Zostavené _tar.gz_ nakopírujeme do zložky [docker](/docker). V tejto zložke vytvoríme obrazy príkazom.

```shell
docker build --build-arg DEBEZIUM_VERSION=0.8.0 -t debezium/connect:0.7 -f Dockerfile.local .
```

### Spustenie Debezia
Služby projektu Debezium spustíme pomocou **docker-compose** príkazu a **yaml** definície popisujúcej nastavenia jednotlivých služieb. Pred jeho spustením je potrebné mať exportovanú premennú _DEBEZIUM_VERSION_. Bližšie informáciie o spustení je možné zhliadnuť [tu](https://github.com/debezium/debezium-examples/tree/master/tutorial). V zložke [docker](/docker) spustíme príkaz: 

```shell
export DEBEZIUM_VERSION=0.7
docker-compose -f docker-compose-mysql.yaml up
```

V tejto chvíli sú spustené všetky služby, ktoré Debezium vyžaduje a je možné nakonfigurovať konektor, ktorý bude odchytávať zmeny v databázy. Súčasťou spustených služieb je aj kontajner s ukážkouvou MySQL databázou, na ktorú sa je možné pripojiť. Konfigurácia konektoru prebieha pomocou Kafka [REST api](https://docs.confluent.io/3.0.0/connect/userguide.html#rest-interface). Nový konektor, ktorý sa pripojí k testovacej databázy konfigurujeme volaním príkazu **curl** a konfiguračným JSON súborom ktorý sa nachádza v zložke [docker](/docker). V konfiguračnom súbore je definované pipojenie k databázy, trieda konektoru, ktorý sa má použiť a taktiež nastavenie parsovacieho módu, ktorý zabezpečí, že sa pri parsovaní DDL použije nová implementácia.

```shell
curl -i -X POST -H "Accept:application/json" -H  "Content-Type:application/json" http://localhost:8083/connectors/ --data "@register-mysql.json"
``` 

Príkazom `curl -H "Accept:application/json" localhost:8083/connectors/` je možné zobraziť všetky nakonfigurované konektory. Tým môžme skontrolvoať úspešné vytvornie MySQL konektoru. 

### Testovanie
K testovacej databázy je možné sa pripojiť následujúcich údajov:

Host: localhost:3306
Database: inventory
User: root
Pass: debezium

Spustené DDL príkazy nad touto databázou budú odchytené, spracované a zaznamenané v Kafka topiku s názvom **dbserver1**, ktorý sa rovná konfiguračnému parametru _database.server.name_ nastavenému pri konfiguráciu konektoru. Sledovanie zaznmenaných správ v konkrétnom Kafka topicu je možné použiť docker kontainer. 

```shell
docker run -it --name watcher --rm -e ZOOKEEPER_CONNECT=docker_zookeeper_1 --net docker_default debezium/kafka:0.7 watch-topic -a -k dbserver1
```

Bližsie informácie o názvoch jednotlivých topikov je možné nájsť v [dokumentácii](http://debezium.io/docs/connectors/mysql/#topic-names) projektu Debezium.