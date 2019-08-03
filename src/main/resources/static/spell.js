"use strict";

var ajax;
var acallback = null;
function spellSearcher(spellName, callback) {
    acallback = callback
	ajax = new XMLHttpRequest();
	ajax.onreadystatechange = ajaxProcess;
	spellName = spellName.toUpperCase();
	ajax.open("GET", "/read/" + spellName);
	ajax.send();
}

function ajaxProcess() {
	if((ajax.readyState == 4)&&(ajax.status == 200)){
		ajaxCompleted(ajax.responseText);
	}
}

function ajaxCompleted(text) {
	var output = document.getElementById("output");
	if(acallback != null) {
		var data = JSON.parse(text);
		acallback(data);
	}
}

function getSpellNames(callback) {
    //acallback = callback
	ajax = new XMLHttpRequest();
	ajax.onreadystatechange = ajaxProcess;
	ajax.open("GET", "/read/list", false);
	ajax.send();
	var data = JSON.parse(ajax.responseText);
	callback(data);
}

function getLoaders(callback) {
    //acallback = callback
    ajax = new XMLHttpRequest();
    ajax.onreadystatechange = ajaxProcess;
    ajax.open("GET", "/read/loaders", false);
    ajax.send();
    var data = JSON.parse(ajax.responseText);
    callback(data);
}

function writeLoader(file, callback) {
	acallback = callback;
	ajax = new XMLHttpRequest();
	ajax.onreadystatechange = ajaxProcess;
	ajax.open("GET", "/loader/" + file);
	ajax.send();
}

function learnSpell(spellName) {
    spellSearcher(spellName, addSpell)
}

function addSpell(spell) {
	var x = document.getElementById(spell.school).getElementsByTagName("h4");
	var y = x[spell.level].childNodes;
	var validate;
	for (var i = 0; i < y.length; i++) {
		if(y[i].innerHTML == spell.name){
			validate = 1;
		}
	}
	
	if(!validate) {
		var spanTag = document.createElement("SPAN");
		var spanText = document.createTextNode(spell.name);
		spanTag.appendChild(spanText);
		var executable = "display(\"" + spell.name + "\")";
		spanTag.setAttribute("onclick", executable);
		
		if(x[spell.level].style.display == "none") { 
			x[spell.level].style.display = "block";
			x[spell.level].appendChild(document.createElement("BR"));
		}
		x[spell.level].appendChild(spanTag);
		x[spell.level].appendChild(document.createElement("BR"));
	}
}

function hider() {
	var schoolHeaders = document.getElementsByTagName("h4");
	for (var i = 0; i < schoolHeaders.length; i++) {
		schoolHeaders[i].style.display = "none";
	}
}

function display(spellName) {
    spellSearcher(spellName, writeInSidebar)
}

function writeInSidebar(spell) {
	document.getElementById("name").innerHTML = spell.name;
	document.getElementById("cast").innerHTML = spell.time;
	document.getElementById("range").innerHTML = spell.range;
	document.getElementById("component").innerHTML = spell.comp;
	document.getElementById("duration").innerHTML = spell.duration;
	document.getElementById("desc").innerHTML = spell.desc;
	if(spell.higher) {
		document.getElementById("higher").innerHTML = spell.higher; }
	else {
		document.getElementById("higher").innerHTML = ""; }
}

function showHint(str) {
    if (str.length == 0) {
        document.getElementById("txtHint").innerHTML = "";
        return;
    } else {
		var hint = "";
		for(var i = 0; i < spellList.length; i++) {
		    str  = str.toUpperCase();
			var n = spellList[i];
			if (n.search(str) == 0) {
				if (hint == "") {
					hint = spellList[i].toLowerCase();
				} else {
					hint += " , " + spellList[i].toLowerCase();
				}
			}
		}
		if (hint == "") {
			document.getElementById("txtHint").innerHTML = "No Suggestion";
		} else {
			document.getElementById("txtHint").innerHTML = hint;
		}
	}
}

function newSpell() {
    // to do (form.html)
}

function populate(json) {
	for(var e=0;e<json.length;e++) {
		learnSpell(json[e]);
	}
}

function getLoaderList() {
    getLoaders(displayLoader);
}

function displayLoader(list) {
    var dropDown = document.getElementById("loader");
    for(var e=0;e<list.length;e++) {
        var optionTag = document.createElement("OPTION");
        var optionText = document.createTextNode(list[e]);
        optionTag.appendChild(optionText);
        optionTag.setAttribute("value", list[e]);
        dropDown.appendChild(optionTag);
    }

}

function getList() {
    getSpellNames(setList);
}

function setList(list) {
    spellList = list;
}

var spellList = null;
function init() {
    hider();
    //getLoaderList();
    getList();
}

window.onload = init;