package gestisimal.presentation;

import gestisimal.business.Article;
import gestisimal.business.ArticleIllegalErrorArgumentException;
import gestisimal.business.ArticleStockException;
import gestisimal.business.Warehouse;
import gestisimal.business.WarehouseDuplicatedNameBrandException;
import gestisimal.business.WarehouseIllegalArgumentException;
import gestisimal.data.WarehouseReaderException;
import gestisimal.data.WarehouseWriterException;
import gestisimal.business.WarehouseArticleNotExistsException;
import util.Menu;
import util.Util;
import static util.Keyboard.*;

/**
 * Programa para probar la clase Warehouse.
 * 
 * @author Rafael del Castillo Gomariz
 *
 */

public class TestWarehouse {
  
  private static Warehouse warehouse = new Warehouse();

  public static void main(String[] args) {
    Menu menu = createMenu();    
    fillWarehouse();  // valores de prueba

    int option;
    do {
      option = menu.choose();
      switch (option) {
        case 1 -> showWarehouse();
        case 2 -> addArticle();
        case 3 -> removeArticle();
        case 4 -> modifyArticle();
        case 5 -> increaseStock();
        case 6 -> decreaseStock();
        case 7 -> saveWarehouse();
        case 8 -> loadWarehouse();
      }
    } while (option != menu.lastOption());

    System.out.println("¡Hasta la próxima! ;-)");
  }

  private static Menu createMenu() {
    return new Menu("\nMenú de opciones",
        "Listado", "Alta de artículo", "Baja de artículo", "Modificación de artículo",
        "Entrada de mercancía", "Salida de mercancía", "Guardar el almacén", "Cargar un almacén", 
        "Terminar");
  }

  private static void fillWarehouse() {
    for (int i = 1; i <= 5; i++) {
      try {
        warehouse.add("Artículo" + i, "Marca" + i, Util.randomInt(1, 100), 
            Util.randomInt(10, 100), Util.randomInt(50, 500));
      } 
      catch (WarehouseDuplicatedNameBrandException e) {
        e.printStackTrace();
      }
    }
  }

  private static void showWarehouse() {
    for (Article article: warehouse) {
      showArticle(article);
      System.out.println("---");
    }
  }

  private static void showArticle(Article article) {
    System.out.println("Artículo código: " + article.getCode());
    System.out.println("Nombre: " + article.getName());
    System.out.println("Marca: " + article.getBrand());
    System.out.println("Unidades: " + article.getUnits());
    System.out.println("Precio de compra: " + String.format("%.2f€", article.getPurchasePrice()));
    System.out.println("Precio de venta: " + String.format("%.2f€", article.getSalePrice()));
    System.out.println("Stock de seguridad: " + article.getSecurityStock());
    System.out.println("Stock máximo: " + article.getMaxStock());
  }

  private static void addArticle() {
    try {
      warehouse.add(readStr("Nombre de artículo a dar de alta"), readStr("Marca"), 
          readInt("Unidades"), readDouble("Precio compra"), readDouble("Precio venta"), 
          readInt("Stock de seguridad"), readInt("Stock máximo"));
    } 
    catch (WarehouseDuplicatedNameBrandException e) {
      System.err.println("ERROR: El artículo que has intentado añadir ya existe en el almacén.");
    } 
    catch (ArticleIllegalErrorArgumentException e) {
      System.err.println("ERROR: " + e.getMessage());
    }
  }

  private static void removeArticle() {
    try {
      warehouse.remove(readInt("Código de artículo a dar de baja"));
    } 
    catch (WarehouseArticleNotExistsException e) {
      printCodeError();
    } 
  }

  private static void modifyArticle() {
    try {
      Article article = warehouse.getArticle(readInt("Código de artículo a modificar"));
      warehouse.modify(article.getCode(), read("Nombre", article.getName()), 
          read("Marca", article.getBrand()), read("Unidades", article.getUnits()),
          read("Precio compra", article.getPurchasePrice()), 
          read("Precio venta", article.getSalePrice()), 
          read("Stock de seguridad", article.getSecurityStock()), 
          read("Stock máximo", article.getMaxStock())); 
    } 
    catch (WarehouseArticleNotExistsException e) {
      printCodeError();
    } 
    catch (WarehouseDuplicatedNameBrandException e) {
      System.err.println("ERROR: El nombre y marca de artículo ya existen en el almacén.");
    } 
    catch (ArticleIllegalErrorArgumentException e) {
      System.err.println("ERROR: " + e.getMessage());
    }
    catch (NumberFormatException e) {
      System.err.println("El formato numérico introducido es incorrecto");
    }
  }

  private static void increaseStock() {
    try {
      warehouse.increaseStock(readInt("Código del artículo del que entra mercancía"), readInt("Unidades"));
    } 
    catch (WarehouseArticleNotExistsException e) {
      printCodeError();
    }
    catch (ArticleIllegalErrorArgumentException e) {
      System.err.println("ERROR: " + e.getMessage());
    }
  }

  private static void decreaseStock() {
    try {
      warehouse.decreaseStock(readInt("Código del artículo del que sale mercancía"), 
          readInt("Unidades"));
    } 
    catch (WarehouseArticleNotExistsException e) {
      printCodeError();
    } catch (ArticleStockException e) {
      System.err.println("ERROR: No hay suficientes unidades en el almacén.");
    }
    catch (ArticleIllegalErrorArgumentException e) {
      System.err.println("ERROR: " + e.getMessage());
    }
  }
  
  private static void printCodeError() {
    System.err.println("ERROR: Ese código no corresponde a ningún artículo.");
  }
  
  private static void saveWarehouse() {
    try {
      warehouse.save(readStr("Fichero (con extensión) donde guardar el almacén"));
    } 
    catch (WarehouseWriterException | WarehouseIllegalArgumentException e) {
      System.err.println("ERROR: " + e.getMessage());
    }
  }
  
  private static void loadWarehouse() {
    try {
      warehouse = new Warehouse(readStr("Fichero (con extensión) de donde cargar el almacén"));
    } 
    catch (WarehouseReaderException | WarehouseIllegalArgumentException e) {
      System.err.println("ERROR: " + e.getMessage());
    }
  }
  
}
