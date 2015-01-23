$(function() {
	$("input[name='share']").keyup(function (){
  		var str = $(this).val();
  		var re = /^\d+\.{0,1}(\d{1,3}){0,1}$/;
  		var isValid = re.test(str) || str == "";
  		if (isValid) {
  			$('input[type="submit"]').prop('disabled',false);
  			$('.shareInputFeedback').text("");
  		} else {
  			$('input[type="submit"]').prop('disabled',true);
  			$('.shareInputFeedback').text("Invalid number input. The precision of share should be within three digits");
  		}
  	});	

  	$("input[name='amount']").keyup(function (){
  		var str = $(this).val();
  		var re = /^\d+\.{0,1}(\d{1,2}){0,1}$/;
  		var isValid = re.test(str) || str == "";
  		if (isValid) {
  			$('input[type="submit"]').prop('disabled',false);
  			$('.amountInputFeedback').text("");
  		} else {
  			$('input[type="submit"]').prop('disabled',true);
  			$('.amountInputFeedback').text("Invalid number input. The precision of share should be within two digits");
  		}
  	});	
  	
  	$("input[name='price']").keyup(function (){
  		var str = $(this).val();
  		var re = /^\d+\.{0,1}(\d{1,2}){0,1}$/;
  		var isValid = re.test(str) || str == "";
  		if (isValid) {
  			$('input[type="submit"]').prop('disabled',false);
  			$('.priceInputFeedback').text("");
  		} else {
  			$('input[type="submit"]').prop('disabled',true);
  			$('.priceInputFeedback').text("Invalid number input. The precision of share should be within two digits");
  		}
  	});	

});

	
	
