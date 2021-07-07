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