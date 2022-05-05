package gestisimal.data;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.gson.Gson;
import gestisimal.business.Warehouse;

/**
 * Esta clase será la encargada de escribir el almacén en JSON.
 * 
 * Aunque no lo hacemos, sería interesante encapsular la librería GSON.
 *  
 * @author Rafael del Castillo Gomariz
 *
 */

class WarehouseWriterJSON implements WarehouseWriter {

  private String fileName;

  void setFileName(String fileName) {
    this.fileName = fileName;
  }
  
  WarehouseWriterJSON(String fileName) {
    setFileName(fileName);
  }
  
  String getFileName() {
    return fileName;
  }

  @Override
  public void save(Warehouse warehouse) throws WarehouseWriterException {
    try {
      String json = new Gson().toJson(warehouse);
      Files.writeString(Paths.get(fileName), json, Charset.defaultCharset());
    } 
    catch (IOException e) {
      throw new WarehouseWriterException("Error al crear el fichero JSON.");
    }
  }

}
