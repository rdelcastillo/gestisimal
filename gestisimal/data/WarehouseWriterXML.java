package gestisimal.data;

import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import gestisimal.business.Article;
import gestisimal.business.Warehouse;
import static gestisimal.data.WarehouseXML.*;

/**
 * Esta clase será la encargada de escribir el Almacén en XML.
 * 
 * @author Rafael del Castillo Gomariz
 *
 */

class WarehouseWriterXML implements WarehouseWriter {

  private String fileName;
  private Document document;

  WarehouseWriterXML(String fileName) {
    setFileName(fileName);
  }
  
  String getFileName() {
    return fileName;
  }

  void setFileName(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public void save(Warehouse warehouse) throws WarehouseWriterException {
    try {
      createDocument(warehouse);
      saveDocument();
    } 
    catch (ParserConfigurationException e) {
      throw new WarehouseWriterException("Error al crear el documento XML: " + e.getMessage());
    } 
    catch (TransformerException e) {
      throw new WarehouseWriterException("Error al crear el documento DOM: " + e.getMessage());
    } 
    catch (IOException e) {
      throw new WarehouseWriterException("Error al crear el fichero: " + e.getMessage());
    }
    
  }

  private void createDocument(Warehouse warehouse) throws ParserConfigurationException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    document = builder.newDocument(); 

    Element root = document.createElement(WAREHOUSE);
    document.appendChild(root);

    for (Article article : warehouse) {
      root.appendChild(newElementArticle(article));
    }
  } 

  private Element newElementArticle(Article article) {
    Element elementArt = document.createElement(ARTICLE);
    elementArt.setAttributeNode(newAttr(CODE, article.getCode()));
    elementArt.appendChild(newElement(NAME, article.getName()));
    elementArt.appendChild(newElement(BRAND, article.getBrand()));
    elementArt.appendChild(newElement(UNITS, String.valueOf(article.getUnits())));
    elementArt.appendChild(newElement(PURCHASE_PRICE, String.valueOf(article.getPurchasePrice())));
    elementArt.appendChild(newElement(SALE_PRICE, String.valueOf(article.getSalePrice())));
    elementArt.appendChild(newElement(SECURITY_STOCK, String.valueOf(article.getSecurityStock())));
    elementArt.appendChild(newElement(MAX_STOCK, String.valueOf(article.getMaxStock())));
    return elementArt;
  }

  private Attr newAttr(String name, int value) {
    Attr attr = document.createAttribute(name);
    attr.setValue(Integer.toString(value));
    return attr;
  }

  private Element newElement(String name, String value) {
    Element element = document.createElement(name);
    element.appendChild(document.createTextNode(value));
    return element;
  }

  private void saveDocument() throws TransformerException, IOException {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource source = new DOMSource(document);
    StreamResult result = new StreamResult(new FileWriter(fileName));
    transformer.transform(source, result);
  }

}
