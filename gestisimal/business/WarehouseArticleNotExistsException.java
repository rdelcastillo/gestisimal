package gestisimal.business;

/**
 * Excepción lanzada cuando a un objeto <code>Warehouse</code> se le pide un artículo que no tiene.
 * 
 * @author Rafael del Castillo Gomariz
 *
 */

public class WarehouseArticleNotExistsException extends Exception {

  private static final long serialVersionUID = 1L;

  public WarehouseArticleNotExistsException() {
    super();
  }

  public WarehouseArticleNotExistsException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public WarehouseArticleNotExistsException(String message, Throwable cause) {
    super(message, cause);
  }

  public WarehouseArticleNotExistsException(String message) {
    super(message);
  }

  public WarehouseArticleNotExistsException(Throwable cause) {
    super(cause);
  }

}
