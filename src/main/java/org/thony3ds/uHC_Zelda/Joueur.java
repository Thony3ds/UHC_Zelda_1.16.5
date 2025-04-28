package org.thony3ds.uHC_Zelda;

public class Joueur {
    private String classe = "";
    private String equipe = "";

    public Joueur(String classe, String equipe){
        this.classe = classe;
        this.equipe = equipe;
    }

    public String getClasse(){return classe;}
    public String getEquipe(){return equipe;}

    public void setClasse(String classe){this.classe = classe;}
    public void setEquipe(String equipe){this.equipe = equipe;}
}
