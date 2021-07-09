function setErrorFor(input, message) {
	
	var parent = input.parentNode;
	var errorText = parent.querySelector('small');
	errorText.classList.add('error')
	errorText.innerText = message;
}

function categoryValidate(value, ele) {
	if (value === "") {
		setErrorFor(ele, "필수 입력 항목입니다.");
		return false;
	}
	return true;
}

function priceValidate(value) {
	if (value === "") {
		setErrorFor(courseForm.price.parentNode, "필수 입력 항목입니다.");
		return false;
	}
	return true;
}

function courseTitleValidate(value) {
	if (value === "") {
		setErrorFor(courseForm.courseTitle.parentNode, "필수 입력 항목입니다.");
		return false;
	}
	return true;
}

function shortDescriptionValidate(value) {
	if (value === "") {
		setErrorFor(courseForm.shortDescription.parentNode, "필수 입력 항목입니다.");
		return false;
	}
	return true;
}

Array.prototype.slice.call(courseForm).forEach(function(inputElement) {
	inputElement.addEventListener("blur", function(e) {
	
		if (e.target.name === 'categoryId')
			categoryValidate(courseForm.categoryId.options[courseForm.categoryId.selectedIndex].value, courseForm.categoryId);

		if (e.target.name === 'adminId')
			categoryValidate(courseForm.adminId.options[courseForm.adminId.selectedIndex].value, courseForm.adminId);
			
		if (e.target.name === 'difficulty')
			categoryValidate(courseForm.difficulty.options[courseForm.difficulty.selectedIndex].value, courseForm.difficulty);
	
		if(e.target.name === 'price') 
			priceValidate(e.target.value.trim(), courseForm.price.parentNode);
			
		if(e.target.name === 'courseTitle') 
			courseTitleValidate(e.target.value.trim(), courseForm.courseTitle.parentNode);
		
		if(e.target.name === 'shortDescription') 
			shortDescriptionValidate(e.target.value.trim(), courseForm.shortDescription.parentNode);
	});
	
	inputElement.addEventListener("input", function() {
		if (inputElement.type === 'checkbox') return;
	 	if (inputElement.name === 'thumbnail') return;
	 	if (inputElement.type === 'file') return;
	 
	 	if (inputElement.name.startsWith('videoTitle-') || inputElement.name.startsWith('curriculum-')) {
	 		$(this).closest(".tab-pane").children('small.error').text("")
	 		return;
	 	}
	 	
	 	if (inputElement.name.startsWith('faqTitle-') || inputElement.name.startsWith('faqContent-')) {
	 		$('#createCourseTwo').children('label.error').text("");
	 		return;
	 	}
	 	
		var parentElement = inputElement.parentElement;
		
		if (inputElement.name === 'price' || inputElement.name === 'courseTitle'
		|| inputElement.name === 'shortDescription') parentElement = parentElement.parentElement
		
		var errorText = parentElement.querySelector('small')
		console.log(parentElement)
		errorText.innerText = "";
	});
});


courseForm.addEventListener('submit', function(e) {
	if (!courseFormValidate(e)) {
		e.preventDefault();
		toastr.error('강의 등록에 실패했습니다. 다시 한 번 확인해주세요!', '강의 등록 오류');
	}
	return false;
});

function courseFormValidate() {
	var errorCheck = false;
	var currError = false;
	var faqError = false;
	
	$('#createCourseTwo').children('small.error').text("")
	
	if (!categoryValidate(courseForm.adminId.options[courseForm.adminId.selectedIndex].value, courseForm.adminId)) {
		errorCheck = true;
	}
	
	if (!categoryValidate(courseForm.categoryId.options[courseForm.categoryId.selectedIndex].value, courseForm.categoryId)) {
		errorCheck = true;
	}
	
	if (!categoryValidate(courseForm.difficulty.options[courseForm.difficulty.selectedIndex].value, courseForm.difficulty)) {
		errorCheck = true;
	}
	
	if (!priceValidate(courseForm.price.value.trim())) {
		errorCheck = true;
	}
	
	if (!thumbnailValidate()) {
		errorCheck = true;
	}
	
	if(!courseTitleValidate(courseForm.courseTitle.value.trim())) {
		errorCheck = true;
	}
	
	if(!shortDescriptionValidate(courseForm.shortDescription.value.trim())) {
		errorCheck = true;
	}
	
	if($('#summernote').summernote('isEmpty')) {
		toastr.error('강의 설명을 입력해주세요.', '강의 설명 필수 입력');
		errorCheck = true;
	}

	$('[id^=summernote-]').each(function() {
		if ($(this).summernote('isEmpty')) {
			toastr.error('비디오 설명을 입력해주세요.', '비디오 설명 필수 입력');
			errorCheck = true;
			currError = true;
		}
	})
	
	$('input[name^=curriculum-]').each(function() {
		if ($(this).val() === "") {
			errorCheck = true;
			currError = true;
		}
	})
	
	$('input[name^=videoTitle-]').each(function() {
		if ($(this).val() === "") {
			errorCheck = true;
			currError = true;
		}
	})
	
	
	$('input[name^=faqTitle-]').each(function() {
		if ($(this).val() === "") {
			errorCheck = true;
			currError = true;
		}
	})
	
	
	$('input[name^=video-]').each(function() {
		if($(this).get(0).files.length === 0) {
			errorCheck = true;
			currError = true;
		}
		
	});
	
	$('input[name^=file-]').each(function() {
		if($(this).get(0).files.length === 0) {
			errorCheck = true;
			currError = true;
		}
	});
	
	$('input[name^=faqTitle-]').each(function() {
		if ($(this).val() === "") {
			errorCheck = true;
			faqError = true;
		}
	})
	
	$('textarea[name^=faqContent-]').each(function() {
		if ($(this).val() === "") {
			errorCheck = true;
			faqError = true;
		}
	})
	
	if (currError) {
		$('#createCourseTwo').children('small.error').text("커리큘럼 항목을 모두 채워주세요.");
	}
	
	if (faqError) {
		$('#createCourseTwo').children('label.error').text("FAQ 항목을 모두 채워주세요.");
	}
	
	if (errorCheck) return false;
	return true;
}


// 썸네일
$(document).ready(function() {
	$("#thumbnail").change(function() {
		showImagePreview(this);
	});
});

// 썸네일 미리보기
function showImagePreview(fileInput) {
	var file = fileInput.files[0];
	if (!file) {
		$("#preview").css("display", "none");
		$(".temp-box").css("display", "block");
		$("#preview").attr("src", "");
		toastr.error('썸네일을 첨부해 주세요', '썸네일 오류');
		return;
	}
	var reader = new FileReader();
	reader.onload = function(e) {
		if (thumbnailValidate()) {
			$(".temp-box").css("display", "none");
			$("#preview").css("display", "block");
			$("#preview").attr("src", e.target.result);
		} 
	};
	if (thumbnailValidate()) {
		reader.readAsDataURL(file);
	} else {
		$("#preview").css("display", "none");
		$(".temp-box").css("display", "block");
	}
}

// 썸네일 유효성 검사
function thumbnailValidate() {
	var file = courseForm.thumbnail;
	if (file.files.length === 0 && !preview.getAttribute('src')) {
		preview.setAttribute('src', "");
		toastr.error('썸네일을 첨부해 주세요', '썸네일 오류');
		return false;
	}
	
	if (file.files.length > 0 && !file.files[0].type.startsWith('image')) {
		preview.setAttribute('src', "");
		toastr.error('이미지 파일만 업로드 해주세요.', '썸네일 오류');
		return false;
	}
	if (file.files.length > 0 && file.files[0].size > 1024*1024*2) {
		preview.setAttribute('src', "");
		toastr.error('최대 용량(2MB)를 초과했습니다.', '썸네일 오류');
		return false;
	}
	return true;
}