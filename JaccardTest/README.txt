Rovelli Hugo TP2

IDE: Netbeans

Suivre les instruction:

1 - Tapper default pour r�pertoir par d�faut ('./test/corpus), corpus simple pour v�rifier le bon fonctonnement des requ�tes ou le chemin vers le corpus voulu ('~' permet d'aller dans le repertoir de l'utilisateur courant).

2 - query

3 - les mots � rechercher doivent �tre entre '', les op�rateurs sans.

Je suis contient qu'il existe de meilleurs manni�re de traiter chaque requ�te que celle de mon programme.
Le code est un peu lourd mais il me semble qu'il traite l'ensemble des cas recherch�s.

Une m�thode de substitution dans la requ�te aurait pu �tre mis en place ex:

'a' and 'b' or 'c' and ('a' or 'b')
{id_resultat1} or 'c' and ('a' or 'b')
{id_resultat1} or 'c' and {id_resultat2}
{id_resultat1} or {id_resultat3}

les id_resultat auraient point�s vers une �l�ment d'une liste contenant le r�sultat de la sous requete.
