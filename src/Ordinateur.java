import java.util.Random;

public class Ordinateur implements Joueur{

	private String nom;
	private Pion pion;

	public Ordinateur(String nom, Pion pion){
		this.nom = nom;
		this.pion = pion;
	}

	@Override
	public int[] jouer(Pion[][] plateau, Pion pion) {
		boolean ok = false;
		int[] res = new int[2];
		int y = 0, x = 0;
		
		Random random = new Random();

		do {
			x = random.nextInt(PlateauDeReversi.TAILLE);
			y = random.nextInt(PlateauDeReversi.TAILLE);
			
			if(x >= 1 && x <= PlateauDeReversi.TAILLE && y >= 1 && y <= PlateauDeReversi.TAILLE) {
				if(plateau[x][y] == Pion.LIBRE) {
					ok = true;
				}
			}
			
		}while(!ok);
		
		res[0] = x;
		res[1] = y;
		
		return res;
	}

	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	public Pion getPion() {
		return this.pion;
	}

}
