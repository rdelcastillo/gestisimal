package gestisimal.data;

import gestisimal.business.Warehouse;

public class WarehousePersistence {

  public static void save(Warehouse warehouse, String fileName, PersistenceType type)
      throws WarehouseWriterException {
    WarehouseWriter writer = type.getWarehouseWriter(fileName);
    writer.save(warehouse);
  }

  public static Warehouse load(String fileName, PersistenceType type)
      throws WarehouseReaderException {
    WarehouseReader reader = type.getWarehouseReader(fileName);
    return reader.load(fileName);
  }

}
