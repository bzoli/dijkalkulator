Az alkalmazas 1 vagy 2 parancssori parametert var indulasakor.
Az elso param ertekei standard vagy swing sztringek lehetnek. Az elobbi a System.out-ra ir, az urobbi swinges feluletet hasznal.
Ha az elso param erteke standard, akkor kotelezo a 2. param, ami a lekoteseket tartalmazo allomany neve (eleresi utja) kell legyen. 

A program a ket konfiguracios allomanyt (point.properties illetve pointGroups.properties) az alkalmazas gyokerkonyvtaraban keresi.

A swinges gui hasznalatakor:
upload gomb: feltolti a file-t, ertelmezi, megjeleniti a textareaban es kimenetet general
calculate gomb: ertelmezi a textarea tartalmat, kimenetet general

(a program csv parsere megengedi az ures sorok hasznalatat, es kommentnek tekinti (nem ertelmezi) a # karakterrel kezdodo sorokat)