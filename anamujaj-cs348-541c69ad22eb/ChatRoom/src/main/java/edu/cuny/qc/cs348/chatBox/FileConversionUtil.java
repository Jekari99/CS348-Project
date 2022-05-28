package edu.cuny.qc.cs348.chatBox;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;

public class FileConversionUtil {

	public static String fileToBase64(File file) {
		try {
			byte[] bytes = FileUtils.readFileToByteArray(file);
			String bytesStr = Base64.getEncoder().encodeToString(bytes);
			return bytesStr;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static File base64ToFile(Message message) {
		String base64content = message.getAttachment();
		byte[] bytes = Base64.getDecoder().decode(base64content);
		try {
			File file = new File("/files/" + message.getAttachmentName());
			FileUtils.writeByteArrayToFile(file, bytes);
			return file;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

		// writeByteArrayToFile
	}
}
