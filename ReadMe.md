## TitanStorm <img src="https://github.com/Phineas09/TitanStorm/blob/master/TitanStorm/src/main/resources/ro/mta/se/lab/miscellaneous/icons/appIcon.png?raw=true" alt="" style="padding-top: 10px" width="32" height="32" />

## Cuprins

- [Introducere](#Introducere)
- [Configurare](#Configurare)
- [Utilizare](#Utilizare)
- [Limbaj](#Limbaj)
- [Realizator](#Realizator)
- [Diagrame UML](https://github.com/Phineas09/TitanStorm/tree/master/UMLDiagrams)

## Introducere

Proiectul este o aplicatie ce se foloseste de API-ul gratuit pus la dispozitie de 
<b>openweathermap.org </b> pentru a oferi utilizatorului un mediu usor de folosit 
pentru a afla prognoza meteo pentru sapte zile ( cea curenta + inca 6 ) in diferite orase
ale lumii. De asemenea utilizatorului i se pun la dispozitie urmatoarele:

- Buton pentru aflarea automata a locatiei prin intermediul unui API de geolocatie.
- Buton de reload pentru refresh-ul prognozei.
- Selectia tarii si a orasului pentru care se doreste prognoza.
- Selectia zilei ( click in meniul cu zilele, la un click ziua respectiva se va afisa central, la alt click pe aceeasi selectie ziua curenta se va afisa ).
- Afisarea istoricului cautarilor si request-urilor facute de aplicatie intr-un fisier de log setabil.
- Customizarea listei oraselor ( cel default are toate orasele si tarile oferite de catre <b>openweathermap.org </b>.

## Configurare

Programul ofera configurarea fisierului de intrare, fisier din care vor fi citite informatiile despre orasele ce vor aparea in cele doua combo box-uri de alegere ( unul pentru tara, altul pentru oras )
precum si a fisierului de logare ( informatiile din fisierul de logare sunt de tipul Error, Warn sau Info ).
<br>Aceste fisiere se pot da ca parametru la program, in cazul in care nu sunt specificate, cele default de vor utiliza.
<br>Cele doua combo box-uri:
<p align="center">
<img src="https://github.com/Phineas09/TitanStorm/blob/master/ReadMeMisc/ChoiseBoxs.PNG?raw=true" alt="ComboBoxes"/></p>

Parametrii pentru specificarea fisierelor sunt:
```
-inFile <custom city list file path> -logFile <custom log file path>
```
Structura fisierului de intrare ce contine informatiile despre orase: ( <b><i> fiecare element despartit de TAB </b></i> )
```
ID      nm      lat     lon     countryCode
```
Exemplu fisier de intrare:
```
30485	Dahasuways	15.72389	50.729439	YE
30490	Ash Shihr	14.75863	49.606392	YE
30543	Al Ghaylah	14.59583	45.583328	YE
30616	Judaydah	15.07512	45.299622	YE
```
Exemplu continut fisier de log:
```
[ 17.01.2021 - 18.17.15 ] Info: Finding location and loading forecast!
[ 17.01.2021 - 18.17.15 ] Info: HTTP request to https://geo.ipify.org/api/v1?apiKey=at_dSkpqzlKeSA4KaW2PCdTOkHE7eF93
[ 17.01.2021 - 18.17.24 ] Info: HTTP request to http://api.openweathermap.org/data/2.5/onecall?lat=44.49002&lon=26.17338&exclude=minutely,hourly,alerts&units=metric&appid=67ca8d4acae59d540ea421e817caf1bb
[ 17.01.2021 - 18.17.25 ] Info: WeatherProvider provided a 7 day forecast for city Voluntari. 
```

## Utilizare

La deschiderea programului acesta va detecta locatia curenta in functie de IP si va afisa prognoza pe sapte zile pentru acea locatie, de asemenea se va incarca casuta de selectie cu tarile disponibile, dupa selectia unei tari se vor incarca si orasele disponibile pentru acea tara.
<br>Se poate selecta o alta zi din meniul de jos si aceea va fi afisata in centru, la deselectia acesteia prognoza pentru ziua curenta va fi afisata.
<br>Butoanele pentru refresh si gasirea locatiei curenta sunt dispuse intuitiv.<br>

<p align="center">
<img src="https://github.com/Phineas09/TitanStorm/blob/master/ReadMeMisc/UsageGif.gif" alt="Usage Example Gif"/></p>

## Limbaj si framework
- JAVA -> SDK 15
- Maven
- JavaFX
- IntelliJ IDEA
   
## Realizator
	Claudiu-Florentin Ghenea (Phineas09 |  OddPants)
	