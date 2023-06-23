Airport services management

Projekt jest aplikacją do zarządzania pracą niewielkiego lokalnego lotniska. Praca lotniska została uproszczona do funkcjonowania podstawowych serwisów, stand managera, general manaera oraz navigatora. Każdej z tych funkcji odpowiada jeden pracownik na dany lot, który jest przedstawicielem danego serwisu/funkcji. Pracownicy po zalogowaniu aplikacji moga wyświetlić formularz adekwatny do danej roli, w którym mogą wykonać swoje zadania - np. przypisywanie pracowników, wysylanie i otrzymywanie wiadomości dotytczącej obsługi danego lotu i czasu w jakim należy wykonać swój serwis. Formularze również dostarczają infpormacji kiedy dany serwis może rozpocząć swoją prace i pozwalają na przesłanie informacji o zakończeniu pracy. Każdy lot posiada status, który przedstawia informację który serwis aktualnie obsluguje dany lot. Aplikacja stworzona jest poza harmonogramem . Każdy pracownik posiada swój harmonogram pracy, na lotnisku istnieje z góry ustalony harmonogram lotów, który pracownicy znają i posiadają w formie elektronicznej. Aplikacja słuzy wyłącznie do kontrolowania procesu obsługi każdego lotu.

Wykorzystane technologie:

Backend: Java + SpringBoot
Frontend: JS + React
BazaDanych: PostgreSQL

Szczegółowy opis działania aplikacji oraz poszczególnych formularzy:

Uzytkownik loguje się za pomocą loginu i hasła. Następnie system wyświetla odpowiedni dla użytkownika formularz. Użytkownicy są dodawani przez administratora systemu. 

General Manager

General manager posiada informacje na temat czasu do następnego odlotu z zewnętrznego serwisu – harmonogramu lotów. Użytkownik loguje się do systemu za pomocą loginu i hasła. System wyświetla odpowiedni dla użytkownika formularz – general manager. W formularzu wyświetlają się informacje o użytkowniku, lista lotów które wylądowały oraz lista lotów które odlecialy  - obie listy odświeżane co sekundę. 
W przypadku gdy samolot wylądował General manager może kliknąć w lot wyświetlany na liście. Po kliknięciu odblokowuje się mozliwośc przydzielenia do lotu o danym numerze Stand Managera oraz czas do odlotu i wiadomość. General manager przydziela stand managera ze zbioru stand managerów. Po przydzieleniu = kliknięciu na przycisk ASSIGN pokazuje mu się nowy, pusty formularz. General manager może przełączać pomiędzy lotami i przypisywać pracowników w dowolnej kolejności według własnego harmonogramu. 

Stand Manager

Użytkownik loguje się do systemu za pomocą loginu i hasła. System wyświetla odpowiedni dla użytkownika formularz – stand manager. W formularzu wyświetlają się informacje o użytkowniku, lista lotów które zostaly do danego uzytkownika przpisane przez General Managera. W przypadku gdy samolot został przydzielony stand manager może wybrać go z listy i rozpocząć przypisywanie użytkowników z danego serwisu. Stand manager po kliknięciu na dany lot dostaje wiadomość oraz czas i odblokowuje się mozliwość przydzielania serwisów i nawigatora dla danego lotu. W formularzu znajdują się sekcje do przydzielania pracownika, czasu i wiadomości dla każdego z serwisów w kolejności wykonywania zadań. Przydzielanie pracownika polega na wybraniu pracownika z dostepnych pracowników z listy rozwijanej, przydzielenie czasu i wiadomości polega na wpisaniu informacji w odpowiednie pole, następnie konieczne jest kliknięcie przycisku ASSIGN. Jak wszystkie wiadomości zostaną przydzielone odblokowuje się stand managerowi przycisk START. Rozpoczyna to czas pierwszego serwisu na wykonanie zadania - Luggage Service.

Serwisy

Użytkownik loguje się do systemu za pomocą loginu i hasła. System wyświetla odpowiedni dla serwisu formularz – np. Luggage Service. W formularzu znajduje się informacja: WAITING  lub ASSIGNED + NR LOTU.  W osobnym okienku wyświetla się przycisk WAITING FOR START oraz START. Użytkownik jest przedstawicielem danego serwisu – na każdy lot do każdego serwisu przydzielany jest jeden użytkownik.  W przypadku gdy samolot został przydzielony użytkownik dostaje wiadomość oraz czas. W przypadku kiedy w drugin okienku pojawi się wiadomość START  odblokowuje się mozliwość wciśnięcia przycisku FINISHED. Po wciśnięciu tego przycisku odblokowuje się czas dla następnego w kolejce serwisu (lub nawigatora).

Navigator

Użytkownik loguje się do systemu za pomocą loginu i hasła. System wyświetla odpowiedni dla użytkownika formularz – navigator. W zależności od tego czy jest to navigator arrival czy navigator departure odblokowane są odpowiednie przyciski. W formularzu znajduje się informacja: WAITING  lub ASSIGNED + NR LOTU.  W osobnym okienku wyświetla się przycisk WAITING FOR START oraz START. W przypadku gdy samolot został przydzielony użytkownik dostaje wiadomość oraz czas. W przypadku kiedy w drugim okienku pojawi się wiadomość START  odblokowuje się mozliwość wciśnięcia przycisku FINISHED. Po wciśnięciu tego przycisku odblokowuje się czas dla pushback service. Po otrzymaniu informacji START po pushback service odblokowuje się  przycisk TAKE OFF. Po kliknięciu tego przycisku pilot może wystartować. Praca dla danego lotu jest zakończona. General manager dostaje informację o wystartowaniu lotu - lot pojawia się na liście Departed Flights. Formularz nawigatora który jest przydzielony do danego samolotu zawiera również pole do wpisania numeru lotu oraz przycisk LANDED który jest odblokowany, aby navigator mógł zgłosić lądowanie danego lotu.

UI

Strona logowania:

![image](https://github.com/monika-sokolowska/Airport-Services/assets/92268170/568e1271-a387-4194-866c-f3b6ea1307f7)

