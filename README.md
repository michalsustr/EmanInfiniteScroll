Zadání
======

Zobrazení seznamu filmů a jejich detailu s offline databázovou cachí.

Úlohy
-----
1. REST služba s JSON formátem dat (na JSON možno použít nativní třídy, nebo nějakou knihovnu pro konverzi json <-> java objekt)
  - http://api.rottentomatoes.com/api/public/v1.0/lists/movies/upcoming.json?page_limit=5&amp;page=1&amp;country=us&amp;apikey=knsujwp4zgd5pa4nnje32wge 
  - nechat page_limit 5 a postupně donačítat dle scrollování v listu (v URL se tím pádem mění page).

2. Donačítací list (preferuji obecně) – Pokud se uživatel blíží konci seznamu, mělo by se spustit donačítání. Pokud uživatel doscrolloval až dolů a není stále donačteno, měla by být vidět nějaká zřejmá indikace, že nejsme na konci. Pokud nastala při načítání chyba, mělo by to být nějak vidět a uživatel by měl mít možnost požadavek opakovat.
  - V list itemu zobrazit název, obrázek a ostatní co se Vám zachce.

3. Načítaná data by měla být cachována, aby při spuštění aplikace v offline modu bylo zobrazeno to, co bylo naposledy načteno. Možno použít Content providery či jiný Vámi systém cachování do databáze.

4. Udělat i nějaký jednoduchý detail, na který se přejde po kliknutí na položku seznamu. Pro tablety poté zvolit layout, že nalevo je seznam filmů a napravo detail.

Co je důležité: 
  - architektrura řešení a přehlednost kódu,
  - důraz na detaily (plynulost, vychytané UX),
  - znovupoužitelnost napsaných částí (donačítacího seznamu),
  - správné ošetření změny orientace displeje – žádná data nesmí být ztracena, případné dialogy nesmí zmizet, atp.,
  - k řešení je možno využívat knihovny třetích stran

Parametry aplikace:
-------------------
Aplikace by měla být buildována s targetSDK nastaveno na nejnovější verzi a minSDK nastaveno na 10.

Riešenie
========

Známe nedostatky:
-----------------
- Pri nesprávnom parametri page (napr. = 0) sa nespracuje správne chyba vrátená serverom. Server vráti {error:"xxx"} a je zle nastavený HTTP STATUS (nemá byť kód 200 OK pri chybe!).
   
  Dá sa to riešiť vlastným registerTypeAdapterFactory pre gson, ale je to trochu oser.

- (drobnosť) Niektoré polia v JSONe som odignoroval (konkrétne "runtime" v objekte Movie). Odpoveď je číslo, ale ak je runtime neznámy, tak server pre to vráti "", čo nie je validná odpoveď. 

- (drobnosť) Nepoužívam mipmap kvôli podpore starších API

- Trieda InfiniteListFragment nejde to veľkého stupňa abstrakcie, dalo by sa to spraviť aj lepšie pomocou rôznych design patterns.

- Netestoval som verziu pre tablet na nejakom vlastnom zariadení, iba pomocou emulátoru.
