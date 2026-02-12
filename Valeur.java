package cartes;

/**
 * Enumération représentant les valeurs des cartes à jouer.
 * Les valeurs vont de AS (1) à ROI (13) dans l'ordre croissant.
 * 
 */
public enum Valeur {
    /** 
     * Représente la valeur AS, valeur numérique 1 
     */
    AS, 
    
    /** 
     * Représente la valeur DEUX, valeur numérique 2 
     */
    DEUX,
    
    /** 
     * Représente la valeur TROIS, valeur numérique 3 
     */
    TROIS, 
    
    /** 
     * Représente la valeur QUATRE, valeur numérique 4 
     */
    QUATRE, 
    
    /** 
     * Représente la valeur CINQ, valeur numérique 5 
     */
    CINQ, 
    
    /** 
     * Représente la valeur SIX, valeur numérique 6 
     */
    SIX, 
    
    /** 
     * Représente la valeur SEPT, valeur numérique 7 
     */
    SEPT, 
    
    /** 
     * Représente la valeur HUIT, valeur numérique 8 
     */
    HUIT, 
    
    /** 
     * Représente la valeur NEUF, valeur numérique 9 
     */
    NEUF, 
    
    /** 
     * Représente la valeur DIX, valeur numérique 10 
     */
    DIX, 
    
    /** 
     * Représente la valeur VALET (Jack), valeur numérique 11 
     */
    VALET, 
    
    /** 
     * Représente la valeur DAME (Queen), valeur numérique 12 
     */
    DAME, 
    
    /** 
     * Représente la valeur ROI (King), valeur numérique 13 
     */
    ROI;


    /**
     * Retourne la valeur numérique associée à cette constante d'énumération.
     * La valeur numérique correspond à la position dans l'énumération + 1.
     * Par exemple : AS → 1, DEUX → 2, ..., ROI → 13.
     *
     * @return la valeur numérique de la carte, comprise entre 1 et 13
     * @see Enum#ordinal()
     */
    public int getValeurNumerique(){
        return ordinal()+1;
    }
}