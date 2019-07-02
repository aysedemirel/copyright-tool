/**
*
* YAZILIM ISMI: DENEME
* TARIH: 2/7/2019
* ACIKLAMA: 
* JDK: 1.8.0_121
**/
package deneme;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class mainProject {

	public static void main(String args[]) throws IOException {
		String projectPath = "\\Yeni klasör\\deneme\\src\\deneme\\";
		getFileInWorkspace(projectPath);
	}

	public static void getFileInWorkspace(String projectPath) throws IOException {
		Path dir = Paths.get(projectPath);
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.java")) {
			for (Path file : stream) {
				System.out.println(file.getFileName());
				if (file.getFileName().equals("mainProject.java"))
					continue;
				String fileName = projectPath + file.getFileName();
				writeFile(fileName);
			}
		}
	}

	public static void writeFile(String filePath) {
		String version = System.getProperty("java.version");
		String cpyWrite = "/**\n" + "*\n" + "* YAZILIM ISMI: DENEME\n" + "* TARIH: " + getDate(filePath) + "\n"
				+ "* ACIKLAMA: \n" + "* JDK: " + version + "\n" + "**/\n";
		try {
			File mFile = new File(filePath);
			FileInputStream fis = new FileInputStream(mFile);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String result = "";
			String line = "";
			while ((line = br.readLine()) != null) {
				if (line.equals("/**")) {
					return;
				}
				result = result + line;
				result += "\n";
			}

			result = cpyWrite + result;

			mFile.delete();
			@SuppressWarnings("resource")
			FileOutputStream fos = new FileOutputStream(mFile);
			fos.write(result.getBytes());
			fos.flush();
		} catch (IOException e) {

		}
	}

	public static String getDate(String filePath) {
		Path fileName = Paths.get(filePath);
		try {
			BasicFileAttributes attr = Files.readAttributes(fileName, BasicFileAttributes.class);
			Date creationDate = new Date(attr.creationTime().to(TimeUnit.MILLISECONDS));

			@SuppressWarnings("deprecation")
			String fileDate = creationDate.getDate() + "/" + (creationDate.getMonth() + 1) + "/"
					+ (creationDate.getYear() + 1900);
			return fileDate;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
}
