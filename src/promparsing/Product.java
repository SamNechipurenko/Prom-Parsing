package promparsing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

class Product {
    private String pic, name, discountInfo;
    private List<String> deliveryServices;
    private float price, oldPrice;
    
    public Product(String name, List<String> deliveryServices,
                   float price, float oldPrice, String discountInfo) {
        this.name = name;
        this.deliveryServices  = deliveryServices;
        this.oldPrice = oldPrice;
        this.price = price;
        this.discountInfo = discountInfo;
    }

    public String getPic() {
        return pic;
    }

    public String getname() {
        return name;
    }

    public List<String> getDeliveryService() {
        return deliveryServices;
    }

    public float getOldPrice() {
        return oldPrice;
    }

    public String getDiscountInfo() {
        return discountInfo;
    }

    public float getPrice() {
        return price;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeliveryService(List<String> deliveryServices) {
        this.deliveryServices = deliveryServices;
    }

    public void setOldPrice(float oldPrice) {
        this.oldPrice = oldPrice;
    }

    public void setDiscountInfo(String discountInfo) {
        this.discountInfo = discountInfo;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "product name: " + name + "\n" + "option list: " + deliveryServices + "\n"
               + "price with discount: " + price + "\n" + "price without discount: " + oldPrice + "\n"
               + "discount info: " + discountInfo; 
    }
    
    public void writeToXML(String xmlPath) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException{
        
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document;
        Node productsNode;
        
        // check wherether XML file with the chosen path exist or not. if 
        // yes creating a new one, if no adding product info at the end
        if (! new File(xmlPath).exists()) {
            document = documentBuilder.newDocument();
            productsNode = document.createElement("products");
            document.appendChild(productsNode);
            System.out.println(productsNode.getNodeName());
        }else {
            document = documentBuilder.parse(xmlPath);
            // calling a root element ("products")
            productsNode = (Element) document.getFirstChild(); 
        }
        
        // creating a new brunch in XML file
        Element productNode = document.createElement("product");
        Element prodName = document.createElement("title");
        Element deliveryServises = document.createElement("deliveryServises");
        Element mPrice = document.createElement("price");
        Element mOldPrice = document.createElement("priceWithoutDiscount");
        Element discInfo = document.createElement("discountInfo");
        
        // setting propper tegs with values
        prodName.setTextContent(name);
        deliveryServises.setTextContent(deliveryServices.toString());
        mPrice.setTextContent(Float.toString(price));
        mOldPrice.setTextContent(Float.toString(oldPrice));
        discInfo.setTextContent(discountInfo);
        
        // adding to proper branches
        productNode.appendChild(prodName);
        productNode.appendChild(deliveryServises);
        productNode.appendChild(mPrice);
        productNode.appendChild(mOldPrice);
        productNode.appendChild(discInfo);
        productsNode.appendChild(productNode);
        
        // writing to XML
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new FileOutputStream(new File(xmlPath)));
        transformer.transform(domSource, streamResult);
    }
}

