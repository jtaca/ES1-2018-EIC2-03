package files;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import entry_objects.InformationEntry;
import other.Service;
import other.XMLUserConfiguration;


public class ReadAndWriteXMLFile {
	
	private final static String CONFIG_FILE_NAME = "config.xml";
	
	public static void CreateConfigXMLFile(List<XMLUserConfiguration> user_config_list) throws Exception {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		
		Document document = documentBuilder.newDocument();
		
		for(int i = 0 ; i < user_config_list.size() ; i++) {
			Element element = document.createElement("XMLUserConfiguration");
			document.appendChild(element);
			
			Attr attr = document.createAttribute("Id");
			attr.setValue("" + i);
			element.setAttributeNode(attr);
			
			Element saveInformation = document.createElement("SaveInformation");
			saveInformation.appendChild(document.createTextNode("" + user_config_list.get(i).isInformationSaved()));
			element.appendChild(saveInformation);
			
			Element typeOfService = document.createElement("TypeOfService");
			typeOfService.appendChild(document.createTextNode("" + user_config_list.get(i).getTypeOfServiceNumber()));
			element.appendChild(typeOfService);
			
			Element username = document.createElement("Username");
			username.appendChild(document.createTextNode(user_config_list.get(i).getUsername()));
			element.appendChild(username);
			
			Element password = document.createElement("Password");
			password.appendChild(document.createTextNode(user_config_list.get(i).getPassword()));
			element.appendChild(password);
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		
		StreamResult streamResult = new StreamResult(new File(CONFIG_FILE_NAME));
		
		transformer.transform(source, streamResult);
		
	}
	
	public static void CreateInformationEntryXMLFile(List<InformationEntry> information_entry_list) throws Exception { // ainda não foi testado
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		
		Document document = documentBuilder.newDocument();
		
		for(int i = 0 ; i < information_entry_list.size() ; i++) {
			//Element element = document.createElement("XMLUserConfiguration");
			Element element = document.createElement("InformationEntry");
			document.appendChild(element); // contem um problema que não sei resolver
			
			Attr attr = document.createAttribute("Id");
			attr.setValue("" + i);
			element.setAttributeNode(attr);
			
			/*Element date = document.createElement("Date");
			date.appendChild(document.createTextNode("" + information_entry_list.get(i).getDate().getTime()));
			element.appendChild(date);
			
			Element service = document.createElement("Service");
			service.appendChild(document.createTextNode("" + information_entry_list.get(i).getService().stringFormat()));
			element.appendChild(service);
			
			Element writerName = document.createElement("WriterName");
			writerName.appendChild(document.createTextNode(information_entry_list.get(i).getWriterName()));
			element.appendChild(writerName);
			
			Element subject = document.createElement("Subject");
			subject.appendChild(document.createTextNode(information_entry_list.get(i).getSubject()));
			element.appendChild(subject);*/
			
			/*Element content = document.createElement("Content");
			content.appendChild(document.createTextNode(information_entry_list.get(i).getContent()));
			element.appendChild(content);*/
			
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		
		StreamResult streamResult = new StreamResult(new File("posts.xml"));
		
		transformer.transform(source, streamResult);
		
	}
	
	public static List<XMLUserConfiguration> ReadConfigXMLFile() throws Exception {
		File xmlFile = new File(CONFIG_FILE_NAME);
		List<XMLUserConfiguration> xml_user_config_array = new ArrayList<XMLUserConfiguration>();
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);
		
		NodeList list = document.getElementsByTagName("XMLUserConfiguration");
		
		boolean saveInformation;
		int typeOfService;
		String username;
		String password;
		
		for(int i = 0 ; i < list.getLength() ; i++) {
			Node node = list.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				
				saveInformation = Boolean.parseBoolean(element.getElementsByTagName("SaveInformation").item(0).getTextContent());
				typeOfService = Integer.parseInt(element.getElementsByTagName("TypeOfService").item(0).getTextContent());
				username = element.getElementsByTagName("Username").item(0).getTextContent();
				password = element.getElementsByTagName("Password").item(0).getTextContent();
				
				System.out.println("ID: " + element.getAttribute("Id"));
				
				xml_user_config_array.add(new XMLUserConfiguration(saveInformation, typeOfService, username, password));
				
			}
		}
		return xml_user_config_array;
	}
	
	/*
	public static List<InformationEntry> ReadInformationEntryXMLFile() throws Exception { // ainda não foi testado // acho que não vai ser usado
		File xmlFile = new File("posts.xml");
		List<InformationEntry> information_entry_list = new ArrayList<InformationEntry>();
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);
		
		NodeList list = document.getElementsByTagName("InformationEntry");
		
		Date date;
		Service service;
		String writerName;
		String subject;
		String content;
		
		for(int i = 0 ; i < list.getLength() ; i++) {
			Node node = list.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				
				date = new Date(Long.valueOf(element.getElementsByTagName("Date").item(0).getTextContent()).longValue());
				switch (element.getElementsByTagName("TypeOfService").item(0).getTextContent()) {
				case "Email":
					service = Service.EMAIL;
					break;
					
				case "Facebook":
					service = Service.FACEBOOK;
					break;
					
				case "Twitter":
					service = Service.TWITTER;
					break;

				default:
					service = null;
					break;
				}
				
				writerName = element.getElementsByTagName("WriterName").item(0).getTextContent();
				subject = element.getElementsByTagName("Subject").item(0).getTextContent();
				content = element.getElementsByTagName("Content").item(0).getTextContent();
				
				System.out.println("ID: " + element.getAttribute("Id"));
				
				//information_entry_list.add(new InformationEntry(date, service, writerName, subject, content));
				
			}
		}
		return information_entry_list;
	}
	*/
	
}
