
public class PlateauDeReversi {

	final static int TAILLE = 8;
	static Pion[][] plateau = new  Pion[TAILLE][TAILLE];

	Humain humain = new Humain("Michel Drucker", Pion.NOIR);
	Ordinateur ordinateur = new Ordinateur("Mr l'ordinateur", Pion.BLANC);
	
	public static void main(String[] args) {
		PlateauDeReversi plateau = new PlateauDeReversi();
		plateau.jouer();
	}
	
	public PlateauDeReversi() {

		
		humain.getPion().gagne(2);
		ordinateur.getPion().gagne(2);

		for(int i = 0; i < TAILLE; i++)
			for(int j = 0; j < TAILLE; j++)
				plateau[i][j] = Pion.LIBRE;

		// On place le pions de d�part
		poser(Pion.BLANC, (TAILLE/2)-1, (TAILLE/2)-1);
		poser(Pion.NOIR, (TAILLE/2)-1, TAILLE/2);
		poser(Pion.NOIR, TAILLE/2, (TAILLE/2)-1);
		poser(Pion.BLANC, TAILLE/2, TAILLE/2);

		humain.getPion().gagne(2);
		ordinateur.getPion().gagne(2);
	}
	
	public void jouer() {
		Joueur joueurTemp = humain;
		int nbTourPasse = 0;
				
		
		// Tant on a pass� deux fois le tour
		while(nbTourPasse < 2) {
			boolean hasPlayed = false;
			System.out.println("Au tour de " + joueurTemp.getNom() + " avec le pion " + joueurTemp.getPion().getSymbole());

			afficher();
			
			// Tant on a pas encore jou�
			do{
				if(peutJouer(joueurTemp.getPion())){
					// Au tour du joueur
					int[] placement = joueurTemp.jouer(plateau, joueurTemp.getPion());
					// On test si il est possible de jouer (avec des pions autour de la position)
					int nbPionsTest = tester(joueurTemp.getPion(), placement[0], placement[1]);
					//Si on a trouv� des solutions autour
					if(nbPionsTest > 0) {
						poser(joueurTemp.getPion(), placement[0], placement[1]);
						joueurTemp.getPion().gagne(nbPionsTest);
						nbTourPasse = 0;
						hasPlayed = true;
					}else if(joueurTemp != ordinateur) {
						System.out.println("Pas possible de placer le pion ici :/");
					}						
				}else {
					System.out.println("Pas possible de jouer pour " + joueurTemp.getNom() + ", passes ton tour");
					nbTourPasse++;
					hasPlayed = true;
				}
			}while(!hasPlayed);	
			
			// On change de joueur
			joueurTemp = joueurTemp == humain ? ordinateur : humain;
		}
			
		System.out.println("Merci d'avoir jou�");
	}
		
	public void poser(Pion pion, int x, int y) {
		//On place le pion
		plateau[x][y] = pion;
		int nbPions;
		
		// On va regarder tout autour du point
		// . . .
		// o x o
		// x . .
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++) {
				nbPions = 0;
				// Si on est sur le point �a sert a rien
				if (i != 0 || j != 0) {
					nbPions += checkIfPossible(pion, x, y, i, j);
					// Une fois qu'on a notre nombre de point, on va parcourir ces points ex : x= 1, i=-1, nbPions = 10 on parcours sur la ligne x dans la direction de i * le nombre de pions
					for (int k = 1; k <= nbPions; k++)
						plateau[x + i * k][y + j * k] = pion;
				}
			}
	}
	
	public int tester(Pion pion, int x, int y) {
		int nbPionsPrisMax = 0;
		if(plateau[x][y] == Pion.LIBRE)
			// On va regarder tout atour du point
			// . . .
			// o x o
			// x . .
			for(int i = -1; i <= 1; i++) 
				for(int j = -1; j <= 1; j++) 
					// Si on est sur le point ça sert a rien
					if(i != 0 || j != 0) 
						nbPionsPrisMax += checkIfPossible(pion, x, y, i, j);

		return nbPionsPrisMax;
	}
	
	public int checkIfPossible(Pion pion, int x, int y, int i, int j) {
		int nbPionspris = 0;
		int currentX = x + i;
		int currentY = y + j;
		// On va regarder si il y a un bien d'autres autre pion
		while(currentX >= 0 && currentX < TAILLE && currentY >= 0 && currentY < TAILLE && plateau[currentX][currentY] == pion.autrePion() ) {
			currentX += i;
			currentY += j;
			nbPionspris++;
		}
		
		// Et si on  notre pion a la fin c'est ok sinon 0 car fin du tableau
		if (currentX < 0 || currentX >= TAILLE || currentY < 0 || currentY >= TAILLE || plateau[currentX][currentY] != pion)
			nbPionspris = 0;
		
		return nbPionspris;
	}

	public boolean peutJouer(Pion pion) {
		boolean res = false;
		int i = 0, j;
		
		// On va tester si on peut jouer
		while(i < TAILLE && !res){
			j = 0;
			while(j < TAILLE && !res) {
				if(plateau[i][j] == Pion.LIBRE) {
					res = tester(pion, i, j) > 0;
				}
				j++;
			}
			i++;
		}
		return res;
	}
	
	public void afficher() {
		// On affiche les scores
		System.out.println(Pion.BLANC.getNbPions() + " " + Pion.BLANC.getSymbole());
		System.out.println(Pion.NOIR.getNbPions() + " " + Pion.NOIR.getSymbole());
		System.out.print("  ");

		// On affiche les colonnes
		for(int i = 1; i <= TAILLE; i++ )
			System.out.print(i + " ");
		System.out.println();

		// On affiche les lignes et les symboles a chaque case
		for(int i = 0; i < TAILLE; i++) {
			System.out.printf(i+1 + " ");
			for(int j = 0; j < TAILLE; j++) {
				System.out.printf(plateau[i][j].getSymbole()+" ");
			}
			System.out.println();
		}
	}

}
