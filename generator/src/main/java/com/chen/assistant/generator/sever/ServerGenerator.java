package com.chen.assistant.generator.sever;


import com.chen.assistant.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ServerGenerator {
    static boolean readOnly = false;
    static String vuePath = "admin/src/views/main/";
    static String serverPath = "[module]/src/main/java/com/jiawa/train/[module]/";
    static String pomPath = "generator/pom.xml";
    static String module = "";
     static {
         new File(serverPath).mkdirs();
     }

    public static void main(String[] args) throws Exception {
        SAXReader saxReader = new SAXReader();
        Map<String, String> map = new HashMap<String, String>();
        map.put("pom", "http://maven.apache.org/POM/4.0.0");
        saxReader.getDocumentFactory().setXPathNamespaceURIs(map);
        Document document = saxReader.read(pomPath);
        Node node = document.selectSingleNode("//pom:configurationFile");
        System.out.println(node.getText());
    }
}
