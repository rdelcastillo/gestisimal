package gestisimal.data;

public class WarehouseWriterException extends Exception {

  private static final long serialVersionUID = 1L;

  public WarehouseWriterException() {
    super();
  }

  public WarehouseWriterException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public WarehouseWriterException(String message, Throwable cause) {
    super(message, cause);
  }

  public WarehouseWriterException(String message) {
    super(message);
  }

  public WarehouseWriterException(Throwable cause) {
    super(cause);
  }

}
