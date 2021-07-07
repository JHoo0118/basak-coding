package com.basakcoding.basak.web.admin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.basakcoding.basak.service.AdminDTO;
import com.basakcoding.basak.service.AdminService;
import com.basakcoding.basak.service.CategoryDTO;
import com.basakcoding.basak.service.CourseDTO;
import com.basakcoding.basak.service.CourseService;
import com.basakcoding.basak.util.FileUploadUtil;

@Controller
@RequestMapping("/admin/course")
public class AdminCourseController {
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private AdminService AdminService;
	
	@GetMapping("/management")
	public String categoryList(Model model) {
		List<Map<String, String>> listCourses = courseService.selectList();
		model.addAttribute("listCourses", listCourses);
		model.addAttribute("title", "강의 관리");
		return "admin/courseManagement";
	}
	
	// 강의 관리 폼 페이지
	@GetMapping("/management/form")
	public String memberForm(@RequestParam Map map, Model model) {
//		String memberId = map.get("memberId");
//		MemberDTO member = new MemberDTO();
//		if (memberId != null) {
//			member = memberService.getMemberById(memberId);
//		}
		
		List<CategoryDTO> listCategories = courseService.categoryList();
		List<AdminDTO> listAdmin = AdminService.getAdminList();
		Map course = new HashMap<>();
		model.addAttribute("listAdmin", listAdmin);
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("course", course);
		model.addAttribute("title", "강의 관리");
		return "admin/courseForm";
	}
	
	// 강의 등록
	@PostMapping("/save")
	public String createCourse(
			@RequestParam Map<String, Object> map, 
			@RequestParam Map<String, MultipartFile> multipartFiles,
			RedirectAttributes redirectAttributes) throws IOException {
		
		// 강의 등록 로직
		if (!multipartFiles.get("thumbnail").isEmpty()) {
			
			MultipartFile thumbnailMulti = multipartFiles.get("thumbnail");
			String thumbnailName = StringUtils.cleanPath(thumbnailMulti.getOriginalFilename());
			map.put("thumbnail", thumbnailName);
			courseService.createCourse(map);
			
			String uploadDir = "upload/course/" + map.get("courseId").toString() +"/thumbnail";
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, thumbnailName, thumbnailMulti);
			String courseId = map.get("courseId").toString();
			Map curriId = new HashMap();
			Map videoId = new HashMap();
			
			Map<String, HashMap> faq = new HashMap();
			Map<String, HashMap> curri = new HashMap();
			Map<String, HashMap> video = new HashMap();
			Map<String, HashMap> file = new HashMap();
			
			for (String key : map.keySet()) {
				String[] temp;
				if (key.startsWith("faq")) {
					temp = key.split("-");
					if (temp[0].equals("faqTitle")) {
						if (!faq.containsKey(temp[1]))
							faq.put(temp[1], new HashMap());
						faq.get(temp[1]).put("title", map.get(key));
					}
					else {
						if (!faq.containsKey(temp[1]))
							faq.put(temp[1], new HashMap());
						faq.get(temp[1]).put("content", map.get(key));
					}
				} else if (key.startsWith("curriculum")) {
					temp = key.split("-");
					if (temp[0].equals("curriculum")) {
						if (!curri.containsKey(temp[1]))
							curri.put(temp[1], new HashMap());
						curri.get(temp[1]).put("name", map.get(key));
					}
				}
			}
			
			// FAQ INSERT
			for (String key : faq.keySet()) {
				faq.get(key).put("courseId", courseId);
				courseService.createFaq(faq.get(key));
			}
			
			// Curriculum INSERT
			for (String key : curri.keySet()) {
				curri.get(key).put("courseId", courseId);
				courseService.createCurri(curri.get(key));
				curriId.put(key, curri.get(key).get("curriculumId"));
			}
			
			int totalVideoLength = 0;
			for (String key : map.keySet()) {
				String[] temp;
				if (key.startsWith("video")) {
					temp = key.split("-");
					if (temp[0].equals("videoTitle")) {
						// temp[1]은 커리큘럼 아이디 temp[2]는 video 아이디
						if (!video.containsKey(temp[2]))
							video.put(temp[2], new HashMap());
						video.get(temp[2]).put("videoTitle", map.get(key));
						video.get(temp[2]).put("curriculumId", curriId.get(temp[1]));
					} else if (temp[0].equals("videoContent")) {
						video.get(temp[2]).put("videoContent", map.get(key));
					} else if (temp[0].equals("videoLength")) {
						int videoLength = Integer.parseInt(map.get(key).toString());
						totalVideoLength += videoLength;
						video.get(temp[2]).put("videoLength", videoLength);
					}
				}
			}
			
			// 비디오 로컬 저장
			int videoCnt = 0;
			
			for (String key : multipartFiles.keySet()) {
				String[] temp;
				if (key.startsWith("video")) {
					temp = key.split("-");
					MultipartFile videoMulti = multipartFiles.get(key);
					String videoUri = StringUtils.cleanPath(videoMulti.getOriginalFilename());
					video.get(temp[2]).put("videoUri", videoUri);
					
					String uploadVideoDir = "upload/course/" + courseId +"/video";
					FileUploadUtil.saveFile(uploadVideoDir, videoUri, videoMulti);
				}
				
			}

			// Video INSERT
			for (String key : video.keySet()) {
				videoCnt++;
				courseService.createVideo(video.get(key));
				videoId.put(key, video.get(key).get("videoId"));
			}
			
			// 강의 비디오 총 개수 및 비디오 총 길이 업데이트
			Map updateForVideoCntAndLength = new HashMap();
			updateForVideoCntAndLength.put("totalVideoLength", totalVideoLength);
			updateForVideoCntAndLength.put("videoCnt", videoCnt);
			updateForVideoCntAndLength.put("courseId", courseId);
			courseService.updateVideoCntAndLength(updateForVideoCntAndLength);
			

			// 파일 로컬 저장
			for (String key : multipartFiles.keySet()) {
				String[] temp;
				if (key.startsWith("file-") && !key.startsWith("files")) {
					temp = key.split("-");
					MultipartFile fileMulti = multipartFiles.get(key);
					String fileUri = StringUtils.cleanPath(fileMulti.getOriginalFilename());
					System.out.println(key);
					if (!file.containsKey(temp[2]))
						file.put(temp[2], new HashMap());
					file.get(temp[2]).put("fileName", fileUri);
					file.get(temp[2]).put("fileUri", fileUri);
					file.get(temp[2]).put("videoId", videoId.get(temp[1]));
					
					String uploadFileDir = "upload/course/" + courseId +"/file";
					FileUploadUtil.saveFile(uploadFileDir, fileUri, fileMulti);
//					FileUploadUtil.saveFile(uploadFileDir, "copy-"+fileUri, fileMulti);
				}
			}
			
			// File INSERT
			for (String key : file.keySet()) {
				courseService.createFile(file.get(key));
			}
			
		}
		
//		System.out.println(map);
//		multipartFiles.values().stream().forEach(System.out::println);
//		multipartFiles.keySet().stream().forEach(System.out::println);
//		multipartFiles.entrySet()
//			.stream()
//			.forEach(System.out::println);
		return "redirect:/admin/course/management";
	}
	
	//강의 상세보기
	@RequestMapping("/management/view")
	public String courseView(@RequestParam Map map,Model model) {
		CourseDTO course = courseService.getCourseOne(map);
		model.addAttribute("course", course);
		return "admin/courseView";
	}
}//class
