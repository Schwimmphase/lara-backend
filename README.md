# lara Backend
Backend für das PSE-Projekt lara auch Werkzeug zur Literaturrecherche genannt.

## Setup

### Datenbank
Diese Anwendung braucht eine PostgreSQL-Datenbank. In den Umgebungsvariablen können alle Parameter für die Verbindung
mit der Datenbank gesetzt werden. Das Schema `lara` muss existieren. Die Tabelle wird automatisch erstellt.

### Umgebungsvariablen
Um das Projekt zu starten, müssen Umgebungsvariablen gesetzt werden. Diese können in einer Datei `.env` gesetzt werden.
Die Umgebungsvariablen sind:
- `POSTGRESQL_HOSTNAME`: Hostname der Datenbank
- `POSTGRESQL_PORT`: Port der Datenbank
- `POSTGRESQL_DATABASE`: Name der Datenbank
- `POSTGRESQL_USERNAME`: Benutzername für die Datenbank
- `POSTGRESQL_PASSWORD`: Passwort für den Benutzer

### Docker
Der einfachste Weg ist es, Docker zu verwenden. Dafür muss Docker installiert sein. Danach kann das Projekt mit 
folgendem Befehl gestartet werden:
```sh
docker-compose up
```

### Manuell
Für die manuellen Weg muss [Java 17](https://adoptium.net/?variant=openjdk17&jvmVariant=hotspot) und 
[Maven](https://maven.apache.org/) installiert sein. Zuerst muss das Projekt gebaut werden:
```sh
mvn clean package
```

Danach kann das Projekt mit folgendem Befehl gestartet werden:
```sh
java -jar target/backend-1.0-SNAPSHOT.jar
```

### Erste Anmeldung
Bevor die Anwendung verwendet werden kann, muss ein Benutzer angelegt werden. Dafür sollte zuerst eine neue 
Nutzerkategorie in der Tabelle `user_categories` erstellt werden. Danach kann ein neuer Benutzer in der Tabelle `users`
erstellt werden. Die Spalte `user_category_id` muss auf die ID der Nutzerkategorie gesetzt werden.
