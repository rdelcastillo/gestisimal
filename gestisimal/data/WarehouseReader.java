package gestisimal.data;

import gestisimal.business.Warehouse;

interface WarehouseReader {

  Warehouse load(String fileName) throws WarehouseReaderException;
}
