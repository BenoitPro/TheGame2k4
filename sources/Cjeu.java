/* * Cjeu.java* * Created on 8 mai 2004, 11:50 */
/** * * @author  Anthony et Benoit MARECHAL*/
import java.util.ArrayList;
import javax.swing.*;
public class Cjeu {
    
    public int[][] jeu ;
    private int[][] tmp ;
    private int nbL,nbC ; // nombre de Lignes ET nombre de Colonnes
    private int val ;
    public int taille_bloc ; 
    public int nbcoup=0;
    private int nbMotif ;
    private final int taille_tmp ;
    public String inf="Jouez !" ;
    public int score_bloc ; 
    public int score_bloc_total ; 
    public int nbbloc21;
    private int nbPre=3;
    public ArrayList v = new ArrayList ();
    public ArrayList score = new ArrayList ();
    private int cpt=0;
    
    public Cjeu(int nbligne,int nbcolone,int nbmotif) { // toutes ses variables doivent etre vérifier avant de lancé l'application
        
        nbL = (nbligne+2) ; nbC = (nbcolone+2) ; // Taille du tablo augmenté de 2 lignes et 2 colonnes !
        nbMotif = nbmotif ; 
        taille_tmp = nbL*nbC/2 ;
        tmp = new int[2][nbL*nbC/2] ; for(int i =0;i<taille_tmp;i++) { tmp[0][i]=0; tmp[1][i]=0; } //*** tmp[][] Mal initialisé (seulement a 20 ici !!) ***//
        jeu = new int[nbL][nbC] ;   
        initialise_jeu();
    }
    
    public void initialise_jeu() {
        
        int i,j;    
        for (i = 1 ; i< nbL-1 ; i++ ) 
            for ( j = 1 ; j< nbC-1 ; j++ ) 
		jeu[i][j] =(int) Math.floor(Math.random()*nbMotif+1) ; 
        for ( j =0 ;j<nbC ; j++) { jeu[0][j] = 0 ; jeu[nbL-1][j] = 0 ; } // 0 autour des bords du tableau
        for ( i =0 ;i<nbL ; i++) { jeu[i][0] = 0 ; jeu[i][nbC-1]= 0 ; } 
    }
    
    public void action(int l,int c) { // ligne jouez ET colonne jouez (coordonnées du bouton cliquez )
        
        taille_bloc=0;  // initialisation de taille_bloc
        for (int i=0; i<2 ;i++) for(int j=0 ; j<taille_tmp ; j++) { tmp[i][j]=0; } //initialisation des coordonnées du bloc
        l++;   // coordonné du bouton +1 (car mon tableau est plus grand)
        c++;   // coordonné du bouton +1
        tmp[0][0]=l;  // Entrer des coordonnées du bouton cliquez
        tmp[1][0]=c;  
        val=jeu[l][c]; // Récupération de la valeur(int (référant o motif)) et mis dans "val"
        if (val !=0) {
            taille_bloc++ ;
            calcul_bloc(l,c);  // Lancement de la fonction récursive pour calculer le bloc (calcul_bloc)   
            if (taille_bloc >1) { // si le bloc est > a 1 
                Precedent(); // on fait une copie du tableu AVANT qu'il soit modifier
                nbcoup++; // on compte lengthnb de coup
                score_bloc = calcul_score() ; // on calcul lengthscore du bloc cliquer
                score_bloc_total+=score_bloc; // on l'additionne au score des blocs précédement cliquer
                remplace_bloc(); // on remplace les bloc cliquer par des 0
                interverti(); // on interverti les 0 , on les monte et on les translate vers la gauche si besoin
                for(int k=1;k<nbC-1;k++) 
                    for(int i=1;i<nbC-1;i++) if(jeu[nbL-2][i]==0)    decalage_Horizontale(i);
                        if (taille_bloc<8) 
                            inf = "Bloc de "+taille_bloc; 
                        else if (taille_bloc>7 && taille_bloc<14)
                            inf = "Bloc de "+taille_bloc+" ==> Super-Bloc !" ;
                        else if (taille_bloc>13 && taille_bloc<20) 
                            inf = "Bloc de "+taille_bloc+" ==> GiGa-Bloc !!";
                        else if (taille_bloc>19)
                            inf = "Bloc de "+taille_bloc+" ==> ULTRA-Bloc !!!";
            }
            else inf = "Bloc de 1: Non supprimable!" ;              
        }
        else inf = "C'est une zone vide";
    }
    
    // fonction récursive donnant les coordonnées des cases similaires (ds tmp[][])
    public void calcul_bloc (int l, int c) {
        
        if (taille_bloc < taille_tmp) {
            int i,j;
            if (jeu[l][c] != 0 ) {
                    if  ( (jeu[l-1][c]==val) && (verif(l-1,c)==true) ) { 
			rempli_tmp(l-1,c);
			calcul_bloc(l-1,c);						
                    }	
                    if ( (jeu[l][c+1]==val) && (verif(l,c+1)==true) ) { 
			rempli_tmp(l,c+1);
			calcul_bloc(l,c+1);						
                    }
                    if  ( (jeu[l+1][c]==val) && (verif(l+1,c)==true) ) { 
			rempli_tmp(l+1,c);
			calcul_bloc(l+1,c);			
                    }
                    if ( (jeu[l][c-1]==val) && (verif(l,c-1)==true) ) { 
			rempli_tmp(l,c-1);
			calcul_bloc(l,c-1);			
                    }
            } // end if jeu[l][c]!=0
        } // end if (taille_bloc<20)
    } // end calcul_bloc
 
    public boolean verif(int l, int c) { // vérification si les coordonné on deja ete entrez

        for(int i=0;i<taille_tmp;i++) 
            if (tmp[0][i] == l) { 
		if (tmp[1][i] == c) { 
			return false;
				}
			}
	return true;
    }
 
    public void rempli_tmp(int l,int c) {

        taille_bloc++;
            for(int i=0;i<taille_tmp;i++) {
		if (tmp[0][i]==0) {
                    tmp[0][i]=l;
                    tmp[1][i]=c;
                    break; // ***pour mettre les valeur seulement ds la 1iere colone et pas ds tout le tablo
                }
            }
    }

    // 2 fonctions servant au decalage verticale remplacement du bloc par des 0 et intervertissement
    public void remplace_bloc() {
    
	for(int i=0;i<taille_tmp;i++) {
            jeu[tmp[0][i] ][ tmp[1][i] ]=0 ; }
    }

    public void interverti() {
    
        for(int k=0;k<nbL;k++) //*** fonction s'executant + de fois ke necessaire dans certains cas ***//
            for(int i=1;i<nbL-1;i++) {
		for(int j=1;j<nbC-1;j++) {
                    if (jeu[i][j]==0) {
			jeu[i][j]=jeu[i-1][j] ;
			jeu[i-1][j] = 0 ;
                    }
		}
            }
    }
    
    public void decalage_Horizontale (int colone) {
        for(int i=1;i<nbL-1;i++)
            for(int j=colone;j<nbC-1;j++)
                jeu[i][j]=jeu[i][j+1];		
    }
 

    public boolean game_over() {
        
        for(int i=1;i<nbL-1;i++) {
            for(int j=1;j<nbC-1;j++) {
                if (jeu[i][j] != 0 ) {
                if ( (jeu[i][j] == jeu[i][j+1]) || (jeu[i][j] == jeu[i+1][j]) ) return false; 
                }
            }
        }
	return true;
 }

    public int calcul_score() {
    
        score_bloc=(taille_bloc*25)+((taille_bloc*25*(taille_bloc-2)*10)/100);
        if (taille_bloc>7) score_bloc+=50; //Super-Bloc (+50pts)
        if (taille_bloc>13) score_bloc+=100; //Giga-Bloc (+150pts)
        if (taille_bloc>19) score_bloc+=200; //Ultra-Bloc (+350pts);
        return score_bloc;  
    }

    public int nb_bloc_de_1() {
        
        for (int i=1; i<nbL-1 ;i++)
            for (int j=1; j<nbC-1; j++)
                if (jeu[i][j]!=0) {
                    nbbloc21++; 
                }
        return nbbloc21;
    }

    public void Precedent() {
    
        int i ,j ;
        int[][] tabpre;
        int score_tmp;
        tabpre = new int [nbL][nbC];
        for (i=0 ; i<nbL ; i++) 
            for (j=0 ; j<nbC ; j++)
                tabpre[i][j]=jeu[i][j];
        v.add(tabpre);
        score_tmp=score_bloc_total;
        score.add(new Integer(score_tmp));        
    }

    public void vide_list() {
    
        int i;
        for (i=v.size()-1 ; i>=0; i--) 
            v.remove(i);  
    }
 
}
