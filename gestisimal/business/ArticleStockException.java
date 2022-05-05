package gestisimal.business;

/**
 * Excepción lanzada antes de que el artículo se quede con existencias en negativo.
 * 
 * @author Rafael del Castillo Gomariz
 *
 */

public class ArticleStockException extends Exception {

  private static final long serialVersionUID = 1L;

  public ArticleStockException() {
    super();
  }

  public ArticleStockException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ArticleStockException(String message, Throwable cause) {
    super(message, cause);
  }

  public ArticleStockException(String message) {
    super(message);
  }

  public ArticleStockException(Throwable cause) {
    super(cause);
  }
  
  

}
