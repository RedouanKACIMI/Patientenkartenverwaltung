# Patientenkartenverwaltung


Entwicklung einer Patientenkartenverwaltung

Folgende Daten sollen hier verwaltet werden können:

- Name
- Vorname
- Geburtsdatum
- Versichertennummer
- Kassenname
- Institutionskennzeichen (IK)
- Ablaufdatum


Hierzu soll eine ==Rest Schnittstelle mit Java== entwickelt werden. Eine Prüfung auf valide Daten (Datum, Prüfsummen bei IK und Versichertennummer) sollte im Backend stattfinden. Die Daten können über einen Restclient oder über ==eine Oberfläche== verwaltet werden. Für die Umsetzung können ==bekannte Frameworks== genutzt werden. Die Rest Schnittstelle soll dokumentiert werden.


### Ansatz:

fürs Back-end [here](/PatientsCardsManager/) wurde Java mit Spring Boot Framework benutzt, verknupft mit einer MySQL Datenbank.
Front End [here](/patientcardapp/) wird mit AngularJS betrieben.


### API REST Interface:
BaseUrl: [Host]

- Alle PatientKarten: ```GET '/patient' ```
- Eine bestimmte PatientKarte: ```GET '/patient/{id}' ```
- eine Karte hinzufügen: ```POST '/patient' ``` + Payload (JSON)
- eine Karte updaten: ```PUT '/patient' ``` + Payload (JSON)
- eine Karte löschen: ```DELETE '/patient/{id}' ``` 


### Datenbank Verknüpfung:
In dieser [File](/Patientenkartenverwaltung/PatientsCardsManager/src/main/resources/application.properties) stehen die Anmeldedaten zur MySQL Database. Entweder eine Lokal Datenbank auf dem Localhost einrichten oder die auskommentierte Zeile nochmal einblenden, um die MySql database die ich auf einem Google Cloud Server eingerichtet habe nutzen.


### Live Demo:

Noch nicht deployed : http://Patientenkartenverwaltung.redouan.net
