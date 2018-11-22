package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import entry_objects.EmailEntry;
import entry_objects.InformationEntry;
import other.Service;
import other.XMLUserConfiguration;

/**
 * The Class ReadAndWriteFile.
 * @author Alexandre Mendes
 * @version 1.0
 */
public class ReadAndWriteFile { // 
	
	private static final String FOLDER_POSTS = "Posts/";
	
	
	/**
	 * Instantiates a new read and write file.
	 */
	public ReadAndWriteFile() {	
	}
	
	
	/**
	 * Save list of information entry.
	 *
	 * @param fileName the file name
	 * @param information_entry_list the information entry list
	 */
	public static void saveListOfInformationEntry(String fileName, List<InformationEntry> information_entry_list) {
		
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FOLDER_POSTS + fileName));) {
			out.writeObject(information_entry_list);
			out.flush();
		} catch (FileNotFoundException e) {
			System.err.println(e.getClass() + ": " + e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getClass() + ": " + e.getMessage());
		}
		
	}
	

	/**
	 * Load list of information entry.
	 *
	 * @param fileName the file name
	 * @return the list
	 */
	public static List<InformationEntry> loadListOfInformationEntry(String fileName) {
		ArrayList<InformationEntry> information_entry_list = new ArrayList<InformationEntry>();
		
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FOLDER_POSTS + fileName));) {
			information_entry_list = (ArrayList<InformationEntry>) in.readObject();
		} catch (FileNotFoundException e) {
			System.err.println(e.getClass() + ": " + e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getClass() + ": " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.err.println(e.getClass() + ": " + e.getMessage());			
		}
		
		return information_entry_list;
	}
	
	
	public static void saveListOfFilters(String fileName, List<String> filters) {
		
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));) {
			out.writeObject(filters);
			out.flush();
		} catch (FileNotFoundException e) {
			System.err.println(e.getClass() + ": " + e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getClass() + ": " + e.getMessage());
		}
	}
	
	public static List<String> loadListOfFilters(String fileName) {
		ArrayList<String> filters = null;
		
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));) {
			filters = (ArrayList<String>) in.readObject();
		} catch (FileNotFoundException e) {
			System.err.println(e.getClass() + ": " + e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getClass() + ": " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.err.println(e.getClass() + ": " + e.getMessage());			
		}
		
		return filters;
	}
	
	/*
	public static XMLUserConfiguration readUserXMLFile(String fileName) throws IOException {
		try (Scanner scanner = new Scanner(new File(fileName + ".xml"))) {
			String line = "";
			String[] lineArray = {};
			boolean informationSaved = false;
			String username = null;
			String password = null;
			XMLUserConfiguration user_config = null;
			while(scanner.hasNextLine()) {
				line = scanner.nextLine();
				lineArray = line.split(": ", 2);
				//if(line.startsWith("Save Information:")) {
				if(lineArray.length == 2 && lineArray[1] != null) {
					switch (lineArray[0]) {
					case "Save Information":
						if(lineArray[1] == "1") {
							informationSaved = true;
						}
						break;
						
					case "Username":
						username = lineArray[1];
						break;
						
					case "Password":
						password = lineArray[1];
						break;

					default:
						break;
					}
				}
				
			}
			if(username == null || password == null) {
				throw new IOException("The username or password isn't defined.");
			}
			user_config = new XMLUserConfiguration(informationSaved, username, password);
			return user_config;
		} catch (FileNotFoundException e) {
			System.out.println(fileName + ".xml not found.");
		}
		return null;
	}
	*/
	
	/*
	public static InformationEntry readInformationEntryFile(String folder, String fileName) { // provavelmente precisa de se mudar para BufferedReader
		try (Scanner scanner = new Scanner(new File(folder + "/" + fileName + ".txt"))) {
			String line = "";
			String[] lineArray = {};
			
			Date date = null;
			Service service = null;
			String writerName = null;
			String subject = null;
			String content = null;
			InformationEntry informationEntry = null;
			
			while(scanner.hasNextLine()) {
				line = scanner.nextLine();
				lineArray = line.split(": ", 2);
				//if(line.startsWith("Save Information:")) {
				if(lineArray.length == 2 && lineArray[1] != null) {
					switch (lineArray[0]) {
					case "Date":
						long millisecondsOfDate = Long.valueOf(lineArray[1]).longValue();
						date = new Date(millisecondsOfDate);
						break;
						
					case "Service":
						if(lineArray[1] == "Email") {
							service = Service.EMAIL;
						}
						if(lineArray[1] == "Facebook") {
							service = Service.FACEBOOK;
						}
						if(lineArray[1] == "Twitter") {
							service = Service.TWITTER;
						}
						break;
						
					case "WriterName":
						writerName = lineArray[1];
						break;
						
					case "Subject":
						subject = lineArray[1];
						break;
						
					case "Content":
						content = lineArray[1];
						while(scanner.hasNextLine()) { // ideia
							content += scanner.nextLine();
						}
						break;

					default:
						break;
					}
				}
				
			}
			//informationEntry = new InformationEntry(date, service, writerName, subject, content);
			return informationEntry;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<InformationEntry> getInformationEntryArrayFromFolder(String folderName) throws IOException, URISyntaxException {
		List<String> strlist = new ArrayList<String>();
		List<InformationEntry> informationEntryArray = new ArrayList<InformationEntry>();
		//Path path = Paths.get(ReadAndWriteFile.class.getResource(".").toURI());
		//System.out.println(path.getParent());
		System.out.println("Pasta: " + folderName);
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("./" + folderName);
		
		if(is == null)
			throw new IOException("Directoria invalida ou Directoria vazia");
		
		InputStreamReader in = new InputStreamReader(is);
		try(BufferedReader brdir = new BufferedReader(in);) {
			String fileStr = "";
			while((fileStr = brdir.readLine()) != null) {
				if(fileStr.endsWith(".txt")) {
					strlist.add(fileStr);
					System.out.println(fileStr);
				}
			}
			in.close();
			is.close();
		}
		for(String fileS : strlist) {
			InputStream fileStream = this.getClass().getClassLoader().getResourceAsStream(folderName + "/" + fileS);
			try {
				InputStreamReader fileInput = new InputStreamReader(fileStream, "UTF-8");
				
				String line = "";
				String[] lineArray = {};
				
				Date date = null;
				Service service = null;
				String writerName = null;
				String subject = null;
				StringBuilder content = new StringBuilder();
				
				try(BufferedReader brFile = new BufferedReader(fileInput)) {
					while(((line = brFile.readLine()) != null)) {
						lineArray = line.split(": ", 2);
						//if(line.startsWith("Save Information:")) {
						if(lineArray.length == 2 && lineArray[1] != null) {
							switch (lineArray[0]) {
							case "Date":
								long millisecondsOfDate = Long.valueOf(lineArray[1]).longValue();
								date = new Date(millisecondsOfDate);
								break;
								
							case "Service":
								if(lineArray[1] == "Email") {
									service = Service.EMAIL;
								}
								if(lineArray[1] == "Facebook") {
									service = Service.FACEBOOK;
								}
								if(lineArray[1] == "Twitter") {
									service = Service.TWITTER;
								}
								break;
								
							case "WriterName":
								writerName = lineArray[1];
								break;
								
							case "Subject":
								subject = lineArray[1];
								break;
								
							case "Content":
								content.append(lineArray[1] + System.getProperty("line.separator"));
								while(((line = brFile.readLine()) != null)) { // ideia
									content.append(line + System.getProperty("line.separator"));
								}
								break;

							default:
								break;
							}
						}
						
					}
					//informationEntryArray.add(new InformationEntry(date, service, writerName, subject, content.toString()));
					fileInput.close();
					fileStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return informationEntryArray;
	}
	*/
	
	/*
	// Requires work on to make it read all the files we will generate to display on the UI
	private void loadNewsToList() throws IOException {
		List<String> strlist = new ArrayList<String>();
		System.out.println("Pasta: " + folderName);
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("./" + folderName);
		
		if(is == null)
			throw new IOException("Directoria invalida");
		
		InputStreamReader in = new InputStreamReader(is);
		try(BufferedReader brdir = new BufferedReader(in);) {
		String fileStr = "";
		while((fileStr = brdir.readLine()) != null) {
			if(fileStr.endsWith(".txt")) {
				strlist.add(fileStr);
			}
		}
		in.close();
		is.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String titulo = "";
		for(String fileS : strlist) {
			InputStream fileStream = this.getClass().getClassLoader().getResourceAsStream(folderName + "/" + fileS);
			try {
				InputStreamReader fileInput = new InputStreamReader(fileStream, "UTF-8");
				StringBuilder contentInFile = new StringBuilder();
				try(BufferedReader brFile = new BufferedReader(fileInput)) {
					String fileContent = brFile.readLine();
					if(fileContent != null) {
						titulo = fileContent;
					}
					while(((fileContent = brFile.readLine()) != null)) {
						contentInFile.append(fileContent + "\n");
					}
					noticias.add(new Noticia(titulo, contentInFile.toString()));
					fileInput.close();
					fileStream.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
	*/
}
