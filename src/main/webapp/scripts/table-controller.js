// methods (void):
    // addRowBack()                                                         
    // addColumnBack()                                                      
    // setCell(Integer row, Integer column, String content, String note)    
    // setCellContent(Integer row, Integer column, String content)          
    // setCellNote(Integer row, Integer column, String note)                
    // clearCell(Integer row, Integer column)                              
    // clearCellNote(Integer row, Integer column)                           
    // removeRow(Integer row)                                               
    // removeColumn(Integer column)

var rowsLength = 0;
var columnsLength = 0;
var actions = [];

var cellId;
var isOpt = false;
var isTeacher;

const popUpMenu = document.getElementById("pop-up-menu"); 

function init() { 
    isTeacher = getCookieValue("isTeacher");

    if(isTeacher === "false") {
        document.getElementById("opt-button").style.display = 'none';
        return;
    }

    document.getElementById("opt-button").addEventListener("click", function(event) {
        if(isOpt) {
            isOpt = false;
            document.getElementById("opt-button").style.color = "rgb(99, 99, 99)";
            buttonsView("hidden");
            
            if(actions.length == 0)
                return;

            fetch('/digital-statement/table-api', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(actions)
            });

            console.log(actions);
            actions = [];
        }
        else {
            isOpt = true;
            document.getElementById("opt-button").style.color = "blue";
            buttonsView("visible");
        }
    });

    setIdForTableElements(0, 0);
    var cells = document.getElementsByTagName("th");  
    Array.from(cells).forEach(element => {   
        element.addEventListener("click", function(event) {
            if(event.button === 0) {
                popUp(event);
            }
        }); 
    }); 

    cells = document.getElementsByTagName("td");  
    Array.from(cells).forEach(element => {  
        element.addEventListener("click", function(event) {
            if(event.button === 0) {
                popUp(event);
            }
        });
    });
}

function popUp(event) { 
    if(!isOpt)
        return;
    popUpMenu.style.top = (event.pageY + 5) + "px";
    popUpMenu.style.left = (event.pageX + 5) + "px";
    console.log(event.target.id);
    cellId = event.target.id;
    popUpMenu.style.display = "block"; 
}

function setIdForTableElements(rowStart, columnStart) {
    var table = document.getElementById("statement-table");
    var rows = table.getElementsByTagName("tr");
 
    for (var i = 0; i < rows.length; i++) {
        var cells = rows[i].getElementsByTagName("th");
        for (var j = columnStart; j < cells.length; j++) {
            cells[j].id = "th-" + i + "-" + j;
        }
  
        cells = rows[i].getElementsByTagName("td");
        for (var k = columnStart; k < cells.length; k++) {
            cells[k].id = "td-" + i + "-" + k;
        }
    }

    rowsLength = rows.length;
    columnsLength = rows[0].getElementsByTagName("th").length;

    console.log({rowsLength, columnsLength});
}

document.getElementById("statement-article").addEventListener("click", function(event) {
    if(event.target.tagName === 'TD' || event.target.tagName === 'TH')
        return;
    closePopUp();
}); 

function closePopUp() {
    document.getElementById("pop-up-menu").style.display = "none";
}

function buttonsView(visual) {
    document.getElementById("add-row").style.visibility = visual
    document.getElementById("add-column").style.visibility = visual;
}

document.getElementById("add-content").addEventListener("click", function(event) {
    closePopUp();
});

function addRowBack() {
    var table = document.getElementsByTagName("table")[0];
    const row = document.createElement("tr");
    for (var i = 0; i < columnsLength; i++) {
        var cell = document.createElement("td");
        cell.addEventListener("click", function(event) {
            if(event.button === 0) {
                popUp(event);
            }
        });
        cell.id = "td-" + rowsLength + "-" + i;
        row.appendChild(cell);
    }
    rowsLength++;
    table.getElementsByTagName("tbody")[0].append(row);

    var action = 'addRowBack()'; 
    actions.push(action);
}

function addColumnBack() {
    var table = document.getElementsByTagName("table")[0];
    var rows = table.getElementsByTagName("tr");

    for (var i = 0; i < rows.length; i++) {
        if(rows[i].firstElementChild.tagName === 'TH')
            var cell = document.createElement("th");
        else
            var cell = document.createElement("td");

        cell.addEventListener("click", function(event) {
            if(event.button === 0) {
                popUp(event);
            }
        });

        cell.id = cell.tagName.toLowerCase() + "-" + i + "-" + columnsLength;
        rows[i].appendChild(cell);
    }
    columnsLength++;

    var action = 'addColumnBack()';
    actions.push(action);
}

function setContent() {
    var cell = document.getElementById(cellId);
    var content = cell.textContent;
    cell.textContent = "";
    var textArea = document.createElement("textarea");
    textArea.textContent = content;

    textArea.style.height = cell.offsetHeight + "px";
    textArea.style.width = cell.offsetWidth + "px";

    textArea.addEventListener("keypress", processTextArea);
    textArea.addEventListener("focusout", processTextArea);

    function processTextArea(event) {
        if (event.key === "Enter" || event.type === "focusout") {
            if(event.key === "Enter")
                cell.getElementsByTagName("textarea")[0].removeEventListener("focusout", processTextArea);

            cell.textContent = textArea.value;
            var row = getCord(cell.id, 1);
            var column = getCord(cell.id, 2);
            var action =  'setCellContent(' + row + ', ' + column + ', ' + textArea.value + ')';
            actions.push(action);
            var area = cell.getElementsByTagName("textarea")[0];
            area.removeEventListener("focusout", processTextArea);
            area.remove();
        }
    }

    textArea.addEventListener("input", function(event) {
        textArea.style.height = "auto";
        textArea.style.height = (textArea.scrollHeight) + "px";
    });
    cell.append(textArea);
    cell.getElementsByTagName("textarea")[0].focus();
    closePopUp();
}

function setNote() {
}

function clearCell() {
    var cell = document.getElementById(cellId);
    cell.textContent = "";
    closePopUp();

    var row = getCord(cell.id, 1);
    var column = getCord(cell.id, 2);
    var action = 'clearCell(' + row + ', ' + column + ')';
    actions.push(action);
}

function clearCellNote(row, column) {

}

function removeRow() {
    closePopUp();
    if(rowsLength === 1) {
        alert("Это минимальный размер рядов");
        return;
    }
    var rowIndex = cellId.split("-", 3)[1];
    document.getElementsByTagName("table")[0].getElementsByTagName("tr")[rowIndex].remove();
    setIdForTableElements(rowIndex, 0);

    var row = rowIndex;
    var action = 'removeRow(' + row + ')';
    actions.push(action);
}

function removeColumn() {
    closePopUp();
    if(columnsLength === 1) {
        alert("Это минимальный размер колонок");
        return;
    }
    var columnIndex = cellId.split("-", 3)[2];
    var rows = document.getElementsByTagName("table")[0].getElementsByTagName("tr");

    for (var i = 0; i < rows.length; i++) {
        var cells = rows[i].getElementsByTagName("th");
        if(cells.length != 0)
            cells[columnIndex].remove();
        else
            rows[i].getElementsByTagName("td")[columnIndex].remove();
    }

    setIdForTableElements(0, columnIndex);

    var column = columnIndex;
    var action = 'removeColumn(' + column + ')';
    actions.push(action);
}

function getCord(id, index) {
    const idParts = id.split('-');
    console.log(idParts[index]);
    return idParts[index];
}

function getCookieValue(cookieName) {
    const name = cookieName + "=";
    const decodedCookie = decodeURIComponent(document.cookie);
    const cookieArray = decodedCookie.split(';');
    for (let i = 0; i < cookieArray.length; i++) {
        let cookie = cookieArray[i];
        while (cookie.charAt(0) === ' ') {
            cookie = cookie.substring(1);
        }
        if (cookie.indexOf(name) === 0) {
            return cookie.substring(name.length, cookie.length);
        }
    }
    return "";
}

init();