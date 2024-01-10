# Razboi-Card-Game

Proiect pentru facultate!

ID: javaproject123
Password: javaproject123



Proiectul foloseste 6 fisiere si 8 clase pentru rularea jocului in relatie de Client-Server.
IDE-ul folosit pentru scrierea programului a fost Visual Studio Code, iar rularea fiecarui client a fost facuta din 3 fisiere diferite unul pentru server si 2 pentru client. Jocul poate suporta doar 2 jucatori si trebuie rulat doar din hostmachine.

Utils.java
  Fisierul Utils.java implementeazaz functiile getPort(), isValidPort(), si assertTrue() pentru a determina portul pe care ruleaza Clientul pentru a evita erori legate de porturi. ( in cod se foloseste doar un port default dar poate fi modificat pentru a rula pe un port custom ).

SocketConnection.java
  Fisierul SocketConnection.java este o sbuclasa a clasei AutoCloseable si implementeaza urmatoarele functii publice:
  SocketConnection() - pentru creerea unei conexiuni noi si pentru acceptarea conexiunii cu serverul.
  Functia privata SocketConnection() - pentru comunicarea cu serverul.
  sendMsgToServer() - transmite informatia.
  recieveMsgFromServer() - proceseaza informatia.
  socketIsConnected() - verifica conexiunea.
  getAddress() - verifica adresa.
  close() - ( clasa suprascrisa din AutoClosable ) ce inchide conexiunea dintre server-client.

GameOverException.java
  Fisierul GameOverException.java foloseste functia GameOverException() - pentru a trimite mesajul catre server atunci cand jocul s-a termiant.

Deck.java
  Fisierul Deck.java foloseste clasa Deck pentru a simula si implementa un pachet de carti (52 de carti ). 
  Deck() - initializeaza pachetul gol.
  getValue() - returneaza valoarea cartii ca numar intreg.
  getString() - returneaza atat numarul cat si simbolul cartii sub forma de string.
  push() - clasa care adauga carti in pachetul jucatorului castigator.
  pop() - functia care scoate carti din pachetul jucatorului pierzator.
  fill() - functia care initializeaza toate cartiile.
  shuffle() - functia care amesteca pachetul.
  lenght() - determina cate carti sunt in pachet.
  combine() - in cazul unui razboi functia combine ia toate cartiile folosite in razboi de adversar si le adauga in pachetul jucatorului castigator.

Server.java
  ClientHandler: Este o clasă care implementează Runnable și este responsabilă pentru gestionarea cererilor de la clienți. Acesta primește conexiunea de la client, primește și trimite mesaje către client.

  Server:

    În metoda main(), serverul inițializează conexiunea și comunicația cu clienții. Așteaptă doi clienți să se conecteze și apoi începe jocul.
    Inițializează și gestionează pachetele de cărți pentru fiecare jucător și pachetele de cărți așezate deoparte pentru fiecare jucător.
    Împarte pachetul de cărți în jumătate pentru fiecare jucător și începe jocul.
    Folosește o buclă principală pentru gestionarea fiecărei runde a jocului până la finalul jocului.
    Folosește mai multe metode pentru a verifica condițiile de încheiere a jocului, pentru a determina câștigătorii fiecărei runde, pentru a gestiona situațiile de "război" între jucători și pentru a face schimb de mesaje cu clienții.
    Printează mesaje informative pentru server și pentru clienți la fiecare pas al jocului.

    ClientConnection: O clasă care gestionează conexiunea cu clientul, trimite și primește mesaje între server și client.

    Metode precum printServerIP, whoWins, handleWar, flipCards, endGameCheck, sunt responsabile pentru diferite aspecte ale logicii jocului, cum ar fi afișarea IP-ului serverului, determinarea câștigătorului fiecărei runde, gestionarea situațiilor de "război" etc.

    Există, de asemenea, gestionarea excepțiilor prin intermediul clasei GameOverException pentru situații în care jocul se încheie din diverse motive.

    În ansamblu, acest cod implementează logica pentru jocul de cărți "War" și gestionarea conexiunii și comunicației între server și clienți. Este un exemplu simplu de utilizare a programării în rețea și a gestionării unui joc simplu de cărți între doi clienți și un server.


Client.java  
  Clasa Client reprezintă un client care se conectează la server și interacționează cu acesta într-un joc de cărți. Iată principalele elemente ale acestei clase:
  Variabile de stare:

    host: Stochează adresa serverului la care clientul se va conecta.
    port: Stochează numărul portului pentru conexiunea serverului.
    ui: O referință către obiectul GUI pentru gestionarea interfeței grafice a utilizatorului.
    Flip: Un indicator pentru stabilirea stării de flip (de a întoarce o carte).
    FlippedCard: Stochează informațiile despre cartea întoarsă.
    response: Variabilă pentru stocarea răspunsurilor de la server.

  Constructor:
    Constructorul primește adresa host și numărul portului port și inițializează variabilele.

  Metoda start():
    Această metodă gestionează conexiunea cu serverul și comunicarea cu acesta.
    Într-un ciclu while, clientul primește mesaje de la server și, pe baza acestor mesaje, decide acțiunile ulterioare.
    Metoda răspunde diferit la diversele tipuri de mesaje primite de la server. De exemplu, dacă mesajul conține informația "GAME_OVER", jocul este considerat încheiat și ciclul se oprește.

  Metode auxiliare:
    resetFlipCardPressed() - Resetarea stării flagului flipCardPressed la false.
    reset() - Resetează anumite componente ale interfeței grafice.
    clearCredentialsFields() - Șterge conținutul câmpurilor pentru credențiale.

  Alte metode pentru gestionarea interacțiunii cu GUI-ul.
  Clasa GUI:
    Această clasă reprezintă interfața grafică a utilizatorului.
    Are componente precum butoane, etichete, câmpuri de text pentru introducerea datelor de autentificare.
    Metodele acestei clase se ocupă de acțiunile ce apar în urma interacțiunii cu butoanele (loginButton, flipButton) sau actualizarea informațiilor vizuale despre cărțile afișate pentru fiecare jucător (setPlayer1Card, setPlayer2Card).

  Metoda main():
    Inițializează un obiect Client și apelează metoda start() pentru a începe interacțiunea cu serverul.
    Această clasă este proiectată pentru a gestiona interacțiunea între client și server în cadrul unui joc de cărți și pentru a afișa interfața grafică pentru utilizator.

