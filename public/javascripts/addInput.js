var counter=1;
function addInput(divID){
    var newdiv = document.createElement('div');
    newdiv.setAttribute("class", "form-inline");

    var divFrom = document.createElement('div');
    var divTo = document.createElement('div');
    divFrom.setAttribute("class", "form-group");
    divTo.setAttribute("class", "form-group");

    var inputFrom = document.createElement('input');
    var inputTo = document.createElement('input');
    var labelFrom = document.createElement('label');
    var labelTo = document.createElement('label');

    inputFrom.setAttribute("type", "time");
    inputFrom.setAttribute("class", "form-control");
    inputFrom.setAttribute("name", "timesFrom"+divID+"[]");
    inputFrom.setAttribute("id", "fromInput"+counter);

    inputTo.setAttribute("type", "time");
    inputTo.setAttribute("class", "form-control");
    inputTo.setAttribute("name", "timesTo"+divID+"[]");
    inputTo.setAttribute("id", "toInput"+counter);

    labelFrom.setAttribute("for", "fromInput"+counter);
    labelTo.setAttribute("for", "toInput"+counter);
    labelFrom.innerHTML = "von&nbsp";
    labelTo.innerHTML = "&nbspbis&nbsp&nbsp";

    newdiv.appendChild(divFrom);
    newdiv.appendChild(divTo);
    divFrom.appendChild(labelFrom);
    divFrom.appendChild(inputFrom);
    divTo.appendChild(labelTo);
    divTo.appendChild(inputTo);

    document.getElementById(divID).appendChild(newdiv);

}