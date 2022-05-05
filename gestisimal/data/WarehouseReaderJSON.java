package gestisimal.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.gson.Gson;
import gestisimal.business.Warehouse;

/**
 * Esta clase será la encargada de leer el Almacén en JSON.
 * 
 * Aunque no lo hacemos, sería interesante encapsular la librería GSON.
 * 
 * @author Rafael del Castillo Gomariz
 *
 */

class WarehouseReaderJSON implements WarehouseReader {
  
  private String fileName;

  WarehouseReaderJSON(String fileName) {
    setFileName(fileName);
  }
  
  String getFileName() {
    return fileName;
  }

  void setFileName(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public Warehouse load(String fileName) throws WarehouseReaderException {
    Warehouse warehouse = null;
    try {
      String json = Files.readString(Paths.get(fileName));
      warehouse = new Gson().fromJson(json, Warehouse.class);
    } 
    catch (IOException e) {
      throw new WarehouseReaderException("Error al leer el fichero JSON.");
    }
    return warehouse;
  }

}
