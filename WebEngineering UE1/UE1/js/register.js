var isBenutzernameKorrekt = false;
var isPasswortKorrekt = false;
var isDateKorrent = true;
	
$(document).ready(function() {
	
	
	
	$("input#benutzername").keyup(function() {
		isBenutzernameKorrekt = checkTextValue(this);
		checkButtonActivated();
	});
	
	$("input#passwort").keyup(function() {
		isPasswortKorrekt = checkTextValue(this);
		checkButtonActivated();
	});
	
	$("input#inpGeburtstag").keyup(function() {
		isDateKorrent = checkDateValue(this);
		checkButtonActivated();
	});
	
	function checkTextValue(oElement) {
		var isKorrekt = false;
		var sText = oElement.value;
		if(sText.length < 4 || sText.length > 8) {
			oElement.style.backgroundColor = "red";
			$(oElement).next().text("Der Benutzername muss mindestens 4 Zeichen und darf maximal 8 Zeichen enthalten!");
			isKorrekt = false;
		} else {
			oElement.style.backgroundColor = "";
			$(oElement).next().text("");
			isKorrekt = true;
		}
		
		if(sText.length == 0) {
			oElement.style.backgroundColor = "";
			$(oElement).next().text("");
			isKorrekt = false;
		}
		
		return isKorrekt;
	}
	
	function checkDateValue(oElement) {
		var isKorrekt = false;
		var sText = oElement.value;
		var sRegex = "[0-3][0-9].([1][0-2]|[0][1-9]).\\d{4}$";
		
		if(!sText.match(sRegex)) {
			oElement.style.backgroundColor = "red";
			$(oElement).next().text("Verwenden Sie bitte folgendes Datumsformat: dd.mm.yyyy (z.B. 24.12.2012).");
			isKorrekt = false;
		} else {
			oElement.style.backgroundColor = "";
			$(oElement).next().text("");
			isKorrekt = true;
		}
		
		if(sText.length == 0) {
			oElement.style.backgroundColor = "";
			$(oElement).next().text("");
			isKorrekt = true;
		}
		return isKorrekt;
	}
	
	function checkButtonActivated() {
		if((isBenutzernameKorrekt === true) && 
				(isPasswortKorrekt === true) && (isDateKorrent)) {
			$("input#submitButton").removeAttr('disabled','disabled');
		} else {
			$("input#submitButton").attr('disabled','disabled');
		}
	}
	
});
