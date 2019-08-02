"use strict";

function learnSpell(n) {
	var o = spellSearcher(n);
	var spell = spellList[o];
	
	var x = document.getElementById(spell.school).getElementsByTagName("h4");
	var y = x[spell.level].childNodes;
	var validate;
	for (var i = 0; i < y.length; i++) {
		if(y[i].innerHTML == spell.name){
			validate = 1;
		}
	}
	
	if(!validate) {
		var node = document.createElement("SPAN");
		var nodeText = document.createTextNode(spell.name); 
		node.appendChild(nodeText);
		var executable = "display(\"" + spellList[o].name + "\")";
		executable = executable.toLowerCase();
		node.setAttribute("onclick", executable);
		
		
		if(x[spell.level].style.display == "none") { 
			x[spell.level].style.display = "block";
			x[spell.level].appendChild(document.createElement("BR"));
		}
		x[spell.level].appendChild(node);
		x[spell.level].appendChild(document.createElement("BR"));
	}
}

function hider() {
	var x = document.getElementsByTagName("h4");
	for (var i = 0; i < x.length; i++) {
		x[i].style.display = "none";
	}
}

function display(x) {
	var spell = spellSearcher(x);
	document.getElementById("name").innerHTML = spellList[spell].name;
	document.getElementById("cast").innerHTML = spellList[spell].time;
	document.getElementById("range").innerHTML = spellList[spell].range;
	document.getElementById("component").innerHTML = spellList[spell].comp;
	document.getElementById("duration").innerHTML = spellList[spell].duration;
	document.getElementById("desc").innerHTML = spellList[spell].desc;
	if(spellList[spell].higher) {
		document.getElementById("higher").innerHTML = spellList[spell].higher; }
	else {
		document.getElementById("higher").innerHTML = ""; }
}

function showHint(str) {
    if (str.length == 0) { 
        document.getElementById("txtHint").innerHTML = "";
        return;
    } else {
		var hint = "";
		str = str.toLowerCase();
		for(var i = 0; i < spellList.length; i++) {
			var n = spellList[i].name.toLowerCase();
			if (n.search(str) == 0) {
				if (hint == "") {
					hint = spellList[i].name;
				} else {
					hint += " , " + spellList[i].name;
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

function spellSearcher(str, add) {
	var low = 0;
	var high = spellList.length -1;
	var mid;
	str = str.toLowerCase();

	while (low <= high) {
		mid = Math.round((low + high) / 2);

		if (str.localeCompare(spellList[mid].name.toLowerCase()) == 0) {
				if (!add) {
					return mid;
				} else {
					return;
				}
		}
		if (str.localeCompare(spellList[mid].name) < 0) {
			high = mid - 1;
		} else {
			low = mid + 1;
		}
      }
	  
	if (!add) {
		return -1;
	} else {
		return mid;
	}
}

function newSpell() {
	var newSpell = {
	"level":"",
	"school":"",
	"name":"",
    "time":"",
    "range":"",
    "comp":"",
    "duration":"",
	"desc":"",
	"higher":"<b>At Higher levels</b>. "
	};
	newSpell.name = document.getElementById("name").value;
	newSpell.level = document.getElementById("level").value;
	newSpell.school = document.getElementById("school").value;
	newSpell.time = document.getElementById("time").value;
	newSpell.range = document.getElementById("range").value;
	newSpell.comp = document.getElementById("comp").value;
	newSpell.duration = document.getElementById("duration").value;
	newSpell.desc = document.getElementById("desc").value;
	newSpell.higher += document.getElementById("higher").value;

	var spell = spellSearcher(newSpell.name, 1);
	var place;

	if (newSpell.name.localeCompare(spellList[spell].name) < 0) {
		place = 0;
	} else {
		place = 1;
	}
	  spellList.splice(spell + place, 0, newSpell);

}

function populate(json) {
	for(var e=0;e<json.length;e++) {
		learnSpell(json[e]);
	}
}

var ajax;
var acallback = null;
function access(file, callback) {
	acallback = callback;
	ajax = new XMLHttpRequest();
	ajax.onreadystatechange = ajaxProcess;
	ajax.open("GET", file);
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

window.onload = hider;