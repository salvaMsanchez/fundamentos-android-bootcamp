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
* [Proyecto: Dragon Ball Battle Simulator App](#proyecto)
	* [Descripción](#descripcion)
	* [Requisitos](#requisitos)
		* [Obligatorios](#obligatorios)
		* [Opcionales](#opcionales)
	* [Diseño](#diseno)
	* [Problemas, decisiones y resolución](#problemas)
	* [Instalación](#instalacion)
	* [Licencia](#licencia)

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
## Proyecto: Dragon Ball Battle Simulator App 👊🏻

![Demo app Android gif](images/demoAppAndroid.gif)

<a name="descripcion"></a>
### Descripción

Dragon Ball Battle Simulator es una aplicación Android desarrollada en Kotlin que permite a los usuarios simular batallas con personajes de Dragon Ball. La aplicación sigue una arquitectura MVVM y cumple con una serie de requisitos obligatorios y opcionales, brindando una experiencia de juego coherente.

<a name="requisitos"></a>
### Requisitos

<a name="obligatorios"></a>
#### Obligatorios

1. **Ventanas y actividades:**
	* Ventana 1: Actividad de inicio de sesión.
	* Ventanas 2 y 3: Fragmentos compartidos en una segunda actividad.
2. **Arquitectura MVVM:**
	* Implementación de la arquitectura Modelo-Vista-VistaModelo-VistaModelo.
3. **Diseño coherente y eficaz:**
	* Diseño libre, pero con atención a la coherencia y eficacia en la presentación de elementos.
4. **Clases de Personajes:**
	* `CharacterDTO`: representa el JSON descargado del servidor.
	* `Character`: clase propia de la aplicación, con puntos de vida y veces seleccionado añadidos. Utilización de la función `map` para transformar de `CharacterDTO` a `Character` con las modificaciones correspondientes.

<a name="opcionales"></a>
#### Opcionales

1. **Restaurar puntos de vida:**
	* Botón en Ventana 2 que, al ser pulsado, restaura todos los puntos de vida de los personajes a 100.
2. **Persistencia del juego:**
	* Los puntos de vida de los personajes se conservan incluso si el usuario cierra la aplicación.
3. **Estadísticas de selección:**
	* Botón en Ventana 3 que muestra el número de veces que el usuario ha seleccionado ese personaje mediante un mensaje estilo Toast.

<a name="problemas"></a>
### Problemas, decisiones y resolución

🚨 POR REDACTAR 🚨

<a name="instalacion"></a>
### Instalación

1. Clona el repositorio.
2. Abre el proyecto en Android Studio.
3. Conecta un dispositivo Android o utiliza un emulador.
4. Ejecuta la aplicación.

<a name="licencia"></a>
### Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE.md]() para más detalles.

---

[Subir ⬆️](#top)

---


