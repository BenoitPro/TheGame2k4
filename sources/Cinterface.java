import javax.swing.*;
public class Cinterface extends javax.swing.JFrame {
    
    private Cjeu TheGame ;
    private int nbL=6,nbC=6 ;
    private int jeu[][] ;
    private int nbMotif=3 ;
    private int tailleMotif=50;
    private String formeMotif="oei";
    private int score_en_cour ;
    private boolean fini = false ;
    private int coups_pre=1000;
    private int cpt_pre=0;
    private Chrono compte_a_rebour ;
    private defil_score defil ;
    private int premier_chrono=0 ;    
    private java.io.File fichier ; 
    private int tt ;
    private Son M,salut,S1,S2,S3,S4,S5,S6;
    
    public Cinterface() {

        initComponents();
        creation_fichier() ;
        setTitle("The Game 2k4");
        TheGame = new Cjeu(nbL,nbC, nbMotif);
        creation_jeu();
        jRadioButtonFacile.setSelected(true);
        M=new Son("Musique1.wav");
        M.loopSon();
        salut=new Son("2.wav");
        salut.playSon();
        S1=new Son("scorebon.wav");
        S2=new Son("scoremoyen.wav");
        S3=new Son("scoreneg.wav");
        S4=new Son("1.wav");
        S5=new Son("blocde1.wav");
        S6=new Son("blocde1bis.wav");
    }

    public void creation_fichier() {

        fichier = new java.io.File("HighScore5");
        try{ 
            if (!fichier.createNewFile()) // Si le fichier existait deja
            { init_HightScore() ;         // on recopie le fichier ds les labels 
            } 
            else { // Si le fichier n'existait pas on le cré
                java.io.FileOutputStream ostream = new java.io.FileOutputStream(fichier);
                java.io.ObjectOutputStream p= new java.io.ObjectOutputStream(ostream);           
                p.writeUTF("Anonyme") ; p.flush();         
                p.writeInt(0) ; p.flush();
                p.writeUTF("Anonyme") ; p.flush();
                p.writeInt(0) ; p.flush();
                p.writeUTF("Anonyme") ; p.flush();
                p.writeInt(0) ; p.flush();
                ostream.close() ;
                init_HightScore() ; // on recopie le fichier ds les labels
            }
        }
        catch (Exception e){ 
            System.out.println("erreur ds creation_fichier()");
        }
    }
        //** Fin création fichier
        //** Initialisation des jLabel ( de la boite de dialog jDiaHightScore ) par le fichier 
    public void init_HightScore() {
        try {
            java.io.FileInputStream istream = new java.io.FileInputStream(fichier);
            java.io.ObjectInputStream p= new java.io.ObjectInputStream(istream);
            String s  = p.readUTF() ;
            jLabel68.setText(s) ;      
            int v = p.readInt() ;
            jLabel69.setText(String.valueOf(v) ) ;          
            jLabel71.setText(p.readUTF()) ;
            jLabel72.setText(String.valueOf(p.readInt()) ) ;  
            jLabel74.setText(p.readUTF()) ;
            jLabel75.setText(String.valueOf(p.readInt()) ) ;
            istream.close() ;
        }
        catch (Exception e ) { System.out.println("pb ds init_hightScore"); }
    }

        // Fin de l'initialisation 
    public void creation_jeu() {
    // initialisations diverses
        if (formeMotif=="smi")
            Gugus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/smismi2.gif")));
        else Gugus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/Javasmi.gif")));
        if (nbL==6 && nbC==6 && nbMotif==3) labelImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/TheG2.jpg")));
            else if (nbL==12 && nbC==12 && nbMotif==4) labelImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/TheG3.jpg")));
            else if (nbL==18 && nbC==18 && nbMotif==6) labelImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/TheG4.jpg")));
            else if (formeMotif=="smi") labelImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/TheB.jpg")));
            else labelImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/TheG5.jpg")));
        fini = false ; // Variable pour la fin du jeu
        jPanelJeuGrille.removeAll(); // Supression de tous les boutons du jPanel jPanelJeuGrille
        TheGame.nbcoup=0; // Compte le nb de coup 
        TheGame.score_bloc_total=0;
        TheGame.score_bloc=0;  // Score du bloc         
        score_en_cour = 0 ;  // Score augmentant en cour de partie
        jPanelJeuGrille.setLayout(new java.awt.GridLayout(nbL,nbC)) ; // initialisation du GridLayout (pour le jPanelJeuGrille
            if (premier_chrono >0) compte_a_rebour.interrupt() ; // Pour que 2 Chrono ne se lance pa en meme temp     
            if (formeMotif == "smi" ) { // Configuration du mode smiley score block sapel now chrono etc...
                jLscore.setText("Chrono"); // jLscore valait "Score" pour les mode normaux maintenat il vaut "Chrono"
                Affichechrono.setVisible(true); // Apparition du jTextField du compte a rebour               
                compte_a_rebour = new Chrono() ; // Création du compte_a_rebour
                compte_a_rebour.start(); // Lancement du compte a rebour
            }
            else { // Lorsque nous ne somme pas en mode Smiley
                jLscore.setText("Score"); // on remet jLscore a "Score"
                Affichechrono.setVisible(false); // on fait disparaitre le jTextField du compte a rebour
                AfficheScore.setVisible(true); // réaparition du JtextField affichant lengthscore en cour
            }       
            if ((nbL>11 && tailleMotif==50) || (nbC>17 && tailleMotif==50))//Pour que la fenetere tienne toujours dans l'ecran sans redimentionnage !
                tailleMotif=40; 
            if ((nbL>14 && tailleMotif==40) || (nbL>14 && tailleMotif==50) ||(nbC>22 && tailleMotif==40) || (nbC>22 && tailleMotif==50))
                tailleMotif=30;
            if ((nbL*tailleMotif+165)<460) Gugus.setVisible(false); // JFrame trop petite -> on enleve Gugus !
            else Gugus.setVisible(true); 
            // Affichage de la fenetre au CENTRE de l'écran avec des dimensions apropriées
            setBounds(512-((nbC*tailleMotif+135)/2),370-((nbL*tailleMotif+170)/2),nbC*tailleMotif+135,nbL*tailleMotif+170);
            // Création des boutons de jPanelJeuGrille 
             for(int i = 0 ; i<nbL ; i++)
                    for(int j=0 ;j <nbC ; j++) {                 
                        JButton button = new JButton() ;
                        button.setName(String.valueOf((i*nbC)+j));  // on donne un numéro a chaque bouton (on par de 0 )                
                        button.addActionListener(new java.awt.event.ActionListener() { // Ecouteur...
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    buttonActionPerformed(evt);
                }
            });
                        jPanelJeuGrille.add(button); // ajout du bouton
                    }
        validate(); // met a jour la JFrame.
        copi_tableau_Cjeu() ; 
        }

     public void copi_tableau_Cjeu() { // Reproduit graphiquement le tableau créé dans Cjeu

         // initialisation interface
        AfficheScore.setText(String.valueOf(TheGame.score_bloc));
        AfficheCoups.setText(String.valueOf(TheGame.nbcoup));
        infos.setText(TheGame.inf); // récupération des informations selon l'endroi cliqué (bloc de 1, zone vide)
        if (TheGame.inf=="Bloc de 1: Non supprimable!") { 
            if ((int)Math.floor(Math.random()*2+1)==1)
                S5.playSon();
            else S6.playSon();
        }
        // Effet de Style : des gifs sont lancé en fonction de la taille des blocs cliquez
        if (TheGame.taille_bloc>19) Bloc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/3ultra.gif")));
        else if (TheGame.taille_bloc>13) Bloc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/2giga.gif")));
        else if (TheGame.taille_bloc>7) Bloc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/1super.gif")));
        else Bloc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/4null.gif")));

        jeu = TheGame.jeu ; // on reprend le tableau créé ds Cjeu
        if (!fini) {  // Vérification de la fin du jeu    
            for(int i = 0 ; i<nbL ; i++)
                for(int j=0 ;j <nbC ; j++) {
                    JButton but =(JButton) jPanelJeuGrille.getComponent((i)*nbC+(j));                    
                    if ( jeu[i+1][j+1] == 0)   { // si la case est 0 on enleve l'icone et les bordure 
    
                        but.setOpaque(false);
                        but.setIcon(null);
                        but.setBorder(new javax.swing.border.MatteBorder(new java.awt.Insets(0, 0, 0, 0), new java.awt.Color(51,51,255))); //***configurer la couleur est inutil
                    }
                    else { // Selon la valeur du tableau jeu et du motif choisi le motif associé est mis en icone
                        for(int k=1;k<=nbMotif;k++) {
                            if( jeu[i+1][j+1] == k) {
                                but.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/"+formeMotif+tailleMotif+k+".gif")));// les nom des fichiers sont du style "smi501" pour smiley de 50 pixels numero 1
                                but.setBackground(java.awt.Color.BLACK);
                            }
                            but.setBorder(new javax.swing.border.MatteBorder(new java.awt.Insets(1, 1, 1, 1), new java.awt.Color(0,0,0)));                     
                        }
                    }
                }      
       }
        if (TheGame.game_over()) fin_du_jeu(); // on lance la fin du jeu si on ne peu plus effectuer aucun coup
     }

     // La Fonction fin_du_jeu ( création des dialog aproprié, calcul du score et initialisations diverses)
     public void fin_du_jeu() {
        //on gere la "fin du jeu"

        infos.setText("C'est fini !");
        fini = true; 
        
        if(formeMotif!="smi") {                           
            // *** Création de la JdiaScore ***//
            jscorebloc.setText(String.valueOf(TheGame.score_bloc_total)+" pts");
            jnbcoups.setText("Vous avez fait "+String.valueOf(TheGame.nbcoup)+" coups: ");
            jnbcoupspts.setText(String.valueOf(TheGame.nbcoup*-25)+" pts");
            TheGame.nbbloc21=0;

                // Affichage du nb de blocs restant
            if (TheGame.nb_bloc_de_1()>1) 
                jnbbloc21.setText("Il reste "+String.valueOf(TheGame.nbbloc21)+" blocs: ");
            else jnbbloc21.setText("Il reste "+String.valueOf(TheGame.nbbloc21)+" bloc: ");

                // Affichage des points enlever a cause des blocs restant
            if (TheGame.nbbloc21>0) {
                jnbbloc21pts.setForeground(new java.awt.Color(255, 0, 0));
                jnbbloc21pts.setText(String.valueOf(TheGame.nbbloc21*-55)+" pts"); //** (<--" *-55 ") ???
            }
            else {
                jnbbloc21pts.setForeground(new java.awt.Color(0, 0, 0));
                jnbbloc21pts.setText("+1000 pts"); // si aucun bloc ne reste +1000
            }

                // Calcul du score final et affichage "par défilement"
            if (TheGame.nbbloc21>0) tt=(TheGame.score_bloc_total)-(TheGame.nbcoup*25)+(TheGame.nbbloc21*-55);
            else tt=(TheGame.score_bloc_total)-(TheGame.nbcoup*25)+(1000); // si aucun bloc ne reste

            defil = new defil_score(tt,jscorefin) ; // défilement du score final



                // Création du jDiaScore en fonction du score (couleur rouge <0 gif different...)
            if (tt<0 ) {
                S3.playSon();
                jscorefin.setForeground(new java.awt.Color(255, 0, 0));
                image_score.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/ScoreNegatif.gif")));
                JDiaScore.setBounds(337,145, 350,450);
            }
            else {
                jscorefin.setForeground(new java.awt.Color(0, 0, 0));
            }
            if (tt<5000 && tt>0)  {
                S2.playSon();
                JDiaScore.setBounds(337,178, 350,385);
                image_score.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/ScoreMoyen.gif")));
            }
            else if (tt>4999){
                S1.playSon();
                JDiaScore.setBounds(337,145, 350,450);
                image_score.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/ScoreGros2.gif")));
            }
            sauvegarde() ; //on verifie grace a cette fonction si le score et superieur au meilleur score puis on enregistre si c'est lengthcas
            JDiaScore.setTitle("Score Final");
            defil.start() ; // on lance le defilement du score
            JDiaScore.show();
            
            //*** La jDiaScore est fini d'être créé 
        }
        
        // Fin du jeu en mode Smiley
       else { 

           compte_a_rebour.interrupt() ;
           if (TheGame.nb_bloc_de_1()>0 || TheGame.nbcoup==65){           
               avis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/Perdu.gif")));
               imageAvi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/PerduAnim1.gif")));
           }
           else {
                avis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/Gagner.gif")));
                imageAvi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/GagnerAnim.gif")));
           }
           jDiaScoreSmi.setTitle("Mode Spécial Smiley's");
           jDiaScoreSmi.setBounds(312,150, 400, 195);
           jDiaScoreSmi.show();
       }
     }   // Fin de la de la fonction fin_du_jeu() ;
     
    public void  sauvegarde() { // Création du Fichier sauvegarde "HightScore"

        try {
        // rajout fichier sauvegarde
            java.io.FileInputStream istream = new java.io.FileInputStream(fichier);
            java.io.ObjectInputStream p= new java.io.ObjectInputStream(istream);
            if ( (nbL==6 ) && (nbC==6 ) && (nbMotif==3 ) ) { // = si on est en mode Facil
                    String s = p.readUTF() ;
                    int v = p.readInt() ;
                    if (v < tt ) {  
                        jDiaPseudo.setBounds(397,324,230,120) ;
                        jDiaPseudo.show() ; }
            }
            
            else if ( (nbL==12 ) && (nbC==12 ) && (nbMotif==4 ) ) { // = si on est en mode Moyen
                 String s = p.readUTF() ;
                    int v = p.readInt() ;
                    s = p.readUTF();
                    v = p.readInt() ;  
                    if (v < tt ) { 
                        jDiaPseudo.setBounds(397,324,230,120) ;
                        jDiaPseudo.show() ; }
            }

            else if ( (nbL==18) && (nbC==18) && (nbMotif==6)) { // = Mode Difficil
                String s = p.readUTF() ;
                    int v = p.readInt() ;
                    s = p.readUTF();
                    v = p.readInt() ;
                    s = p.readUTF();
                    v = p.readInt() ;                
                    if (v < tt ) {                    
                        jDiaPseudo.setBounds(397,324,230,120) ; 
                        jDiaPseudo.show() ; }  
            }   
         istream.close();
        }    
        catch (Exception e) { System.out.println("Erreur ds le lancement de la jDiaPseudo (pour saisir le pseudo du HightScore)"); }
     }// Fin Fichier sauvegarde
    
class defil_score extends Thread {
    private int scoree ;
    private JTextField Tf ;
    
    public defil_score(int scoree,JTextField Tf ) { 
        this.scoree = scoree ;this.Tf = Tf ;
    }

    public void run() {
        int i ;
        int reste ;
        try {
            if (scoree > 0 && scoree<10000) {
                for (i = 0 ; i <= scoree/10 ; i++ ) {
                    Tf.setText(String.valueOf(i*10));
                    sleep(10);
                }
            Tf.setText(String.valueOf(scoree)) ;
            }
            else if (scoree>9999) {
                 for (i = 0 ; i <= scoree/100 ; i++ ) {
                    Tf.setText(String.valueOf(i*100));
                    sleep(10);
                 }
                Tf.setText(String.valueOf(scoree)) ;
            }
            else {
                for(i = 0 ; i >=scoree/10 ; i-- )  {  
                    Tf.setText(String.valueOf(i*10));   
                    sleep(10);                      
                }
                Tf.setText(String.valueOf(scoree)) ;
            }
        }
        catch( InterruptedException e) { }
    }
}

class Chrono extends Thread {

    public Chrono() {
        jLscore.setText("Chrono");
        AfficheScore.setVisible(false) ;
        premier_chrono++ ;
    }
    
    public void run() {

        try {
            int i=41;
            while( !interrupted() && (i>0) ){
                i--;
                Affichechrono.setText(String.valueOf(i)+ " sec.") ;
                sleep(1000);
                if (i==0) {
                    infos.setText("Fin du temp réglementaire.");
                    if (TheGame.nb_bloc_de_1()>0 || TheGame.nbcoup==65){           
                        avis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/Perdu.gif")));
                        imageAvi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/PerduAnim1.gif")));
                    }
                    else {
                        avis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/Gagner.gif")));
                        imageAvi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/GagnerAnim.gif")));
                    }
                    jDiaScoreSmi.setTitle("Mode Spécial Smiley's");
                    jDiaScoreSmi.setBounds(312,150, 400, 195);
                    jDiaScoreSmi.show();
                }
            }   
        }
        catch (InterruptedException e) { return ; }          
    }
}
        
    private void initComponents() {//GEN-BEGIN:initComponents
        JDiaChoixMotifs = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jRad1 = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jRad2 = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jRad3 = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        bMotif = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        ChoixQ = new javax.swing.JComboBox();
        jLabel60 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        ChoixT = new javax.swing.JComboBox();
        jLabel40 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        JDiaTailleGrille = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        RadioPetite = new javax.swing.JRadioButton();
        jLabel24 = new javax.swing.JLabel();
        RadioMoyenne = new javax.swing.JRadioButton();
        jLabel25 = new javax.swing.JLabel();
        RadioGrande = new javax.swing.JRadioButton();
        jLabel26 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        TailleL = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        TailleC = new javax.swing.JTextField();
        RadioCache = new javax.swing.JRadioButton();
        jPanel32 = new javax.swing.JPanel();
        Avertiss1 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        Avertiss2 = new javax.swing.JLabel();
        bValiderTaille = new javax.swing.JButton();
        buttonGroup2 = new javax.swing.ButtonGroup();
        JDiaScore = new javax.swing.JDialog();
        jPanel15 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jnbbloc21 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jnbcoups = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        image_score = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jscorebloc = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jnbbloc21pts = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        jnbcoupspts = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jscorefin = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        Rejouer = new javax.swing.JButton();
        Recommencer = new javax.swing.JButton();
        Quitter2 = new javax.swing.JButton();
        jDiaRScore = new javax.swing.JDialog();
        jPanel35 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jDiaRegle = new javax.swing.JDialog();
        jPanel36 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jDiaRemer = new javax.swing.JDialog();
        jPanel38 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jDiaApropos = new javax.swing.JDialog();
        jPanel40 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jDiaScoreSmi = new javax.swing.JDialog();
        jPanel42 = new javax.swing.JPanel();
        imageAvi = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        avis = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        jButNouvelleSmi = new javax.swing.JButton();
        jButQuitterSmi = new javax.swing.JButton();
        jDiaOptions = new javax.swing.JDialog();
        jPanel45 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        jRadio1coup = new javax.swing.JRadioButton();
        jPanel48 = new javax.swing.JPanel();
        jRadio5coups = new javax.swing.JRadioButton();
        jPanel49 = new javax.swing.JPanel();
        jRadio15coups = new javax.swing.JRadioButton();
        jPanel50 = new javax.swing.JPanel();
        jRadio3coups = new javax.swing.JRadioButton();
        jPanel51 = new javax.swing.JPanel();
        jRadio8coups = new javax.swing.JRadioButton();
        jPanel52 = new javax.swing.JPanel();
        jRadioMax = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        buttonGroupOptionsAvances = new javax.swing.ButtonGroup();
        jDiaPseudo = new javax.swing.JDialog();
        jPanel54 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        jPanel53 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        Pseudo = new javax.swing.JTextField();
        jDiaHightScore = new javax.swing.JDialog();
        jPanel55 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jPanel56 = new javax.swing.JPanel();
        jLabel76 = new javax.swing.JLabel();
        jPanel57 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        buttonGroupMusik = new javax.swing.ButtonGroup();
        jPanel23 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel58 = new javax.swing.JPanel();
        Laye = new javax.swing.JLayeredPane();
        jPanelJeuGrille = new javax.swing.JPanel();
        labelImage = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLnbcoups = new javax.swing.JLabel();
        AfficheCoups = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLscore = new javax.swing.JLabel();
        AfficheScore = new javax.swing.JTextField();
        Affichechrono = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        bPrecedent = new javax.swing.JButton();
        jLabel64 = new javax.swing.JLabel();
        Bloc = new javax.swing.JLabel();
        Gugus = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLinfos = new javax.swing.JLabel();
        infos = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuParties = new javax.swing.JMenu();
        jMenuItemNouvelle = new javax.swing.JMenuItem();
        jMenuItemHigh = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItemQuitter = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jRadioButtonFacile = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMoyen = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonDifficile = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonSmileys = new javax.swing.JRadioButtonMenuItem();
        jMenuParamètres = new javax.swing.JMenu();
        jMenuItemMotifs = new javax.swing.JMenuItem();
        jMenuItemTailleGrille = new javax.swing.JMenuItem();
        jMenuItemOptions = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMu1 = new javax.swing.JRadioButtonMenuItem();
        jMu2 = new javax.swing.JRadioButtonMenuItem();
        jMenuRègles = new javax.swing.JMenu();
        jMenuItemRegle = new javax.swing.JMenuItem();
        jMenuItemCalcul = new javax.swing.JMenuItem();
        jMenuInterrogation = new javax.swing.JMenu();
        jMenuItemRemerciements = new javax.swing.JMenuItem();
        jMenuItemApropos = new javax.swing.JMenuItem();

        JDiaChoixMotifs.setTitle("Choix des motifs");
        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));
        jLabel14.setFont(new java.awt.Font("Dialog", 2, 12));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("        ChinToyToy");
        jPanel3.add(jLabel14);

        jLabel19.setText(" ");
        jPanel3.add(jLabel19);

        jLabel16.setBackground(new java.awt.Color(0, 0, 255));
        jLabel16.setForeground(new java.awt.Color(255, 51, 153));
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/symm301.gif")));
        jLabel16.setText("                                      ");
        jLabel16.setMaximumSize(new java.awt.Dimension(120, 32));
        jLabel16.setMinimumSize(new java.awt.Dimension(120, 32));
        jLabel16.setPreferredSize(new java.awt.Dimension(120, 32));
        jLabel16.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(jLabel16);

        jLabel17.setBackground(new java.awt.Color(255, 255, 51));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/symm302.gif")));
        jLabel17.setText("                                      ");
        jLabel17.setMaximumSize(new java.awt.Dimension(120, 32));
        jLabel17.setMinimumSize(new java.awt.Dimension(120, 32));
        jLabel17.setPreferredSize(new java.awt.Dimension(120, 32));
        jLabel17.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(jLabel17);

        jLabel18.setBackground(new java.awt.Color(51, 255, 0));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/symm303.gif")));
        jLabel18.setText("                                      ");
        jLabel18.setMaximumSize(new java.awt.Dimension(120, 32));
        jLabel18.setMinimumSize(new java.awt.Dimension(120, 32));
        jLabel18.setPreferredSize(new java.awt.Dimension(120, 32));
        jLabel18.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(jLabel18);

        jLabel35.setBackground(new java.awt.Color(0, 0, 255));
        jLabel35.setForeground(new java.awt.Color(255, 51, 153));
        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/symm304.gif")));
        jLabel35.setText("                                      ");
        jLabel35.setMaximumSize(new java.awt.Dimension(120, 32));
        jLabel35.setMinimumSize(new java.awt.Dimension(120, 32));
        jLabel35.setPreferredSize(new java.awt.Dimension(120, 32));
        jLabel35.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(jLabel35);

        jLabel36.setBackground(new java.awt.Color(0, 0, 255));
        jLabel36.setForeground(new java.awt.Color(255, 51, 153));
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/symm305.gif")));
        jLabel36.setText("                                      ");
        jLabel36.setMaximumSize(new java.awt.Dimension(120, 32));
        jLabel36.setMinimumSize(new java.awt.Dimension(120, 32));
        jLabel36.setPreferredSize(new java.awt.Dimension(120, 32));
        jLabel36.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(jLabel36);

        jLabel37.setBackground(new java.awt.Color(0, 0, 255));
        jLabel37.setForeground(new java.awt.Color(255, 51, 153));
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/symm306.gif")));
        jLabel37.setText("                                      ");
        jLabel37.setMaximumSize(new java.awt.Dimension(120, 32));
        jLabel37.setMinimumSize(new java.awt.Dimension(120, 32));
        jLabel37.setPreferredSize(new java.awt.Dimension(120, 32));
        jLabel37.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(jLabel37);

        jLabel22.setText(" ");
        jPanel3.add(jLabel22);

        jRad1.setBackground(new java.awt.Color(204, 204, 255));
        buttonGroup1.add(jRad1);
        jRad1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jRad1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jRad1.setMaximumSize(new java.awt.Dimension(150, 24));
        jRad1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRad1ActionPerformed(evt);
            }
        });

        jPanel3.add(jRad1);

        jPanel4.add(jPanel3);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(122, 184));
        jPanel2.setMinimumSize(new java.awt.Dimension(122, 184));
        jPanel2.setPreferredSize(new java.awt.Dimension(122, 184));
        jLabel11.setFont(new java.awt.Font("Dialog", 2, 12));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("           BiZaRres");
        jPanel2.add(jLabel11);

        jLabel12.setText(" ");
        jPanel2.add(jLabel12);

        jLabel6.setBackground(new java.awt.Color(255, 51, 153));
        jLabel6.setForeground(new java.awt.Color(255, 51, 153));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/biz301.gif")));
        jLabel6.setText("                                      ");
        jLabel6.setMaximumSize(new java.awt.Dimension(120, 32));
        jLabel6.setMinimumSize(new java.awt.Dimension(120, 32));
        jLabel6.setPreferredSize(new java.awt.Dimension(120, 32));
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel6);

        jLabel7.setBackground(new java.awt.Color(153, 102, 255));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/biz302.gif")));
        jLabel7.setText("                                      ");
        jLabel7.setMaximumSize(new java.awt.Dimension(120, 32));
        jLabel7.setMinimumSize(new java.awt.Dimension(120, 32));
        jLabel7.setPreferredSize(new java.awt.Dimension(120, 32));
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel7);

        jLabel8.setBackground(new java.awt.Color(0, 0, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/biz303.gif")));
        jLabel8.setText("                                      ");
        jLabel8.setMaximumSize(new java.awt.Dimension(120, 32));
        jLabel8.setMinimumSize(new java.awt.Dimension(120, 32));
        jLabel8.setPreferredSize(new java.awt.Dimension(120, 32));
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel8);

        jLabel30.setBackground(new java.awt.Color(51, 255, 51));
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/biz304.gif")));
        jLabel30.setText("                                      ");
        jLabel30.setMaximumSize(new java.awt.Dimension(120, 32));
        jLabel30.setMinimumSize(new java.awt.Dimension(120, 32));
        jLabel30.setPreferredSize(new java.awt.Dimension(120, 32));
        jLabel30.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel30);

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/biz305.gif")));
        jLabel9.setText("                                      ");
        jLabel9.setMaximumSize(new java.awt.Dimension(120, 32));
        jLabel9.setMinimumSize(new java.awt.Dimension(120, 32));
        jLabel9.setPreferredSize(new java.awt.Dimension(120, 32));
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel9);

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/biz306.gif")));
        jLabel15.setText("                                      ");
        jLabel15.setMaximumSize(new java.awt.Dimension(120, 32));
        jLabel15.setMinimumSize(new java.awt.Dimension(120, 32));
        jLabel15.setPreferredSize(new java.awt.Dimension(120, 32));
        jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel15);

        jLabel21.setText(" ");
        jPanel2.add(jLabel21);

        jRad2.setBackground(new java.awt.Color(204, 204, 255));
        buttonGroup1.add(jRad2);
        jRad2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jRad2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jRad2.setMaximumSize(new java.awt.Dimension(116, 24));
        jRad2.setMinimumSize(new java.awt.Dimension(116, 24));
        jRad2.setPreferredSize(new java.awt.Dimension(116, 24));
        jPanel2.add(jRad2);

        jPanel4.add(jPanel2);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(122, 350));
        jPanel1.setMinimumSize(new java.awt.Dimension(122, 350));
        jPanel1.setPreferredSize(new java.awt.Dimension(122, 350));
        jLabel10.setFont(new java.awt.Font("Dialog", 2, 12));
        jLabel10.setText("          Ne Noeil ");
        jPanel1.add(jLabel10);

        jLabel13.setText(" ");
        jPanel1.add(jLabel13);

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/oei301.gif")));
        jLabel29.setText("                                      ");
        jLabel29.setMaximumSize(new java.awt.Dimension(120, 32));
        jLabel29.setMinimumSize(new java.awt.Dimension(120, 32));
        jLabel29.setPreferredSize(new java.awt.Dimension(120, 32));
        jLabel29.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel29);

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/oei302.gif")));
        jLabel38.setText("                                      ");
        jLabel38.setMaximumSize(new java.awt.Dimension(120, 32));
        jLabel38.setMinimumSize(new java.awt.Dimension(120, 32));
        jLabel38.setPreferredSize(new java.awt.Dimension(120, 32));
        jLabel38.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel38);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/oei303.gif")));
        jLabel1.setText("                                      ");
        jLabel1.setMaximumSize(new java.awt.Dimension(120, 32));
        jLabel1.setMinimumSize(new java.awt.Dimension(120, 32));
        jLabel1.setPreferredSize(new java.awt.Dimension(120, 32));
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel1);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/oei304.gif")));
        jLabel2.setText("                                      ");
        jLabel2.setMaximumSize(new java.awt.Dimension(120, 32));
        jLabel2.setMinimumSize(new java.awt.Dimension(120, 32));
        jLabel2.setPreferredSize(new java.awt.Dimension(120, 32));
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel2);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/oei305.gif")));
        jLabel3.setText("                                      ");
        jLabel3.setMaximumSize(new java.awt.Dimension(120, 32));
        jLabel3.setMinimumSize(new java.awt.Dimension(120, 32));
        jLabel3.setPreferredSize(new java.awt.Dimension(120, 32));
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel3);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/oei306.gif")));
        jLabel4.setText("                                      ");
        jLabel4.setMaximumSize(new java.awt.Dimension(120, 32));
        jLabel4.setMinimumSize(new java.awt.Dimension(120, 32));
        jLabel4.setPreferredSize(new java.awt.Dimension(120, 32));
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel4);

        jLabel20.setText(" ");
        jPanel1.add(jLabel20);

        jRad3.setBackground(new java.awt.Color(204, 204, 255));
        jRad3.setSelected(true);
        buttonGroup1.add(jRad3);
        jRad3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jRad3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jRad3.setMaximumSize(new java.awt.Dimension(116, 24));
        jRad3.setMinimumSize(new java.awt.Dimension(116, 24));
        jRad3.setPreferredSize(new java.awt.Dimension(116, 24));
        jPanel1.add(jRad3);

        jPanel4.add(jPanel1);

        JDiaChoixMotifs.getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(204, 204, 255));
        jPanel5.setMaximumSize(new java.awt.Dimension(219, 60));
        jPanel5.setMinimumSize(new java.awt.Dimension(219, 60));
        jPanel5.setPreferredSize(new java.awt.Dimension(219, 60));
        jLabel5.setBackground(new java.awt.Color(204, 204, 255));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 13));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Choisissez vos motifs et leurs quantit\u00e9s !");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel5.setOpaque(true);
        jPanel5.add(jLabel5);

        JDiaChoixMotifs.getContentPane().add(jPanel5, java.awt.BorderLayout.NORTH);

        bMotif.setBackground(new java.awt.Color(204, 255, 204));
        bMotif.setText("Valider");
        bMotif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMotifActionPerformed(evt);
            }
        });

        JDiaChoixMotifs.getContentPane().add(bMotif, java.awt.BorderLayout.SOUTH);

        jPanel10.setLayout(new java.awt.GridLayout(10, 1));

        jPanel10.setBackground(new java.awt.Color(204, 204, 255));
        jPanel10.setAlignmentX(0.0F);
        jPanel10.setAlignmentY(0.0F);
        jPanel10.setMaximumSize(new java.awt.Dimension(100, 290));
        jPanel10.setMinimumSize(new java.awt.Dimension(100, 290));
        jPanel10.setPreferredSize(new java.awt.Dimension(100, 290));
        jLabel59.setFont(new java.awt.Font("Dialog", 2, 12));
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel59.setMaximumSize(new java.awt.Dimension(50, 20));
        jLabel59.setMinimumSize(new java.awt.Dimension(50, 20));
        jLabel59.setPreferredSize(new java.awt.Dimension(50, 20));
        jLabel59.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel10.add(jLabel59);

        jLabel33.setFont(new java.awt.Font("Dialog", 2, 12));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel33.setMaximumSize(new java.awt.Dimension(50, 20));
        jLabel33.setMinimumSize(new java.awt.Dimension(50, 20));
        jLabel33.setPreferredSize(new java.awt.Dimension(50, 20));
        jLabel33.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel10.add(jLabel33);

        jLabel34.setFont(new java.awt.Font("Dialog", 2, 12));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Quantit\u00e9s :");
        jLabel34.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel34.setMaximumSize(new java.awt.Dimension(75, 30));
        jLabel34.setMinimumSize(new java.awt.Dimension(75, 30));
        jLabel34.setPreferredSize(new java.awt.Dimension(75, 30));
        jLabel34.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel10.add(jLabel34);

        ChoixQ.setBackground(new java.awt.Color(255, 255, 204));
        ChoixQ.setFont(new java.awt.Font("Comic Sans MS", 0, 12));
        ChoixQ.setMaximumRowCount(4);
        ChoixQ.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "3 Motifs", "4 Motifs", "5 Motifs", "6 Motifs" }));
        ChoixQ.setMaximumSize(new java.awt.Dimension(66, 24));
        jPanel10.add(ChoixQ);

        jLabel60.setFont(new java.awt.Font("Dialog", 2, 12));
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel60.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel60.setMaximumSize(new java.awt.Dimension(50, 20));
        jLabel60.setMinimumSize(new java.awt.Dimension(50, 20));
        jLabel60.setPreferredSize(new java.awt.Dimension(50, 20));
        jLabel60.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel10.add(jLabel60);

        jLabel39.setFont(new java.awt.Font("Dialog", 2, 12));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("Taille:");
        jLabel39.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel39.setMaximumSize(new java.awt.Dimension(75, 30));
        jLabel39.setMinimumSize(new java.awt.Dimension(75, 30));
        jLabel39.setPreferredSize(new java.awt.Dimension(75, 30));
        jLabel39.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel10.add(jLabel39);

        ChoixT.setBackground(new java.awt.Color(255, 255, 204));
        ChoixT.setFont(new java.awt.Font("Comic Sans MS", 1, 10));
        ChoixT.setMaximumRowCount(4);
        ChoixT.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "50 x 50 pixels", "40 x 40 pixels", "30 x 30 pixels" }));
        ChoixT.setMaximumSize(new java.awt.Dimension(66, 24));
        jPanel10.add(ChoixT);

        jLabel40.setFont(new java.awt.Font("Dialog", 2, 12));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel40.setMaximumSize(new java.awt.Dimension(50, 20));
        jLabel40.setMinimumSize(new java.awt.Dimension(50, 20));
        jLabel40.setPreferredSize(new java.awt.Dimension(50, 20));
        jLabel40.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel10.add(jLabel40);

        jLabel61.setFont(new java.awt.Font("Dialog", 2, 12));
        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel61.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel61.setMaximumSize(new java.awt.Dimension(50, 20));
        jLabel61.setMinimumSize(new java.awt.Dimension(50, 20));
        jLabel61.setPreferredSize(new java.awt.Dimension(50, 20));
        jLabel61.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel10.add(jLabel61);

        JDiaChoixMotifs.getContentPane().add(jPanel10, java.awt.BorderLayout.EAST);

        jPanel7.setLayout(new java.awt.GridLayout(5, 0));

        jPanel7.setBackground(new java.awt.Color(204, 204, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("  Petite (6x6) : ");
        jPanel7.add(jLabel23);

        RadioPetite.setBackground(new java.awt.Color(204, 204, 255));
        RadioPetite.setSelected(true);
        buttonGroup2.add(RadioPetite);
        RadioPetite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioPetiteActionPerformed(evt);
            }
        });

        jPanel7.add(RadioPetite);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("  Moyenne (12x12): ");
        jPanel7.add(jLabel24);

        RadioMoyenne.setBackground(new java.awt.Color(204, 204, 255));
        buttonGroup2.add(RadioMoyenne);
        RadioMoyenne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioMoyenneActionPerformed(evt);
            }
        });

        jPanel7.add(RadioMoyenne);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("  Grande (18x18) : ");
        jPanel7.add(jLabel25);

        RadioGrande.setBackground(new java.awt.Color(204, 204, 255));
        buttonGroup2.add(RadioGrande);
        RadioGrande.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioGrandeActionPerformed(evt);
            }
        });

        jPanel7.add(RadioGrande);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("  Personnalis\u00e9e : ");
        jPanel7.add(jLabel26);

        jPanel9.setLayout(new javax.swing.BoxLayout(jPanel9, javax.swing.BoxLayout.X_AXIS));

        jPanel9.setBackground(new java.awt.Color(204, 204, 255));
        TailleL.setMaximumSize(new java.awt.Dimension(40, 30));
        TailleL.setMinimumSize(new java.awt.Dimension(40, 30));
        TailleL.setPreferredSize(new java.awt.Dimension(63, 30));
        TailleL.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TailleLFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                TailleLFocusLost(evt);
            }
        });

        jPanel9.add(TailleL);

        jLabel27.setText("   X   ");
        jPanel9.add(jLabel27);

        TailleC.setMaximumSize(new java.awt.Dimension(40, 30));
        TailleC.setMinimumSize(new java.awt.Dimension(40, 30));
        TailleC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TailleCActionPerformed(evt);
            }
        });
        TailleC.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                TailleCFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                TailleCFocusLost(evt);
            }
        });

        jPanel9.add(TailleC);

        RadioCache.setBackground(new java.awt.Color(204, 204, 255));
        buttonGroup2.add(RadioCache);
        RadioCache.setMaximumSize(new java.awt.Dimension(1, 1));
        RadioCache.setOpaque(false);
        RadioCache.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioCacheActionPerformed(evt);
            }
        });

        jPanel9.add(RadioCache);

        jPanel7.add(jPanel9);

        jPanel32.setLayout(new java.awt.GridLayout(3, 0));

        jPanel32.setBackground(new java.awt.Color(204, 204, 255));
        Avertiss1.setBackground(new java.awt.Color(204, 204, 255));
        Avertiss1.setFont(new java.awt.Font("Dialog", 2, 12));
        Avertiss1.setForeground(new java.awt.Color(255, 0, 51));
        Avertiss1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Avertiss1.setText("Votre grille doit \u00eatre ");
        jPanel32.add(Avertiss1);

        jPanel7.add(jPanel32);

        jPanel33.setLayout(new java.awt.GridLayout(3, 0));

        jPanel33.setBackground(new java.awt.Color(204, 204, 255));
        Avertiss2.setBackground(new java.awt.Color(204, 204, 255));
        Avertiss2.setFont(new java.awt.Font("Dialog", 2, 12));
        Avertiss2.setForeground(new java.awt.Color(255, 0, 51));
        Avertiss2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Avertiss2.setText("entre 6 x 6 et 18 x 28 !");
        jPanel33.add(Avertiss2);

        jPanel7.add(jPanel33);

        JDiaTailleGrille.getContentPane().add(jPanel7, java.awt.BorderLayout.CENTER);

        bValiderTaille.setBackground(new java.awt.Color(204, 255, 204));
        bValiderTaille.setText("Valider");
        bValiderTaille.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bValiderTailleActionPerformed(evt);
            }
        });

        JDiaTailleGrille.getContentPane().add(bValiderTaille, java.awt.BorderLayout.SOUTH);

        JDiaScore.setModal(true);
        jPanel15.setBackground(new java.awt.Color(204, 204, 255));
        jPanel15.setMinimumSize(new java.awt.Dimension(50, 70));
        jPanel15.setPreferredSize(new java.awt.Dimension(50, 70));
        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/ScoreAnim2.gif")));
        jPanel15.add(jLabel46);

        JDiaScore.getContentPane().add(jPanel15, java.awt.BorderLayout.NORTH);

        jPanel14.setLayout(new java.awt.BorderLayout());

        jPanel14.setMaximumSize(new java.awt.Dimension(250, 10));
        jPanel14.setMinimumSize(new java.awt.Dimension(250, 10));
        jPanel14.setPreferredSize(new java.awt.Dimension(250, 10));
        jPanel18.setLayout(new java.awt.GridLayout(5, 1));

        jPanel18.setBackground(new java.awt.Color(204, 204, 255));
        jPanel18.setMinimumSize(new java.awt.Dimension(212, 0));
        jPanel18.setPreferredSize(new java.awt.Dimension(272, 0));
        jPanel19.setLayout(new java.awt.GridLayout(1, 0));

        jPanel19.setBackground(new java.awt.Color(204, 204, 255));
        jLabel47.setFont(new java.awt.Font("Comic Sans MS", 2, 13));
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel47.setText("Score blocs: ");
        jLabel47.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel19.add(jLabel47);

        jPanel18.add(jPanel19);

        jPanel20.setLayout(new java.awt.GridLayout(1, 0));

        jPanel20.setBackground(new java.awt.Color(204, 204, 255));
        jnbbloc21.setFont(new java.awt.Font("Comic Sans MS", 2, 13));
        jnbbloc21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jnbbloc21.setText(" XX ");
        jnbbloc21.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel20.add(jnbbloc21);

        jPanel18.add(jPanel20);

        jPanel21.setLayout(new java.awt.GridLayout(1, 0));

        jPanel21.setBackground(new java.awt.Color(204, 204, 255));
        jnbcoups.setFont(new java.awt.Font("Comic Sans MS", 2, 13));
        jnbcoups.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jnbcoups.setText(" XX ");
        jPanel21.add(jnbcoups);

        jPanel18.add(jPanel21);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jPanel18.add(jLabel41);

        jPanel30.setLayout(new java.awt.GridLayout(1, 0));

        jPanel30.setBackground(new java.awt.Color(204, 204, 255));
        jLabel54.setFont(new java.awt.Font("Comic Sans MS", 3, 14));
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel54.setText("SCORE FINAL :  ");
        jPanel30.add(jLabel54);

        jPanel18.add(jPanel30);

        jPanel14.add(jPanel18, java.awt.BorderLayout.CENTER);

        jPanel22.setBackground(new java.awt.Color(204, 204, 255));
        image_score.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/ScoreNegatif.gif")));
        jPanel22.add(image_score);

        jPanel14.add(jPanel22, java.awt.BorderLayout.SOUTH);

        jPanel8.setLayout(new java.awt.GridLayout(5, 1));

        jPanel8.setBackground(new java.awt.Color(204, 204, 255));
        jPanel8.setMaximumSize(new java.awt.Dimension(100, 200));
        jPanel8.setMinimumSize(new java.awt.Dimension(100, 200));
        jPanel8.setPreferredSize(new java.awt.Dimension(130, 200));
        jPanel11.setLayout(new javax.swing.BoxLayout(jPanel11, javax.swing.BoxLayout.X_AXIS));

        jPanel11.setBackground(new java.awt.Color(204, 204, 255));
        jscorebloc.setEditable(false);
        jscorebloc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jscorebloc.setMaximumSize(new java.awt.Dimension(70, 20));
        jscorebloc.setMinimumSize(new java.awt.Dimension(70, 20));
        jscorebloc.setPreferredSize(new java.awt.Dimension(70, 20));
        jPanel11.add(jscorebloc);

        jPanel8.add(jPanel11);

        jPanel12.setLayout(new javax.swing.BoxLayout(jPanel12, javax.swing.BoxLayout.X_AXIS));

        jPanel12.setBackground(new java.awt.Color(204, 204, 255));
        jnbbloc21pts.setEditable(false);
        jnbbloc21pts.setForeground(new java.awt.Color(255, 0, 51));
        jnbbloc21pts.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jnbbloc21pts.setMaximumSize(new java.awt.Dimension(70, 20));
        jnbbloc21pts.setMinimumSize(new java.awt.Dimension(70, 20));
        jnbbloc21pts.setPreferredSize(new java.awt.Dimension(70, 20));
        jPanel12.add(jnbbloc21pts);

        jPanel8.add(jPanel12);

        jPanel17.setLayout(new javax.swing.BoxLayout(jPanel17, javax.swing.BoxLayout.X_AXIS));

        jPanel17.setBackground(new java.awt.Color(204, 204, 255));
        jnbcoupspts.setEditable(false);
        jnbcoupspts.setForeground(new java.awt.Color(255, 0, 51));
        jnbcoupspts.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jnbcoupspts.setMaximumSize(new java.awt.Dimension(70, 20));
        jnbcoupspts.setMinimumSize(new java.awt.Dimension(70, 20));
        jnbcoupspts.setPreferredSize(new java.awt.Dimension(70, 20));
        jPanel17.add(jnbcoupspts);

        jPanel8.add(jPanel17);

        jPanel8.add(jLabel42);

        jPanel31.setLayout(new javax.swing.BoxLayout(jPanel31, javax.swing.BoxLayout.X_AXIS));

        jPanel31.setBackground(new java.awt.Color(204, 204, 255));
        jPanel31.setMaximumSize(new java.awt.Dimension(500, 30));
        jPanel31.setMinimumSize(new java.awt.Dimension(500, 30));
        jPanel31.setPreferredSize(new java.awt.Dimension(500, 30));
        jscorefin.setBackground(new java.awt.Color(204, 255, 255));
        jscorefin.setEditable(false);
        jscorefin.setFont(new java.awt.Font("Comic Sans MS", 0, 18));
        jscorefin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jscorefin.setText("       ");
        jscorefin.setMaximumSize(new java.awt.Dimension(100, 30));
        jscorefin.setMinimumSize(new java.awt.Dimension(50, 30));
        jscorefin.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel31.add(jscorefin);

        jPanel8.add(jPanel31);

        jPanel14.add(jPanel8, java.awt.BorderLayout.EAST);

        JDiaScore.getContentPane().add(jPanel14, java.awt.BorderLayout.CENTER);

        jPanel16.setBackground(new java.awt.Color(204, 204, 255));
        Rejouer.setBackground(new java.awt.Color(204, 255, 204));
        Rejouer.setText("Nouvelle partie");
        Rejouer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RejouerActionPerformed(evt);
            }
        });

        jPanel16.add(Rejouer);

        Recommencer.setBackground(new java.awt.Color(204, 255, 204));
        Recommencer.setText("Recommencer");
        Recommencer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RecommencerActionPerformed(evt);
            }
        });

        jPanel16.add(Recommencer);

        Quitter2.setBackground(new java.awt.Color(204, 255, 204));
        Quitter2.setText("Quitter ");
        Quitter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Quitter2ActionPerformed(evt);
            }
        });

        jPanel16.add(Quitter2);

        JDiaScore.getContentPane().add(jPanel16, java.awt.BorderLayout.SOUTH);

        jDiaRScore.setTitle("Calcul du score");
        jPanel35.setBackground(new java.awt.Color(204, 255, 255));
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/CalculduscoreAnim2.gif")));
        jPanel35.add(jLabel31);

        jDiaRScore.getContentPane().add(jPanel35, java.awt.BorderLayout.NORTH);

        jPanel34.setBackground(new java.awt.Color(204, 255, 255));
        jLabel43.setText("<html></font><b>Voici les points attribu\u00e9s selon\n        la taille des blocs</b><br><table border=2 bordercolor=blue bgcolor=#CCFFCC align=center><tr align=center bgcolor=#CCCCFF><td>Blocs<td>Points<td>Bonus<td>Nom<tr align=center><td>2<td>50<td>0 %<td>P'tit Bloc<tr align=center><td>\n        3<td>82<td>10 %<td>P'tit Bloc\n        <tr align=center><td>4<td>120<td>20 %<td>P'tit Bloc\n        <tr align=center><td>5<td>162<td>30 %<td>P'tit Bloc\n        <tr align=center><td>6<td>185<td>40 %<td>P'tit Bloc\n        <tr align=center><td>7<td>262<td>50 %<td>P'tit Bloc\n        <tr align=center><td><font color=blue>8<td><font color=blue>450<td><font color=blue>60 % + 50pts<td><font color=blue>Super Bloc\n        <tr align=center><td>...<td>...<td>...<td></font>\n        <tr align=center><td><font color=green>14<td><font color=green>600<td><font color=green>120 % + 150pts<td><font color=green><u>GiGa Bloc\n        <tr align=center><td>...<td>...<td>...<td></font>\n        <font color=white><tr align=center><td><font color=red>20<td><font color=red>600<td><font color=red>180 % + 350pts<td><font color=red><u><i>ULTRA BLOC\n        <tr align=center><td>...<td>...<td>...<td>...</font>\n        </table><br>Calcul final du score : <ul type=disc><li>Vous perdez 55 pts par bloc restant \u00e0 la fin de la partie\n        <li>Mais vous gagnez 1000 pts s' il ne reste pas de blocs\n        <li>Et vous perdez 25 pts par coups effectu\u00e9s\n        <li>\n        </html>");
        jPanel34.add(jLabel43);

        jDiaRScore.getContentPane().add(jPanel34, java.awt.BorderLayout.CENTER);

        jPanel36.setBackground(new java.awt.Color(204, 204, 255));
        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/ReglesAnim.gif")));
        jPanel36.add(jLabel53);

        jDiaRegle.getContentPane().add(jPanel36, java.awt.BorderLayout.NORTH);

        jPanel37.setBackground(new java.awt.Color(204, 204, 255));
        jLabel55.setText("<html><table width=450><tr><td>\n        <ul type=disc><li>Les blocs sont supprimables si l'on a au <i><u>minimum 2 blocs identiques</u></i> (verticalement et/ou horizontalement) l'un \u00e0 cot\u00e9s de l'autre.<br>\n        <li>Les blocs identiques <i>\n        <u>en diagonales</i></u> ne sont pas supprimables.<br><li>Lors de la destruction de blocs ceux qui se trouvaient au dessus \"tombent\"\n        <br><li>Si une colonne enti\u00e8re vient \u00e0 \u00eatre d\u00e9truite les colonnes \u00e0 sa droite se reserreront \u00e0 gauche\n        </ul><b><u><font color=blue>Mode Facile:</b></u><ul type=circle><li>La grille fait 6 x 6<li>Il y a 3 sortes de motifs\n        <li>Les motifs utilis\u00e9s sont les <i>Ne Noeils<i></font></ul>\n        <b><u><font color=green>Mode Moyen:</b></u><ul type=circle><li>La grille fait 12 x 12<li>Il y a 4 sortes de motifs\n        <li>Les motifs utilis\u00e9s sont les <i>BiZaRrEs</i></font></ul>\n        <b><u><font color=red>Mode Difficile:</b></u><ul type=circle><li>La grille fait 18 x 18<li>Il y a 6 sortes de motifs\n        <li>Les motifs utilis\u00e9s sont les <i>ChinToyToy</i></font></ul>\n        <b><u><font color=yellow>Mode Smiley:</b></u><ul type=circle><li>La grille fait 12 x 16<li>Il y a 4 sortes de motifs\n        <li>Les motifs utilis\u00e9s sont les <i>Smiley's</i><li>Vous \u00eates limit\u00e9s a 65 coups<li>Vous avez 40 secondes\n        <li>Il ne faut pas qu'il reste de blocs sinon vous avez perdu...<li>Il n'y a pas de score, le but est de gagner ! ;)\n        </font></td></tr></table></html>");
        jPanel37.add(jLabel55);

        jDiaRegle.getContentPane().add(jPanel37, java.awt.BorderLayout.CENTER);

        jPanel38.setBackground(new java.awt.Color(255, 204, 204));
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/RemerciementsAnim.gif")));
        jPanel38.add(jLabel28);

        jDiaRemer.getContentPane().add(jPanel38, java.awt.BorderLayout.NORTH);

        jPanel39.setLayout(new java.awt.BorderLayout());

        jPanel39.setBackground(new java.awt.Color(255, 204, 204));
        jLabel51.setText("<html>\n<table width=450><tr><td>\n&nbsp;&nbsp;&nbsp;&nbsp;Nous tenons \u00e0 remercier les utilisateurs des forums de developpez.com pour leurs pr\u00e9cieux conseils.<br><br>\n&nbsp;&nbsp;&nbsp;&nbsp;Nous voulions \u00e9galement remercier les diff\u00e9rents logiciels qui nous ont permis de r\u00e9aliser ce projet :\n<ul type=circle>\n<li>NetBeans 3.5.1\n<li>Jasc Animation Shop Pro 3\n<li>Adobe Photoshop 7\n<li>Microsoft Picture it\n<li>Et notre indispensable Bloc-notes...\n</ul> \n\n&nbsp;&nbsp;&nbsp;&nbsp;Et enfin nous remercions Grand-M\u00e8re et son caf\u00e9, qui nous a permis de finir le projet dans les temps :)\n\n\n\n</td></tr></table></html>");
        jPanel39.add(jLabel51, java.awt.BorderLayout.CENTER);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/Javasmi2.gif")));
        jPanel39.add(jLabel52, java.awt.BorderLayout.SOUTH);

        jDiaRemer.getContentPane().add(jPanel39, java.awt.BorderLayout.CENTER);

        jPanel40.setBackground(new java.awt.Color(255, 204, 204));
        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/AproposdeAnim.gif")));
        jPanel40.add(jLabel56);

        jDiaApropos.getContentPane().add(jPanel40, java.awt.BorderLayout.NORTH);

        jPanel41.setBackground(new java.awt.Color(255, 204, 204));
        jLabel57.setText("<html><table width=450><tr><td>\n&nbsp;&nbsp;&nbsp;&nbsp;<u>The Game 2k4</u> \u00e0 \u00e9t\u00e9 r\u00e9alis\u00e9 par <u>Anthony et Benoit Mar\u00e9chal</u> \u00e0 l'issue d'un projet informatique et dans le cadre de leurs \u00e9tudes en Fac de Sciences.<br><br>\n\n&nbsp;&nbsp;&nbsp;&nbsp;<b><font color=red>Historique de The Game 2k4:</font></b><br>\n\n<ul>\n<li><i><font color=blue>The Game Version Beta 0.8: (07/05/04)</font></i><br>\nLe jeu comprend 3 modes: Facile , Moyen et Difficile. Il n'y a pas de choix de motifs pour l'instant. Le calcul des scores ne prend pas encore en compte le nombre de coups ni le nombre de blocs restants. Et il n'est pas possible de revenir au coup pr\u00e9c\u00e9dent.\n<br>\n<li><i><font color=blue>The Game 2k4 Version Beta 0.9: (15/05/04)</font></i><br>\nThe Game change de nom et s'appelle dor\u00e9navant The Game 2k4 car r\u00e9alis\u00e9 en 2004... 3 choix de motifs sont disponibles, des gifs anim\u00e9s \"Super Bloc\" , \"GiGa Bloc\" et \"ULTRA Bloc\" se lancent selon la taille des blocs supprim\u00e9s. Le calcul du score est termin\u00e9. Il est possible de revenir sur un coup effectu\u00e9.\n<br>\n<li><i><font color=blue>The Game 2k4 Version 1: (23/05/04)</font></i><br><br>\nThe Game 2k4 est achev\u00e9. Un mode smiley avec pour but de ne plus avoir de bloc \u00e0 la fin du jeu a \u00e9t\u00e9 ajout\u00e9. Le nombre de coups pr\u00e9cedents peut \u00eatre illimit\u00e9 ce qui permet de revenir jusqu'au d\u00e9but de la partie. Il est possible de recommencer la m\u00eame partie une fois celle-ci finie. Les motifs existent en 3 tailles diff\u00e9rentes afin d'augmenter la taille maximum de notre grille. Tout les bugs mineurs connus ont \u00e9t\u00e9 supprim\u00e9s. \n\nLes concepteurs vous souhaitent donc un <i><b>Bon Jeu !</b></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;Copyright (c) 2004\n\n</td></tr></table></html>\n\n\n\n\n\n");
        jPanel41.add(jLabel57);

        jDiaApropos.getContentPane().add(jPanel41, java.awt.BorderLayout.CENTER);

        jDiaScoreSmi.setModal(true);
        jPanel42.setBackground(new java.awt.Color(253, 255, 204));
        imageAvi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/PerduAnim1.gif")));
        jPanel42.add(imageAvi);

        jDiaScoreSmi.getContentPane().add(jPanel42, java.awt.BorderLayout.CENTER);

        jPanel43.setBackground(new java.awt.Color(253, 255, 204));
        avis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/Perdu.gif")));
        jPanel43.add(avis);

        jDiaScoreSmi.getContentPane().add(jPanel43, java.awt.BorderLayout.NORTH);

        jPanel44.setBackground(new java.awt.Color(253, 255, 204));
        jButNouvelleSmi.setBackground(new java.awt.Color(204, 255, 204));
        jButNouvelleSmi.setText("Nouvelle");
        jButNouvelleSmi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButNouvelleSmiActionPerformed(evt);
            }
        });

        jPanel44.add(jButNouvelleSmi);

        jButQuitterSmi.setBackground(new java.awt.Color(204, 255, 204));
        jButQuitterSmi.setText("Quitter");
        jButQuitterSmi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButQuitterSmiActionPerformed(evt);
            }
        });

        jPanel44.add(jButQuitterSmi);

        jDiaScoreSmi.getContentPane().add(jPanel44, java.awt.BorderLayout.SOUTH);

        jPanel45.setBackground(new java.awt.Color(255, 204, 204));
        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/bibi.gif")));
        jPanel45.add(jLabel58);

        jLabel66.setText("<html><table width=335><tr><td>\n<div align=center>Nombre de coups pr\u00e9c\u00e9dents\nque vous souhaitez tol\u00e9rer :\n\n\n</div>\n</td></tr></table></html>");
        jPanel45.add(jLabel66);

        jDiaOptions.getContentPane().add(jPanel45, java.awt.BorderLayout.NORTH);

        jPanel46.setLayout(new java.awt.GridLayout(2, 3));

        jPanel46.setBackground(new java.awt.Color(255, 204, 204));
        jPanel47.setBackground(new java.awt.Color(255, 204, 204));
        jRadio1coup.setBackground(new java.awt.Color(255, 204, 204));
        jRadio1coup.setText("1 coup");
        buttonGroupOptionsAvances.add(jRadio1coup);
        jRadio1coup.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel47.add(jRadio1coup);

        jPanel46.add(jPanel47);

        jPanel48.setBackground(new java.awt.Color(255, 204, 204));
        jRadio5coups.setBackground(new java.awt.Color(255, 204, 204));
        jRadio5coups.setText("5 coups");
        buttonGroupOptionsAvances.add(jRadio5coups);
        jRadio5coups.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel48.add(jRadio5coups);

        jPanel46.add(jPanel48);

        jPanel49.setBackground(new java.awt.Color(255, 204, 204));
        jRadio15coups.setBackground(new java.awt.Color(255, 204, 204));
        jRadio15coups.setText("15 coups");
        buttonGroupOptionsAvances.add(jRadio15coups);
        jRadio15coups.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel49.add(jRadio15coups);

        jPanel46.add(jPanel49);

        jPanel50.setBackground(new java.awt.Color(255, 204, 204));
        jRadio3coups.setBackground(new java.awt.Color(255, 204, 204));
        jRadio3coups.setText("3 coups");
        buttonGroupOptionsAvances.add(jRadio3coups);
        jRadio3coups.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel50.add(jRadio3coups);

        jPanel46.add(jPanel50);

        jPanel51.setBackground(new java.awt.Color(255, 204, 204));
        jRadio8coups.setBackground(new java.awt.Color(255, 204, 204));
        jRadio8coups.setText("8 coups");
        buttonGroupOptionsAvances.add(jRadio8coups);
        jRadio8coups.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel51.add(jRadio8coups);

        jPanel46.add(jPanel51);

        jPanel52.setBackground(new java.awt.Color(255, 204, 204));
        jRadioMax.setBackground(new java.awt.Color(255, 204, 204));
        jRadioMax.setSelected(true);
        jRadioMax.setText("Maximum");
        buttonGroupOptionsAvances.add(jRadioMax);
        jRadioMax.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel52.add(jRadioMax);

        jPanel46.add(jPanel52);

        jDiaOptions.getContentPane().add(jPanel46, java.awt.BorderLayout.CENTER);

        jButton1.setBackground(new java.awt.Color(204, 255, 204));
        jButton1.setText("Valider");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jDiaOptions.getContentPane().add(jButton1, java.awt.BorderLayout.SOUTH);

        jDiaPseudo.setTitle("Record Battu !");
        jDiaPseudo.setLocationRelativeTo(JDiaScore);
        jDiaPseudo.setModal(true);
        jPanel54.setBackground(new java.awt.Color(255, 255, 204));
        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/record.gif")));
        jPanel54.add(jLabel63);

        jDiaPseudo.getContentPane().add(jPanel54, java.awt.BorderLayout.NORTH);

        jPanel53.setBackground(new java.awt.Color(255, 255, 204));
        jLabel62.setText("Votre pseudo:");
        jPanel53.add(jLabel62);

        Pseudo.setMaximumSize(new java.awt.Dimension(100, 20));
        Pseudo.setMinimumSize(new java.awt.Dimension(100, 20));
        Pseudo.setPreferredSize(new java.awt.Dimension(100, 20));
        Pseudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PseudoActionPerformed(evt);
            }
        });

        jPanel53.add(Pseudo);

        jDiaPseudo.getContentPane().add(jPanel53, java.awt.BorderLayout.CENTER);

        jDiaHightScore.setTitle("High Scores");
        jPanel55.setLayout(new java.awt.GridLayout(3, 3));

        jPanel55.setBackground(new java.awt.Color(255, 204, 204));
        jLabel67.setBackground(new java.awt.Color(255, 204, 204));
        jLabel67.setText(" Mode Facile :");
        jPanel55.add(jLabel67);

        jLabel68.setBackground(new java.awt.Color(255, 204, 204));
        jPanel55.add(jLabel68);

        jLabel69.setBackground(new java.awt.Color(255, 204, 204));
        jPanel55.add(jLabel69);

        jLabel70.setBackground(new java.awt.Color(255, 204, 204));
        jLabel70.setText(" Mode Moyen :");
        jPanel55.add(jLabel70);

        jLabel71.setBackground(new java.awt.Color(255, 204, 204));
        jPanel55.add(jLabel71);

        jLabel72.setBackground(new java.awt.Color(255, 204, 204));
        jPanel55.add(jLabel72);

        jLabel73.setBackground(new java.awt.Color(255, 204, 204));
        jLabel73.setText(" Mode Difficile :");
        jPanel55.add(jLabel73);

        jLabel74.setBackground(new java.awt.Color(255, 204, 204));
        jPanel55.add(jLabel74);

        jLabel75.setBackground(new java.awt.Color(255, 204, 204));
        jPanel55.add(jLabel75);

        jDiaHightScore.getContentPane().add(jPanel55, java.awt.BorderLayout.CENTER);

        jPanel56.setBackground(new java.awt.Color(255, 204, 204));
        jLabel76.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/hight scoresAnim.gif")));
        jPanel56.add(jLabel76);

        jDiaHightScore.getContentPane().add(jPanel56, java.awt.BorderLayout.NORTH);

        jPanel57.setBackground(new java.awt.Color(255, 204, 204));
        jButton3.setBackground(new java.awt.Color(255, 255, 204));
        jButton3.setText("Effacer les Scores");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel57.add(jButton3);

        jDiaHightScore.getContentPane().add(jPanel57, java.awt.BorderLayout.SOUTH);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jPanel23.setLayout(new java.awt.BorderLayout());

        jPanel24.setBackground(new java.awt.Color(204, 204, 255));
        jPanel23.add(jPanel24, java.awt.BorderLayout.SOUTH);

        jPanel25.setBackground(new java.awt.Color(204, 204, 255));
        jPanel25.setMaximumSize(new java.awt.Dimension(10, 60));
        jPanel25.setMinimumSize(new java.awt.Dimension(10, 60));
        jPanel25.setPreferredSize(new java.awt.Dimension(10, 60));
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/TheGame2k4anim2.gif")));
        jPanel25.add(jLabel32);

        jPanel23.add(jPanel25, java.awt.BorderLayout.NORTH);

        jPanel26.setBackground(new java.awt.Color(204, 204, 255));
        jPanel23.add(jPanel26, java.awt.BorderLayout.EAST);

        jPanel27.setBackground(new java.awt.Color(204, 204, 255));
        jPanel23.add(jPanel27, java.awt.BorderLayout.WEST);

        jPanel58.setLayout(new java.awt.GridLayout(1, 0));

        jPanel58.setBackground(new java.awt.Color(204, 204, 255));
        jPanel58.setOpaque(false);
        Laye.setMinimumSize(new java.awt.Dimension(500, 500));
        Laye.setOpaque(true);
        jPanelJeuGrille.setLayout(new java.awt.GridLayout(10, 10));

        jPanelJeuGrille.setBackground(new java.awt.Color(204, 204, 255));
        jPanelJeuGrille.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelJeuGrille.setName("");
        jPanelJeuGrille.setPreferredSize(new java.awt.Dimension(400, 400));
        jPanelJeuGrille.setOpaque(false);
        jPanelJeuGrille.setBounds(0, 0, -1, -1);
        Laye.add(jPanelJeuGrille, javax.swing.JLayeredPane.DEFAULT_LAYER);

        labelImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/TheG2.jpg")));
        labelImage.setBounds(0, 0, -1, -1);
        Laye.add(labelImage, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel58.add(Laye);

        jPanel23.add(jPanel58, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel23, java.awt.BorderLayout.CENTER);

        jPanel13.setLayout(new java.awt.BorderLayout());

        jPanel13.setBackground(new java.awt.Color(204, 204, 255));
        jPanel13.setMaximumSize(new java.awt.Dimension(93, 50));
        jPanel13.setMinimumSize(new java.awt.Dimension(93, 50));
        jPanel13.setPreferredSize(new java.awt.Dimension(93, 50));
        jPanel28.setLayout(new javax.swing.BoxLayout(jPanel28, javax.swing.BoxLayout.Y_AXIS));

        jPanel28.setBackground(new java.awt.Color(204, 204, 255));
        jLabel49.setBackground(new java.awt.Color(204, 255, 255));
        jLabel49.setText(" ");
        jLabel49.setMaximumSize(new java.awt.Dimension(78, 16));
        jLabel49.setMinimumSize(new java.awt.Dimension(78, 16));
        jLabel49.setPreferredSize(new java.awt.Dimension(78, 16));
        jPanel28.add(jLabel49);

        jLabel50.setBackground(new java.awt.Color(204, 255, 255));
        jLabel50.setText(" ");
        jLabel50.setMaximumSize(new java.awt.Dimension(78, 16));
        jLabel50.setMinimumSize(new java.awt.Dimension(78, 16));
        jLabel50.setPreferredSize(new java.awt.Dimension(78, 16));
        jPanel28.add(jLabel50);

        jLabel48.setBackground(new java.awt.Color(204, 255, 255));
        jLabel48.setText(" ");
        jLabel48.setMaximumSize(new java.awt.Dimension(78, 16));
        jLabel48.setMinimumSize(new java.awt.Dimension(78, 16));
        jLabel48.setPreferredSize(new java.awt.Dimension(78, 16));
        jPanel28.add(jLabel48);

        jLabel45.setBackground(new java.awt.Color(204, 255, 255));
        jLabel45.setText(" ");
        jLabel45.setMaximumSize(new java.awt.Dimension(78, 16));
        jLabel45.setMinimumSize(new java.awt.Dimension(78, 16));
        jLabel45.setPreferredSize(new java.awt.Dimension(78, 16));
        jPanel28.add(jLabel45);

        jLnbcoups.setBackground(new java.awt.Color(204, 255, 255));
        jLnbcoups.setFont(new java.awt.Font("Comic Sans MS", 0, 10));
        jLnbcoups.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLnbcoups.setText("Nb de coups");
        jLnbcoups.setMaximumSize(new java.awt.Dimension(100, 16));
        jLnbcoups.setMinimumSize(new java.awt.Dimension(100, 16));
        jLnbcoups.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel28.add(jLnbcoups);

        AfficheCoups.setEditable(false);
        AfficheCoups.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        AfficheCoups.setText(" ");
        AfficheCoups.setAlignmentX(0.0F);
        AfficheCoups.setMaximumSize(new java.awt.Dimension(100, 16));
        AfficheCoups.setMinimumSize(new java.awt.Dimension(100, 16));
        AfficheCoups.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel28.add(AfficheCoups);

        jLabel44.setBackground(new java.awt.Color(204, 255, 255));
        jLabel44.setText(" ");
        jLabel44.setMaximumSize(new java.awt.Dimension(78, 16));
        jLabel44.setMinimumSize(new java.awt.Dimension(78, 16));
        jLabel44.setPreferredSize(new java.awt.Dimension(78, 16));
        jPanel28.add(jLabel44);

        jLscore.setFont(new java.awt.Font("Comic Sans MS", 1, 10));
        jLscore.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLscore.setText("Score bloc");
        jLscore.setMaximumSize(new java.awt.Dimension(100, 16));
        jLscore.setMinimumSize(new java.awt.Dimension(100, 16));
        jLscore.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel28.add(jLscore);

        AfficheScore.setEditable(false);
        AfficheScore.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        AfficheScore.setText(" ");
        AfficheScore.setAlignmentX(0.0F);
        AfficheScore.setMaximumSize(new java.awt.Dimension(100, 16));
        AfficheScore.setMinimumSize(new java.awt.Dimension(100, 16));
        AfficheScore.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel28.add(AfficheScore);

        Affichechrono.setEditable(false);
        Affichechrono.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Affichechrono.setText(" ");
        Affichechrono.setAlignmentX(0.0F);
        Affichechrono.setMaximumSize(new java.awt.Dimension(100, 16));
        Affichechrono.setMinimumSize(new java.awt.Dimension(100, 16));
        Affichechrono.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel28.add(Affichechrono);

        jLabel65.setBackground(new java.awt.Color(204, 255, 255));
        jLabel65.setText(" ");
        jLabel65.setMaximumSize(new java.awt.Dimension(78, 16));
        jLabel65.setMinimumSize(new java.awt.Dimension(78, 16));
        jLabel65.setPreferredSize(new java.awt.Dimension(78, 16));
        jPanel28.add(jLabel65);

        bPrecedent.setBackground(new java.awt.Color(255, 255, 204));
        bPrecedent.setFont(new java.awt.Font("Comic Sans MS", 1, 10));
        bPrecedent.setText("Pr\u00e9c\u00e9dent");
        bPrecedent.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bPrecedent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPrecedentActionPerformed(evt);
            }
        });

        jPanel28.add(bPrecedent);

        jLabel64.setBackground(new java.awt.Color(204, 255, 255));
        jLabel64.setText(" ");
        jLabel64.setMaximumSize(new java.awt.Dimension(78, 8));
        jLabel64.setMinimumSize(new java.awt.Dimension(78, 8));
        jLabel64.setPreferredSize(new java.awt.Dimension(78, 8));
        jPanel28.add(jLabel64);

        Bloc.setBackground(new java.awt.Color(204, 255, 255));
        Bloc.setText(" ");
        Bloc.setMaximumSize(new java.awt.Dimension(78, 55));
        Bloc.setMinimumSize(new java.awt.Dimension(78, 55));
        Bloc.setPreferredSize(new java.awt.Dimension(78, 55));
        jPanel28.add(Bloc);

        Gugus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Gugus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anim/Javasmi.GIF")));
        Gugus.setMaximumSize(new java.awt.Dimension(65, 68));
        Gugus.setMinimumSize(new java.awt.Dimension(65, 68));
        Gugus.setPreferredSize(new java.awt.Dimension(65, 68));
        jPanel28.add(Gugus);

        jPanel13.add(jPanel28, java.awt.BorderLayout.CENTER);

        jPanel29.setBackground(new java.awt.Color(204, 204, 255));
        jPanel13.add(jPanel29, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel13, java.awt.BorderLayout.EAST);

        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.X_AXIS));

        jPanel6.setBackground(new java.awt.Color(204, 255, 255));
        jPanel6.setMaximumSize(new java.awt.Dimension(400, 32));
        jPanel6.setMinimumSize(new java.awt.Dimension(400, 32));
        jPanel6.setPreferredSize(new java.awt.Dimension(400, 32));
        jLinfos.setFont(new java.awt.Font("Comic Sans MS", 1, 10));
        jLinfos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLinfos.setText("Informations :");
        jLinfos.setMaximumSize(new java.awt.Dimension(75, 16));
        jLinfos.setMinimumSize(new java.awt.Dimension(100, 16));
        jLinfos.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(jLinfos);

        infos.setEditable(false);
        infos.setRows(2);
        infos.setAlignmentX(0.0F);
        infos.setMaximumSize(new java.awt.Dimension(200, 16));
        infos.setMinimumSize(new java.awt.Dimension(200, 16));
        infos.setPreferredSize(new java.awt.Dimension(200, 32));
        jPanel6.add(infos);

        getContentPane().add(jPanel6, java.awt.BorderLayout.SOUTH);

        jMenuBar1.setBackground(new java.awt.Color(204, 255, 255));
        jMenuParties.setBackground(new java.awt.Color(204, 255, 255));
        jMenuParties.setText("Partie");
        jMenuParties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuPartiesActionPerformed(evt);
            }
        });

        jMenuItemNouvelle.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItemNouvelle.setBackground(new java.awt.Color(204, 255, 255));
        jMenuItemNouvelle.setText("Nouvelle");
        jMenuItemNouvelle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNouvelleActionPerformed(evt);
            }
        });

        jMenuParties.add(jMenuItemNouvelle);

        jMenuItemHigh.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0));
        jMenuItemHigh.setBackground(new java.awt.Color(204, 255, 255));
        jMenuItemHigh.setText("High Scores");
        jMenuItemHigh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemHighActionPerformed(evt);
            }
        });

        jMenuParties.add(jMenuItemHigh);

        jSeparator1.setBackground(new java.awt.Color(204, 255, 255));
        jMenuParties.add(jSeparator1);

        jMenuItemQuitter.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        jMenuItemQuitter.setBackground(new java.awt.Color(204, 255, 255));
        jMenuItemQuitter.setText("Quitter");
        jMenuItemQuitter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemQuitterActionPerformed(evt);
            }
        });

        jMenuParties.add(jMenuItemQuitter);

        jMenuBar1.add(jMenuParties);

        jMenu1.setBackground(new java.awt.Color(204, 255, 255));
        jMenu1.setText("Modes");
        jRadioButtonFacile.setBackground(new java.awt.Color(204, 255, 255));
        jRadioButtonFacile.setSelected(true);
        jRadioButtonFacile.setText("Facile");
        buttonGroup2.add(jRadioButtonFacile);
        jRadioButtonFacile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonFacileActionPerformed(evt);
            }
        });

        jMenu1.add(jRadioButtonFacile);

        jRadioButtonMoyen.setBackground(new java.awt.Color(204, 255, 255));
        jRadioButtonMoyen.setText("Moyen");
        buttonGroup2.add(jRadioButtonMoyen);
        jRadioButtonMoyen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMoyenActionPerformed(evt);
            }
        });

        jMenu1.add(jRadioButtonMoyen);

        jRadioButtonDifficile.setBackground(new java.awt.Color(204, 255, 255));
        jRadioButtonDifficile.setText("Difficile");
        buttonGroup2.add(jRadioButtonDifficile);
        jRadioButtonDifficile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonDifficileActionPerformed(evt);
            }
        });

        jMenu1.add(jRadioButtonDifficile);

        jRadioButtonSmileys.setBackground(new java.awt.Color(204, 255, 255));
        jRadioButtonSmileys.setText("Smiley's");
        buttonGroup2.add(jRadioButtonSmileys);
        jRadioButtonSmileys.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonSmileysActionPerformed(evt);
            }
        });

        jMenu1.add(jRadioButtonSmileys);

        jMenuBar1.add(jMenu1);

        jMenuParamètres.setBackground(new java.awt.Color(204, 255, 255));
        jMenuParamètres.setText("Param\u00e8tres");
        jMenuParamètres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuParamètresActionPerformed(evt);
            }
        });

        jMenuItemMotifs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jMenuItemMotifs.setBackground(new java.awt.Color(204, 255, 255));
        jMenuItemMotifs.setText("Modifier les motifs");
        jMenuItemMotifs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemMotifsActionPerformed(evt);
            }
        });

        jMenuParamètres.add(jMenuItemMotifs);

        jMenuItemTailleGrille.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        jMenuItemTailleGrille.setBackground(new java.awt.Color(204, 255, 255));
        jMenuItemTailleGrille.setText("Taille de la grille");
        jMenuItemTailleGrille.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTailleGrilleActionPerformed(evt);
            }
        });

        jMenuParamètres.add(jMenuItemTailleGrille);

        jMenuItemOptions.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItemOptions.setBackground(new java.awt.Color(204, 255, 255));
        jMenuItemOptions.setText("Option Avanc\u00e9e");
        jMenuItemOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOptionsActionPerformed(evt);
            }
        });

        jMenuParamètres.add(jMenuItemOptions);

        jMenu2.setBackground(new java.awt.Color(204, 255, 255));
        jMenu2.setText("Sons");
        jMu1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, 0));
        jMu1.setBackground(new java.awt.Color(204, 255, 255));
        jMu1.setSelected(true);
        jMu1.setText("Activer");
        buttonGroupMusik.add(jMu1);
        jMu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMu1ActionPerformed(evt);
            }
        });

        jMenu2.add(jMu1);

        jMu2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, 0));
        jMu2.setBackground(new java.awt.Color(204, 255, 255));
        jMu2.setText("D\u00e9sactiver");
        buttonGroupMusik.add(jMu2);
        jMu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMu2ActionPerformed(evt);
            }
        });

        jMenu2.add(jMu2);

        jMenuParamètres.add(jMenu2);

        jMenuBar1.add(jMenuParamètres);

        jMenuRègles.setBackground(new java.awt.Color(204, 255, 255));
        jMenuRègles.setText("R\u00e8gles");
        jMenuItemRegle.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        jMenuItemRegle.setBackground(new java.awt.Color(204, 255, 255));
        jMenuItemRegle.setText("R\u00e8gles du jeu");
        jMenuItemRegle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRegleActionPerformed(evt);
            }
        });

        jMenuRègles.add(jMenuItemRegle);

        jMenuItemCalcul.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0));
        jMenuItemCalcul.setBackground(new java.awt.Color(204, 255, 255));
        jMenuItemCalcul.setText("Calcul du score");
        jMenuItemCalcul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCalculActionPerformed(evt);
            }
        });

        jMenuRègles.add(jMenuItemCalcul);

        jMenuBar1.add(jMenuRègles);

        jMenuInterrogation.setBackground(new java.awt.Color(204, 255, 255));
        jMenuInterrogation.setText("?");
        jMenuInterrogation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuInterrogationActionPerformed(evt);
            }
        });

        jMenuItemRemerciements.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, 0));
        jMenuItemRemerciements.setBackground(new java.awt.Color(204, 255, 255));
        jMenuItemRemerciements.setText("Remerciements");
        jMenuItemRemerciements.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRemerciementsActionPerformed(evt);
            }
        });

        jMenuInterrogation.add(jMenuItemRemerciements);

        jMenuItemApropos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9, 0));
        jMenuItemApropos.setBackground(new java.awt.Color(204, 255, 255));
        jMenuItemApropos.setText("A propos de...");
        jMenuItemApropos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAproposActionPerformed(evt);
            }
        });

        jMenuInterrogation.add(jMenuItemApropos);

        jMenuBar1.add(jMenuInterrogation);

        setJMenuBar(jMenuBar1);

        pack();
    }//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
    int largeur=Laye.getBounds().width;
    int hauteur=Laye.getBounds().height;
    jPanelJeuGrille.setSize(largeur, hauteur);
    labelImage.setSize(largeur, hauteur);
    setVisible(true);
    }//GEN-LAST:event_formComponentResized

    private void jMu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMu1ActionPerformed
    M.stopSon();
    S1=new Son("scorebon.wav");
    S2=new Son("scoremoyen.wav");
    S3=new Son("scoreneg.wav");
    S4=new Son("1.wav");
    S5=new Son("blocde1.wav");
    S6=new Son("blocde1bis.wav");
    M.loopSon();

    }//GEN-LAST:event_jMu1ActionPerformed

    private void jMu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMu2ActionPerformed
    M.stopSon();
    S1.seturl("");
    S2.seturl("");
    S3.seturl("");
    S4.seturl("");
    S5.seturl("");
    S6.seturl("");
    }//GEN-LAST:event_jMu2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       try {  java.io.FileOutputStream ostream = new java.io.FileOutputStream(fichier); //Bouton effacer les scores
         java.io.ObjectOutputStream p= new java.io.ObjectOutputStream(ostream);           
         p.writeUTF("Anonyme") ; p.flush();         
         p.writeInt(0) ; p.flush();
         p.writeUTF("Anonyme") ; p.flush();
         p.writeInt(0) ; p.flush();
         p.writeUTF("Anonyme") ; p.flush();
         p.writeInt(0) ; p.flush();
         ostream.close() ;
         init_HightScore() ;
       }
       catch(Exception e) {
       }
       
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItemHighActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemHighActionPerformed
        jDiaHightScore.setBounds(352,255,320,230);
        jDiaHightScore.show();
    }//GEN-LAST:event_jMenuItemHighActionPerformed

    private void PseudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PseudoActionPerformed
    try { //Action performed sur le jTextField ou l'on entre le notre pseudo
        java.io.FileOutputStream ostream = new java.io.FileOutputStream(fichier);
        java.io.ObjectOutputStream p= new java.io.ObjectOutputStream(ostream);    
        
        if ( (nbL==6 ) && (nbC==6 ) && (nbMotif==3 ) ) { // = si on est en mode Facil  
            p.writeUTF(Pseudo.getText()) ; p.flush() ;   
            p.writeInt(tt) ;  p.flush() ;
            p.writeUTF(jLabel71.getText()); p.flush() ;
            p.writeInt( Integer.parseInt( jLabel72.getText() ) ) ; p.flush() ;
            p.writeUTF(jLabel74.getText()) ; p.flush() ;
            p.writeInt( Integer.parseInt( jLabel75.getText() ) ) ; p.flush() ; 
        }
       
        else if ( (nbL==12 ) && (nbC==12 ) && (nbMotif==4 ) ) { // = si on est en mode Moyen
            p.writeUTF(jLabel68.getText()) ; p.flush() ;
            p.writeInt( Integer.parseInt( jLabel69.getText() ) ) ;  p.flush() ;
            p.writeUTF(Pseudo.getText()); p.flush() ;
            p.writeInt(tt) ; p.flush() ;
            p.writeUTF(jLabel74.getText()) ; p.flush() ;
            p.writeInt( Integer.parseInt( jLabel75.getText() ) ) ; p.flush() ;                
        }
       
        else  if ( (nbL==18 ) && (nbC==18 ) && (nbMotif==6 ) ){ // = Mode Difficil
            p.writeUTF(jLabel68.getText()) ; p.flush() ;
            p.writeInt( Integer.parseInt( jLabel69.getText() ) ) ;  p.flush() ;
            p.writeUTF(jLabel71.getText()); p.flush() ;
            p.writeInt( Integer.parseInt( jLabel72.getText() ) ) ; p.flush() ;
            p.writeUTF(Pseudo.getText() ) ; p.flush() ;
            p.writeInt(tt) ; p.flush() ;                
        }  
        ostream.close();
    }
    catch (Exception e) { System.out.println("Erreur ds la saisi du fichier HighScore5"); }
    init_HightScore() ;
    jDiaPseudo.dispose();
    
    }//GEN-LAST:event_PseudoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jRadio1coup.isSelected()) coups_pre=1; // On regarde quel jRadio du nombre de coup précédant tolérer a "t" selectionner
        else if (jRadio3coups.isSelected()) coups_pre=3;
        else if (jRadio5coups.isSelected()) coups_pre=5;
        else if (jRadio8coups.isSelected()) coups_pre=8;
        else if (jRadio15coups.isSelected()) coups_pre=15;
        else if (jRadioMax.isSelected()) coups_pre=1000;
        jDiaOptions.setVisible(false);
        jDiaOptions.dispose();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButNouvelleSmiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButNouvelleSmiActionPerformed
        TheGame = new Cjeu(nbL,nbC, nbMotif); //Nouvelle partie
        creation_jeu() ;
        jDiaScoreSmi.setVisible(false);
        jDiaScoreSmi.dispose();
    }//GEN-LAST:event_jButNouvelleSmiActionPerformed

    private void jButQuitterSmiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButQuitterSmiActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButQuitterSmiActionPerformed

    private void jRadioButtonSmileysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonSmileysActionPerformed
    nbL=12; // Si selection du mode Smiley, on initialise les variables
    nbC=16;
    nbMotif=4 ;
    tailleMotif=30;
    formeMotif="smi";
    TheGame = new Cjeu(nbL,nbC, nbMotif); // on crrer lengthjeu avec les nouvelle valeur des variables
    creation_jeu() ;
    }//GEN-LAST:event_jRadioButtonSmileysActionPerformed

    private void TailleCFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TailleCFocusLost

    }//GEN-LAST:event_TailleCFocusLost

    private void bPrecedentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPrecedentActionPerformed
        //Bouton Precendent 
        if (TheGame.v.size()>0  && cpt_pre<coups_pre) { //Tan que l'ArrayList n'est pas vide et que le nb de coup précédent tolerer n'est pas dépasser
        TheGame.jeu=(int[][]) TheGame.v.get(TheGame.v.size()-1); // on recupere le dernier (size()-1) élément de la liste et on lengthremplace par l'élément courant (TheGame.jeu)
        copi_tableau_Cjeu(); 
        TheGame.v.remove(TheGame.v.size()-1); // on supprime le dernier element de la liste pour ne plus y avoir acces lorsque l'on relancera get(TheGame.v.ize()-1)
        TheGame.nbcoup=TheGame.nbcoup-1; // on décrémente le nombre de coups
        AfficheCoups.setText(String.valueOf(TheGame.nbcoup));
        TheGame.score_bloc_total= ((Integer)TheGame.score.get(TheGame.score.size()-1)).intValue();// on récupere lengthdernier score total enregistrer et on lengthremplace dans l'element courant
        TheGame.score.remove(TheGame.score.size()-1); // et on le supprime
        cpt_pre++; // on compte lengthnombre de coup precedent successif
        infos.setText("Vous vous êtes trompé ?!");
      }
      else {
        infos.setText("Impossible de reculer plus...");   
      }
    }//GEN-LAST:event_bPrecedentActionPerformed

    private void jRadioButtonDifficileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonDifficileActionPerformed
    nbL=18; //Mode Difficile
    nbC=18;
    nbMotif=6 ;
    tailleMotif=30;
    formeMotif="symm";
    TheGame = new Cjeu(nbL,nbC, nbMotif);
    creation_jeu() ;
    }//GEN-LAST:event_jRadioButtonDifficileActionPerformed

    private void jRadioButtonMoyenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMoyenActionPerformed
    nbL=12; // Mode Moyen
    nbC=12;
    nbMotif=4 ;
    tailleMotif=40;
    formeMotif="biz";
    TheGame = new Cjeu(nbL,nbC, nbMotif);
    creation_jeu() ;
    }//GEN-LAST:event_jRadioButtonMoyenActionPerformed

    private void RecommencerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RecommencerActionPerformed
        //Bouton Recomencer (dans le jDialog jDiaScore)
        TheGame.jeu=(int[][]) TheGame.v.get(0); //on récupere le 1er element de la l'ArrayList v
        TheGame.vide_list(); // on la vide (en supprimant tout ces elements
        creation_jeu();
        JDiaScore.setVisible(false);
        JDiaScore.dispose();
        defil.interrupt();
       
    }//GEN-LAST:event_RecommencerActionPerformed

    private void jRadioButtonFacileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonFacileActionPerformed
    nbL=6; // Mode Facile
    nbC=6;
    nbMotif=3 ;
    tailleMotif=50;
    formeMotif="oei";
    TheGame = new Cjeu(nbL,nbC, nbMotif);
    creation_jeu() ;
    }//GEN-LAST:event_jRadioButtonFacileActionPerformed

    private void RejouerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RejouerActionPerformed
        JDiaScore.setVisible(false); // Bouton nouvelle partie
        JDiaScore.dispose();
        TheGame = new Cjeu(nbL,nbC, nbMotif);
        creation_jeu() ;
        defil.interrupt(); 

    }//GEN-LAST:event_RejouerActionPerformed

    private void jMenuItemNouvelleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNouvelleActionPerformed
        TheGame = new Cjeu(nbL,nbC, nbMotif);
        creation_jeu() ;
    }//GEN-LAST:event_jMenuItemNouvelleActionPerformed

    private void bMotifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMotifActionPerformed
        //Bouton valider du jDialog jDiaChoixMotif 
        nbMotif=ChoixQ.getSelectedIndex()+3; // on recupere le l'index du jComboBox du nombre de motifs (0 pour 3; 1 pour 4 ; 2 pour 5 )
        tailleMotif=50-ChoixT.getSelectedIndex()*10; //idem pour la taille des motif (index 0 pour 50 pixel ; index 1 pour 40 pixel ... ce qui explique la petite formule)
        if (jRad1.isSelected()) formeMotif="symm"; //On regarde quel jRadio est selectionné
        else if (jRad2.isSelected()) 
            formeMotif="biz";
        else if (jRad3.isSelected()) formeMotif="oei";
        JDiaChoixMotifs.setVisible(false);
        JDiaChoixMotifs.dispose();
        TheGame = new Cjeu(nbL,nbC, nbMotif); // on cré la partie
        creation_jeu() ;
        
    }//GEN-LAST:event_bMotifActionPerformed

    private void jMenuItemCalculActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCalculActionPerformed
        jDiaRScore.setBounds(312,70,400,600); // jDialog Regle du Jeu
        jDiaRScore.show();
    }//GEN-LAST:event_jMenuItemCalculActionPerformed

    private void Quitter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Quitter2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_Quitter2ActionPerformed

    private void jMenuItemOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOptionsActionPerformed
        jDiaOptions.setTitle("Option Avancée"); // Option Avancée
        jDiaOptions.setBounds(307,275, 410, 190);
        jDiaOptions.show();
    }//GEN-LAST:event_jMenuItemOptionsActionPerformed

    private void RadioGrandeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioGrandeActionPerformed
        //jDialog Taille Grille
        TailleL.setText("");//on vide le contenu des jTextField si quelquechose etait dedans lorsque l'on (re) selectionne un jRadio 
        TailleC.setText("");
        Avertiss1.setVisible(false);//on efface l'avertissement "la grille doit etre entre 6*6 et 18*28"
        Avertiss2.setVisible(false);
    }//GEN-LAST:event_RadioGrandeActionPerformed

    private void RadioPetiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioPetiteActionPerformed
        TailleL.setText("");//on vide le contenu des jTextField si quelquechose etait dedans lorsque l'on (re) selectionne un jRadio 
        TailleC.setText("");
        Avertiss1.setVisible(false);//on efface l'avertissement "la grille doit etre entre 6*6 et 18*28"
        Avertiss2.setVisible(false);
    }//GEN-LAST:event_RadioPetiteActionPerformed

    private void jMenuItemRegleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRegleActionPerformed
        jDiaRegle.setBounds(282,20,460,700);
        jDiaRegle.show();
    }//GEN-LAST:event_jMenuItemRegleActionPerformed

    private void TailleLFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TailleLFocusLost

    }//GEN-LAST:event_TailleLFocusLost
    
    private void RadioMoyenneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioMoyenneActionPerformed
        TailleL.setText("");//on vide le contenu des jTextField si quelquechose etait dedans lorsque l'on (re) selectionne un jRadio 
        TailleC.setText("");
        Avertiss1.setVisible(false);//on efface l'avertissement "la grille doit etre entre 6*6 et 18*28"
        Avertiss2.setVisible(false);
    }//GEN-LAST:event_RadioMoyenneActionPerformed
    
    private void RadioCacheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RadioCacheActionPerformed

    }//GEN-LAST:event_RadioCacheActionPerformed
    
    private void TailleCFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TailleCFocusGained
        //jDialog Taille Grille
        //On coche la case caché si l'utilisateur clique dans l'un des deux jTextField
        if (RadioPetite.isSelected()) { 
            RadioPetite.setSelected(false);
            RadioCache.setSelected(true);
        }
        if (RadioMoyenne.isSelected()) {
            RadioMoyenne.setSelected(false);
            RadioCache.setSelected(true);
        }
        if (RadioGrande.isSelected()) {
            RadioGrande.setSelected(false);
            RadioCache.setSelected(true);
        }
        Avertiss1.setVisible(true);//on affiche l'avertissement "la grille doit etre entre 6*6 et 18*28"
        Avertiss2.setVisible(true);
    }//GEN-LAST:event_TailleCFocusGained
    
    private void TailleCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TailleCActionPerformed

    }//GEN-LAST:event_TailleCActionPerformed
    
    private void TailleLFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_TailleLFocusGained
        //jDialog Taille Grille
        //On coche la case caché si l'utilisateur clique dans l'un des deux jTextField
        if (RadioPetite.isSelected()) {
            RadioPetite.setSelected(false);
            RadioCache.setSelected(true);
        }
        if (RadioMoyenne.isSelected()) {
            RadioMoyenne.setSelected(false);
            RadioCache.setSelected(true);
        }
        if (RadioGrande.isSelected()) {
            RadioGrande.setSelected(false);
            RadioCache.setSelected(true);
        }
        Avertiss1.setVisible(true);//on affiche l'avertissement "la grille doit etre entre 6*6 et 18*28"
        Avertiss2.setVisible(true); 
    }//GEN-LAST:event_TailleLFocusGained
    
    private void bValiderTailleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bValiderTailleActionPerformed
        //Bouton Valider du panneau Taille Grille
        //On regarde quel jRadio est selectionner et on modifie les variable en conséquence
        if (RadioPetite.isSelected()) {
            nbL=6;
            nbC=6;
            JDiaTailleGrille.setVisible(false);
            JDiaTailleGrille.dispose();
            TheGame = new Cjeu(nbL,nbC, nbMotif);
            creation_jeu() ;
        }
        if (RadioMoyenne.isSelected()) {
            nbL=12;
            nbC=12;
            JDiaTailleGrille.setVisible(false);
            JDiaTailleGrille.dispose();
            TheGame = new Cjeu(nbL,nbC, nbMotif);
            creation_jeu() ;
        }
        if (RadioGrande.isSelected()) {
            nbL=18;
            nbC=18;
            JDiaTailleGrille.setVisible(false);
            JDiaTailleGrille.dispose();
            TheGame = new Cjeu(nbL,nbC, nbMotif);
            creation_jeu() ;
        }
        //Verification de se qui est entrer (bon nombre et/ou non-lettre) dans les jTextField
        String chaine=TailleC.getText();
        String chaine2=TailleL.getText();
        int a,b;
        boolean conv=false;
        boolean chaine1bonne=true;
        boolean chaine2bonne=true;
        if (((TailleL.getText().equals("")==false) && (TailleC.getText().equals("")==false)))//Si les jTextfield contienne quelque chose
        {
        for (int i=0 ; i<chaine.length() ; i++){//On parcour le 1er jTextfield et on regarde si lengthcode ascii est compris entre 48 et 57 (c.a.d 0 et 9) pour chaque caractere de la chaine
            a=chaine.charAt(i);
            if (a<48 || a>57 )
                chaine1bonne=false;
        }
        if (chaine1bonne) {
            for (int j=0 ; j<chaine2.length() ; j++){//idem
            b=chaine2.charAt(j);
            if (b<48 || b>57)
                chaine2bonne=false;
            }
        }
        if (chaine1bonne==true && chaine2bonne==true)//Si les chaine sont des chiffre , on vérifie ensuite si les chiffre son bien compris entre les tailles maximum et minimum autoriser
            if((java.lang.Integer.parseInt(TailleL.getText())>5) && (java.lang.Integer.parseInt(TailleC.getText())>5) && (java.lang.Integer.parseInt(TailleC.getText())<29) && (java.lang.Integer.parseInt(TailleL.getText())<19))
                            conv=true;
        }
        if (conv) {
            nbL=java.lang.Integer.parseInt(TailleL.getText());
            nbC=java.lang.Integer.parseInt(TailleC.getText());
            JDiaTailleGrille.setVisible(false);
            JDiaTailleGrille.dispose();
            TheGame = new Cjeu(nbL,nbC, nbMotif);
            creation_jeu() ;
        }
        else
            TailleL.requestFocus();


    }//GEN-LAST:event_bValiderTailleActionPerformed
    
    private void jMenuItemTailleGrilleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTailleGrilleActionPerformed
        if (formeMotif!="smi") {//Lancement de la jDialog Taile Grille sauf en mode smiley
            JDiaTailleGrille.setBounds(372,246,280,245);
            JDiaTailleGrille.setTitle("Taille de la grille");
            Avertiss1.setVisible(false);
            Avertiss2.setVisible(false);
            JDiaTailleGrille.show();
        }
        else infos.setText("Nan , pas en mode Smiley's !...");
        
    }//GEN-LAST:event_jMenuItemTailleGrilleActionPerformed
    
    private void jMenuParamètresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuParamètresActionPerformed

    }//GEN-LAST:event_jMenuParamètresActionPerformed
    
    private void jMenuItemRemerciementsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRemerciementsActionPerformed
        jDiaRemer.setBounds(282,170,460,400);
        jDiaRemer.setTitle("Remerciements");
        jDiaRemer.show();
    }//GEN-LAST:event_jMenuItemRemerciementsActionPerformed
    
    private void jMenuItemAproposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAproposActionPerformed
        jDiaApropos.setBounds(282,70,460,600);
        jDiaApropos.setTitle("A propos de...");
        jDiaApropos.show();
    }//GEN-LAST:event_jMenuItemAproposActionPerformed
    
    private void jMenuInterrogationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuInterrogationActionPerformed
        
    }//GEN-LAST:event_jMenuInterrogationActionPerformed
    
    private void jMenuItemQuitterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemQuitterActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItemQuitterActionPerformed
        
    private void jRad1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRad1ActionPerformed

    }//GEN-LAST:event_jRad1ActionPerformed
    
    private void jMenuItemMotifsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemMotifsActionPerformed
        JDiaChoixMotifs.setBounds(287,165,450,410);
        JDiaChoixMotifs.show();
    }//GEN-LAST:event_jMenuItemMotifsActionPerformed
    
    private void jMenuPartiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuPartiesActionPerformed

    }//GEN-LAST:event_jMenuPartiesActionPerformed

    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
   
    private void buttonActionPerformed(java.awt.event.ActionEvent evt) {
        JButton but =(JButton) evt.getSource() ;//on recupere la source du bouton cliqué
        int nb = Integer.parseInt(but.getName()); // on recupere son nom que l'on a converti en int
        int coordi = (nb / nbC) ; // on recupere d'apres son numero les coordonnée x, et y du bouton (l, c dans la classe Cjeu)
        int coordj = nb - nbC*coordi ;
        TheGame.action(coordi,coordj); // on lance les methodes de modification du tableau d'aleatoire (voir action de Cjeu)
        cpt_pre=0;      //on initialise le nombre de coup precedent a 0
        S4.playSon();
        copi_tableau_Cjeu(); // on réaffiche le tableau ainsi modifier 

     }
     
    public static void main(String args[]) {
        new Cinterface().show();
    }
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AfficheCoups;
    private javax.swing.JTextField AfficheScore;
    private javax.swing.JTextField Affichechrono;
    private javax.swing.JLabel Avertiss1;
    private javax.swing.JLabel Avertiss2;
    private javax.swing.JLabel Bloc;
    private javax.swing.JComboBox ChoixQ;
    private javax.swing.JComboBox ChoixT;
    private javax.swing.JLabel Gugus;
    private javax.swing.JDialog JDiaChoixMotifs;
    private javax.swing.JDialog JDiaScore;
    private javax.swing.JDialog JDiaTailleGrille;
    private javax.swing.JLayeredPane Laye;
    private javax.swing.JTextField Pseudo;
    private javax.swing.JButton Quitter2;
    private javax.swing.JRadioButton RadioCache;
    private javax.swing.JRadioButton RadioGrande;
    private javax.swing.JRadioButton RadioMoyenne;
    private javax.swing.JRadioButton RadioPetite;
    private javax.swing.JButton Recommencer;
    private javax.swing.JButton Rejouer;
    private javax.swing.JTextField TailleC;
    private javax.swing.JTextField TailleL;
    private javax.swing.JLabel avis;
    private javax.swing.JButton bMotif;
    private javax.swing.JButton bPrecedent;
    private javax.swing.JButton bValiderTaille;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroupMusik;
    private javax.swing.ButtonGroup buttonGroupOptionsAvances;
    private javax.swing.JLabel imageAvi;
    private javax.swing.JLabel image_score;
    private javax.swing.JTextArea infos;
    private javax.swing.JButton jButNouvelleSmi;
    private javax.swing.JButton jButQuitterSmi;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JDialog jDiaApropos;
    private javax.swing.JDialog jDiaHightScore;
    private javax.swing.JDialog jDiaOptions;
    private javax.swing.JDialog jDiaPseudo;
    private javax.swing.JDialog jDiaRScore;
    private javax.swing.JDialog jDiaRegle;
    private javax.swing.JDialog jDiaRemer;
    private javax.swing.JDialog jDiaScoreSmi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLinfos;
    private javax.swing.JLabel jLnbcoups;
    private javax.swing.JLabel jLscore;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuInterrogation;
    private javax.swing.JMenuItem jMenuItemApropos;
    private javax.swing.JMenuItem jMenuItemCalcul;
    private javax.swing.JMenuItem jMenuItemHigh;
    private javax.swing.JMenuItem jMenuItemMotifs;
    private javax.swing.JMenuItem jMenuItemNouvelle;
    private javax.swing.JMenuItem jMenuItemOptions;
    private javax.swing.JMenuItem jMenuItemQuitter;
    private javax.swing.JMenuItem jMenuItemRegle;
    private javax.swing.JMenuItem jMenuItemRemerciements;
    private javax.swing.JMenuItem jMenuItemTailleGrille;
    private javax.swing.JMenu jMenuParamètres;
    private javax.swing.JMenu jMenuParties;
    private javax.swing.JMenu jMenuRègles;
    private javax.swing.JRadioButtonMenuItem jMu1;
    private javax.swing.JRadioButtonMenuItem jMu2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelJeuGrille;
    private javax.swing.JRadioButton jRad1;
    private javax.swing.JRadioButton jRad2;
    private javax.swing.JRadioButton jRad3;
    private javax.swing.JRadioButton jRadio15coups;
    private javax.swing.JRadioButton jRadio1coup;
    private javax.swing.JRadioButton jRadio3coups;
    private javax.swing.JRadioButton jRadio5coups;
    private javax.swing.JRadioButton jRadio8coups;
    private javax.swing.JRadioButtonMenuItem jRadioButtonDifficile;
    private javax.swing.JRadioButtonMenuItem jRadioButtonFacile;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMoyen;
    private javax.swing.JRadioButtonMenuItem jRadioButtonSmileys;
    private javax.swing.JRadioButton jRadioMax;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel jnbbloc21;
    private javax.swing.JTextField jnbbloc21pts;
    private javax.swing.JLabel jnbcoups;
    private javax.swing.JTextField jnbcoupspts;
    private javax.swing.JTextField jscorebloc;
    private javax.swing.JTextField jscorefin;
    private javax.swing.JLabel labelImage;
    // End of variables declaration//GEN-END:variables

   }

