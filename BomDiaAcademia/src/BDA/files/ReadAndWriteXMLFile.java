package BDA.files;

import java.io.File;
import java.util.ArrayList;
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

import BDA.email.EmailConnection;
import BDA.facebook.FacebookConnection;
import BDA.interfaces.ServiceInstance;
import BDA.other.Service;
import BDA.other.XMLUserConfiguration;
import BDA.twitter.TwitterConnection;


// TODO: Auto-generated Javadoc
/**
 * The Class ReadAndWriteXMLFile.
 * @author Alexandre Mendes
 * @version 1.0
 */
public class ReadAndWriteXMLFile { //
	
	/** The Constant CONFIG_FILE_NAME. */
	private final static String CONFIG_FILE_NAME = "config.xml";
	
	/** The Constant TWITTER_CONFIG. */
	private final static String TWITTER_CONFIG = "twitter.xml";
	
	/**
	 * Creates the config XML file.
	 *
	 * @param user_config_list the user config list
	 * @throws Exception the exception
	 */
	public synchronized static void CreateConfigXMLFile(List<XMLUserConfiguration> user_config_list) throws Exception {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		
		Element configurationList = document.createElement("XMLUserConfigurationList");
		document.appendChild(configurationList);
		
		for(int i = 0 ; i < user_config_list.size() ; i++) { 
			Element element = document.createElement("XMLUserConfiguration");
			configurationList.appendChild(element);
			
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
			
			Element twitterConsumerKey = document.createElement("TwitterConsumerKey");
			twitterConsumerKey.appendChild(document.createTextNode(user_config_list.get(i).getTwitterConsumerKey()));
			element.appendChild(twitterConsumerKey);
			
			Element twitterSecretKey = document.createElement("TwitterSecretKey");
			twitterSecretKey.appendChild(document.createTextNode(user_config_list.get(i).getTwitterSecretKey()));
			element.appendChild(twitterSecretKey);
			
			Element twitterAccessToken = document.createElement("TwitterAccessToken");
			twitterAccessToken.appendChild(document.createTextNode(user_config_list.get(i).getTwitterAccessToken()));
			element.appendChild(twitterAccessToken);
			
			Element twitterAccessTokenSecret = document.createElement("TwitterAccessTokenSecret");
			twitterAccessTokenSecret.appendChild(document.createTextNode(user_config_list.get(i).getTwitterAccessTokenSecret()));
			element.appendChild(twitterAccessTokenSecret);	
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		
		StreamResult streamResult = new StreamResult(new File(CONFIG_FILE_NAME));
		
		transformer.transform(source, streamResult);
	}
	
	/*
	public static void CreateInformationEntryXMLFile(List<InformationEntry> information_entry_list) throws Exception { // ainda n�o foi testado
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		
		Document document = documentBuilder.newDocument();
		
		for(int i = 0 ; i < information_entry_list.size() ; i++) {
			//Element element = document.createElement("XMLUserConfiguration");
			Element element = document.createElement("InformationEntry");
			document.appendChild(element); // contem um problema que n�o sei resolver
			
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
			element.appendChild(subject);
			
			/*Element content = document.createElement("Content");
			content.appendChild(document.createTextNode(information_entry_list.get(i).getContent()));
			element.appendChild(content);
			
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		
		StreamResult streamResult = new StreamResult(new File("posts.xml"));
		
		transformer.transform(source, streamResult);
		
	}
	*/
	
	/**
	 * Read config XML file.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	public synchronized static List<XMLUserConfiguration> ReadConfigXMLFile() throws Exception {
		File xmlFile = new File(CONFIG_FILE_NAME);
		List<XMLUserConfiguration> xml_user_config_array = new ArrayList<XMLUserConfiguration>();
		
		if(xmlFile.exists()) {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(xmlFile.toURI().toString());
		
			NodeList list = document.getElementsByTagName("XMLUserConfigurationList");
		
			boolean saveInformation;
			int typeOfService;
			String username; 
			String password;
			
			String twitterConsumerKey;
			String twitterSecretKey;
			String twitterAccessToken;
			String twitterAccessTokenSecret;
			
			XMLUserConfiguration xmlUserConfiguration;
		
			for(int i = 0 ; i < list.getLength() ; i++) {
				Node node = list.item(i);
			
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					
					NodeList listUserConfiguration = element.getElementsByTagName("XMLUserConfiguration");
					
					
					for(int x = 0 ; x < listUserConfiguration.getLength() ; x++) {
						Node nodeUserConfig = listUserConfiguration.item(x);
						
						if(node.getNodeType() == Node.ELEMENT_NODE) {
							Element elementUserConfig = (Element) nodeUserConfig;
							
							
							saveInformation = Boolean.parseBoolean(elementUserConfig.getElementsByTagName("SaveInformation").item(0).getTextContent());
							typeOfService = Integer.parseInt(elementUserConfig.getElementsByTagName("TypeOfService").item(0).getTextContent());
							username = elementUserConfig.getElementsByTagName("Username").item(0).getTextContent();
							password = elementUserConfig.getElementsByTagName("Password").item(0).getTextContent();
							
							
							if(typeOfService == Service.TWITTER.getTypeOfServiceNumber()) {
								twitterConsumerKey = elementUserConfig.getElementsByTagName("TwitterConsumerKey").item(0).getTextContent();
								twitterSecretKey = elementUserConfig.getElementsByTagName("TwitterSecretKey").item(0).getTextContent();
								twitterAccessToken = elementUserConfig.getElementsByTagName("TwitterAccessToken").item(0).getTextContent();
								twitterAccessTokenSecret = elementUserConfig.getElementsByTagName("TwitterAccessTokenSecret").item(0).getTextContent();
								
								
								xmlUserConfiguration = new XMLUserConfiguration(saveInformation, typeOfService, twitterConsumerKey, twitterSecretKey, twitterAccessToken, twitterAccessTokenSecret);
							} else {
								xmlUserConfiguration = new XMLUserConfiguration(saveInformation, typeOfService, username, password);
							}
							
						
//							System.out.println("ID: " + element.getAttribute("Id"));
						
							xml_user_config_array.add(xmlUserConfiguration);
						}
						//System.out.println(xml_user_config_array);
					}
				}
			}
		}
		
		return xml_user_config_array;
	}
	
	public static void addServiceInstanceToXMLFile(ServiceInstance serviceInstance) throws Exception {
		if(serviceInstance != null) {
			List<XMLUserConfiguration> xml_user_configuration_list = ReadConfigXMLFile();
			XMLUserConfiguration xml_user_configuration = null;
			if(xml_user_configuration_list != null) {
				switch (serviceInstance.getService()) {
					case EMAIL:
						EmailConnection email_connection = (EmailConnection) serviceInstance;
						xml_user_configuration = new XMLUserConfiguration(true, Service.EMAIL, email_connection.getUsername(), email_connection.getPassword());
						break;
				
					case TWITTER: // da forma como esta implementado se chamarmos isto so irá trazer duplicados
						TwitterConnection twitter_functions = (TwitterConnection) serviceInstance;
						String[] keys = TwitterConnection.getKeys();
						xml_user_configuration = new XMLUserConfiguration(true, Service.TWITTER, keys[0], keys[1], keys[2], keys[3]); // nao tenho a certeza se esta bem
						break;
					
					case FACEBOOK: // da forma como esta implementado se chamarmos isto so irá trazer duplicados
						FacebookConnection facebook_connection = (FacebookConnection) serviceInstance;
						xml_user_configuration = new XMLUserConfiguration(true, Service.FACEBOOK, FacebookConnection.getAccessToken2()); // isto esta feito para coisas estaticas apenas
						break;
	
					default:
						break;
				}
				xml_user_configuration_list.add(xml_user_configuration);
				CreateConfigXMLFile(xml_user_configuration_list);
			}
		}
	}
	
	public static void addServiceInstanceToXMLFile(List<ServiceInstance> serviceInstances) throws Exception {
		List<XMLUserConfiguration> xml_user_configuration_list = ReadConfigXMLFile();
		XMLUserConfiguration xml_user_configuration = null;
		if(xml_user_configuration_list != null) {
			for(ServiceInstance serviceInstance : serviceInstances) {
				switch (serviceInstance.getService()) {
					case EMAIL:
						EmailConnection email_connection = (EmailConnection) serviceInstance;
						xml_user_configuration = new XMLUserConfiguration(true, Service.EMAIL, email_connection.getUsername(), email_connection.getPassword());
						break;
				
					case TWITTER: // da forma como esta implementado se chamarmos isto so irá trazer duplicados
						TwitterConnection twitter_functions = (TwitterConnection) serviceInstance;
						String[] keys = TwitterConnection.getKeys();
						xml_user_configuration = new XMLUserConfiguration(true, Service.TWITTER, keys[0], keys[1], keys[2], keys[3]); // nao tenho a certeza se esta bem
						break;
					
					case FACEBOOK: // da forma como esta implementado se chamarmos isto so irá trazer duplicados
						FacebookConnection facebook_connection = (FacebookConnection) serviceInstance;
						xml_user_configuration = new XMLUserConfiguration(true, Service.FACEBOOK, FacebookConnection.getAccessToken2()); // isto esta feito para coisas estaticas apenas
						break;
	
					default:
						break;
				}
				xml_user_configuration_list.add(xml_user_configuration);
			}
			CreateConfigXMLFile(xml_user_configuration_list);
		}
	}
	
	public static void removeServiceInstanceFromXMLFile(ServiceInstance serviceInstance) throws Exception {
		List<XMLUserConfiguration> xml_user_configuration_list = ReadConfigXMLFile();
		List<XMLUserConfiguration> to_remove = new ArrayList<XMLUserConfiguration>();
		if(xml_user_configuration_list != null) {
			switch (serviceInstance.getService()) {
				case EMAIL:
					EmailConnection email_connection = (EmailConnection) serviceInstance;
					for(XMLUserConfiguration xml_user : xml_user_configuration_list) {
						if(xml_user.getService() == Service.EMAIL && xml_user.getUsername().equals(email_connection.getUsername()) && xml_user.getPassword().equals(email_connection.getPassword())) {
							to_remove.add(xml_user);
							break;
						}
					}
					break;
			
				case TWITTER: // da forma como esta implementado se chamarmos isto so irá trazer duplicados
					TwitterConnection twitter_functions = (TwitterConnection) serviceInstance;
					String[] keys = TwitterConnection.getKeys();
					for(XMLUserConfiguration xml_user : xml_user_configuration_list) {
						if(xml_user.getService() == Service.TWITTER && xml_user.getTwitterConsumerKey().equals(keys[0]) && xml_user.getTwitterSecretKey().equals(keys[1]) 
								&& xml_user.getTwitterAccessToken().equals(keys[2]) && xml_user.getTwitterAccessTokenSecret().equals(keys[3])) {
							to_remove.add(xml_user);
							break;
						}
					}
					break;
				
				case FACEBOOK: // da forma como esta implementado se chamarmos isto so irá trazer duplicados
					FacebookConnection facebook_connection = (FacebookConnection) serviceInstance;
					for(XMLUserConfiguration xml_user : xml_user_configuration_list) {
						if(xml_user.getService() == Service.FACEBOOK && xml_user.getAccessToken2().equals(FacebookConnection.getAccessToken2())) {
							to_remove.add(xml_user);
							break;
						}
					}
					break;
	
				default:
					break;
			}
			xml_user_configuration_list.removeAll(to_remove);
			CreateConfigXMLFile(xml_user_configuration_list);
		}
	}
	
	public static void removeServiceInstanceFromXMLFile(List<ServiceInstance> serviceInstances) throws Exception {
		List<XMLUserConfiguration> xml_user_configuration_list = ReadConfigXMLFile();
		List<XMLUserConfiguration> to_remove = new ArrayList<XMLUserConfiguration>();
		if(xml_user_configuration_list != null) {
			for(ServiceInstance serviceInstance : serviceInstances) {
				switch (serviceInstance.getService()) {
					case EMAIL:
						EmailConnection email_connection = (EmailConnection) serviceInstance;
						for(XMLUserConfiguration xml_user : xml_user_configuration_list) {
							if(xml_user.getService() == Service.EMAIL && xml_user.getUsername().equals(email_connection.getUsername()) && xml_user.getPassword().equals(email_connection.getPassword())) {
								to_remove.add(xml_user);
								break;
							}
						}
						break;
				
					case TWITTER: // da forma como esta implementado se chamarmos isto so irá trazer duplicados
						TwitterConnection twitter_functions = (TwitterConnection) serviceInstance;
						String[] keys = TwitterConnection.getKeys();
						for(XMLUserConfiguration xml_user : xml_user_configuration_list) {
							if(xml_user.getService() == Service.TWITTER && xml_user.getTwitterConsumerKey().equals(keys[0]) && xml_user.getTwitterSecretKey().equals(keys[1]) 
									&& xml_user.getTwitterAccessToken().equals(keys[2]) && xml_user.getTwitterAccessTokenSecret().equals(keys[3])) {
								to_remove.add(xml_user);
								break;
							}
						}
						break;
					
					case FACEBOOK: // da forma como esta implementado se chamarmos isto so irá trazer duplicados
						FacebookConnection facebook_connection = (FacebookConnection) serviceInstance;
						for(XMLUserConfiguration xml_user : xml_user_configuration_list) {
							if(xml_user.getService() == Service.FACEBOOK && xml_user.getAccessToken2().equals(FacebookConnection.getAccessToken2())) {
								to_remove.add(xml_user);
								break;
							}
						}
						break;
		
					default:
						break;
				}
				xml_user_configuration_list.removeAll(to_remove);
			}
			CreateConfigXMLFile(xml_user_configuration_list);
		}
	}
	
	
	
	/**
	 * Gets the twitter users.
	 *
	 * @return the twitter users
	 * @throws Exception the exception
	 */
	public static List<String> getTwitterUsers() throws Exception {
		File xmlFile = new File(TWITTER_CONFIG);
		List<String> users = new ArrayList<>();
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document;
		
		if(xmlFile.exists()) {
			document = documentBuilder.parse(xmlFile.toURI().toString());
			NodeList list = document.getElementsByTagName("TwitterUser");
		
			for(int i = 0 ; i < list.getLength() ; i++) {
				Node node = list.item(i);
			
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					
					users.add(element.getElementsByTagName("Username").item(0).getTextContent());
				}
			}
		} else {
			document = documentBuilder.newDocument();
			String usernames[] = {"iscteiul", "istar_iul"};
			Element configurationList = document.createElement("TwitterUserList");
			
			document.appendChild(configurationList);

			for(String username : usernames) { 
				Element element = document.createElement("TwitterUser");
				configurationList.appendChild(element);
				
				Element user = document.createElement("Username");
				user.appendChild(document.createTextNode(username));
				element.appendChild(user);
				
				users.add(username);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			
			StreamResult streamResult = new StreamResult(new File(TWITTER_CONFIG));
			
			transformer.transform(source, streamResult);
		}
		
		return users;
	}
	
	/*
	public static List<InformationEntry> ReadInformationEntryXMLFile() throws Exception { // ainda n�o foi testado // acho que n�o vai ser usado
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
