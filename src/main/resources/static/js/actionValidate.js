var form = document.forms[1];
form.addEventListener("submit", function(e) {
	var result = formValidate();
	if (!result) e.preventDefault();
})
function formValidate() {
	if (form.action.value === '') {
		alert("액션을 선택해 주세요.");
		return false;
	}
	
	var checked = 0;
	
	if (form.target[0] === undefined) {
		if (form.target.checked) checked++;
	} else {
		form.target.forEach(function(item) {
			if (item.checked) checked++;
		})
	}
	
	
	if (form.action.value === 'edit' && checked !== 1) {
		alert("수정할 항목을 1개 선택해 주세요.");
		return false;
	}

	if (form.action.value === 'del') {
		if (!checked) {
			alert("삭제할 항목을 1개 이상 선택해 주세요.");
			return false;
		} else if (!confirm("정말 삭제하시겠습니까?")) {
			return false;
		}
	}
	
	return true;
}