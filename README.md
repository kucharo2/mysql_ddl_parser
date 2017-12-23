# Lexikálny parser pre DDL dotazy databáze MySQL

Tento repozitár slúži ako úložisko pre vypracovanie diplomovej práce na ČVUT FEL. Práca je vypracovávana pod Red Hat labom jej výsledok má byť súčasťou projektu [Debezium](http://debezium.io/).

## Názov práce 
Lexikálny analyzátor dialektu dotazovacieho jazyka databáze MySQL pre zachhytávanie zmien v databázi.

## Cieľ
Debezium vyžaduje metadata popisujúce štruktúru databáze v závislosit na čase. U MySQL sú DDL príkazy zachytávane, analyzované a na ich základe je budovaný model v pamäti, popisujúci metadata. Stávajúci analyzátor projektu Debezium je zastaralý, zle štruktúrovaný a náchylný k chybám nakoľko jeho implementácia nieje úplná.

### Hlavnou úlohou diplomovej práce je:
 - naštudovať projekt debezium, obzvlášť MySQL konektor
 - analyzovať možnosti nahradenia ručného analyzátoru strojovo generovaným
 - vytvorriť strojovo generovaný analyzátor na základe gramatiky, ktorá je súčassťou zdrojového kódu MySQL a poskytnúť sad testov
 - nahradiť stávajúci analyzátor novo vytvoreným
 - overiť funkciu analyzátoru pomocou benchmarku SQL príkazov, kktoré sú súčasťou testovacej sady projektu Debezium
 - poskytnúť vytvorený kód projektu Debezium pod open source licencí k jeo začleneniu a aktualizovat projektovú dokumentáciu







