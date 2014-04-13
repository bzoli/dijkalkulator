usage: java hu.bernatzoltan.dijkalkulator.App param1 [param2] 

Az alkalmazas 1 vagy 2 parancssori parametert var indulasakor.
Az elso param ertekei standard vagy swing sztringek lehetnek. Az elobbi a System.out-ra ir, az urobbi swinges feluletet hasznal.

Ha az elso param erteke standard, es van 2. param is, akkor a lekoteseket tartalmazo allomany nevet (eleresi utjat) a 2. parambol veszi a program. Ha nincs 2. param, akkor bekeri a konzolon a file nevet. 

A program a ket konfiguracios allomanyt (point.properties illetve pointGroups.properties) az alkalmazas gyokerkonyvtaraban keresi.

A swinges gui hasznalatakor:
upload gomb: feltolti a file-t, ertelmezi, megjeleniti a textareaban(itt modosithatoak a bejegyzesek) es kimenetet general
calculate gomb: ertelmezi a textarea tartalmat, kimenetet general

(a program csv parsere megengedi az ures sorok hasznalatat, es kommentnek tekinti (nem ertelmezi) a # karakterrel kezdodo sorokat)