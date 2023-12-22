<a name="top"></a>

<h1 align="center">
  <strong><span>Bootcamp Desarrollo de Apps Móviles </span></strong>
</h1>

---

<p align="center">
  <strong><span style="font-size:20px;">Módulo: Fundamentos Android 🤖</span></strong>
</p>

---

<p align="center">
  <strong>Autor:</strong> Salva Moreno Sánchez
</p>

<p align="center">
  <a href="https://www.linkedin.com/in/salvador-moreno-sanchez/">
    <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn">
  </a>
</p>

## Índice
 
* [Herramientas](#herramientas)
* [Proyecto: Dragon Ball Heroes App](#proyecto)
	* [Descripción](#descripcion)
	* [Requisitos](#requisitos)
		* [Obligatorios](#obligatorios)
		* [Opcionales](#opcionales)
	* [Diseño](#diseno) 
	* [Problemas, decisiones y resolución](#problemas)
		* [Uso de CoreData con SwiftUI](#coredata)
		* [Utilización de *Routing* para la navegación con un *routeViewModel*](#route)
		* [Ocultar el indicador de flecha en `List`](#chevron)
		* [Uso de animaciones Lottie en WatchOS con SwiftUI](#lottie)
	* [Algunos aspectos en los que seguir mejorando la aplicación](#mejoras)

<a name="herramientas"></a>
## Herramientas

<p align="center">

<a href="https://www.apple.com/es/ios/ios-17/">
   <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Android">
 </a>
  
 <a href="https://www.swift.org/documentation/">
   <img src="https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin">
 </a>
  
 <a href="https://developer.apple.com/xcode/">
   <img src="https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white" alt="Android Studio">
 </a>
  
</p>

* **Android SDK:** API 24 ("Nougat"; Android 7.0)
* **Kotlin:** 1.9
* **Android Studio:** Hedgehog - 2023.1.1

<a name="proyecto"></a>
## Proyecto: Dragon Ball Heroes App

![Demo app iOS gif](images/demoAppiOS.gif)

<a name="descripcion"></a>
### Descripción

Aplicación iOS como proyecto final del módulo *iOS Superpoderes* del *Bootcamp en Desarrollo de Apps Móviles* de [KeepCoding](https://keepcoding.io), donde se nos ha propuesto seguir la arquitectura MVVM con SwiftUI y consumir datos de *[The Marvel Comics API](https://developer.marvel.com)*, teniendo en cuenta los siguientes requisitos:

* Obligatorios:
	* Usar SwiftUI.
	* Usar Combine.
	* Alcanzar un 50% mínimo de cobertura respecto a las pruebas unitarias.
	* Pantallas: listado de héroes y detalle de cada uno mostrando las series en las que aparece.
* Opcionales:
	* Agregar pantallas de carga.
	* Uso de `Async/Await` en vez de Combine.

Cabe destacar que me he decantado por el **uso de asincronía con `Async/Await` ya que permite una adecuada integración para la consecución de los principios *SOLID* a través del uso de protocolos, casos de uso, *repository*, etc.**, tal y como explico en la sección de [Arquitectura](#arquitectura).

Hasta el momento, **el uso del *framework* Combine lo veo muy útil para implementar programación reactiva en UIKit** para gestión de estados, establecimiento de observadores en variables, *bindings*, etc. Un ejemplo de ello es el **proyecto [Marvel App 🦸🏻‍♂️ UIKit + Combine](https://github.com/salvaMsanchez/MarvelApp-UIKit-Combine)** albergado en mi [GitHub](https://github.com/salvaMsanchez).

También, debo señalar que, a causa de que la API de Marvel devuelve héroes que no tienen descripción, foto, etc., he desarrollado las llamadas en función a un listado de nombres que he escogido, los cuales representan los superhéroes más relevantes y que albergan en sí todos los datos a emplear en la aplicación en pos de tener un producto final más visual y estético.

<a name="requisitos"></a>
### Requisitos

<a name="obligatorios"></a>
#### Obligatorios

<a name="opcionales"></a>
#### Opcionales

<a name="diseno"></a>
### Diseño

Como inspiración, he partido del **[concepto creativo y prototipo](https://github.com/salvaMsanchez/ux-ui-bootcamp)** que desarrollé en **Figma** como proyecto final del módulo *UX móvil & diseño de UI* del *Bootcamp en Desarrollo de Apps Móviles*, punto de partida que me ha ayudado para comenzar a desarrollar este proyecto.

<a name="problemas"></a>
### Problemas, decisiones y resolución

<a name="coredata"></a>
#### Uso de CoreData con SwiftUI

Cuando construyes un proyecto con SwiftUI y CoreData para persistir datos, Xcode ya te incluye en el ciclo de vida del proyecto el `persistenceController` como variable de entorno para que se pueda usar en cualquier vista.

Sin embargo, he optado por realizar las llamadas a CoreData al igual que lo haría con una API, dejando la responsabilidad de ello al *repository*, acorde a la arquitectura que vengo exponiendo. De esta forma, y como ya se ha comentado en la sección de [Arquitectura](#arquitectura), conseguimos **separar responsabilidades, reducir la lógica de negocio, facilitar las pruebas unitarias y reutilizar código**.

Aquello que no he podido conseguir en relación a CoreData ha sido la **conectividad y sincronía de los datos persistidos en memoria entre la aplicación iOS y WatchOS**. Estuve realizando búsquedas sobre ello, pero su implementación requería de más tiempo, por lo que representa un tema pendiente de estudio. Pienso que este artículo titulado [iOS Share CoreData with Extension and App Groups](https://medium.com/@pietromessineo/ios-share-coredata-with-extension-and-app-groups-69f135628736) del autor [Pietro Messineo](https://medium.com/@pietromessineo) podría ser un buen punto de partida para su debida investigación.

<a name="route"></a>
#### Utilización de *Routing* para la navegación con un *routeViewModel*

Aunque en un principio puede parecer innecesario en un proyecto simple, su inclusión se basa en las **buenas prácticas de desarrollo y la planificación para el crecimiento futuro del proyecto**.

Para entenderlo, debemos exponer que un `RouteViewModel` se trata de una abstracción que se encarga de manejar la navegación dentro de la aplicación. Su utilidad principal radica en **simplificar y desacoplar la lógica de navegación del resto de pantallas**. Esto permite una mayor modularidad y facilita la expansión y mantenimiento del código a medida que el proyecto evoluciona.

Así, y como ya he mencionado, la inclusión de un `RouteViewModel` puede parecer innecesaria en un proyecto como este; sin embargo, su adopción se justifica por la **visión a largo plazo** y el deseo de **mantener un código limpio y escalable**. Este enfoque proactivo sienta las bases para futuras expansiones y la incorporación de características más avanzadas, como el manejo de sesiones de usuario y otras funcionalidades complejas.

<a name="chevron"></a>
#### Ocultar el indicador de flecha en `List`

Para ser fiel al concepto creativo del que partía para el diseño, debía ocultar el *chevron* predeterminado que aparece en las `List` cuando usamos `NavigationLink`.

Después de realizar varias búsquedas, me encontré el magnífico artículo titulado [SwiftUI NavigationLink Hide Arrow Indicator on List](https://thinkdiff.net/swiftui-navigationlink-hide-arrow-indicator-on-list-b842bcb78c79) del autor [Mahmud Ahsan](https://mahmudahsan.medium.com), donde detalla cómo hacerlo en diversas versiones del sistema operativo iOS y, además, aquellos problemas que pueden surgir.

<a name="lottie"></a>
#### Uso de animaciones Lottie en WatchOS con SwiftUI

Sorpresa fue la mía cuando la implementación de las animaciones Lottie en WatchOS no se realizaba de la misma manera que para iOS. Representó un quebradero de cabeza hasta que encontré el artículo titulado [A Guide to Utilize Lottie Animations in SwiftUI watchOS](https://medium.com/@achmadsyarieft/a-guide-to-utilize-lottie-animations-in-swiftui-watchos-b76e07524700) del autor [Achmad Syarief Thalib](https://medium.com/@achmadsyarieft), donde explica paso a paso cómo podemos conseguir manipular de forma exitosa animaciones Lottie para WatchOS.

<a name="mejoras"></a>
## Algunos aspectos en los que seguir mejorando la aplicación

* **CoreData para Series:** utilizar CoreData para gestionar eficientemente la información de las series en la aplicación, facilitando la manipulación y recuperación de datos.
* **Pantalla de Detalle para Series:** con el fin de mejorar la legibilidad y diseño mediante un diseño *responsive*, tamaños de texto dinámicos, organización en tarjetas, etc.
* **Implementar conectividad entre móvil y reloj:** sincronización de datos y garantizar seguridad para lograr una interacción fluida entre dispositivos móviles y relojes inteligentes.

# Apuntes

* Añadir SplashActivity o RootActivity, pero que solo tenga la función de encargarse de decidir si va al login o al home en función de tener o no token. Investigar si se puede hacer una LaunchScreen que pueda hacer esa decisión.

* Hacer SharedViewModel que realice la llamada y guarde los characters en variable. Así, podremos cargarlos en el RecyclerView. Luego, al pulsar en una celda, podemos pasar la posición en vez del nombre y así mostrar el personaje seleccionado.
* SharedViewModel -> Detección de cambios en distintos fragments. Tendríamos dos fragments, un sharedViewModel donde tenemos una variable que sería la vida que va cambiando y a la que estarían suscritos los dos fragments, así cuando cambia en uno, cambia en el otro.
* SingleActivity
* En el HomeActivity mostramos el primer fragment y cuando se pulse en una celda, navgeamos al siguiente fragment.
* Meter Toast o Dialog cuando se recibe error en la llaamda del login, es decir, cuando las credenciales no son correctas.
* Añadir SharedPreferences.
* PROBLEMA: Hablar sobre que he guardado el token en SharedPreferences, aunque es muy mala práctica, y que he usado un Object como clase a la que acceder desde cualquier lugar a modo de Singleton para guardar los datos en SharedPreferences. He querido hacer el guardado del token en SharedPreferences desde la propia clase APIClient con una varibale de acceso personalizado (get y set), pero me ha resultado muy complicado al necesitar el contexto. Intenté pasarle el contexto al APIClient, pero al llamarlo desde el ViewModel, volvía a necesitar en este el contexto, lo cual es una práctica muy poco recomendado ya que puede haber fugas de memoria (memory leaks).
* Hablar sobre la decisión de incluir en el onCreate del LoginActivity la decisión de inicializar la vista del Login o la vista del Home. Se ha realizado por facilidad y por las pocas exigencias a nivel arquitectura de esta aplicación, pero dependiendo de las funcionalidades del proyecto, se podría haber realizado un SplashScreen que contuviera, además de una pantalla animada de bienvenida a la aplicación, la lógica de enrutamiento. Por otro lado, se podría haber elaborado un RouteActivity que se encargara de esto también.
* Hablar sobre la comprensión de la destrucción de los activities y los fragments y su gestión sobre la pila. Cuando he tenido que configurar el logout desde el primer fragment de mi HomeActivity, navegaba al Login pero no destruía el fragment, por lo que cuando volvía a hacer login y realizaba un back con el botón del propio dispositivo, navegaba al fragment que no había destruido anteriormente al hacer logout.

---

[Subir ⬆️](#top)

---


