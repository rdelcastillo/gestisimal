package gestisimal.business;

import java.util.Objects;

/**
 * Clase Artículo que representa a los artículos de un almacén.   
 * <p>
 * Su estado será: código, nombre, marca, precio de compra y de venta, unidades (nunca negativas), 
 * stock de seguridad y stock máximo (si no proporcionamos los valores de stock valen cero, 
 * igualmente si no proporcionamos el del stock máximo).
 * <p>
 * El código se genera de forma automática en el constructor de forma que no haya dos artículos con 
 * el mismo código. No puede modificarse, el resto de las propiedades sí.
 * <p>
 * Los objetos de esta clase solo serán modificables desde la clase Almacén.
 *  
 * @author Rafael del Castillo Gomariz
 *
 */

public class Article implements Comparable<Article> {
  
  private static int lastCode = 0;
  
  /**
   * Modifica el último código asignado. 
   * 
   * @param code para evitar duplicados solo es válido si no es menor que el actual.
   */
  static int getLastCode() {
    return lastCode;
  }
  
  static void setLastCode(int code) {
    if (code < lastCode) {
      throw new ArticleIllegalErrorArgumentException("No se puede asignar el código " + code);
    }
    lastCode = code;
  }
  
  private int code;
  private String name;
  private String brand;
  private int units;
  private double purchasePrice;
  private double salePrice;
  private int securityStock;
  private int maxStock;
  
  /**
   * Crea un objeto asignándole el último código aumentado en uno. 
   * Este es el contructor principal, los demás llaman a este.
   * 
   * @throws ArticleIllegalErrorArgumentException si hay errores en los valores de los parámetros
   */
  Article(String name, String brand, int units, double purchasePrice, double salePrice, 
      int securityStock, int maxStock) {
    set(name, brand, units, purchasePrice, salePrice, securityStock, maxStock);
    code = ++lastCode;
  }
  
  /**
   * Crea un objeto asignándole el último código aumentado en uno con el stock máximo a 0.
   */
  Article(String name, String brand, int units, double purchasePrice, double salePrice, 
      int securityStock) {
    this(name, brand, units, purchasePrice, salePrice, securityStock, 0);
  }
  
  /**
   * Crea un objeto asignándole el último código aumentado en uno con el stock de seguridad y 
   * máximo a 0.
   */
  Article(String name, String brand, int units, double purchasePrice, double salePrice) {
    this(name, brand, units, purchasePrice, salePrice, 0, 0);
  }
  
  /**
   * Crea un objeto con el código pasado como parámetro.
   * 
   * @param code código del artículo a crear.
   */
  Article(int code) {   
    this.code = code;
  }
  
  // Getters (públicos)

  public int getCode() {
    return code;
  }

  public String getName() {
    return name;
  }
  
  public String getBrand() {
    return brand;
  }
  
  public int getUnits() {
    return units;
  }  

  public double getPurchasePrice() {
    return purchasePrice;
  }
  
  public double getSalePrice() {
    return salePrice;
  }
  
  public int getSecurityStock() {
    return securityStock;
  }
  
  public int getMaxStock() {
    return maxStock;
  }

  // Setters
  
  /**
   * @throws ArticleIllegalErrorArgumentException si hay errores en los valores de los parámetros
   */
  void set(String name, String brand, int units, double purchasePrice, double salePrice,
      int securityStock, int maxStock) {
    setName(name);
    setBrand(brand);
    setUnits(units);
    setPurchasePrice(purchasePrice);
    setSalePrice(salePrice);
    if (securityStock <= this.maxStock) {  // para evitar excepción si han cambiado los dos stock
      setSecurityStock(securityStock);
      setMaxStock(maxStock);  
    } else {
      setMaxStock(maxStock);
      setSecurityStock(securityStock);
    }
  }

  /**
   * @throws ArticleIllegalErrorArgumentException si el nombre está vacío.
   */
  void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new ArticleIllegalErrorArgumentException("El nombre del artículo no puede estar vacío");
    }
    this.name = name;
  }

  /**
   * @throws ArticleIllegalErrorArgumentException si la marca está vacía.
   */
  void setBrand(String brand) {
    if (brand == null || brand.isBlank()) {
      throw new ArticleIllegalErrorArgumentException("El nombre de la marca no puede estar vacío");
    }
    this.brand = brand;
  }

  /**
   * @throws ArticleIllegalErrorArgumentException si las unidades son negativas.
   */
  void setUnits(int units) {
    throwExceptionIfNegativeUnits(units);
    this.units = units;
  }

  /**
   * @throws ArticleIllegalErrorArgumentException si el precio de compra es negativo.
   */
  void setPurchasePrice(double purchasePrice) {
    if (purchasePrice < 0) {
      throw new ArticleIllegalErrorArgumentException("El precio de compra no puede ser negativo");
    }
    this.purchasePrice = purchasePrice;
  }

  /**
   * @throws ArticleIllegalErrorArgumentException si el precio de venta es negativo.
   */
  void setSalePrice(double salePrice) {
    if (salePrice < 0) {
      throw new ArticleIllegalErrorArgumentException("El precio de venta no puede ser negativo");
    }
    this.salePrice = salePrice;
  }

  /**
   * @throws ArticleIllegalErrorArgumentException si el stock de seguridad es negativo o superior al máximo.
   */
  void setSecurityStock(int securityStock) {
    if (securityStock < 0) {
      throw new ArticleIllegalErrorArgumentException("El stock de seguridad no puede ser negativo");
    }
    if (this.maxStock != 0 && this.maxStock < securityStock) {
      throw new ArticleIllegalErrorArgumentException("El stock de seguridad no puede ser superior al máximo");
    }
    this.securityStock = securityStock;
  }

  /**
   * @throws ArticleIllegalErrorArgumentException si el stock máximo es negativo o inferior al mínimo.
   * @param maxStock
   */
  void setMaxStock(int maxStock) {
    if (maxStock < 0) {
      throw new ArticleIllegalErrorArgumentException("El stock máximo no puede ser negativo");
    }
    if (maxStock != 0 && maxStock < this.securityStock) {
      throw new ArticleIllegalErrorArgumentException("El stock de seguridad no puede ser superior al máximo");
    }
    this.maxStock = maxStock;
  }
  
  /**
   * Incrementa las existencias del artículo.
   * @param units cantidad de artículos a incrementar.
   * @throws ArticleIllegalErrorArgumentException si las unidades son negativas.
   */
  void increase(int units) {
    throwExceptionIfNegativeUnits(units);
    this.units += units;
  }
  
  /**
   * Decrementa las existencias del artículo.
   * @param units cantidad de artículos a incrementar.
   * @throws ArticleIllegalErrorArgumentException si las unidades son negativas.
   * @throws ArticleStockException si el stock se quedase en negativo al retirar las unidades.
   */
  void decrease(int units) throws ArticleStockException {
    throwExceptionIfNegativeUnits(units);
    if (this.units - units < 0) {
      throw new ArticleStockException("No hay unidades suficientes para retirar " + units);
    }
    this.units -= units;
  }
  
  // Resto de métodos
  
  @Override
  public int hashCode() {
    return Objects.hash(code);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Article other = (Article) obj;
    return code == other.code;
  }

  @Override
  public String toString() {
    return "Artículo [código=" + code + ", nombre=" + name + ", marca=" + brand + ", unidades=" + units
        + ", precioCompra=" + purchasePrice + ", precioVenta=" + salePrice + ", stockSeguridad="
        + securityStock + ", stockMáximo=" + maxStock + "]";
  }
  
  private void throwExceptionIfNegativeUnits(int units) {
    if (units < 0) {
      throw new ArticleIllegalErrorArgumentException("Las unidades no puede ser menores que cero");
    }
  }

  @Override
  public int compareTo(Article o) {
    return code - o.code;
  }
  
}
