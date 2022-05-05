package gestisimal.business;

/**
 * Excepción lanzada cuando se llama a algún método de un objeto <code>Article</code> 
 * con parámetros erróneos.
 * 
 * @author Rafael del Castillo Gomariz
 *
 */

public class ArticleIllegalErrorArgumentException extends IllegalArgumentException {

  private static final long serialVersionUID = 1L;

  public ArticleIllegalErrorArgumentException() {
    super();
  }

  public ArticleIllegalErrorArgumentException(String s) {
    super(s);
  }

  public ArticleIllegalErrorArgumentException(Throwable cause) {
    super(cause);
  }

  public ArticleIllegalErrorArgumentException(String message, Throwable cause) {
    super(message, cause);
  }

}
