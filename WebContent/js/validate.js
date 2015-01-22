$(function() {
	$("input[name='share']").keyup(function (){
  		var str = $(this).val();
  		var re = /^\d+(\.\d{1,3}){0,1}$/;
  		var isValid = re.test(str);
  		if (isValid) {
  			$('input[type="submit"]').prop('disabled',false);
  		} else {
  			$('input[type="submit"]').prop('disabled',true);
  			$('.shareInputFeedback').text("The precision of share should be within three digits");
  		}
  	});	

  	$("input[name='amount']").keyup(function (){
  		var str = $(this).val();
  		var re = /^\d+(\.\d{1,2}){0,1}$/;
  		var isValid = re.test(str);
  		if (isValid) {
  			$('input[type="submit"]').prop('disabled',false);
  		} else {
  			$('input[type="submit"]').prop('disabled',true);
  			$('.amountInputFeedback').text("The precision of share should be within two digits");
  		}
  	});	

});

	
	
