/*
 * The MIT License
 *
 * Copyright 2016 PoVALE team.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package es.ucm.povale.views;

import es.ucm.povale.entity.Entity;
import es.ucm.povale.environment.Environment;
import es.ucm.povale.parameter.ParameterEditor;
import es.ucm.povale.variable.Var;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author PoVALE Team
 */
public class XMLExport {

    private Environment environment;
    private Document document;
    private Element rootElement;
    
    public XMLExport(Environment environment) {
        this.environment = environment;
    }
    
    public void export(){
        
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            this.document = dBuilder.newDocument();
            
            this.rootElement = document.createElement("assignment");
            document.appendChild(rootElement);
            
            //Element specName = document.createElement("specName");
            //specName.appendChild(document.createTextNode(environment.getValue("specName").toString()));
            
            List<Var> list = this.environment.getVariables().stream().collect(Collectors.toList());

            for (int i = 0; i < list.size(); i++) {
                Element variable = document.createElement("var");
                
                Element type = document.createElement("type");
                type.appendChild(document.createTextNode(list.get(i).getType()));
                
                Element name = document.createElement("name");
                name.appendChild(document.createTextNode(list.get(i).getName()));
                
                Element contents = document.createElement("contents");
                Entity e = environment.getValue(list.get(i).getName());
                e.toXML(contents, document);
                
                variable.appendChild(type);
                variable.appendChild(name);
                variable.appendChild(contents);
                this.rootElement.appendChild(variable);
            }
            
            //write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            StreamResult result =  new StreamResult(new File("spec8.xml"));
            
            transformer.transform(source, result);
            
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLExport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(XMLExport.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}
