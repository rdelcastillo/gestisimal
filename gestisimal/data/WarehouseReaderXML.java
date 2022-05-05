package gestisimal.data;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import gestisimal.business.Warehouse;
import gestisimal.business.WarehouseDuplicatedCodeException;
import gestisimal.business.WarehouseDuplicatedNameBrandException;
import static gestisimal.data.WarehouseXML.*;

/**
 * Esta clase será la encargada de leer el Almacén en XML.
 * 
 * @author Rafael del Castillo Gomariz
 *
 */

public class WarehouseReaderXML implements WarehouseReader {
  
  private String fileName;
  private Warehouse warehouse;

  public WarehouseReaderXML(String fileName) {
    this.setFileName(fileName);
  }
  
  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }


  @Override
  public Warehouse load(String fileName) throws WarehouseReaderException {
    warehouse = new Warehouse();
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(new File(fileName));  
      document.getDocumentElement().normalize();

      NodeList nodes = document.getElementsByTagName("Artículo");
      for (int i = 0; i < nodes.getLength(); i++) {
        addArticle(nodes.item(i));
      }
    } 
    catch (ParserConfigurationException | SAXException | NumberFormatException e) {
      throw new WarehouseReaderException("Error en el XML: " + e.getMessage());
    } 
    catch (IOException e) {
      throw new WarehouseReaderException("Error al leer del fichero: " + e.getMessage());
    } 
    catch (WarehouseDuplicatedNameBrandException | WarehouseDuplicatedCodeException e) {
      throw new WarehouseReaderException("Artículo duplicado en el XML: " + e.getMessage());
    } 
    return warehouse;
  }

  private void addArticle(Node item) throws NumberFormatException, WarehouseDuplicatedNameBrandException, WarehouseDuplicatedCodeException {
    Element article = (Element) item;
    warehouse.add(Integer.parseInt(article.getAttribute(CODE)), getElement(article, NAME), 
        getElement(article, BRAND), Integer.parseInt(getElement(article, UNITS)), 
        Double.parseDouble(getElement(article, PURCHASE_PRICE)),
        Double.parseDouble(getElement(article, SALE_PRICE)), 
        Integer.parseInt(getElement(article, SECURITY_STOCK)), 
        Integer.parseInt(getElement(article, MAX_STOCK)));
  }
  
  private String getElement(Element element, String name) {
    return element.getElementsByTagName(name).item(0).getTextContent();
  }

}
