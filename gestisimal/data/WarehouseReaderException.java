package gestisimal.data;

public class WarehouseReaderException extends Exception {

  private static final long serialVersionUID = 1L;

  public WarehouseReaderException() {
    super();
  }

  public WarehouseReaderException(String message) {
    super(message);
  }

  public WarehouseReaderException(Throwable cause) {
    super(cause);
  }

  public WarehouseReaderException(String message, Throwable cause) {
    super(message, cause);
  }

  public WarehouseReaderException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
