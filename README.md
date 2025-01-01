## Guess-the-movie
### PR01-Ejercicio 1 M03-UF3: Programación Estructurada
### Víctor Martínez
** **
**Descripción del proyecto:**

Este ejercicio, consiste en la creación de un programa donde el usuario ha de adivinar el nombre de una película aleatoria.

Para navegar por este programa utilizamos un menú, donde el usuario puede intentar adivinar el nombre de esta:
```
[1] Guess a letter  
[2] Guess the movie's title
[3] Exit
```
[1] Insertar una letra para comprobar si está en el nombre de la película.

[2] Intentar adivinar el nombre completo de la película

[3] Cierra el programa

** **
**Clase Game:**

*La clase carga y gestiona el nombre de la película a adivinar*
- ***film:*** Nombre de la película
- ***guess:*** Nombre de la película ocultado
- ***errorList:*** ArrayList de letras incorrectas

**Clase Player:**

*La clase contiene los datos del usuario durante la partida. Y se encarga de almacenarlos al finalizar.*
- ***turns:*** Turnos que tiene el jugador para adivinar la palabra
- ***points:*** Número de puntos que ha logrado obtener el jugador
- ***leaderboard*** Array list que almacena las 5 mayores puntuaciones junto al nickname del jugador correspondiente

** **
**movies.txt**

Fichero que utilizamos para obtener el nombre de la película aleatoria a adivinar.

**scores.data**

Fichero que almacena la tabla con las mejores 5 puntuaciones.
