package gestisimal.data;

public enum PersistenceType {
  XML, JSON;
  
  WarehouseWriter getWarehouseWriter(String fileName) {
    WarehouseWriter writer = null;
    switch (this) {
      case XML -> writer = (WarehouseWriter) new WarehouseWriterXML(fileName);
      case JSON -> writer = (WarehouseWriter) new WarehouseWriterJSON(fileName);
    }
    return writer;
  }

  WarehouseReader getWarehouseReader(String fileName) {
    WarehouseReader reader = null;
    switch (this) {
      case XML -> reader = (WarehouseReader) new WarehouseReaderXML(fileName);
      case JSON -> reader = (WarehouseReader) new WarehouseReaderJSON(fileName);
    }
    return reader;
  }

}
