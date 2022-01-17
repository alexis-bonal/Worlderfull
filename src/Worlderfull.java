import extensions.File;
import extensions.CSVFile;

class Worlderfull extends Program {
	/**************************************************
	* auteurs     : BONAL Alexis et BACHMANN Thibaud  *
	*                                                 *
	* description : Worlderfull est un jeu de rôle où *
	*               le joueur va parcourir un monde   *
	*               fantastique à la recherche de     *
	*               reliques qui lui permettront de   *
	*               terminer le jeu.                  *
	*                                                 *
	* date        : dim. 10 janv.                     *
	**************************************************/

// Initialisation des variables globales :

	int LINES     = stringToInt(getCell(loadCSV("../ressources/dim.csv", ';'), 0, 0)); // 25
	int COLUMNS   = stringToInt(getCell(loadCSV("../ressources/dim.csv", ';'), 0, 1)); // 80
	int[] CURSEUR = new int[] {1, 1}; // {ligne, colonne}

	int POSITION = 0;
	String PSEUDO;
	String FICHIER;
	boolean[] RELIQUES = new boolean[] {false, false, false, false}; // math / fr / angl / hist
	boolean[] MATHS    = new boolean[] {false, false, false, false};
	boolean[] FRANCAIS = new boolean[] {false, true , false};
	boolean[] ANGLAIS  = new boolean[] {false, false, false, false};

// listerEnLigne \\
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	void listerEnLigne(int[] liste) {
	// listerEnLigne()
	//
	//   description : Affiche les éléments en liste les uns à la suite des autres, séparés par des virgules et espacés, puis termine avec un point.
	//   entrée : une liste d'entiers.
	//   sortie : un affichage.

		// Initialisation
		int tailleListe = length(liste);

		// Éxecution
		for (int indiceListe = 0; indiceListe < (tailleListe - 1); indiceListe++) {
			print(liste[indiceListe] + ", ");
		}
		println(liste[tailleListe - 1] + ".");
	}

	void listerEnLigne(String[] liste) {
	// listerEnLigne()
	//
	//   description : Affiche les éléments en liste les uns à la suite des autres, séparés par des virgules et espacés, puis termine avec un point.
	//   entrée : une liste de chaînes de caractères.
	//   sortie : un affichage.

		// Initialisation
		int tailleListe = length(liste);

		// Éxecution
		for (int indiceListe = 0; indiceListe < (tailleListe - 1); indiceListe++) {
			print(liste[indiceListe] + ", ");
		}
		println(liste[tailleListe - 1] + ".");
	}

	void testListerEnLigne() {
		println();

		// test sur une liste d'entiers
		int[] listeInt = new int[] {1, 2, 3, 4, 5};
		listerEnLigne(listeInt);

		// test sur une liste de chaînes de caractères
		String[] listeString = new String[] {"Un", "Deux", "Trois", "Quatre", "Cinq"};
		listerEnLigne(listeString);
	}


// initRandom \\
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	int initRandom(int borneInf, int borneSup) {
	// initRandom()
	//
	//   description : Génère un nombre aléatoire compris entre deux bornes données.
	//   entrée : deux entiers, le premier pour la borne inférieur et le second pour la borne supérieur.
	//   sortie : un entier.

		// Sortie
		return (int) (random()*(borneSup + 1 - borneInf) + borneInf);
	}

	void initRandom(int borneInf, int borneSup, int[] liste) {
	// initRandom()
	//
	//   description : Génère un nombre aléatoire compris entre deux bornes données.
	//   entrée : - deux entiers, le premier pour la borne inférieur et le second pour la borne supérieur.
	//            - une liste d'entiers à initialiser
	//   sortie : le tableau donné initialisé

		// Initialisation
		int tailleListe = length(liste);

		// Éxecution
		for (int indiceListe = 0; indiceListe < tailleListe; indiceListe++) {
			liste[indiceListe] = initRandom(borneInf, borneSup);
		}
	}

	void testInitRandom() {
		println();

		// test pour un entier
		int borneInf = 2;
		int borneSup = 5;
		int n;
		for (int i = 0; i < 100; i++) {
			n = initRandom(borneInf, borneSup);
			assertTrue(n >= borneInf && n <= borneSup);
		}

		//test pour une liste
		int[] liste = new int[10];
		initRandom(borneInf, borneSup, liste);
		listerEnLigne(liste);
	}

	String[] melange(String[] liste) {
		int tailleListe = length(liste);
		String[] newListe = new String[tailleListe];

		int cpt = 0;
		while (cpt < tailleListe) {
			String pioche = "";
			int aleat = 0;
			while (equals(pioche, "")) {
				aleat = initRandom(0, tailleListe - 1);
				pioche = liste[aleat];
			}
			liste[aleat] = "";
			newListe[cpt] = pioche;
			cpt++;
		}

		return newListe;
	}


// saisir (int, char ou String) \\
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	int saisirInt() {
	// saisirInt()
	//
	// description : oblige l'utilisateur à saisir un nombre entier.
	// entrée : aucune.
	// sortie : le nombre entier saisi.

		// Initialisation
		String saisi;
		int tailleSaisi;
		int indiceLettre;
		boolean estEntier;
		cusp();


		// Éxecution
		println();
		do {
			curp();

			up();
			clearEOL();
			saisi = readString();
			tailleSaisi = length(saisi);
			estEntier =    tailleSaisi < 10                // Une saisie trop grande peut mener à une erreur, on ne devrait pas dépasser les millions dans les exercices.
					    && tailleSaisi > 0
					    && (   
					           charAt(saisi, 0) == '-'     // Il peut y avoir un signe devant le nombres
					 	    || charAt(saisi, 0) == '+'
					 	    || (   
					 	           charAt(saisi, 0) >= '0' // ou simplement le début du nombre.
					 	    	&& charAt(saisi, 0) <= '9'
					 	    	)
					 	    );
			indiceLettre = 1;
			while (indiceLettre < tailleSaisi && estEntier) {
				estEntier = estEntier && charAt(saisi, indiceLettre) >= '0' && charAt(saisi, indiceLettre) <= '9'; // Le reste des caractères sont des nombres.
				indiceLettre++;
			}
		} while (!estEntier);

		// Sortie
		return stringToInt(saisi);
	}

	int saisirInt(String message) {
	// saisirInt()
	//
	// description : oblige l'utilisateur à saisir un nombre entier.
	// entrée : le message à afficher avant la saisie.
	// sortie : le nombre entier saisi.

		// Initialisation
		String saisi;
		int tailleSaisi;
		int indiceLettre;
		boolean estEntier;
		cusp();

		// Éxecution
		println();
		do {
			curp();
			up();
			clearEOL();
			print(message);
			saisi = readString();
			tailleSaisi = length(saisi);
			estEntier =    tailleSaisi < 10                // Une saisie trop grande peut mener à une erreur, on ne devrait pas dépasser les millions dans les exercices.
					    && tailleSaisi > 0
					    && (   
					           charAt(saisi, 0) == '-'     // Il peut y avoir un signe devant le nombre
					 	    || charAt(saisi, 0) == '+'
					 	    || (   
					 	           charAt(saisi, 0) >= '0' // ou simplement le début du nombre.
					 	    	&& charAt(saisi, 0) <= '9'
					 	    	)
					 	    );
			indiceLettre = 1;
			while (indiceLettre < tailleSaisi && estEntier) {
				estEntier = estEntier && charAt(saisi, indiceLettre) >= '0' && charAt(saisi, indiceLettre) <= '9'; // Le reste des caractères sont des nombres.
				indiceLettre++;
			}
		} while (!estEntier);

		// Sortie
		return stringToInt(saisi);
	}

	char saisirChar() {
	// saisirChar()
	//
	// description : oblige l'utilisateur à saisir un seul caractère.
	// entrée : aucune.
	// sortie : le caractère saisi.

		// Initialisation
		String saisi;
		boolean estSeul;
		cusp();

		// Éxecution
		println();
		do {
			curp();
			up();
			clearEOL();
			saisi = readString(); // REVOIR : cause un retour à la ligne et affiche la saisie
			estSeul = length(saisi) == 1;
		} while (!estSeul);

		// Sortie
		return charAt(saisi, 0);
	}

	char saisirChar(String message) {
	// saisirChar()
	//
	// description : oblige l'utilisateur à saisir un seul caractère.
	// entrée : le message à afficher avant la saisie.
	// sortie : le caractère saisi.

		// Initialisation
		String saisi;
		boolean estSeul;
		cusp();

		// Éxecution
		println();
		do {
			curp();
			up();
			clearEOL();
			print(message);
			saisi = readString(); // REVOIR : cause un retour à la ligne et affiche la saisie
			estSeul = length(saisi) == 1;
		} while (!estSeul);

		// Sortie
		return charAt(saisi, 0);
	}

	String saisirString(String message, String[] reponses) {
	// saisirString()
	//
	//   description : oblige l'utilisateur à saisir l'une des réponses données en paramètres.
	//   entrée : le message à afficher et les réponses attendues.
	//   sortie : l'un des messages souhaités.

		// Initialisation
		String saisi = "";
		int nombreReponses = length(reponses);
		boolean valide = false;
		cusp();

		// Éxecution
		println();
		while (!valide) {
			curp();
			up();
			clearEOL();
			print(message);
			saisi = readString(); // REVOIR : cause un retour à la ligne et affiche la saisie
			int cpt = 0;
			while (!valide && cpt < nombreReponses) {
				valide = equals(toLowerCase(saisi), toLowerCase(reponses[cpt]));
				cpt++;
			}
		}

		// Sortie
		return saisi;
	}

	String saisirString(String message) {
	// saisirString()
	//
	//   description : oblige l'utilisateur à saisir une chaîne de caractères tout en affichant un message.
	//   entrée : le message à afficher et les réponses attendues.
	//   sortie : l'un des messages souhaités.

		// Initialisation
		String saisi;

		// Éxecution
		print(message);
		saisi = readString(); // REVOIR : cause un retour à la ligne et affiche la saisie

		// Sortie
		return saisi;
	}


// ToString \\
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	String boolToString(boolean bool) {
	// boolToString()
	// 
	//   description : convertit un booléen en une chaîne de caractères. 0 pour faux et 1 pour vrai.
	//   entrée : booléen.
	//   sortie : chaîne de caractère.

		// Éxecution
		if (bool) {
			return "1";
		} else return "0";
	}

	String[] tabIntToTabString(int[] tabInt) {
	// tabIntToTabString()
	// 
	//   description : COnvertit un tableau d'entiers en tableau de string.
	//   entrée : tableau d'entiers.
	//   sortie : Tableau de string.

		// Initialisation
		int tailleTab = length(tabInt);
		String[] tabString = new String[tailleTab];

		// Éxecution
		for (int i = 0; i < tailleTab; i++) {
			tabString[i] = "" + tabInt[i];
		}

		// Sortie
		return tabString;
	}


// align (center, right) \\
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	void alignCenter(String message) {
	// alignCenter()
	//
	// description : Aligne du texte au centre de la fenetre.
	// entrée : le message à afficher.
	// sortie : affichage du texte au centre.

		// Initialisation
		int tailleMessage = length(message);

		// Éxecution
		cursor(CURSEUR[0], COLUMNS/2 - tailleMessage/2);
		print(message);
		reset();
		println();
	}

	void alignCenter(String message, String color) {
	// alignCenter()
	//
	// description : Aligne du texte au centre de la fenetre.
	// entrée : le message à afficher.
	// sortie : affichage du texte au centre.

		// Initialisation
		int tailleMessage = length(message);

		// Éxecution
		cursor(CURSEUR[0], COLUMNS/2 - tailleMessage/2);
		text(color);
		print(message);
		reset();
		println();
	}

	void alignRight(String message) {
	// alignRight()
	//
	// description : Aligne du texte au plus près du bord droit de la fenetre.
	// entrée : le message à afficher.
	// sortie : affichage du texte au centre.

		// Initialisation
		int tailleMessage = length(message);

		// Éxecution
		cursor(CURSEUR[0], COLUMNS - tailleMessage);
		print(message);
		reset();
		println();
	}

	void alignRight(String message, String color) {
	// alignRight()
	//
	// description : Aligne du texte au plus près du bord droit de la fenetre.
	// entrée : le message à afficher.
	// sortie : affichage du texte au centre.

		// Initialisation
		int tailleMessage = length(message);

		// Éxecution
		cursor(CURSEUR[0], COLUMNS - tailleMessage);
		text(color);
		print(message);
		reset();
		println();
	}


// cursor \\
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	void resetCursor() {
	// resetCursor()
	//
	// description : place le curseur en haut à gauche de la page.
	// entrée : aucune.
	// sortie : aucune.

		// Initialisation
		CURSEUR = new int[] {1, 1};
	}

	void topCursor() {
	// topCursor()
	//
	// description : place le curseur en haut de la page.
	// entrée : aucune.
	// sortie : aucune.

		// Initialisation
		CURSEUR[0] = 1;
	}

	void bottomCursor() {
	// bottomCursor()
	//
	// description : place le curseur en bas de la page.
	// entrée : aucune.
	// sortie : aucune.

		// Initialisation
		CURSEUR[0] = LINES; 
	}

	void leftCursor() {
	// leftCursor()
	//
	// description : place le curseur à gauche de la page.
	// entrée : aucune.
	// sortie : aucune.

		// Initialisation
		CURSEUR[1] = 1; 
	}

	void rightCursor() {
	// rightCursor()
	//
	// description : place le curseur à droite de la page.
	// entrée : aucune.
	// sortie : aucune.

		// Initialisation
		CURSEUR[1] = COLUMNS; 
	}

	void sauterLigne() {
	// sauterLigne()
	// 
	// description : permet de sauter une ligne
	// entrée : aucune.
	// sortie : aucune.

		// Initialisation
		CURSEUR[0]++;
	}

	void sauterLigne(int n) {
	// sauterLigne()
	//
	// description : permet de faire sauter plusieurs lignes au curseur
	// entrée : le nombre de lignes
	// sortie : aucune

		// Éxecution
		for (int i = 0; i < n; i++) {
			CURSEUR[0]++;
		}
	}


// estDans \\
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	boolean estDans(int[] liste, int n) {
	// estDans()
	//
	// description : vérifie si un entier est bien dans la liste.
	// entrée : la liste à regarder et l'entier à chercher dans la liste.
	// sortie : vrai si le nombre apparait dans la liste et faux sinon.

		// Initialisation
		int i;
		int tailleListe = length(liste);
		boolean estDansListe = false;

		// Éxecution
		i = 0;
		while (i < tailleListe && !estDansListe) {
			estDansListe = liste[i] == n;
			i++;
		}

		// Sortie
		return estDansListe;
	}

	void testEstDans() {
		int[] liste = new int[] {1, 2, 3, 4};
		assertTrue(estDans(liste, 3));
		assertFalse(estDans(liste, 6));
	}


// victoire \\
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	void victoire(boolean condition) {
	// victoire()
	//
	//   description : selon une condition de victoire, 
	//	 entrée : condition booléenne.
	//   sortie : affichage du résultat

		// Éxecution
		if (condition) {
			text("green");
			println("\t\tBravo !");
		} else {
			text("red");
			println("\t\tDommage...");
		}
		reset();
	}


// Exercices de maths \\
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	boolean mathCalculMental() {
	// mathCalculMental()
	//
	//   description : Génère un exercice aléatoirement de calcul mental.
	//   entrée : aucune.
	//   sortie : affichage de l'exercice et booléen de réussite.

		// Initialisation
		char[] signes = new char[] {'+', '-', '*'}; // on évite la division
		char signeAB = signes[initRandom(0, 2)];
		int a = initRandom(1, 10);
		int b = initRandom(1, 10);
		int save; // permet de mettre en mémoire l'échange de a et b puis sert à stocker le résultat

		// Éxecution
		if (signeAB == '+') {        // addition
			save = a + b;
		} else if (signeAB == '-') { // soustraction
			if (a < b) {             // on évite les nombres négatifs
				save = a;
				a = b;
				b = save;
			}
			save = a - b;
		} else {                 // multiplication
			save = a*b;
		}

		return saisirInt("\t" + a + " " + signeAB + " " + b + " = ") == save;
	}

	boolean mathEgalite() {
	// mathEgalite()
	//
	//   description : Génère un exercice aléatoirement avec une égalité à vérifier.
	//   entrée : aucune.
	//   sortie : affichage de l'exercice et booléen de réussite.

		// Initialisation
		char[] signes = new char[] {'+', '-'}; // on reste sur des signes simples
		String[] reponses = new String[] {"vrai", "faux"};
		int reponse;
		char signeAB = signes[initRandom(0, 1)];
		char signeCD;
		int a = initRandom(1, 10);
		int b = initRandom(1, 10);
		int c;
		int d;
		int ab; // calcul de a ± b
		int cd; // calcul de c ± d

		// Éxecution
		do { // On génère c différent de a et de b
			c = initRandom(1, 10); // la probabilité de retomber sur le même nombre est faible, on peut donc générer aléatoirement c
		} while (c == a || c == b);

		if (signeAB == '+') { // calcul de ab 
			ab = a + b;
		} else {
			if (a < b) {
				ab = a;
				a = b;
				b = ab;
			}
			ab = a - b;
		}

		if (c < ab) { // choix du signe de la deuxième égalité
			signeCD = '+';
		} else {
			signeCD = '-';
		}

		if (random() >= 0.5) { // On génère une fois sur deux la bonne réponse
			if (signeCD == '+') {
				d = ab -c;
			} else {
				d = c - ab;
			}
		} else {
			d = initRandom(1, 10);
		}

		if (signeCD == '+') { // calcul de cd
			cd = c + d;
		} else {
			if (c < d) {
				cd = c;
				c = d;
				d = cd;
			}
			cd = c - d;
		}

		if (ab == cd) { // génération de la bonne réponse.
			reponse = 0;
		} else {
			reponse = 1;
		}
		println("\t" + a + " " + signeAB + " " + b + " = " + c + " " + signeCD + " " + d);
		println();
		return equals(saisirString("\tVrai ou Faux : ", reponses), reponses[reponse]);
	}

	boolean mathIsoler() {
	// mathIsoler()
	//
	//   description : Génère un exercice aléatoirement où l'on doit décomposer un nombre,
	//   entrée : aucune.
	//   sortie : affichage de l'exercice et booléen de réussite.

		// Initialisation
		String[] decomposition = new String[] {"unitées", "dizaines", "centaines", "milliers"};
		int nbAleatoire = initRandom(0, 2);
		int aIsoler = length(decomposition);
		int nombre;

		// Éxecution
		if (nbAleatoire == 0) { 
			nombre = initRandom(10, 99);     // On a une chance sur trois de tomber sur un nombre à 2 chiffres
		} else if (nbAleatoire == 1) {
			nombre = initRandom(100, 999);   // à 3 chiffres
		} else {
			nombre = initRandom(1000, 9999); // à 4 chiffres
		}

		if (nombre < 1000) { // on adapte la question au nombre généré
			--aIsoler;
		}
		if (nombre < 100) {
			--aIsoler;
		}

		aIsoler = initRandom(0, aIsoler - 1);
		
		return saisirInt("\tIsole les " + decomposition[aIsoler] + " du nombre " + nombre + " : ") == nombre%((int) pow(10, aIsoler + 1))/((int) pow(10, aIsoler));
	}
	
	boolean mathTrie() {
	// mathTrie()
	//
	//   description : Génère un exercice aléatoire avec une liste à trier.
	//   entrée : aucune.
	//   sortie : affichage de l'exercice et booléen de réussite.

		// Initialsation
		int[] nombres = new int[100]; // Contient tous les nombres disponibles
		int taille = 10;
		int[] nombresAleatoires = new int[taille]; // liste de nombres aléatoires tirés dans la liste
		String[] objectif = new String[] {"croissant", "décroissant", "pairs", "impairs"};
		int indiceObjectif = initRandom(0, 1); // Pair ou impair requière trop de vérification pour ce que l'on souhaite faire.

		for (int i = 1; i <= length(nombres); i++) {
			nombres[i - 1] = i;
		}

		// Éxecution
		text("white");
		println("\tVoici une liste de nombres :");
		print("\t\t");

		for (int i = 0; i < length(nombresAleatoires); i++) { // Génération d'une liste aléatoire
			while (nombresAleatoires[i] == 0) {
				nombresAleatoires[i] = nombres[initRandom(0, 99)]; // Faible chance de tomber sur les mêmes nombres
			}
			nombres[nombresAleatoires[i] - 1] = 0;
		}
		reset();
		listerEnLigne(nombresAleatoires);
		text("white");

		if (indiceObjectif == 0 || indiceObjectif == 1) { // Si on est tombé sur croissant ou décroissant
			println("\n\tTrier dans l'ordre " + objectif[indiceObjectif] + " la liste.");
			if (indiceObjectif == 0) { // croissant
				for (int i = 1; i < taille; i++) { // trie dans l'ordre croissant de la liste
					int j = i;
					while (j > 0 && nombresAleatoires[j - 1] > nombresAleatoires[j]) {
						int save = nombresAleatoires[j];
						nombresAleatoires[j] = nombresAleatoires[j - 1];
						nombresAleatoires[j - 1] = save;
						--j;
					}
				}
			} else { // décroissant
				for (int i = 1; i < taille; i++) { // tri dans l'ordre décroissant de la liste
					int j = i;
					while (j > 0 && nombresAleatoires[j - 1] < nombresAleatoires[j]) {
						int save = nombresAleatoires[j];
						nombresAleatoires[j] = nombresAleatoires[j - 1];
						nombresAleatoires[j - 1] = save;
						--j;
					}
				}
			}
		} else { // Pair ou Impair
			println("\n\tQuels sont les nombres " + objectif[indiceObjectif] + " de la liste ?");
			if (indiceObjectif == 2) { // Pair
				for (int i = 0; i < taille; i++) {
					if (nombresAleatoires[i]%2 == 1) {
						nombresAleatoires[i] = -1;
					}
				}
			} else { // Impair
				for (int i = 0; i < taille; i++) {
					if (nombresAleatoires[i]%2 == 0) {
						nombresAleatoires[i] = -1;
					}
				}
			}
		}
		println();
		println();
		reset();

		String[] bonneReponse = tabIntToTabString(nombresAleatoires);
		boolean reussite = true;
		String reponse; // réponse utilisateur
		sauterLigne(8);
		for (int i = 0; i < taille; i++) {
			reponse = saisirString("\tSaisie un nombre de la liste : ", bonneReponse);
			println();
			sauterLigne();
			reussite = reussite && nombresAleatoires[i] == stringToInt(reponse);
		}
		
		return reussite;


	}


// Partie \\
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	//Partie getPartie() {}

	//void setPartie() {}


// Exercices CSV \\
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	boolean exerciceQCM(CSVFile csv) {
	// exerciceQCM()
	//
	//   description : question, nb réponses possibles, réponses possibles
	//   entrée : CSV
	//   sortie : booléen de réussite

		// Initialisation
		int ligneQuestion = initRandom(0, rowCount(csv) - 1);
		String[] reponses;
		int nombreReponse = columnCount(csv, ligneQuestion) - 1;
		String question     = getCell(csv, ligneQuestion, 0);
		String bonneReponse = getCell(csv, ligneQuestion, 1);


		// Éxecution
		reponses = new String[nombreReponse];
		for (int i = 0; i < nombreReponse; i++) {
			reponses[i] = getCell(csv, ligneQuestion, i + 1);
		}
		println(question);
		reponses = melange(reponses);
		listerEnLigne(reponses);
		println();
		return equals(toLowerCase(saisirString(" : ", reponses)), toLowerCase(bonneReponse));
	}

	boolean exerciceQuestRep(CSVFile csv) {
	// exerciceQuestRep()
	//
	//   description : ligne de réponses, question
	//   entrée : CSV à 
	//   sortie : booléen de réussite

		// Initialisation
		int ligneQuestion = initRandom(1, rowCount(csv) - 1);
		String[] reponses;
		int nombreReponse = columnCount(csv, 0);
		String question     = getCell(csv, ligneQuestion, 0);
		String bonneReponse = getCell(csv, ligneQuestion, 1);


		// Éxecution
		reponses = new String[nombreReponse];
		for (int i = 0; i < nombreReponse; i++) {
			reponses[i] = getCell(csv, 0, i);
		}
		listerEnLigne(reponses);
		println();
		println(question);
		println();
		return equals(toLowerCase(saisirString(" : ", reponses)), toLowerCase(bonneReponse));
	}

	boolean exerciceRandom() {
	// exerciceRandom()
	//
	//   description : question, nb réponses possibles, réponses possibles
	//   entrée : CSV à 
	//   sortie : booléen de réussite

		// Initialisation


		// Éxecution
		return true;
	}


// scenario \\
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	/*void scene00_dimensionsEcran() {
	// scene00_dimensionsEcran()
	//
	//   description : utile pour redimensionner l'écran à la bonne taille.
	//   entrée : intéraction de l'utilisateur.
	//   sortie : affichage seulement.

		// Éxecution
		clearScreen();
		for (int i = 1; i < LINES; i++) {
			for (int j = 1; j <= COLUMNS; j++) {
				if (i == 1 || j == 1 || i == LINES - 1 || j == COLUMNS) {
					print('#');
				} else print(' ');
			}
			println();
		}
		String message = "Faire correspondre les dimensions de la fenêtre avec le cadre.";
		print(message);
		readString();
	}*/

	void scene01_splashScreen() {
	// scene01_splashScreen()
	//
	//   description : fait défiler le nom du jeu.
	//   entrée : aucune.
	//   sortie : affichage.

		// Initialisation
		File affichage = newFile("../ressources/ascii/worlderfull.txt");
		int temps = 100; // en ms

		// Éxecution
		clearScreen();
		while (ready(affichage)) {
			bottomCursor();
			String ligne = readLine(affichage);
			alignCenter(ligne, "yellow");
			delay(temps);
		}

		for (int i = 0; i < LINES; i++) {
			println();
			delay(temps);
		}
	}

	void scene02_sauvegarde() {
	// scene02_sauvegarde()
	//
	//   description : Lancement du gestionnaire de sauvegarde, renvoie à la création si aucune sauvegarde, sinon renvoie au chargement.
	//   entrée : aucune.
	//   sortie : aucune.

		// Initialisation
		String[] sauvegardes = getAllFilesFromDirectory("../ressources/save/");
		int nombreSauvegardes = length(sauvegardes);

		// Éxecution
		if (nombreSauvegardes == 0) {
			POSITION = 3;
		} else POSITION = 4;
	}

	void scene03_sauvegardeCreer() {
	// scene03_sauvegardeCreer()
	//
	//   description : Permet au joueur de créer une sauvegarde.
	//   entrée : saisie de l'utilisateur.
	//   sortie : affichage et création de fichier.

		// Initialisation
		String[][] save;
		String[] sauvegardes = getAllFilesFromDirectory("../ressources/save/");
		int nombreSauvegardes = length(sauvegardes);


		// Éxecution
		clearScreen();
		resetCursor();

		alignCenter("Créer une partie", "green");
		sauterLigne(2);
		alignCenter("Afin de ne pas perdre vos données, nous allons créer un fichier de sauvegarde.", "white");
		sauterLigne();
		PSEUDO = saisirString("\tVeuillez saisir votre surnom : ");
		FICHIER = "../ressources/save/" + nombreSauvegardes + "-" + toLowerCase(PSEUDO);

		save = new String[][] { {PSEUDO                   , ""                       , ""                       , ""                       },
								{boolToString(RELIQUES[0]), boolToString(RELIQUES[1]), boolToString(RELIQUES[2]), boolToString(RELIQUES[3])},
								{boolToString(MATHS[0]   ), boolToString(MATHS[1]   ), boolToString(MATHS[2]   ), boolToString(MATHS[3]   )},
								{boolToString(FRANCAIS[0]), boolToString(FRANCAIS[1]), boolToString(FRANCAIS[2]), ""                       },
								{boolToString(ANGLAIS[0] ), boolToString(ANGLAIS[1] ), boolToString(ANGLAIS[2] ), boolToString(ANGLAIS[3] )}};
		saveCSV(save, FICHIER, ';');


		clearScreen(); 
		resetCursor();

		alignCenter("Introduction", "green");
		sauterLigne(2);
		alignCenter("Il était une fois,", "white");
		sauterLigne(2);
		alignCenter("Dans le magnifique monde de worlderfull,", "white");
		sauterLigne();
		alignCenter("Quatre reliques qui permettaient à tous de vivre dans la joie et la paix.", "white");
		sauterLigne();
		alignCenter("Ces reliques apportaient de grandes connaissances,", "white");
		sauterLigne();
		alignCenter("Grâce à elles, la vie fût prospère et la science avançait.", "white");
		sauterLigne(2);
		alignCenter("Jusqu'au jour où les chanmés, de petits démons mal intentionnés, arrivèrent.", "white");
		sauterLigne();
		alignCenter("Ils volèrent les reliques pour les cacher dans les différentes régions.", "white");
		sauterLigne();
		alignCenter("Le désespoir des habitants était grand,", "white");
		sauterLigne();
		alignCenter("Car aucun d'entre eux n'était partit aussi loin...", "white");
		sauterLigne();
		alignCenter("Ce n'était pas sans compter sur " + PSEUDO + " qui était prêt à partir à l'aventure", "white");
		sauterLigne(2);
		alignCenter("Arriverez-vous à récupérer les reliques perdues ?", "white");
		println();
		println();
		saisirString("Appuies sur [entrée]", new String[] {""});

		POSITION = 5;
	}

	void scene04_sauvegardeCharger() {
	// scene04_sauvegardeCharger()
	//
	//   description : Propose au joueur les parties existantes et lui demande laquelle charger puis charge la partie voulue.
	//   entrée : Saisie du joueur.
	//   sortie : affichage et chargement de fichier.

		// Initialisation
		String reponse;
		CSVFile fichierCSV;
		String[] sauvegardes = getAllFilesFromDirectory("../ressources/save/");
		int nombreSauvegardes = length(sauvegardes);

		// Éxecution
		clearScreen();
		resetCursor();

		alignCenter("Charger une partie", "green");
		sauterLigne(2);
		alignCenter("Vous pouvez [A] : Charger ou [B] : Créer une partie", "white");
		println();
		println();
		text("white");
		reponse = saisirString("\tQue voulez vous faire ? : ", new String[] {"A", "B"});
		reset();
		if (equals(toLowerCase(reponse), "a")) {
			sauterLigne(4);
			alignCenter("Choisissez un fichier de sauvegarde.", "white");
			println();
			println();
			print("\t");
			listerEnLigne(sauvegardes);
			println();
			println();
			text("white");
			reponse = toLowerCase(saisirString("\tQuel fichier charger ? : ", sauvegardes));
			reset();
			FICHIER = "../ressources/save/" + reponse;
			fichierCSV = loadCSV(FICHIER, ';');
			PSEUDO = getCell(fichierCSV, 0, 0);
			for (int i = 0; i < length(RELIQUES); i++) {
				RELIQUES[i] = stringToInt(getCell(fichierCSV, 1, i)) == 1;
			}
			for (int i = 0; i < length(MATHS); i++) {
				MATHS[i] = stringToInt(getCell(fichierCSV, 2, i)) == 1;
			}
			for (int i = 0; i < length(FRANCAIS); i++) {
				FRANCAIS[i] = stringToInt(getCell(fichierCSV, 3, i)) == 1;
			}
			for (int i = 0; i < length(ANGLAIS); i++) {
				ANGLAIS[i] = stringToInt(getCell(fichierCSV, 4, i)) == 1;
			}

			POSITION = 5;
		} else POSITION = 3;
	}

	void scene05_menuPrincipal() {
	// scene05_menuPrincipal()
	//
	//   description : Le menu principal du jeu auquel on doit pouvoir accéder à tout.
	//   entrée : la saisie du joueur.
	//   sortie : affichage et suite du jeu.

		// Initialisation
		String saisi;
		String[] reponses = {"A", "B", "C", "D", "Z", "exit"};

		// Éxecution
		clearScreen();
		resetCursor();

		background("green");
		alignCenter(" WORLDERFULL ", "black");
		sauterLigne(2);
		leftCursor();

		alignCenter("Bonjour " + PSEUDO + ", tu te trouves à la capitale de Worlderfull.", "white");
		sauterLigne();
		leftCursor();
		alignCenter("D'ici tu peux explorer les différentes régions en quête des reliques.", "white");
		println();

		if (RELIQUES[0]) {
			text("yellow");
		}
		println("\tA\t: Mont Maths");

		if (RELIQUES[1]) {
			text("yellow");
		} else {
			reset();
		}
		println("\tB\t: Bois des Mots Perdus");

		if (RELIQUES[2]) {
			text("yellow");
		} else {
			reset();
		}
		println("\tC\t: La Vallée Historique");

		if (RELIQUES[3]) {
			text("yellow");
		} else {
			reset();
		}
		println("\tD\t: Port Anglo");
		reset();
		println();
		println("\tZ\t: Paramètres");
		println("\texit\t: Quitter");
		println();
		println();
		text("white");
		saisi = toLowerCase(saisirString("Tapes la lettre qui correspond à ton choix et appuies sur [entrée] : ", reponses));

		if (equals(saisi, "z")) {
			POSITION = 50;
		} else if (equals(saisi, "exit")) {
			POSITION = -POSITION;
		} else POSITION = 10*(charAt(saisi, 0) - 'a' + 1); // 10 si a, 20 si b, ...
	}

	// Math \\
		void scene10_mathMenu() {
		// scene10_mathMenu()
		//
		//   description : affiche le menu des épreuves de math.
		//   entrée : saisie de l'utilisateur.
		//   sortie : affichage et suite du jeu.

			// Initialisation
			String saisi;
			String[] reponses = {"A", "B", "C", "D", "Z", "exit"};
			boolean relique = true;

			// Éxecution
			clearScreen();
			resetCursor();

			int i = 0;
			while (relique && i < length(MATHS)) {
				relique = relique && MATHS[i];
				i++;
			}
			RELIQUES[0] = relique;

			background("green");
			alignCenter(" MONT MATHS ", "black");
			sauterLigne(2);
			leftCursor();

			alignCenter("Bienvenue dans les hauteurs du mont Maths.", "white");
			sauterLigne();
			leftCursor();
			alignCenter("Afin de récupérer la relique volée tu devras réussir chacun de ces défis :", "white");
			println();

			if (MATHS[0]) {
				text("yellow");
			}
			println("\tA\t: Calcul mental");

			if (MATHS[1]) {
				text("yellow");
			} else {
				reset();
			}
			println("\tB\t: Vérifier des égalités");

			if (MATHS[2]) {
				text("yellow");
			} else {
				reset();
			}
			println("\tC\t: Isoler unitées, dizaines, centaines, milliers");

			if (MATHS[3]) {
				text("yellow");
			} else {
				reset();
			}
			println("\tD\t: Trier des nombres");
			reset();

			println();
			println("\tZ\t: Retour");
			println("\texit\t: Quitter");
			println();
			println();
			text("white");
			saisi = toLowerCase(saisirString("Choix : ", reponses));

			if (equals(saisi, "z")) {
				POSITION = 5;
			} else if (equals(saisi, "exit")) {
				POSITION = -POSITION;
			} else POSITION = 11 + charAt(saisi, 0) - 'a'; // 11 si a, 12 si b, ...
		}

		void scene11_mathCalculMental() {
		// scene11_mathCalculMental()
		//
		//   description : cette scene permet le bon déroulement du jeu de calcul mental.
		//   entrée : saisie du joueur.
		//   sortie : affichage et suite du jeu.

			// Initialisation
			int nombreErreurs = 0;
			boolean reussite;

			// Éxecution
			clearScreen();
			resetCursor();

			alignCenter("MONT MATHS :: Calcul mental", "green");
			sauterLigne(2);
			alignCenter("Vous devez résoudre 5 calculs pour valider cette épreuve.", "white");
			sauterLigne();
			alignCenter("Vous n'avez le droit qu'à une erreur.", "white");
			println();
			println();
			sauterLigne(2);
			for (int cpt = 0; cpt < 5; cpt++) {
				reussite = mathCalculMental();
				if (!reussite) {
					nombreErreurs++;
				}
				victoire(reussite);
				println();
				println();
				sauterLigne(3);
			}
			reussite = nombreErreurs <= 1;
			alignCenter("Tu as fait " + nombreErreurs + " erreur(s).", "white");
			sauterLigne();
			if (reussite) {
				MATHS[0] = reussite;
				alignCenter("Tu as gagné cette épreuve !", "green");
			} else alignCenter("Tu as perdu cette épreuve...", "red");
			delay(5000);

			POSITION = 10;
		}

		void scene12_mathEgalite() {
		// scene12_mathEgalite()
		//
		//   description : cette scene permet le bon déroulement du jeu d'égalités.
		//   entrée : saisie du joueur.
		//   sortie : affichage et suite du jeu.

			// Initialisation
			int nombreErreurs = 0;
			boolean reussite;

			// Éxecution
			clearScreen();
			resetCursor();

			alignCenter("MONT MATHS :: Vérifier les égalités", "green");
			sauterLigne(2);
			alignCenter("Vous devez dire pour chaque égalités si elle est vraie ou fausse.", "white");
			sauterLigne();
			alignCenter("Vous n'avez le droit qu'à une erreur.", "white");
			println();
			println();
			for (int cpt = 0; cpt < 3; cpt++) {
				reussite = mathEgalite();
				if (!reussite) {
					nombreErreurs++;
				}
				victoire(reussite);
				println();
				sauterLigne(5);
			}
			reussite = nombreErreurs <= 1;
			alignCenter("Tu as fait " + nombreErreurs + " erreur(s).", "white");
			sauterLigne();
			if (reussite) {
				MATHS[1] = reussite;
				alignCenter("Tu as gagné cette épreuve !", "green");
			} else alignCenter("Tu as perdu cette épreuve...", "red");
			delay(5000);

			POSITION = 10;
		}

		void scene13_mathIsoler() {
		// scene13_mathIsoler()
		//
		//   description : cette scene permet le déroulement du jeu de séparation des unités, dizaines, ... .
		//   entrée : saisie du joueur.
		//   sortie : affichage et suite du jeu.

			// Initialisation
			int nombreErreurs = 0;
			boolean reussite;

			// Éxecution
			clearScreen();
			resetCursor();

			alignCenter("MONT MATHS :: Isoler le bon chiffre", "green");
			sauterLigne(2);
			alignCenter("Vous devez isoler le bon chiffre demandé.", "white");
			sauterLigne();
			alignCenter("Vous n'avez le droit qu'à une erreur.", "white");
			println();
			println();
			for (int cpt = 0; cpt < 5; cpt++) {
				reussite = mathIsoler();
				if (!reussite) {
					nombreErreurs++;
				}
				victoire(reussite);
				println();
				println();
				sauterLigne(3);
			}
			sauterLigne(2);
			reussite = nombreErreurs <= 1;
			alignCenter("Tu as fait " + nombreErreurs + " erreur(s).", "white");
			sauterLigne();
			if (reussite) {
				MATHS[2] = reussite;
				alignCenter("Tu as gagné cette épreuve !", "green");
			} else alignCenter("Tu as perdu cette épreuve...", "red");
			delay(5000);

			POSITION = 10;
		}

		void scene14_mathTrie() {
		// ()
		//
		//   description : .
		//   entrée : .
		//   sortie : .

			// Initialisation
			boolean reussite;

			// Éxecution
			clearScreen();
			resetCursor();

			alignCenter("MONT MATHS :: Isoler le bon chiffre", "green");
			println();

			reussite = mathTrie();
			if (reussite) {
				MATHS[3] = reussite;
				alignCenter("Tu as gagné cette épreuve !", "green");
			} else alignCenter("Tu as perdu cette épreuve...", "red");
			delay(5000);

			POSITION = 10;
		}


	// Français \\
		void scene20_frMenu() {
		// ()
		//
		//   description : affiche le menu des épreuves de français.
		//   entrée : saisie de l'utilisateur.
		//   sortie : affichage et suite du jeu.

			// Initialisation
			String saisi;
			String[] reponses = {"A", "B", "C", "Z", "exit"};
			boolean relique = true;

			// Éxecution
			clearScreen();
			resetCursor();

			int i = 0;
			while (relique && i < length(FRANCAIS)) {
				relique = relique && FRANCAIS[i];
				i++;
			}
			RELIQUES[1] = relique;

			background("green");
			alignCenter(" Bois des Mots Perdus ", "black");
			sauterLigne(2);
			leftCursor();

			alignCenter("Bienvenue dans le bois des Mots Perdus.", "white");
			sauterLigne();
			leftCursor();
			alignCenter("Afin de récupérer la relique volée tu devras réussir chacun de ces défis :", "white");
			println();

			if (FRANCAIS[0]) {
				text("yellow");
			}
			println("\tA\t: Les Synonymes");

			if (FRANCAIS[1]) {
				text("yellow");
			} else {
				reset();
			}
			println("\tB\t: L'intrus");

			if (FRANCAIS[2]) {
				text("yellow");
			} else {
				reset();
			}
			println("\tC\t: Les couleurs");
			reset();

			println();
			println("\tZ\t: Retour");
			println("\texit\t: Quitter");
			println();
			println();
			text("white");
			saisi = toLowerCase(saisirString("Choix : ", reponses));

			if (equals(saisi, "z")) {
				POSITION = 5;
			} else if (equals(saisi, "exit")) {
				POSITION = -POSITION;
			} else POSITION = 21 + charAt(saisi, 0) - 'a'; // 21 si a, 22 si b, ...
		}

		void scene21_frSynonymes() {
		// scene21_frSynonymes()
		//
		//   description : cette scene permet le déroulement du jeu de synonymes.
		//   entrée : saisie du joueur.
		//   sortie : affichage et suite du jeu.

			// Initialisation
			CSVFile csv = loadCSV("../ressources/exercices/Francais/Francais_synonymes.csv", ',');
			int nombreErreurs = 0;
			boolean reussite;

			// Éxecution
			clearScreen();
			resetCursor();alignCenter("Le Bois des Mots Perdus :: Synonymes", "green");
			sauterLigne(2);
			alignCenter("Vous devez répondre à 5 questions pour réussir.", "white");
			sauterLigne();
			alignCenter("Vous n'avez le droit qu'à une erreur.", "white");
			println();
			println();
			sauterLigne(2);
			for (int cpt = 0; cpt < 5; cpt++) {
				reussite = exerciceQuestRep(csv);
				if (!reussite) {
					nombreErreurs++;
				}
				victoire(reussite);
				println();
				sauterLigne(4);
			}
			reussite = nombreErreurs <= 1;
			alignCenter("Tu as fait " + nombreErreurs + " erreur(s).", "white");
			sauterLigne();
			if (reussite) {
				FRANCAIS[0] = reussite;
				alignCenter("Tu as gagné !", "green");
			} else alignCenter("Tu as perdu...", "red");
			delay(5000);

			POSITION = 20;
		}

		void scene22_frIntrus() {
			POSITION = 20;
		}

		void scene23_frCouleurs() {
			// Initialisation
			CSVFile csv = loadCSV("../ressources/exercices/Francais/Francais_couleurs.csv", ',');
			int nombreErreurs = 0;
			boolean reussite;

			// Éxecution
			clearScreen();
			resetCursor();alignCenter("Le Bois des Mots Perdus :: Couleurs", "green");
			sauterLigne(2);
			alignCenter("Vous devez répondre à 5 questions pour réussir.", "white");
			sauterLigne();
			alignCenter("Vous n'avez le droit qu'à une erreur.", "white");
			println();
			println();
			sauterLigne(2);
			for (int cpt = 0; cpt < 5; cpt++) {
				reussite = exerciceQuestRep(csv);
				if (!reussite) {
					nombreErreurs++;
				}
				victoire(reussite);
				println();
				sauterLigne(4);
			}
			reussite = nombreErreurs <= 1;
			alignCenter("Tu as fait " + nombreErreurs + " erreur(s).", "white");
			sauterLigne();
			if (reussite) {
				FRANCAIS[2] = reussite;
				alignCenter("Tu as gagné !", "green");
			} else alignCenter("Tu as perdu...", "red");
			delay(5000);

			POSITION = 20;
		}

	void scene30_histGeoMenu() {
	// ()
	//
	//   description : .
	//   entrée : .
	//   sortie : .

		// Initialisation
		CSVFile csv = loadCSV("../ressources/exercices/Histoire.csv", ',');
		int nombreErreurs = 0;
		boolean reussite;

		// Éxecution
		clearScreen();
		resetCursor();alignCenter("VALLÉE HISTORIQUE", "green");
		sauterLigne(2);
		alignCenter("Vous devez répondre à 5 questions pour obtenir la relique.", "white");
		sauterLigne();
		alignCenter("Vous n'avez le droit qu'à une erreur.", "white");
		println();
		println();
		sauterLigne(2);
		for (int cpt = 0; cpt < 5; cpt++) {
			reussite = exerciceQCM(csv);
			if (!reussite) {
				nombreErreurs++;
			}
			victoire(reussite);
			println();
			sauterLigne(4);
		}
		reussite = nombreErreurs <= 1;
		alignCenter("Tu as fait " + nombreErreurs + " erreur(s).", "white");
		sauterLigne();
		if (reussite) {
			RELIQUES[2] = reussite;
			alignCenter("Tu as gagné la relique !", "green");
		} else alignCenter("Tu as perdu...", "red");
		delay(5000);

		POSITION = 5;

		// Éxecution

		
	}

	// Anglais \\

		void scene40_anglMenu() {
		// ()
		//
		//   description : affiche le menu des épreuves de français.
		//   entrée : saisie de l'utilisateur.
		//   sortie : affichage et suite du jeu.

			// Initialisation
			String saisi;
			String[] reponses = {"A", "B", "C", "Z", "exit"};
			boolean relique = true;

			// Éxecution
			clearScreen();
			resetCursor();

			int i = 0;
			while (relique && i < length(ANGLAIS)) {
				relique = relique && ANGLAIS[i];
				i++;
			}
			RELIQUES[3] = relique;

			background("green");
			alignCenter(" Port Anglo ", "black");
			sauterLigne(2);
			leftCursor();

			alignCenter("Bienvenue au port Anglo.", "white");
			sauterLigne();
			leftCursor();
			alignCenter("Afin de récupérer la relique volée tu devras réussir chacun de ces défis :", "white");
			println();

			if (ANGLAIS[0]) {
				text("yellow");
			}
			println("\tA\t: Les Couleurs");

			if (ANGLAIS[1]) {
				text("yellow");
			} else {
				reset();
			}
			println("\tB\t: Les Mois");

			if (ANGLAIS[2]) {
				text("yellow");
			} else {
				reset();
			}
			println("\tC\t: Les Jours de la Semaine");

			if (ANGLAIS[3]) {
				text("yellow");
			} else {
				reset();
			}
			println("\tD\t: Les Saisons");
			reset();

			println();
			println("\tZ\t: Retour");
			println("\texit\t: Quitter");
			println();
			println();
			text("white");
			saisi = toLowerCase(saisirString("Choix : ", reponses));

			if (equals(saisi, "z")) {
				POSITION = 5;
			} else if (equals(saisi, "exit")) {
				POSITION = -POSITION;
			} else POSITION = 41 + charAt(saisi, 0) - 'a'; // 21 si a, 22 si b, ...
		}

		void scene41_anglCouleurs() {
			// Initialisation
			CSVFile csv = loadCSV("../ressources/exercices/Anglais/Anglais_Couleurs.csv", ',');
			int nombreErreurs = 0;
			boolean reussite;

			// Éxecution
			clearScreen();
			resetCursor();alignCenter("Port Anglo :: Couleurs", "green");
			sauterLigne(2);
			alignCenter("Vous devez répondre à 5 questions pour réussir.", "white");
			sauterLigne();
			alignCenter("Vous n'avez le droit qu'à une erreur.", "white");
			println();
			println();
			sauterLigne(2);
			for (int cpt = 0; cpt < 5; cpt++) {
				reussite = exerciceQuestRep(csv);
				if (!reussite) {
					nombreErreurs++;
				}
				victoire(reussite);
				println();
				sauterLigne(4);
			}
			reussite = nombreErreurs <= 1;
			alignCenter("Tu as fait " + nombreErreurs + " erreur(s).", "white");
			sauterLigne();
			if (reussite) {
				ANGLAIS[0] = reussite;
				alignCenter("Tu as gagné !", "green");
			} else alignCenter("Tu as perdu...", "red");
			delay(5000);

			POSITION = 40;
		}

		void scene42_anglMois() {
			// Initialisation
			CSVFile csv = loadCSV("../ressources/exercices/Anglais/Anglais_LesMois.csv", ',');
			int nombreErreurs = 0;
			boolean reussite;

			// Éxecution
			clearScreen();
			resetCursor();alignCenter("Port Anglo :: Mois", "green");
			sauterLigne(2);
			alignCenter("Vous devez répondre à 5 questions pour réussir.", "white");
			sauterLigne();
			alignCenter("Vous n'avez le droit qu'à une erreur.", "white");
			println();
			println();
			sauterLigne(2);
			for (int cpt = 0; cpt < 5; cpt++) {
				reussite = exerciceQuestRep(csv);
				if (!reussite) {
					nombreErreurs++;
				}
				victoire(reussite);
				println();
				sauterLigne(4);
			}
			reussite = nombreErreurs <= 1;
			alignCenter("Tu as fait " + nombreErreurs + " erreur(s).", "white");
			sauterLigne();
			if (reussite) {
				ANGLAIS[1] = reussite;
				alignCenter("Tu as gagné !", "green");
			} else alignCenter("Tu as perdu...", "red");
			delay(5000);

			POSITION = 40;
		}

		void scene43_anglJours() {
			// Initialisation
			CSVFile csv = loadCSV("../ressources/exercices/Anglais/Anglais_JoursSemaine.csv", ',');
			int nombreErreurs = 0;
			boolean reussite;

			// Éxecution
			clearScreen();
			resetCursor();alignCenter("Port Anglo :: Jours", "green");
			sauterLigne(2);
			alignCenter("Vous devez répondre à 5 questions pour réussir.", "white");
			sauterLigne();
			alignCenter("Vous n'avez le droit qu'à une erreur.", "white");
			println();
			println();
			sauterLigne(2);
			for (int cpt = 0; cpt < 5; cpt++) {
				reussite = exerciceQuestRep(csv);
				if (!reussite) {
					nombreErreurs++;
				}
				victoire(reussite);
				println();
				sauterLigne(4);
			}
			reussite = nombreErreurs <= 1;
			alignCenter("Tu as fait " + nombreErreurs + " erreur(s).", "white");
			sauterLigne();
			if (reussite) {
				ANGLAIS[2] = reussite;
				alignCenter("Tu as gagné !", "green");
			} else alignCenter("Tu as perdu...", "red");
			delay(5000);

			POSITION = 40;
		}

		void scene44_anglSaisons() {
			// Initialisation
			CSVFile csv = loadCSV("../ressources/exercices/Anglais/Anglais_Saisons.csv", ',');
			int nombreErreurs = 0;
			boolean reussite;

			// Éxecution
			clearScreen();
			resetCursor();alignCenter("Port Anglo :: Saisons", "green");
			sauterLigne(2);
			alignCenter("Vous devez répondre à 5 questions pour réussir.", "white");
			sauterLigne();
			alignCenter("Vous n'avez le droit qu'à une erreur.", "white");
			println();
			println();
			sauterLigne(2);
			for (int cpt = 0; cpt < 5; cpt++) {
				reussite = exerciceQuestRep(csv);
				if (!reussite) {
					nombreErreurs++;
				}
				victoire(reussite);
				println();
				sauterLigne(4);
			}
			reussite = nombreErreurs <= 1;
			alignCenter("Tu as fait " + nombreErreurs + " erreur(s).", "white");
			sauterLigne();
			if (reussite) {
				ANGLAIS[3] = reussite;
				alignCenter("Tu as gagné !", "green");
			} else alignCenter("Tu as perdu...", "red");
			delay(5000);

			POSITION = 40;
		}

	void scene50_paramMenu() {
	// ()
	//
	//   description : .
	//   entrée : .
	//   sortie : .

		// Initialisation

		// Éxecution
		POSITION = 5;
	}

	void scene60_victoire() {
	// ()
	//
	//   description : .
	//   entrée : .
	//   sortie : .

		// Initialisation

		// Éxecution
		clearScreen(); 
		resetCursor();

		alignCenter("THE END", "green");
		sauterLigne(2);
		alignCenter("Bonjour " + PSEUDO + ",", "white");
		sauterLigne(2);
		alignCenter("Je suis le roi de Worlderfull.", "white");
		sauterLigne();
		alignCenter("Mes messagers m'ont fait écho de ton aventure héroïque.", "white");
		sauterLigne();
		alignCenter("Je tiens à te remercier pour tout ce que tu as fait.", "white");
		sauterLigne();
		alignCenter("Pour t'honorer, je t'adoube chevalier du royaume.", "white");
		sauterLigne();
		alignCenter("Ainsi, tu pourras continuer de visiter les différentes régions à ta guise.", "white");
		sauterLigne(2);
		alignRight("signé Rathur,", "white");
		sauterLigne();
		alignRight("Roi de Worlderfull.", "white");
		println();
		println();
		saisirString("Appuies sur [entrée]", new String[] {""});
	}

	void scene70_quitter() {
	// ()
	//
	//   description : .
	//   entrée : .
	//   sortie : .

		// Initialisation
		String[][] save;
		boolean reussite = true;

		// Éxecution
		for (int i = 0; i < length(RELIQUES); i++) {
			reussite = reussite && RELIQUES[i];
		}

		save = new String[][] { {PSEUDO, "", "", ""},
								{boolToString(RELIQUES[0]), boolToString(RELIQUES[1]), boolToString(RELIQUES[2]), boolToString(RELIQUES[3])},
								{boolToString(MATHS[0]   ), boolToString(MATHS[1]   ), boolToString(MATHS[2]   ), boolToString(MATHS[3]   )},
								{boolToString(FRANCAIS[0]), boolToString(FRANCAIS[1]), boolToString(FRANCAIS[2]), ""                       },
								{boolToString(ANGLAIS[0] ), boolToString(ANGLAIS[1] ), boolToString(ANGLAIS[2] ), boolToString(ANGLAIS[3] )}};
		saveCSV(save, FICHIER, ';');

		if (reussite) {
			scene60_victoire();
		}

		clearScreen(); 
		resetCursor();

		CURSEUR[0] = LINES/2;
		alignCenter("Merci " + PSEUDO + " d'avoir joué à Worlderfull.", "white");
		delay(5000);

		clearScreen();
		resetCursor();
		reset();
	}


// algorithm \\
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	void algorithm() {
		while (POSITION >= 0) {
			if (POSITION < 10) {        // premiers affichages
				if (POSITION == 0) {
					//scene00_dimensionsEcran();   // 0 redimmensionner écran
					scene01_splashScreen() ;     // 1 afficher ascii worlderfull
					scene02_sauvegarde();        // 2 lancer le gestionnaire de sauvegardes
				} else if (POSITION == 3) {
					scene03_sauvegardeCreer();   // 3 créer une sauvegarde s'il faut
				} else if (POSITION == 4) {
					scene04_sauvegardeCharger(); // 4 Charger une sauvegarde existante sinon
				} else {
					scene05_menuPrincipal();     // 5 Menu principal du jeu avec les lieux, quitter et paramètres
				}
			} else if (POSITION < 20) { // mathématiques
				if (POSITION == 10) {
					scene10_mathMenu();         // 10 menu
					if (POSITION == 11) {
						scene11_mathCalculMental(); // 11 mathCalculMental
					} else if (POSITION == 12) {
						scene12_mathEgalite();      // 12 mathEgalite
					} else if (POSITION == 13) {
						scene13_mathIsoler();       // 13 mathIsoler
					} else if (POSITION == 14) {
						scene14_mathTrie();         // 14 mathTrie
					}
				}
			} else if (POSITION < 30) { // français
				if (POSITION == 20) {
					scene20_frMenu();           // 20 menu
					if (POSITION == 21) {
						scene21_frSynonymes();
					} else if (POSITION == 22) {
						scene22_frIntrus();
					} else if (POSITION == 23) {
						scene23_frCouleurs();
					}
				}
			} else if (POSITION < 40) { // histoire - géo
				if (POSITION == 30) {
					scene30_histGeoMenu();      // 30 menu
					if (POSITION == 31) {
						
					}
				}
			} else if (POSITION < 50) { // anglais
				if (POSITION == 40) {
					scene40_anglMenu();         // 40 menu
					if (POSITION == 41) {
						scene41_anglCouleurs();
					} else if (POSITION == 42) {
						scene42_anglMois();
					} else if (POSITION == 43) {
						scene43_anglJours();
					} else if (POSITION == 44) {
						scene44_anglSaisons();
					}
				}
			} else { // Paramètres
				if (POSITION == 50) { 
					scene50_paramMenu();        // 50 menu
				}
				
			}
		}
		scene70_quitter();                      // 70 quitter // 60 victoire
	}
}
