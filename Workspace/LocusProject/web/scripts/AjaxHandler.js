/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function changePage(page){
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            document.getElementById("content").innerHTML=xmlhttp.responseText;
        }
    }
    xmlhttp.open("GET",page,true);
    xmlhttp.send();
}

function post(page, params){
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            document.getElementById("content").innerHTML=xmlhttp.responseText;
        }
    }
    xmlhttp.open("POST",page,true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send(params);
}

function emailExists(str){
    var xmlhttp;
    document.getElementById("testing").style.display = 'block';
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        document.getElementById("testing").style.display = 'none';
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            var mess = xmlhttp.responseText;
            //document.getElementById("content").innerHTML=mess;
            if(mess=="true")
                document.getElementById("unique").style.display = 'block';
            else
                document.getElementById("unique").style.display = 'none';
        } 
    }
    xmlhttp.open("POST","./testEmail.htm",true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("email="+encodeURI(str));
}