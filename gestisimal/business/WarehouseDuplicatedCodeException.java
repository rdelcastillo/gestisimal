package gestisimal.business;

public class WarehouseDuplicatedCodeException extends Exception {

  private static final long serialVersionUID = 1L;

  public WarehouseDuplicatedCodeException() {
    super();
  }

  public WarehouseDuplicatedCodeException(String message) {
    super(message);
  }

  public WarehouseDuplicatedCodeException(Throwable cause) {
    super(cause);
  }

  public WarehouseDuplicatedCodeException(String message, Throwable cause) {
    super(message, cause);
  }

  public WarehouseDuplicatedCodeException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
