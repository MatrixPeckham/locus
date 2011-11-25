/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function getRequest(){
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    return xmlhttp;
}

function changePage(page){
    var xmlhttp=getRequest();
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
    var xmlhttp=getRequest();
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
    var xmlhttp=getRequest();
    document.getElementById("testing").style.display = 'block';
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

function nameExists(str){
    var xmlhttp=getRequest();
    document.getElementById("testingname").style.display = 'block';
    xmlhttp.onreadystatechange=function()
    {
        document.getElementById("testingname").style.display = 'none';
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            var mess = xmlhttp.responseText;
            //document.getElementById("content").innerHTML=mess;
            if(mess=="true")
                document.getElementById("uniquename").style.display = 'block';
            else
                document.getElementById("uniquename").style.display = 'none';
        } 
    }
    xmlhttp.open("POST","./testName.htm",true);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("name="+encodeURI(str));
}

function validateAndSubmitRegistration(){
    var valid = true;
    var email=document.getElementById("email").value;
    var atpos=email.indexOf("@");
    var dotpos=email.lastIndexOf(".");
    document.getElementById("requiredemail").style.display='none';
    document.getElementById("invalid").style.display='none';
    document.getElementById("unique").style.display='none';
    document.getElementById("requiredname").style.display='none';
    document.getElementById("uniquename").style.display='none';
    document.getElementById("requiredpass").style.display='none';
    document.getElementById("requiredpass2").style.display='none';
    document.getElementById("mustmatch1").style.display='none';
    document.getElementById("mustmatch2").style.display='none';
    document.getElementById("servererrname").style.display='none';
    if (email==null||email==""){
        document.getElementById("requiredemail").style.display='block';
        valid=false;
    }
    if (atpos<1 || dotpos<atpos+2 || dotpos+2>=email.length){
        document.getElementById("invalid").style.display = 'block';
        valid=false;
    }
    var emailreq=getRequest();
    emailreq.open("POST","./testEmail.htm",false);
    emailreq.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    emailreq.send("email="+encodeURI(email));
    if(emailreq.responseText=="true"){
        document.getElementById("uniqueemail").style.display = 'block';
        valid=false;
    }
    var name=document.getElementById("name").value;
    if(name==""||name==null){
        document.getElementById("requiredname").style.display='block';
        valid=false;
    }
    var namereq=getRequest();
    namereq.open("POST","./testName.htm",false);
    namereq.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    namereq.send("name="+encodeURI(name));
    if(namereq.responseText=="true"){
        document.getElementById("uniquename").style.display = 'block';
        valid=false;
    }
    var pass1=document.getElementById("pass").value;
    var pass2=document.getElementById("pass2").value;
    if(pass1==null||pass1==""){
        document.getElementById("requiredpass").style.display='block';
        valid=false;
    }
    if(pass2==null||pass2==""){
        document.getElementById("requiredpass2").style.display='block';
        valid=false;
    }
    if(pass1!=pass2){
        document.getElementById("mustmatch1").style.display='block';
        document.getElementById("mustmatch2").style.display='block';
        valid=false;
    }
    if(valid==false){
        return false;
    }
    var addreq = getRequest();
    addreq.open("POST","./RegisterUser.htm", false);
    addreq.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    addreq.send("name="+encodeURI(name) + "&" +
        "email="+encodeURI(email) + "&" +
        "pass="+encodeURI(pass1));
    if(addreq.responseText!="OK"){
        document.getElementById("servererrname").style.display='block';
        document.getElementById("servererrname").innerHTML=addreq.responseText;
        return false;
    }
    
    changePage('JSPChunks/Home.jsp');
    
    return true;
}

function login(){
    var name=document.getElementById("logname").value;
    var pass=document.getElementById("logpass").value;
    var xmlhttp = getRequest();
    xmlhttp.open("POST","./Login.htm",false);
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send("username="+encodeURI(name)+"&password="+encodeURI(pass));
    var resp=xmlhttp.responseText;
    if(resp.indexOf("Welcome")==0){
        document.getElementById("loginerr").style.display='none';
        document.getElementById("login").innerHTML=resp;
    } else {
        document.getElementById("loginerr").style.display='block';
        document.getElementById("loginerr").innerHTML=resp;
        
    }
}

function logout(){
    var xmlhttp=getRequest();
    var loginText = '<form id="login" action="javascript:void(0)" onsubmit="login()">'+
    '<span><label>Display Name:</label><input type="text" id="logname"></input></span>'+
    '<span><label>Password:</label><input type="password" id="logpass"></input></span>'+
    '<span><input type="submit" id="loggo" value="Go"></input></span>'+
    '<br />'+
    '<span><label class="error" id="loginerr"></label></span>'+
    '<br/>Not a Member? Click <a onclick="changePage(\'JSPChunks/Registration.jsp\')" href="#">here</a> to register.'+
    '</form>';
    xmlhttp.open("GET","./Logout.htm",false)
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            document.getElementById("login").innerHTML=loginText;
        } 
    }
    xmlhttp.send(null);
}
