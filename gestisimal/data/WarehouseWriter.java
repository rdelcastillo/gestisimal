package gestisimal.data;

import gestisimal.business.Warehouse;

interface WarehouseWriter {
  
  void save(Warehouse warehouse) throws WarehouseWriterException;
  
}
