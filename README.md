Muenzproblem-Solver
===================

Eine GUI die einen Algorithmus zum Lösen des Münzproblems beschreibt. 

Dieses Programm soll dabei helfen zu verstehen wie man Problemstellungen wie die folgende lösen kann:

"In einem fernen Land herrscht ein anderes Währungssystem: Es gibt nur Münzen mit dem Wert 2, 3, 5, 7 und 13.
Wie viele Möglichkeiten gibt es die Beträge 12, 123, 1234, 12345, 123456, 1234567, 2345678 und 123456789 auszuzahlen?"

Die Idee hinter dem Algorithmus viele Fälle zusammenzufassen. Es ist nämlich egal was man vorher ausgegeben hat! Wenn man zum Beispiel einen Betrag von 123 hat, so stehen beginnt man mit der höchsten Münze: 13. Man kann nun die 13er Münze verwenden, dann hat man nurnoch einen Betrag von 110, oder man begibt sich zu den kleineren Münzen und hat dann aber noch den vollen Betrag von 123. Nur diese 2 Möglichkeiten gibt es!

Der Algorithmus funktioniert auf die folgende Weise: Es wird eine Matrix von der Größe [Zu erreichender Geldbetrag+1] x [Münzanzahl] erstellt. Man beginnt mit dem vollen Geldbetrag mit der höchstwertigen Münze, also wird die Position [Zu erreichender Betrag][Münzanzahl-1] mit 1 initialisiert. Anschließend wird durch die Matrix iteriert: Der Wert eines Eintrages ergibt sich aus dem Feld der Metrix an dem selben Geldbetrag, aber nächthöherer Münze und dem Feld aus der Metrix der selben Münze, um den Wert der Münze nach rechts verschoben.

Wegen Faulheit tippe ich später weiter.
