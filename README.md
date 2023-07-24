# SNAKE_GAME

Wielowątkowa aplikacja okienkowa przedstawiająca autorską wersję gry Snake. Gracz porusza się wężem za pomocą strzałek po planszy 25x16. Wąż początkowo składa się z jednego segmentu. Zadaniem gracza jest zdobycie jak największej ilości punktów, które zyskuje poprzez zbieranie pożywienia. W celu utrudnienia za każdy zdobyty punkt wąż wydłuża się o jeden segment. 

Dodatkowo po zakończeniu rozgrywki, wyświetla się na ekranie lista 10 najlepszych graczy wraz z ilością punktów jaką uzyskali. Informacja ta jest zapisywana i przechowywanaw pliku binarnym zgodnie ze schematem:
- pole LEN o wielkości 1 bajt - zawierającą informację o ilości znaków opisującychnazwę gracza;
- sekwencja LEN bajtów zawierających znaki składające się na nazwę gracza;
- 4 bajty opisujące ilość zdobytych punktów.
