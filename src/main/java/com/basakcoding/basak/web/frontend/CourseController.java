package com.basakcoding.basak.web.frontend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.basakcoding.basak.service.CourseService;
import com.basakcoding.basak.service.CurriculumDTO;
import com.basakcoding.basak.service.FileDTO;
import com.basakcoding.basak.service.VideoDTO;

@Controller
public class CourseController {
	
	@Autowired
	CourseService courseService;
	
	@Autowired
    ResourceLoader resourceLoader;
	@GetMapping("/class/{videoId}")
	public String course(@PathVariable String videoId, Model model, HttpServletRequest req) throws IOException {
		
		if (videoId.charAt(0) >= 'A')
			videoId = ""+7;
		// String videoId = ""+7;
		String courseId = courseService.getCourseId(videoId);
		VideoDTO video = courseService.getVideo(videoId);
		video.setCourseId(courseId);
		
		// 커리큘럼과 비디오 얻기
		List<CurriculumDTO> curriculumList = courseService.getCurriculumList(courseId);
		
		// 비디오 파일들 가져오기
		List<Map> fileList = courseService.getFileList(videoId);
		for (int i=0; i<fileList.size(); i++) {
			String filename = fileList.get(i).get("FILENAME").toString();
			String fileExtension = filename.substring(filename.lastIndexOf(".")+1, filename.length());
			fileList.get(i).put("FILE_EXTENSION", fileExtension);
			
			// courseId 받아와야함 input hidden으로
			
			if (i == 0) {
				String dirName = "upload/course/" + courseId + "/file/copy-" + filename;
				Path dir = Paths.get(dirName);
				String path = dir.toFile().getAbsolutePath();
				File file = new File(path);
				StringBuilder sb = new StringBuilder();
				
				if (file.exists()) {
					String data;
					BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
					while ((data=br.readLine()) != null) {
						sb.append(data);
						sb.append("\r\n");
					}
					br.close();
				} else {
					System.out.println("파일 없음");
				}
				
				if (fileExtension.equals("html"))
					fileList.get(0).put("INITIAL_CODE", sb.toString());
			}

			// fileList.get(i).put("FILE_CONTENT", sb.toString());
		}
		
		model.addAttribute("fileList", fileList);
		model.addAttribute("currVideo", video);
		model.addAttribute("curriculums", curriculumList);

		return "frontend/course";
	}
	
	@PostMapping("/class/initial-code")
	@ResponseBody
	public void initializeCode(@RequestParam Map<String, String> map) throws FileNotFoundException, IOException {
		String videoId = map.get("videoId");
		String filename = map.get("filename");
		String courseId = courseService.getCourseId(videoId);
		
		List<Map> fileList = courseService.getFileList(videoId);
		
		String copyFileDirName = "upload/course/" + courseId + "/file/copy-" + filename;
		String originFileDirName =  "upload/course/" + courseId + "/file/" + filename;
		
		Path copyFileDir = Paths.get(copyFileDirName);
		Path originFileDir = Paths.get(originFileDirName);
		
		String copyFilePath = copyFileDir.toFile().getAbsolutePath();
		String originFilePath = originFileDir.toFile().getAbsolutePath();
		
		File copyFile = new File(copyFilePath);
		File originFile = new File(originFilePath);
		
		// 현재 파일 내용 초기화
		new FileOutputStream(copyFile).close();
		
		BufferedReader br = new BufferedReader(new FileReader(originFile.getAbsolutePath()));
		BufferedWriter bw = new BufferedWriter(new FileWriter(copyFile.getAbsolutePath()));
		
		String data;
		StringBuilder sb = new StringBuilder();
		while ((data=br.readLine()) != null) {
			bw.write(data);
			bw.newLine();
			sb.append(data);
			sb.append("\r\n");
			bw.flush();
		}
		bw.close();
		br.close();
		
//		System.out.println(file);
	}
	
	@PostMapping("/class/autosave-code")
	@ResponseBody
	public String autoSave(@RequestParam Map<String, String> map) throws IOException {
		String courseId = courseService.getCourseId(map.get("videoId"));
		
		String currFilename = map.get("currFilename");
		String nextFilename = map.get("nextFilename");

		String currFileDirName = "upload/course/" + courseId + "/file/copy-" + currFilename;
		String nextFileDirName = "upload/course/" + courseId + "/file/copy-" + nextFilename;
		
		Path currFileDir = Paths.get(currFileDirName);
		Path nextFileDir = Paths.get(nextFileDirName);
		
		String currFilePath = currFileDir.toFile().getAbsolutePath();
		String nextFilePath = nextFileDir.toFile().getAbsolutePath();
		
		File currFile = new File(currFilePath);
		File nextFile = new File(nextFilePath);
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(currFile.getAbsolutePath()));
		bw.write(map.get("currFileContent"));
		
		StringBuilder nextFileContent = new StringBuilder();
		
		String data;
		BufferedReader br = new BufferedReader(new FileReader(nextFile.getAbsolutePath()));
		while ((data=br.readLine()) != null) {
			nextFileContent.append(data);
			nextFileContent.append("\r\n");
		}
		
		br.close();
		bw.close();
		return nextFileContent.toString();
	}
}
