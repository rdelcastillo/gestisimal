package gestisimal.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import gestisimal.data.PersistenceType;
import gestisimal.data.WarehousePersistence;
import gestisimal.data.WarehouseReaderException;
import gestisimal.data.WarehouseWriterException;

/**
 * Clase Almacén que realiza el alta, baja, modificación, entrada de mercancía (incrementa unidades)
 * y salida de mercancía (decrementa unidades).
 * <p>
 * El estado será un ArrayList de objetos de clase Artículo.
 * <p>
 * Su comportamiento será:
 * <ul>
 * <li>Añadir artículos (no puede haber dos artículos con el mismo nombre y marca).
 * <li>Eliminar artículos (por código o nombre y marca).
 * <li>Incrementar las existencias de un artículo (por código o nombre y marca).
 * <li>Decrementar las existencias de un artículo (nunca por debajo de cero).
 * <li>Devolver un artículo (para mostrarlo).
 * <li>Listar el almacén.
 * </ul>
 * 
 * @author Rafael del Castillo Gomariz
 *
 */

public class Warehouse implements Iterable<Article> {

  private List<Article> warehouse = new ArrayList<>();

  /**
   * Crea un almacén vacío.
   */
  public Warehouse() {}

  /**
   * Crea un almacén desde un archivo.
   * 
   * @param fileName nombre del archivo con los datos.
   * @param type tipo de fichero (XML, JSON, ...)
   * @throws WarehouseReaderException 
   */
  public Warehouse(String fileName, PersistenceType type) throws WarehouseReaderException {
    Warehouse obj = WarehousePersistence.load(fileName, type);
    warehouse = obj.warehouse;
    if (!warehouse.isEmpty()) {
      Article lastArticle = warehouse.get(warehouse.size() - 1);
      if (Article.getLastCode() < lastArticle.getCode()) {  // puede haber otros almacenes creados
        Article.setLastCode(lastArticle.getCode());
      }
    }
  }
  
  /**
   * Crea un almacén desde un archivo.
   * 
   * @param fileName nombre del archivo con los datos, de su extensión se deduce el tipo.
   * @throws WarehouseReaderException 
   */
  public Warehouse(String fileName) throws WarehouseReaderException {
    this(fileName, getPersistenceType(fileName));
  }

  /**
   * Da de alta un artículo en el almacén.
   * 
   * @throws WarehouseDuplicatedNameBrandException si el artículo existe.
   */
  public void add(String name, String brand, int units, double purchasePrice, double salePrice,
      int securityStock, int maxStock) throws WarehouseDuplicatedNameBrandException {
    if (exists(name, brand)) {
      throw new WarehouseDuplicatedNameBrandException(name + "-" + brand + " ya está en el almacén.");
    }
    warehouse.add(new Article(name, brand, units, purchasePrice, salePrice, securityStock, maxStock));
  }

  /**
   * Da de alta un artículo en el almacén con stock máximo a 0.
   * 
   * @throws WarehouseDuplicatedNameBrandException si el artículo existe.
   */
  public void add(String name, String brand, int units, double purchasePrice, double salePrice,
      int securityStock) throws WarehouseDuplicatedNameBrandException {
    add(name, brand, units, purchasePrice, salePrice, securityStock, 0);
  }

  /**
   * Da de alta un artículo en el almacén con stock de seguridad y máximo a 0.
   * 
   * @throws WarehouseDuplicatedNameBrandException si el artículo existe.
   */
  public void add(String name, String brand, int units, double purchasePrice, double salePrice)
      throws WarehouseDuplicatedNameBrandException {
    add(name, brand, units, purchasePrice, salePrice, 0, 0);
  }
  
  /**
   * Da de alta un artículo en el almacén con un código concreto. Es necesario para la persistencia.
   * 
   * @throws WarehouseDuplicatedNameBrandException si nombre y marca ya existen.
   * @throws WarehouseDuplicatedCodeException si el código de artículo ya existe.
   */
  public void add(int code, String name, String brand, int units, double purchasePrice, 
      double salePrice, int securityStock, int maxStock) 
          throws WarehouseDuplicatedNameBrandException, WarehouseDuplicatedCodeException {
    throwExceptionIfDuplicatedArticle(code, name, brand);
    Article article = new Article(code);
    article.set(name, brand, units, purchasePrice, salePrice, securityStock, maxStock);
    warehouse.add(article);
    Collections.sort(warehouse);
    if (code > Article.getLastCode()) {
      Article.setLastCode(code);
    }
  }

  private void throwExceptionIfDuplicatedArticle(int code, String name, String brand)
      throws WarehouseDuplicatedCodeException, WarehouseDuplicatedNameBrandException {
    if (exists(code)) {
      throw new WarehouseDuplicatedCodeException("El código " + code + " ya existe en el almacén.");
    }
    if (exists(name, brand)) {
      throw new WarehouseDuplicatedNameBrandException(name + "-" + brand + " ya está en el almacén.");
    }
  }

  /**
   * Devuelve <code>true</code> si el artículo de ese nombre y marca se encuentra en el almacén.
   * 
   * @return <code>true</code> si el artículo está en el almacén.
   */
  public boolean exists(String name, String brand) {
    for (Article article : warehouse) {
      if (article.getName().equals(name) && article.getBrand().equals(brand)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Devuelve <code>true</code> si el artículo de ese código se encuentra en el almacén.
   * 
   * @return <code>true</code> si el artículo está en el almacén.
   */
  public boolean exists(int code) {
    return warehouse.contains(new Article(code));
  }

  /**
   * Borra el artículo con el código pasado como parámetro.
   * 
   * @throws WarehouseArticleNotExistsException si el artículo no existe en el almacén.
   */
  public void remove(int code) throws WarehouseArticleNotExistsException {
    if (!warehouse.remove(new Article(code))) {
      throw new WarehouseArticleNotExistsException("Artículo con código " + code + " no está en el almacén.");
    }
  }

  /**
   * Borra el artículo con el nombre y marca pasado como parámetro.
   * 
   * @throws WarehouseArticleNotExistsException si el artículo no existe en el almacén.
   */
  public void remove(String name, String brand) throws WarehouseArticleNotExistsException {
    if (!warehouse.removeIf(art -> art.getName().equals(name) && art.getBrand().equals(brand))) {
      throw new WarehouseArticleNotExistsException(name + "-" + brand + " no está en el almacén.");
    }
  }

  /**
   * Incrementa las existencias del artículo con el código pasado como parámetro.
   * 
   * @param code código del artículo.
   * @param units unidades a incrementar.
   * @throws WarehouseArticleNotExistsException si el artículo no existe en el almacén.
   */
  public void increaseStock(int code, int units) throws WarehouseArticleNotExistsException {
    Article article = getArticle(code);
    article.increase(units);
  }

  /**
   * Incrementa las existencias del artículo con el nombre y marca pasados como parámetro.
   * 
   * @param name nombre del artículo.
   * @param brand marca del artículo.
   * @param units unidades a incrementar.
   * @throws WarehouseArticleNotExistsException si el artículo no existe en el almacén.
   */
  public void increaseStock(String name, String brand, int units)
      throws WarehouseArticleNotExistsException {
    Article article = getArticle(name, brand);
    increaseStock(article.getCode(), units);
  }

  /**
   * Decrementa las existencias del artículo con el código pasado como parámetro.
   * 
   * @param code código del artículo.
   * @param units unidades a incrementar.
   * @throws WarehouseArticleNotExistsException si el artículo no existe en el almacén.
   * @throws ArticleStockException si el stock se quedase en negativo al retirar las unidades.
   */
  public void decreaseStock(int code, int units)
      throws WarehouseArticleNotExistsException, ArticleStockException {
    Article article = getArticle(code);
    article.decrease(units);
  }

  /**
   * Decrementa las existencias del artículo con el nombre y marca pasados como parámetro.
   * 
   * @param name nombre del artículo.
   * @param brand marca del artículo.
   * @param units unidades a incrementar.
   * @throws WarehouseArticleNotExistsException si el artículo no existe en el almacén.
   * @throws ArticleStockException si el stock se quedase en negativo al retirar las unidades.
   */
  public void decreaseStock(String name, String brand, int units)
      throws WarehouseArticleNotExistsException, ArticleStockException {
    Article article = getArticle(name, brand);
    decreaseStock(article.getCode(), units);
  }
  
  /**
   * Devuelve el artículo con el código pasado como parámetro.
   * 
   * @param code código del artículo.
   * @return artículo con el código pasado como parámetro.
   * @throws WarehouseArticleNotExistsException si el artículo no existe en el almacén.
   */
  public Article getArticle(int code) throws WarehouseArticleNotExistsException {
    try {
      return warehouse.get(warehouse.indexOf(new Article(code)));
    } 
    catch (IndexOutOfBoundsException e) {
      throw new WarehouseArticleNotExistsException("Artículo con código " + code + " no está en el almacén.");
    }
  }

  /**
   * Devuelve el artículo con el nombre y marca pasados como parámetro.
   * 
   * @param name nombre del artículo.
   * @param brand marca del artículo.
   * @return artículo con el nombre y marca pasados como parámetros.
   * @throws WarehouseArticleNotExistsException si el artículo no existe en el almacén.
   */
  public Article getArticle(String name, String brand) throws WarehouseArticleNotExistsException {
    for (Article article : warehouse) {
      if (article.getName().equals(name) && article.getBrand().equals(brand)) {
        return article;
      }
    }
    throw new WarehouseArticleNotExistsException(name + "-" + brand + " no está en el almacén.");
  }

  /**
   * Modifica el artículo con el código enviado como parámetro.
   * 
   * @param code código del artículo a modificar.
   * @throws WarehouseArticleNotExistsException si no existe el código de artículo en el almacén.
   * @throws WarehouseDuplicatedNameBrandException si el nombre y la marca se duplican.
   */
  public void modify(int code, String name, String brand, int units, double purchasePrice,
      double salePrice, int securityStock, int maxStock)
          throws WarehouseArticleNotExistsException, WarehouseDuplicatedNameBrandException {
    Article article = getArticle(code);
    if ((!article.getName().equals(name) || !article.getBrand().equals(brand)) && exists(name, brand)) {
      throw new WarehouseDuplicatedNameBrandException("Ya existe un artículo con ese nombre y marca");
    }
    article.set(name, brand, units, purchasePrice, salePrice, securityStock, maxStock);
  }

  /**
   * Guarda la lista de artículos del almacén.
   * 
   * @param fileName fichero donde almacenar los datos.
   * @param type tipo de fichero (XML, JSON, ...)
   * @throws WarehouseWriterException
   */
  public void save(String fileName, PersistenceType type) throws WarehouseWriterException {
    WarehousePersistence.save(this, fileName, type);
  }

  /**
   * Guarda la lista de artículos del almacén.
   * 
   * @param fileName fichero donde guardar los datos, de su extensión se deduce el tipo.
   * @throws WarehouseWriterException
   */
  public void save(String fileName) throws WarehouseWriterException {
    save(fileName, getPersistenceType(fileName));
  }

  private static PersistenceType getPersistenceType(String fileName) {
    try {
      String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
      return PersistenceType.valueOf(fileExtension.toUpperCase());
    } 
    catch (IllegalArgumentException e) {
      throw new WarehouseIllegalArgumentException("No se puede deducir el tipo de fichero: " + fileName);
    }
  }

  @Override
  public String toString() {
    String toString = "Almacén {\n";
    for (Article article : warehouse) {
      toString += "- " + article + "\n";
    }
    toString += "}";
    return toString;
  }

  @Override
  public Iterator<Article> iterator() {
    return warehouse.iterator();
  }

}
