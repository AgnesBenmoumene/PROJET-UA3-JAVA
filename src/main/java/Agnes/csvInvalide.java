package Agnes;
//si Le programme rencontre un problème, il identifie que c'est un problème de format CSV
// il l'explique à l'utilisateur, et il reste prêt pour la suite.
public class csvInvalide extends RuntimeException {//pour signaler des erreurs de logique
    public csvInvalide(String message) {
        super(message);
    }//retourner notre message d'erreur
    public csvInvalide(String message, Throwable cause) {
        super(message, cause);
    }//l'erreur d'origine

}
