package org.dandelion.commons.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * @author lx6x
 * @date 2023/7/21
 */
public class BpmnXmlParser {

    public static void main(String[] args) {
        try {
            File xmlFile = new File("C:\\Users\\zk\\Desktop\\test\\example.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // 获取根元素
            Element definitionsElement = doc.getDocumentElement();

            // 解析 process 元素
            NodeList processList = definitionsElement.getElementsByTagName("bpmn2:process");
            if (processList.getLength() > 0) {
                Element processElement = (Element) processList.item(0);
                String processId = processElement.getAttribute("id");
                String processName = processElement.getAttribute("name");
                System.out.println("Process ID: " + processId);
                System.out.println("Process Name: " + processName);
            }

            // 解析 startEvent 元素
            NodeList startEventList = definitionsElement.getElementsByTagName("bpmn2:startEvent");
            if (startEventList.getLength() > 0) {
                Element startEventElement = (Element) startEventList.item(0);
                String startEventId = startEventElement.getAttribute("id");
                String startEventName = startEventElement.getAttribute("name");
                String formKey = startEventElement.getAttribute("flowable:formKey");
                System.out.println("Start Event ID: " + startEventId);
                System.out.println("Start Event Name: " + startEventName);
                System.out.println("Form Key: " + formKey);
            }

            // 解析 userTask 元素
            NodeList userTaskList = definitionsElement.getElementsByTagName("bpmn2:userTask");
            for (int i = 0; i < userTaskList.getLength(); i++) {
                Element userTaskElement = (Element) userTaskList.item(i);
                String userTaskId = userTaskElement.getAttribute("id");
                String userTaskName = userTaskElement.getAttribute("name");
                String dataType = userTaskElement.getAttribute("flowable:dataType");
                String candidateUsers = userTaskElement.getAttribute("flowable:candidateUsers");
                String text = userTaskElement.getAttribute("flowable:text");
                System.out.println("User Task ID: " + userTaskId);
                System.out.println("User Task Name: " + userTaskName);
                System.out.println("Data Type: " + dataType);
                System.out.println("Candidate Users: " + candidateUsers);
                System.out.println("Text: " + text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
