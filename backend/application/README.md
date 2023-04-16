# TO-project
Airport services management
System zarządzania obsługą lotniska
1) System ma na celu usprawnianie obsługi naziemnej lotniska. 
Będzie to aplikacja typu klient-serwer, w której pracownicy 
o poszczególnych rolach będą mieli możliwość komunikacji ze 
sobą za pomocą GUI poprzez serwer. Funkcjonalności, jakie 
przewidujemy to synchronizacja pracy, informacje, jak ma 
zostać ona wykonana, potwierdzenie wykonania pracy, 
odliczanie czasu do odlotu maszyny. 
Role pracowników dostępnych w systemie to kierownik naczelny, 
kierownik stanowiska, pilot, serwis bagażowy, serwis cateringowy, serwis sprzątający, 
serwis boardingowy, serwis wypychający, serwis tankujący, serwis "follow me".
2) Wzorce strukturalne: Pełnomocnik, Dekorator 

   Wzorce behawioralne: Obserwator, Polecenie, Iterator, Mediator
   
   Wzorce kreacyjne: Builder, Metoda wytwórcza
3) Pełnomocnik - stworzenie nowej klasy pośredniczącej, która po otrzymaniu żądania od klienta przeprowadzi kontrolę dostępu i następnie utworzy obiekt usługi i przekaże mu żądanie

   Dekorator - utworzenie dekoratora, który opakowujac obiekty ułatwi przesyłanie informacji na lotnisku wieloma 
          kanałami jednocześnie np. w przypadku opóźnienia lotu
          
   Obserwator - ulatwienie publikacji komunikatów do wszystkich odbiorców, poprzez dodanie mechanizmu subskrypcji do 
          klasy wysyłającej komunikaty
          
   Polecenie - stworzenie łącza pomiędzy obiektami interfejsu użytkownika i logiki biznesowej, tworzenie kolejki poleceń
   
   Builder - budowanie obiektu zawierającego informacje o rodzaju pracy do wykonania
   
   Metoda wytwórcza - użycie do serwisu boardingowy - np. Schody i Autobus bedą implementować interfejs Transport
   
   Mediator - kierownik naczelny
   
   Iterator - użycie do przechodzenia po liście pracowników w celu znalezienia pracownika spełniającego kryteria 
          iteracji (np.wolnego w danym terminie lub o najlepszych kwalifikacjach) 
