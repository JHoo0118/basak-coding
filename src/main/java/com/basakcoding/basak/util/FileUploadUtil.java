package com.basakcoding.basak.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	// 파일 업로드
	public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(uploadDir);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			throw new IOException("파일" +fileName+"을 저장할 수 없습니다.");
		}
		
	}
	
	// 디렉토리 안의 파일만 삭제
	public static void cleanDir(String dir) throws IOException {
		Path dirPath = Paths.get(dir);
		if (!Files.exists(dirPath)) {
			return;
		}
		try {
			Files.list(dirPath).forEach(file -> {
				if (!Files.isDirectory(file)) {
					try {
						Files.delete(file);						
					} catch (IOException ex) {
						System.out.println("파일"+file+"을 삭제할 수 없습니다.");
					}
				}
			});
		} catch (IOException ex) {
			return;
//			System.out.println("디렉토리를 찾을 수 없습니다:"+dirPath);
		}
	}
	
	// 디렉토리 삭제
	public static void deleteDir(String dir) throws IOException {
		cleanDir(dir);
		if (!Files.exists(Paths.get(dir))) {
			return;
		}
		Files.delete(Paths.get(dir));
	}
}
