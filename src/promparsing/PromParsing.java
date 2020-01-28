package promparsing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

public class PromParsing {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        Product product = getProduct("https://prom.ua/p1065445612-besprovodnye-naushniki-airplus.html");
        System.out.println(product);
        product.writeToXML("C:\\Users\\USER\\Documents\\NetBeansProjects\\"
                                                     + "PromParsing\\prom products.xml");
    }
    
    // reading all information about the product from the url and returning an instance of Product class
    private static Product getProduct(String url) throws IOException{
        
        Document doc = Jsoup.connect(url).get();
        String productName = doc.getElementsByAttributeValue("class", "x-title").get(0).text(); 
        Elements priceElement = doc.getElementsByAttributeValue("class", "x-product-price__value");
        Elements oldPriceElement = doc.getElementsByAttributeValue("class", "x-product-price__discount-value");
        Elements deliveryServiceElements = doc.getElementsByAttributeValue("class", "x-emoji-label__text");
        Elements discountInfoElements = doc.getElementsByAttributeValue("class", "x-product-price__discount-info");
        
        String price = priceElement.attr("data-qaprice");
        
        String oldPrice;
        if (oldPriceElement.attr("data-qaprice").equals("")) oldPrice = price;
        else oldPrice = oldPriceElement.attr("data-qaprice");
        
        String discoutnInfo;
        if (discountInfoElements.text().equals("")) discoutnInfo = "no discounts";
        else discoutnInfo = discountInfoElements.text();
        
        List<String> deliveryServices = new ArrayList();
        deliveryServiceElements.forEach((el) -> {
            if (!el.text().equals("Новинки")) deliveryServices.add(el.text());
        });
        
       return new Product(productName, deliveryServices, Float.parseFloat(price),  
               Float.parseFloat(oldPrice), discoutnInfo);
        
    }
}
