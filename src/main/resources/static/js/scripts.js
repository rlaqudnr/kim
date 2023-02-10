

var over;
$("#userId").keyup(function() {
	const id = $("#userId").val();
	$.ajax({
		
		
		
		type: "get",
		async: false,
		url: "http://ec2-3-39-201-37.ap-northeast-2.compute.amazonaws.com:80/users/idcheck",
		data: { id: id },
		success: function(data) {
			if (data == 1) {
				$("#signup").attr("type", "button");
				$("#olmessage").text("이미 사용중인 ID 입니다.");

				$("#olmessage").addClass("olmessagef");
				$("#olmessage").removeClass("olmessaget");
			} else {
				$("#olmessage").text("사용 가능한 ID 입니다.");
				$("#olmessage").addClass("olmessaget");
				$("#olmessage").removeClass("olmessagef");
				$("#signup").attr("type", "submit");
			}
		}
	})
});

$("#password").keyup(function() { // 
	const pw = $("#password").val();

	if (pw.length < 8 || pw.length > 20) {
		$("#passwordMs").text("비밀번호는 8자이상 20자 이내로 작성해 주세요.");
		$("#passwordMs").addClass("olmessagef");
		$("#passwordMs").removeClass("olmessaget");
		$("#signup").attr("type", "button");
		;
	} else {

		$("#passwordMs").text("사용가능한 비밀번호 입니다");
		$("#passwordMs").addClass("olmessagef");
		$("#passwordMs").removeClass("olmessaget");
		$("#signup").attr("type", "submit");
	}

});

$("#email").keyup(function() { // 
	
		$("#signup").attr("type", "submit");
	

});



$("#name").keyup(function() { // 
	
		$("#signup").attr("type", "submit");
	

});




























	 
	//$("#userId").keyup(function() { // 중복확인을 다 끝낸후에 id를 변경하면 재중복확인을 해야함.
//	$("#olmessage").text("중복 확인을 해주세요.");
//	$("#signup").attr("type", "button");
//	$("#olmessage").addClass("olmessagef");
//	$("#olmessage").removeClass("olmessaget");
//});
//$("#signup").click(function() {
//	if (!over == 1) //만약 중복확인을 안했을시 if문에 걸림
//	$("#olmessage").text("중복 확인을 해주세요.");
//	$("#signup").attr("type", "button");
//	$("#olmessage").addClass("olmessagef");
//	$("#olmessage").removeClass("olmessaget");
//	}

//)
	




