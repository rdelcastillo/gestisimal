package gestisimal.business;

/**
 * Excepción lanzada cuando a un objeto <code>Warehouse</code> se le pide crear un artículo que
 * ya existe.
 * 
 * @author Rafael del Castillo Gomariz
 *
 */

public class WarehouseDuplicatedNameBrandException extends Exception {

  private static final long serialVersionUID = 1L;

  public WarehouseDuplicatedNameBrandException() {
    super();
  }

  public WarehouseDuplicatedNameBrandException(String message) {
    super(message);
  }

  public WarehouseDuplicatedNameBrandException(Throwable cause) {
    super(cause);
  }

  public WarehouseDuplicatedNameBrandException(String message, Throwable cause) {
    super(message, cause);
  }

  public WarehouseDuplicatedNameBrandException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
