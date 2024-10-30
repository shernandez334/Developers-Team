# Escape Room

## Design Patterns

One of the goals of this project was to implement different design patterns. Some of the patterns used include:

- **DAO:** abstract the data retrieval and storage from the business logic following the principle of separation concerns.
- **Abstract Factory:** switch between different database providers (MySQL and MongoDB) with minimum code changes.
- **Singleton:** ensure a class has only one instance and provide a global point of acces to that instance throughout the app execution.
- **Observer:** player may subscribe and unsubscribe to receive notifications.
- **Chain of responsibility:** rewards and badges attainment conditions are passed along a chain of Reward Handlers that either process the request of pass it to the next handler in the chain.
- **Template:** a template Reward Handler class provides a basic structure for other reward handlers, promoting code reusability and simplifying the creation of new rewards.

## Technology/libraries

- #### Testing: JUnit and Mockito
- #### Database: JDBC Driver for MySQL (Connector/J)
- #### Logging: SLF4J (logback implementation)

## Assignment

### Nivell 1 ⭐

Hem de desenvolupar una aplicació per gestionar un **Escape Room virtual**, on els usuaris puguin gaudir d'aventures emocionants i resoldre enigmes desafiant. En el nivell 1, com a persistència **MySQL**.

#### L'aplicació ha de complir amb els següents requisits:

- En el nostre Escape Room virtual, que té un nom específic, oferim una varietat de **sales** temàtiques, **pistes** intrigants i **objectes de decoració** únics.
- Cada sala té assignat un **nivell de dificultat** per proporcionar una experiència equilibrada i desafiadora
- Les pistes estan dissenyades amb **temes específics** per guiar els jugadors en la cerca de solucions
- Els objectes de decoració contribueixen a crear una atmosfera immersiva i memorable a cada sala, utilitzant diferents tipus de **materials**.
- Tots els elements tenen un **preu associat** que reflecteix el seu valor en el joc
- El nostre Escape Room virtual ha de mantenir un **inventari** actualitzat de totes les sales, pistes i objectes de decoració disponibles.
- A més, portarem un registre del **valor total de l'inventari** per tenir una visió clara dels nostres actius.
- L'aplicació oferirà una funcionalitat per emetre **certificats** de superació d'enigmes, on es registraran els assoliments aconseguits pels jugadors durant la seva experiència a l'Escape Room.
- A més, se'ls podran atorgar possibles **regals o recompenses** com a reconeixement per la seva habilitat i destresa en resoldre els reptes plantejats.

#### Pel que fa a les funcionalitats a mostrar per pantalla, s'espera que inclogui com a mínim les següents:

- Crear un nou Escape Room virtual.
- Afegir una nova sala amb el seu respectiu nivell de dificultat.
- Incorporar pistes temàtiques per enriquir l'experiència de joc.
- Introduir objectes de decoració per ambientar les sales de manera única.
- Mostrar l'inventari actualitzat, mostrant les quantitats disponibles de cada element (sales, pistes i objectes de decoració).
- Visualitzar el valor total en euros de l'inventari de l'Escape Room virtual.
- Permetre la retirada de sales, pistes o objectes de decoració de l'inventari.
- Generar tiquets de venda per als diferents jugadors/es.
- Calcular i mostrar el total d'ingressos generats per vendes de tiquets de l'Escape Room virtual.
- Notificar als usuaris sobre esdeveniments importants a l'Escape Room, com l'addició de noves pistes, la finalització d'una sala, etc.
- Els usuaris interessats en aquests esdeveniments podran sol·licitar registrar-se per poder rebre notificacions quan es produeixin esdeveniments rellevants.

### Nivell 2 ⭐⭐

Tèsting.

### Nivell 3 ⭐⭐⭐

Usa com a persistència MongoDB.
